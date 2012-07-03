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
 */
package org.eclipse.emf.cdo.internal.server;

import org.eclipse.emf.cdo.common.branch.CDOBranch;
import org.eclipse.emf.cdo.common.branch.CDOBranchPoint;
import org.eclipse.emf.cdo.common.id.CDOID;
import org.eclipse.emf.cdo.common.id.CDOIDUtil;
import org.eclipse.emf.cdo.common.revision.CDOIDAndBranch;
import org.eclipse.emf.cdo.common.revision.CDORevision;
import org.eclipse.emf.cdo.server.IRepository;
import org.eclipse.emf.cdo.server.ISession;
import org.eclipse.emf.cdo.server.ISessionManager;
import org.eclipse.emf.cdo.server.IStoreAccessor;
import org.eclipse.emf.cdo.server.IStoreAccessor.DurableLocking;
import org.eclipse.emf.cdo.server.IView;
import org.eclipse.emf.cdo.server.StoreThreadLocal;
import org.eclipse.emf.cdo.spi.common.branch.CDOBranchUtil;
import org.eclipse.emf.cdo.spi.server.InternalLockManager;
import org.eclipse.emf.cdo.spi.server.InternalRepository;
import org.eclipse.emf.cdo.spi.server.InternalStore;
import org.eclipse.emf.cdo.spi.server.InternalView;

import org.eclipse.net4j.util.ImplementationError;
import org.eclipse.net4j.util.ReflectUtil.ExcludeFromDump;
import org.eclipse.net4j.util.WrappedException;
import org.eclipse.net4j.util.concurrent.RWLockManager;
import org.eclipse.net4j.util.container.ContainerEventAdapter;
import org.eclipse.net4j.util.container.IContainer;
import org.eclipse.net4j.util.event.IListener;
import org.eclipse.net4j.util.lifecycle.ILifecycle;
import org.eclipse.net4j.util.lifecycle.LifecycleEventAdapter;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * @author Simon McDuff
 * @since 3.0
 */
public class LockManager extends RWLockManager<Object, IView> implements InternalLockManager
{
  private InternalRepository repository;

  private Map<String, InternalView> openViews = new HashMap<String, InternalView>();

  @ExcludeFromDump
  private transient IListener sessionListener = new ContainerEventAdapter<IView>()
  {
    @Override
    protected void onRemoved(IContainer<IView> container, IView view)
    {
      String durableLockingID = view.getDurableLockingID();
      if (durableLockingID == null)
      {
        unlock(view);
      }
      else
      {
        changeContext(view, new DurableView(durableLockingID));
        unregisterOpenView(durableLockingID);
      }
    }
  };

  @ExcludeFromDump
  private transient IListener sessionManagerListener = new ContainerEventAdapter<ISession>()
  {
    @Override
    protected void onAdded(IContainer<ISession> container, ISession session)
    {
      session.addListener(sessionListener);
    }

    @Override
    protected void onRemoved(IContainer<ISession> container, ISession session)
    {
      session.removeListener(sessionListener);
    }
  };

  public LockManager()
  {
  }

  public InternalRepository getRepository()
  {
    return repository;
  }

  public void setRepository(InternalRepository repository)
  {
    this.repository = repository;
  }

  public Object getLockEntryObject(Object key)
  {
    LockEntry<Object, IView> lockEntry = getLockEntry(key);
    return lockEntry.getObject();
  }

  public Object getLockKey(CDOID id, CDOBranch branch)
  {
    if (repository.isSupportingBranches())
    {
      return CDOIDUtil.createIDAndBranch(id, branch);
    }

    return id;
  }

  public Map<CDOID, LockGrade> getLocks(final IView view)
  {
    final Map<CDOID, LockGrade> result = new HashMap<CDOID, LockGrade>();
    LockEntryHandler<Object, IView> handler = new LockEntryHandler<Object, IView>()
    {
      public boolean handleLockEntry(LockEntry<Object, IView> lockEntry)
      {
        CDOID id = getLockKeyID(lockEntry.getObject());
        LockGrade grade = LockGrade.NONE;
        if (lockEntry.isReadLock(view))
        {
          grade = grade.getUpdated(LockType.READ, true);
        }

        if (lockEntry.isWriteLock(view))
        {
          grade = grade.getUpdated(LockType.WRITE, true);
        }

        if (grade != LockGrade.NONE)
        {
          result.put(id, grade);
        }

        return true;
      }
    };

    handleLockEntries(view, handler);
    return result;
  }

