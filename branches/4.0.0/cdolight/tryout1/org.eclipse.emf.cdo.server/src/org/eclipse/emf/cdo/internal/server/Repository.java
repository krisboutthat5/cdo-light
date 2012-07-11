/**
 * Copyright (c) 2004 - 2011 Eike Stepper (Berlin, Germany) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Eike Stepper - initial API and implementation
 *    Simon McDuff - bug 201266
 *    Simon McDuff - bug 233273
 *    Simon McDuff - bug 233490
 *    Stefan Winkler - changed order of determining audit and revision delta support.
 *    Andre Dietisheim - bug 256649
 */
package org.eclipse.emf.cdo.internal.server;

import java.io.IOException;
import java.io.OutputStream;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.eclipse.emf.cdo.common.branch.CDOBranch;
import org.eclipse.emf.cdo.common.branch.CDOBranchHandler;
import org.eclipse.emf.cdo.common.branch.CDOBranchPoint;
import org.eclipse.emf.cdo.common.commit.CDOChangeSetData;
import org.eclipse.emf.cdo.common.commit.CDOCommitData;
import org.eclipse.emf.cdo.common.commit.CDOCommitInfo;
import org.eclipse.emf.cdo.common.commit.CDOCommitInfoHandler;
import org.eclipse.emf.cdo.common.lob.CDOLobHandler;
import org.eclipse.emf.cdo.common.model.CDOModelUtil;
import org.eclipse.emf.cdo.common.model.CDOPackageUnit;
import org.eclipse.emf.cdo.common.model.EMFUtil;
import org.eclipse.emf.cdo.common.protocol.CDODataOutput;
import org.eclipse.emf.cdo.common.protocol.CDOProtocolConstants;
import org.eclipse.emf.cdo.common.revision.CDORevision;
import org.eclipse.emf.cdo.common.revision.CDORevisionFactory;
import org.eclipse.emf.cdo.common.revision.CDORevisionHandler;
import org.eclipse.emf.cdo.common.revision.CDORevisionUtil;
import org.eclipse.emf.cdo.common.util.CDOQueryInfo;
import org.eclipse.emf.cdo.common.util.RepositoryStateChangedEvent;
import org.eclipse.emf.cdo.common.util.RepositoryTypeChangedEvent;
import org.eclipse.emf.cdo.eresource.EresourcePackage;
import org.eclipse.emf.cdo.etypes.EtypesPackage;
import org.eclipse.emf.cdo.internal.common.model.CDOPackageRegistryImpl;
import org.eclipse.emf.cdo.internal.server.bundle.OM;
import org.eclipse.emf.cdo.server.IQueryHandler;
import org.eclipse.emf.cdo.server.IQueryHandlerProvider;
import org.eclipse.emf.cdo.server.IStore;
import org.eclipse.emf.cdo.server.IStoreAccessor;
import org.eclipse.emf.cdo.server.IStoreChunkReader;
import org.eclipse.emf.cdo.server.IStoreChunkReader.Chunk;
import org.eclipse.emf.cdo.server.ITransaction;
import org.eclipse.emf.cdo.server.StoreThreadLocal;
import org.eclipse.emf.cdo.spi.common.CDOReplicationContext;
import org.eclipse.emf.cdo.spi.common.CDOReplicationInfo;
import org.eclipse.emf.cdo.spi.common.branch.CDOBranchUtil;
import org.eclipse.emf.cdo.spi.common.branch.InternalCDOBranchManager;
import org.eclipse.emf.cdo.spi.common.commit.CDOChangeSetSegment;
import org.eclipse.emf.cdo.spi.common.commit.CDOCommitInfoUtil;
import org.eclipse.emf.cdo.spi.common.commit.CDORevisionAvailabilityInfo;
import org.eclipse.emf.cdo.spi.common.commit.InternalCDOCommitInfoManager;
import org.eclipse.emf.cdo.spi.common.model.InternalCDOPackageInfo;
import org.eclipse.emf.cdo.spi.common.model.InternalCDOPackageRegistry;
import org.eclipse.emf.cdo.spi.common.model.InternalCDOPackageUnit;
import org.eclipse.emf.cdo.spi.common.revision.DetachedCDORevision;
import org.eclipse.emf.cdo.spi.common.revision.InternalCDOList;
import org.eclipse.emf.cdo.spi.common.revision.InternalCDORevision;
import org.eclipse.emf.cdo.spi.common.revision.InternalCDORevisionManager;
import org.eclipse.emf.cdo.spi.common.revision.PointerCDORevision;
import org.eclipse.emf.cdo.spi.common.revision.RevisionInfo;
import org.eclipse.emf.cdo.spi.server.ContainerQueryHandlerProvider;
import org.eclipse.emf.cdo.spi.server.InternalCommitContext;
import org.eclipse.emf.cdo.spi.server.InternalCommitManager;
import org.eclipse.emf.cdo.spi.server.InternalLockManager;
import org.eclipse.emf.cdo.spi.server.InternalQueryManager;
import org.eclipse.emf.cdo.spi.server.InternalRepository;
import org.eclipse.emf.cdo.spi.server.InternalSession;
import org.eclipse.emf.cdo.spi.server.InternalSessionManager;
import org.eclipse.emf.cdo.spi.server.InternalStore;
import org.eclipse.emf.cdo.spi.server.InternalTransaction;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.internal.cdo.object.CDOFactoryImpl;
import org.eclipse.net4j.util.ReflectUtil.ExcludeFromDump;
import org.eclipse.net4j.util.StringUtil;
import org.eclipse.net4j.util.collection.MoveableList;
import org.eclipse.net4j.util.collection.Pair;
import org.eclipse.net4j.util.container.Container;
import org.eclipse.net4j.util.container.IPluginContainer;
import org.eclipse.net4j.util.lifecycle.LifecycleUtil;
import org.eclipse.net4j.util.om.monitor.Monitor;
import org.eclipse.net4j.util.om.monitor.OMMonitor;
import org.eclipse.net4j.util.transaction.TransactionException;

