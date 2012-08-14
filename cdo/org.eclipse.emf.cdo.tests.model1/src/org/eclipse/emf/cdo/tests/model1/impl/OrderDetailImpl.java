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
package org.eclipse.emf.cdo.tests.model1.impl;

import org.eclipse.emf.cdo.tests.model1.Model1Package;
import org.eclipse.emf.cdo.tests.model1.Order;
import org.eclipse.emf.cdo.tests.model1.OrderDetail;
import org.eclipse.emf.cdo.tests.model1.Product1;

import org.eclipse.emf.internal.cdo.CDOObjectImpl;

import org.eclipse.emf.ecore.EClass;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Order Detail</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link org.eclipse.emf.cdo.tests.model1.impl.OrderDetailImpl#getOrder <em>Order</em>}</li>
 * <li>{@link org.eclipse.emf.cdo.tests.model1.impl.OrderDetailImpl#getProduct <em>Product</em>}</li>
 * <li>{@link org.eclipse.emf.cdo.tests.model1.impl.OrderDetailImpl#getPrice <em>Price</em>}</li>
 * </ul>
 * </p>
 * 
 * @generated
 */
public class OrderDetailImpl extends CDOObjectImpl implements OrderDetail
{
  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  public static final String copyright = "Copyright (c) 2004 - 2012 Eike Stepper (Berlin, Germany) and others.\r\nAll rights reserved. This program and the accompanying materials\r\nare made available under the terms of the Eclipse Public License v1.0\r\nwhich accompanies this distribution, and is available at\r\nhttp://www.eclipse.org/legal/epl-v10.html\r\n\r\nContributors:\r\n   Eike Stepper - initial API and implementation";

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  protected OrderDetailImpl()
  {
    super();
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  @Override
  protected EClass eStaticClass()
  {
    return Model1Package.eINSTANCE.getOrderDetail();
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  @Override
  protected int eStaticFeatureCount()
  {
    return 0;
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  public Order getOrder()
  {
    return (Order)eGet(Model1Package.eINSTANCE.getOrderDetail_Order(), true);
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  public void setOrder(Order newOrder)
  {
    eSet(Model1Package.eINSTANCE.getOrderDetail_Order(), newOrder);
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  public Product1 getProduct()
  {
    return (Product1)eGet(Model1Package.eINSTANCE.getOrderDetail_Product(), true);
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  public void setProduct(Product1 newProduct)
  {
    eSet(Model1Package.eINSTANCE.getOrderDetail_Product(), newProduct);
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  public float getPrice()
  {
    return (Float)eGet(Model1Package.eINSTANCE.getOrderDetail_Price(), true);
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  public void setPrice(float newPrice)
  {
    eSet(Model1Package.eINSTANCE.getOrderDetail_Price(), newPrice);
  }

} // OrderDetailImpl
