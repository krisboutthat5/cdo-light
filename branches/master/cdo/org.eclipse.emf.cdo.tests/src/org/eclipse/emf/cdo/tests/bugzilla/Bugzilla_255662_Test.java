/*
 * Copyright (c) 2004 - 2012 Eike Stepper (Berlin, Germany) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *    Simon McDuff - initial API and implementation
 *    Eike Stepper - maintenance
 */
package org.eclipse.emf.cdo.tests.bugzilla;

import org.eclipse.emf.cdo.eresource.CDOResource;
import org.eclipse.emf.cdo.session.CDOSession;
import org.eclipse.emf.cdo.tests.AbstractCDOTest;
import org.eclipse.emf.cdo.tests.model1.Customer;
import org.eclipse.emf.cdo.transaction.CDOTransaction;

/**
 * NPE in CDOResourceImpl.save()
 * <p>
 * See bug 255662
 * 
 * @author Simon McDuff
 */
public class Bugzilla_255662_Test extends AbstractCDOTest
{
  public void testBugzilla_255662() throws Exception
  {
    CDOSession session = openSession();
    CDOTransaction transaction = session.openTransaction();

    CDOResource resource = transaction.createResource(getResourcePath("/test1"));

    Customer customer = getModel1Factory().createCustomer();
    customer.setName("customer");

    resource.getContents().add(customer);
    resource.save(null);
    session.close();
  }
}
