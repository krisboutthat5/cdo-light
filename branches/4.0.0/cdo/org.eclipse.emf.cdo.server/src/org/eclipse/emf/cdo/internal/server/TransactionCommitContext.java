/**
 * Copyright (c) 2004 - 2011 Eike Stepper (Berlin, Germany) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Simon McDuff - initial API and implementation
 *    Eike Stepper - maintenance
 *    Martin Fluegge - maintenance, bug 318518
 */
package org.eclipse.emf.cdo.internal.server;

import org.eclipse.emf.cdo.common.branch.CDOBranch;
import org.eclipse.emf.cdo.common.branch.CDOBranchPoint;
import org.eclipse.emf.cdo.common.commit.CDOCommitData;
import org.eclipse.emf.cdo.common.commit.CDOCommitInfo;
import org.eclipse.emf.cdo.common.id.CDOID;
import org.eclipse.emf.cdo.common.id.CDOIDObject;
import org.eclipse.emf.cdo.common.id.CDOIDReference;
import org.eclipse.emf.cdo.common.id.CDOIDUtil;
import org.eclipse.emf.cdo.common.model.CDOModelUtil;
import org.eclipse.emf.cdo.common.model.CDOPackageUnit;
import org.eclipse.emf.cdo.common.revision.CDOIDAndBranch;
import org.eclipse.emf.cdo.common.revision.CDOIDAndVersion;
import org.eclipse.emf.cdo.common.revision.CDORevision;
import org.eclipse.emf.cdo.common.revision.CDORevisionKey;
import org.eclipse.emf.cdo.common.revision.CDORevisionUtil;
import org.eclipse.emf.cdo.common.revision.delta.CDOAddFeatureDelta;
import org.eclipse.emf.cdo.common.revision.delta.CDOContainerFeatureDelta;
import org.eclipse.emf.cdo.common.revision.delta.CDOFeatureDelta;
import org.eclipse.emf.cdo.common.revision.delta.CDOFeatureDeltaVisitor;
import org.eclipse.emf.cdo.common.revision.delta.CDORevisionDelta;
import org.eclipse.emf.cdo.common.revision.delta.CDOSetFeatureDelta;
import org.eclipse.emf.cdo.common.util.CDOCommonUtil;
import org.eclipse.emf.cdo.common.util.CDOQueryInfo;
import org.eclipse.emf.cdo.internal.common.commit.CDOCommitDataImpl;
import org.eclipse.emf.cdo.internal.common.commit.FailureCommitInfo;
import org.eclipse.emf.cdo.internal.common.model.CDOPackageRegistryImpl;
import org.eclipse.emf.cdo.internal.server.bundle.OM;
import org.eclipse.emf.cdo.server.ContainmentCycleDetectedException;
import org.eclipse.emf.cdo.server.IStoreAccessor;
import org.eclipse.emf.cdo.server.IStoreAccessor.QueryXRefsContext;
import org.eclipse.emf.cdo.server.StoreThreadLocal;
import org.eclipse.emf.cdo.spi.common.commit.InternalCDOCommitInfoManager;
import org.eclipse.emf.cdo.spi.common.model.InternalCDOPackageInfo;
import org.eclipse.emf.cdo.spi.common.model.InternalCDOPackageRegistry;
import org.eclipse.emf.cdo.spi.common.model.InternalCDOPackageUnit;
import org.eclipse.emf.cdo.spi.common.revision.CDOFeatureDeltaVisitorImpl;
import org.eclipse.emf.cdo.spi.common.revision.CDOIDMapper;
import org.eclipse.emf.cdo.spi.common.revision.CDOReferenceAdjuster;
import org.eclipse.emf.cdo.spi.common.revision.DetachedCDORevision;
import org.eclipse.emf.cdo.spi.common.revision.InternalCDORevision;
import org.eclipse.emf.cdo.spi.common.revision.InternalCDORevisionDelta;
import org.eclipse.emf.cdo.spi.common.revision.InternalCDORevisionManager;
import org.eclipse.emf.cdo.spi.common.revision.StubCDORevision;
import org.eclipse.emf.cdo.spi.server.InternalCommitContext;
import org.eclipse.emf.cdo.spi.server.InternalLockManager;
import org.eclipse.emf.cdo.spi.server.InternalRepository;
import org.eclipse.emf.cdo.spi.server.InternalSession;
import org.eclipse.emf.cdo.spi.server.InternalTransaction;

