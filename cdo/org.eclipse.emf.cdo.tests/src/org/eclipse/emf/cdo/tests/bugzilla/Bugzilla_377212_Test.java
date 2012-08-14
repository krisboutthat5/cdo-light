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

import org.eclipse.emf.cdo.common.util.CDOException;
import org.eclipse.emf.cdo.eresource.CDOResourceFolder;
import org.eclipse.emf.cdo.session.CDOSession;
import org.eclipse.emf.cdo.tests.AbstractCDOTest;
import org.eclipse.emf.cdo.transaction.CDOTransaction;
import org.eclipse.emf.cdo.util.CDOBalancedTree;

import org.eclipse.emf.ecore.EObject;

/**
 * @author Eike Stepper
 */
public class Bugzilla_377212_Test extends AbstractCDOTest
{
  public void testBalancedTree() throws Exception
  {
    CDOSession session = openSession();
    CDOTransaction transaction = session.openTransaction();
    CDOResourceFolder root = transaction.createResourceFolder(getResourcePath("tree"));

    CDOBalancedTree tree = new CDOBalancedTree(root);

    for (int i = 0; i < 10000; i++)
    {
      EObject object = getModel1Factory().createSupplier();
      tree.addObject(object);
    }

    transaction.commit();
    msg("Committed transaction");
  }

  public void testBalancedTreeLocked() throws Exception
  {
    CDOSession session = openSession();
    CDOTransaction transaction = session.openTransaction();
    CDOResourceFolder root = transaction.createResourceFolder(getResourcePath("tree"));
    transaction.commit();

    CDOTransaction transaction2 = session.openTransaction();
    CDOResourceFolder root2 = transaction2.getObject(root);
    root2.cdoWriteLock().lock();

    CDOBalancedTree tree = new CDOBalancedTree(root);
    tree.setLockAttempts(3);

    EObject object = getModel1Factory().createSupplier();

    try
    {
      tree.addObject(object);
    }
    catch (CDOException ex)
    {
      assertEquals(true, ex.getMessage().startsWith("Unable to aquire write lock on balanced tree"));
      return;
    }

    fail("CDOException expected");
  }
}
