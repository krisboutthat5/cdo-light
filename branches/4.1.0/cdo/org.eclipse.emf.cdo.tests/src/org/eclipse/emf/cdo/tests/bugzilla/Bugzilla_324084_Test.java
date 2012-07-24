/*
 * Copyright (c) 2004 - 2012 Eike Stepper (Berlin, Germany) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Pascal Lehmann - initial API and implementation
 *    Eike Stepper - maintenance
 */
package org.eclipse.emf.cdo.tests.bugzilla;

import org.eclipse.emf.cdo.common.branch.CDOBranch;
import org.eclipse.emf.cdo.eresource.CDOResource;
import org.eclipse.emf.cdo.server.IRepository;
import org.eclipse.emf.cdo.session.CDOSession;
import org.eclipse.emf.cdo.tests.AbstractCDOTest;
import org.eclipse.emf.cdo.tests.config.IRepositoryConfig;
import org.eclipse.emf.cdo.tests.config.impl.ConfigTest.CleanRepositoriesBefore;
import org.eclipse.emf.cdo.tests.model4.RefMultiContained;
import org.eclipse.emf.cdo.tests.model4.RefSingleContained;
import org.eclipse.emf.cdo.transaction.CDOTransaction;
import org.eclipse.emf.cdo.view.CDOAdapterPolicy;

import org.eclipse.emf.spi.cdo.DefaultCDOMerger;

import java.util.Map;

/**
 * CommitException with XRef on new objects after branch merge.
 * <p>
 * See bug 324084
 * 
 * @author Pascal Lehmann
 */
@CleanRepositoriesBefore
public class Bugzilla_324084_Test extends AbstractCDOTest
{
  @Override
  public synchronized Map<String, Object> getTestProperties()
  {
    Map<String, Object> map = super.getTestProperties();
    map.put(IRepository.Props.ENSURE_REFERENTIAL_INTEGRITY, "true");
    return map;
  }

  @Requires(IRepositoryConfig.CAPABILITY_BRANCHING)
  public void testXRefMergeSingleValueTest() throws Exception
  {
    // setup transaction.
    final CDOSession session = openSession();
    final CDOTransaction tr1 = session.openTransaction();
    tr1.options().addChangeSubscriptionPolicy(CDOAdapterPolicy.ALL);

    final CDOResource resource = tr1.createResource(getResourcePath("/test1"));
    RefSingleContained container = getModel4Factory().createRefSingleContained();
    resource.getContents().add(container);

    tr1.commit();
    sleep(1000);

    final CDOBranch otherBranch = tr1.getBranch().createBranch("other");
    final CDOTransaction tr2 = session.openTransaction(otherBranch);

    RefSingleContained otherContainer = tr2.getObject(container);
    assertNotNull(otherContainer);

    // set a new element.
    otherContainer.setElement(getModel4Factory().createSingleContainedElement());

    tr2.commit();

    // sleep to have the merger see the changes.
    sleep(1000);

    // merge the other branch to main.
    tr1.merge(tr2.getBranch().getHead(), new DefaultCDOMerger.PerFeature.ManyValued());

    tr1.commit();
    assertEquals(false, tr1.isDirty());
  }

  @Requires(IRepositoryConfig.CAPABILITY_BRANCHING)
  public void testXRefMergeManyValueTest() throws Exception
  {
    // setup transaction.
    final CDOSession session = openSession();
    final CDOTransaction tr1 = session.openTransaction();
    tr1.options().addChangeSubscriptionPolicy(CDOAdapterPolicy.ALL);

    final CDOResource resource = tr1.createResource(getResourcePath("/test1"));
    RefMultiContained container = getModel4Factory().createRefMultiContained();
    resource.getContents().add(container);

    tr1.commit();
    sleep(1000);

    final CDOBranch otherBranch = tr1.getBranch().createBranch("other");
    final CDOTransaction tr2 = session.openTransaction(otherBranch);

    RefMultiContained otherContainer = tr2.getObject(container);
    assertNotNull(otherContainer);

    // add a new element on other branch at index 0.
    otherContainer.getElements().add(0, getModel4Factory().createMultiContainedElement());

    tr2.commit();

    // sleep to have the merger see the changes.
    sleep(1000);

    // merge the other branch to main.
    tr1.merge(tr2.getBranch().getHead(), new DefaultCDOMerger.PerFeature.ManyValued());

    tr1.commit();
    assertEquals(false, tr1.isDirty());
  }
}