import org.eclipse.net4j.util.CheckUtil;
import org.eclipse.net4j.util.ObjectUtil;
import org.eclipse.net4j.util.StringUtil;
import org.eclipse.net4j.util.collection.IndexedList;
import org.eclipse.net4j.util.concurrent.IRWLockManager.LockType;
import org.eclipse.net4j.util.io.ExtendedDataInputStream;
import org.eclipse.net4j.util.om.monitor.Monitor;
import org.eclipse.net4j.util.om.monitor.OMMonitor;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author Simon McDuff
 * @since 2.0
 */
public class TransactionCommitContext implements InternalCommitContext
{
  private static final InternalCDORevision DETACHED = new StubCDORevision(null);

  private InternalTransaction transaction;

  private InternalRepository repository;

  private InternalCDORevisionManager revisionManager;

  private InternalLockManager lockManager;

  private TransactionPackageRegistry packageRegistry;

  private IStoreAccessor accessor;

  private long timeStamp = CDORevision.UNSPECIFIED_DATE;

  private long previousTimeStamp = CDORevision.UNSPECIFIED_DATE;

  private String commitComment;

  private InternalCDOPackageUnit[] newPackageUnits = new InternalCDOPackageUnit[0];

  private InternalCDORevision[] newObjects = new InternalCDORevision[0];

  private InternalCDORevisionDelta[] dirtyObjectDeltas = new InternalCDORevisionDelta[0];

  private CDOID[] detachedObjects = new CDOID[0];

  private Map<CDOID, EClass> detachedObjectTypes;

  private InternalCDORevision[] dirtyObjects = new InternalCDORevision[0];

  private InternalCDORevision[] cachedDetachedRevisions = new InternalCDORevision[0];

  private Map<CDOID, InternalCDORevision> cachedRevisions;

  private Set<Object> lockedObjects = new HashSet<Object>();

  private List<CDOID> lockedTargets;

  private ConcurrentMap<CDOID, CDOID> idMappings = new ConcurrentHashMap<CDOID, CDOID>();

  private CDOReferenceAdjuster idMapper = new CDOIDMapper(idMappings);

  private String rollbackMessage;

  private List<CDOIDReference> xRefs;

  private boolean ensuringReferentialIntegrity;

  private boolean autoReleaseLocksEnabled;

  private ExtendedDataInputStream lobs;

  public TransactionCommitContext(InternalTransaction transaction)
  {
    this.transaction = transaction;

    repository = transaction.getRepository();
    revisionManager = repository.getRevisionManager();
    lockManager = repository.getLockManager();
    ensuringReferentialIntegrity = repository.isEnsuringReferentialIntegrity();

    packageRegistry = new TransactionPackageRegistry(repository.getPackageRegistry(false));
    packageRegistry.activate();
  }

  public InternalTransaction getTransaction()
  {
    return transaction;
  }

  public CDOBranchPoint getBranchPoint()
  {
    return transaction.getBranch().getPoint(timeStamp);
  }

  public String getUserID()
  {
    return transaction.getSession().getUserID();
  }

  public String getCommitComment()
  {
    return commitComment;
  }

  public boolean isAutoReleaseLocksEnabled()
  {
    return autoReleaseLocksEnabled;
  }

  public String getRollbackMessage()
  {
    return rollbackMessage;
  }

  public List<CDOIDReference> getXRefs()
  {
    return xRefs;
  }

  public InternalCDOPackageRegistry getPackageRegistry()
  {
    return packageRegistry;
  }

  public InternalCDOPackageUnit[] getNewPackageUnits()
  {
    return newPackageUnits;
  }

  public InternalCDORevision[] getNewObjects()
  {
    return newObjects;
  }

  public InternalCDORevision[] getDirtyObjects()
  {
    return dirtyObjects;
  }

  public CDOID[] getDetachedObjects()
  {
    return detachedObjects;
  }

  public Map<CDOID, EClass> getDetachedObjectTypes()
  {
    return detachedObjectTypes;
  }

  public InternalCDORevision[] getDetachedRevisions()
  {
    // TODO This array can contain null values as they only come from the cache
    for (InternalCDORevision cachedDetachedRevision : cachedDetachedRevisions)
    {
      if (cachedDetachedRevision == null)
      {
        throw new AssertionError("Detached revisions are incomplete");
      }
    }

    return cachedDetachedRevisions;
  }

  public InternalCDORevisionDelta[] getDirtyObjectDeltas()
  {
    return dirtyObjectDeltas;
  }

  public CDORevision getRevision(CDOID id)
  {
    if (cachedRevisions == null)
    {
      cachedRevisions = cacheRevisions();
    }

    // Try "after state"
    InternalCDORevision revision = cachedRevisions.get(id);
    if (revision == DETACHED)
    {
      return null;
    }

    if (revision != null)
    {
      return revision;
    }

    // Fall back to "before state"
    return transaction.getRevision(id);
  }

