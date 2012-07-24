/*
 * Copyright (c) 2004 - 2012 Eike Stepper (Berlin, Germany) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Martin Fluegge - initial API and implementation
 */
package org.eclipse.emf.cdo.tests.bugzilla;

import org.eclipse.emf.cdo.eresource.CDOResource;
import org.eclipse.emf.cdo.eresource.EresourcePackage;
import org.eclipse.emf.cdo.session.CDOSession;
import org.eclipse.emf.cdo.tests.AbstractCDOTest;
import org.eclipse.emf.cdo.tests.mango.MangoValue;
import org.eclipse.emf.cdo.transaction.CDOTransaction;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;

/**
 * @author Martin Fluegge
 */
public class Bugzilla_314186_Test extends AbstractCDOTest
{
  public void testEMFCompliance() throws Exception
  {
    CDOSession session = openSession();
    ResourceSet resourceSet = new ResourceSetImpl();

    CDOTransaction transaction = session.openTransaction(resourceSet);
    CDOResource resource = transaction.createResource(getResourcePath("/resource"));

    resource.eAdapters().add(new AdapterImpl()
    {
      @Override
      public void notifyChanged(Notification notification)
      {
        if (notification.getFeature() != null
            && notification.getFeature().equals(EresourcePackage.eINSTANCE.getCDOResource_ResourceSet()))
        {
          assertEquals(Resource.RESOURCE__RESOURCE_SET, notification.getFeatureID(Resource.class));
        }
      }
    });

    MangoValue mangoValue = getMangoFactory().createMangoValue();
    resource.getContents().add(mangoValue);

    transaction.commit();
    session.close();
  }
}