  public void lock(boolean explicit, LockType type, IView view, Collection<? extends Object> objectsToLock, long timeout)
      throws InterruptedException
  {
    String durableLockingID = null;
    DurableLocking accessor = null;

    if (explicit)
    {
      durableLockingID = view.getDurableLockingID();
      if (durableLockingID != null)
      {
        accessor = getDurableLocking();
      }
    }

    super.lock(type, view, objectsToLock, timeout);

    if (accessor != null)
    {
      accessor.lock(durableLockingID, type, objectsToLock);
    }
  }

  public void unlock(boolean explicit, LockType type, IView view, Collection<? extends Object> objectsToUnlock)
  {
    if (explicit)
    {
      String durableLockingID = view.getDurableLockingID();
      if (durableLockingID != null)
      {
        DurableLocking accessor = getDurableLocking();
        accessor.unlock(durableLockingID, type, objectsToUnlock);
      }
    }

    super.unlock(type, view, objectsToUnlock);
  }

  public void unlock(boolean explicit, IView view)
  {
    if (explicit)
    {
      String durableLockingID = view.getDurableLockingID();
      if (durableLockingID != null)
      {
        DurableLocking accessor = getDurableLocking();
        accessor.unlock(durableLockingID);
      }
    }

    super.unlock(view);
  }

  public LockArea createLockArea(String userID, CDOBranchPoint branchPoint, boolean readOnly,
      Map<CDOID, LockGrade> locks)
  {
    DurableLocking accessor = getDurableLocking();
    return accessor.createLockArea(userID, branchPoint, readOnly, locks);
  }

  public LockArea createLockArea(InternalView view)
  {
    String userID = view.getSession().getUserID();
    CDOBranchPoint branchPoint = CDOBranchUtil.copyBranchPoint(view);
    boolean readOnly = view.isReadOnly();
    Map<CDOID, LockGrade> locks = getLocks(view);

    LockArea area = createLockArea(userID, branchPoint, readOnly, locks);
    synchronized (openViews)
    {
      openViews.put(area.getDurableLockingID(), view);
    }

    return area;
  }

  public LockArea getLockArea(String durableLockingID) throws LockAreaNotFoundException
  {
    DurableLocking accessor = getDurableLocking();
    return accessor.getLockArea(durableLockingID);
  }

  public void getLockAreas(String userIDPrefix, LockArea.Handler handler)
  {
    if (userIDPrefix == null)
    {
      userIDPrefix = "";
    }

    DurableLocking accessor = getDurableLocking();
    accessor.getLockAreas(userIDPrefix, handler);
  }

  public void deleteLockArea(String durableLockingID)
  {
    DurableLocking accessor = getDurableLocking();
    accessor.deleteLockArea(durableLockingID);
    unregisterOpenView(durableLockingID);
  }

  public IView openView(ISession session, int viewID, boolean readOnly, final String durableLockingID)
  {
    synchronized (openViews)
    {
      InternalView view = openViews.get(durableLockingID);
      if (view != null)
      {
        throw new IllegalStateException("Durable view is already open: " + view);
      }

      LockArea area = getLockArea(durableLockingID);
      if (area.isReadOnly() != readOnly)
      {
        throw new IllegalStateException("Durable read-only state does not match the request");
      }

      if (readOnly)
      {
        view = (InternalView)session.openView(viewID, area);
      }
      else
      {
        view = (InternalView)session.openTransaction(viewID, area);
      }

      changeContext(new DurableView(durableLockingID), view);
      view.setDurableLockingID(durableLockingID);
      view.addListener(new LifecycleEventAdapter()
      {
        @Override
        protected void onDeactivated(ILifecycle lifecycle)
        {
          synchronized (openViews)
          {
            openViews.remove(durableLockingID);
          }
        }
      });

      openViews.put(durableLockingID, view);
      return view;
    }
  }

  @Override
  protected void doActivate() throws Exception
  {
    super.doActivate();
    loadDurableLocks();
    getRepository().getSessionManager().addListener(sessionManagerListener);
  }

