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
package org.eclipse.emf.cdo.tests.legacy.model2;

import org.eclipse.emf.cdo.tests.model2.EnumListHolder;
import org.eclipse.emf.cdo.tests.model2.MapHolder;
import org.eclipse.emf.cdo.tests.model2.NotUnsettable;
import org.eclipse.emf.cdo.tests.model2.NotUnsettableWithDefault;
import org.eclipse.emf.cdo.tests.model2.PersistentContainment;
import org.eclipse.emf.cdo.tests.model2.SpecialPurchaseOrder;
import org.eclipse.emf.cdo.tests.model2.Task;
import org.eclipse.emf.cdo.tests.model2.TaskContainer;
import org.eclipse.emf.cdo.tests.model2.TransientContainer;
import org.eclipse.emf.cdo.tests.model2.Unsettable1;
import org.eclipse.emf.cdo.tests.model2.Unsettable2WithDefault;

/**
 * <!-- begin-user-doc --> The <b>Factory</b> for the model. It provides a create method for each non-abstract class of
 * the model. <!-- end-user-doc -->
 * 
 * @see org.eclipse.emf.cdo.tests.legacy.model2.Model2Package
 * @generated
 */
public interface Model2Factory extends org.eclipse.emf.cdo.tests.model2.Model2Factory
{
  /**
   * The singleton instance of the factory. <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  Model2Factory eINSTANCE = org.eclipse.emf.cdo.tests.legacy.model2.impl.Model2FactoryImpl.init();

  /**
   * Returns a new object of class '<em>Special Purchase Order</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @return a new object of class '<em>Special Purchase Order</em>'.
   * @generated
   */
  SpecialPurchaseOrder createSpecialPurchaseOrder();

  /**
   * Returns a new object of class '<em>Task Container</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @return a new object of class '<em>Task Container</em>'.
   * @generated
   */
  TaskContainer createTaskContainer();

  /**
   * Returns a new object of class '<em>Task</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @return a new object of class '<em>Task</em>'.
   * @generated
   */
  Task createTask();

  /**
   * Returns a new object of class '<em>Unsettable1</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @return a new object of class '<em>Unsettable1</em>'.
   * @generated
   */
  Unsettable1 createUnsettable1();

  /**
   * Returns a new object of class '<em>Unsettable2 With Default</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @return a new object of class '<em>Unsettable2 With Default</em>'.
   * @generated
   */
  Unsettable2WithDefault createUnsettable2WithDefault();

  /**
   * Returns a new object of class '<em>Persistent Containment</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @return a new object of class '<em>Persistent Containment</em>'.
   * @generated
   */
  PersistentContainment createPersistentContainment();

  /**
   * Returns a new object of class '<em>Transient Container</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @return a new object of class '<em>Transient Container</em>'.
   * @generated
   */
  TransientContainer createTransientContainer();

  /**
   * Returns a new object of class '<em>Not Unsettable</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @return a new object of class '<em>Not Unsettable</em>'.
   * @generated
   */
  NotUnsettable createNotUnsettable();

  /**
   * Returns a new object of class '<em>Not Unsettable With Default</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @return a new object of class '<em>Not Unsettable With Default</em>'.
   * @generated
   */
  NotUnsettableWithDefault createNotUnsettableWithDefault();

  /**
   * Returns a new object of class '<em>Map Holder</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @return a new object of class '<em>Map Holder</em>'.
   * @generated
   */
  MapHolder createMapHolder();

  /**
   * Returns a new object of class '<em>Enum List Holder</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @return a new object of class '<em>Enum List Holder</em>'.
   * @generated
   */
  EnumListHolder createEnumListHolder();

  /**
   * Returns the package supported by this factory. <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @return the package supported by this factory.
   * @generated
   */
  Model2Package getModel2Package();

} // Model2Factory
