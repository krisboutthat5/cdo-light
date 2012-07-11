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
package org.eclipse.emf.cdo.internal.net4j.protocol;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.cdo.CDOObject;
import org.eclipse.emf.cdo.common.CDOCommonSession.Options.PassiveUpdateMode;
import org.eclipse.emf.cdo.common.branch.CDOBranch;
import org.eclipse.emf.cdo.common.branch.CDOBranchHandler;
import org.eclipse.emf.cdo.common.branch.CDOBranchPoint;
import org.eclipse.emf.cdo.common.commit.CDOChangeSetData;
import org.eclipse.emf.cdo.common.commit.CDOCommitData;
import org.eclipse.emf.cdo.common.commit.CDOCommitInfoHandler;
import org.eclipse.emf.cdo.common.lob.CDOLob;
import org.eclipse.emf.cdo.common.lob.CDOLobInfo;
import org.eclipse.emf.cdo.common.model.CDOPackageUnit;
import org.eclipse.emf.cdo.common.protocol.CDOProtocolConstants;
import org.eclipse.emf.cdo.common.revision.CDORevisionHandler;
import org.eclipse.emf.cdo.common.revision.delta.CDORevisionDelta;
import org.eclipse.emf.cdo.common.util.TransportException;
import org.eclipse.emf.cdo.internal.net4j.bundle.OM;
import org.eclipse.emf.cdo.session.CDOSession;
import org.eclipse.emf.cdo.session.remote.CDORemoteSession;
import org.eclipse.emf.cdo.session.remote.CDORemoteSessionMessage;
import org.eclipse.emf.cdo.spi.common.CDORawReplicationContext;
import org.eclipse.emf.cdo.spi.common.CDOReplicationContext;
import org.eclipse.emf.cdo.spi.common.commit.CDORevisionAvailabilityInfo;
import org.eclipse.emf.cdo.spi.common.model.InternalCDOPackageUnit;
import org.eclipse.emf.cdo.spi.common.revision.InternalCDORevision;
import org.eclipse.emf.cdo.spi.common.revision.RevisionInfo;
import org.eclipse.emf.cdo.view.CDOView;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.spi.cdo.AbstractQueryIterator;
import org.eclipse.emf.spi.cdo.CDOSessionProtocol;
import org.eclipse.emf.spi.cdo.InternalCDOObject;
import org.eclipse.emf.spi.cdo.InternalCDORemoteSessionManager;
import org.eclipse.emf.spi.cdo.InternalCDOXATransaction.InternalCDOXACommitContext;
import org.eclipse.net4j.signal.RemoteException;
import org.eclipse.net4j.signal.RequestWithConfirmation;
import org.eclipse.net4j.signal.RequestWithMonitoring;
import org.eclipse.net4j.signal.SignalProtocol;
import org.eclipse.net4j.signal.SignalReactor;
import org.eclipse.net4j.util.WrappedException;
import org.eclipse.net4j.util.collection.Pair;
import org.eclipse.net4j.util.concurrent.IRWLockManager.LockType;
import org.eclipse.net4j.util.io.StringCompressor;
import org.eclipse.net4j.util.io.StringIO;
import org.eclipse.net4j.util.om.monitor.OMMonitor;
import org.eclipse.net4j.util.om.trace.PerfTracer;

/**
 * @author Eike Stepper
 */
public class CDOClientProtocol extends SignalProtocol<CDOSession> implements CDOSessionProtocol
{
  private static final PerfTracer REVISION_LOADING = new PerfTracer(OM.PERF_REVISION_LOADING, CDOClientProtocol.class);

  private StringIO packageURICompressor = StringCompressor.BYPASS ? StringIO.DIRECT : new StringCompressor(true);

  public CDOClientProtocol()
  {
    super(CDOProtocolConstants.PROTOCOL_NAME);
  }

  public CDOSession getSession()
  {
    return getInfraStructure();
  }

  public StringIO getPackageURICompressor()
  {
    return packageURICompressor;
  }

  public OpenSessionResult openSession(String repositoryName, boolean passiveUpdateEnabled,
      PassiveUpdateMode passiveUpdateMode)
  {
    return send(new OpenSessionRequest(this, repositoryName, passiveUpdateEnabled, passiveUpdateMode));
  }

  public void disablePassiveUpdate()
  {
    send(new DisablePassiveUpdateRequest(this));
  }

  public void setPassiveUpdateMode(PassiveUpdateMode mode)
  {
    send(new SetPassiveUpdateModeRequest(this, mode));
  }

  public RepositoryTimeResult getRepositoryTime()
  {
    return send(new RepositoryTimeRequest(this));
  }

  public EPackage[] loadPackages(CDOPackageUnit packageUnit)
  {
    return send(new LoadPackagesRequest(this, (InternalCDOPackageUnit)packageUnit));
  }

  public BranchInfo loadBranch(int branchID)
  {
    return send(new LoadBranchRequest(this, branchID));
  }

