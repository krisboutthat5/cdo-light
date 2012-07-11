/**
 * Copyright (c) 2004 - 2011 Eike Stepper (Berlin, Germany) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Eike Stepper - initial API and implementation
 *    Simon McDuff - maintenance
 *    Victor Roldan Betancort - maintenance
 */
package org.eclipse.emf.cdo.view;

import org.eclipse.emf.cdo.CDOAdapter;
import org.eclipse.emf.cdo.CDOInvalidationNotification;
import org.eclipse.emf.cdo.CDOObject;
import org.eclipse.emf.cdo.CDOObjectReference;
import org.eclipse.emf.cdo.common.CDOCommonView;
import org.eclipse.emf.cdo.common.branch.CDOBranch;
import org.eclipse.emf.cdo.common.branch.CDOBranchPoint;
import org.eclipse.emf.cdo.common.commit.CDOChangeSetData;
import org.eclipse.emf.cdo.common.revision.CDORevision;
import org.eclipse.emf.cdo.eresource.CDOResource;
import org.eclipse.emf.cdo.eresource.CDOResourceFolder;
import org.eclipse.emf.cdo.eresource.CDOResourceNode;
import org.eclipse.emf.cdo.session.CDOSession;
import org.eclipse.emf.cdo.transaction.CDOTransaction;
import org.eclipse.emf.cdo.util.CDOUpdatable;
import org.eclipse.emf.cdo.util.ReadOnlyException;