/**
 * @author Eike Stepper
 * @since 2.0
 */
public class Repository extends Container<Object> implements InternalRepository
{
  private String name;

  private String uuid;

  private InternalStore store;

  private Type type = Type.MASTER;

  private State state = State.ONLINE;

  private Map<String, String> properties;

  private boolean supportingEcore;

  private boolean ensuringReferentialIntegrity;

  private InternalCDOPackageRegistry packageRegistry;

  private InternalCDOBranchManager branchManager;

  private InternalCDORevisionManager revisionManager;

  private InternalCDOCommitInfoManager commitInfoManager;

  private InternalSessionManager sessionManager;

  private InternalQueryManager queryManager;

  private InternalCommitManager commitManager;

  private InternalLockManager lockManager;

  private IQueryHandlerProvider queryHandlerProvider;

  private List<ReadAccessHandler> readAccessHandlers = new ArrayList<ReadAccessHandler>();

  private List<WriteAccessHandler> writeAccessHandlers = new ArrayList<WriteAccessHandler>();

  private List<CDOCommitInfoHandler> commitInfoHandlers = new ArrayList<CDOCommitInfoHandler>();

  private EPackage[] initialPackages;

  @ExcludeFromDump
  private transient Object createBranchLock = new Object();

  private boolean skipInitialization;

  private long rootResourceID;

  public Repository()
  {
  }

  public String getName()
  {
    return name;
  }

  public void setName(String name)
  {
    this.name = name;
  }

  public String getUUID()
  {
    if (uuid == null)
    {
      uuid = getProperties().get(Props.OVERRIDE_UUID);
      if (uuid == null)
      {
        uuid = UUID.randomUUID().toString();
      }
      else if (uuid.length() == 0)
      {
        uuid = getName();
      }
    }

    return uuid;
  }

  public InternalStore getStore()
  {
    return store;
  }

  public void setStore(InternalStore store)
  {
    this.store = store;
  }

  public Type getType()
  {
    return type;
  }

  public void setType(Type type)
  {
    checkArg(type, "type"); //$NON-NLS-1$
    if (this.type != type)
    {
      changingType(this.type, type);
    }
  }

  protected void changingType(Type oldType, Type newType)
  {
    type = newType;
    fireEvent(new RepositoryTypeChangedEvent(this, oldType, newType));

    if (sessionManager != null)
    {
      sessionManager.sendRepositoryTypeNotification(oldType, newType);
    }
  }

  public State getState()
  {
    return state;
  }

  public void setState(State state)
  {
    checkArg(state, "state"); //$NON-NLS-1$
    if (this.state != state)
    {
      changingState(this.state, state);
    }
  }

  protected void changingState(State oldState, State newState)
  {
    state = newState;
    fireEvent(new RepositoryStateChangedEvent(this, oldState, newState));

    if (sessionManager != null)
    {
      sessionManager.sendRepositoryStateNotification(oldState, newState);
    }
  }

  public synchronized Map<String, String> getProperties()
  {
    if (properties == null)
    {
      properties = new HashMap<String, String>();
    }

    return properties;
  }

  public synchronized void setProperties(Map<String, String> properties)
  {
    this.properties = properties;
  }

  public boolean isSupportingEcore()
  {
    return supportingEcore;
  }

  public boolean isEnsuringReferentialIntegrity()
  {
    return ensuringReferentialIntegrity;
  }

  public String getStoreType()
  {
    return store.getType();
  }


  public long getRootResourceID()
  {
    return rootResourceID;
  }

  public void setRootResourceID(long rootResourceID)
  {
    this.rootResourceID = rootResourceID;
  }

  public Object processPackage(Object value)
  {
    CDOFactoryImpl.prepareDynamicEPackage(value);
    return value;
  }

  public EPackage[] loadPackages(CDOPackageUnit packageUnit)
  {
    IStoreAccessor accessor = StoreThreadLocal.getAccessor();
    return accessor.loadPackageUnit((InternalCDOPackageUnit)packageUnit);
  }

  public BranchInfo loadBranch(int branchID)
  {
    IStoreAccessor accessor = StoreThreadLocal.getAccessor();
    return accessor.loadBranch(branchID);
  }

  public SubBranchInfo[] loadSubBranches(int branchID)
  {
    IStoreAccessor accessor = StoreThreadLocal.getAccessor();
    return accessor.loadSubBranches(branchID);
  }

  public int loadBranches(int startID, int endID, CDOBranchHandler branchHandler)
  {
    IStoreAccessor accessor = StoreThreadLocal.getAccessor();
    return accessor.loadBranches(startID, endID, branchHandler);
  }