  private Map<CDOID, InternalCDORevision> cacheRevisions()
  {
    Map<CDOID, InternalCDORevision> cache = new HashMap<CDOID, InternalCDORevision>();
    if (newObjects != null)
    {
      for (int i = 0; i < newObjects.length; i++)
      {
        InternalCDORevision revision = newObjects[i];
        cache.put(revision.getID(), revision);
      }
    }

    if (dirtyObjects != null)
    {
      for (int i = 0; i < dirtyObjects.length; i++)
      {
        InternalCDORevision revision = dirtyObjects[i];
        cache.put(revision.getID(), revision);
      }
    }

    if (detachedObjects != null)
    {
      for (int i = 0; i < detachedObjects.length; i++)
      {
        cache.put(detachedObjects[i], DETACHED);
      }
    }

    return cache;
  }

  public Map<CDOID, CDOID> getIDMappings()
  {
    return Collections.unmodifiableMap(idMappings);
  }

  public void addIDMapping(CDOID oldID, CDOID newID)
  {
    if (CDOIDUtil.isNull(newID) || newID.isTemporary())
    {
      throw new IllegalStateException("newID=" + newID); //$NON-NLS-1$
    }

    CDOID previousMapping = idMappings.putIfAbsent(oldID, newID);
    if (previousMapping != null)
    {
      throw new IllegalStateException("previousMapping != null"); //$NON-NLS-1$
    }
  }

  public void applyIDMappings(OMMonitor monitor)
  {
    try
    {
      monitor.begin(newObjects.length + dirtyObjects.length + dirtyObjectDeltas.length);
      applyIDMappings(newObjects, monitor.fork(newObjects.length));
      applyIDMappings(dirtyObjects, monitor.fork(dirtyObjects.length));
      for (CDORevisionDelta dirtyObjectDelta : dirtyObjectDeltas)
      {
        ((InternalCDORevisionDelta)dirtyObjectDelta).adjustReferences(idMapper);
        monitor.worked();
      }
    }
    finally
    {
      monitor.done();
    }
  }

  public void preWrite()
  {
    // Allocate a store writer
    accessor = repository.getStore().getWriter(transaction);

    // Make the store writer available in a ThreadLocal variable
    StoreThreadLocal.setAccessor(accessor);
    StoreThreadLocal.setCommitContext(this);
  }

  public void setNewPackageUnits(InternalCDOPackageUnit[] newPackageUnits)
  {
    this.newPackageUnits = newPackageUnits;
  }

  public void setNewObjects(InternalCDORevision[] newObjects)
  {
    this.newObjects = newObjects;
  }

  public void setDirtyObjectDeltas(InternalCDORevisionDelta[] dirtyObjectDeltas)
  {
    this.dirtyObjectDeltas = dirtyObjectDeltas;
  }

  public void setDetachedObjects(CDOID[] detachedObjects)
  {
    this.detachedObjects = detachedObjects;
  }

  public void setDetachedObjectTypes(Map<CDOID, EClass> detachedObjectTypes)
  {
    this.detachedObjectTypes = detachedObjectTypes;
  }

  public void setAutoReleaseLocksEnabled(boolean on)
  {
    autoReleaseLocksEnabled = on;
  }

  public void setCommitComment(String commitComment)
  {
    this.commitComment = commitComment;
  }

  public ExtendedDataInputStream getLobs()
  {
    return lobs;
  }

  public void setLobs(ExtendedDataInputStream in)
  {
    lobs = in;
  }

  /**
   * @since 2.0
   */
  public void write(OMMonitor monitor)
  {
    try
    {
      monitor.begin(107);
      dirtyObjects = new InternalCDORevision[dirtyObjectDeltas.length];

      lockObjects(); // Can take long and must come before setTimeStamp()
      monitor.worked();

      setTimeStamp(monitor.fork());

      adjustForCommit();
      monitor.worked();

      computeDirtyObjects(monitor.fork());

      checkXRefs();
      monitor.worked();
      if (rollbackMessage != null)
      {
        return;
      }

      detachObjects(monitor.fork());
      repository.notifyWriteAccessHandlers(transaction, this, true, monitor.fork());
      accessor.write(this, monitor.fork(100));
    }
    catch (Throwable t)
    {
      handleException(t);
    }
    finally
    {
      finishMonitor(monitor);
    }
  }

