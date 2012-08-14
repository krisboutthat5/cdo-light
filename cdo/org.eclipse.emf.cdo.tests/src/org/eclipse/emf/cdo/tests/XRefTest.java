/*
 * Copyright (c) 2004 - 2012 Eike Stepper (Berlin, Germany) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Eike Stepper - initial API and implementation
 *    Stefan Winkler - additional tests
 */
package org.eclipse.emf.cdo.tests;

import org.eclipse.emf.cdo.CDOObject;
import org.eclipse.emf.cdo.CDOObjectReference;
import org.eclipse.emf.cdo.common.model.EMFUtil;
import org.eclipse.emf.cdo.eresource.CDOResource;
import org.eclipse.emf.cdo.session.CDOSession;
import org.eclipse.emf.cdo.tests.model1.PurchaseOrder;
import org.eclipse.emf.cdo.tests.model1.Supplier;
import org.eclipse.emf.cdo.transaction.CDOTransaction;
import org.eclipse.emf.cdo.util.CDOUtil;
import org.eclipse.emf.cdo.view.CDOView;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.util.EcoreUtil;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * 300149: Support remote cross referencing with a convenient API on the client and SPI on the server for the stores to
 * implement https://bugs.eclipse.org/bugs/show_bug.cgi?id=300149
 * 
 * @author Eike Stepper
 * @since 4.0
 */
public class XRefTest extends AbstractCDOTest
{
  @Override
  protected void doSetUp() throws Exception
  {
    super.doSetUp();
    skipStoreWithoutQueryXRefs();
  }

  public void testCrossReferenceMultivalueEReferenceQuery() throws Exception
  {
    PurchaseOrder purchaseOrder1 = getModel1Factory().createPurchaseOrder();
    PurchaseOrder purchaseOrder2 = getModel1Factory().createPurchaseOrder();
    PurchaseOrder purchaseOrder3 = getModel1Factory().createPurchaseOrder();
    PurchaseOrder purchaseOrder4 = getModel1Factory().createPurchaseOrder();

    Supplier supplier = getModel1Factory().createSupplier();
    supplier.getPurchaseOrders().add(purchaseOrder1);
    supplier.getPurchaseOrders().add(purchaseOrder2);
    supplier.getPurchaseOrders().add(purchaseOrder3);
    supplier.getPurchaseOrders().add(purchaseOrder4);

    CDOSession session1 = openSession();
    CDOTransaction transaction = session1.openTransaction();

    CDOResource resource = transaction.createResource(getResourcePath("/test1"));
    resource.getContents().add(supplier);
    resource.getContents().add(purchaseOrder1);
    resource.getContents().add(purchaseOrder2);
    resource.getContents().add(purchaseOrder3);
    resource.getContents().add(purchaseOrder4);

    transaction.commit();

    /******************/

    CDOSession session2 = openSession();
    CDOView view = session2.openView();

    List<CDOObjectReference> results = view.queryXRefs(Collections.singleton(CDOUtil.getCDOObject(supplier)));
    assertEquals(4, results.size());

    for (CDOObjectReference result : results)
    {
      CDOObject sourceObject = result.getSourceObject();
      assertInstanceOf(PurchaseOrder.class, CDOUtil.getEObject(sourceObject));
    }
  }

