/*
 * Copyright (c) 2004 - 2012 Eike Stepper (Berlin, Germany) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *    Eike Stepper - initial API and implementation
 *
 * $Id: IntegerToStringMapImpl.java,v 1.2 2011-01-01 11:01:57 estepper Exp $
 */
package org.eclipse.emf.cdo.tests.model2.impl;

import org.eclipse.emf.cdo.tests.model2.Model2Package;

import org.eclipse.emf.internal.cdo.CDOObjectImpl;

import org.eclipse.emf.common.util.BasicEMap;
import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Integer To String Map</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link org.eclipse.emf.cdo.tests.model2.impl.IntegerToStringMapImpl#getTypedKey <em>Key</em>}</li>
 * <li>{@link org.eclipse.emf.cdo.tests.model2.impl.IntegerToStringMapImpl#getTypedValue <em>Value</em>}</li>
 * </ul>
 * </p>
 * 
 * @generated
 */
public class IntegerToStringMapImpl extends CDOObjectImpl implements BasicEMap.Entry<Integer, String>
{
  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  protected IntegerToStringMapImpl()
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
    return Model2Package.eINSTANCE.getIntegerToStringMap();
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
  public Integer getTypedKey()
  {
    return (Integer)eGet(Model2Package.eINSTANCE.getIntegerToStringMap_Key(), true);
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  public void setTypedKey(Integer newKey)
  {
    eSet(Model2Package.eINSTANCE.getIntegerToStringMap_Key(), newKey);
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  public String getTypedValue()
  {
    return (String)eGet(Model2Package.eINSTANCE.getIntegerToStringMap_Value(), true);
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  public void setTypedValue(String newValue)
  {
    eSet(Model2Package.eINSTANCE.getIntegerToStringMap_Value(), newValue);
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  protected int hash = -1;

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  public int getHash()
  {
    if (hash == -1)
    {
      Object theKey = getKey();
      hash = (theKey == null ? 0 : theKey.hashCode());
    }
    return hash;
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  public void setHash(int hash)
  {
    this.hash = hash;
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  public Integer getKey()
  {
    return getTypedKey();
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  public void setKey(Integer key)
  {
    setTypedKey(key);
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  public String getValue()
  {
    return getTypedValue();
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  public String setValue(String value)
  {
    String oldValue = getValue();
    setTypedValue(value);
    return oldValue;
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  @SuppressWarnings("unchecked")
  public EMap<Integer, String> getEMap()
  {
    EObject container = eContainer();
    return container == null ? null : (EMap<Integer, String>)container.eGet(eContainmentFeature());
  }

} // IntegerToStringMapImpl
