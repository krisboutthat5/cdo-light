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
package org.eclipse.net4j.util.lifecycle;

import org.eclipse.net4j.util.event.INotifier;

/**
 * @author Eike Stepper
 */
public interface ILifecycle extends INotifier
{
  public void activate() throws LifecycleException;

  public Exception deactivate();

  /**
   * @since 3.0
   */
  public LifecycleState getLifecycleState();

  /**
   * @since 3.0
   */
  public boolean isActive();
}
