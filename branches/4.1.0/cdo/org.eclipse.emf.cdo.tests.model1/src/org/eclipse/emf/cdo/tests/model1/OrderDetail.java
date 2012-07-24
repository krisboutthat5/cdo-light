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
package org.eclipse.emf.cdo.tests.model1;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Order Detail</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.eclipse.emf.cdo.tests.model1.OrderDetail#getOrder <em>Order</em>}</li>
 * <li>{@link org.eclipse.emf.cdo.tests.model1.OrderDetail#getProduct <em>Product</em>}</li>
 * <li>{@link org.eclipse.emf.cdo.tests.model1.OrderDetail#getPrice <em>Price</em>}</li>
 * </ul>
 * </p>
 * 
 * @see org.eclipse.emf.cdo.tests.model1.Model1Package#getOrderDetail()
 * @model
 * @generated
 */
public interface OrderDetail extends EObject
{
  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  String copyright = "Copyright (c) 2004 - 2012 Eike Stepper (Berlin, Germany) and others.\r\nAll rights reserved. This program and the accompanying materials\r\nare made available under the terms of the Eclipse Public License v1.0\r\nwhich accompanies this distribution, and is available at\r\nhttp://www.eclipse.org/legal/epl-v10.html\r\n\r\nContributors:\r\n   Eike Stepper - initial API and implementation";

  /**
   * Returns the value of the '<em><b>Order</b></em>' container reference. It is bidirectional and its opposite is '
   * {@link org.eclipse.emf.cdo.tests.model1.Order#getOrderDetails <em>Order Details</em>}'. <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Order</em>' container reference isn't clear, there really should be more of a
   * description here...
   * </p>
   * <!-- end-user-doc -->
   * 
   * @return the value of the '<em>Order</em>' container reference.
   * @see #setOrder(Order)
   * @see org.eclipse.emf.cdo.tests.model1.Model1Package#getOrderDetail_Order()
   * @see org.eclipse.emf.cdo.tests.model1.Order#getOrderDetails
   * @model opposite="orderDetails" required="true" transient="false"
   * @generated
   */
  Order getOrder();

  /**
   * Sets the value of the '{@link org.eclipse.emf.cdo.tests.model1.OrderDetail#getOrder <em>Order</em>}' container
   * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @param value
   *          the new value of the '<em>Order</em>' container reference.
   * @see #getOrder()
   * @generated
   */
  void setOrder(Order value);

  /**
   * Returns the value of the '<em><b>Product</b></em>' reference. It is bidirectional and its opposite is '
   * {@link org.eclipse.emf.cdo.tests.model1.Product1#getOrderDetails <em>Order Details</em>}'. <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Product</em>' reference isn't clear, there really should be more of a description
   * here...
   * </p>
   * <!-- end-user-doc -->
   * 
   * @return the value of the '<em>Product</em>' reference.
   * @see #setProduct(Product1)
   * @see org.eclipse.emf.cdo.tests.model1.Model1Package#getOrderDetail_Product()
   * @see org.eclipse.emf.cdo.tests.model1.Product1#getOrderDetails
   * @model opposite="orderDetails"
   * @generated
   */
  Product1 getProduct();

  /**
   * Sets the value of the '{@link org.eclipse.emf.cdo.tests.model1.OrderDetail#getProduct <em>Product</em>}' reference.
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @param value
   *          the new value of the '<em>Product</em>' reference.
   * @see #getProduct()
   * @generated
   */
  void setProduct(Product1 value);

  /**
   * Returns the value of the '<em><b>Price</b></em>' attribute. <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Price</em>' attribute isn't clear, there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * 
   * @return the value of the '<em>Price</em>' attribute.
   * @see #setPrice(float)
   * @see org.eclipse.emf.cdo.tests.model1.Model1Package#getOrderDetail_Price()
   * @model
   * @generated
   */
  float getPrice();

  /**
   * Sets the value of the '{@link org.eclipse.emf.cdo.tests.model1.OrderDetail#getPrice <em>Price</em>}' attribute.
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @param value
   *          the new value of the '<em>Price</em>' attribute.
   * @see #getPrice()
   * @generated
   */
  void setPrice(float value);

} // OrderDetail
