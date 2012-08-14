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
package org.eclipse.emf.cdo.tests.legacy.model1.impl;

import org.eclipse.emf.cdo.tests.legacy.model1.Model1Package;
import org.eclipse.emf.cdo.tests.model1.Customer;
import org.eclipse.emf.cdo.tests.model1.SalesOrder;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Sales Order</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link org.eclipse.emf.cdo.tests.legacy.model1.impl.SalesOrderImpl#getId <em>Id</em>}</li>
 * <li>{@link org.eclipse.emf.cdo.tests.legacy.model1.impl.SalesOrderImpl#getCustomer <em>Customer</em>}</li>
 * </ul>
 * </p>
 * 
 * @generated
 */
public class SalesOrderImpl extends OrderImpl implements SalesOrder
{
  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  public static final String copyright = "Copyright (c) 2004 - 2012 Eike Stepper (Berlin, Germany) and others.\r\nAll rights reserved. This program and the accompanying materials\r\nare made available under the terms of the Eclipse Public License v1.0\r\nwhich accompanies this distribution, and is available at\r\nhttp://www.eclipse.org/legal/epl-v10.html\r\n\r\nContributors:\r\n   Eike Stepper - initial API and implementation";

  /**
   * The default value of the '{@link #getId() <em>Id</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @see #getId()
   * @generated
   * @ordered
   */
  protected static final int ID_EDEFAULT = 0;

  /**
   * The cached value of the '{@link #getId() <em>Id</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @see #getId()
   * @generated
   * @ordered
   */
  protected int id = ID_EDEFAULT;

  /**
   * The cached value of the '{@link #getCustomer() <em>Customer</em>}' reference. <!-- begin-user-doc --> <!--
   * end-user-doc -->
   * 
   * @see #getCustomer()
   * @generated
   * @ordered
   */
  protected Customer customer;

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  protected SalesOrderImpl()
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
    return Model1Package.eINSTANCE.getSalesOrder();
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  public int getId()
  {
    return id;
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  public void setId(int newId)
  {
    int oldId = id;
    id = newId;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, Model1Package.SALES_ORDER__ID, oldId, id));
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  public Customer getCustomer()
  {
    if (customer != null && customer.eIsProxy())
    {
      InternalEObject oldCustomer = (InternalEObject)customer;
      customer = (Customer)eResolveProxy(oldCustomer);
      if (customer != oldCustomer)
      {
        if (eNotificationRequired())
          eNotify(new ENotificationImpl(this, Notification.RESOLVE, Model1Package.SALES_ORDER__CUSTOMER, oldCustomer,
              customer));
      }
    }
    return customer;
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  public Customer basicGetCustomer()
  {
    return customer;
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  public NotificationChain basicSetCustomer(Customer newCustomer, NotificationChain msgs)
  {
    Customer oldCustomer = customer;
    customer = newCustomer;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
          Model1Package.SALES_ORDER__CUSTOMER, oldCustomer, newCustomer);
      if (msgs == null)
        msgs = notification;
      else
        msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  public void setCustomer(Customer newCustomer)
  {
    if (newCustomer != customer)
    {
      NotificationChain msgs = null;
      if (customer != null)
        msgs = ((InternalEObject)customer).eInverseRemove(this, Model1Package.CUSTOMER__SALES_ORDERS, Customer.class,
            msgs);
      if (newCustomer != null)
        msgs = ((InternalEObject)newCustomer).eInverseAdd(this, Model1Package.CUSTOMER__SALES_ORDERS, Customer.class,
            msgs);
      msgs = basicSetCustomer(newCustomer, msgs);
      if (msgs != null)
        msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, Model1Package.SALES_ORDER__CUSTOMER, newCustomer,
          newCustomer));
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  @Override
  public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs)
  {
    switch (featureID)
    {
    case Model1Package.SALES_ORDER__CUSTOMER:
      if (customer != null)
        msgs = ((InternalEObject)customer).eInverseRemove(this, Model1Package.CUSTOMER__SALES_ORDERS, Customer.class,
            msgs);
      return basicSetCustomer((Customer)otherEnd, msgs);
    }
    return super.eInverseAdd(otherEnd, featureID, msgs);
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  @Override
  public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs)
  {
    switch (featureID)
    {
    case Model1Package.SALES_ORDER__CUSTOMER:
      return basicSetCustomer(null, msgs);
    }
    return super.eInverseRemove(otherEnd, featureID, msgs);
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  @Override
  public Object eGet(int featureID, boolean resolve, boolean coreType)
  {
    switch (featureID)
    {
    case Model1Package.SALES_ORDER__ID:
      return getId();
    case Model1Package.SALES_ORDER__CUSTOMER:
      if (resolve)
        return getCustomer();
      return basicGetCustomer();
    }
    return super.eGet(featureID, resolve, coreType);
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  @Override
  public void eSet(int featureID, Object newValue)
  {
    switch (featureID)
    {
    case Model1Package.SALES_ORDER__ID:
      setId((Integer)newValue);
      return;
    case Model1Package.SALES_ORDER__CUSTOMER:
      setCustomer((Customer)newValue);
      return;
    }
    super.eSet(featureID, newValue);
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  @Override
  public void eUnset(int featureID)
  {
    switch (featureID)
    {
    case Model1Package.SALES_ORDER__ID:
      setId(ID_EDEFAULT);
      return;
    case Model1Package.SALES_ORDER__CUSTOMER:
      setCustomer((Customer)null);
      return;
    }
    super.eUnset(featureID);
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  @Override
  public boolean eIsSet(int featureID)
  {
    switch (featureID)
    {
    case Model1Package.SALES_ORDER__ID:
      return id != ID_EDEFAULT;
    case Model1Package.SALES_ORDER__CUSTOMER:
      return customer != null;
    }
    return super.eIsSet(featureID);
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  @Override
  public String toString()
  {
    if (eIsProxy())
      return super.toString();

    StringBuffer result = new StringBuffer(super.toString());
    result.append(" (id: ");
    result.append(id);
    result.append(')');
    return result.toString();
  }

} // SalesOrderImpl
