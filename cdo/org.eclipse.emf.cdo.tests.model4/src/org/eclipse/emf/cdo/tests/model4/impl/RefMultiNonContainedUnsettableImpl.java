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
package org.eclipse.emf.cdo.tests.model4.impl;

import org.eclipse.emf.cdo.tests.model4.MultiNonContainedUnsettableElement;
import org.eclipse.emf.cdo.tests.model4.RefMultiNonContainedUnsettable;
import org.eclipse.emf.cdo.tests.model4.model4Package;

import org.eclipse.emf.internal.cdo.CDOObjectImpl;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Ref Multi Non Contained Unsettable</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link org.eclipse.emf.cdo.tests.model4.impl.RefMultiNonContainedUnsettableImpl#getElements <em>Elements</em>}</li>
 * </ul>
 * </p>
 * 
 * @generated
 */
public class RefMultiNonContainedUnsettableImpl extends CDOObjectImpl implements RefMultiNonContainedUnsettable
{
  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  protected RefMultiNonContainedUnsettableImpl()
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
    return model4Package.eINSTANCE.getRefMultiNonContainedUnsettable();
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
  @SuppressWarnings("unchecked")
  public EList<MultiNonContainedUnsettableElement> getElements()
  {
    return (EList<MultiNonContainedUnsettableElement>)eGet(
        model4Package.eINSTANCE.getRefMultiNonContainedUnsettable_Elements(), true);
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  public void unsetElements()
  {
    eUnset(model4Package.eINSTANCE.getRefMultiNonContainedUnsettable_Elements());
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  public boolean isSetElements()
  {
    return eIsSet(model4Package.eINSTANCE.getRefMultiNonContainedUnsettable_Elements());
  }

} // RefMultiNonContainedUnsettableImpl
