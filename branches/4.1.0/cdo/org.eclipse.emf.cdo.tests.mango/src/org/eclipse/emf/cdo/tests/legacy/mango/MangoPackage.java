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
package org.eclipse.emf.cdo.tests.legacy.mango;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc --> The <b>Package</b> for the model. It contains accessors for the meta objects to represent
 * <ul>
 * <li>each class,</li>
 * <li>each feature of each class,</li>
 * <li>each enum,</li>
 * <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * 
 * @see org.eclipse.emf.cdo.tests.legacy.mango.MangoFactory
 * @model kind="package"
 * @generated
 */
public interface MangoPackage extends org.eclipse.emf.cdo.tests.mango.MangoPackage
{
  /**
   * The package name. <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  String eNAME = "mango";

  /**
   * The package namespace URI. <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  String eNS_URI = "http://www.eclipse.org/emf/CDO/tests/legacy/mango";

  /**
   * The package namespace name. <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  String eNS_PREFIX = "mango";

  /**
   * The singleton instance of the package. <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  MangoPackage eINSTANCE = org.eclipse.emf.cdo.tests.legacy.mango.impl.MangoPackageImpl.init();

  /**
   * The meta object id for the '{@link org.eclipse.emf.cdo.tests.legacy.mango.impl.MangoValueListImpl
   * <em>Value List</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @see org.eclipse.emf.cdo.tests.legacy.mango.impl.MangoValueListImpl
   * @see org.eclipse.emf.cdo.tests.legacy.mango.impl.MangoPackageImpl#getMangoValueList()
   * @generated
   */
  int MANGO_VALUE_LIST = 0;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   * @ordered
   */
  int MANGO_VALUE_LIST__NAME = 0;

  /**
   * The feature id for the '<em><b>Values</b></em>' reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   * @ordered
   */
  int MANGO_VALUE_LIST__VALUES = 1;

  /**
   * The number of structural features of the '<em>Value List</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   * @ordered
   */
  int MANGO_VALUE_LIST_FEATURE_COUNT = 2;

  /**
   * The meta object id for the '{@link org.eclipse.emf.cdo.tests.legacy.mango.impl.MangoValueImpl <em>Value</em>}'
   * class. <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @see org.eclipse.emf.cdo.tests.legacy.mango.impl.MangoValueImpl
   * @see org.eclipse.emf.cdo.tests.legacy.mango.impl.MangoPackageImpl#getMangoValue()
   * @generated
   */
  int MANGO_VALUE = 1;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   * @ordered
   */
  int MANGO_VALUE__NAME = 0;

  /**
   * The number of structural features of the '<em>Value</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   * @ordered
   */
  int MANGO_VALUE_FEATURE_COUNT = 1;

  /**
   * The meta object id for the '{@link org.eclipse.emf.cdo.tests.legacy.mango.impl.MangoParameterImpl
   * <em>Parameter</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @see org.eclipse.emf.cdo.tests.legacy.mango.impl.MangoParameterImpl
   * @see org.eclipse.emf.cdo.tests.legacy.mango.impl.MangoPackageImpl#getMangoParameter()
   * @generated
   */
  int MANGO_PARAMETER = 2;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   * @ordered
   */
  int MANGO_PARAMETER__NAME = 0;

  /**
   * The feature id for the '<em><b>Passing</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   * @ordered
   */
  int MANGO_PARAMETER__PASSING = 1;

  /**
   * The number of structural features of the '<em>Parameter</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   * @ordered
   */
  int MANGO_PARAMETER_FEATURE_COUNT = 2;

  /**
   * The meta object id for the '{@link org.eclipse.emf.cdo.tests.legacy.mango.ParameterPassing
   * <em>Parameter Passing</em>}' enum. <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @see org.eclipse.emf.cdo.tests.legacy.mango.ParameterPassing
   * @see org.eclipse.emf.cdo.tests.legacy.mango.impl.MangoPackageImpl#getParameterPassing()
   * @generated
   */
  int PARAMETER_PASSING = 3;