  @Override
  protected void doDeactivate() throws Exception
  {
    ISessionManager sessionManager = getRepository().getSessionManager();
    sessionManager.removeListener(sessionManagerListener);
    for (ISession session : sessionManager.getSessions())
    {
      session.removeListener(sessionListener);
    }

    super.doDeactivate();
  }

  private DurableLocking getDurableLocking()
  {
    IStoreAccessor accessor = StoreThreadLocal.getAccessor();
    if (accessor instanceof DurableLocking)
    {
      return (DurableLocking)accessor;
    }

    throw new IllegalStateException("Store does not support durable locking");
  }

  private void loadDurableLocks()
  {
    InternalStore store = repository.getStore();
    IStoreAccessor reader = null;

    try
    {
      reader = store.getReader(null);
      if (reader instanceof DurableLocking)
      {
        StoreThreadLocal.setAccessor(reader);

        DurableLockLoader handler = new DurableLockLoader();
        getLockAreas(null, handler);
      }
    }
    finally
    {
      StoreThreadLocal.release();
    }
  }

  private void unregisterOpenView(String durableLockingID)
  {
    synchronized (openViews)
    {
      InternalView view = openViews.remove(durableLockingID);
      if (view != null)
      {
        view.setDurableLockingID(null);
      }
    }
  }

  public CDOID getLockKeyID(Object key)
  {
    if (key instanceof CDOID)
    {
      return (CDOID)key;
    }

    if (key instanceof CDOIDAndBranch)
    {
      return ((CDOIDAndBranch)key).getID();
    }

    throw new ImplementationError("Unexpected lock object: " + key);
  }

  /**
   * @author Eike Stepper
   */
  private final class DurableView implements IView
  {
    private String durableLockingID;

    public DurableView(String durableLockingID)
    {
      this.durableLockingID = durableLockingID;
    }

    public String getDurableLockingID()
    {
      return durableLockingID;
    }

    public int getViewID()
    {
      throw new UnsupportedOperationException();
    }

    public boolean isReadOnly()
    {
      throw new UnsupportedOperationException();
    }

    public CDOBranch getBranch()
    {
      throw new UnsupportedOperationException();
    }

    public long getTimeStamp()
    {
      throw new UnsupportedOperationException();
    }

    public CDORevision getRevision(CDOID id)
    {
      throw new UnsupportedOperationException();
    }

    public void close()
    {
      throw new UnsupportedOperationException();
    }

    public boolean isClosed()
    {
      throw new UnsupportedOperationException();
    }

    public IRepository getRepository()
    {
      throw new UnsupportedOperationException();
    }

    public ISession getSession()
    {
      throw new UnsupportedOperationException();
    }

    @Override
    public int hashCode()
    {
      return durableLockingID.hashCode();
    }

    @Override
    public boolean equals(Object obj)
    {
      if (obj == this)
      {
        return true;
      }

      if (obj instanceof DurableView)
      {
        DurableView that = (DurableView)obj;
        return durableLockingID.equals(that.getDurableLockingID());
      }

      return false;
    }

    @Override
    public String toString()
    {
      return MessageFormat.format("DurableView[{0}]", durableLockingID);
    }
  }

  /**
   * @author Eike Stepper
   */
  private final class DurableLockLoader implements LockArea.Handler
  {
    public boolean handleLockArea(LockArea area)
    {
      IView view = new DurableView(area.getDurableLockingID());

      Collection<Object> readLocks = new ArrayList<Object>();
      Collection<Object> writeLocks = new ArrayList<Object>();
      for (Entry<CDOID, LockGrade> entry : area.getLocks().entrySet())
      {
        Object key = getLockKey(entry.getKey(), area.getBranch());
        LockGrade grade = entry.getValue();
        if (grade.isRead())
        {
          readLocks.add(key);
        }

        if (grade.isWrite())
        {
          writeLocks.add(key);
        }
      }

      try
      {
        lock(LockType.READ, view, readLocks, 1000L);
        lock(LockType.WRITE, view, writeLocks, 1000L);
      }
      catch (InterruptedException ex)
      {
        throw WrappedException.wrap(ex);
      }

      return true;
    }
  }
}
