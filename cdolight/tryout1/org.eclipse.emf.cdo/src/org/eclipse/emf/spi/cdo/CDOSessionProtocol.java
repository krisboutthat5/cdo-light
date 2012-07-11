/***************************************************************************
 * Copyright (c) 2004 - 2011 Eike Stepper (Berlin, Germany) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Eike Stepper - initial API and implementation
 **************************************************************************/
package org.eclipse.emf.spi.cdo;

import org.eclipse.emf.cdo.CDOObject;
import org.eclipse.emf.cdo.CDOObjectReference;
import org.eclipse.emf.cdo.common.CDOCommonRepository;
import org.eclipse.emf.cdo.common.CDOCommonSession.Options.PassiveUpdateMode;
import org.eclipse.emf.cdo.common.branch.CDOBranch;
import org.eclipse.emf.cdo.common.branch.CDOBranchPoint;
import org.eclipse.emf.cdo.common.commit.CDOChangeSetData;
import org.eclipse.emf.cdo.common.commit.CDOCommitData;
import org.eclipse.emf.cdo.common.lob.CDOLob;
import org.eclipse.emf.cdo.common.lob.CDOLobInfo;
import org.eclipse.emf.cdo.common.model.CDOPackageUnit;
import org.eclipse.emf.cdo.common.protocol.CDOProtocol;
import org.eclipse.emf.cdo.common.revision.CDOIDAndVersion;
import org.eclipse.emf.cdo.common.revision.CDORevisionHandler;
import org.eclipse.emf.cdo.common.revision.CDORevisionKey;
import org.eclipse.emf.cdo.common.revision.delta.CDORevisionDelta;
import org.eclipse.emf.cdo.common.util.CDOCommonUtil;
import org.eclipse.emf.cdo.session.remote.CDORemoteSession;
import org.eclipse.emf.cdo.session.remote.CDORemoteSessionMessage;
import org.eclipse.emf.cdo.spi.common.CDORawReplicationContext;
import org.eclipse.emf.cdo.spi.common.CDOReplicationContext;
import org.eclipse.emf.cdo.spi.common.branch.InternalCDOBranchManager.BranchLoader;
import org.eclipse.emf.cdo.spi.common.commit.CDORevisionAvailabilityInfo;
import org.eclipse.emf.cdo.spi.common.commit.InternalCDOCommitInfoManager.CommitInfoLoader;
import org.eclipse.emf.cdo.spi.common.model.InternalCDOPackageRegistry.PackageLoader;
import org.eclipse.emf.cdo.spi.common.model.InternalCDOPackageUnit;
import org.eclipse.emf.cdo.spi.common.revision.InternalCDORevision;
import org.eclipse.emf.cdo.spi.common.revision.InternalCDORevisionManager.RevisionLoader;
import org.eclipse.emf.cdo.view.CDOView;

import org.eclipse.net4j.util.concurrent.IRWLockManager.LockType;
import org.eclipse.net4j.util.om.monitor.OMMonitor;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.spi.cdo.InternalCDOXATransaction.InternalCDOXACommitContext;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Eike Stepper
 * @since 2.0
 * @noextend This interface is not intended to be extended by clients.
 * @noimplement This interface is not intended to be implemented by clients.
 */
public interface CDOSessionProtocol extends CDOProtocol, PackageLoader, BranchLoader, RevisionLoader, CommitInfoLoader
{
  public RepositoryTimeResult getRepositoryTime();

  /**
   * @since 3.0
   */
  public void disablePassiveUpdate();

  /**
   * @since 3.0
   */
  public void setPassiveUpdateMode(PassiveUpdateMode mode);

  /**
   * @since 3.0
   */
  public RefreshSessionResult refresh(Map<Long, InternalCDORevision> viewedRevisions, int initialChunkSize,
      boolean enablePassiveUpdates);