  public void commit(OMMonitor monitor)
  {
    try
    {
      monitor.begin(101);
      accessor.commit(monitor.fork(100));
      updateInfraStructure(monitor.fork());

      // Bugzilla 297940
      repository.endCommit(timeStamp);
    }
    catch (Throwable ex)
    {
      handleException(ex);
    }
    finally
    {
      finishMonitor(monitor);
    }
  }

  private void handleException(Throwable ex)
  {
    try
    {
      OM.LOG.error(ex);
      String storeClass = repository.getStore().getClass().getSimpleName();
      rollback("Rollback in " + storeClass + ": " + StringUtil.formatException(ex)); //$NON-NLS-1$ //$NON-NLS-2$
    }
    catch (Exception ex1)
    {
      if (rollbackMessage == null)
      {
        rollbackMessage = ex1.getMessage();
      }

      try
      {
        OM.LOG.error(ex1);
      }
      catch (Exception ignore)
      {
      }
    }
  }

  private void finishMonitor(OMMonitor monitor)
  {
    try
    {
      monitor.done();
    }
    catch (Exception ex)
    {
      try
      {
        OM.LOG.warn(ex);
      }
      catch (Exception ignore)
      {
      }
    }
  }

  private void setTimeStamp(OMMonitor mmonitor)
  {
    long[] times = createTimeStamp(mmonitor); // Could throw an exception
    timeStamp = times[0];
    previousTimeStamp = times[1];
    CheckUtil.checkState(timeStamp != CDOBranchPoint.UNSPECIFIED_DATE, "Commit timestamp must not be 0");
  }

  protected long[] createTimeStamp(OMMonitor monitor)
  {
    return repository.createCommitTimeStamp(monitor);
  }

  protected long getTimeStamp()
  {
    return timeStamp;
  }

  protected void setTimeStamp(long timeStamp)
  {
    repository.forceCommitTimeStamp(timeStamp, new Monitor());
    this.timeStamp = timeStamp;
  }

  public long getPreviousTimeStamp()
  {
    return previousTimeStamp;
  }

  public void postCommit(boolean success)
  {
    try
    {
      InternalSession sender = transaction.getSession();
      CDOCommitInfo commitInfo = success ? createCommitInfo() : createFailureCommitInfo();

      repository.sendCommitNotification(sender, commitInfo);
    }
    catch (Exception ex)
    {
      OM.LOG.warn("A problem occured while notifying other sessions", ex);
    }
    finally
    {
      StoreThreadLocal.release();
      accessor = null;
      lockedTargets = null;

      if (packageRegistry != null)
      {
        packageRegistry.deactivate();
        packageRegistry = null;
      }
    }
  }

  public CDOCommitInfo createCommitInfo()
  {
    CDOBranch branch = transaction.getBranch();
    String userID = transaction.getSession().getUserID();
    CDOCommitData commitData = createCommitData();

    InternalCDOCommitInfoManager commitInfoManager = repository.getCommitInfoManager();
    return commitInfoManager.createCommitInfo(branch, timeStamp, previousTimeStamp, userID, commitComment, commitData);
  }

  public CDOCommitInfo createFailureCommitInfo()
  {
    return new FailureCommitInfo(timeStamp, previousTimeStamp);
  }

  private CDOCommitData createCommitData()
  {
    List<CDOPackageUnit> newPackageUnitsCollection = new IndexedList.ArrayBacked<CDOPackageUnit>()
    {
      @Override
      protected CDOPackageUnit[] getArray()
      {
        return newPackageUnits;
      }
    };

    List<CDOIDAndVersion> newObjectsCollection = new IndexedList.ArrayBacked<CDOIDAndVersion>()
    {
      @Override
      protected CDOIDAndVersion[] getArray()
      {
        return newObjects;
      }
    };

    List<CDORevisionKey> changedObjectsCollection = new IndexedList.ArrayBacked<CDORevisionKey>()
    {
      @Override
      protected CDORevisionKey[] getArray()
      {
        return dirtyObjectDeltas;
      }
    };

    List<CDOIDAndVersion> detachedObjectsCollection = new IndexedList<CDOIDAndVersion>()
    {
      @Override
      public CDOIDAndVersion get(int i)
      {
        if (cachedDetachedRevisions[i] != null)
        {
          return cachedDetachedRevisions[i];
        }

        return CDOIDUtil.createIDAndVersion(detachedObjects[i], CDORevision.UNSPECIFIED_VERSION);
      }

      @Override
      public int size()
      {
        return detachedObjects.length;
      }
    };

    return new CDOCommitDataImpl(newPackageUnitsCollection, newObjectsCollection, changedObjectsCollection,
        detachedObjectsCollection);
  }

