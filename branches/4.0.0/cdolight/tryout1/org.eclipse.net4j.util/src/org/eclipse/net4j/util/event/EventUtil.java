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
package org.eclipse.net4j.util.event;

/**
 * @author Eike Stepper
 */
public final class EventUtil
{
  /**
   * @since 3.0
   */
  public static final IListener[] NO_LISTENERS = {};

  private EventUtil()
  {
  }

  public static boolean addListener(Object notifier, IListener listener)
  {
    if (notifier instanceof INotifier)
    {
      ((INotifier)notifier).addListener(listener);
      return true;
    }

    return false;
  }

  public static boolean removeListener(Object notifier, IListener listener)
  {
    if (notifier instanceof INotifier)
    {
      ((INotifier)notifier).removeListener(listener);
      return true;
    }

    return false;
  }

  public static IListener[] getListeners(Object notifier)
  {
    if (notifier instanceof INotifier)
    {
      return ((INotifier)notifier).getListeners();
    }

    return NO_LISTENERS;
  }
}