  /**
   * @param accessIndex
   *          Index of the item access at the client (with modifications)
   * @param fetchIndex
   *          Index of the item access at the server (without any modifications)
   * @param fromIndex
   *          Load objects at the client from fromIndex (inclusive)
   * @param toIndex
   *          Load objects at the client to toIndex (inclusive)
   */
  public Object loadChunk(InternalCDORevision revision, EStructuralFeature feature, int accessIndex, int fetchIndex,
      int fromIndex, int toIndex);

  /**
   * @since 4.0
   */
  public void openView(int viewID, boolean readOnly);

  /**
   * @since 4.0
   */
  public CDOBranchPoint openView(int viewID, boolean readOnly, String durableLockingID);

  /**
   * @since 4.0
   */
  public void switchTarget(int viewID, List<InternalCDOObject> invalidObjects,
      List<Long> allChangedObjects, List<Long> allDetachedObjects, OMMonitor monitor);

  public void closeView(int viewID);

  public void changeSubscription(int viewId, List<Long> ids, boolean subscribeMode, boolean clear);

  /**
   * @since 4.0
   */
  public void query(CDOView view, AbstractQueryIterator<?> queryResult);

  public boolean cancelQuery(int queryId);

  /**
   * @param cdoBranch 
 * @param viewID 
 * @since 4.0
   */
  public LockObjectsResult lockObjects(List<InternalCDORevision> viewedRevisions, int viewID,  LockType lockType, long timeout) throws InterruptedException;

  /**
   * @since 3.0
   */
  public void unlockObjects(CDOView view, Collection<? extends CDOObject> objects, LockType lockType);

  /**
   * @since 3.0
   */
  public boolean isObjectLocked(CDOView view, CDOObject object, LockType lockType, boolean byOthers);

  /**
   * @since 4.0
   */
  public String changeLockArea(CDOView view, boolean create);

  /**
   * @since 4.0
   */
  public List<byte[]> queryLobs(Set<byte[]> ids);

  /**
   * @since 4.0
   */
  public void loadLob(CDOLobInfo info, Object outputStreamOrWriter) throws IOException;

  /**
   * @since 4.0
   */
  public CommitTransactionResult commitTransaction(int transactionID, String comment, boolean releaseLocks, CDOCommitData commitData, Collection<CDOLob<?>> lobs, OMMonitor monitor);

  /**
   * @since 4.0
   */
  public CommitTransactionResult commitDelegation(String userID, String comment,
      CDOCommitData commitData, Map<Long, EClass> detachedObjectTypes, Collection<CDOLob<?>> lobs, OMMonitor monitor);

  /**
   * @since 3.0
   */
  public CommitTransactionResult commitXATransactionPhase1(InternalCDOXACommitContext xaContext, OMMonitor monitor);

  /**
   * @since 3.0
   */
  public CommitTransactionResult commitXATransactionPhase2(InternalCDOXACommitContext xaContext, OMMonitor monitor);

  /**
   * @since 3.0
   */
  public CommitTransactionResult commitXATransactionPhase3(InternalCDOXACommitContext xaContext, OMMonitor monitor);

  /**
   * @since 3.0
   */
  public CommitTransactionResult commitXATransactionCancel(InternalCDOXACommitContext xaContext, OMMonitor monitor);

  public List<CDORemoteSession> getRemoteSessions(InternalCDORemoteSessionManager manager, boolean subscribe);

  /**
   * @since 3.0
   */
  public Set<Integer> sendRemoteMessage(CDORemoteSessionMessage message, List<CDORemoteSession> recipients);

  /**
   * @since 3.0
   */
  public boolean unsubscribeRemoteSessions();


  /**
   * @since 3.0
   */
  public CDOChangeSetData[] loadChangeSets();

  /**
   * @since 4.0
   */
  public Set<Long> loadMergeData(CDORevisionAvailabilityInfo targetInfo, CDORevisionAvailabilityInfo sourceInfo,
      CDORevisionAvailabilityInfo targetBaseInfo, CDORevisionAvailabilityInfo sourceBaseInfo);