  protected void adjustForCommit()
  {
    for (InternalCDOPackageUnit newPackageUnit : newPackageUnits)
    {
      newPackageUnit.setTimeStamp(timeStamp);
    }

    CDOBranch branch = transaction.getBranch();
    for (InternalCDORevision newObject : newObjects)
    {
      newObject.adjustForCommit(branch, timeStamp);
    }
  }

  protected void lockObjects() throws InterruptedException
  {
    lockedObjects.clear();
    lockedTargets = null;

    try
    {

      final boolean supportingBranches = repository.isSupportingBranches();

      CDOFeatureDeltaVisitor deltaTargetLocker = null;
      if (ensuringReferentialIntegrity)
      {
        final Set<CDOID> newIDs = new HashSet<CDOID>();
        for (int i = 0; i < newObjects.length; i++)
        {
          InternalCDORevision newRevision = newObjects[i];
          CDOID newID = newRevision.getID();
          if (newID instanceof CDOIDObject)
          {
            // After merges newObjects may contain non-TEMP ids
            newIDs.add(newID);
          }
        }

        deltaTargetLocker = new CDOFeatureDeltaVisitorImpl()
        {
          @Override
          public void visit(CDOAddFeatureDelta delta)
          {
            lockTarget(delta.getValue(), newIDs, supportingBranches);
          }

          @Override
          public void visit(CDOSetFeatureDelta delta)
          {
            lockTarget(delta.getValue(), newIDs, supportingBranches);
          }
        };

        CDOReferenceAdjuster revisionTargetLocker = new CDOReferenceAdjuster()
        {
          public Object adjustReference(Object value, EStructuralFeature feature, int index)
          {
            lockTarget(value, newIDs, supportingBranches);
            return value;
          }
        };

        for (int i = 0; i < newObjects.length; i++)
        {
          InternalCDORevision newRevision = newObjects[i];
          newRevision.adjustReferences(revisionTargetLocker);
        }
      }

      for (int i = 0; i < dirtyObjectDeltas.length; i++)
      {
        InternalCDORevisionDelta delta = dirtyObjectDeltas[i];
        CDOID id = delta.getID();
        Object key = lockManager.getLockKey(id, transaction.getBranch());
        lockedObjects.add(new DeltaLockWrapper(key, delta));

        if (hasContainmentChanges(delta))
        {
          if (isContainerLocked(delta))
          {
            throw new ContainmentCycleDetectedException("Parent (" + key
                + ") is already locked for containment changes");
          }
        }
      }

      for (int i = 0; i < dirtyObjectDeltas.length; i++)
      {
        InternalCDORevisionDelta delta = dirtyObjectDeltas[i];
        if (deltaTargetLocker != null)
        {
          delta.accept(deltaTargetLocker);
        }
      }

      for (int i = 0; i < detachedObjects.length; i++)
      {
        CDOID id = detachedObjects[i];
        Object key = lockManager.getLockKey(id, transaction.getBranch());
        lockedObjects.add(key);
      }

      if (!lockedObjects.isEmpty())
      {
        // First lock all objects (incl. possible ref targets).
        // This is a transient operation, it does not check for existance!
        lockManager.lock(LockType.WRITE, transaction, lockedObjects, 1000);

        // If all locks could be acquired, check if locked targets do still exist
        if (lockedTargets != null)
        {
          for (CDOID id : lockedTargets)
          {
            InternalCDORevision revision = //
            revisionManager.getRevision(id, transaction, CDORevision.UNCHUNKED, CDORevision.DEPTH_NONE, true);

            if (revision == null || revision instanceof DetachedCDORevision)
            {
              throw new IllegalStateException("Object " + id
                  + " can not be referenced anymore because it has been detached");
            }
          }
        }
      }
    }
    catch (RuntimeException ex)
    {
      lockedObjects.clear();
      lockedTargets = null;
      throw ex;
    }
  }

  /**
   * Iterates up the eContainers of an object and returns <code>true</code> on the first parent locked by another view.
   * 
   * @return <code>true</code> if any parent is locked, <code>false</code> otherwise.
   */
  private boolean isContainerLocked(InternalCDORevisionDelta delta)
  {
    CDOID id = delta.getID();
    InternalCDORevision revision = revisionManager.getRevisionByVersion(id, delta, CDORevision.UNCHUNKED, true);
    if (revision == null)
    {
      // Can happen with non-auditing cache
      throw new ConcurrentModificationException("Attempt by " + transaction + " to modify historical revision: "
          + CDORevisionUtil.copyRevisionKey(delta));
    }

    return isContainerLocked(revision);
  }