  public void loadCommitInfos(CDOCommitInfoHandler handler)
  {
    IStoreAccessor accessor = StoreThreadLocal.getAccessor();
    accessor.loadCommitInfos(handler);
  }

  public CDOCommitData loadCommitData()
  {
    IStoreAccessor accessor = StoreThreadLocal.getAccessor();
    return accessor.loadCommitData();
  }

  public List<InternalCDORevision> loadRevisions(List<RevisionInfo> infos,  int referenceChunk, int prefetchDepth)
  {
    for (RevisionInfo info : infos)
    {
      long id = info.getID();
      RevisionInfo.Type type = info.getType();
      switch (type)
      {
      case AVAILABLE_NORMAL: // direct == false
      {
        RevisionInfo.Available.Normal availableInfo = (RevisionInfo.Available.Normal)info;
        break;
      }

      case AVAILABLE_POINTER: // direct == false || target == null
      {
        RevisionInfo.Available.Pointer pointerInfo = (RevisionInfo.Available.Pointer)info;
        boolean needsTarget = !pointerInfo.hasTarget();

        if (needsTarget)
        {
          InternalCDORevision target = loadRevision(id, referenceChunk);
          PointerCDORevision pointer = new PointerCDORevision(target.getEClass(), id, target.getID());

          info.setResult(target);
          info.setSynthetic(pointer);
          continue;
        }

        break;
      }

      case AVAILABLE_DETACHED: // direct == false
      {
        RevisionInfo.Available.Detached detachedInfo = (RevisionInfo.Available.Detached)info;
        break;
      }

      case MISSING:
      {
        break;
      }

      default:
        throw new IllegalStateException("Invalid revision info type: " + type);
      }

      IStoreAccessor accessor = StoreThreadLocal.getAccessor();
      InternalCDORevision revision = accessor.readRevision(id,  referenceChunk, revisionManager);
      if (revision == null)
      {
          DetachedCDORevision detachedRevision = new DetachedCDORevision(EcorePackage.Literals.ECLASS, id);
          info.setSynthetic(detachedRevision);
      }
      else if (revision instanceof DetachedCDORevision)
      {
        DetachedCDORevision detached = (DetachedCDORevision)revision;
        info.setSynthetic(detached);
      }
      else
      {
        revision.freeze();
        info.setResult(revision);
      }
    }

    return null;
  }

  private InternalCDORevision loadRevisionTarget(long id, int referenceChunk,
      IStoreAccessor accessor)
  {

      InternalCDORevision revision = accessor.readRevision(id, referenceChunk, revisionManager);
      if (revision != null)
      {
        revision.freeze();
        return revision;
      }

    return null;
  }

  private long loadRevisionRevised(long id)
  {
    InternalCDORevision revision = loadRevision(id, CDORevision.UNCHUNKED);
    if (revision != null)
    {
      return - 1;
    }

    return 0;
  }

  public InternalCDORevision loadRevision(long id,int referenceChunk)
  {
    IStoreAccessor accessor = StoreThreadLocal.getAccessor();
    return accessor.readRevision(id, referenceChunk, revisionManager);
  }

  protected void ensureChunks(InternalCDORevision revision, int referenceChunk, IStoreAccessor accessor)
  {
    EClass eClass = revision.getEClass();
    EStructuralFeature[] features = CDOModelUtil.getAllPersistentFeatures(eClass);
    for (int i = 0; i < features.length; i++)
    {
      EStructuralFeature feature = features[i];
      if (feature.isMany())
      {
        MoveableList<Object> list = revision.getList(feature);
        int chunkEnd = Math.min(referenceChunk, list.size());
        accessor = ensureChunk(revision, feature, accessor, list, 0, chunkEnd);
      }
    }
  }

  public IStoreAccessor ensureChunk(InternalCDORevision revision, EStructuralFeature feature, int chunkStart,
      int chunkEnd)
  {
    MoveableList<Object> list = revision.getList(feature);
    chunkEnd = Math.min(chunkEnd, list.size());
    return ensureChunk(revision, feature, StoreThreadLocal.getAccessor(), list, chunkStart, chunkEnd);
  }

