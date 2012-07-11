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
package org.eclipse.emf.cdo.server;

import org.eclipse.emf.cdo.common.CDOCommonRepository;
import org.eclipse.emf.cdo.common.branch.CDOBranchManager;
import org.eclipse.emf.cdo.common.commit.CDOCommitInfoHandler;
import org.eclipse.emf.cdo.common.lock.IDurableLockingManager;
import org.eclipse.emf.cdo.common.model.CDOPackageRegistry;
import org.eclipse.emf.cdo.common.revision.CDORevision;
import org.eclipse.emf.cdo.common.revision.CDORevisionManager;

import org.eclipse.net4j.util.container.IContainer;
import org.eclipse.net4j.util.om.monitor.OMMonitor;

import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EPackage.Registry;

import java.util.List;
import java.util.Map;

/**
 * @author Eike Stepper
 * @noextend This interface is not intended to be extended by clients.
 * @noimplement This interface is not intended to be implemented by clients.
 */
public interface IRepository extends CDOCommonRepository, IQueryHandlerProvider, IContainer<Object>
{
  /**
   * @since 3.0
   */
  public static final String SYSTEM_USER_ID = "CDO_SYSTEM"; //$NON-NLS-1$

  public IStore getStore();

  public Map<String, String> getProperties();

  /**
   * Returns the EMF {@link Registry package registry} that is used by this repository.
   * 
   * @since 2.0
   */
  public CDOPackageRegistry getPackageRegistry();

  /**
   * @since 3.0
   */
  public CDOBranchManager getBranchManager();

  /**
   * @since 3.0
   */
  public CDORevisionManager getRevisionManager();

  public ISessionManager getSessionManager();

  /**
   * @since 4.0
   */
  public IDurableLockingManager getLockManager();

  /**
   * @since 2.0
   */
  public IQueryHandlerProvider getQueryHandlerProvider();


  /**
   * Validates the given timeStamp against the repository time.
   * 
   * @throws IllegalArgumentException
   *           if the given timeStamp is less than the repository creation time or greater than the current repository
   *           time.
   * @since 2.0
   */
  public void validateTimeStamp(long timeStamp) throws IllegalArgumentException;

  /**
   * @since 4.0
   */
  public void addCommitInfoHandler(CDOCommitInfoHandler handler);

  /**
   * @since 4.0
   */
  public void removeCommitInfoHandler(CDOCommitInfoHandler handler);

  /**
   * @since 2.0
   */
  public void addHandler(Handler handler);

  /**
   * @since 2.0
   */
  public void removeHandler(Handler handler);

  /**
   * @since 4.0
   */
  public void setInitialPackages(EPackage... initialPackages);

  /**
   * A marker interface to indicate valid arguments to {@link IRepository#addHandler(Handler)} and
   * {@link IRepository#removeHandler(Handler)}.
   * 
   * @see ReadAccessHandler
   * @see WriteAccessHandler
   * @author Eike Stepper
   * @since 2.0
   */
  public interface Handler
  {
  }

  /**
   * Provides a way to handle revisions that are to be sent to the client.
   * 
   * @author Eike Stepper
   * @since 2.0
   */
  public interface ReadAccessHandler extends Handler
  {
    /**
     * Provides a way to handle revisions that are to be sent to the client.
     * 
     * @param session
     *          The session that is going to send the revisions.
     * @param revisions
     *          The revisions that are requested by the client. If the client must not see any of these revisions an
     *          unchecked exception must be thrown.
     * @param additionalRevisions
     *          The additional revisions that are to be sent to the client because internal optimizers believe that they
     *          will be needed soon. If the client must not see any of these revisions they should be removed from the
     *          list.
     * @throws RuntimeException
     *           to indicate that none of the revisions must be sent to the client. This exception will be visible at
     *           the client side!
     */
    public void handleRevisionsBeforeSending(ISession session, CDORevision[] revisions,
        List<CDORevision> additionalRevisions) throws RuntimeException;
  }

  /**
   * @author Eike Stepper
   * @since 2.0
   */
  public interface WriteAccessHandler extends Handler
  {
    /**
     * Provides a way to handle transactions that are to be committed to the backend store.
     * 
     * @param transaction
     *          The transaction that is going to be committed.
     * @param commitContext
     *          The context of the commit operation that is to be executed against the backend store. The context can be
     *          used to introspect all aspects of the current commit operation. <b>Note that you must not alter the
     *          internal state of the commit context in any way!</b>
     * @param monitor
     *          A monitor that should be used by the implementor to avoid timeouts.
     * @throws RuntimeException
     *           to indicate that the commit operation must not be executed against the backend store. This exception
     *           will be visible at the client side!
     */
    public void handleTransactionBeforeCommitting(ITransaction transaction, IStoreAccessor.CommitContext commitContext,
        OMMonitor monitor) throws RuntimeException;

    /**
     * Provides a way to handle transactions after they have been committed to the backend store.
     * 
     * @param transaction
     *          The transaction that has been committed.
     * @param commitContext
     *          The context of the commit operation that was executed against the backend store. The context can be used
     *          to introspect all aspects of the current commit operation. <b>Note that you must not alter the internal
     *          state of the commit context in any way!</b>
     * @param monitor
     *          A monitor that should be used by the implementor to avoid timeouts.
     * @since 3.0
     */
    public void handleTransactionAfterCommitted(ITransaction transaction, IStoreAccessor.CommitContext commitContext,
        OMMonitor monitor);
  }

  /**
   * @author Eike Stepper
   * @noimplement This interface is not intended to be implemented by clients.
   */
  public interface Props
  {
    /**
     * @since 2.0
     */
    public static final String OVERRIDE_UUID = "overrideUUID"; //$NON-NLS-1$


    /**
     * @since 4.0
     */
    public static final String SUPPORTING_ECORE = "supportingEcore"; //$NON-NLS-1$

    /**
     * @since 3.0
     */
    public static final String ENSURE_REFERENTIAL_INTEGRITY = "ensureReferentialIntegrity"; //$NON-NLS-1$

    /**
     * @since 4.0
     */
    public static final String ALLOW_INTERRUPT_RUNNING_QUERIES = "allowInterruptRunningQueries"; //$NON-NLS-1$
  }
}
