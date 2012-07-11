/**
 * Copyright (c) 2004 - 2011 Eike Stepper (Berlin, Germany) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Eike Stepper - initial API and implementation
 */
package org.eclipse.emf.spi.cdo;

import org.eclipse.emf.cdo.CDOObject;
import org.eclipse.emf.cdo.CDOState;
import org.eclipse.emf.cdo.common.branch.CDOBranch;
import org.eclipse.emf.cdo.common.revision.CDOIDAndVersion;
import org.eclipse.emf.cdo.common.revision.CDORevisionKey;
import org.eclipse.emf.cdo.common.revision.delta.CDORevisionDelta;
import org.eclipse.emf.cdo.eresource.impl.CDOResourceImpl;
import org.eclipse.emf.cdo.spi.common.revision.InternalCDORevision;
import org.eclipse.emf.cdo.view.CDOFeatureAnalyzer;
import org.eclipse.emf.cdo.view.CDOView;

import org.eclipse.net4j.util.concurrent.IRWLockManager.LockType;
import org.eclipse.net4j.util.lifecycle.ILifecycle;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.ecore.EObject;

import java.util.List;
import java.util.Map;

/**
 * @author Eike Stepper
 * @since 2.0
 * @noextend This interface is not intended to be extended by clients.
 * @noimplement This interface is not intended to be implemented by clients.
 */
public interface InternalCDOView extends CDOView, ILifecycle
{
  public void setViewID(int viewId);

  public InternalCDOSession getSession();

  public void setSession(InternalCDOSession session);

  public InternalCDOViewSet getViewSet();

  public void setViewSet(InternalCDOViewSet viewSet);

  public CDOFeatureAnalyzer getFeatureAnalyzer();

  public void setFeatureAnalyzer(CDOFeatureAnalyzer featureAnalyzer);

  /**
   * Returns an unmodifiable map of the objects managed by this view.
   * 
   * @since 4.0
   */
  public Map<Long, InternalCDOObject> getObjects();

  /**
   * @since 4.0
   */
  public CDOStore getStore();

  public InternalCDOTransaction toTransaction();

  public void attachResource(CDOResourceImpl resource);

  /**
   * @since 3.0
   */
  public void handleObjectStateChanged(InternalCDOObject object, CDOState oldState, CDOState newState);

  /**
   * @since 4.0
   */
  public void invalidate(List<CDORevisionDelta> allChangedObjects,
      List<Long> allDetachedObjects, Map<Long, InternalCDORevision> oldRevisions, boolean async);

  /**
   * @since 3.0
   */
  public void collectViewedRevisions(Map<Long, InternalCDORevision> revisions);

  public void remapObject(long oldID);

  public long getResourceNodeID(String path);

  public void registerProxyResource(CDOResourceImpl resource);

  public void registerObject(InternalCDOObject object);

  public void deregisterObject(InternalCDOObject object);

  public InternalCDORevision getRevision(long id, boolean loadOnDemand);

  /**
   * @since 3.0
   */
  public void prefetchRevisions(long id, int depth);

  public long convertObjectToID(Object potentialObject);

  public long convertObjectToID(Object potentialObject, boolean onlyPersistedID);

  public Object convertIDToObject(long potentialID);

  /**
   * @since 3.0
   */
  public boolean isObjectLocked(CDOObject object, LockType lockType, boolean byOthers);

  public void handleAddAdapter(InternalCDOObject eObject, Adapter adapter);

  public void handleRemoveAdapter(InternalCDOObject eObject, Adapter adapter);

  public void subscribe(EObject eObject, Adapter adapter);

  public void unsubscribe(EObject eObject, Adapter adapter);

  public boolean hasSubscription(long id);

  // /**
  // * Each time CDORevision or CDOState of an CDOObject is modified, ensure that no concurrent access is modifying it
  // at
  // * the same time. Uses {@link InternalCDOView#getStateLock()} to be thread safe.
  // * <p>
  // * In the case where {@link CDOObject#cdoRevision()} or {@link CDOObject#cdoState()} is called without using this
  // * lock, it is not guarantee that the state didn't change immediately after.
  // * <p>
  // * <code>
  // * if (cdoObject.cdoState() != CDOState.PROXY)
  // * {
  // * // At this point could be a proxy!
  // * cdoObject.cdoRevision();
  // * }
  // * </code>
  // * <p>
  // * The reason were we didn't use {@link CDOView#getLock()} is to not allow the access of that lock to the users
  // since
  // * it is very critical. Instead of giving this API to the end-users, a better API should be given in the CDOObject
  // to
  // * give them want they need.
  // */
  // public ReentrantLock getStateLock();
  //
  // /**
  // * @since 4.0
  // */
  // public Object getObjectsLock();
}
