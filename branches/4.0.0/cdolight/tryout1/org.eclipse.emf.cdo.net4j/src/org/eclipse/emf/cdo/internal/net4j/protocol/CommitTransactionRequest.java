/***************************************************************************
 * Copyright (c) 2004 - 2011 Eike Stepper (Berlin, Germany) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Eike Stepper - initial API and implementation
 *    Simon McDuff - bug 201266
 *    Simon McDuff - bug 215688
 *    Simon McDuff - bug 213402
 *    Andre Dietisheim - bug 256649
 **************************************************************************/
package org.eclipse.emf.cdo.internal.net4j.protocol;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.emf.cdo.CDOObject;
import org.eclipse.emf.cdo.CDOObjectReference;
import org.eclipse.emf.cdo.common.commit.CDOCommitData;
import org.eclipse.emf.cdo.common.id.CDOIDReference;
import org.eclipse.emf.cdo.common.id.CDOIDUtil;
import org.eclipse.emf.cdo.common.lob.CDOBlob;
import org.eclipse.emf.cdo.common.lob.CDOClob;
import org.eclipse.emf.cdo.common.lob.CDOLob;
import org.eclipse.emf.cdo.common.model.CDOPackageUnit;
import org.eclipse.emf.cdo.common.protocol.CDODataInput;
import org.eclipse.emf.cdo.common.protocol.CDODataOutput;
import org.eclipse.emf.cdo.common.protocol.CDOProtocolConstants;
import org.eclipse.emf.cdo.common.revision.CDOIDAndVersion;
import org.eclipse.emf.cdo.common.revision.CDORevision;
import org.eclipse.emf.cdo.common.revision.CDORevisionKey;
import org.eclipse.emf.cdo.common.revision.delta.CDORevisionDelta;
import org.eclipse.emf.cdo.internal.net4j.bundle.OM;
import org.eclipse.emf.cdo.transaction.CDOTransaction;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.internal.cdo.object.CDOObjectReferenceImpl;
import org.eclipse.emf.spi.cdo.CDOSessionProtocol.CommitTransactionResult;
import org.eclipse.net4j.util.io.ExtendedDataOutputStream;
import org.eclipse.net4j.util.io.IOUtil;
import org.eclipse.net4j.util.om.monitor.OMMonitor;
import org.eclipse.net4j.util.om.trace.ContextTracer;

/**
 * @author Eike Stepper
 */
public class CommitTransactionRequest extends CDOClientRequestWithMonitoring<CommitTransactionResult>
{
  private static final ContextTracer TRACER = new ContextTracer(OM.DEBUG_PROTOCOL, CommitTransactionRequest.class);

  private String comment;

  private boolean releaseLocks;

  private CDOCommitData commitData;

  private Collection<CDOLob<?>> lobs;

  private CDOTransaction transaction;

  public CommitTransactionRequest(CDOClientProtocol protocol, int transactionID, String comment, boolean releaseLocks, CDOCommitData commitData, Collection<CDOLob<?>> lobs)
  {
    this(protocol, CDOProtocolConstants.SIGNAL_COMMIT_TRANSACTION, transactionID, comment, releaseLocks,
        commitData, lobs);
  }

  public CommitTransactionRequest(CDOClientProtocol protocol, short signalID, int transactionID, String comment,
      boolean releaseLocks, CDOCommitData commitData, Collection<CDOLob<?>> lobs)
  {
    super(protocol, signalID);

    transaction = (CDOTransaction)getSession().getView(transactionID);
    this.comment = comment;
    this.releaseLocks = releaseLocks;
    this.commitData = commitData;
    this.lobs = lobs;
  }

  @Override
  protected int getMonitorTimeoutSeconds()
  {
    org.eclipse.emf.cdo.net4j.CDOSession session = (org.eclipse.emf.cdo.net4j.CDOSession)getSession();
    return session.options().getCommitTimeout();
  }


  @Override
  protected void requesting(CDODataOutput out, OMMonitor monitor) throws IOException
  {
    requestingTransactionInfo(out);
    requestingCommit(out);
  }

  protected void requestingTransactionInfo(CDODataOutput out) throws IOException
  {
    out.writeInt(transaction.getViewID());
  }

