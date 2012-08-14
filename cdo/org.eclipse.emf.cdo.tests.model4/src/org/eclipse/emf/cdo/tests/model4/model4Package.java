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
package org.eclipse.emf.cdo.tests.model4;

import org.eclipse.emf.cdo.tests.model4interfaces.model4interfacesPackage;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
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
 * @see org.eclipse.emf.cdo.tests.model4.model4Factory
 * @model kind="package"
 * @generated
 */
public interface model4Package extends EPackage
{
  /**
   * The package name. <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  String eNAME = "model4";

  /**
   * The package namespace URI. <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  String eNS_URI = "http://www.eclipse.org/emf/CDO/tests/model4/1.0.0";

  /**
   * The package namespace name. <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  String eNS_PREFIX = "model4";

  /**
   * The singleton instance of the package. <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  model4Package eINSTANCE = org.eclipse.emf.cdo.tests.model4.impl.model4PackageImpl.init();

  /**
   * The meta object id for the '{@link org.eclipse.emf.cdo.tests.model4.impl.RefSingleContainedImpl
   * <em>Ref Single Contained</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @see org.eclipse.emf.cdo.tests.model4.impl.RefSingleContainedImpl
   * @see org.eclipse.emf.cdo.tests.model4.impl.model4PackageImpl#getRefSingleContained()
   * @generated
   */
  int REF_SINGLE_CONTAINED = 0;

  /**
   * The feature id for the '<em><b>Element</b></em>' containment reference. <!-- begin-user-doc --> <!-- end-user-doc
   * -->
   * 
   * @generated
   * @ordered
   */
  int REF_SINGLE_CONTAINED__ELEMENT = 0;

  /**
   * The number of structural features of the '<em>Ref Single Contained</em>' class. <!-- begin-user-doc --> <!--
   * end-user-doc -->
   * 
   * @generated
   * @ordered
   */
  int REF_SINGLE_CONTAINED_FEATURE_COUNT = 1;

  /**
   * The meta object id for the '{@link org.eclipse.emf.cdo.tests.model4.impl.SingleContainedElementImpl
   * <em>Single Contained Element</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @see org.eclipse.emf.cdo.tests.model4.impl.SingleContainedElementImpl
   * @see org.eclipse.emf.cdo.tests.model4.impl.model4PackageImpl#getSingleContainedElement()
   * @generated
   */
  int SINGLE_CONTAINED_ELEMENT = 1;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   * @ordered
   */
  int SINGLE_CONTAINED_ELEMENT__NAME = 0;

  /**
   * The feature id for the '<em><b>Parent</b></em>' container reference. <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   * @ordered
   */
  int SINGLE_CONTAINED_ELEMENT__PARENT = 1;

  /**
   * The number of structural features of the '<em>Single Contained Element</em>' class. <!-- begin-user-doc --> <!--
   * end-user-doc -->
   * 
   * @generated
   * @ordered
   */
  int SINGLE_CONTAINED_ELEMENT_FEATURE_COUNT = 2;

  /**
   * The meta object id for the '{@link org.eclipse.emf.cdo.tests.model4.impl.RefSingleNonContainedImpl
   * <em>Ref Single Non Contained</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @see org.eclipse.emf.cdo.tests.model4.impl.RefSingleNonContainedImpl
   * @see org.eclipse.emf.cdo.tests.model4.impl.model4PackageImpl#getRefSingleNonContained()
   * @generated
   */
  int REF_SINGLE_NON_CONTAINED = 2;

  /**
   * The feature id for the '<em><b>Element</b></em>' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   * @ordered
   */
  int REF_SINGLE_NON_CONTAINED__ELEMENT = 0;

  /**
   * The number of structural features of the '<em>Ref Single Non Contained</em>' class. <!-- begin-user-doc --> <!--
   * end-user-doc -->
   * 
   * @generated
   * @ordered
   */
  int REF_SINGLE_NON_CONTAINED_FEATURE_COUNT = 1;

  /**
   * The meta object id for the '{@link org.eclipse.emf.cdo.tests.model4.impl.SingleNonContainedElementImpl
   * <em>Single Non Contained Element</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @see org.eclipse.emf.cdo.tests.model4.impl.SingleNonContainedElementImpl
   * @see org.eclipse.emf.cdo.tests.model4.impl.model4PackageImpl#getSingleNonContainedElement()
   * @generated
   */
  int SINGLE_NON_CONTAINED_ELEMENT = 3;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   * @ordered
   */
  int SINGLE_NON_CONTAINED_ELEMENT__NAME = 0;

  /**
   * The feature id for the '<em><b>Parent</b></em>' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   * @ordered
   */
  int SINGLE_NON_CONTAINED_ELEMENT__PARENT = 1;

  /**
   * The number of structural features of the '<em>Single Non Contained Element</em>' class. <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * 
   * @generated
   * @ordered
   */
  int SINGLE_NON_CONTAINED_ELEMENT_FEATURE_COUNT = 2;

  /**
   * The meta object id for the '{@link org.eclipse.emf.cdo.tests.model4.impl.RefMultiContainedImpl
   * <em>Ref Multi Contained</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @see org.eclipse.emf.cdo.tests.model4.impl.RefMultiContainedImpl
   * @see org.eclipse.emf.cdo.tests.model4.impl.model4PackageImpl#getRefMultiContained()
   * @generated
   */
  int REF_MULTI_CONTAINED = 4;

  /**
   * The feature id for the '<em><b>Elements</b></em>' containment reference list. <!-- begin-user-doc --> <!--
   * end-user-doc -->
   * 
   * @generated
   * @ordered
   */
  int REF_MULTI_CONTAINED__ELEMENTS = 0;

  /**
   * The number of structural features of the '<em>Ref Multi Contained</em>' class. <!-- begin-user-doc --> <!--
   * end-user-doc -->
   * 
   * @generated
   * @ordered
   */
  int REF_MULTI_CONTAINED_FEATURE_COUNT = 1;

  /**
   * The meta object id for the '{@link org.eclipse.emf.cdo.tests.model4.impl.MultiContainedElementImpl
   * <em>Multi Contained Element</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @see org.eclipse.emf.cdo.tests.model4.impl.MultiContainedElementImpl
   * @see org.eclipse.emf.cdo.tests.model4.impl.model4PackageImpl#getMultiContainedElement()
   * @generated
   */
  int MULTI_CONTAINED_ELEMENT = 5;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   * @ordered
   */
  int MULTI_CONTAINED_ELEMENT__NAME = 0;

  /**
   * The feature id for the '<em><b>Parent</b></em>' container reference. <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   * @ordered
   */
  int MULTI_CONTAINED_ELEMENT__PARENT = 1;

  /**
   * The number of structural features of the '<em>Multi Contained Element</em>' class. <!-- begin-user-doc --> <!--
   * end-user-doc -->
   * 
   * @generated
   * @ordered
   */
  int MULTI_CONTAINED_ELEMENT_FEATURE_COUNT = 2;

  /**
   * The meta object id for the '{@link org.eclipse.emf.cdo.tests.model4.impl.RefMultiNonContainedImpl
   * <em>Ref Multi Non Contained</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @see org.eclipse.emf.cdo.tests.model4.impl.RefMultiNonContainedImpl
   * @see org.eclipse.emf.cdo.tests.model4.impl.model4PackageImpl#getRefMultiNonContained()
   * @generated
   */
  int REF_MULTI_NON_CONTAINED = 6;

  /**
   * The feature id for the '<em><b>Elements</b></em>' reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   * @ordered
   */
  int REF_MULTI_NON_CONTAINED__ELEMENTS = 0;

  /**
   * The number of structural features of the '<em>Ref Multi Non Contained</em>' class. <!-- begin-user-doc --> <!--
   * end-user-doc -->
   * 
   * @generated
   * @ordered
   */
  int REF_MULTI_NON_CONTAINED_FEATURE_COUNT = 1;

  /**
   * The meta object id for the '{@link org.eclipse.emf.cdo.tests.model4.impl.MultiNonContainedElementImpl
   * <em>Multi Non Contained Element</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @see org.eclipse.emf.cdo.tests.model4.impl.MultiNonContainedElementImpl
   * @see org.eclipse.emf.cdo.tests.model4.impl.model4PackageImpl#getMultiNonContainedElement()
   * @generated
   */
  int MULTI_NON_CONTAINED_ELEMENT = 7;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   * @ordered
   */
  int MULTI_NON_CONTAINED_ELEMENT__NAME = 0;

  /**
   * The feature id for the '<em><b>Parent</b></em>' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   * @ordered
   */
  int MULTI_NON_CONTAINED_ELEMENT__PARENT = 1;

  /**
   * The number of structural features of the '<em>Multi Non Contained Element</em>' class. <!-- begin-user-doc --> <!--
   * end-user-doc -->
   * 
   * @generated
   * @ordered
   */
  int MULTI_NON_CONTAINED_ELEMENT_FEATURE_COUNT = 2;

