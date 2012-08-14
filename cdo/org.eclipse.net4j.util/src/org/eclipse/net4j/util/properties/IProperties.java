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
package org.eclipse.net4j.util.properties;

/**
 * Contains a list of {@link Property properties}.
 * 
 * @author Eike Stepper
 * @since 3.2
 */
public interface IProperties<RECEIVER> extends IPropertyProvider<RECEIVER>
{
  public Class<RECEIVER> getReceiverType();

  public Property<RECEIVER> getProperty(String name);

  public void add(Property<RECEIVER> property);
}