  public SubBranchInfo[] loadSubBranches(int branchID)
  {
    return send(new LoadSubBranchesRequest(this, branchID));
  }

  public int loadBranches(int startID, int endID, CDOBranchHandler handler)
  {
    return send(new LoadBranchesRequest(this, startID, endID, handler));
  }

  public void loadCommitInfos(CDOCommitInfoHandler handler)
  {
    send(new LoadCommitInfosRequest(this, handler));
  }

  public CDOCommitData loadCommitData()
  {
    return send(new LoadCommitDataRequest(this));
  }

  public Object loadChunk(InternalCDORevision revision, EStructuralFeature feature, int accessIndex, int fetchIndex,
      int fromIndex, int toIndex)
  {
    return send(new LoadChunkRequest(this, revision, feature, accessIndex, fetchIndex, fromIndex, toIndex));
  }

  public List<InternalCDORevision> loadRevisions(List<RevisionInfo> infos, int referenceChunk, int prefetchDepth)
  {
    return send(new LoadRevisionsRequest(this, infos, referenceChunk, prefetchDepth));
  }

  public InternalCDORevision loadRevision(long id,int referenceChunk)
  {
    return send(new LoadRevisionByVersionRequest(this, id,  referenceChunk));
  }

  public RefreshSessionResult refresh(Map<Long, InternalCDORevision> viewedRevisions, int initialChunkSize,
      boolean enablePassiveUpdates)
  {
    return send(new RefreshSessionRequest(this, viewedRevisions, initialChunkSize, enablePassiveUpdates));
  }

  public void openView(int viewID, boolean readOnly)
  {
    send(new OpenViewRequest(this, viewID, readOnly));
  }

  public CDOBranchPoint openView(int viewID, boolean readOnly, String durableLockingID)
  {
    return send(new OpenViewRequest(this, viewID, readOnly, durableLockingID));
  }

  public void switchTarget(int viewID, List<InternalCDOObject> invalidObjects,
      List<Long> allChangedObjects, List<Long> allDetachedObjects, OMMonitor monitor)
  {
    send(new SwitchTargetRequest(this, viewID,  invalidObjects, allChangedObjects, allDetachedObjects),
        monitor);
  }

  public void closeView(int viewID)
  {
    send(new CloseViewRequest(this, viewID));
  }

  public void changeSubscription(int viewID, List<Long> ids, boolean subscribeMode, boolean clear)
  {
    send(new ChangeSubscriptionRequest(this, viewID, ids, subscribeMode, clear));
  }

  public void query(CDOView view, AbstractQueryIterator<?> queryResult)
  {
    send(new QueryRequest(this, view, queryResult));
  }

  public boolean cancelQuery(int queryId)
  {
    try
    {
      return new QueryCancelRequest(this, queryId).send();
    }
    catch (Exception ignore)
    {
      return false;
    }
  }

  public LockObjectsResult lockObjects(List<InternalCDORevision> viewedRevisions, int viewID, LockType lockType, long timeout) throws InterruptedException
  {
    InterruptedException interruptedException = null;
    RuntimeException runtimeException = null;

    try
    {
      return new LockObjectsRequest(this, viewedRevisions, viewID, lockType, timeout).send();
    }
    catch (RemoteException ex)
    {
      if (ex.getCause() instanceof RuntimeException)
      {
        runtimeException = (RuntimeException)ex.getCause();
      }
      else if (ex.getCause() instanceof InterruptedException)
      {
        interruptedException = (InterruptedException)ex.getCause();
      }
      else
      {
        runtimeException = WrappedException.wrap(ex);
      }
    }
    catch (Exception ex)
    {
      throw WrappedException.wrap(ex);
    }

    if (interruptedException != null)
    {
      throw interruptedException;
    }

    throw runtimeException;
  }

  public void unlockObjects(CDOView view, Collection<? extends CDOObject> objects, LockType lockType)
  {
    send(new UnlockObjectsRequest(this, view, objects, lockType));
  }

  public boolean isObjectLocked(CDOView view, CDOObject object, LockType lockType, boolean byOthers)
  {
    return send(new ObjectLockedRequest(this, view, object, lockType, byOthers));
  }

  public String changeLockArea(CDOView view, boolean create)
  {
    return send(new LockAreaRequest(this, view, create));
  }

  public List<byte[]> queryLobs(Set<byte[]> ids)
  {
    return send(new QueryLobsRequest(this, ids));
  }

  public void loadLob(CDOLobInfo info, Object outputStreamOrWriter) throws IOException
  {
    try
    {
      new LoadLobRequest(this, info, outputStreamOrWriter).send();
    }
    catch (RuntimeException ex)
    {
      throw ex;
    }
    catch (IOException ex)
    {
      throw ex;
    }
    catch (Exception ex)
    {
      throw new TransportException(ex);
    }
  }

