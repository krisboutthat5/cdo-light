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

import org.eclipse.emf.cdo.eresource.CDOResource;
import org.eclipse.emf.cdo.session.CDOSession;
import org.eclipse.emf.cdo.tests.AbstractCDOTest;
import org.eclipse.emf.cdo.tests.model4.ContainedElementNoOpposite;
import org.eclipse.emf.cdo.tests.model4.RefSingleContainedNPL;
import org.eclipse.emf.cdo.transaction.CDOTransaction;

/**
 * @author Martin Fluegge
 */
public class Bugzilla_320837_Test extends AbstractCDOTest
{
  public void testLoadContainedBeforeContainer() throws Exception
  {
    CDOSession session1 = openSession();
    CDOTransaction transaction1 = session1.openTransaction();

    CDOResource resource1 = transaction1.createResource(getResourcePath("/test"));
    RefSingleContainedNPL container1 = getModel4Factory().createRefSingleContainedNPL();
    ContainedElementNoOpposite contained1 = getModel4Factory().createContainedElementNoOpposite();
    container1.setElement(contained1);

    resource1.getContents().add(container1);
    transaction1.commit();

    CDOSession session2 = openSession();
    CDOTransaction transaction2 = session2.openTransaction();

    // Revieving the object should not result in an exception
    transaction2.getObject(contained1);
  }
}