  protected IStoreAccessor ensureChunk(InternalCDORevision revision, EStructuralFeature feature,
      IStoreAccessor accessor, MoveableList<Object> list, int chunkStart, int chunkEnd)
  {
    IStoreChunkReader chunkReader = null;
    int fromIndex = -1;
    for (int j = chunkStart; j < chunkEnd; j++)
    {
      if (list.get(j) == InternalCDOList.UNINITIALIZED)
      {
        if (fromIndex == -1)
        {
          fromIndex = j;
        }
      }
      else
      {
        if (fromIndex != -1)
        {
          if (chunkReader == null)
          {
            if (accessor == null)
            {
              accessor = StoreThreadLocal.getAccessor();
            }

            chunkReader = accessor.createChunkReader(revision, feature);
          }

          int toIndex = j;
          if (fromIndex == toIndex - 1)
          {
            chunkReader.addSimpleChunk(fromIndex);
          }
          else
          {
            chunkReader.addRangedChunk(fromIndex, toIndex);
          }

          fromIndex = -1;
        }
      }
    }

    // Add last chunk
    if (fromIndex != -1)
    {
      if (chunkReader == null)
      {
        if (accessor == null)
        {
          accessor = StoreThreadLocal.getAccessor();
        }

        chunkReader = accessor.createChunkReader(revision, feature);
      }

      int toIndex = chunkEnd;
      if (fromIndex == toIndex - 1)
      {
        chunkReader.addSimpleChunk(fromIndex);
      }
      else
      {
        chunkReader.addRangedChunk(fromIndex, toIndex);
      }
    }

    if (chunkReader != null)
    {
      InternalCDOList cdoList = list instanceof InternalCDOList ? (InternalCDOList)list : null;

      List<Chunk> chunks = chunkReader.executeRead();
      for (Chunk chunk : chunks)
      {
        int startIndex = chunk.getStartIndex();
        for (int indexInChunk = 0; indexInChunk < chunk.size(); indexInChunk++)
        {
          Object id = chunk.get(indexInChunk);
          if (cdoList != null)
          {
            cdoList.setWithoutFrozenCheck(startIndex + indexInChunk, id);
          }
          else
          {
            list.set(startIndex + indexInChunk, id);
          }
        }
      }
    }

    return accessor;
  }

  public InternalCDOPackageRegistry getPackageRegistry(boolean considerCommitContext)
  {
    if (considerCommitContext)
    {
      IStoreAccessor.CommitContext commitContext = StoreThreadLocal.getCommitContext();
      if (commitContext != null)
      {
        InternalCDOPackageRegistry contextualPackageRegistry = commitContext.getPackageRegistry();
        if (contextualPackageRegistry != null)
        {
          return contextualPackageRegistry;
        }
      }
    }

    return packageRegistry;
  }

  public InternalCDOPackageRegistry getPackageRegistry()
  {
    return getPackageRegistry(true);
  }

  public void setPackageRegistry(InternalCDOPackageRegistry packageRegistry)
  {
    checkInactive();
    this.packageRegistry = packageRegistry;
  }

  public InternalSessionManager getSessionManager()
  {
    return sessionManager;
  }

  /**
   * @since 2.0
   */
  public void setSessionManager(InternalSessionManager sessionManager)
  {
    checkInactive();
    this.sessionManager = sessionManager;
  }

  public InternalCDOBranchManager getBranchManager()
  {
    return branchManager;
  }

  public void setBranchManager(InternalCDOBranchManager branchManager)
  {
    checkInactive();
    this.branchManager = branchManager;
  }

  public InternalCDOCommitInfoManager getCommitInfoManager()
  {
    return commitInfoManager;
  }

  public void setCommitInfoManager(InternalCDOCommitInfoManager commitInfoManager)
  {
    checkInactive();
    this.commitInfoManager = commitInfoManager;
  }

  public InternalCDORevisionManager getRevisionManager()
  {
    return revisionManager;
  }

  /**
   * @since 2.0
   */
  public void setRevisionManager(InternalCDORevisionManager revisionManager)
  {
    checkInactive();
    this.revisionManager = revisionManager;
  }

  /**
   * @since 2.0
   */
  public InternalQueryManager getQueryManager()
  {
    return queryManager;
  }

  /**
   * @since 2.0
   */
  public void setQueryManager(InternalQueryManager queryManager)
  {
    checkInactive();
    this.queryManager = queryManager;
  }

  /**
   * @since 2.0
   */
  public InternalCommitManager getCommitManager()
  {
    return commitManager;
  }

  /**
   * @since 2.0
   */
  public void setCommitManager(InternalCommitManager commitManager)
  {
    checkInactive();
    this.commitManager = commitManager;
  }

  /**
   * @since 2.0
   */
  public InternalLockManager getLockManager()
  {
    return lockManager;
  }

  /**
   * @since 2.0
   */
  public void setLockManager(InternalLockManager lockManager)
  {
    checkInactive();
    this.lockManager = lockManager;
  }

  public InternalCommitContext createCommitContext(InternalTransaction transaction)
  {
    return new TransactionCommitContext(transaction);
  }

  public void endCommit()
  {
  }

  public void failCommit()
  {
  }

  /**
   * @since 4.0
   */
  public void addCommitInfoHandler(CDOCommitInfoHandler handler)
  {
    synchronized (commitInfoHandlers)
    {
      if (!commitInfoHandlers.contains(handler))
      {
        commitInfoHandlers.add(handler);
      }
    }
  }

  /**
   * @since 4.0
   */
  public void removeCommitInfoHandler(CDOCommitInfoHandler handler)
  {
    synchronized (commitInfoHandlers)
    {
      commitInfoHandlers.remove(handler);
    }
  }

  public void sendCommitNotification(InternalSession sender, CDOCommitInfo commitInfo)
  {
    sessionManager.sendCommitNotification(sender, commitInfo);

    CDOCommitInfoHandler[] handlers;
    synchronized (commitInfoHandlers)
    {
      handlers = commitInfoHandlers.toArray(new CDOCommitInfoHandler[commitInfoHandlers.size()]);
    }

    for (CDOCommitInfoHandler handler : handlers)
    {
      try
      {
        handler.handleCommitInfo(commitInfo);
      }
      catch (Exception ex)
      {
        OM.LOG.error(ex);
      }
    }
  }

