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
package org.eclipse.emf.cdo.tests.mango.impl;

import org.eclipse.emf.cdo.tests.mango.MangoPackage;
import org.eclipse.emf.cdo.tests.mango.MangoParameter;
import org.eclipse.emf.cdo.tests.mango.ParameterPassing;

import org.eclipse.emf.internal.cdo.CDOObjectImpl;

import org.eclipse.emf.ecore.EClass;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Parameter</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link org.eclipse.emf.cdo.tests.mango.impl.MangoParameterImpl#getName <em>Name</em>}</li>
 * <li>{@link org.eclipse.emf.cdo.tests.mango.impl.MangoParameterImpl#getPassing <em>Passing</em>}</li>
 * </ul>
 * </p>
 * 
 * @generated
 */
public class MangoParameterImpl extends CDOObjectImpl implements MangoParameter
{
  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  protected MangoParameterImpl()
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
    return MangoPackage.eINSTANCE.getMangoParameter();
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
  public String getName()
  {
    return (String)eGet(MangoPackage.eINSTANCE.getMangoParameter_Name(), true);
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  public void setName(String newName)
  {
    eSet(MangoPackage.eINSTANCE.getMangoParameter_Name(), newName);
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  public ParameterPassing getPassing()
  {
    return (ParameterPassing)eGet(MangoPackage.eINSTANCE.getMangoParameter_Passing(), true);
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  public void setPassing(ParameterPassing newPassing)
  {
    eSet(MangoPackage.eINSTANCE.getMangoParameter_Passing(), newPassing);
  }

} // ParameterImpl
