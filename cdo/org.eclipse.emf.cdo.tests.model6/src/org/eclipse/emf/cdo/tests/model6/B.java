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

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>B</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.emf.cdo.tests.model6.B#getOwnedC <em>Owned C</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.emf.cdo.tests.model6.Model6Package#getB()
 * @model
 * @generated
 */
public interface B extends EObject
{
  /**
   * Returns the value of the '<em><b>Owned C</b></em>' containment reference. <!-- begin-user-doc --> <!-- end-user-doc
   * --> <!-- begin-model-doc --> The style of the node. <!-- end-model-doc -->
   *
   * @return the value of the '<em>Owned C</em>' containment reference.
   * @see #setOwnedC(C)
   * @see org.eclipse.emf.cdo.tests.model6.Model6Package#getB_OwnedC()
   * @model containment="true"
   * @generated
   */
  C getOwnedC();

  /**
   * Sets the value of the '{@link org.eclipse.emf.cdo.tests.model6.B#getOwnedC <em>Owned C</em>}' containment reference.
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * @param value the new value of the '<em>Owned C</em>' containment reference.
   * @see #getOwnedC()
   * @generated
   */
  void setOwnedC(C value);

} // B
