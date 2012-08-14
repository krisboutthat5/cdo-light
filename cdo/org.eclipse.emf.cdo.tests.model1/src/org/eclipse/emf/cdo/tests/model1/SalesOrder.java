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

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Sales Order</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.eclipse.emf.cdo.tests.model1.SalesOrder#getId <em>Id</em>}</li>
 * <li>{@link org.eclipse.emf.cdo.tests.model1.SalesOrder#getCustomer <em>Customer</em>}</li>
 * </ul>
 * </p>
 * 
 * @see org.eclipse.emf.cdo.tests.model1.Model1Package#getSalesOrder()
 * @model
 * @generated
 */
public interface SalesOrder extends Order
{
  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  String copyright = "Copyright (c) 2004 - 2012 Eike Stepper (Berlin, Germany) and others.\r\nAll rights reserved. This program and the accompanying materials\r\nare made available under the terms of the Eclipse Public License v1.0\r\nwhich accompanies this distribution, and is available at\r\nhttp://www.eclipse.org/legal/epl-v10.html\r\n\r\nContributors:\r\n   Eike Stepper - initial API and implementation";

  /**
   * Returns the value of the '<em><b>Id</b></em>' attribute. <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Id</em>' attribute isn't clear, there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * 
   * @return the value of the '<em>Id</em>' attribute.
   * @see #setId(int)
   * @see org.eclipse.emf.cdo.tests.model1.Model1Package#getSalesOrder_Id()
   * @model
   * @generated
   */
  int getId();

  /**
   * Sets the value of the '{@link org.eclipse.emf.cdo.tests.model1.SalesOrder#getId <em>Id</em>}' attribute. <!--
   * begin-user-doc --> <!-- end-user-doc -->
   * 
   * @param value
   *          the new value of the '<em>Id</em>' attribute.
   * @see #getId()
   * @generated
   */
  void setId(int value);

  /**
   * Returns the value of the '<em><b>Customer</b></em>' reference. It is bidirectional and its opposite is '
   * {@link org.eclipse.emf.cdo.tests.model1.Customer#getSalesOrders <em>Sales Orders</em>}'. <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Customer</em>' reference isn't clear, there really should be more of a description
   * here...
   * </p>
   * <!-- end-user-doc -->
   * 
   * @return the value of the '<em>Customer</em>' reference.
   * @see #setCustomer(Customer)
   * @see org.eclipse.emf.cdo.tests.model1.Model1Package#getSalesOrder_Customer()
   * @see org.eclipse.emf.cdo.tests.model1.Customer#getSalesOrders
   * @model opposite="salesOrders" required="true"
   * @generated
   */
  Customer getCustomer();

  /**
   * Sets the value of the '{@link org.eclipse.emf.cdo.tests.model1.SalesOrder#getCustomer <em>Customer</em>}'
   * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @param value
   *          the new value of the '<em>Customer</em>' reference.
   * @see #getCustomer()
   * @generated
   */
  void setCustomer(Customer value);

} // SalesOrder
