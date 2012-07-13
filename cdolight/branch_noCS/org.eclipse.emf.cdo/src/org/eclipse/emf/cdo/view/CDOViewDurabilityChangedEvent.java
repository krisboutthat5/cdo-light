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
package org.eclipse.emf.cdo.view;

/**
 * A {@link CDOViewEvent view event} fired when a {@link CDOView view} has been made
 * {@link CDOView#enableDurableLocking(boolean) durable} or volatile.
 * 
 * @author Eike Stepper
 * @since 4.0
 * @noextend This interface is not intended to be extended by clients.
 * @noimplement This interface is not intended to be implemented by clients.
 */
public interface CDOViewDurabilityChangedEvent extends CDOViewEvent
{
  public String getOldDurableLockingID();

  public String getNewDurableLockingID();
}