  /**
   * @since 2.0
   */
  public IQueryHandlerProvider getQueryHandlerProvider()
  {
    return queryHandlerProvider;
  }

  /**
   * @since 2.0
   */
  public void setQueryHandlerProvider(IQueryHandlerProvider queryHandlerProvider)
  {
    this.queryHandlerProvider = queryHandlerProvider;
  }

  /**
   * @since 2.0
   */
  public synchronized IQueryHandler getQueryHandler(CDOQueryInfo info)
  {
    String language = info.getQueryLanguage();
    if (CDOProtocolConstants.QUERY_LANGUAGE_RESOURCES.equals(language))
    {
      return new ResourcesQueryHandler();
    }

    if (CDOProtocolConstants.QUERY_LANGUAGE_XREFS.equals(language))
    {
      return new XRefsQueryHandler();
    }

    IStoreAccessor storeAccessor = StoreThreadLocal.getAccessor();
    if (storeAccessor != null)
    {
      IQueryHandler handler = storeAccessor.getQueryHandler(info);
      if (handler != null)
      {
        return handler;
      }
    }

    if (queryHandlerProvider == null)
    {
      queryHandlerProvider = new ContainerQueryHandlerProvider(IPluginContainer.INSTANCE);
    }

    IQueryHandler handler = queryHandlerProvider.getQueryHandler(info);
    if (handler != null)
    {
      return handler;
    }

    return null;
  }

  public Object[] getElements()
  {
    final Object[] elements = { packageRegistry, branchManager, revisionManager, sessionManager, queryManager,
        commitManager, commitInfoManager, lockManager, store };
    return elements;
  }

  @Override
  public boolean isEmpty()
  {
    return false;
  }

  /**
   * @since 2.0
   */
  public long getCreationTime()
  {
    return store.getCreationTime();
  }

  /**
   * @since 2.0
   */
  public void validateTimeStamp(long timeStamp) throws IllegalArgumentException
  {
    long creationTimeStamp = getCreationTime();
    if (timeStamp < creationTimeStamp)
    {
      throw new IllegalArgumentException(
          MessageFormat
              .format(
                  "timeStamp ({0}) < repository creation time ({1})", timeStamp, creationTimeStamp)); //$NON-NLS-1$
    }

    long currentTimeStamp = getTimeStamp();
    if (timeStamp > currentTimeStamp)
    {
      throw new IllegalArgumentException(
          MessageFormat
              .format(
                  "timeStamp ({0}) > current time ({1})", timeStamp, currentTimeStamp)); //$NON-NLS-1$
    }
  }

  public long getTimeStamp()
  {
    return System.currentTimeMillis();
  }

  /**
   * @since 2.0
   */
  public void addHandler(Handler handler)
  {
    if (handler instanceof ReadAccessHandler)
    {
      synchronized (readAccessHandlers)
      {
        if (!readAccessHandlers.contains(handler))
        {
          readAccessHandlers.add((ReadAccessHandler)handler);
        }
      }
    }

    if (handler instanceof WriteAccessHandler)
    {
      synchronized (writeAccessHandlers)
      {
        if (!writeAccessHandlers.contains(handler))
        {
          writeAccessHandlers.add((WriteAccessHandler)handler);
        }
      }
    }
  }

  /**
   * @since 2.0
   */
  public void removeHandler(Handler handler)
  {
    if (handler instanceof ReadAccessHandler)
    {
      synchronized (readAccessHandlers)
      {
        readAccessHandlers.remove(handler);
      }
    }

    if (handler instanceof WriteAccessHandler)
    {
      synchronized (writeAccessHandlers)
      {
        writeAccessHandlers.remove(handler);
      }
    }
  }

  /**
   * @since 2.0
   */
  public void notifyReadAccessHandlers(InternalSession session, CDORevision[] revisions,
      List<CDORevision> additionalRevisions)
  {
    ReadAccessHandler[] handlers;
    synchronized (readAccessHandlers)
    {
      int size = readAccessHandlers.size();
      if (size == 0)
      {
        return;
      }

      handlers = readAccessHandlers.toArray(new ReadAccessHandler[size]);
    }

    for (ReadAccessHandler handler : handlers)
    {
      // Do *not* protect against unchecked exceptions from handlers!
      handler.handleRevisionsBeforeSending(session, revisions, additionalRevisions);
    }
  }

  public void notifyWriteAccessHandlers(ITransaction transaction, IStoreAccessor.CommitContext commitContext,
      boolean beforeCommit, OMMonitor monitor)
  {
    WriteAccessHandler[] handlers;
    synchronized (writeAccessHandlers)
    {
      int size = writeAccessHandlers.size();
      if (size == 0)
      {
        return;
      }

      handlers = writeAccessHandlers.toArray(new WriteAccessHandler[size]);
    }

    try
    {
      monitor.begin(handlers.length);
      for (WriteAccessHandler handler : handlers)
      {
        try
        {
          if (beforeCommit)
          {
            handler.handleTransactionBeforeCommitting(transaction, commitContext, monitor.fork());
          }
          else
          {
            handler.handleTransactionAfterCommitted(transaction, commitContext, monitor.fork());
          }
        }
        catch (RuntimeException ex)
        {
          if (!beforeCommit)
          {
            OM.LOG.error(ex);
          }
          else
          {
            // Do *not* protect against unchecked exceptions from handlers on before case!
            throw ex;
          }
        }
      }
    }
    finally
    {
      monitor.done();
    }
  }