  protected void requestingCommit(CDODataOutput out) throws IOException
  {
    List<CDOPackageUnit> newPackageUnits = commitData.getNewPackageUnits();
    List<CDORevision> newObjects = commitData.getNewObjects();
    List<CDORevisionDelta> changedObjects = commitData.getChangedObjects();
    List<Long> detachedObjects = commitData.getDetachedObjects();

    out.writeBoolean(releaseLocks);
    out.writeString(comment);
    out.writeInt(newPackageUnits.size());
    out.writeInt(newObjects.size());
    out.writeInt(changedObjects.size());
    out.writeInt(detachedObjects.size());

    if (TRACER.isEnabled())
    {
      TRACER.format("Writing {0} new package units", newPackageUnits.size()); //$NON-NLS-1$
    }

    for (CDOPackageUnit newPackageUnit : newPackageUnits)
    {
      out.writeCDOPackageUnit(newPackageUnit, true);
    }

    if (TRACER.isEnabled())
    {
      TRACER.format("Writing {0} new objects", newObjects.size()); //$NON-NLS-1$
    }

    for (CDOIDAndVersion newObject : newObjects)
    {
      out.writeCDORevision((CDORevision)newObject, CDORevision.UNCHUNKED);
    }

    if (TRACER.isEnabled())
    {
      TRACER.format("Writing {0} dirty objects", changedObjects.size()); //$NON-NLS-1$
    }

    for (CDORevisionKey changedObject : changedObjects)
    {
      out.writeCDORevisionDelta((CDORevisionDelta)changedObject);
    }

    if (TRACER.isEnabled())
    {
      TRACER.format("Writing {0} detached objects", detachedObjects.size()); //$NON-NLS-1$
    }

    boolean ensuringReferentialIntegrity = getSession().getRepositoryInfo().isEnsuringReferentialIntegrity();
    for (Long detachedObject : detachedObjects)
    {
      long id = detachedObject;
      out.writeCDOID(id);
      if (ensuringReferentialIntegrity)
      {
        EClass eClass = getObjectType(id);
        out.writeCDOClassifierRef(eClass);
      }
    }

    requestingLobs();
  }

  protected void requestingLobs() throws IOException
  {
    ExtendedDataOutputStream out = getRequestStream();
    out.writeInt(lobs.size());
    for (CDOLob<?> lob : lobs)
    {
      out.writeByteArray(lob.getID());
      long size = lob.getSize();
      if (lob instanceof CDOBlob)
      {
        CDOBlob blob = (CDOBlob)lob;
        out.writeLong(size);
        IOUtil.copyBinary(blob.getContents(), out, size);
      }
      else
      {
        CDOClob clob = (CDOClob)lob;
        out.writeLong(-size);
        IOUtil.copyCharacter(clob.getContents(), new OutputStreamWriter(out), size);
      }
    }
  }

  protected EClass getObjectType(long id)
  {
    CDOObject object = transaction.getObject(id);
    return object.eClass();
  }

  @Override
  protected CommitTransactionResult confirming(CDODataInput in, OMMonitor monitor) throws IOException
  {
    CommitTransactionResult result = confirmingCheckError(in);
    if (result != null)
    {
      return result;
    }

    result = confirmingResult(in);
    confirmingMappingNewObjects(in, result);
    return result;
  }

  protected CommitTransactionResult confirmingCheckError(CDODataInput in) throws IOException
  {
    boolean success = in.readBoolean();
    if (!success)
    {
      String rollbackMessage = in.readString();
      OM.LOG.error(rollbackMessage);

      long previousTimeStamp = in.readLong();

      List<CDOObjectReference> xRefs = null;
      int size = in.readInt();
      if (size != 0)
      {
        xRefs = new ArrayList<CDOObjectReference>(size);
        for (int i = 0; i < size; i++)
        {
          CDOIDReference idReference = in.readCDOIDReference();
          xRefs.add(new CDOObjectReferenceImpl(transaction, idReference));
        }
      }

      return new CommitTransactionResult(rollbackMessage, xRefs);
    }

    return null;
  }

  protected CommitTransactionResult confirmingResult(CDODataInput in) throws IOException
  {
    long previousTimeStamp = in.readLong();
    return new CommitTransactionResult();
  }

  protected void confirmingMappingNewObjects(CDODataInput in, CommitTransactionResult result) throws IOException
  {
    for (;;)
    {
      long id = in.readCDOID();
      if (CDOIDUtil.isNull(id))
      {
        break;
      }

//      if (id instanceof CDOIDTemp)
//      {
//        long oldID = (Long)id;
//        long newID = in.readCDOID();
//        result.addIDMapping(oldID, newID);
//      }
//      else
//      {
//        throw new ClassCastException("Not a temporary ID: " + id);
//      }
    }
  }
}