  public void testLocallyDetachedObject() throws Exception
  {
    PurchaseOrder purchaseOrder1 = getModel1Factory().createPurchaseOrder();
    PurchaseOrder purchaseOrder2 = getModel1Factory().createPurchaseOrder();
    PurchaseOrder purchaseOrder3 = getModel1Factory().createPurchaseOrder();
    PurchaseOrder purchaseOrder4 = getModel1Factory().createPurchaseOrder();

    Supplier supplier = getModel1Factory().createSupplier();
    supplier.getPurchaseOrders().add(purchaseOrder1);
    supplier.getPurchaseOrders().add(purchaseOrder2);
    supplier.getPurchaseOrders().add(purchaseOrder3);
    supplier.getPurchaseOrders().add(purchaseOrder4);

    CDOSession session1 = openSession();
    CDOTransaction transaction1 = session1.openTransaction();

    CDOResource resource = transaction1.createResource(getResourcePath("/test1"));
    resource.getContents().add(supplier);
    resource.getContents().add(purchaseOrder1);
    resource.getContents().add(purchaseOrder2);
    resource.getContents().add(purchaseOrder3);
    resource.getContents().add(purchaseOrder4);

    transaction1.commit();

    /******************/

    CDOSession session2 = openSession();
    CDOTransaction transaction2 = session2.openTransaction();
    supplier = (Supplier)transaction2.getResource(getResourcePath("/test1")).getContents().remove(0);

    List<CDOObjectReference> results = transaction2.queryXRefs(Collections.singleton(CDOUtil.getCDOObject(supplier)));
    assertEquals(4, results.size());

    for (CDOObjectReference result : results)
    {
      CDOObject sourceObject = result.getSourceObject();
      assertInstanceOf(PurchaseOrder.class, CDOUtil.getEObject(sourceObject));
    }
  }

  @SuppressWarnings("unchecked")
  public void testXRefsToMany() throws Exception
  {
    // create model
    EPackage pkg = EMFUtil.createEPackage("xreftest", "xreftest",
        "http://cdo.emf.eclipse.org/TestModels/XRefTestToMany.ecore");
    EClass cls1 = EMFUtil.createEClass(pkg, "referencer", false, false);
    EAttribute id1 = EMFUtil.createEAttribute(cls1, "id", EcorePackage.eINSTANCE.getEInt());

    EClass cls2 = EMFUtil.createEClass(pkg, "referencee", false, false);
    EAttribute id2 = EMFUtil.createEAttribute(cls2, "id", EcorePackage.eINSTANCE.getEInt());

    EReference ref = EMFUtil.createEReference(cls1, "ref", cls2, false, true);

    if (!isConfig(LEGACY))
    {
      CDOUtil.prepareDynamicEPackage(pkg);
    }

    // create instances
    EObject obj2_1 = EcoreUtil.create(cls2);
    obj2_1.eSet(id2, 11);
    EObject obj2_2 = EcoreUtil.create(cls2);
    obj2_2.eSet(id2, 12);
    EObject obj2_3 = EcoreUtil.create(cls2);
    obj2_3.eSet(id2, 13);
    EObject obj2_4 = EcoreUtil.create(cls2);
    obj2_4.eSet(id2, 14);

    EObject obj1_1 = EcoreUtil.create(cls1);
    obj1_1.eSet(id1, 1);
    EObject obj1_2 = EcoreUtil.create(cls1);
    obj1_2.eSet(id1, 2);
    EObject obj1_3 = EcoreUtil.create(cls1);
    obj1_3.eSet(id1, 3);
    EObject obj1_4 = EcoreUtil.create(cls1);
    obj1_4.eSet(id1, 4);

    // configure references
    ((EList<EObject>)obj1_1.eGet(ref)).add(obj2_2);
    ((EList<EObject>)obj1_1.eGet(ref)).add(obj2_3);

    ((EList<EObject>)obj1_2.eGet(ref)).add(obj2_2);

    ((EList<EObject>)obj1_3.eGet(ref)).add(obj2_1);
    ((EList<EObject>)obj1_3.eGet(ref)).add(obj2_2);

    // store objects
    CDOSession session1 = openSession();
    CDOTransaction transaction = session1.openTransaction();
    CDOResource resource = transaction.createResource(getResourcePath("/test1"));
    resource.getContents().addAll(Arrays.asList(obj1_1, obj1_2, obj1_3, obj1_4, obj2_1, obj2_2, obj2_3, obj2_4));
    transaction.commit();
    transaction.close();
    session1.close();

    // check XRefs
    CDOSession session2 = openSession();
    CDOView view = session2.openView();

    List<CDOObjectReference> results = view.queryXRefs(Collections.singleton(CDOUtil.getCDOObject(obj2_1)));
    assertEquals(1, results.size());

    {
      CDOObjectReference result = results.get(0);
      assertEquals(0, result.getSourceIndex());
      // XXX fails!
      // assertEquals(ref, result.getSourceReference());
      CDOObject sourceObject = results.get(0).getSourceObject();
      assertEquals(3, sourceObject.eGet(sourceObject.eClass().getEStructuralFeature("id")));
    }

    results = view.queryXRefs(Collections.singleton(CDOUtil.getCDOObject(obj2_3)));
    assertEquals(1, results.size());
    {
      CDOObjectReference result = results.get(0);
      assertEquals(1, result.getSourceIndex());
      assertEquals(ref.getName(), result.getSourceFeature().getName());
      CDOObject sourceObject = results.get(0).getSourceObject();
      assertEquals(1, sourceObject.eGet(sourceObject.eClass().getEStructuralFeature("id")));
    }

    results = view.queryXRefs(Collections.singleton(CDOUtil.getCDOObject(obj2_2)));
    assertEquals(3, results.size());

    boolean found1 = false;
    boolean found2 = false;
    boolean found3 = false;

    for (CDOObjectReference result : results)
    {
      assertEquals(ref.getName(), results.get(0).getSourceFeature().getName());

      CDOObject sourceObject = result.getSourceObject();
      Integer id = (Integer)sourceObject.eGet(sourceObject.eClass().getEStructuralFeature("id"));
      switch (id.intValue())
      {
      case 1:
        assertEquals(0, result.getSourceIndex());
        found1 = true;
        break;
      case 2:
        assertEquals(0, result.getSourceIndex());
        found2 = true;
        break;
      case 3:
        assertEquals(1, result.getSourceIndex());
        found3 = true;
        break;
      default:
        // must not happen!
        assertEquals(true, false);
      }
    }

    assertEquals(true, found1 && found2 && found3);

    view.close();
    session2.close();
  }

