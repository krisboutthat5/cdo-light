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
package base;

import org.eclipse.emf.cdo.CDOObject;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Class</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link base.BaseClass#getCouter <em>Couter</em>}</li>
 * </ul>
 * </p>
 * 
 * @see base.BasePackage#getBaseClass()
 * @model
 * @extends CDOObject
 * @generated
 */
public interface BaseClass extends CDOObject
{
  /**
   * Returns the value of the '<em><b>Couter</b></em>' attribute. <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Couter</em>' attribute isn't clear, there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * 
   * @return the value of the '<em>Couter</em>' attribute.
   * @see #setCouter(int)
   * @see base.BasePackage#getBaseClass_Couter()
   * @model
   * @generated
   */
  int getCouter();

  /**
   * Sets the value of the '{@link base.BaseClass#getCouter <em>Couter</em>}' attribute. <!-- begin-user-doc --> <!--
   * end-user-doc -->
   * 
   * @param value
   *          the new value of the '<em>Couter</em>' attribute.
   * @see #getCouter()
   * @generated
   */
  void setCouter(int value);

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @model
   * @generated
   */
  void increment();

} // BaseClass