  /**
   * The meta object id for the '{@link org.eclipse.emf.cdo.tests.model4.impl.RefMultiNonContainedUnsettableImpl
   * <em>Ref Multi Non Contained Unsettable</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @see org.eclipse.emf.cdo.tests.model4.impl.RefMultiNonContainedUnsettableImpl
   * @see org.eclipse.emf.cdo.tests.model4.impl.model4PackageImpl#getRefMultiNonContainedUnsettable()
   * @generated
   */
  int REF_MULTI_NON_CONTAINED_UNSETTABLE = 8;

  /**
   * The feature id for the '<em><b>Elements</b></em>' reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   * @ordered
   */
  int REF_MULTI_NON_CONTAINED_UNSETTABLE__ELEMENTS = 0;

  /**
   * The number of structural features of the '<em>Ref Multi Non Contained Unsettable</em>' class. <!-- begin-user-doc
   * --> <!-- end-user-doc -->
   * 
   * @generated
   * @ordered
   */
  int REF_MULTI_NON_CONTAINED_UNSETTABLE_FEATURE_COUNT = 1;

  /**
   * The meta object id for the '{@link org.eclipse.emf.cdo.tests.model4.impl.MultiNonContainedUnsettableElementImpl
   * <em>Multi Non Contained Unsettable Element</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @see org.eclipse.emf.cdo.tests.model4.impl.MultiNonContainedUnsettableElementImpl
   * @see org.eclipse.emf.cdo.tests.model4.impl.model4PackageImpl#getMultiNonContainedUnsettableElement()
   * @generated
   */
  int MULTI_NON_CONTAINED_UNSETTABLE_ELEMENT = 9;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   * @ordered
   */
  int MULTI_NON_CONTAINED_UNSETTABLE_ELEMENT__NAME = 0;

  /**
   * The feature id for the '<em><b>Parent</b></em>' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   * @ordered
   */
  int MULTI_NON_CONTAINED_UNSETTABLE_ELEMENT__PARENT = 1;

  /**
   * The number of structural features of the '<em>Multi Non Contained Unsettable Element</em>' class. <!--
   * begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   * @ordered
   */
  int MULTI_NON_CONTAINED_UNSETTABLE_ELEMENT_FEATURE_COUNT = 2;

  /**
   * The meta object id for the '{@link org.eclipse.emf.cdo.tests.model4.impl.RefSingleContainedNPLImpl
   * <em>Ref Single Contained NPL</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @see org.eclipse.emf.cdo.tests.model4.impl.RefSingleContainedNPLImpl
   * @see org.eclipse.emf.cdo.tests.model4.impl.model4PackageImpl#getRefSingleContainedNPL()
   * @generated
   */
  int REF_SINGLE_CONTAINED_NPL = 10;

  /**
   * The feature id for the '<em><b>Element</b></em>' containment reference. <!-- begin-user-doc --> <!-- end-user-doc
   * -->
   * 
   * @generated
   * @ordered
   */
  int REF_SINGLE_CONTAINED_NPL__ELEMENT = 0;

  /**
   * The number of structural features of the '<em>Ref Single Contained NPL</em>' class. <!-- begin-user-doc --> <!--
   * end-user-doc -->
   * 
   * @generated
   * @ordered
   */
  int REF_SINGLE_CONTAINED_NPL_FEATURE_COUNT = 1;

  /**
   * The meta object id for the '{@link org.eclipse.emf.cdo.tests.model4.impl.RefSingleNonContainedNPLImpl
   * <em>Ref Single Non Contained NPL</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @see org.eclipse.emf.cdo.tests.model4.impl.RefSingleNonContainedNPLImpl
   * @see org.eclipse.emf.cdo.tests.model4.impl.model4PackageImpl#getRefSingleNonContainedNPL()
   * @generated
   */
  int REF_SINGLE_NON_CONTAINED_NPL = 11;

  /**
   * The feature id for the '<em><b>Element</b></em>' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   * @ordered
   */
  int REF_SINGLE_NON_CONTAINED_NPL__ELEMENT = 0;

  /**
   * The number of structural features of the '<em>Ref Single Non Contained NPL</em>' class. <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * 
   * @generated
   * @ordered
   */
  int REF_SINGLE_NON_CONTAINED_NPL_FEATURE_COUNT = 1;

  /**
   * The meta object id for the '{@link org.eclipse.emf.cdo.tests.model4.impl.RefMultiContainedNPLImpl
   * <em>Ref Multi Contained NPL</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @see org.eclipse.emf.cdo.tests.model4.impl.RefMultiContainedNPLImpl
   * @see org.eclipse.emf.cdo.tests.model4.impl.model4PackageImpl#getRefMultiContainedNPL()
   * @generated
   */
  int REF_MULTI_CONTAINED_NPL = 12;

  /**
   * The feature id for the '<em><b>Elements</b></em>' containment reference list. <!-- begin-user-doc --> <!--
   * end-user-doc -->
   * 
   * @generated
   * @ordered
   */
  int REF_MULTI_CONTAINED_NPL__ELEMENTS = 0;

  /**
   * The number of structural features of the '<em>Ref Multi Contained NPL</em>' class. <!-- begin-user-doc --> <!--
   * end-user-doc -->
   * 
   * @generated
   * @ordered
   */
  int REF_MULTI_CONTAINED_NPL_FEATURE_COUNT = 1;

  /**
   * The meta object id for the '{@link org.eclipse.emf.cdo.tests.model4.impl.RefMultiNonContainedNPLImpl
   * <em>Ref Multi Non Contained NPL</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @see org.eclipse.emf.cdo.tests.model4.impl.RefMultiNonContainedNPLImpl
   * @see org.eclipse.emf.cdo.tests.model4.impl.model4PackageImpl#getRefMultiNonContainedNPL()
   * @generated
   */
  int REF_MULTI_NON_CONTAINED_NPL = 13;

  /**
   * The feature id for the '<em><b>Elements</b></em>' reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   * @ordered
   */
  int REF_MULTI_NON_CONTAINED_NPL__ELEMENTS = 0;

  /**
   * The number of structural features of the '<em>Ref Multi Non Contained NPL</em>' class. <!-- begin-user-doc --> <!--
   * end-user-doc -->
   * 
   * @generated
   * @ordered
   */
  int REF_MULTI_NON_CONTAINED_NPL_FEATURE_COUNT = 1;

  /**
   * The meta object id for the '{@link org.eclipse.emf.cdo.tests.model4.impl.ContainedElementNoOppositeImpl
   * <em>Contained Element No Opposite</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @see org.eclipse.emf.cdo.tests.model4.impl.ContainedElementNoOppositeImpl
   * @see org.eclipse.emf.cdo.tests.model4.impl.model4PackageImpl#getContainedElementNoOpposite()
   * @generated
   */
  int CONTAINED_ELEMENT_NO_OPPOSITE = 14;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   * @ordered
   */
  int CONTAINED_ELEMENT_NO_OPPOSITE__NAME = 0;

  /**
   * The number of structural features of the '<em>Contained Element No Opposite</em>' class. <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * 
   * @generated
   * @ordered
   */
  int CONTAINED_ELEMENT_NO_OPPOSITE_FEATURE_COUNT = 1;

  /**
   * The meta object id for the '{@link org.eclipse.emf.cdo.tests.model4.impl.GenRefSingleContainedImpl
   * <em>Gen Ref Single Contained</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @see org.eclipse.emf.cdo.tests.model4.impl.GenRefSingleContainedImpl
   * @see org.eclipse.emf.cdo.tests.model4.impl.model4PackageImpl#getGenRefSingleContained()
   * @generated
   */
  int GEN_REF_SINGLE_CONTAINED = 15;

  /**
   * The feature id for the '<em><b>Element</b></em>' containment reference. <!-- begin-user-doc --> <!-- end-user-doc
   * -->
   * 
   * @generated
   * @ordered
   */
  int GEN_REF_SINGLE_CONTAINED__ELEMENT = 0;

  /**
   * The number of structural features of the '<em>Gen Ref Single Contained</em>' class. <!-- begin-user-doc --> <!--
   * end-user-doc -->
   * 
   * @generated
   * @ordered
   */
  int GEN_REF_SINGLE_CONTAINED_FEATURE_COUNT = 1;

  /**
   * The meta object id for the '{@link org.eclipse.emf.cdo.tests.model4.impl.GenRefSingleNonContainedImpl
   * <em>Gen Ref Single Non Contained</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @see org.eclipse.emf.cdo.tests.model4.impl.GenRefSingleNonContainedImpl
   * @see org.eclipse.emf.cdo.tests.model4.impl.model4PackageImpl#getGenRefSingleNonContained()
   * @generated
   */
  int GEN_REF_SINGLE_NON_CONTAINED = 16;