  public void setInitialPackages(EPackage... initialPackages)
  {
    checkInactive();
    this.initialPackages = initialPackages;
  }

  public CDOReplicationInfo replicateRaw(CDODataOutput out, int lastReplicatedBranchID, long lastReplicatedCommitTime)
      throws IOException
  {
    final int fromBranchID = lastReplicatedBranchID + 1;
    final int toBranchID = getStore().getLastBranchID();

    final long fromCommitTime = lastReplicatedCommitTime + 1L;
    final long toCommitTime = getStore().getLastCommitTime();

    out.writeInt(toBranchID);
    out.writeLong(toCommitTime);

    IStoreAccessor.Raw accessor = (IStoreAccessor.Raw)StoreThreadLocal.getAccessor();
    accessor.rawExport(out, fromBranchID, toBranchID, fromCommitTime, toCommitTime);

    return new CDOReplicationInfo()
    {
      public int getLastReplicatedBranchID()
      {
        return toBranchID;
      }

      public long getLastReplicatedCommitTime()
      {
        return toCommitTime;
      }
    };
  }

  public CDOChangeSetData getChangeSet(CDOBranchPoint startPoint, CDOBranchPoint endPoint)
  {
    CDOChangeSetSegment[] segments = CDOChangeSetSegment.createFrom(startPoint, endPoint);

    IStoreAccessor accessor = StoreThreadLocal.getAccessor();
    Set<Long> ids = accessor.readChangeSet(new Monitor(), segments);

    return CDORevisionUtil.createChangeSetData(ids, startPoint, endPoint, revisionManager);
  }

  public Set<Long> getMergeData(CDORevisionAvailabilityInfo targetInfo, CDORevisionAvailabilityInfo sourceInfo,
      CDORevisionAvailabilityInfo targetBaseInfo, CDORevisionAvailabilityInfo sourceBaseInfo, OMMonitor monitor)
  {
    monitor.begin(5);

    try
    {
      IStoreAccessor accessor = StoreThreadLocal.getAccessor();
      Set<Long> ids = new HashSet<Long>();

      if (targetBaseInfo == null && sourceBaseInfo == null)
      {
//        if (CDOBranchUtil.isContainedBy(source, target))
//        {
//          ids.addAll(accessor.readChangeSet(monitor.fork(), CDOChangeSetSegment.createFrom(source, target)));
//        }
//        else if (CDOBranchUtil.isContainedBy(target, source))
//        {
//          ids.addAll(accessor.readChangeSet(monitor.fork(), CDOChangeSetSegment.createFrom(target, source)));
//        }
//        else
//        {
//          CDOBranchPoint ancestor = CDOBranchUtil.getAncestor(target, source);
//          ids.addAll(accessor.readChangeSet(monitor.fork(), CDOChangeSetSegment.createFrom(ancestor, target)));
//          ids.addAll(accessor.readChangeSet(monitor.fork(), CDOChangeSetSegment.createFrom(ancestor, source)));
//        }
      }
      else
      {
        CDORevisionAvailabilityInfo sourceBaseInfoToUse = sourceBaseInfo == null ? targetBaseInfo : sourceBaseInfo;

//        ids.addAll(accessor.readChangeSet(monitor.fork(),
//            CDOChangeSetSegment.createFrom(targetBaseInfo.getBranchPoint(), target)));
//
//        ids.addAll(accessor.readChangeSet(monitor.fork(),
//            CDOChangeSetSegment.createFrom(sourceBaseInfoToUse.getBranchPoint(), source)));
      }

      loadMergeData(ids, targetInfo, monitor.fork());
      loadMergeData(ids, sourceInfo, monitor.fork());

      if (targetBaseInfo != null)
      {
        loadMergeData(ids, targetBaseInfo, monitor.fork());
      }

      if (sourceBaseInfo != null)
      {
        loadMergeData(ids, sourceBaseInfo, monitor.fork());
      }

      return ids;
    }
    finally
    {
      monitor.done();
    }
  }

  private void loadMergeData(Set<Long> ids, CDORevisionAvailabilityInfo info, OMMonitor monitor)
  {
    int size = ids.size();
    monitor.begin(size);

    try
    {
      for (Long id : ids)
      {
        if (info.containsRevision(id))
        {
          info.removeRevision(id);
        }
        else
        {
          InternalCDORevision revision = getRevisionFromBranch(id);
          if (revision != null)
          {
            info.addRevision(revision);
          }
          else
          {
            info.removeRevision(id);
          }
        }

        monitor.worked();
      }
    }
    finally
    {
      monitor.done();
    }
  }

  private InternalCDORevision getRevisionFromBranch(long id)
  {
    InternalCDORevision revision = revisionManager.getRevision(id, CDORevision.UNCHUNKED,
        CDORevision.DEPTH_NONE, true);
    // if (revision == null || !ObjectUtil.equals(revision.getBranch(), branchPoint.getBranch()))
    // {
    // return null;
    // }

    return revision;
  }

  public void queryLobs(List<byte[]> ids)
  {
    IStoreAccessor accessor = StoreThreadLocal.getAccessor();
    accessor.queryLobs(ids);
  }

