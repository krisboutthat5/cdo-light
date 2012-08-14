/*
 * Copyright (c) 2004 - 2012 Eike Stepper (Berlin, Germany) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *    Caspar De Groot - initial API and implementation
 */
package org.eclipse.emf.cdo.tests.bugzilla;

import org.eclipse.emf.cdo.CDOObject;
import org.eclipse.emf.cdo.common.CDOCommonSession.Options.PassiveUpdateMode;
import org.eclipse.emf.cdo.common.commit.CDOCommitInfo;
import org.eclipse.emf.cdo.common.revision.CDORevision;
import org.eclipse.emf.cdo.eresource.CDOResource;
import org.eclipse.emf.cdo.session.CDOSession;
import org.eclipse.emf.cdo.tests.AbstractCDOTest;
import org.eclipse.emf.cdo.tests.model1.PurchaseOrder;
import org.eclipse.emf.cdo.transaction.CDOTransaction;
import org.eclipse.emf.cdo.util.CDOUtil;
import org.eclipse.emf.cdo.util.CommitException;

import org.eclipse.emf.ecore.EObject;

import java.util.Date;

/**
 * @author Caspar De Groot
 */
public class Bugzilla_338779_Test extends AbstractCDOTest
{
  private void test(PassiveUpdateMode passiveUpdateMode) throws CommitException
  {
    final CDOSession session = openSession();
    CDOTransaction tx = session.openTransaction();
    if (passiveUpdateMode != null)
    {
      session.options().setPassiveUpdateEnabled(true);
      session.options().setPassiveUpdateMode(passiveUpdateMode);
    }
    else
    {
      session.options().setPassiveUpdateEnabled(false);
    }
    CDOResource r1 = tx.createResource(getResourcePath("/r1")); //$NON-NLS-1$

    PurchaseOrder po1 = getModel1Factory().createPurchaseOrder();
    po1.setDate(new Date());
    r1.getContents().add(po1);

    tx.commit();

    check(po1, session);

    long timestamp = doSecondSession();
    if (passiveUpdateMode != null)
    {
      session.waitForUpdate(timestamp);

      if (passiveUpdateMode == PassiveUpdateMode.INVALIDATIONS)
      {
        // Read something on the object to force load
        po1.getDate();
      }
    }
    else
    {
      session.refresh();
    }

    check(po1, session);

    tx.close();
    session.close();
  }

  public void test_refresh() throws CommitException
  {
    test(null);
  }

  public void test_passiveUpdate_invalidations() throws CommitException
  {
    test(PassiveUpdateMode.INVALIDATIONS);
  }

  public void test_passiveUpdate_changes() throws CommitException
  {
    test(PassiveUpdateMode.CHANGES);
  }

  public void test_passiveUpdate_additions() throws CommitException
  {
    test(PassiveUpdateMode.ADDITIONS);
  }

  private void check(EObject eObject, CDOSession session)
  {
    CDOObject obj = CDOUtil.getCDOObject(eObject);
    assertClean(obj, obj.cdoView());
    CDORevision rev1 = obj.cdoRevision();
    CDORevision rev2 = session.getRevisionManager().getRevisionByVersion(rev1.getID(), rev1, 0, false);
    assertEquals(rev1, rev2);
    assertSame(rev1, rev2);
  }

  private long doSecondSession() throws CommitException
  {
    CDOSession session = openSession();
    CDOTransaction tx = session.openTransaction();
    CDOResource r1 = tx.getResource(getResourcePath("/r1")); //$NON-NLS-1$

    // Change the purchaseOrder
    PurchaseOrder po1 = (PurchaseOrder)r1.getContents().get(0);
    po1.setDate(new Date());

    CDOCommitInfo commitInfo = tx.commit();
    tx.close();
    session.close();

    return commitInfo.getTimeStamp();
  }
}