  /**
   * The feature id for the '<em><b>Element</b></em>' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   * @ordered
   */
  int GEN_REF_SINGLE_NON_CONTAINED__ELEMENT = 0;

  /**
   * The number of structural features of the '<em>Gen Ref Single Non Contained</em>' class. <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * 
   * @generated
   * @ordered
   */
  int GEN_REF_SINGLE_NON_CONTAINED_FEATURE_COUNT = 1;

  /**
   * The meta object id for the '{@link org.eclipse.emf.cdo.tests.model4.impl.GenRefMultiContainedImpl
   * <em>Gen Ref Multi Contained</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @see org.eclipse.emf.cdo.tests.model4.impl.GenRefMultiContainedImpl
   * @see org.eclipse.emf.cdo.tests.model4.impl.model4PackageImpl#getGenRefMultiContained()
   * @generated
   */
  int GEN_REF_MULTI_CONTAINED = 17;

  /**
   * The feature id for the '<em><b>Elements</b></em>' containment reference list. <!-- begin-user-doc --> <!--
   * end-user-doc -->
   * 
   * @generated
   * @ordered
   */
  int GEN_REF_MULTI_CONTAINED__ELEMENTS = 0;

  /**
   * The number of structural features of the '<em>Gen Ref Multi Contained</em>' class. <!-- begin-user-doc --> <!--
   * end-user-doc -->
   * 
   * @generated
   * @ordered
   */
  int GEN_REF_MULTI_CONTAINED_FEATURE_COUNT = 1;

  /**
   * The meta object id for the '{@link org.eclipse.emf.cdo.tests.model4.impl.GenRefMultiNonContainedImpl
   * <em>Gen Ref Multi Non Contained</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @see org.eclipse.emf.cdo.tests.model4.impl.GenRefMultiNonContainedImpl
   * @see org.eclipse.emf.cdo.tests.model4.impl.model4PackageImpl#getGenRefMultiNonContained()
   * @generated
   */
  int GEN_REF_MULTI_NON_CONTAINED = 18;

  /**
   * The feature id for the '<em><b>Elements</b></em>' reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   * @ordered
   */
  int GEN_REF_MULTI_NON_CONTAINED__ELEMENTS = 0;

  /**
   * The number of structural features of the '<em>Gen Ref Multi Non Contained</em>' class. <!-- begin-user-doc --> <!--
   * end-user-doc -->
   * 
   * @generated
   * @ordered
   */
  int GEN_REF_MULTI_NON_CONTAINED_FEATURE_COUNT = 1;

  /**
   * The meta object id for the '{@link org.eclipse.emf.cdo.tests.model4.impl.ImplSingleRefContainerImpl
   * <em>Impl Single Ref Container</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @see org.eclipse.emf.cdo.tests.model4.impl.ImplSingleRefContainerImpl
   * @see org.eclipse.emf.cdo.tests.model4.impl.model4PackageImpl#getImplSingleRefContainer()
   * @generated
   */
  int IMPL_SINGLE_REF_CONTAINER = 19;

  /**
   * The feature id for the '<em><b>Element</b></em>' containment reference. <!-- begin-user-doc --> <!-- end-user-doc
   * -->
   * 
   * @generated
   * @ordered
   */
  int IMPL_SINGLE_REF_CONTAINER__ELEMENT = model4interfacesPackage.ISINGLE_REF_CONTAINER__ELEMENT;

  /**
   * The number of structural features of the '<em>Impl Single Ref Container</em>' class. <!-- begin-user-doc --> <!--
   * end-user-doc -->
   * 
   * @generated
   * @ordered
   */
  int IMPL_SINGLE_REF_CONTAINER_FEATURE_COUNT = model4interfacesPackage.ISINGLE_REF_CONTAINER_FEATURE_COUNT + 0;

  /**
   * The meta object id for the '{@link org.eclipse.emf.cdo.tests.model4.impl.ImplSingleRefContainedElementImpl
   * <em>Impl Single Ref Contained Element</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @see org.eclipse.emf.cdo.tests.model4.impl.ImplSingleRefContainedElementImpl
   * @see org.eclipse.emf.cdo.tests.model4.impl.model4PackageImpl#getImplSingleRefContainedElement()
   * @generated
   */
  int IMPL_SINGLE_REF_CONTAINED_ELEMENT = 20;

  /**
   * The feature id for the '<em><b>Parent</b></em>' container reference. <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   * @ordered
   */
  int IMPL_SINGLE_REF_CONTAINED_ELEMENT__PARENT = model4interfacesPackage.ISINGLE_REF_CONTAINED_ELEMENT__PARENT;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   * @ordered
   */
  int IMPL_SINGLE_REF_CONTAINED_ELEMENT__NAME = model4interfacesPackage.ISINGLE_REF_CONTAINED_ELEMENT_FEATURE_COUNT + 0;

  /**
   * The number of structural features of the '<em>Impl Single Ref Contained Element</em>' class. <!-- begin-user-doc
   * --> <!-- end-user-doc -->
   * 
   * @generated
   * @ordered
   */
  int IMPL_SINGLE_REF_CONTAINED_ELEMENT_FEATURE_COUNT = model4interfacesPackage.ISINGLE_REF_CONTAINED_ELEMENT_FEATURE_COUNT + 1;

  /**
   * The meta object id for the '{@link org.eclipse.emf.cdo.tests.model4.impl.ImplSingleRefNonContainerImpl
   * <em>Impl Single Ref Non Container</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @see org.eclipse.emf.cdo.tests.model4.impl.ImplSingleRefNonContainerImpl
   * @see org.eclipse.emf.cdo.tests.model4.impl.model4PackageImpl#getImplSingleRefNonContainer()
   * @generated
   */
  int IMPL_SINGLE_REF_NON_CONTAINER = 21;

  /**
   * The feature id for the '<em><b>Element</b></em>' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   * @ordered
   */
  int IMPL_SINGLE_REF_NON_CONTAINER__ELEMENT = model4interfacesPackage.ISINGLE_REF_NON_CONTAINER__ELEMENT;

  /**
   * The number of structural features of the '<em>Impl Single Ref Non Container</em>' class. <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * 
   * @generated
   * @ordered
   */
  int IMPL_SINGLE_REF_NON_CONTAINER_FEATURE_COUNT = model4interfacesPackage.ISINGLE_REF_NON_CONTAINER_FEATURE_COUNT + 0;

  /**
   * The meta object id for the '{@link org.eclipse.emf.cdo.tests.model4.impl.ImplSingleRefNonContainedElementImpl
   * <em>Impl Single Ref Non Contained Element</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @see org.eclipse.emf.cdo.tests.model4.impl.ImplSingleRefNonContainedElementImpl
   * @see org.eclipse.emf.cdo.tests.model4.impl.model4PackageImpl#getImplSingleRefNonContainedElement()
   * @generated
   */
  int IMPL_SINGLE_REF_NON_CONTAINED_ELEMENT = 22;

  /**
   * The feature id for the '<em><b>Parent</b></em>' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   * @ordered
   */
  int IMPL_SINGLE_REF_NON_CONTAINED_ELEMENT__PARENT = model4interfacesPackage.ISINGLE_REF_NON_CONTAINED_ELEMENT__PARENT;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   * @ordered
   */
  int IMPL_SINGLE_REF_NON_CONTAINED_ELEMENT__NAME = model4interfacesPackage.ISINGLE_REF_NON_CONTAINED_ELEMENT_FEATURE_COUNT + 0;

  /**
   * The number of structural features of the '<em>Impl Single Ref Non Contained Element</em>' class. <!--
   * begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   * @ordered
   */
  int IMPL_SINGLE_REF_NON_CONTAINED_ELEMENT_FEATURE_COUNT = model4interfacesPackage.ISINGLE_REF_NON_CONTAINED_ELEMENT_FEATURE_COUNT + 1;

  /**
   * The meta object id for the '{@link org.eclipse.emf.cdo.tests.model4.impl.ImplMultiRefNonContainerImpl
   * <em>Impl Multi Ref Non Container</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @see org.eclipse.emf.cdo.tests.model4.impl.ImplMultiRefNonContainerImpl
   * @see org.eclipse.emf.cdo.tests.model4.impl.model4PackageImpl#getImplMultiRefNonContainer()
   * @generated
   */
  int IMPL_MULTI_REF_NON_CONTAINER = 23;

  /**
   * The feature id for the '<em><b>Elements</b></em>' reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   * @ordered
   */
  int IMPL_MULTI_REF_NON_CONTAINER__ELEMENTS = model4interfacesPackage.IMULTI_REF_NON_CONTAINER__ELEMENTS;

  /**
   * The number of structural features of the '<em>Impl Multi Ref Non Container</em>' class. <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * 
   * @generated
   * @ordered
   */
  int IMPL_MULTI_REF_NON_CONTAINER_FEATURE_COUNT = model4interfacesPackage.IMULTI_REF_NON_CONTAINER_FEATURE_COUNT + 0;

