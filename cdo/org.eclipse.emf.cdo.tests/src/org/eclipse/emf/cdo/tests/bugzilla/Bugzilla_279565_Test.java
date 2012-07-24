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
package org.eclipse.emf.cdo.tests.bugzilla;

import org.eclipse.emf.cdo.session.CDOSession;
import org.eclipse.emf.cdo.tests.AbstractCDOTest;
import org.eclipse.emf.cdo.transaction.CDOTransaction;

import org.eclipse.net4j.util.event.IEvent;
import org.eclipse.net4j.util.event.IListener;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;

/**
 * Invalid listener might prevent CDOTransaction to close properly
 * <p>
 * See bug 279565
 * 
 * @author Eike Stepper
 */
public class Bugzilla_279565_Test extends AbstractCDOTest
{
  public void testBugzilla_279565() throws Exception
  {
    CDOSession session = openSession();
    CDOTransaction tx = session.openTransaction();
    final ResourceSet resourceSet = tx.getResourceSet();
    resourceSet.eAdapters().add(new AdapterImpl()
    {
      @Override
      public void notifyChanged(Notification msg)
      {
        throw new IllegalStateException("Simulated exception");
      }
    });

    try
    {
      tx.getOrCreateResource(getResourcePath("/test"));
      fail("IllegalStateException expected");
    }
    catch (IllegalStateException sucess)
    {
    }

    tx.close();

    new PollingTimeOuter()
    {

      @Override
      protected boolean successful()
      {
        return resourceSet.getResources().isEmpty();
      }
    };
  }

  public void testBugzilla_279565_OwnResourceSet() throws Exception
  {
    final ResourceSet resourceSet = new ResourceSetImpl();
    resourceSet.eAdapters().add(new AdapterImpl()
    {
      @Override
      public void notifyChanged(Notification msg)
      {
        throw new IllegalStateException("Simulated exception");
      }
    });

    CDOSession session = openSession();
    CDOTransaction tx = session.openTransaction();
    tx.getOrCreateResource(getResourcePath("/test"));
    tx.close();

    new PollingTimeOuter()
    {
      @Override
      protected boolean successful()
      {
        return resourceSet.getResources().isEmpty();
      }
    }.assertNoTimeOut();
  }

  public void testBugzilla_279565_TXListener() throws Exception
  {
    CDOSession session = openSession();
    CDOTransaction tx = session.openTransaction();
    final ResourceSet resourceSet = tx.getResourceSet();
    tx.addListener(new IListener()
    {
      public void notifyEvent(IEvent event)
      {
        throw new IllegalStateException("Simulated exception");
      }
    });

    tx.getOrCreateResource(getResourcePath("/test"));
    tx.close();

    new PollingTimeOuter()
    {
      @Override
      protected boolean successful()
      {
        return resourceSet.getResources().isEmpty();
      }
    }.assertNoTimeOut();
  }

  public void testBugzilla_279565_AddedFromRemote() throws Exception
  {
    CDOSession session = openSession();
    CDOTransaction tx = session.openTransaction();
    final ResourceSet resourceSet = tx.getResourceSet();
    resourceSet.eAdapters().add(new AdapterImpl()
    {
      @Override
      public void notifyChanged(Notification msg)
      {
        throw new IllegalStateException("Simulated exception");
      }
    });

    CDOSession session2 = openSession();
    CDOTransaction tx2 = session2.openTransaction();
    tx2.getOrCreateResource(getResourcePath("/test"));
    session2.close();

    new PollingTimeOuter()
    {
      @Override
      protected boolean successful()
      {
        return resourceSet.getResources().size() == 1;
      }
    };

    tx.close();
  }
}