  public void handleLobs(long fromTime, long toTime, CDOLobHandler handler) throws IOException
  {
    IStoreAccessor accessor = StoreThreadLocal.getAccessor();
    accessor.handleLobs(fromTime, toTime, handler);
  }

  public void loadLob(byte[] id, OutputStream out) throws IOException
  {
    IStoreAccessor accessor = StoreThreadLocal.getAccessor();
    accessor.loadLob(id, out);
  }

  public void handleRevisions(EClass eClass, final CDORevisionHandler handler)
  {
    CDORevisionHandler wrapper = handler;

      wrapper = new CDORevisionHandler()
      {
        private Set<Long> handled = new HashSet<Long>();

        public boolean handleRevision(CDORevision revision)
        {
          if (handled.add(revision.getID()))
          {
            return handler.handleRevision(revision);
          }

          return true;
        }
      };

    IStoreAccessor accessor = StoreThreadLocal.getAccessor();
    accessor.handleRevisions(eClass, wrapper);
  }

  @Override
  public String toString()
  {
    return MessageFormat.format("Repository[{0}]", name); //$NON-NLS-1$
  }

  public boolean isSkipInitialization()
  {
    return skipInitialization;
  }

  public void setSkipInitialization(boolean skipInitialization)
  {
    this.skipInitialization = skipInitialization;
  }

  protected void initProperties()
  {


    String valueEcore = properties.get(Props.SUPPORTING_ECORE);
    if (valueEcore != null)
    {
      supportingEcore = Boolean.valueOf(valueEcore);
    }

    String valueIntegrity = properties.get(Props.ENSURE_REFERENTIAL_INTEGRITY);
    if (valueIntegrity != null)
    {
      ensuringReferentialIntegrity = Boolean.valueOf(valueIntegrity);
    }
  }

  public void initSystemPackages()
  {
    IStoreAccessor writer = store.getWriter(null);
    StoreThreadLocal.setAccessor(writer);

    try
    {
      List<InternalCDOPackageUnit> units = new ArrayList<InternalCDOPackageUnit>();
      units.add(initSystemPackage(EcorePackage.eINSTANCE));
      units.add(initSystemPackage(EresourcePackage.eINSTANCE));
      units.add(initSystemPackage(EtypesPackage.eINSTANCE));

      if (initialPackages != null)
      {
        for (EPackage initialPackage : initialPackages)
        {
          if (!packageRegistry.containsKey(initialPackage.getNsURI()))
          {
            units.add(initSystemPackage(initialPackage));
          }
        }
      }

      InternalCDOPackageUnit[] systemUnits = units.toArray(new InternalCDOPackageUnit[units.size()]);
      writer.writePackageUnits(systemUnits, new Monitor());
      writer.commit(new Monitor());
    }
    finally
    {
      StoreThreadLocal.release();
    }
  }

  protected InternalCDOPackageUnit initSystemPackage(EPackage ePackage)
  {
    EMFUtil.registerPackage(ePackage, packageRegistry);
    InternalCDOPackageInfo packageInfo = packageRegistry.getPackageInfo(ePackage);

    InternalCDOPackageUnit packageUnit = packageInfo.getPackageUnit();
    packageUnit.setState(CDOPackageUnit.State.LOADED);
    return packageUnit;
  }

  public void initMainBranch(InternalCDOBranchManager branchManager, long timeStamp)
  {
    branchManager.initMainBranch(false, timeStamp);
  }

  protected void initRootResource()
  {

    CDORevisionFactory factory = getRevisionManager().getFactory();
    InternalCDORevision rootResource = (InternalCDORevision)factory
        .createRevision(EresourcePackage.Literals.CDO_RESOURCE);

    rootResource.setContainerID(0);
    rootResource.setContainingFeatureID(0);
    long tempID = 1;
    rootResource.setID(1);
    rootResource.setResourceID(1);

    InternalSession session = getSessionManager().openSession(null);
    InternalTransaction transaction = session.openTransaction(1);
    InternalCommitContext commitContext = new TransactionCommitContext(transaction)
    {

      @Override
      public String getUserID()
      {
        return SYSTEM_USER_ID;
      }

      @Override
      public String getCommitComment()
      {
        return "<initialize>"; //$NON-NLS-1$
      }
    };

    commitContext.setNewObjects(new InternalCDORevision[] { rootResource });
    commitContext.preWrite();

    commitContext.write(new Monitor());
    commitContext.commit(new Monitor());

    String rollbackMessage = commitContext.getRollbackMessage();
    if (rollbackMessage != null)
    {
      throw new TransactionException(rollbackMessage);
    }

    rootResourceID = tempID;

    commitContext.postCommit(true);
    session.close();
  }

  protected void readRootResource()
  {
    IStoreAccessor reader = store.getReader(null);
    StoreThreadLocal.setAccessor(reader);

    try
    {
      rootResourceID = reader.readResourceID(0L, null);
    }
    finally
    {
      StoreThreadLocal.release();
    }
  }

  protected void readPackageUnits()
  {
    IStoreAccessor reader = store.getReader(null);
    StoreThreadLocal.setAccessor(reader);

    try
    {
      Collection<InternalCDOPackageUnit> packageUnits = reader.readPackageUnits();
      for (InternalCDOPackageUnit packageUnit : packageUnits)
      {
        packageRegistry.putPackageUnit(packageUnit);
      }
    }
    finally
    {
      StoreThreadLocal.release();
    }
  }