  /**
   * The meta object id for the '{@link org.eclipse.emf.cdo.tests.model4.impl.ImplMultiRefNonContainedElementImpl
   * <em>Impl Multi Ref Non Contained Element</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @see org.eclipse.emf.cdo.tests.model4.impl.ImplMultiRefNonContainedElementImpl
   * @see org.eclipse.emf.cdo.tests.model4.impl.model4PackageImpl#getImplMultiRefNonContainedElement()
   * @generated
   */
  int IMPL_MULTI_REF_NON_CONTAINED_ELEMENT = 24;

  /**
   * The feature id for the '<em><b>Parent</b></em>' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   * @ordered
   */
  int IMPL_MULTI_REF_NON_CONTAINED_ELEMENT__PARENT = model4interfacesPackage.IMULTI_REF_NON_CONTAINED_ELEMENT__PARENT;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   * @ordered
   */
  int IMPL_MULTI_REF_NON_CONTAINED_ELEMENT__NAME = model4interfacesPackage.IMULTI_REF_NON_CONTAINED_ELEMENT_FEATURE_COUNT + 0;

  /**
   * The number of structural features of the '<em>Impl Multi Ref Non Contained Element</em>' class. <!-- begin-user-doc
   * --> <!-- end-user-doc -->
   * 
   * @generated
   * @ordered
   */
  int IMPL_MULTI_REF_NON_CONTAINED_ELEMENT_FEATURE_COUNT = model4interfacesPackage.IMULTI_REF_NON_CONTAINED_ELEMENT_FEATURE_COUNT + 1;

  /**
   * The meta object id for the '{@link org.eclipse.emf.cdo.tests.model4.impl.ImplMultiRefContainerImpl
   * <em>Impl Multi Ref Container</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @see org.eclipse.emf.cdo.tests.model4.impl.ImplMultiRefContainerImpl
   * @see org.eclipse.emf.cdo.tests.model4.impl.model4PackageImpl#getImplMultiRefContainer()
   * @generated
   */
  int IMPL_MULTI_REF_CONTAINER = 25;

  /**
   * The feature id for the '<em><b>Elements</b></em>' containment reference list. <!-- begin-user-doc --> <!--
   * end-user-doc -->
   * 
   * @generated
   * @ordered
   */
  int IMPL_MULTI_REF_CONTAINER__ELEMENTS = model4interfacesPackage.IMULTI_REF_CONTAINER__ELEMENTS;

  /**
   * The number of structural features of the '<em>Impl Multi Ref Container</em>' class. <!-- begin-user-doc --> <!--
   * end-user-doc -->
   * 
   * @generated
   * @ordered
   */
  int IMPL_MULTI_REF_CONTAINER_FEATURE_COUNT = model4interfacesPackage.IMULTI_REF_CONTAINER_FEATURE_COUNT + 0;

  /**
   * The meta object id for the '{@link org.eclipse.emf.cdo.tests.model4.impl.ImplMultiRefContainedElementImpl
   * <em>Impl Multi Ref Contained Element</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @see org.eclipse.emf.cdo.tests.model4.impl.ImplMultiRefContainedElementImpl
   * @see org.eclipse.emf.cdo.tests.model4.impl.model4PackageImpl#getImplMultiRefContainedElement()
   * @generated
   */
  int IMPL_MULTI_REF_CONTAINED_ELEMENT = 26;

  /**
   * The feature id for the '<em><b>Parent</b></em>' container reference. <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   * @ordered
   */
  int IMPL_MULTI_REF_CONTAINED_ELEMENT__PARENT = model4interfacesPackage.IMULTI_REF_CONTAINED_ELEMENT__PARENT;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   * @ordered
   */
  int IMPL_MULTI_REF_CONTAINED_ELEMENT__NAME = model4interfacesPackage.IMULTI_REF_CONTAINED_ELEMENT_FEATURE_COUNT + 0;

  /**
   * The number of structural features of the '<em>Impl Multi Ref Contained Element</em>' class. <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * 
   * @generated
   * @ordered
   */
  int IMPL_MULTI_REF_CONTAINED_ELEMENT_FEATURE_COUNT = model4interfacesPackage.IMULTI_REF_CONTAINED_ELEMENT_FEATURE_COUNT + 1;

  /**
   * The meta object id for the '{@link org.eclipse.emf.cdo.tests.model4.impl.ImplSingleRefContainerNPLImpl
   * <em>Impl Single Ref Container NPL</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @see org.eclipse.emf.cdo.tests.model4.impl.ImplSingleRefContainerNPLImpl
   * @see org.eclipse.emf.cdo.tests.model4.impl.model4PackageImpl#getImplSingleRefContainerNPL()
   * @generated
   */
  int IMPL_SINGLE_REF_CONTAINER_NPL = 27;

  /**
   * The feature id for the '<em><b>Element</b></em>' containment reference. <!-- begin-user-doc --> <!-- end-user-doc
   * -->
   * 
   * @generated
   * @ordered
   */
  int IMPL_SINGLE_REF_CONTAINER_NPL__ELEMENT = model4interfacesPackage.ISINGLE_REF_CONTAINER_NPL__ELEMENT;

  /**
   * The number of structural features of the '<em>Impl Single Ref Container NPL</em>' class. <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * 
   * @generated
   * @ordered
   */
  int IMPL_SINGLE_REF_CONTAINER_NPL_FEATURE_COUNT = model4interfacesPackage.ISINGLE_REF_CONTAINER_NPL_FEATURE_COUNT + 0;

  /**
   * The meta object id for the '{@link org.eclipse.emf.cdo.tests.model4.impl.ImplSingleRefNonContainerNPLImpl
   * <em>Impl Single Ref Non Container NPL</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @see org.eclipse.emf.cdo.tests.model4.impl.ImplSingleRefNonContainerNPLImpl
   * @see org.eclipse.emf.cdo.tests.model4.impl.model4PackageImpl#getImplSingleRefNonContainerNPL()
   * @generated
   */
  int IMPL_SINGLE_REF_NON_CONTAINER_NPL = 28;

  /**
   * The feature id for the '<em><b>Element</b></em>' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   * @ordered
   */
  int IMPL_SINGLE_REF_NON_CONTAINER_NPL__ELEMENT = model4interfacesPackage.ISINGLE_REF_NON_CONTAINER_NPL__ELEMENT;

  /**
   * The number of structural features of the '<em>Impl Single Ref Non Container NPL</em>' class. <!-- begin-user-doc
   * --> <!-- end-user-doc -->
   * 
   * @generated
   * @ordered
   */
  int IMPL_SINGLE_REF_NON_CONTAINER_NPL_FEATURE_COUNT = model4interfacesPackage.ISINGLE_REF_NON_CONTAINER_NPL_FEATURE_COUNT + 0;

  /**
   * The meta object id for the '{@link org.eclipse.emf.cdo.tests.model4.impl.ImplMultiRefContainerNPLImpl
   * <em>Impl Multi Ref Container NPL</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @see org.eclipse.emf.cdo.tests.model4.impl.ImplMultiRefContainerNPLImpl
   * @see org.eclipse.emf.cdo.tests.model4.impl.model4PackageImpl#getImplMultiRefContainerNPL()
   * @generated
   */
  int IMPL_MULTI_REF_CONTAINER_NPL = 29;

  /**
   * The feature id for the '<em><b>Elements</b></em>' containment reference list. <!-- begin-user-doc --> <!--
   * end-user-doc -->
   * 
   * @generated
   * @ordered
   */
  int IMPL_MULTI_REF_CONTAINER_NPL__ELEMENTS = model4interfacesPackage.IMULTI_REF_CONTAINER_NPL__ELEMENTS;

  /**
   * The number of structural features of the '<em>Impl Multi Ref Container NPL</em>' class. <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * 
   * @generated
   * @ordered
   */
  int IMPL_MULTI_REF_CONTAINER_NPL_FEATURE_COUNT = model4interfacesPackage.IMULTI_REF_CONTAINER_NPL_FEATURE_COUNT + 0;

  /**
   * The meta object id for the '{@link org.eclipse.emf.cdo.tests.model4.impl.ImplMultiRefNonContainerNPLImpl
   * <em>Impl Multi Ref Non Container NPL</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @see org.eclipse.emf.cdo.tests.model4.impl.ImplMultiRefNonContainerNPLImpl
   * @see org.eclipse.emf.cdo.tests.model4.impl.model4PackageImpl#getImplMultiRefNonContainerNPL()
   * @generated
   */
  int IMPL_MULTI_REF_NON_CONTAINER_NPL = 30;

  /**
   * The feature id for the '<em><b>Elements</b></em>' reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   * @ordered
   */
  int IMPL_MULTI_REF_NON_CONTAINER_NPL__ELEMENTS = model4interfacesPackage.IMULTI_REF_NON_CONTAINER_NPL__ELEMENTS;