  @CleanRepositoriesBefore
  public void testXRefsToOne() throws Exception
  {
    // create model
    EPackage pkg = EMFUtil.createEPackage("xreftest", "xreftest",
        "http://cdo.emf.eclipse.org/TestModels/XRefTestToOne.ecore");
    EClass cls1 = EMFUtil.createEClass(pkg, "referencer", false, false);
    EAttribute id1 = EMFUtil.createEAttribute(cls1, "id", EcorePackage.eINSTANCE.getEInt());

    EClass cls2 = EMFUtil.createEClass(pkg, "referencee", false, false);
    EAttribute id2 = EMFUtil.createEAttribute(cls2, "id", EcorePackage.eINSTANCE.getEInt());

    EReference ref = EMFUtil.createEReference(cls1, "ref", cls2, false, false);

    if (!isConfig(LEGACY))
    {
      CDOUtil.prepareDynamicEPackage(pkg);
    }

    // create instances
    EObject obj2_1 = EcoreUtil.create(cls2);
    obj2_1.eSet(id2, 11);
    EObject obj2_2 = EcoreUtil.create(cls2);
    obj2_2.eSet(id2, 12);
    EObject obj2_3 = EcoreUtil.create(cls2);
    obj2_3.eSet(id2, 13);
    EObject obj2_4 = EcoreUtil.create(cls2);
    obj2_4.eSet(id2, 14);

    EObject obj1_1 = EcoreUtil.create(cls1);
    obj1_1.eSet(id1, 1);
    EObject obj1_2 = EcoreUtil.create(cls1);
    obj1_2.eSet(id1, 2);
    EObject obj1_3 = EcoreUtil.create(cls1);
    obj1_3.eSet(id1, 3);
    EObject obj1_4 = EcoreUtil.create(cls1);
    obj1_4.eSet(id1, 4);

    // configure references
    obj1_1.eSet(ref, obj2_2);
    obj1_2.eSet(ref, obj2_2);
    obj1_3.eSet(ref, obj2_3);

    // store objects
    CDOSession session1 = openSession();
    CDOTransaction transaction = session1.openTransaction();
    CDOResource resource = transaction.createResource(getResourcePath("/test1"));
    resource.getContents().addAll(Arrays.asList(obj1_1, obj1_2, obj1_3, obj1_4, obj2_1, obj2_2, obj2_3, obj2_4));
    transaction.commit();
    transaction.close();
    session1.close();

    // check XRefs
    CDOSession session2 = openSession();
    CDOView view = session2.openView();

    List<CDOObjectReference> results = view.queryXRefs(Collections.singleton(CDOUtil.getCDOObject(obj2_1)));
    assertEquals(true, results.isEmpty());

    results = view.queryXRefs(Collections.singleton(CDOUtil.getCDOObject(obj2_3)));

    assertEquals(1, results.size());
    {
      CDOObjectReference result = results.get(0);
      assertEquals(0, result.getSourceIndex());

      assertEquals(ref.getName(), result.getSourceFeature().getName());
      CDOObject sourceObject = results.get(0).getSourceObject();
      assertEquals(3, sourceObject.eGet(sourceObject.eClass().getEStructuralFeature("id")));
    }

    results = view.queryXRefs(Collections.singleton(CDOUtil.getCDOObject(obj2_2)));
    assertEquals(2, results.size());

    boolean found1 = false;
    boolean found2 = false;

    for (CDOObjectReference result : results)
    {
      assertEquals(ref.getName(), results.get(0).getSourceFeature().getName());

      CDOObject sourceObject = result.getSourceObject();
      Integer id = (Integer)sourceObject.eGet(sourceObject.eClass().getEStructuralFeature("id"));
      assertEquals(true, id == 1 || id == 2);
      assertEquals(0, result.getSourceIndex());

      if (id == 1)
      {
        found1 = true;
      }
      else if (id == 2)
      {
        found2 = true;
      }
    }

    assertEquals(true, found1 && found2);

    view.close();
    session2.close();
  }

