/*
 * Copyright (c) 2004 - 2012 Eike Stepper (Berlin, Germany) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Stefan Winkler - initial API and implementation
 */
package org.eclipse.emf.cdo.tests.bugzilla;

import org.eclipse.emf.cdo.common.model.EMFUtil;
import org.eclipse.emf.cdo.eresource.CDOResource;
import org.eclipse.emf.cdo.session.CDOSession;
import org.eclipse.emf.cdo.tests.AbstractCDOTest;
import org.eclipse.emf.cdo.transaction.CDOTransaction;
import org.eclipse.emf.cdo.util.CDOUtil;
import org.eclipse.emf.cdo.util.CommitException;
import org.eclipse.emf.cdo.view.CDOView;

import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.util.EcoreUtil;

/**
 * @author Stefan Winkler
 */
public class Bugzilla_326743_Test extends AbstractCDOTest
{
  private EPackage pkg;

  private EClass cls;

  private EAttribute att;

  @Override
  public void setUp() throws Exception
  {
    super.setUp();

    pkg = EMFUtil.createEPackage("customTest", "ct", "http://cdo.emf.eclipse.org/customTest.ecore");

    EDataType customPrimitive = EcoreFactory.eINSTANCE.createEDataType();
    customPrimitive.setInstanceTypeName("boolean");
    customPrimitive.setName("CustomPrimitiveType");

    // added type annotation for teneo test case
    final EAnnotation eAnnotation = EcoreFactory.eINSTANCE.createEAnnotation();
    eAnnotation.setSource("teneo.jpa");
    final String typeAnnotation = "@Type(type=\"org.eclipse.emf.cdo.server.internal.hibernate.tuplizer.CDOCustomTypeUserType\")";
    eAnnotation.getDetails().put("value", typeAnnotation);
    customPrimitive.getEAnnotations().add(eAnnotation);

    pkg.getEClassifiers().add(customPrimitive);

    cls = EMFUtil.createEClass(pkg, "Foobar", false, false);
    att = EMFUtil.createEAttribute(cls, "attPrimitive", customPrimitive);

    CDOUtil.prepareDynamicEPackage(pkg);
  }

  public void testDefaultValue() throws CommitException
  {
    EObject obj = EcoreUtil.create(cls);

    {
      CDOSession session = openSession();
      session.getPackageRegistry().putEPackage(pkg);
      CDOTransaction tx = session.openTransaction();
      CDOResource res = tx.createResource(getResourcePath("/test"));
      res.getContents().add(obj);
      tx.commit();
      tx.close();
      session.close();
    }

    clearCache(getRepository().getRevisionManager());

    {
      CDOSession session = openSession();
      session.getPackageRegistry().putEPackage(pkg);
      CDOView v = session.openView();
      CDOResource res = v.getResource(getResourcePath("/test"));
      EObject persistent = res.getContents().get(0);

      boolean pCustom = (Boolean)persistent.eGet(att);
      assertEquals(false, pCustom);

      v.close();
      session.close();
    }
  }
}
