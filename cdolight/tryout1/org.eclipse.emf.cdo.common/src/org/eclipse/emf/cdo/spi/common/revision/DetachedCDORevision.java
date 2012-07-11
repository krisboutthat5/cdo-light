/**
 * Copyright (c) 2004 - 2011 Eike Stepper (Berlin, Germany) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Eike Stepper - initial API and implementation
 */
package org.eclipse.emf.cdo.spi.common.revision;

import org.eclipse.emf.ecore.EClass;

/**
 * @author Eike Stepper
 * @since 3.0
 */
public class DetachedCDORevision extends SyntheticCDORevision
{

  /**
   * @since 4.0
   */
  public DetachedCDORevision(EClass eClass, long id)
  {
    super(eClass, id);
  }



}