import org.eclipse.net4j.util.collection.CloseableIterator;
import org.eclipse.net4j.util.concurrent.IRWLockManager.LockType;
import org.eclipse.net4j.util.event.INotifier;
import org.eclipse.net4j.util.options.IOptions;
import org.eclipse.net4j.util.options.IOptionsContainer;
import org.eclipse.net4j.util.options.IOptionsEvent;
import org.eclipse.net4j.util.ref.ReferenceType;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.URIHandler;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * A read-only view to the state of the object graph in the repository of the underlying {@link CDOSession session} at a
 * specific {@link #getTimeStamp() time} at a specific {@link #getBranch() branch}.
 * <p>
 * If the {@link #getTimeStamp() time} of a view is {@link CDOBranchPoint#UNSPECIFIED_DATE unspecified} the objects
 * provided and managed by that view always show the latest state the repository graph.
 * <p>
 * Objects that are accessed through this view are immutable for the client. Each attempt to call a mutator on one of
 * these objects or one of their feature lists will result in a {@link ReadOnlyException} being thrown immediately.
 * Mutable objects can be provided by a {@link CDOTransaction transaction}.
 * <p>
 * A view is opened through API of the underlying session like this:
 * 
 * <pre>
 *   CDOSession session = ...
 *   CDOView view = session.openView();
 *   ...
 * </pre>
 * 
 * @author Eike Stepper
 * @since 2.0
 * @noextend This interface is not intended to be extended by clients.
 * @noimplement This interface is not intended to be implemented by clients.
 */
public interface CDOView extends CDOCommonView, CDOUpdatable, INotifier, IOptionsContainer
{
  /**
   * Returns the {@link CDOSession session} this view was opened by.
   * 
   * @return The session this view was opened by, or <code>null</code> if this view is closed.
   * @see #close()
   * @see #isClosed()
   * @see CDOSession#openView()
   * @see CDOSession#openTransaction()
   */
  public CDOSession getSession();

  /**
   * Returns the {@link CDOViewSet view set} this view is associated with.
   * 
   * @return The view set this view is associated with, never <code>null</code>.
   * @see CDOViewSet#getViews()
   */
  public CDOViewSet getViewSet();

  /**
   * Returns the {@link ResourceSet resource set} this view is associated with.
   * <p>
   * Same as calling <tt>getViewSet().getResourceSet()</tt>.
   * 
   * @see CDOViewSet#getResourceSet()
   */
  public ResourceSet getResourceSet();

  public URIHandler getURIHandler();



  /**
   * @since 4.0
   */
  public boolean isInvalidationRunnerActive();

  /**
   * @since 3.0
   */
  public boolean isLegacyModeEnabled();

  /**
   * Returns always <code>false</code>.
   * <p>
   * This method has a special implementation in {@link CDOTransaction} as well.
   * 
   * @see CDOTransaction#isDirty()
   */
  public boolean isDirty();

  /**
   * Returns always <code>false</code>.
   * <p>
   * This method has a special implementation in {@link CDOTransaction} as well.
   * 
   * @see CDOTransaction#hasConflict()
   */
  public boolean hasConflict();

  /**
   * Returns <code>true</code> if a resource with the given path exists in the repository, <code>false</code>.
   * 
   * @see #getResource(String, boolean)
   */
  public boolean hasResource(String path);

  /**
   * @see ResourceSet#getResource(URI, boolean)
   */
  public CDOResource getResource(String path, boolean loadOnDemand);

  /**
   * Same as {@link #getResource(String, boolean) getResource(String, true)}.
   * 
   * @see ResourceSet#getResource(URI, boolean)
   */
  public CDOResource getResource(String path);

  /**
   * Returns the resource node with the given path, or <code>null</code> if no such resource node exists.
   */
  public CDOResourceNode getResourceNode(String path);

  /**
   * Returns the root resource of the repository.
   * <p>
   * The root resource is a special resource with only {@link CDOResourceNode CDOResourceNodes} in its contents list.
   * You can use it as the main entry into the new resource and folder structure.
   */
  public CDOResource getRootResource();

  /**
   * Returns a list of the resources in the given folder with a name equal to or starting with the value of the name
   * parameter.
   * 
   * @param folder
   *          The folder to search in, or <code>null</code> for top level resource nodes.
   * @param name
   *          the name or prefix of the resource nodes to return.
   * @param exactMatch
   *          <code>true</code> if the complete name of the resource must match, <code>false</code> if only a common
   *          prefix of the name must match.
   */
  public List<CDOResourceNode> queryResources(CDOResourceFolder folder, String name, boolean exactMatch);

  /**
   * Returns an iterator over the resources in the given folder with a name equal to or starting with the value of the
   * name parameter. The underlying query will be executed asynchronously.
   * 
   * @param folder
   *          The folder to search in, or <code>null</code> for top level resource nodes.
   * @param name
   *          the name or prefix of the resource nodes to return.
   * @param exactMatch
   *          <code>true</code> if the complete name of the resource must match, <code>false</code> if only a common
   *          prefix of the name must match.
   */
  public CloseableIterator<CDOResourceNode> queryResourcesAsync(CDOResourceFolder folder, String name,
      boolean exactMatch);

  /**
   * Returns a list of {@link CDOObjectReference object references} that represent the cross references to the specified
   * target object.
   * 
   * @param targetObject
   *          The target object that referencing objects are requested for.
   * @param sourceReferences
   *          The reference features that referencing objects are requested for, or an empty array if all reference
   *          features are to be used in the request.
   * @since 4.0
   * @see CDOView#queryXRefs(Set, EReference...)
   * @see CDOView#queryXRefsAsync(Set, EReference...)
   */
  public List<CDOObjectReference> queryXRefs(CDOObject targetObject, EReference... sourceReferences);

  /**
   * Returns a list of {@link CDOObjectReference object references} that represent the cross references to the specified
   * target objects.
   * 
   * @param targetObjects
   *          The set of target objects that referencing objects are requested for.
   * @param sourceReferences
   *          The reference features that referencing objects are requested for, or an empty array if all reference
   *          features are to be used in the request.
   * @since 3.0
   * @see CDOView#queryXRefs(CDOObject, EReference...)
   * @see CDOView#queryXRefsAsync(Set, EReference...)
   */
  public List<CDOObjectReference> queryXRefs(Set<CDOObject> targetObjects, EReference... sourceReferences);

  /**
   * Returns an iterator over the {@link CDOObjectReference object references} that represent the cross references to
   * the specified target objects. The underlying query will be executed asynchronously.
   * 
   * @param targetObjects
   *          The set of target objects that referencing objects are requested for.
   * @param sourceReferences
   *          The reference features that referencing objects are requested for, or an empty array if all reference
   *          features are to be used in the request.
   * @since 3.0
   * @see CDOView#queryXRefs(CDOObject, EReference...)
   * @see CDOView#queryXRefs(Set, EReference...)
   */
  public CloseableIterator<CDOObjectReference> queryXRefsAsync(Set<CDOObject> targetObjects,
      EReference... sourceReferences);

  /**
   * Returns the object for the given CDOID.
   * 
   * @param loadOnDemand
   *          whether to create and load the object, if it doesn't already exist.
   * @return the object resolved by the CDOID if the id is not <code>null</code>, or <code>null</code> if there isn't
   *         one and loadOnDemand is <code>false</code>.
   */
  public CDOObject getObject(long id, boolean loadOnDemand);

  /**
   * Returns the object for the given CDOID.
   * <p>
   * Same as <code>getObject(id, true)</code>.
   * 
   * @see #getObject(CDOID, boolean)
   */
  public CDOObject getObject(long id);

  /**
   * Takes an object from a (possibly) different view and <em>contextifies</em> it for the usage with this view.
   * <ul>
   * <li>If the given object is contained in this view it is returned unmodified.
   * <li>If the given object can not be cast to {@link CDOObject} it is returned unmodified.
   * <li>If the view of the given object is contained in a different session an <code>IllegalArgumentException</code> is
   * thrown.
   * <li>If <code>null</code> is passed <code>null</code> is returned.
   * </ul>
   */
  public <T extends EObject> T getObject(T objectFromDifferentView);

  /**
   * Returns <code>true</code> if an {@link CDOObject object} with the given {@link CDOID id} is currently registered in
   * this view, <code>false</code> otherwise.
   */
  public boolean isObjectRegistered(long id);

  /**
   * Reloads the given {@link CDOObject objects} from the repository.
   */
  public int reload(CDOObject... objects);

  /**
   * Locks the given objects. Once the objects are locked, they will not be changed remotely or go in conflict state.
   * 
   * @since 3.0
   */
  public void lockObjects(Collection<? extends CDOObject> objects, LockType lockType, long timeout)
      throws InterruptedException;

  /**
   * Unlocks the given locked objects of this view.
   */
  public void unlockObjects(Collection<? extends CDOObject> objects, LockType lockType);

  /**
   * Unlocks all locked objects of this view.
   * 
   * @since 2.0
   */
  public void unlockObjects();

  /**
   * Enables or disables the storage of all information that's needed to {@link CDOSession#openView(String) reopen} this
   * view at a later point in time. This information includes the {@link CDOBranchPoint branch point}, the user ID of
   * the {@link CDOSession session}, whether it's a read-only view or a {@link CDOTransaction transaction} and all the
   * locks that are acquired or will be acquired while durable locking is enabled.
   * 
   * @see CDOSession#openView(String)
   * @see CDOSession#openView(String, ResourceSet)
   * @see CDOSession#openTransaction(String)
   * @see CDOSession#openTransaction(String, ResourceSet)
   * @see CDOView#lockObjects(Collection, LockType, long)
   * @see CDOView#unlockObjects(Collection, LockType)
   * @see CDOView#unlockObjects()
   * @see CDOObject#cdoReadLock()
   * @see CDOObject#cdoWriteLock()
   * @since 4.0
   */
  public String enableDurableLocking(boolean enable);

  /**
   * @since 3.0
   */
  public void addObjectHandler(CDOObjectHandler handler);

  /**
   * @since 3.0
   */
  public void removeObjectHandler(CDOObjectHandler handler);

  /**
   * @since 3.0
   */
  public CDOObjectHandler[] getObjectHandlers();

  /**
   * Same as <code>createQuery(language, queryString, null)</code>.
   * 
   * @see #createQuery(String, String, Object)
   * @since 2.0
   */
  public CDOQuery createQuery(String language, String queryString);

  /**
   * @since 4.0
   */
  public CDOQuery createQuery(String language, String queryString, Object context);


  /**
   * @since 2.0
   */
  public Options options();

  /**
   * Encapsulates a set of notifying {@link CDOView view} configuration options.
   * 
   * @author Simon McDuff
   * @noextend This interface is not intended to be extended by clients.
   * @noimplement This interface is not intended to be implemented by clients.
   */
  public interface Options extends IOptions
  {
    /**
     * Returns the {@link CDOView view} of this options object.
     * 
     * @since 4.0
     */
    public CDOView getContainer();

    /**
     * @since 3.0
     */
    public static final int DEFAULT_REVISION_PREFETCHING = 100;

    public static final int NO_REVISION_PREFETCHING = 1;

    /**
     * Returns the reference type to be used in the internal object cache.
     * 
     * @return Either {@link ReferenceType#STRONG STRONG}, {@link ReferenceType#SOFT SOFT} or {@link ReferenceType#WEAK
     *         WEAK}.
     */
    public ReferenceType getCacheReferenceType();

    /**
     * Sets the reference type to be used in the internal object cache to either {@link ReferenceType#STRONG STRONG},
     * {@link ReferenceType#SOFT SOFT} or {@link ReferenceType#WEAK WEAK}. If <code>null</code> is passed the default
     * reference type {@link ReferenceType#SOFT SOFT} is set. If the given reference type does not differ from the one
     * being currently set the new value is ignored and <code>false</code> is returned. Otherwise existing object
     * references are converted to the new type and <code>true</code> is returned.
     */
    public boolean setCacheReferenceType(ReferenceType referenceType);

    /**
     * @since 3.0
     */
    public CDOInvalidationPolicy getInvalidationPolicy();

    /**
     * @since 3.0
     */
    public void setInvalidationPolicy(CDOInvalidationPolicy policy);

    /**
     * Returns <code>true</code> if the {@link CDOObject objects} in this view will notify their
     * {@link org.eclipse.emf.common.notify.Adapter adapters} about the fact that they are <em>invalidated</em> (due to
     * remote changes), <code>false</code> otherwise.
     * 
     * @see CDOInvalidationNotification
     */
    public boolean isInvalidationNotificationEnabled();

    /**
     * Specifies whether the {@link CDOObject objects} in this view will notify their
     * {@link org.eclipse.emf.common.notify.Adapter adapters} about the fact that they are <em>invalidated</em> (due to
     * remote changes) or not.
     * 
     * @see CDOInvalidationNotification
     */
    public void setInvalidationNotificationEnabled(boolean enabled);

    /**
     * Returns the current set of {@link CDOAdapterPolicy change subscription policies}.
     * 
     * @return The current set of change subscription policies, never <code>null</code>.
     * @see #addChangeSubscriptionPolicy(CDOAdapterPolicy)
     */
    public CDOAdapterPolicy[] getChangeSubscriptionPolicies();

    /**
     * Adds a change subscription policy to this view.
     * <p>
     * To activate a policy, you must do the following: <br>
     * <code>view.options().addChangeSubscriptionPolicy(CDOChangeSubscriptionPolicy.ALL);</code>
     * <p>
     * To register an object, you must add an adapter to the object in which you are interested:<br>
     * <code>eObject.eAdapters().add(myAdapter);</code>
     * <p>
     * By activating this feature, each object having at least one adapter that matches the current policy will be
     * registered with the server and will be notified for each change occurring in the scope of any other transaction.
     * <p>
     * {@link CDOAdapterPolicy#NONE} - Ignored. <br>
     * {@link CDOAdapterPolicy#ALL} - Enabled for all adapters used.<br>
     * {@link CDOAdapterPolicy#CDO} - Enabled only for adapters that implement {@link CDOAdapter}. <br>
     * Any other class that implement {@link CDOAdapterPolicy} will enable for whatever rules defined in that class.
     * <br>
     * <p>
     * If <code>myAdapter</code> in the above example matches the current policy, <code>eObject</code> will be
     * registered with the server and you will receive all changes from other transaction.
     * <p>
     * When the policy is changed all objects in the cache will automatically be recalculated.
     * <p>
     * You can subscribe to temporary objects. Even if you cannot receive notifications from other
     * {@link CDOTransaction} for these because they are only local to you, at commit time these objects will be
     * registered automatically.
     * 
     * @see #removeChangeSubscriptionPolicy(CDOAdapterPolicy)
     * @see #getChangeSubscriptionPolicies()
     */
    public void addChangeSubscriptionPolicy(CDOAdapterPolicy policy);

    /**
     * Removes a change subscription policy from this view.
     * 
     * @see #addChangeSubscriptionPolicy(CDOAdapterPolicy)
     * @see #getChangeSubscriptionPolicies()
     */
    public void removeChangeSubscriptionPolicy(CDOAdapterPolicy policy);

    // TODO
    public CDOAdapterPolicy getStrongReferencePolicy();

    /**
     * Sets the reference type to be used when an adapter is used to an object.
     * <p>
     * When <code>CDOView.setStrongReference(CDOAdapterPolicy.ALL)</code> is used, it is possible that the target object
     * will be GC. In that case, the adapter will never received notifications. By Default the value is at
     * <code>CDOAdapterPolicy.ALL</code>
     */
    public void setStrongReferencePolicy(CDOAdapterPolicy policy);

    /**
     * Returns the CDOStaleReferencePolicy in use.
     * 
     * @since 3.0
     */
    public CDOStaleReferencePolicy getStaleReferenceBehaviour();

    /**
     * Sets a policy on how to deal with stale references.
     * 
     * @since 3.0
     */
    public void setStaleReferenceBehaviour(CDOStaleReferencePolicy policy);

    /**
     * Returns the CDORevisionPrefetchingPolicy in use.
     */
    public CDORevisionPrefetchingPolicy getRevisionPrefetchingPolicy();

    /**
     * The CDORevisionPrefetchingPolicy feature of the CDOView allows CDO users to fetch many objects at a time.
     * <p>
     * The difference between the CDOCollectionLoadingPolicy feature and the CDORevisionPrefetchingPolicy feature is
     * subtle. The CDOCollectionLoadingPolicy feature determines how and when to fetch CDOIDs, while the
     * CDORevisionPrefetchingPolicy feature determines how and when to resolve CDOIDs (i.e. fetch the target objects).
     * <p>
     * <code>view.options().setRevisionPrefetchingPolicy (CDONet4jUtil.createRevisionPrefetchingPolicy(10));</code>
     * <p>
     * The end-user could provide its own implementation of the CDORevisionPrefetchingPolicy interface.
     */
    public void setRevisionPrefetchingPolicy(CDORevisionPrefetchingPolicy prefetchingPolicy);

    /**
     * An {@link IOptionsEvent options event} fired from view {@link CDOView#options() options} when the
     * {@link Options#setCacheReferenceType(ReferenceType) cache reference type} option has changed.
     * 
     * @author Eike Stepper
     * @noextend This interface is not intended to be extended by clients.
     * @noimplement This interface is not intended to be implemented by clients.
     */
    public interface CacheReferenceTypeEvent extends IOptionsEvent
    {
    }

    /**
     * An {@link IOptionsEvent options event} fired from view {@link CDOView#options() options} when the
     * {@link Options#setStrongReferencePolicy(CDOAdapterPolicy) strong reference policy} option has changed.
     * 
     * @author Eike Stepper
     * @noextend This interface is not intended to be extended by clients.
     * @noimplement This interface is not intended to be implemented by clients.
     * @since 4.0
     */
    public interface StrongReferencePolicyEvent extends IOptionsEvent
    {
    }

    /**
     * An {@link IOptionsEvent options event} fired from view {@link CDOView#options() options} when the
     * {@link Options#setStrongReferencePolicy(CDOAdapterPolicy) strong reference policy} option has changed.
     * 
     * @author Eike Stepper
     * @noextend This interface is not intended to be extended by clients.
     * @noimplement This interface is not intended to be implemented by clients.
     * @deprecated Use {@link StrongReferencePolicyEvent} instead.
     */
    @Deprecated
    public interface ReferencePolicyEvent extends StrongReferencePolicyEvent
    {
    }

    /**
     * An {@link IOptionsEvent options event} fired from view {@link CDOView#options() options} when the
     * {@link Options#setStaleReferenceBehaviour(CDOStaleReferencePolicy) stale reference type} option has changed.
     * 
     * @since 3.0
     * @noextend This interface is not intended to be extended by clients.
     * @noimplement This interface is not intended to be implemented by clients.
     */
    public interface StaleReferencePolicyEvent extends IOptionsEvent
    {
    }

    /**
     * An {@link IOptionsEvent options event} fired from view {@link CDOView#options() options} when the
     * {@link Options#addChangeSubscriptionPolicy(CDOAdapterPolicy) change subscription policies} option has changed.
     * 
     * @author Eike Stepper
     * @noextend This interface is not intended to be extended by clients.
     * @noimplement This interface is not intended to be implemented by clients.
     */
    public interface ChangeSubscriptionPoliciesEvent extends IOptionsEvent
    {
    }

    /**
     * An {@link IOptionsEvent options event} fired from view {@link CDOView#options() options} when the
     * {@link Options#setInvalidationPolicy(CDOInvalidationPolicy) invalidation policy} option has changed.
     * 
     * @author Eike Stepper
     * @since 3.0
     * @noextend This interface is not intended to be extended by clients.
     * @noimplement This interface is not intended to be implemented by clients.
     */
    public interface InvalidationPolicyEvent extends IOptionsEvent
    {
    }

    /**
     * An {@link IOptionsEvent options event} fired from view {@link CDOView#options() options} when the
     * {@link Options#setInvalidationNotificationEnabled(boolean) invalidation notification enabled} option has changed.
     * 
     * @author Eike Stepper
     * @noextend This interface is not intended to be extended by clients.
     * @noimplement This interface is not intended to be implemented by clients.
     */
    public interface InvalidationNotificationEvent extends IOptionsEvent
    {
    }

    /**
     * An {@link IOptionsEvent options event} fired from view {@link CDOView#options() options} when the
     * {@link Options#setRevisionPrefetchingPolicy(CDORevisionPrefetchingPolicy) revision prefetching policy} option has
     * changed.
     * 
     * @author Eike Stepper
     * @noextend This interface is not intended to be extended by clients.
     * @noimplement This interface is not intended to be implemented by clients.
     */
    public interface RevisionPrefetchingPolicyEvent extends IOptionsEvent
    {
    }
  }
}
