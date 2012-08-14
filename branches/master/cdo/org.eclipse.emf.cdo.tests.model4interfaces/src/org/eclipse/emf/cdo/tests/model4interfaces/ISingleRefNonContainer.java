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
package org.eclipse.emf.cdo.tests.model4interfaces;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>ISingle Ref Non Container</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.eclipse.emf.cdo.tests.model4interfaces.ISingleRefNonContainer#getElement <em>Element</em>}</li>
 * </ul>
 * </p>
 * 
 * @see org.eclipse.emf.cdo.tests.model4interfaces.model4interfacesPackage#getISingleRefNonContainer()
 * @model interface="true" abstract="true"
 * @generated
 */
public interface ISingleRefNonContainer extends EObject
{
  /**
   * Returns the value of the '<em><b>Element</b></em>' reference. It is bidirectional and its opposite is '
   * {@link org.eclipse.emf.cdo.tests.model4interfaces.ISingleRefNonContainedElement#getParent <em>Parent</em>}'. <!--
   * begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Element</em>' reference isn't clear, there really should be more of a description
   * here...
   * </p>
   * <!-- end-user-doc -->
   * 
   * @return the value of the '<em>Element</em>' reference.
   * @see #setElement(ISingleRefNonContainedElement)
   * @see org.eclipse.emf.cdo.tests.model4interfaces.model4interfacesPackage#getISingleRefNonContainer_Element()
   * @see org.eclipse.emf.cdo.tests.model4interfaces.ISingleRefNonContainedElement#getParent
   * @model opposite="parent"
   * @generated
   */
  ISingleRefNonContainedElement getElement();

  /**
   * Sets the value of the '{@link org.eclipse.emf.cdo.tests.model4interfaces.ISingleRefNonContainer#getElement
   * <em>Element</em>}' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @param value
   *          the new value of the '<em>Element</em>' reference.
   * @see #getElement()
   * @generated
   */
  void setElement(ISingleRefNonContainedElement value);

} // ISingleRefNonContainer
