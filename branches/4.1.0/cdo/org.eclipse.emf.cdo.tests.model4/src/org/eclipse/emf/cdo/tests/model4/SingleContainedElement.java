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

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Single Contained Element</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.eclipse.emf.cdo.tests.model4.SingleContainedElement#getName <em>Name</em>}</li>
 * <li>{@link org.eclipse.emf.cdo.tests.model4.SingleContainedElement#getParent <em>Parent</em>}</li>
 * </ul>
 * </p>
 * 
 * @see org.eclipse.emf.cdo.tests.model4.model4Package#getSingleContainedElement()
 * @model
 * @generated
 */
public interface SingleContainedElement extends EObject
{
  /**
   * Returns the value of the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Name</em>' attribute isn't clear, there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * 
   * @return the value of the '<em>Name</em>' attribute.
   * @see #setName(String)
   * @see org.eclipse.emf.cdo.tests.model4.model4Package#getSingleContainedElement_Name()
   * @model
   * @generated
   */
  String getName();

  /**
   * Sets the value of the '{@link org.eclipse.emf.cdo.tests.model4.SingleContainedElement#getName <em>Name</em>}'
   * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @param value
   *          the new value of the '<em>Name</em>' attribute.
   * @see #getName()
   * @generated
   */
  void setName(String value);

  /**
   * Returns the value of the '<em><b>Parent</b></em>' container reference. It is bidirectional and its opposite is '
   * {@link org.eclipse.emf.cdo.tests.model4.RefSingleContained#getElement <em>Element</em>}'. <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Parent</em>' container reference isn't clear, there really should be more of a
   * description here...
   * </p>
   * <!-- end-user-doc -->
   * 
   * @return the value of the '<em>Parent</em>' container reference.
   * @see #setParent(RefSingleContained)
   * @see org.eclipse.emf.cdo.tests.model4.model4Package#getSingleContainedElement_Parent()
   * @see org.eclipse.emf.cdo.tests.model4.RefSingleContained#getElement
   * @model opposite="element" transient="false"
   * @generated
   */
  RefSingleContained getParent();

  /**
   * Sets the value of the '{@link org.eclipse.emf.cdo.tests.model4.SingleContainedElement#getParent <em>Parent</em>}'
   * container reference. <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @param value
   *          the new value of the '<em>Parent</em>' container reference.
   * @see #getParent()
   * @generated
   */
  void setParent(RefSingleContained value);

} // SingleContainedElement
