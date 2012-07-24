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
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>G</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.emf.cdo.tests.model6.G#getDummy <em>Dummy</em>}</li>
 *   <li>{@link org.eclipse.emf.cdo.tests.model6.G#getReference <em>Reference</em>}</li>
 *   <li>{@link org.eclipse.emf.cdo.tests.model6.G#getList <em>List</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.emf.cdo.tests.model6.Model6Package#getG()
 * @model
 * @generated
 */
public interface G extends EObject
{
  /**
   * Returns the value of the '<em><b>Dummy</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Dummy</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Dummy</em>' attribute.
   * @see #setDummy(String)
   * @see org.eclipse.emf.cdo.tests.model6.Model6Package#getG_Dummy()
   * @model required="true"
   * @generated
   */
  String getDummy();

  /**
   * Sets the value of the '{@link org.eclipse.emf.cdo.tests.model6.G#getDummy <em>Dummy</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Dummy</em>' attribute.
   * @see #getDummy()
   * @generated
   */
  void setDummy(String value);

  /**
   * Returns the value of the '<em><b>Reference</b></em>' reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Reference</em>' reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Reference</em>' reference.
   * @see #setReference(BaseObject)
   * @see org.eclipse.emf.cdo.tests.model6.Model6Package#getG_Reference()
   * @model required="true"
   * @generated
   */
  BaseObject getReference();

  /**
   * Sets the value of the '{@link org.eclipse.emf.cdo.tests.model6.G#getReference <em>Reference</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Reference</em>' reference.
   * @see #getReference()
   * @generated
   */
  void setReference(BaseObject value);

  /**
   * Returns the value of the '<em><b>List</b></em>' reference list.
   * The list contents are of type {@link org.eclipse.emf.cdo.tests.model6.BaseObject}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>List</em>' reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>List</em>' reference list.
   * @see org.eclipse.emf.cdo.tests.model6.Model6Package#getG_List()
   * @model
   * @generated
   */
  EList<BaseObject> getList();

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @model kind="operation" required="true"
   * @generated
   */
  boolean isAttributeModified();

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @model kind="operation" required="true"
   * @generated
   */
  boolean isReferenceModified();

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @model kind="operation" required="true"
   * @generated
   */
  boolean isListModified();

} // G