  private boolean isContainerLocked(InternalCDORevision revision)
  {
    CDOID id = (CDOID)revision.getContainerID();
    if (CDOIDUtil.isNull(id))
    {
      return false;
    }

    Object key = lockManager.getLockKey(id, transaction.getBranch());
    DeltaLockWrapper lockWrapper = new DeltaLockWrapper(key, null);

    if (lockManager.hasLockByOthers(LockType.WRITE, transaction, lockWrapper))
    {
      Object object = lockManager.getLockEntryObject(lockWrapper);
      if (object instanceof DeltaLockWrapper)
      {
        InternalCDORevisionDelta delta = ((DeltaLockWrapper)object).getDelta();
        if (delta != null && hasContainmentChanges(delta))
        {
          return true;
        }
      }
    }

    InternalCDORevision parent = revisionManager.getRevision(id, transaction, CDORevision.UNCHUNKED,
        CDORevision.DEPTH_NONE, true);

    if (parent != null)
    {
      return isContainerLocked(parent);
    }

    return false;
  }

  private boolean hasContainmentChanges(InternalCDORevisionDelta delta)
  {
    for (CDOFeatureDelta featureDelta : delta.getFeatureDeltas())
    {
      EStructuralFeature feature = featureDelta.getFeature();
      if (feature instanceof EReference)
      {
        if (((EReference)feature).isContainment())
        {
          return true;
        }
      }
    }

    return false;
  }

  private void lockTarget(Object value, Set<CDOID> newIDs, boolean supportingBranches)
  {
    if (value instanceof CDOIDObject)
    {
      CDOIDObject id = (CDOIDObject)value;
      if (id.isNull())
      {
        return;
      }

      if (newIDs.contains(id))
      {
        // After merges newObjects may contain non-TEMP ids
        return;
      }

      if (detachedObjectTypes != null && detachedObjectTypes.containsKey(id))
      {
        throw new IllegalStateException("This commit deletes object " + id + " and adds a reference at the same time");
      }

      // Let this object be locked
      Object key = lockManager.getLockKey(id, transaction.getBranch());
      lockedObjects.add(key);

      // Let this object be checked for existance after it has been locked
      if (lockedTargets == null)
      {
        lockedTargets = new ArrayList<CDOID>();
      }

      lockedTargets.add(id);
    }
  }

  protected void checkXRefs()
  {
    if (ensuringReferentialIntegrity && detachedObjectTypes != null)
    {
      XRefContext context = new XRefContext();
      xRefs = context.getXRefs(accessor);
      if (!xRefs.isEmpty())
      {
        rollbackMessage = "Referential integrity violated";
      }
    }
  }

  private synchronized void unlockObjects()
  {
    if (!lockedObjects.isEmpty())
    {
      lockManager.unlock(LockType.WRITE, transaction, lockedObjects);
      lockedObjects.clear();
    }
  }

  private void computeDirtyObjects(OMMonitor monitor)
  {
    try
    {
      monitor.begin(dirtyObjectDeltas.length);
      for (int i = 0; i < dirtyObjectDeltas.length; i++)
      {
        dirtyObjects[i] = computeDirtyObject(dirtyObjectDeltas[i]);
        if (dirtyObjects[i] == null)
        {
          throw new IllegalStateException("Can not retrieve origin revision for " + dirtyObjectDeltas[i]); //$NON-NLS-1$
        }

        monitor.worked();
      }
    }
    finally
    {
      monitor.done();
    }
  }

  private InternalCDORevision computeDirtyObject(InternalCDORevisionDelta delta)
  {
    CDOID id = delta.getID();

    InternalCDORevision oldRevision = revisionManager.getRevisionByVersion(id, delta, CDORevision.UNCHUNKED, true);
    if (oldRevision == null)
    {
      throw new IllegalStateException("Origin revision not found for " + delta);
    }

    CDOBranch branch = transaction.getBranch();
    if (ObjectUtil.equals(oldRevision.getBranch(), branch) && oldRevision.isHistorical())
    {
      throw new ConcurrentModificationException("Attempt by " + transaction + " to modify historical revision: "
          + oldRevision);
    }

    // Make sure all chunks are loaded
    for (EStructuralFeature feature : CDOModelUtil.getAllPersistentFeatures(oldRevision.getEClass()))
    {
      if (feature.isMany())
      {
        repository.ensureChunk(oldRevision, feature, 0, oldRevision.getList(feature).size());
      }
    }

    InternalCDORevision newRevision = oldRevision.copy();
    newRevision.adjustForCommit(branch, timeStamp);

    delta.apply(newRevision);
    return newRevision;
  }