  /**
   * The number of structural features of the '<em>Impl Multi Ref Non Container NPL</em>' class. <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * 
   * @generated
   * @ordered
   */
  int IMPL_MULTI_REF_NON_CONTAINER_NPL_FEATURE_COUNT = model4interfacesPackage.IMULTI_REF_NON_CONTAINER_NPL_FEATURE_COUNT + 0;

  /**
   * The meta object id for the '{@link org.eclipse.emf.cdo.tests.model4.impl.ImplContainedElementNPLImpl
   * <em>Impl Contained Element NPL</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @see org.eclipse.emf.cdo.tests.model4.impl.ImplContainedElementNPLImpl
   * @see org.eclipse.emf.cdo.tests.model4.impl.model4PackageImpl#getImplContainedElementNPL()
   * @generated
   */
  int IMPL_CONTAINED_ELEMENT_NPL = 31;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   * @ordered
   */
  int IMPL_CONTAINED_ELEMENT_NPL__NAME = model4interfacesPackage.ICONTAINED_ELEMENT_NO_PARENT_LINK_FEATURE_COUNT + 0;

  /**
   * The number of structural features of the '<em>Impl Contained Element NPL</em>' class. <!-- begin-user-doc --> <!--
   * end-user-doc -->
   * 
   * @generated
   * @ordered
   */
  int IMPL_CONTAINED_ELEMENT_NPL_FEATURE_COUNT = model4interfacesPackage.ICONTAINED_ELEMENT_NO_PARENT_LINK_FEATURE_COUNT + 1;

  /**
   * The meta object id for the '{@link org.eclipse.emf.cdo.tests.model4.impl.GenRefMultiNUNonContainedImpl
   * <em>Gen Ref Multi NU Non Contained</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @see org.eclipse.emf.cdo.tests.model4.impl.GenRefMultiNUNonContainedImpl
   * @see org.eclipse.emf.cdo.tests.model4.impl.model4PackageImpl#getGenRefMultiNUNonContained()
   * @generated
   */
  int GEN_REF_MULTI_NU_NON_CONTAINED = 32;

  /**
   * The feature id for the '<em><b>Elements</b></em>' reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   * @ordered
   */
  int GEN_REF_MULTI_NU_NON_CONTAINED__ELEMENTS = 0;

  /**
   * The number of structural features of the '<em>Gen Ref Multi NU Non Contained</em>' class. <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * 
   * @generated
   * @ordered
   */
  int GEN_REF_MULTI_NU_NON_CONTAINED_FEATURE_COUNT = 1;

  /**
   * The meta object id for the '{@link org.eclipse.emf.cdo.tests.model4.impl.GenRefMapNonContainedImpl
   * <em>Gen Ref Map Non Contained</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @see org.eclipse.emf.cdo.tests.model4.impl.GenRefMapNonContainedImpl
   * @see org.eclipse.emf.cdo.tests.model4.impl.model4PackageImpl#getGenRefMapNonContained()
   * @generated
   */
  int GEN_REF_MAP_NON_CONTAINED = 33;

  /**
   * The feature id for the '<em><b>Elements</b></em>' map. <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   * @ordered
   */
  int GEN_REF_MAP_NON_CONTAINED__ELEMENTS = 0;

  /**
   * The number of structural features of the '<em>Gen Ref Map Non Contained</em>' class. <!-- begin-user-doc --> <!--
   * end-user-doc -->
   * 
   * @generated
   * @ordered
   */
  int GEN_REF_MAP_NON_CONTAINED_FEATURE_COUNT = 1;

  /**
   * The meta object id for the '{@link org.eclipse.emf.cdo.tests.model4.impl.StringToEObjectImpl
   * <em>String To EObject</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @see org.eclipse.emf.cdo.tests.model4.impl.StringToEObjectImpl
   * @see org.eclipse.emf.cdo.tests.model4.impl.model4PackageImpl#getStringToEObject()
   * @generated
   */
  int STRING_TO_EOBJECT = 34;

  /**
   * The feature id for the '<em><b>Key</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   * @ordered
   */
  int STRING_TO_EOBJECT__KEY = 0;

  /**
   * The feature id for the '<em><b>Value</b></em>' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   * @ordered
   */
  int STRING_TO_EOBJECT__VALUE = 1;

  /**
   * The number of structural features of the '<em>String To EObject</em>' class. <!-- begin-user-doc --> <!--
   * end-user-doc -->
   * 
   * @generated
   * @ordered
   */
  int STRING_TO_EOBJECT_FEATURE_COUNT = 2;

  /**
   * Returns the meta object for class '{@link org.eclipse.emf.cdo.tests.model4.RefSingleContained
   * <em>Ref Single Contained</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @return the meta object for class '<em>Ref Single Contained</em>'.
   * @see org.eclipse.emf.cdo.tests.model4.RefSingleContained
   * @generated
   */
  EClass getRefSingleContained();

  /**
   * Returns the meta object for the containment reference '
   * {@link org.eclipse.emf.cdo.tests.model4.RefSingleContained#getElement <em>Element</em>}'. <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * 
   * @return the meta object for the containment reference '<em>Element</em>'.
   * @see org.eclipse.emf.cdo.tests.model4.RefSingleContained#getElement()
   * @see #getRefSingleContained()
   * @generated
   */
  EReference getRefSingleContained_Element();

  /**
   * Returns the meta object for class '{@link org.eclipse.emf.cdo.tests.model4.SingleContainedElement
   * <em>Single Contained Element</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @return the meta object for class '<em>Single Contained Element</em>'.
   * @see org.eclipse.emf.cdo.tests.model4.SingleContainedElement
   * @generated
   */
  EClass getSingleContainedElement();

  /**
   * Returns the meta object for the attribute '{@link org.eclipse.emf.cdo.tests.model4.SingleContainedElement#getName
   * <em>Name</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @return the meta object for the attribute '<em>Name</em>'.
   * @see org.eclipse.emf.cdo.tests.model4.SingleContainedElement#getName()
   * @see #getSingleContainedElement()
   * @generated
   */
  EAttribute getSingleContainedElement_Name();

  /**
   * Returns the meta object for the container reference '
   * {@link org.eclipse.emf.cdo.tests.model4.SingleContainedElement#getParent <em>Parent</em>}'. <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * 
   * @return the meta object for the container reference '<em>Parent</em>'.
   * @see org.eclipse.emf.cdo.tests.model4.SingleContainedElement#getParent()
   * @see #getSingleContainedElement()
   * @generated
   */
  EReference getSingleContainedElement_Parent();

  /**
   * Returns the meta object for class '{@link org.eclipse.emf.cdo.tests.model4.RefSingleNonContained
   * <em>Ref Single Non Contained</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @return the meta object for class '<em>Ref Single Non Contained</em>'.
   * @see org.eclipse.emf.cdo.tests.model4.RefSingleNonContained
   * @generated
   */
  EClass getRefSingleNonContained();

  /**
   * Returns the meta object for the reference '
   * {@link org.eclipse.emf.cdo.tests.model4.RefSingleNonContained#getElement <em>Element</em>}'. <!-- begin-user-doc
   * --> <!-- end-user-doc -->
   * 
   * @return the meta object for the reference '<em>Element</em>'.
   * @see org.eclipse.emf.cdo.tests.model4.RefSingleNonContained#getElement()
   * @see #getRefSingleNonContained()
   * @generated
   */
  EReference getRefSingleNonContained_Element();

  /**
   * Returns the meta object for class '{@link org.eclipse.emf.cdo.tests.model4.SingleNonContainedElement
   * <em>Single Non Contained Element</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @return the meta object for class '<em>Single Non Contained Element</em>'.
   * @see org.eclipse.emf.cdo.tests.model4.SingleNonContainedElement
   * @generated
   */
  EClass getSingleNonContainedElement();

  /**
   * Returns the meta object for the attribute '
   * {@link org.eclipse.emf.cdo.tests.model4.SingleNonContainedElement#getName <em>Name</em>}'. <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * 
   * @return the meta object for the attribute '<em>Name</em>'.
   * @see org.eclipse.emf.cdo.tests.model4.SingleNonContainedElement#getName()
   * @see #getSingleNonContainedElement()
   * @generated
   */
  EAttribute getSingleNonContainedElement_Name();

  /**
   * Returns the meta object for the reference '
   * {@link org.eclipse.emf.cdo.tests.model4.SingleNonContainedElement#getParent <em>Parent</em>}'. <!-- begin-user-doc
   * --> <!-- end-user-doc -->
   * 
   * @return the meta object for the reference '<em>Parent</em>'.
   * @see org.eclipse.emf.cdo.tests.model4.SingleNonContainedElement#getParent()
   * @see #getSingleNonContainedElement()
   * @generated
   */
  EReference getSingleNonContainedElement_Parent();

