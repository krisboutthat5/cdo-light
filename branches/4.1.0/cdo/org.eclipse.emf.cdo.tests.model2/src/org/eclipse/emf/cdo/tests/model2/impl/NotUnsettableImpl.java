/*
 * Copyright (c) 2004 - 2012 Eike Stepper (Berlin, Germany) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *    Eike Stepper - initial API and implementation
 *
 * $Id: NotUnsettableImpl.java,v 1.2 2011-01-01 11:01:57 estepper Exp $
 */
package org.eclipse.emf.cdo.tests.model2.impl;

import org.eclipse.emf.cdo.tests.model1.VAT;
import org.eclipse.emf.cdo.tests.model2.Model2Package;
import org.eclipse.emf.cdo.tests.model2.NotUnsettable;

import org.eclipse.emf.internal.cdo.CDOObjectImpl;

import org.eclipse.emf.ecore.EClass;

import java.util.Date;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Not Unsettable</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link org.eclipse.emf.cdo.tests.model2.impl.NotUnsettableImpl#isNotUnsettableBoolean <em>Not Unsettable Boolean
 * </em>}</li>
 * <li>{@link org.eclipse.emf.cdo.tests.model2.impl.NotUnsettableImpl#getNotUnsettableByte <em>Not Unsettable Byte</em>}
 * </li>
 * <li>{@link org.eclipse.emf.cdo.tests.model2.impl.NotUnsettableImpl#getNotUnsettableChar <em>Not Unsettable Char</em>}
 * </li>
 * <li>{@link org.eclipse.emf.cdo.tests.model2.impl.NotUnsettableImpl#getNotUnsettableDate <em>Not Unsettable Date</em>}
 * </li>
 * <li>{@link org.eclipse.emf.cdo.tests.model2.impl.NotUnsettableImpl#getNotUnsettableDouble <em>Not Unsettable Double
 * </em>}</li>
 * <li>{@link org.eclipse.emf.cdo.tests.model2.impl.NotUnsettableImpl#getNotUnsettableFloat <em>Not Unsettable Float
 * </em>}</li>
 * <li>{@link org.eclipse.emf.cdo.tests.model2.impl.NotUnsettableImpl#getNotUnsettableInt <em>Not Unsettable Int</em>}</li>
 * <li>{@link org.eclipse.emf.cdo.tests.model2.impl.NotUnsettableImpl#getNotUnsettableLong <em>Not Unsettable Long</em>}
 * </li>
 * <li>{@link org.eclipse.emf.cdo.tests.model2.impl.NotUnsettableImpl#getNotUnsettableShort <em>Not Unsettable Short
 * </em>}</li>
 * <li>{@link org.eclipse.emf.cdo.tests.model2.impl.NotUnsettableImpl#getNotUnsettableString <em>Not Unsettable String
 * </em>}</li>
 * <li>{@link org.eclipse.emf.cdo.tests.model2.impl.NotUnsettableImpl#getNotUnsettableVAT <em>Not Unsettable VAT</em>}</li>
 * </ul>
 * </p>
 * 
 * @generated
 */
public class NotUnsettableImpl extends CDOObjectImpl implements NotUnsettable
{
  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  protected NotUnsettableImpl()
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
    return Model2Package.eINSTANCE.getNotUnsettable();
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
  public boolean isNotUnsettableBoolean()
  {
    return (Boolean)eGet(Model2Package.eINSTANCE.getNotUnsettable_NotUnsettableBoolean(), true);
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  public void setNotUnsettableBoolean(boolean newNotUnsettableBoolean)
  {
    eSet(Model2Package.eINSTANCE.getNotUnsettable_NotUnsettableBoolean(), newNotUnsettableBoolean);
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  public byte getNotUnsettableByte()
  {
    return (Byte)eGet(Model2Package.eINSTANCE.getNotUnsettable_NotUnsettableByte(), true);
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  public void setNotUnsettableByte(byte newNotUnsettableByte)
  {
    eSet(Model2Package.eINSTANCE.getNotUnsettable_NotUnsettableByte(), newNotUnsettableByte);
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  public char getNotUnsettableChar()
  {
    return (Character)eGet(Model2Package.eINSTANCE.getNotUnsettable_NotUnsettableChar(), true);
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  public void setNotUnsettableChar(char newNotUnsettableChar)
  {
    eSet(Model2Package.eINSTANCE.getNotUnsettable_NotUnsettableChar(), newNotUnsettableChar);
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  public Date getNotUnsettableDate()
  {
    return (Date)eGet(Model2Package.eINSTANCE.getNotUnsettable_NotUnsettableDate(), true);
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  public void setNotUnsettableDate(Date newNotUnsettableDate)
  {
    eSet(Model2Package.eINSTANCE.getNotUnsettable_NotUnsettableDate(), newNotUnsettableDate);
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  public double getNotUnsettableDouble()
  {
    return (Double)eGet(Model2Package.eINSTANCE.getNotUnsettable_NotUnsettableDouble(), true);
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  public void setNotUnsettableDouble(double newNotUnsettableDouble)
  {
    eSet(Model2Package.eINSTANCE.getNotUnsettable_NotUnsettableDouble(), newNotUnsettableDouble);
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  public float getNotUnsettableFloat()
  {
    return (Float)eGet(Model2Package.eINSTANCE.getNotUnsettable_NotUnsettableFloat(), true);
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  public void setNotUnsettableFloat(float newNotUnsettableFloat)
  {
    eSet(Model2Package.eINSTANCE.getNotUnsettable_NotUnsettableFloat(), newNotUnsettableFloat);
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  public int getNotUnsettableInt()
  {
    return (Integer)eGet(Model2Package.eINSTANCE.getNotUnsettable_NotUnsettableInt(), true);
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  public void setNotUnsettableInt(int newNotUnsettableInt)
  {
    eSet(Model2Package.eINSTANCE.getNotUnsettable_NotUnsettableInt(), newNotUnsettableInt);
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  public long getNotUnsettableLong()
  {
    return (Long)eGet(Model2Package.eINSTANCE.getNotUnsettable_NotUnsettableLong(), true);
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  public void setNotUnsettableLong(long newNotUnsettableLong)
  {
    eSet(Model2Package.eINSTANCE.getNotUnsettable_NotUnsettableLong(), newNotUnsettableLong);
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  public short getNotUnsettableShort()
  {
    return (Short)eGet(Model2Package.eINSTANCE.getNotUnsettable_NotUnsettableShort(), true);
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  public void setNotUnsettableShort(short newNotUnsettableShort)
  {
    eSet(Model2Package.eINSTANCE.getNotUnsettable_NotUnsettableShort(), newNotUnsettableShort);
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  public String getNotUnsettableString()
  {
    return (String)eGet(Model2Package.eINSTANCE.getNotUnsettable_NotUnsettableString(), true);
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  public void setNotUnsettableString(String newNotUnsettableString)
  {
    eSet(Model2Package.eINSTANCE.getNotUnsettable_NotUnsettableString(), newNotUnsettableString);
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  public VAT getNotUnsettableVAT()
  {
    return (VAT)eGet(Model2Package.eINSTANCE.getNotUnsettable_NotUnsettableVAT(), true);
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  public void setNotUnsettableVAT(VAT newNotUnsettableVAT)
  {
    eSet(Model2Package.eINSTANCE.getNotUnsettable_NotUnsettableVAT(), newNotUnsettableVAT);
  }

} // NotUnsettableImpl