  /**
   * @since 4.0
   */
  public void handleRevisions(EClass eClass, CDOBranch branch, boolean exactBranch, long timeStamp, boolean exactTime,
      CDORevisionHandler handler);

  /**
   * @author Eike Stepper
   * @since 3.0
   * @noinstantiate This class is not intended to be instantiated by clients.
   */
  public final class OpenSessionResult
  {
    private int sessionID;

    private String userID;

    private String repositoryUUID;

    private CDOCommonRepository.Type repositoryType;

    private CDOCommonRepository.State repositoryState;

    private String storeType;

    private long repositoryCreationTime;

    private RepositoryTimeResult repositoryTimeResult;

    private long rootResourceID;

    private boolean repositorySupportingAudits;

    private boolean repositorySupportingBranches;

    private boolean repositorySupportingEcore;

    private boolean repositoryEnsuringReferentialIntegrity;

    private List<InternalCDOPackageUnit> packageUnits = new ArrayList<InternalCDOPackageUnit>();

    /**
     * @since 4.0
     */
    public OpenSessionResult(int sessionID, String userID, String repositoryUUID,
        CDOCommonRepository.Type repositoryType, CDOCommonRepository.State repositoryState, String storeType,
        long repositoryCreationTime, long lastUpdateTime, long rootResourceID,
        boolean repositorySupportingAudits, boolean repositorySupportingBranches, boolean repositorySupportingEcore,
        boolean repositoryEnsuringReferentialIntegrity)
    {
      this.sessionID = sessionID;
      this.userID = userID;
      this.repositoryUUID = repositoryUUID;
      this.repositoryType = repositoryType;
      this.repositoryState = repositoryState;
      this.storeType = storeType;
      this.repositoryCreationTime = repositoryCreationTime;
      this.rootResourceID = rootResourceID;
      this.repositorySupportingAudits = repositorySupportingAudits;
      this.repositorySupportingBranches = repositorySupportingBranches;
      this.repositorySupportingEcore = repositorySupportingEcore;
      this.repositoryEnsuringReferentialIntegrity = repositoryEnsuringReferentialIntegrity;
    }

    public int getSessionID()
    {
      return sessionID;
    }

    /**
     * @since 3.0
     */
    public String getUserID()
    {
      return userID;
    }

    public String getRepositoryUUID()
    {
      return repositoryUUID;
    }

    /**
     * @since 3.0
     */
    public CDOCommonRepository.Type getRepositoryType()
    {
      return repositoryType;
    }

    /**
     * @since 3.0
     */
    public CDOCommonRepository.State getRepositoryState()
    {
      return repositoryState;
    }

    /**
     * @since 3.0
     */
    public String getStoreType()
    {
      return storeType;
    }


    /**
     * @since 3.0
     */
    public long getRootResourceID()
    {
      return rootResourceID;
    }

    public long getRepositoryCreationTime()
    {
      return repositoryCreationTime;
    }

    public boolean isRepositorySupportingAudits()
    {
      return repositorySupportingAudits;
    }

    /**
     * @since 3.0
     */
    public boolean isRepositorySupportingBranches()
    {
      return repositorySupportingBranches;
    }

    /**
     * @since 4.0
     */
    public boolean isRepositorySupportingEcore()
    {
      return repositorySupportingEcore;
    }

    /**
     * @since 4.0
     */
    public boolean isRepositoryEnsuringReferentialIntegrity()
    {
      return repositoryEnsuringReferentialIntegrity;
    }

    public RepositoryTimeResult getRepositoryTimeResult()
    {
      return repositoryTimeResult;
    }

    public void setRepositoryTimeResult(RepositoryTimeResult repositoryTimeResult)
    {
      this.repositoryTimeResult = repositoryTimeResult;
    }


    public List<InternalCDOPackageUnit> getPackageUnits()
    {
      return packageUnits;
    }
  }