  /**
   * Returns the meta object for class '{@link org.eclipse.emf.cdo.tests.legacy.mango.MangoValueList
   * <em>Value List</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @return the meta object for class '<em>Value List</em>'.
   * @see org.eclipse.emf.cdo.tests.legacy.mango.MangoValueList
   * @generated
   */
  EClass getMangoValueList();

  /**
   * Returns the meta object for the attribute '{@link org.eclipse.emf.cdo.tests.legacy.mango.MangoValueList#getName
   * <em>Name</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @return the meta object for the attribute '<em>Name</em>'.
   * @see org.eclipse.emf.cdo.tests.legacy.mango.MangoValueList#getName()
   * @see #getMangoValueList()
   * @generated
   */
  EAttribute getMangoValueList_Name();

  /**
   * Returns the meta object for the reference list '
   * {@link org.eclipse.emf.cdo.tests.legacy.mango.MangoValueList#getValues <em>Values</em>}'. <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * 
   * @return the meta object for the reference list '<em>Values</em>'.
   * @see org.eclipse.emf.cdo.tests.legacy.mango.MangoValueList#getValues()
   * @see #getMangoValueList()
   * @generated
   */
  EReference getMangoValueList_Values();

  /**
   * Returns the meta object for class '{@link org.eclipse.emf.cdo.tests.legacy.mango.MangoValue <em>Value</em>}'. <!--
   * begin-user-doc --> <!-- end-user-doc -->
   * 
   * @return the meta object for class '<em>Value</em>'.
   * @see org.eclipse.emf.cdo.tests.legacy.mango.MangoValue
   * @generated
   */
  EClass getMangoValue();

  /**
   * Returns the meta object for the attribute '{@link org.eclipse.emf.cdo.tests.legacy.mango.MangoValue#getName
   * <em>Name</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @return the meta object for the attribute '<em>Name</em>'.
   * @see org.eclipse.emf.cdo.tests.legacy.mango.MangoValue#getName()
   * @see #getMangoValue()
   * @generated
   */
  EAttribute getMangoValue_Name();

  /**
   * Returns the meta object for class '{@link org.eclipse.emf.cdo.tests.legacy.mango.MangoParameter <em>Parameter</em>}
   * '. <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @return the meta object for class '<em>Parameter</em>'.
   * @see org.eclipse.emf.cdo.tests.legacy.mango.MangoParameter
   * @generated
   */
  EClass getMangoParameter();

  /**
   * Returns the meta object for the attribute '{@link org.eclipse.emf.cdo.tests.legacy.mango.MangoParameter#getName
   * <em>Name</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @return the meta object for the attribute '<em>Name</em>'.
   * @see org.eclipse.emf.cdo.tests.legacy.mango.MangoParameter#getName()
   * @see #getMangoParameter()
   * @generated
   */
  EAttribute getMangoParameter_Name();

  /**
   * Returns the meta object for the attribute '{@link org.eclipse.emf.cdo.tests.legacy.mango.MangoParameter#getPassing
   * <em>Passing</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @return the meta object for the attribute '<em>Passing</em>'.
   * @see org.eclipse.emf.cdo.tests.legacy.mango.MangoParameter#getPassing()
   * @see #getMangoParameter()
   * @generated
   */
  EAttribute getMangoParameter_Passing();

  /**
   * Returns the meta object for enum '{@link org.eclipse.emf.cdo.tests.legacy.mango.ParameterPassing
   * <em>Parameter Passing</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @return the meta object for enum '<em>Parameter Passing</em>'.
   * @see org.eclipse.emf.cdo.tests.legacy.mango.ParameterPassing
   * @generated
   */
  EEnum getParameterPassing();

  /**
   * Returns the factory that creates the instances of the model. <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @return the factory that creates the instances of the model.
   * @generated
   */
  MangoFactory getMangoFactory();

} // MangoPackage