  @SuppressWarnings({ "unchecked", "unused" })
  public void testSpecificReferences0() throws Exception
  {
    EClass a = EcoreFactory.eINSTANCE.createEClass();
    a.setName("A");

    EClass b = EcoreFactory.eINSTANCE.createEClass();
    b.setName("B");

    EReference aa1 = addReference(a, a, true);
    EReference aa2 = addReference(a, a, true);
    EReference aa3 = addReference(a, a, false);
    EReference aa4 = addReference(a, a, false);
    EReference ab5 = addReference(a, b, true);
    EReference ab6 = addReference(a, b, true);
    EReference ab7 = addReference(a, b, false);
    EReference ab8 = addReference(a, b, false);

    EReference ba1 = addReference(b, a, true);
    EReference ba2 = addReference(b, a, true);
    EReference ba3 = addReference(b, a, false);
    EReference ba4 = addReference(b, a, false);
    EReference bb5 = addReference(b, b, true);
    EReference bb6 = addReference(b, b, true);
    EReference bb7 = addReference(b, b, false);
    EReference bb8 = addReference(b, b, false);

    EPackage xref = EcoreFactory.eINSTANCE.createEPackage();
    xref.setName("xref");
    xref.setNsPrefix("xref");
    xref.setNsURI("http://xref");
    xref.getEClassifiers().add(a);
    xref.getEClassifiers().add(b);

    CDOSession session = openSession();
    session.getPackageRegistry().putEPackage(xref);

    CDOTransaction transaction = session.openTransaction();
    CDOResource resource = transaction.createResource(getResourcePath("/test1"));

    EObject a1 = addObject(resource, a);
    EObject a2 = addObject(resource, a);
    EObject a3 = addObject(resource, a);
    EObject a4 = addObject(resource, a);

    EObject b1 = addObject(resource, b);
    EObject b2 = addObject(resource, b);
    EObject b3 = addObject(resource, b);
    EObject b4 = addObject(resource, b);

    ((EList<EObject>)a1.eGet(ab5)).add(b1);
    ((EList<EObject>)a1.eGet(ab5)).add(b2);
    ((EList<EObject>)a1.eGet(ab5)).add(b3);
    ((EList<EObject>)a1.eGet(ab5)).add(b4);

    ((EList<EObject>)a1.eGet(ab6)).add(b1);
    ((EList<EObject>)a1.eGet(ab6)).add(b2);
    ((EList<EObject>)a1.eGet(ab6)).add(b3);
    ((EList<EObject>)a1.eGet(ab6)).add(b4);

    transaction.commit();

    /******************/

    List<CDOObjectReference> results = transaction.queryXRefs(CDOUtil.getCDOObject(b1));
    assertEquals(2, results.size());

    assertEquals(0, results.get(0).getSourceIndex());
    assertEquals(ab5, results.get(0).getSourceFeature());
    assertEquals(a1, results.get(0).getSourceObject());
    assertEquals(b1, results.get(0).getTargetObject());

    assertEquals(0, results.get(1).getSourceIndex());
    assertEquals(ab6, results.get(1).getSourceFeature());
    assertEquals(a1, results.get(1).getSourceObject());
    assertEquals(b1, results.get(1).getTargetObject());
  }

