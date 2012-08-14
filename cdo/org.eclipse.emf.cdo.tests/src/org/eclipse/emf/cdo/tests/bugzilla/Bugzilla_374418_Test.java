/*
 * Copyright (c) 2004 - 2012 Eike Stepper (Berlin, Germany) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Esteban Dugueperoux - initial API and implementation
 */
package org.eclipse.emf.cdo.tests.bugzilla;

import org.eclipse.emf.cdo.eresource.CDOResource;
import org.eclipse.emf.cdo.session.CDOSession;
import org.eclipse.emf.cdo.tests.AbstractCDOTest;
import org.eclipse.emf.cdo.tests.model6.BaseObject;
import org.eclipse.emf.cdo.tests.model6.Root;
import org.eclipse.emf.cdo.transaction.CDOTransaction;

/**
 * @author Esteban Dugueperoux
 */
public class Bugzilla_374418_Test extends AbstractCDOTest
{
  public void testControlUncontrol() throws Throwable
  {
    Root root = getModel6Factory().createRoot();
    BaseObject childRoot = getModel6Factory().createBaseObject();
    root.getListA().add(childRoot);

    CDOSession session = openSession();
    CDOTransaction transaction = session.openTransaction();
    CDOResource mainResource = transaction.createResource(getResourcePath("/mainResource.model1"));
    mainResource.getContents().add(root);
    transaction.commit();

    // Control childRoot to a new resource
    CDOResource fragmentResource = transaction.createResource(getResourcePath("/fragmentResource.model1"));
    fragmentResource.getContents().add(childRoot);
    transaction.commit();

    // Uncontrol category1 to its original resource
    fragmentResource.getContents().remove(childRoot);
    transaction.setSavepoint(); // <---- Problem here!!!
    transaction.commit();

    // Control again childRoot to the new resource
    fragmentResource.getContents().add(childRoot);
    transaction.commit();
  }
}
