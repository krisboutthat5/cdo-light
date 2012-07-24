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

import org.eclipse.emf.cdo.common.id.CDOID;
import org.eclipse.emf.cdo.eresource.CDOResource;
import org.eclipse.emf.cdo.session.CDOSession;
import org.eclipse.emf.cdo.tests.AbstractCDOTest;
import org.eclipse.emf.cdo.tests.model1.Company;
import org.eclipse.emf.cdo.transaction.CDOTransaction;
import org.eclipse.emf.cdo.util.CDOUtil;

/**
 * NPE during Transaction.getObject(id, false)
 * <p>
 * See bug 243310
 * 
 * @author Eike Stepper
 */
public class Bugzilla_243310_Test extends AbstractCDOTest
{
  public void testBugzilla_243310() throws Exception
  {
    CDOSession session = openSession();

    CDOTransaction transaction1 = session.openTransaction();
    CDOResource res = transaction1.createResource(getResourcePath("/test1"));
    Company companyTx1 = getModel1Factory().createCompany();
    companyTx1.setName("Company ABC");
    res.getContents().add(companyTx1);
    transaction1.commit();

    boolean loadOnDemand = false;

    CDOTransaction transaction2 = session.openTransaction();
    CDOID id = CDOUtil.getCDOObject(companyTx1).cdoID();
    Company companyTx2 = (Company)transaction2.getObject(id, loadOnDemand);
    assertNull(companyTx2);
  }
}
