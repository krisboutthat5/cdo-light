/*
 * Copyright (c) 2004 - 2012 Eike Stepper (Berlin, Germany) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Eike Stepper - initial API and implementation
 */
package org.eclipse.emf.cdo.spi.server;

import org.eclipse.emf.cdo.CDOObject;
import org.eclipse.emf.cdo.server.CDOServerUtil;
import org.eclipse.emf.cdo.server.IRepository.WriteAccessHandler;
import org.eclipse.emf.cdo.server.IStoreAccessor;
import org.eclipse.emf.cdo.server.IStoreAccessor.CommitContext;
import org.eclipse.emf.cdo.server.ITransaction;
import org.eclipse.emf.cdo.spi.common.revision.InternalCDORevision;
import org.eclipse.emf.cdo.util.CDOUtil;
import org.eclipse.emf.cdo.view.CDOView;

import org.eclipse.net4j.util.lifecycle.LifecycleUtil;
import org.eclipse.net4j.util.om.monitor.OMMonitor;

import org.eclipse.emf.ecore.EObject;

/**
 * @author Eike Stepper
 * @since 4.0
 */
public class ObjectWriteAccessHandler implements WriteAccessHandler
{
  private boolean legacyModeEnabled;

  private IStoreAccessor.CommitContext commitContext;

  private CDOView view;

  private EObject[] newObjects;

  private EObject[] dirtyObjects;

  public ObjectWriteAccessHandler()
  {
  }

  public ObjectWriteAccessHandler(boolean legacyModeEnabled)
  {
    this.legacyModeEnabled = legacyModeEnabled;
  }

  public final boolean isLegacyModeEnabled()
  {
    return legacyModeEnabled;
  }

  protected final IStoreAccessor.CommitContext getCommitContext()
  {
    return commitContext;
  }

  protected final ITransaction getTransaction()
  {
    return commitContext.getTransaction();
  }

  protected final CDOView getView()
  {
    if (view == null)
    {
      view = CDOServerUtil.openView(commitContext, legacyModeEnabled);
    }

    return view;
  }

  protected final EObject[] getNewObjects()
  {
    if (newObjects == null)
    {
      InternalCDORevision[] newRevisions = commitContext.getNewObjects();
      newObjects = new EObject[newRevisions.length];
      CDOView view = getView();

      for (int i = 0; i < newRevisions.length; i++)
      {
        InternalCDORevision newRevision = newRevisions[i];
        CDOObject newObject = view.getObject(newRevision.getID());
        newObjects[i] = CDOUtil.getEObject(newObject);
      }
    }

    return newObjects;
  }

  protected final EObject[] getDirtyObjects()
  {
    if (dirtyObjects == null)
    {
      InternalCDORevision[] dirtyRevisions = commitContext.getDirtyObjects();
      dirtyObjects = new EObject[dirtyRevisions.length];
      CDOView view = getView();

      for (int i = 0; i < dirtyRevisions.length; i++)
      {
        InternalCDORevision dirtyRevision = dirtyRevisions[i];
        CDOObject dirtyObject = view.getObject(dirtyRevision.getID());
        dirtyObjects[i] = CDOUtil.getEObject(dirtyObject);
      }
    }

    return dirtyObjects;
  }

  public final void handleTransactionBeforeCommitting(ITransaction transaction,
      IStoreAccessor.CommitContext commitContext, OMMonitor monitor) throws RuntimeException
  {
    try
    {
      this.commitContext = commitContext;
      handleTransactionBeforeCommitting(monitor);
    }
    finally
    {
      LifecycleUtil.deactivate(view);
      view = null;
      dirtyObjects = null;
      newObjects = null;
      this.commitContext = null;
    }
  }

  public final void handleTransactionAfterCommitted(ITransaction transaction, CommitContext commitContext,
      OMMonitor monitor)
  {
    try
    {
      this.commitContext = commitContext;
      handleTransactionAfterCommitted(monitor);
    }
    finally
    {
      LifecycleUtil.deactivate(view);
      view = null;
      dirtyObjects = null;
      newObjects = null;
      this.commitContext = null;
    }
  }

  protected void handleTransactionBeforeCommitting(OMMonitor monitor) throws RuntimeException
  {
  }

  protected void handleTransactionAfterCommitted(OMMonitor monitor)
  {
  }
}