  /**
   * Returns the meta object for class '{@link org.eclipse.emf.cdo.tests.model4.RefMultiContained
   * <em>Ref Multi Contained</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @return the meta object for class '<em>Ref Multi Contained</em>'.
   * @see org.eclipse.emf.cdo.tests.model4.RefMultiContained
   * @generated
   */
  EClass getRefMultiContained();

  /**
   * Returns the meta object for the containment reference list '
   * {@link org.eclipse.emf.cdo.tests.model4.RefMultiContained#getElements <em>Elements</em>}'. <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * 
   * @return the meta object for the containment reference list '<em>Elements</em>'.
   * @see org.eclipse.emf.cdo.tests.model4.RefMultiContained#getElements()
   * @see #getRefMultiContained()
   * @generated
   */
  EReference getRefMultiContained_Elements();

  /**
   * Returns the meta object for class '{@link org.eclipse.emf.cdo.tests.model4.MultiContainedElement
   * <em>Multi Contained Element</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @return the meta object for class '<em>Multi Contained Element</em>'.
   * @see org.eclipse.emf.cdo.tests.model4.MultiContainedElement
   * @generated
   */
  EClass getMultiContainedElement();

  /**
   * Returns the meta object for the attribute '{@link org.eclipse.emf.cdo.tests.model4.MultiContainedElement#getName
   * <em>Name</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @return the meta object for the attribute '<em>Name</em>'.
   * @see org.eclipse.emf.cdo.tests.model4.MultiContainedElement#getName()
   * @see #getMultiContainedElement()
   * @generated
   */
  EAttribute getMultiContainedElement_Name();

  /**
   * Returns the meta object for the container reference '
   * {@link org.eclipse.emf.cdo.tests.model4.MultiContainedElement#getParent <em>Parent</em>}'. <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * 
   * @return the meta object for the container reference '<em>Parent</em>'.
   * @see org.eclipse.emf.cdo.tests.model4.MultiContainedElement#getParent()
   * @see #getMultiContainedElement()
   * @generated
   */
  EReference getMultiContainedElement_Parent();

  /**
   * Returns the meta object for class '{@link org.eclipse.emf.cdo.tests.model4.RefMultiNonContained
   * <em>Ref Multi Non Contained</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @return the meta object for class '<em>Ref Multi Non Contained</em>'.
   * @see org.eclipse.emf.cdo.tests.model4.RefMultiNonContained
   * @generated
   */
  EClass getRefMultiNonContained();

  /**
   * Returns the meta object for the reference list '
   * {@link org.eclipse.emf.cdo.tests.model4.RefMultiNonContained#getElements <em>Elements</em>}'. <!-- begin-user-doc
   * --> <!-- end-user-doc -->
   * 
   * @return the meta object for the reference list '<em>Elements</em>'.
   * @see org.eclipse.emf.cdo.tests.model4.RefMultiNonContained#getElements()
   * @see #getRefMultiNonContained()
   * @generated
   */
  EReference getRefMultiNonContained_Elements();

  /**
   * Returns the meta object for class '{@link org.eclipse.emf.cdo.tests.model4.MultiNonContainedElement
   * <em>Multi Non Contained Element</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @return the meta object for class '<em>Multi Non Contained Element</em>'.
   * @see org.eclipse.emf.cdo.tests.model4.MultiNonContainedElement
   * @generated
   */
  EClass getMultiNonContainedElement();

  /**
   * Returns the meta object for the attribute '
   * {@link org.eclipse.emf.cdo.tests.model4.MultiNonContainedElement#getName <em>Name</em>}'. <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * 
   * @return the meta object for the attribute '<em>Name</em>'.
   * @see org.eclipse.emf.cdo.tests.model4.MultiNonContainedElement#getName()
   * @see #getMultiNonContainedElement()
   * @generated
   */
  EAttribute getMultiNonContainedElement_Name();

  /**
   * Returns the meta object for the reference '
   * {@link org.eclipse.emf.cdo.tests.model4.MultiNonContainedElement#getParent <em>Parent</em>}'. <!-- begin-user-doc
   * --> <!-- end-user-doc -->
   * 
   * @return the meta object for the reference '<em>Parent</em>'.
   * @see org.eclipse.emf.cdo.tests.model4.MultiNonContainedElement#getParent()
   * @see #getMultiNonContainedElement()
   * @generated
   */
  EReference getMultiNonContainedElement_Parent();

  /**
   * Returns the meta object for class '{@link org.eclipse.emf.cdo.tests.model4.RefMultiNonContainedUnsettable
   * <em>Ref Multi Non Contained Unsettable</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @return the meta object for class '<em>Ref Multi Non Contained Unsettable</em>'.
   * @see org.eclipse.emf.cdo.tests.model4.RefMultiNonContainedUnsettable
   * @generated
   */
  EClass getRefMultiNonContainedUnsettable();

  /**
   * Returns the meta object for the reference list '
   * {@link org.eclipse.emf.cdo.tests.model4.RefMultiNonContainedUnsettable#getElements <em>Elements</em>}'. <!--
   * begin-user-doc --> <!-- end-user-doc -->
   * 
   * @return the meta object for the reference list '<em>Elements</em>'.
   * @see org.eclipse.emf.cdo.tests.model4.RefMultiNonContainedUnsettable#getElements()
   * @see #getRefMultiNonContainedUnsettable()
   * @generated
   */
  EReference getRefMultiNonContainedUnsettable_Elements();

  /**
   * Returns the meta object for class '{@link org.eclipse.emf.cdo.tests.model4.MultiNonContainedUnsettableElement
   * <em>Multi Non Contained Unsettable Element</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @return the meta object for class '<em>Multi Non Contained Unsettable Element</em>'.
   * @see org.eclipse.emf.cdo.tests.model4.MultiNonContainedUnsettableElement
   * @generated
   */
  EClass getMultiNonContainedUnsettableElement();

  /**
   * Returns the meta object for the attribute '
   * {@link org.eclipse.emf.cdo.tests.model4.MultiNonContainedUnsettableElement#getName <em>Name</em>}'. <!--
   * begin-user-doc --> <!-- end-user-doc -->
   * 
   * @return the meta object for the attribute '<em>Name</em>'.
   * @see org.eclipse.emf.cdo.tests.model4.MultiNonContainedUnsettableElement#getName()
   * @see #getMultiNonContainedUnsettableElement()
   * @generated
   */
  EAttribute getMultiNonContainedUnsettableElement_Name();

  /**
   * Returns the meta object for the reference '
   * {@link org.eclipse.emf.cdo.tests.model4.MultiNonContainedUnsettableElement#getParent <em>Parent</em>}'. <!--
   * begin-user-doc --> <!-- end-user-doc -->
   * 
   * @return the meta object for the reference '<em>Parent</em>'.
   * @see org.eclipse.emf.cdo.tests.model4.MultiNonContainedUnsettableElement#getParent()
   * @see #getMultiNonContainedUnsettableElement()
   * @generated
   */
  EReference getMultiNonContainedUnsettableElement_Parent();

  /**
   * Returns the meta object for class '{@link org.eclipse.emf.cdo.tests.model4.RefSingleContainedNPL
   * <em>Ref Single Contained NPL</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @return the meta object for class '<em>Ref Single Contained NPL</em>'.
   * @see org.eclipse.emf.cdo.tests.model4.RefSingleContainedNPL
   * @generated
   */
  EClass getRefSingleContainedNPL();

  /**
   * Returns the meta object for the containment reference '
   * {@link org.eclipse.emf.cdo.tests.model4.RefSingleContainedNPL#getElement <em>Element</em>}'. <!-- begin-user-doc
   * --> <!-- end-user-doc -->
   * 
   * @return the meta object for the containment reference '<em>Element</em>'.
   * @see org.eclipse.emf.cdo.tests.model4.RefSingleContainedNPL#getElement()
   * @see #getRefSingleContainedNPL()
   * @generated
   */
  EReference getRefSingleContainedNPL_Element();

  /**
   * Returns the meta object for class '{@link org.eclipse.emf.cdo.tests.model4.RefSingleNonContainedNPL
   * <em>Ref Single Non Contained NPL</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @return the meta object for class '<em>Ref Single Non Contained NPL</em>'.
   * @see org.eclipse.emf.cdo.tests.model4.RefSingleNonContainedNPL
   * @generated
   */
  EClass getRefSingleNonContainedNPL();

  /**
   * Returns the meta object for the reference '
   * {@link org.eclipse.emf.cdo.tests.model4.RefSingleNonContainedNPL#getElement <em>Element</em>}'. <!-- begin-user-doc
   * --> <!-- end-user-doc -->
   * 
   * @return the meta object for the reference '<em>Element</em>'.
   * @see org.eclipse.emf.cdo.tests.model4.RefSingleNonContainedNPL#getElement()
   * @see #getRefSingleNonContainedNPL()
   * @generated
   */
  EReference getRefSingleNonContainedNPL_Element();