  @SuppressWarnings({ "unchecked", "unused" })
  public void testSpecificReferences1() throws Exception
  {
    EClass a = EcoreFactory.eINSTANCE.createEClass();
    a.setName("A");

    EClass b = EcoreFactory.eINSTANCE.createEClass();
    b.setName("B");

    EReference aa1 = addReference(a, a, true);
    EReference aa2 = addReference(a, a, true);
    EReference aa3 = addReference(a, a, false);
    EReference aa4 = addReference(a, a, false);
    EReference ab5 = addReference(a, b, true);
    EReference ab6 = addReference(a, b, true);
    EReference ab7 = addReference(a, b, false);
    EReference ab8 = addReference(a, b, false);

    EReference ba1 = addReference(b, a, true);
    EReference ba2 = addReference(b, a, true);
    EReference ba3 = addReference(b, a, false);
    EReference ba4 = addReference(b, a, false);
    EReference bb5 = addReference(b, b, true);
    EReference bb6 = addReference(b, b, true);
    EReference bb7 = addReference(b, b, false);
    EReference bb8 = addReference(b, b, false);

    EPackage xref = EcoreFactory.eINSTANCE.createEPackage();
    xref.setName("xref");
    xref.setNsPrefix("xref");
    xref.setNsURI("http://xref");
    xref.getEClassifiers().add(a);
    xref.getEClassifiers().add(b);

    CDOSession session = openSession();
    session.getPackageRegistry().putEPackage(xref);

    CDOTransaction transaction = session.openTransaction();
    CDOResource resource = transaction.createResource(getResourcePath("/test1"));

    EObject a1 = addObject(resource, a);
    EObject a2 = addObject(resource, a);
    EObject a3 = addObject(resource, a);
    EObject a4 = addObject(resource, a);

    EObject b1 = addObject(resource, b);
    EObject b2 = addObject(resource, b);
    EObject b3 = addObject(resource, b);
    EObject b4 = addObject(resource, b);

    ((EList<EObject>)a1.eGet(ab5)).add(b1);
    ((EList<EObject>)a1.eGet(ab5)).add(b2);
    ((EList<EObject>)a1.eGet(ab5)).add(b3);
    ((EList<EObject>)a1.eGet(ab5)).add(b4);

    ((EList<EObject>)a1.eGet(ab6)).add(b1);
    ((EList<EObject>)a1.eGet(ab6)).add(b2);
    ((EList<EObject>)a1.eGet(ab6)).add(b3);
    ((EList<EObject>)a1.eGet(ab6)).add(b4);

    transaction.commit();

    /******************/

    List<CDOObjectReference> results = transaction.queryXRefs(CDOUtil.getCDOObject(b1), ab5);
    assertEquals(1, results.size());

    assertEquals(0, results.get(0).getSourceIndex());
    assertEquals(ab5, results.get(0).getSourceFeature());
    assertEquals(a1, results.get(0).getSourceObject());
    assertEquals(b1, results.get(0).getTargetObject());
  }