  public void handleRevisions(EClass eClass, CDOBranch branch, boolean exactBranch, long timeStamp, boolean exactTime,
      CDORevisionHandler handler)
  {
    send(new HandleRevisionsRequest(this, eClass, exactBranch, timeStamp, exactTime, handler));
  }

  public CommitTransactionResult commitTransaction(int transactionID, String comment, boolean releaseLocks, CDOCommitData commitData, Collection<CDOLob<?>> lobs, OMMonitor monitor)
  {
    return send(new CommitTransactionRequest(this, transactionID, comment, releaseLocks, commitData, lobs),
        monitor);
  }

  public CommitTransactionResult commitDelegation(String userID, String comment,
      CDOCommitData commitData, Map<Long, EClass> detachedObjectTypes, Collection<CDOLob<?>> lobs, OMMonitor monitor)
  {
    return send(new CommitDelegationRequest(this, userID, comment, commitData, detachedObjectTypes, lobs),
        monitor);
  }

  public CommitTransactionResult commitXATransactionPhase1(InternalCDOXACommitContext xaContext, OMMonitor monitor)
  {
    return send(new CommitXATransactionPhase1Request(this, xaContext), monitor);
  }

  public CommitTransactionResult commitXATransactionPhase2(InternalCDOXACommitContext xaContext, OMMonitor monitor)
  {
    return send(new CommitXATransactionPhase2Request(this, xaContext), monitor);
  }

  public CommitTransactionResult commitXATransactionPhase3(InternalCDOXACommitContext xaContext, OMMonitor monitor)
  {
    return send(new CommitXATransactionPhase3Request(this, xaContext), monitor);
  }

  public CommitTransactionResult commitXATransactionCancel(InternalCDOXACommitContext xaContext, OMMonitor monitor)
  {
    return send(new CommitXATransactionCancelRequest(this, xaContext), monitor);
  }

  public List<CDORemoteSession> getRemoteSessions(InternalCDORemoteSessionManager manager, boolean subscribe)
  {
    return send(new GetRemoteSessionsRequest(this, subscribe));
  }

  public Set<Integer> sendRemoteMessage(CDORemoteSessionMessage message, List<CDORemoteSession> recipients)
  {
    return send(new RemoteMessageRequest(this, message, recipients));
  }

  public boolean unsubscribeRemoteSessions()
  {
    return send(new UnsubscribeRemoteSessionsRequest(this));
  }

  public CDOChangeSetData[] loadChangeSets()
  {
    return send(new LoadChangeSetsRequest(this));
  }

  public Set<Long> loadMergeData(CDORevisionAvailabilityInfo targetInfo, CDORevisionAvailabilityInfo sourceInfo,
      CDORevisionAvailabilityInfo targetBaseInfo, CDORevisionAvailabilityInfo sourceBaseInfo)
  {
    return send(new LoadMergeDataRequest(this, targetInfo, sourceInfo, targetBaseInfo, sourceBaseInfo));
  }

  @Override
  protected SignalReactor createSignalReactor(short signalID)
  {
    switch (signalID)
    {
    case CDOProtocolConstants.SIGNAL_AUTHENTICATION:
      return new AuthenticationIndication(this);

    case CDOProtocolConstants.SIGNAL_BRANCH_NOTIFICATION:
      return new BranchNotificationIndication(this);

    case CDOProtocolConstants.SIGNAL_REPOSITORY_TYPE_NOTIFICATION:
      return new RepositoryTypeNotificationIndication(this);

    case CDOProtocolConstants.SIGNAL_REPOSITORY_STATE_NOTIFICATION:
      return new RepositoryStateNotificationIndication(this);

    case CDOProtocolConstants.SIGNAL_COMMIT_NOTIFICATION:
      return new CommitNotificationIndication(this);

    case CDOProtocolConstants.SIGNAL_REMOTE_SESSION_NOTIFICATION:
      return new RemoteSessionNotificationIndication(this);

    case CDOProtocolConstants.SIGNAL_REMOTE_MESSAGE_NOTIFICATION:
      return new RemoteMessageNotificationIndication(this);

    default:
      return super.createSignalReactor(signalID);
    }
  }

  private <RESULT> RESULT send(RequestWithConfirmation<RESULT> request)
  {
    try
    {
      return request.send();
    }
    catch (RuntimeException ex)
    {
      throw ex;
    }
    catch (Exception ex)
    {
      throw new TransportException(ex);
    }
  }

  private <RESULT> RESULT send(RequestWithMonitoring<RESULT> request, OMMonitor monitor)
  {
    try
    {
      return request.send(monitor);
    }
    catch (RuntimeException ex)
    {
      throw ex;
    }
    catch (Exception ex)
    {
      throw new TransportException(ex);
    }
  }

  private List<InternalCDORevision> send(LoadRevisionsRequest request)
  {
    try
    {
      REVISION_LOADING.start(request);
      return send((RequestWithConfirmation<List<InternalCDORevision>>)request);
    }
    finally
    {
      REVISION_LOADING.stop(request);
    }
  }



}