  private void applyIDMappings(InternalCDORevision[] revisions, OMMonitor monitor)
  {
    try
    {
      monitor.begin(revisions.length);
      for (InternalCDORevision revision : revisions)
      {
        if (revision != null)
        {
          CDOID newID = idMappings.get(revision.getID());
          if (newID != null)
          {
            revision.setID(newID);
          }

          revision.adjustReferences(idMapper);
          monitor.worked();
        }
      }
    }
    finally
    {
      monitor.done();
    }
  }

  public synchronized void rollback(String message)
  {
    // Check if we already rolled back
    if (rollbackMessage == null)
    {
      rollbackMessage = message;
      unlockObjects();
      if (accessor != null)
      {
        try
        {
          accessor.rollback();
        }
        catch (RuntimeException ex)
        {
          OM.LOG.warn("Problem while rolling back  the transaction", ex); //$NON-NLS-1$
        }
        finally
        {
          repository.failCommit(timeStamp);
        }
      }
    }
  }

  protected IStoreAccessor getAccessor()
  {
    return accessor;
  }

  private void updateInfraStructure(OMMonitor monitor)
  {
    try
    {
      monitor.begin(7);
      addNewPackageUnits(monitor.fork());
      addRevisions(newObjects, monitor.fork());
      addRevisions(dirtyObjects, monitor.fork());
      reviseDetachedObjects(monitor.fork());

      unlockObjects();
      monitor.worked();

      if (isAutoReleaseLocksEnabled())
      {
        repository.getLockManager().unlock(transaction);
      }

      monitor.worked();
      repository.notifyWriteAccessHandlers(transaction, this, false, monitor.fork());
    }
    finally
    {
      monitor.done();
    }
  }

  private void addNewPackageUnits(OMMonitor monitor)
  {
    InternalCDOPackageRegistry repositoryPackageRegistry = repository.getPackageRegistry(false);
    synchronized (repositoryPackageRegistry)
    {
      try
      {
        monitor.begin(newPackageUnits.length);
        for (int i = 0; i < newPackageUnits.length; i++)
        {
          newPackageUnits[i].setState(CDOPackageUnit.State.LOADED);
          repositoryPackageRegistry.putPackageUnit(newPackageUnits[i]);
          monitor.worked();
        }
      }
      finally
      {
        monitor.done();
      }
    }
  }

  private void addRevisions(CDORevision[] revisions, OMMonitor monitor)
  {
    try
    {
      monitor.begin(revisions.length);
      for (CDORevision revision : revisions)
      {
        if (revision != null)
        {
          revisionManager.addRevision(revision);
        }

        monitor.worked();
      }
    }
    finally
    {
      monitor.done();
    }
  }

  private void reviseDetachedObjects(OMMonitor monitor)
  {
    try
    {
      monitor.begin(cachedDetachedRevisions.length);
      long revised = getBranchPoint().getTimeStamp() - 1;
      for (InternalCDORevision revision : cachedDetachedRevisions)
      {
        if (revision != null)
        {
          revision.setRevised(revised);
        }

        monitor.worked();
      }
    }
    finally
    {
      monitor.done();
    }
  }

  private void detachObjects(OMMonitor monitor)
  {
    int size = detachedObjects.length;
    cachedDetachedRevisions = new InternalCDORevision[size];

    CDOID[] detachedObjects = getDetachedObjects();

    try
    {
      monitor.begin(size);
      for (int i = 0; i < size; i++)
      {
        CDOID id = detachedObjects[i];

        // Remember the cached revision that must be revised after successful commit through updateInfraStructure
        cachedDetachedRevisions[i] = (InternalCDORevision)revisionManager.getCache().getRevision(id, transaction);
        monitor.worked();
      }
    }
    finally
    {
      monitor.done();
    }
  }

  @Override
  public String toString()
  {
    return MessageFormat.format("TransactionCommitContext[{0}, {1}, {2}]", transaction.getSession(), transaction, //$NON-NLS-1$
        CDOCommonUtil.formatTimeStamp(timeStamp));
  }

  /**
   * @author Eike Stepper
   */
  public static final class TransactionPackageRegistry extends CDOPackageRegistryImpl
  {
    private static final long serialVersionUID = 1L;

    public TransactionPackageRegistry(InternalCDOPackageRegistry repositoryPackageRegistry)
    {
      delegateRegistry = repositoryPackageRegistry;
      setPackageLoader(repositoryPackageRegistry.getPackageLoader());
    }

    @Override
    public synchronized void putPackageUnit(InternalCDOPackageUnit packageUnit)
    {
      packageUnit.setPackageRegistry(this);
      for (InternalCDOPackageInfo packageInfo : packageUnit.getPackageInfos())
      {
        EPackage ePackage = packageInfo.getEPackage();
        basicPut(ePackage.getNsURI(), ePackage);
      }

      resetInternalCaches();
    }