  @SuppressWarnings({ "unchecked", "unused" })
  public void testSpecificReferences2() throws Exception
  {
    EClass a = EcoreFactory.eINSTANCE.createEClass();
    a.setName("A");

    EClass b = EcoreFactory.eINSTANCE.createEClass();
    b.setName("B");

    EReference aa1 = addReference(a, a, true);
    EReference aa2 = addReference(a, a, true);
    EReference aa3 = addReference(a, a, false);
    EReference aa4 = addReference(a, a, false);
    EReference ab5 = addReference(a, b, true);
    EReference ab6 = addReference(a, b, true);
    EReference ab7 = addReference(a, b, false);
    EReference ab8 = addReference(a, b, false);

    EReference ba1 = addReference(b, a, true);
    EReference ba2 = addReference(b, a, true);
    EReference ba3 = addReference(b, a, false);
    EReference ba4 = addReference(b, a, false);
    EReference bb5 = addReference(b, b, true);
    EReference bb6 = addReference(b, b, true);
    EReference bb7 = addReference(b, b, false);
    EReference bb8 = addReference(b, b, false);

    EPackage xref = EcoreFactory.eINSTANCE.createEPackage();
    xref.setName("xref");
    xref.setNsPrefix("xref");
    xref.setNsURI("http://xref");
    xref.getEClassifiers().add(a);
    xref.getEClassifiers().add(b);

    CDOSession session = openSession();
    session.getPackageRegistry().putEPackage(xref);

    CDOTransaction transaction = session.openTransaction();
    CDOResource resource = transaction.createResource(getResourcePath("/test1"));

    EObject a1 = addObject(resource, a);
    EObject a2 = addObject(resource, a);
    EObject a3 = addObject(resource, a);
    EObject a4 = addObject(resource, a);

    EObject b1 = addObject(resource, b);
    EObject b2 = addObject(resource, b);
    EObject b3 = addObject(resource, b);
    EObject b4 = addObject(resource, b);

    ((EList<EObject>)a1.eGet(ab5)).add(b1);
    ((EList<EObject>)a1.eGet(ab5)).add(b2);
    ((EList<EObject>)a1.eGet(ab5)).add(b3);
    ((EList<EObject>)a1.eGet(ab5)).add(b4);

    ((EList<EObject>)a1.eGet(ab6)).add(b1);
    ((EList<EObject>)a1.eGet(ab6)).add(b2);
    ((EList<EObject>)a1.eGet(ab6)).add(b3);
    ((EList<EObject>)a1.eGet(ab6)).add(b4);

    transaction.commit();

    /******************/

    List<CDOObjectReference> results = transaction.queryXRefs(CDOUtil.getCDOObject(b1), ab6);
    assertEquals(1, results.size());

    assertEquals(0, results.get(0).getSourceIndex());
    assertEquals(ab6, results.get(0).getSourceFeature());
    assertEquals(a1, results.get(0).getSourceObject());
    assertEquals(b1, results.get(0).getTargetObject());
  }

  private EObject addObject(CDOResource resource, EClass eClass)
  {
    EObject b8 = EcoreUtil.create(eClass);
    resource.getContents().add(b8);
    return b8;
  }

  private EReference addReference(EClass source, EClass target, boolean many)
  {
    EReference reference = EcoreFactory.eINSTANCE.createEReference();
    EList<EStructuralFeature> features = source.getEStructuralFeatures();
    features.add(reference);

    reference.setName(target.getName().toLowerCase() + features.size());
    reference.setEType(target);
    reference.setUpperBound(many ? -1 : 1);
    return reference;
  }
}
