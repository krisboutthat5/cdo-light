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
package org.eclipse.emf.cdo.server;

import org.w3c.dom.Element;

import java.util.Map;

/**
 * @author Eike Stepper
 */
public interface IStoreFactory
{
  public String getStoreType();

  /**
   * @since 4.0
   */
  public IStore createStore(String repositoryName, Map<String, String> repositoryProperties, Element storeConfig);
}
