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

import org.eclipse.emf.cdo.common.model.EMFUtil;
import org.eclipse.emf.cdo.eresource.CDOResource;
import org.eclipse.emf.cdo.session.CDOSession;
import org.eclipse.emf.cdo.tests.AbstractCDOTest;
import org.eclipse.emf.cdo.tests.legacy.model1.Model1Package;
import org.eclipse.emf.cdo.tests.model1.VAT;
import org.eclipse.emf.cdo.transaction.CDOTransaction;
import org.eclipse.emf.cdo.util.CDOUtil;
import org.eclipse.emf.cdo.util.CommitException;
import org.eclipse.emf.cdo.view.CDOView;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.util.EcoreUtil;

/**
 * @author Eike Stepper
 */
public class Bugzilla_306998_Test extends AbstractCDOTest
{
  @CleanRepositoriesBefore
  public void testValidEENumLiteral() throws CommitException
  {
    EPackage pkg = EMFUtil.createEPackage("pkg", "pkg", "http://cdo.eclipse.org/Bugzilla_306998_Test_1.ecore");
    EClass cls = EMFUtil.createEClass(pkg, "cls", false, false);
    EAttribute att = EMFUtil.createEAttribute(cls, "att", Model1Package.eINSTANCE.getVAT());
    att.setDefaultValueLiteral("vat7");

    CDOUtil.prepareDynamicEPackage(pkg);

    {
      CDOSession session = openSession();
      session.getPackageRegistry().putEPackage(pkg);
      CDOTransaction tx = session.openTransaction();
      CDOResource res = tx.createResource(getResourcePath("/test"));

      EObject obj = EcoreUtil.create(cls);

      res.getContents().add(obj);
      tx.commit();
      tx.close();
      session.close();
    }
    clearCache(getRepository().getRevisionManager());
    {
      CDOSession session = openSession();
      session.getPackageRegistry().putEPackage(pkg);
      CDOView view = session.openView();
      CDOResource res = view.getResource(getResourcePath("/test"));

      EObject obj = res.getContents().get(0);
      assertEquals(7, ((VAT)obj.eGet(att)).getValue());

      view.close();
      session.close();
    }
  }

  // does not affect MemStore!
  @Skips("MEM")
  @CleanRepositoriesBefore
  public void testInalidEENumLiteral() throws CommitException
  {
    EPackage pkg = EMFUtil.createEPackage("pkg", "pkg", "http://cdo.eclipse.org/Bugzilla_306998_Test_1.ecore");
    EClass cls = EMFUtil.createEClass(pkg, "cls", false, false);
    EAttribute att = EMFUtil.createEAttribute(cls, "att", Model1Package.eINSTANCE.getVAT());
    att.setDefaultValueLiteral("vat8");
    att.setDefaultValue(Model1Package.eINSTANCE.getVAT().getEEnumLiteral("vat8"));

    CDOUtil.prepareDynamicEPackage(pkg);

    {
      CDOSession session = openSession();
      session.getPackageRegistry().putEPackage(pkg);
      CDOTransaction tx = session.openTransaction();
      CDOResource res = tx.createResource(getResourcePath("/test"));

      EObject obj = EcoreUtil.create(cls);

      res.getContents().add(obj);
      tx.commit();
      tx.close();
      session.close();
    }

    clearCache(getRepository().getRevisionManager());

    {
      CDOSession session = openSession();
      session.getPackageRegistry().putEPackage(pkg);
      CDOView view = session.openView();
      CDOResource res = view.getResource(getResourcePath("/test"));

      EObject obj = res.getContents().get(0);
      assertEquals(0, ((VAT)obj.eGet(att)).getValue());

      view.close();
      session.close();
    }
  }
}
