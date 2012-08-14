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
package org.eclipse.emf.cdo.tests.legacy.model6.impl;

import org.eclipse.emf.cdo.tests.legacy.model6.Model6Package;
import org.eclipse.emf.cdo.tests.model6.MyEnum;
import org.eclipse.emf.cdo.tests.model6.MyEnumListUnsettable;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EDataTypeUniqueEList;
import org.eclipse.emf.ecore.util.InternalEList;

import java.util.Collection;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>My Enum List Unsettable</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.emf.cdo.tests.legacy.model6.impl.MyEnumListUnsettableImpl#getMyEnum <em>My Enum</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class MyEnumListUnsettableImpl extends EObjectImpl implements MyEnumListUnsettable
{
  /**
   * The cached value of the '{@link #getMyEnum() <em>My Enum</em>}' attribute list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getMyEnum()
   * @generated
   * @ordered
   */
  protected EList<MyEnum> myEnum;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected MyEnumListUnsettableImpl()
  {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass()
  {
    return Model6Package.eINSTANCE.getMyEnumListUnsettable();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<MyEnum> getMyEnum()
  {
    if (myEnum == null)
    {
      myEnum = new EDataTypeUniqueEList.Unsettable<MyEnum>(MyEnum.class, this,
          Model6Package.MY_ENUM_LIST_UNSETTABLE__MY_ENUM);
    }
    return myEnum;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void unsetMyEnum()
  {
    if (myEnum != null)
    {
      ((InternalEList.Unsettable<?>)myEnum).unset();
    }
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public boolean isSetMyEnum()
  {
    return myEnum != null && ((InternalEList.Unsettable<?>)myEnum).isSet();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Object eGet(int featureID, boolean resolve, boolean coreType)
  {
    switch (featureID)
    {
    case Model6Package.MY_ENUM_LIST_UNSETTABLE__MY_ENUM:
      return getMyEnum();
    }
    return super.eGet(featureID, resolve, coreType);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @SuppressWarnings("unchecked")
  @Override
  public void eSet(int featureID, Object newValue)
  {
    switch (featureID)
    {
    case Model6Package.MY_ENUM_LIST_UNSETTABLE__MY_ENUM:
      getMyEnum().clear();
      getMyEnum().addAll((Collection<? extends MyEnum>)newValue);
      return;
    }
    super.eSet(featureID, newValue);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void eUnset(int featureID)
  {
    switch (featureID)
    {
    case Model6Package.MY_ENUM_LIST_UNSETTABLE__MY_ENUM:
      unsetMyEnum();
      return;
    }
    super.eUnset(featureID);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean eIsSet(int featureID)
  {
    switch (featureID)
    {
    case Model6Package.MY_ENUM_LIST_UNSETTABLE__MY_ENUM:
      return isSetMyEnum();
    }
    return super.eIsSet(featureID);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public String toString()
  {
    if (eIsProxy())
    {
      return super.toString();
    }

    StringBuffer result = new StringBuffer(super.toString());
    result.append(" (myEnum: ");
    result.append(myEnum);
    result.append(')');
    return result.toString();
  }

} // MyEnumListUnsettableImpl
