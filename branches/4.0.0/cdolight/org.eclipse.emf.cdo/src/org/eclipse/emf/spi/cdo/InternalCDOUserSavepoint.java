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
package org.eclipse.emf.spi.cdo;

import org.eclipse.emf.cdo.transaction.CDOUserSavepoint;

/**
 * @author Eike Stepper
 * @since 3.0
 * @noextend This interface is not intended to be extended by clients.
 * @noimplement This interface is not intended to be implemented by clients.
 */
public interface InternalCDOUserSavepoint extends CDOUserSavepoint
{
  public InternalCDOUserTransaction getTransaction();

  public InternalCDOUserSavepoint getFirstSavePoint();

  public InternalCDOUserSavepoint getPreviousSavepoint();

  public InternalCDOUserSavepoint getNextSavepoint();

  public void setPreviousSavepoint(InternalCDOUserSavepoint previousSavepoint);

  public void setNextSavepoint(InternalCDOUserSavepoint nextSavepoint);
}
