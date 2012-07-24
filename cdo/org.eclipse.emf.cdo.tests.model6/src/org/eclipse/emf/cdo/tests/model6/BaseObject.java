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
package org.eclipse.emf.cdo.tests.model6;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Base Object</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.emf.cdo.tests.model6.BaseObject#getAttributeOptional <em>Attribute Optional</em>}</li>
 *   <li>{@link org.eclipse.emf.cdo.tests.model6.BaseObject#getAttributeRequired <em>Attribute Required</em>}</li>
 *   <li>{@link org.eclipse.emf.cdo.tests.model6.BaseObject#getAttributeList <em>Attribute List</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.emf.cdo.tests.model6.Model6Package#getBaseObject()
 * @model
 * @generated
 */
public interface BaseObject extends EObject
{
  /**
   * Returns the value of the '<em><b>Attribute Optional</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Attribute Optional</em>' attribute isn't clear, there really should be more of a
   * description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Attribute Optional</em>' attribute.
   * @see #setAttributeOptional(String)
   * @see org.eclipse.emf.cdo.tests.model6.Model6Package#getBaseObject_AttributeOptional()
   * @model
   * @generated
   */
  String getAttributeOptional();

  /**
   * Sets the value of the '{@link org.eclipse.emf.cdo.tests.model6.BaseObject#getAttributeOptional <em>Attribute Optional</em>}' attribute.
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * @param value the new value of the '<em>Attribute Optional</em>' attribute.
   * @see #getAttributeOptional()
   * @generated
   */
  void setAttributeOptional(String value);

  /**
   * Returns the value of the '<em><b>Attribute Required</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Attribute Required</em>' attribute isn't clear, there really should be more of a
   * description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Attribute Required</em>' attribute.
   * @see #setAttributeRequired(String)
   * @see org.eclipse.emf.cdo.tests.model6.Model6Package#getBaseObject_AttributeRequired()
   * @model required="true"
   * @generated
   */
  String getAttributeRequired();

  /**
   * Sets the value of the '{@link org.eclipse.emf.cdo.tests.model6.BaseObject#getAttributeRequired <em>Attribute Required</em>}' attribute.
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * @param value the new value of the '<em>Attribute Required</em>' attribute.
   * @see #getAttributeRequired()
   * @generated
   */
  void setAttributeRequired(String value);

  /**
   * Returns the value of the '<em><b>Attribute List</b></em>' attribute list.
   * The list contents are of type {@link java.lang.String}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Attribute List</em>' attribute list isn't clear, there really should be more of a
   * description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Attribute List</em>' attribute list.
   * @see org.eclipse.emf.cdo.tests.model6.Model6Package#getBaseObject_AttributeList()
   * @model
   * @generated
   */
  EList<String> getAttributeList();

} // BaseObject
