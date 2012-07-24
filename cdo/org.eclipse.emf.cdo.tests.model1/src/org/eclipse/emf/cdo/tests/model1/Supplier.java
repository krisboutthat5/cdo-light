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

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Supplier</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.eclipse.emf.cdo.tests.model1.Supplier#getPurchaseOrders <em>Purchase Orders</em>}</li>
 * <li>{@link org.eclipse.emf.cdo.tests.model1.Supplier#isPreferred <em>Preferred</em>}</li>
 * </ul>
 * </p>
 * 
 * @see org.eclipse.emf.cdo.tests.model1.Model1Package#getSupplier()
 * @model
 * @generated
 */
public interface Supplier extends Address
{
  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  String copyright = "Copyright (c) 2004 - 2012 Eike Stepper (Berlin, Germany) and others.\r\nAll rights reserved. This program and the accompanying materials\r\nare made available under the terms of the Eclipse Public License v1.0\r\nwhich accompanies this distribution, and is available at\r\nhttp://www.eclipse.org/legal/epl-v10.html\r\n\r\nContributors:\r\n   Eike Stepper - initial API and implementation";

  /**
   * Returns the value of the '<em><b>Purchase Orders</b></em>' reference list. The list contents are of type
   * {@link org.eclipse.emf.cdo.tests.model1.PurchaseOrder}. It is bidirectional and its opposite is '
   * {@link org.eclipse.emf.cdo.tests.model1.PurchaseOrder#getSupplier <em>Supplier</em>}'. <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Purchase Orders</em>' reference list isn't clear, there really should be more of a
   * description here...
   * </p>
   * <!-- end-user-doc -->
   * 
   * @return the value of the '<em>Purchase Orders</em>' reference list.
   * @see org.eclipse.emf.cdo.tests.model1.Model1Package#getSupplier_PurchaseOrders()
   * @see org.eclipse.emf.cdo.tests.model1.PurchaseOrder#getSupplier
   * @model opposite="supplier"
   * @generated
   */
  EList<PurchaseOrder> getPurchaseOrders();

  /**
   * Returns the value of the '<em><b>Preferred</b></em>' attribute. The default value is <code>"true"</code>. <!--
   * begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Preferred</em>' attribute isn't clear, there really should be more of a description
   * here...
   * </p>
   * <!-- end-user-doc -->
   * 
   * @return the value of the '<em>Preferred</em>' attribute.
   * @see #setPreferred(boolean)
   * @see org.eclipse.emf.cdo.tests.model1.Model1Package#getSupplier_Preferred()
   * @model default="true"
   * @generated
   */
  boolean isPreferred();

  /**
   * Sets the value of the '{@link org.eclipse.emf.cdo.tests.model1.Supplier#isPreferred <em>Preferred</em>}' attribute.
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @param value
   *          the new value of the '<em>Preferred</em>' attribute.
   * @see #isPreferred()
   * @generated
   */
  void setPreferred(boolean value);

} // Supplier