    @Override
    protected void disposePackageUnits()
    {
      // Do nothing
    }

    @Override
    public Collection<Object> values()
    {
      throw new UnsupportedOperationException();
    }
  }

  /**
   * @author Martin Fluegge
   */
  private static final class DeltaLockWrapper implements CDOIDAndBranch
  {
    private Object key;

    private InternalCDORevisionDelta delta;

    public DeltaLockWrapper(Object key, InternalCDORevisionDelta delta)
    {
      this.key = key;
      this.delta = delta;
    }

    public Object getKey()
    {
      return key;
    }

    public InternalCDORevisionDelta getDelta()
    {
      return delta;
    }

    public CDOID getID()
    {
      return key instanceof CDOIDAndBranch ? ((CDOIDAndBranch)key).getID() : (CDOID)key;
    }

    public CDOBranch getBranch()
    {
      return key instanceof CDOIDAndBranch ? ((CDOIDAndBranch)key).getBranch() : null;
    }

    @Override
    public boolean equals(Object obj)
    {
      if (obj instanceof DeltaLockWrapper)
      {
        DeltaLockWrapper wrapper = (DeltaLockWrapper)obj;
        return key.equals(wrapper.getKey());
      }

      return key.equals(obj);
    }

    @Override
    public int hashCode()
    {
      return key.hashCode();
    }

    @Override
    public String toString()
    {
      return key.toString();
    }
  }

  /**
   * @author Eike Stepper
   */
  private final class XRefContext implements QueryXRefsContext
  {
    private Map<EClass, List<EReference>> sourceCandidates = new HashMap<EClass, List<EReference>>();

    private Set<CDOID> detachedIDs = new HashSet<CDOID>();

    private Set<CDOID> dirtyIDs = new HashSet<CDOID>();

    private List<CDOIDReference> result = new ArrayList<CDOIDReference>();

    public XRefContext()
    {
      XRefsQueryHandler.collectSourceCandidates(transaction, detachedObjectTypes.values(), sourceCandidates);

      for (CDOID id : detachedObjects)
      {
        detachedIDs.add(id);
      }

      for (InternalCDORevision revision : dirtyObjects)
      {
        dirtyIDs.add(revision.getID());
      }
    }

    public List<CDOIDReference> getXRefs(IStoreAccessor accessor)
    {
      accessor.queryXRefs(this);
      checkDirtyObjects();
      return result;
    }

    private void checkDirtyObjects()
    {
      final CDOID[] dirtyID = { null };
      CDOReferenceAdjuster dirtyObjectChecker = new CDOReferenceAdjuster()
      {
        public Object adjustReference(Object targetID, EStructuralFeature feature, int index)
        {
          if (feature != CDOContainerFeatureDelta.CONTAINER_FEATURE)
          {
            if (detachedIDs.contains(targetID))
            {
              result.add(new CDOIDReference((CDOID)targetID, dirtyID[0], feature, index));
            }

          }

          return targetID;
        }
      };

      for (InternalCDORevision dirtyObject : dirtyObjects)
      {
        dirtyID[0] = dirtyObject.getID();
        dirtyObject.adjustReferences(dirtyObjectChecker);
      }
    }

    public long getTimeStamp()
    {
      return CDOBranchPoint.UNSPECIFIED_DATE;
    }

    public CDOBranch getBranch()
    {
      return transaction.getBranch();
    }

    public Map<CDOID, EClass> getTargetObjects()
    {
      return detachedObjectTypes;
    }

    public EReference[] getSourceReferences()
    {
      return new EReference[0];
    }

    public Map<EClass, List<EReference>> getSourceCandidates()
    {
      return sourceCandidates;
    }

    public int getMaxResults()
    {
      return CDOQueryInfo.UNLIMITED_RESULTS;
    }

    public boolean addXRef(CDOID targetID, CDOID sourceID, EReference sourceReference, int sourceIndex)
    {
      if (CDOIDUtil.isNull(targetID))
      {
        // Compensate potential issues with the XRef implementation in the store accessor.
        return true;
      }

      if (detachedIDs.contains(sourceID))
      {
        // Ignore XRefs from objects that are about to be detached themselves by this commit.
        return true;
      }

      if (dirtyIDs.contains(sourceID))
      {
        // Ignore XRefs from objects that are about to be modified by this commit. They're handled later in getXRefs().
        return true;
      }

      result.add(new CDOIDReference(targetID, sourceID, sourceReference, sourceIndex));
      return true;
    }
  }
}
