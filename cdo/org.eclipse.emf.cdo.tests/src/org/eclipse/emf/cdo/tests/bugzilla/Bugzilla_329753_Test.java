/*
 * Copyright (c) 2004 - 2012 Eike Stepper (Berlin, Germany) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Martin Fluegge - initial API and implementation
 */
package org.eclipse.emf.cdo.tests.bugzilla;

import org.eclipse.emf.cdo.CDOObject;
import org.eclipse.emf.cdo.common.revision.CDORevision;
import org.eclipse.emf.cdo.session.CDOSession;
import org.eclipse.emf.cdo.tests.AbstractCDOTest;
import org.eclipse.emf.cdo.tests.config.IModelConfig;
import org.eclipse.emf.cdo.tests.model1.OrderDetail;
import org.eclipse.emf.cdo.tests.model1.Product1;
import org.eclipse.emf.cdo.transaction.CDOTransaction;
import org.eclipse.emf.cdo.util.CDOUtil;

import org.eclipse.emf.ecore.resource.Resource;

/**
 * @author Martin Fluegge
 */
public class Bugzilla_329753_Test extends AbstractCDOTest
{
  @Requires(IModelConfig.CAPABILITY_LEGACY)
  public void testIncreasingVersion() throws Exception
  {
    CDOSession session = openSession();
    CDOTransaction transaction = session.openTransaction();

    OrderDetail orderDetail = getModel1Factory().createOrderDetail();

    Product1 product1 = getModel1Factory().createProduct1();
    orderDetail.setProduct(product1);

    String path = "/test";
    Resource resource = transaction.createResource(getResourcePath(path));
    resource.getContents().add(orderDetail);
    resource.getContents().add(product1);

    transaction.commit();

    CDOObject cdoObject = CDOUtil.getCDOObject(orderDetail);
    CDORevision cdoRevision1 = cdoObject.cdoRevision();

    int version1 = cdoRevision1.getVersion();

    orderDetail.setProduct(product1);

    transaction.commit();

    CDORevision cdoRevision2 = cdoObject.cdoRevision();
    int version2 = cdoRevision2.getVersion();
    assertEquals(version1, version2);
  }
}