  /**
   * Returns the meta object for class '{@link org.eclipse.emf.cdo.tests.model4.RefMultiContainedNPL
   * <em>Ref Multi Contained NPL</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @return the meta object for class '<em>Ref Multi Contained NPL</em>'.
   * @see org.eclipse.emf.cdo.tests.model4.RefMultiContainedNPL
   * @generated
   */
  EClass getRefMultiContainedNPL();

  /**
   * Returns the meta object for the containment reference list '
   * {@link org.eclipse.emf.cdo.tests.model4.RefMultiContainedNPL#getElements <em>Elements</em>}'. <!-- begin-user-doc
   * --> <!-- end-user-doc -->
   * 
   * @return the meta object for the containment reference list '<em>Elements</em>'.
   * @see org.eclipse.emf.cdo.tests.model4.RefMultiContainedNPL#getElements()
   * @see #getRefMultiContainedNPL()
   * @generated
   */
  EReference getRefMultiContainedNPL_Elements();

  /**
   * Returns the meta object for class '{@link org.eclipse.emf.cdo.tests.model4.RefMultiNonContainedNPL
   * <em>Ref Multi Non Contained NPL</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @return the meta object for class '<em>Ref Multi Non Contained NPL</em>'.
   * @see org.eclipse.emf.cdo.tests.model4.RefMultiNonContainedNPL
   * @generated
   */
  EClass getRefMultiNonContainedNPL();

  /**
   * Returns the meta object for the reference list '
   * {@link org.eclipse.emf.cdo.tests.model4.RefMultiNonContainedNPL#getElements <em>Elements</em>}'. <!--
   * begin-user-doc --> <!-- end-user-doc -->
   * 
   * @return the meta object for the reference list '<em>Elements</em>'.
   * @see org.eclipse.emf.cdo.tests.model4.RefMultiNonContainedNPL#getElements()
   * @see #getRefMultiNonContainedNPL()
   * @generated
   */
  EReference getRefMultiNonContainedNPL_Elements();

  /**
   * Returns the meta object for class '{@link org.eclipse.emf.cdo.tests.model4.ContainedElementNoOpposite
   * <em>Contained Element No Opposite</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @return the meta object for class '<em>Contained Element No Opposite</em>'.
   * @see org.eclipse.emf.cdo.tests.model4.ContainedElementNoOpposite
   * @generated
   */
  EClass getContainedElementNoOpposite();

  /**
   * Returns the meta object for the attribute '
   * {@link org.eclipse.emf.cdo.tests.model4.ContainedElementNoOpposite#getName <em>Name</em>}'. <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * 
   * @return the meta object for the attribute '<em>Name</em>'.
   * @see org.eclipse.emf.cdo.tests.model4.ContainedElementNoOpposite#getName()
   * @see #getContainedElementNoOpposite()
   * @generated
   */
  EAttribute getContainedElementNoOpposite_Name();

  /**
   * Returns the meta object for class '{@link org.eclipse.emf.cdo.tests.model4.GenRefSingleContained
   * <em>Gen Ref Single Contained</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @return the meta object for class '<em>Gen Ref Single Contained</em>'.
   * @see org.eclipse.emf.cdo.tests.model4.GenRefSingleContained
   * @generated
   */
  EClass getGenRefSingleContained();

  /**
   * Returns the meta object for the containment reference '
   * {@link org.eclipse.emf.cdo.tests.model4.GenRefSingleContained#getElement <em>Element</em>}'. <!-- begin-user-doc
   * --> <!-- end-user-doc -->
   * 
   * @return the meta object for the containment reference '<em>Element</em>'.
   * @see org.eclipse.emf.cdo.tests.model4.GenRefSingleContained#getElement()
   * @see #getGenRefSingleContained()
   * @generated
   */
  EReference getGenRefSingleContained_Element();

  /**
   * Returns the meta object for class '{@link org.eclipse.emf.cdo.tests.model4.GenRefSingleNonContained
   * <em>Gen Ref Single Non Contained</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @return the meta object for class '<em>Gen Ref Single Non Contained</em>'.
   * @see org.eclipse.emf.cdo.tests.model4.GenRefSingleNonContained
   * @generated
   */
  EClass getGenRefSingleNonContained();

  /**
   * Returns the meta object for the reference '
   * {@link org.eclipse.emf.cdo.tests.model4.GenRefSingleNonContained#getElement <em>Element</em>}'. <!-- begin-user-doc
   * --> <!-- end-user-doc -->
   * 
   * @return the meta object for the reference '<em>Element</em>'.
   * @see org.eclipse.emf.cdo.tests.model4.GenRefSingleNonContained#getElement()
   * @see #getGenRefSingleNonContained()
   * @generated
   */
  EReference getGenRefSingleNonContained_Element();

  /**
   * Returns the meta object for class '{@link org.eclipse.emf.cdo.tests.model4.GenRefMultiContained
   * <em>Gen Ref Multi Contained</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @return the meta object for class '<em>Gen Ref Multi Contained</em>'.
   * @see org.eclipse.emf.cdo.tests.model4.GenRefMultiContained
   * @generated
   */
  EClass getGenRefMultiContained();

  /**
   * Returns the meta object for the containment reference list '
   * {@link org.eclipse.emf.cdo.tests.model4.GenRefMultiContained#getElements <em>Elements</em>}'. <!-- begin-user-doc
   * --> <!-- end-user-doc -->
   * 
   * @return the meta object for the containment reference list '<em>Elements</em>'.
   * @see org.eclipse.emf.cdo.tests.model4.GenRefMultiContained#getElements()
   * @see #getGenRefMultiContained()
   * @generated
   */
  EReference getGenRefMultiContained_Elements();

  /**
   * Returns the meta object for class '{@link org.eclipse.emf.cdo.tests.model4.GenRefMultiNonContained
   * <em>Gen Ref Multi Non Contained</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @return the meta object for class '<em>Gen Ref Multi Non Contained</em>'.
   * @see org.eclipse.emf.cdo.tests.model4.GenRefMultiNonContained
   * @generated
   */
  EClass getGenRefMultiNonContained();

  /**
   * Returns the meta object for the reference list '
   * {@link org.eclipse.emf.cdo.tests.model4.GenRefMultiNonContained#getElements <em>Elements</em>}'. <!--
   * begin-user-doc --> <!-- end-user-doc -->
   * 
   * @return the meta object for the reference list '<em>Elements</em>'.
   * @see org.eclipse.emf.cdo.tests.model4.GenRefMultiNonContained#getElements()
   * @see #getGenRefMultiNonContained()
   * @generated
   */
  EReference getGenRefMultiNonContained_Elements();

  /**
   * Returns the meta object for class '{@link org.eclipse.emf.cdo.tests.model4.ImplSingleRefContainer
   * <em>Impl Single Ref Container</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @return the meta object for class '<em>Impl Single Ref Container</em>'.
   * @see org.eclipse.emf.cdo.tests.model4.ImplSingleRefContainer
   * @generated
   */
  EClass getImplSingleRefContainer();

  /**
   * Returns the meta object for class '{@link org.eclipse.emf.cdo.tests.model4.ImplSingleRefContainedElement
   * <em>Impl Single Ref Contained Element</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @return the meta object for class '<em>Impl Single Ref Contained Element</em>'.
   * @see org.eclipse.emf.cdo.tests.model4.ImplSingleRefContainedElement
   * @generated
   */
  EClass getImplSingleRefContainedElement();

  /**
   * Returns the meta object for the attribute '
   * {@link org.eclipse.emf.cdo.tests.model4.ImplSingleRefContainedElement#getName <em>Name</em>}'. <!-- begin-user-doc
   * --> <!-- end-user-doc -->
   * 
   * @return the meta object for the attribute '<em>Name</em>'.
   * @see org.eclipse.emf.cdo.tests.model4.ImplSingleRefContainedElement#getName()
   * @see #getImplSingleRefContainedElement()
   * @generated
   */
  EAttribute getImplSingleRefContainedElement_Name();

  /**
   * Returns the meta object for class '{@link org.eclipse.emf.cdo.tests.model4.ImplSingleRefNonContainer
   * <em>Impl Single Ref Non Container</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @return the meta object for class '<em>Impl Single Ref Non Container</em>'.
   * @see org.eclipse.emf.cdo.tests.model4.ImplSingleRefNonContainer
   * @generated
   */
  EClass getImplSingleRefNonContainer();

  /**
   * Returns the meta object for class '{@link org.eclipse.emf.cdo.tests.model4.ImplSingleRefNonContainedElement
   * <em>Impl Single Ref Non Contained Element</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @return the meta object for class '<em>Impl Single Ref Non Contained Element</em>'.
   * @see org.eclipse.emf.cdo.tests.model4.ImplSingleRefNonContainedElement
   * @generated
   */
  EClass getImplSingleRefNonContainedElement();

