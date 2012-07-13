/**
 * Copyright (c) 2004 - 2011 Eike Stepper (Berlin, Germany) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *    Eike Stepper - initial API and implementation
 *    Victor Roldan Betancort - maintenance
 */
package org.eclipse.net4j.util.container;

import org.eclipse.net4j.util.event.INotifier;

import java.util.Collection;

/**
 * @author Eike Stepper
 */
public interface IContainer<E> extends INotifier
{
  public boolean isEmpty();

  public E[] getElements();

  /**
   * @author Eike Stepper
   * @since 2.0
   */
  public interface Modifiable<E> extends IContainer<E>
  {
    public boolean addElement(E element);

    public boolean addAllElements(Collection<E> elements);

    public boolean removeElement(E element);

    public boolean removeAllElements(Collection<E> elements);
  }
}
