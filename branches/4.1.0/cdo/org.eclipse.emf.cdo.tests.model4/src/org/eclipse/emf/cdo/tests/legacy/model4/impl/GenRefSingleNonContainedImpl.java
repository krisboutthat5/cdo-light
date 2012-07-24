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
package org.eclipse.emf.cdo.tests.legacy.model4.impl;

import org.eclipse.emf.cdo.tests.legacy.model4.model4Package;
import org.eclipse.emf.cdo.tests.model4.GenRefSingleNonContained;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Gen Ref Single Non Contained</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link org.eclipse.emf.cdo.tests.legacy.model4.impl.GenRefSingleNonContainedImpl#getElement <em>Element</em>}</li>
 * </ul>
 * </p>
 * 
 * @generated
 */
public class GenRefSingleNonContainedImpl extends EObjectImpl implements GenRefSingleNonContained
{
  /**
   * The cached value of the '{@link #getElement() <em>Element</em>}' reference. <!-- begin-user-doc --> <!--
   * end-user-doc -->
   * 
   * @see #getElement()
   * @generated
   * @ordered
   */
  protected EObject element;

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  protected GenRefSingleNonContainedImpl()
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
    return model4Package.eINSTANCE.getGenRefSingleNonContained();
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  public EObject getElement()
  {
    if (element != null && element.eIsProxy())
    {
      InternalEObject oldElement = (InternalEObject)element;
      element = eResolveProxy(oldElement);
      if (element != oldElement)
      {
        if (eNotificationRequired())
          eNotify(new ENotificationImpl(this, Notification.RESOLVE,
              model4Package.GEN_REF_SINGLE_NON_CONTAINED__ELEMENT, oldElement, element));
      }
    }
    return element;
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  public EObject basicGetElement()
  {
    return element;
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  public void setElement(EObject newElement)
  {
    EObject oldElement = element;
    element = newElement;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, model4Package.GEN_REF_SINGLE_NON_CONTAINED__ELEMENT,
          oldElement, element));
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  @Override
  public Object eGet(int featureID, boolean resolve, boolean coreType)
  {
    switch (featureID)
    {
    case model4Package.GEN_REF_SINGLE_NON_CONTAINED__ELEMENT:
      if (resolve)
        return getElement();
      return basicGetElement();
    }
    return super.eGet(featureID, resolve, coreType);
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  @Override
  public void eSet(int featureID, Object newValue)
  {
    switch (featureID)
    {
    case model4Package.GEN_REF_SINGLE_NON_CONTAINED__ELEMENT:
      setElement((EObject)newValue);
      return;
    }
    super.eSet(featureID, newValue);
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  @Override
  public void eUnset(int featureID)
  {
    switch (featureID)
    {
    case model4Package.GEN_REF_SINGLE_NON_CONTAINED__ELEMENT:
      setElement((EObject)null);
      return;
    }
    super.eUnset(featureID);
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  @Override
  public boolean eIsSet(int featureID)
  {
    switch (featureID)
    {
    case model4Package.GEN_REF_SINGLE_NON_CONTAINED__ELEMENT:
      return element != null;
    }
    return super.eIsSet(featureID);
  }

} // GenRefSingleNonContainedImpl