  /**
   * Returns the meta object for the attribute '
   * {@link org.eclipse.emf.cdo.tests.model4.ImplSingleRefNonContainedElement#getName <em>Name</em>}'. <!--
   * begin-user-doc --> <!-- end-user-doc -->
   * 
   * @return the meta object for the attribute '<em>Name</em>'.
   * @see org.eclipse.emf.cdo.tests.model4.ImplSingleRefNonContainedElement#getName()
   * @see #getImplSingleRefNonContainedElement()
   * @generated
   */
  EAttribute getImplSingleRefNonContainedElement_Name();

  /**
   * Returns the meta object for class '{@link org.eclipse.emf.cdo.tests.model4.ImplMultiRefNonContainer
   * <em>Impl Multi Ref Non Container</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @return the meta object for class '<em>Impl Multi Ref Non Container</em>'.
   * @see org.eclipse.emf.cdo.tests.model4.ImplMultiRefNonContainer
   * @generated
   */
  EClass getImplMultiRefNonContainer();

  /**
   * Returns the meta object for class '{@link org.eclipse.emf.cdo.tests.model4.ImplMultiRefNonContainedElement
   * <em>Impl Multi Ref Non Contained Element</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @return the meta object for class '<em>Impl Multi Ref Non Contained Element</em>'.
   * @see org.eclipse.emf.cdo.tests.model4.ImplMultiRefNonContainedElement
   * @generated
   */
  EClass getImplMultiRefNonContainedElement();

  /**
   * Returns the meta object for the attribute '
   * {@link org.eclipse.emf.cdo.tests.model4.ImplMultiRefNonContainedElement#getName <em>Name</em>}'. <!--
   * begin-user-doc --> <!-- end-user-doc -->
   * 
   * @return the meta object for the attribute '<em>Name</em>'.
   * @see org.eclipse.emf.cdo.tests.model4.ImplMultiRefNonContainedElement#getName()
   * @see #getImplMultiRefNonContainedElement()
   * @generated
   */
  EAttribute getImplMultiRefNonContainedElement_Name();

  /**
   * Returns the meta object for class '{@link org.eclipse.emf.cdo.tests.model4.ImplMultiRefContainer
   * <em>Impl Multi Ref Container</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @return the meta object for class '<em>Impl Multi Ref Container</em>'.
   * @see org.eclipse.emf.cdo.tests.model4.ImplMultiRefContainer
   * @generated
   */
  EClass getImplMultiRefContainer();

  /**
   * Returns the meta object for class '{@link org.eclipse.emf.cdo.tests.model4.ImplMultiRefContainedElement
   * <em>Impl Multi Ref Contained Element</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @return the meta object for class '<em>Impl Multi Ref Contained Element</em>'.
   * @see org.eclipse.emf.cdo.tests.model4.ImplMultiRefContainedElement
   * @generated
   */
  EClass getImplMultiRefContainedElement();

  /**
   * Returns the meta object for the attribute '
   * {@link org.eclipse.emf.cdo.tests.model4.ImplMultiRefContainedElement#getName <em>Name</em>}'. <!-- begin-user-doc
   * --> <!-- end-user-doc -->
   * 
   * @return the meta object for the attribute '<em>Name</em>'.
   * @see org.eclipse.emf.cdo.tests.model4.ImplMultiRefContainedElement#getName()
   * @see #getImplMultiRefContainedElement()
   * @generated
   */
  EAttribute getImplMultiRefContainedElement_Name();

  /**
   * Returns the meta object for class '{@link org.eclipse.emf.cdo.tests.model4.ImplSingleRefContainerNPL
   * <em>Impl Single Ref Container NPL</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @return the meta object for class '<em>Impl Single Ref Container NPL</em>'.
   * @see org.eclipse.emf.cdo.tests.model4.ImplSingleRefContainerNPL
   * @generated
   */
  EClass getImplSingleRefContainerNPL();

  /**
   * Returns the meta object for class '{@link org.eclipse.emf.cdo.tests.model4.ImplSingleRefNonContainerNPL
   * <em>Impl Single Ref Non Container NPL</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @return the meta object for class '<em>Impl Single Ref Non Container NPL</em>'.
   * @see org.eclipse.emf.cdo.tests.model4.ImplSingleRefNonContainerNPL
   * @generated
   */
  EClass getImplSingleRefNonContainerNPL();

  /**
   * Returns the meta object for class '{@link org.eclipse.emf.cdo.tests.model4.ImplMultiRefContainerNPL
   * <em>Impl Multi Ref Container NPL</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @return the meta object for class '<em>Impl Multi Ref Container NPL</em>'.
   * @see org.eclipse.emf.cdo.tests.model4.ImplMultiRefContainerNPL
   * @generated
   */
  EClass getImplMultiRefContainerNPL();

  /**
   * Returns the meta object for class '{@link org.eclipse.emf.cdo.tests.model4.ImplMultiRefNonContainerNPL
   * <em>Impl Multi Ref Non Container NPL</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @return the meta object for class '<em>Impl Multi Ref Non Container NPL</em>'.
   * @see org.eclipse.emf.cdo.tests.model4.ImplMultiRefNonContainerNPL
   * @generated
   */
  EClass getImplMultiRefNonContainerNPL();

  /**
   * Returns the meta object for class '{@link org.eclipse.emf.cdo.tests.model4.ImplContainedElementNPL
   * <em>Impl Contained Element NPL</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @return the meta object for class '<em>Impl Contained Element NPL</em>'.
   * @see org.eclipse.emf.cdo.tests.model4.ImplContainedElementNPL
   * @generated
   */
  EClass getImplContainedElementNPL();

  /**
   * Returns the meta object for class '{@link org.eclipse.emf.cdo.tests.model4.GenRefMultiNUNonContained
   * <em>Gen Ref Multi NU Non Contained</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @return the meta object for class '<em>Gen Ref Multi NU Non Contained</em>'.
   * @see org.eclipse.emf.cdo.tests.model4.GenRefMultiNUNonContained
   * @generated
   */
  EClass getGenRefMultiNUNonContained();

  /**
   * Returns the meta object for the reference list '
   * {@link org.eclipse.emf.cdo.tests.model4.GenRefMultiNUNonContained#getElements <em>Elements</em>}'. <!--
   * begin-user-doc --> <!-- end-user-doc -->
   * 
   * @return the meta object for the reference list '<em>Elements</em>'.
   * @see org.eclipse.emf.cdo.tests.model4.GenRefMultiNUNonContained#getElements()
   * @see #getGenRefMultiNUNonContained()
   * @generated
   */
  EReference getGenRefMultiNUNonContained_Elements();

  /**
   * Returns the meta object for class '{@link org.eclipse.emf.cdo.tests.model4.GenRefMapNonContained
   * <em>Gen Ref Map Non Contained</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @return the meta object for class '<em>Gen Ref Map Non Contained</em>'.
   * @see org.eclipse.emf.cdo.tests.model4.GenRefMapNonContained
   * @generated
   */
  EClass getGenRefMapNonContained();

  /**
   * Returns the meta object for the map '{@link org.eclipse.emf.cdo.tests.model4.GenRefMapNonContained#getElements
   * <em>Elements</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @return the meta object for the map '<em>Elements</em>'.
   * @see org.eclipse.emf.cdo.tests.model4.GenRefMapNonContained#getElements()
   * @see #getGenRefMapNonContained()
   * @generated
   */
  EReference getGenRefMapNonContained_Elements();

  /**
   * Returns the meta object for class '{@link java.util.Map.Entry <em>String To EObject</em>}'. <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * 
   * @return the meta object for class '<em>String To EObject</em>'.
   * @see java.util.Map.Entry
   * @model keyDataType="org.eclipse.emf.ecore.EString" valueType="org.eclipse.emf.ecore.EObject"
   *        valueResolveProxies="false"
   * @generated
   */
  EClass getStringToEObject();

  /**
   * Returns the meta object for the attribute '{@link java.util.Map.Entry <em>Key</em>}'. <!-- begin-user-doc --> <!--
   * end-user-doc -->
   * 
   * @return the meta object for the attribute '<em>Key</em>'.
   * @see java.util.Map.Entry
   * @see #getStringToEObject()
   * @generated
   */
  EAttribute getStringToEObject_Key();

  /**
   * Returns the meta object for the reference '{@link java.util.Map.Entry <em>Value</em>}'. <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * 
   * @return the meta object for the reference '<em>Value</em>'.
   * @see java.util.Map.Entry
   * @see #getStringToEObject()
   * @generated
   */
  EReference getStringToEObject_Value();

  /**
   * Returns the factory that creates the instances of the model. <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @return the factory that creates the instances of the model.
   * @generated
   */
  model4Factory getmodel4Factory();

} // model4Package