  /**
   * @author Eike Stepper
   * @since 3.0
   */
  public static final class RefreshSessionResult
  {
    private List<CDOPackageUnit> packageUnits = new ArrayList<CDOPackageUnit>();

    private List<InternalCDORevision> changedObjects = new ArrayList<InternalCDORevision>();

    private List<Long> detachedObjects = new ArrayList<Long>();

    public RefreshSessionResult()
    {
    }

    public List<CDOPackageUnit> getPackageUnits()
    {
      return packageUnits;
    }

    public List<InternalCDORevision> getChangedObjects()
    {
      return changedObjects;
    }

    public List<Long> getDetachedObjects()
    {
      return detachedObjects;
    }

    public void addPackageUnit(CDOPackageUnit packageUnit)
    {
      packageUnits.add(packageUnit);
    }

    public void addChangedObject(InternalCDORevision revision)
    {
      changedObjects.add(revision);
    }

    public void addDetachedObject(Long id)
    {
      detachedObjects.add(id);
    }
  }

  /**
   * @author Eike Stepper
   */
  public final class RepositoryTimeResult
  {
    private long requested;

    private long indicated;

    private long responded;

    private long confirmed;

    public RepositoryTimeResult()
    {
    }

    public long getRequested()
    {
      return requested;
    }

    public void setRequested(long requested)
    {
      this.requested = requested;
    }

    public long getIndicated()
    {
      return indicated;
    }

    public void setIndicated(long indicated)
    {
      this.indicated = indicated;
    }

    public long getResponded()
    {
      return responded;
    }

    public void setResponded(long responded)
    {
      this.responded = responded;
    }

    public long getConfirmed()
    {
      return confirmed;
    }

    public void setConfirmed(long confirmed)
    {
      this.confirmed = confirmed;
    }

    public long getAproximateRepositoryOffset()
    {
      long latency = confirmed - requested >> 1;
      long shift = confirmed - responded;
      return shift - latency;
    }

    public long getAproximateRepositoryTime()
    {
      long offset = getAproximateRepositoryOffset();
      return System.currentTimeMillis() + offset;
    }

    @Override
    public String toString()
    {
      return MessageFormat.format(
          "RepositoryTime[requested={0}, indicated={1}, responded={2}, confirmed={3}]", //$NON-NLS-1$
          requested, indicated,
          responded, confirmed);
    }
  }

  /**
   * @author Eike Stepper
   * @since 3.0
   */
  public final class CommitTransactionResult implements CDOBranchPoint
  {
    private String rollbackMessage;

    private List<CDOObjectReference> xRefs;

    /**
     * @since 4.0
     */
    public CommitTransactionResult(String rollbackMessage, List<CDOObjectReference> xRefs)
    {
      this.rollbackMessage = rollbackMessage;
      this.xRefs = xRefs;
    }

    /**
     * @since 4.0
     */
    public CommitTransactionResult()
    {
    }


    public String getRollbackMessage()
    {
      return rollbackMessage;
    }


    /**
     * @since 4.0
     */
    public List<CDOObjectReference> getXRefs()
    {
      return xRefs;
    }

	public CDOBranch getBranch() {
		// TODO Auto-generated method stub
		return null;
	}

  }

  /**
   * @since 4.0
   */
  public static final class LockObjectsResult
  {
    private boolean successful;

    private boolean timedOut;

    private boolean waitForUpdate;

    private long[] staleRevisions;

    public LockObjectsResult(boolean successful, boolean timedOut, boolean waitForUpdate,
        long[] staleRevisions)
    {
      this.successful = successful;
      this.timedOut = timedOut;
      this.waitForUpdate = waitForUpdate;
      this.staleRevisions = staleRevisions;
    }

    public boolean isSuccessful()
    {
      return successful;
    }

    public boolean isTimedOut()
    {
      return timedOut;
    }

    public boolean isWaitForUpdate()
    {
      return waitForUpdate;
    }

    public long[] getStaleRevisions()
    {
      return staleRevisions;
    }
  }
}