  @Override
  protected void doBeforeActivate() throws Exception
  {
    super.doBeforeActivate();
    checkState(store, "store"); //$NON-NLS-1$
    checkState(!StringUtil.isEmpty(name), "name is empty"); //$NON-NLS-1$
    checkState(packageRegistry, "packageRegistry"); //$NON-NLS-1$
    checkState(sessionManager, "sessionManager"); //$NON-NLS-1$
    checkState(branchManager, "branchManager"); //$NON-NLS-1$
    checkState(revisionManager, "revisionManager"); //$NON-NLS-1$
    checkState(queryManager, "queryManager"); //$NON-NLS-1$
    checkState(commitInfoManager, "commitInfoManager"); //$NON-NLS-1$
    checkState(commitManager, "commitManager"); //$NON-NLS-1$
    checkState(lockManager, "lockingManager"); //$NON-NLS-1$

    packageRegistry.setReplacingDescriptors(true);
    packageRegistry.setPackageProcessor(this);
    packageRegistry.setPackageLoader(this);

    branchManager.setBranchLoader(this);
    branchManager.setTimeProvider(this);

    revisionManager.setRevisionLoader(this);
    sessionManager.setRepository(this);
    queryManager.setRepository(this);
    commitInfoManager.setCommitInfoLoader(this);
    commitManager.setRepository(this);
    lockManager.setRepository(this);
    store.setRepository(this);
  }

  @Override
  protected void doActivate() throws Exception
  {
    super.doActivate();

    initProperties();

    LifecycleUtil.activate(store);
    LifecycleUtil.activate(packageRegistry);
    LifecycleUtil.activate(sessionManager);
    LifecycleUtil.activate(revisionManager);
    LifecycleUtil.activate(branchManager);
    LifecycleUtil.activate(queryManager);
    LifecycleUtil.activate(commitInfoManager);
    LifecycleUtil.activate(commitManager);
    LifecycleUtil.activate(queryHandlerProvider);

    if (!skipInitialization)
    {
      long lastCommitTimeStamp = Math.max(store.getCreationTime(), store.getLastCommitTime());
      initMainBranch(branchManager, lastCommitTimeStamp);

      if (store.isFirstStart())
      {
        initSystemPackages();
        initRootResource();
      }
      else
      {
        readPackageUnits();
        readRootResource();
      }

      // This check does not work for CDOWorkspace:
      // if (CDOIDUtil.isNull(rootResourceID))
      // {
      // throw new IllegalStateException("Root resource ID is null");
      // }
    }

    LifecycleUtil.activate(lockManager); // Needs an initialized main branch / branch manager
  }

  @Override
  protected void doDeactivate() throws Exception
  {
    LifecycleUtil.deactivate(lockManager);
    LifecycleUtil.deactivate(queryHandlerProvider);
    LifecycleUtil.deactivate(commitManager);
    LifecycleUtil.deactivate(commitInfoManager);
    LifecycleUtil.deactivate(queryManager);
    LifecycleUtil.deactivate(revisionManager);
    LifecycleUtil.deactivate(sessionManager);
    LifecycleUtil.deactivate(store);
    LifecycleUtil.deactivate(branchManager);
    LifecycleUtil.deactivate(packageRegistry);
    super.doDeactivate();
  }

  /**
   * @author Eike Stepper
   * @since 2.0
   */
  public static class Default extends Repository
  {
    public Default()
    {
    }

    @Override
    protected void doBeforeActivate() throws Exception
    {
      if (getPackageRegistry(false) == null)
      {
        setPackageRegistry(createPackageRegistry());
      }

      if (getSessionManager() == null)
      {
        setSessionManager(createSessionManager());
      }

      if (getBranchManager() == null)
      {
        setBranchManager(createBranchManager());
      }

      if (getRevisionManager() == null)
      {
        setRevisionManager(createRevisionManager());
      }

      if (getQueryManager() == null)
      {
        setQueryManager(createQueryManager());
      }

      if (getCommitInfoManager() == null)
      {
        setCommitInfoManager(createCommitInfoManager());
      }

      if (getCommitManager() == null)
      {
        setCommitManager(createCommitManager());
      }

      if (getLockManager() == null)
      {
        setLockManager(createLockManager());
      }

      super.doBeforeActivate();
    }

    protected InternalCDOPackageRegistry createPackageRegistry()
    {
      return new CDOPackageRegistryImpl();
    }

    protected InternalSessionManager createSessionManager()
    {
      return new SessionManager();
    }

    protected InternalCDOBranchManager createBranchManager()
    {
      return CDOBranchUtil.createBranchManager();
    }

    protected InternalCDORevisionManager createRevisionManager()
    {
      return (InternalCDORevisionManager)CDORevisionUtil.createRevisionManager();
    }

    protected InternalQueryManager createQueryManager()
    {
      return new QueryManager();
    }

    protected InternalCDOCommitInfoManager createCommitInfoManager()
    {
      return CDOCommitInfoUtil.createCommitInfoManager();
    }

    protected InternalCommitManager createCommitManager()
    {
      return new CommitManager();
    }

    protected InternalLockManager createLockManager()
    {
      return new LockManager();
    }
  }
}
