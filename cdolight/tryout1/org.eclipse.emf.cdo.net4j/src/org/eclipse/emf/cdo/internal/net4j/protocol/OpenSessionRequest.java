/***************************************************************************
 * Copyright (c) 2004 - 2011 Eike Stepper (Berlin, Germany) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Eike Stepper - initial API and implementation
 *    Simon McDuff - bug 230832
 **************************************************************************/
package org.eclipse.emf.cdo.internal.net4j.protocol;

import java.io.IOException;

import org.eclipse.emf.cdo.common.CDOCommonRepository;
import org.eclipse.emf.cdo.common.CDOCommonSession.Options.PassiveUpdateMode;
import org.eclipse.emf.cdo.common.model.CDOPackageUnit;
import org.eclipse.emf.cdo.common.protocol.CDODataInput;
import org.eclipse.emf.cdo.common.protocol.CDODataOutput;
import org.eclipse.emf.cdo.common.protocol.CDOProtocolConstants;
import org.eclipse.emf.cdo.internal.net4j.bundle.OM;
import org.eclipse.emf.cdo.spi.common.model.InternalCDOPackageUnit;
import org.eclipse.emf.spi.cdo.CDOSessionProtocol.OpenSessionResult;
import org.eclipse.net4j.util.om.monitor.OMMonitor;
import org.eclipse.net4j.util.om.trace.ContextTracer;

/**
 * @author Eike Stepper
 */
public class OpenSessionRequest extends CDOClientRequestWithMonitoring<OpenSessionResult>
{
  private static final ContextTracer TRACER = new ContextTracer(OM.DEBUG_PROTOCOL, OpenSessionRequest.class);

  private String repositoryName;

  private boolean passiveUpdateEnabled;

  private PassiveUpdateMode passiveUpdateMode;

  private OpenSessionResult result;

  public OpenSessionRequest(CDOClientProtocol protocol, String repositoryName, boolean passiveUpdateEnabled,
      PassiveUpdateMode passiveUpdateMode)
  {
    super(protocol, CDOProtocolConstants.SIGNAL_OPEN_SESSION);
    this.repositoryName = repositoryName;
    this.passiveUpdateEnabled = passiveUpdateEnabled;
    this.passiveUpdateMode = passiveUpdateMode;
  }

  @Override
  protected void requesting(CDODataOutput out, OMMonitor monitor) throws IOException
  {
    if (TRACER.isEnabled())
    {
      TRACER.format("Writing repositoryName: {0}", repositoryName); //$NON-NLS-1$
    }

    out.writeString(repositoryName);

    if (TRACER.isEnabled())
    {
      TRACER.format("Writing passiveUpdateEnabled: {0}", passiveUpdateEnabled); //$NON-NLS-1$
    }

    out.writeBoolean(passiveUpdateEnabled);

    if (TRACER.isEnabled())
    {
      TRACER.format("Writing passiveUpdateMode: {0}", passiveUpdateMode); //$NON-NLS-1$
    }

    out.writeEnum(passiveUpdateMode);
  }

  @Override
  protected OpenSessionResult confirming(CDODataInput in, OMMonitor monitor) throws IOException
  {
    int sessionID = in.readInt();
    if (TRACER.isEnabled())
    {
      TRACER.format("Read sessionID: {0}", sessionID); //$NON-NLS-1$
    }

    String userID = in.readString();
    if (TRACER.isEnabled())
    {
      TRACER.format("Read userID: {0}", userID); //$NON-NLS-1$
    }

    String repositoryUUID = in.readString();
    if (TRACER.isEnabled())
    {
      TRACER.format("Read repositoryUUID: {0}", repositoryUUID); //$NON-NLS-1$
    }

    CDOCommonRepository.Type repositoryType = in.readEnum(CDOCommonRepository.Type.class);
    if (TRACER.isEnabled())
    {
      TRACER.format("Read repositoryType: {0}", repositoryType); //$NON-NLS-1$
    }

    CDOCommonRepository.State repositoryState = in.readEnum(CDOCommonRepository.State.class);
    if (TRACER.isEnabled())
    {
      TRACER.format("Read repositoryState: {0}", repositoryState); //$NON-NLS-1$
    }

    String storeType = in.readString();
    if (TRACER.isEnabled())
    {
      TRACER.format("Read storeType: {0}", storeType); //$NON-NLS-1$
    }

//    Set<CDOID.ObjectType> objectIDTypes = new HashSet<ObjectType>();
//    int types = in.readInt();
//    for (int i = 0; i < types; i++)
//    {
//      CDOID.ObjectType objectIDType = in.readEnum(CDOID.ObjectType.class);
//      if (TRACER.isEnabled())
//      {
//        TRACER.format("Read objectIDType: {0}", objectIDType); //$NON-NLS-1$
//      }
//
//      objectIDTypes.add(objectIDType);
//    }

    long repositoryCreationTime = in.readLong();
    if (TRACER.isEnabled())
    {
      TRACER.format("Read repositoryCreationTime: {0}", repositoryCreationTime); //$NON-NLS-1$
    }

    long lastUpdateTime = in.readLong();
    if (TRACER.isEnabled())
    {
      TRACER.format("Read lastUpdateTime: {0}", lastUpdateTime); //$NON-NLS-1$
    }

    long rootResourceID = in.readCDOID();
    if (TRACER.isEnabled())
    {
      TRACER.format("Read rootResourceID: {0}", rootResourceID); //$NON-NLS-1$
    }

    boolean repositorySupportingAudits = in.readBoolean();
    if (TRACER.isEnabled())
    {
      TRACER.format("Read repositorySupportingAudits: {0}", repositorySupportingAudits); //$NON-NLS-1$
    }

    boolean repositorySupportingBranches = in.readBoolean();
    if (TRACER.isEnabled())
    {
      TRACER.format("Read repositorySupportingBranches: {0}", repositorySupportingBranches); //$NON-NLS-1$
    }

    boolean repositorySupportingEcore = in.readBoolean();
    if (TRACER.isEnabled())
    {
      TRACER.format("Read repositorySupportingEcore: {0}", repositorySupportingEcore); //$NON-NLS-1$
    }

    boolean repositoryEnsuringReferentialIntegrity = in.readBoolean();
    if (TRACER.isEnabled())
    {
      TRACER.format("Read repositoryEnsuringReferentialIntegrity: {0}", repositoryEnsuringReferentialIntegrity); //$NON-NLS-1$
    }

    result = new OpenSessionResult(sessionID, userID, repositoryUUID, repositoryType, repositoryState, storeType, repositoryCreationTime, lastUpdateTime, rootResourceID, repositorySupportingAudits,
        repositorySupportingBranches, repositorySupportingEcore, repositoryEnsuringReferentialIntegrity);

    CDOPackageUnit[] packageUnits = in.readCDOPackageUnits(null);
    for (int i = 0; i < packageUnits.length; i++)
    {
      result.getPackageUnits().add((InternalCDOPackageUnit)packageUnits[i]);
    }

    return result;
  }
}
