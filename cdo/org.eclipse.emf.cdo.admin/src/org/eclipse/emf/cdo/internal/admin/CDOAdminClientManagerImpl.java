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
package org.eclipse.emf.cdo.internal.admin;

import org.eclipse.emf.cdo.admin.CDOAdminClient;
import org.eclipse.emf.cdo.admin.CDOAdminClientManager;

import org.eclipse.net4j.signal.ISignalProtocol;
import org.eclipse.net4j.util.ObjectUtil;
import org.eclipse.net4j.util.concurrent.ExecutorServiceFactory;
import org.eclipse.net4j.util.container.IManagedContainer;
import org.eclipse.net4j.util.container.SetContainer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutorService;

/**
 * @author Eike Stepper
 */
public class CDOAdminClientManagerImpl extends SetContainer<CDOAdminClient> implements CDOAdminClientManager
{
  private final IManagedContainer container;

  private final ExecutorService executorService;

  public CDOAdminClientManagerImpl(IManagedContainer container)
  {
    super(CDOAdminClient.class);
    this.container = container;
    executorService = ExecutorServiceFactory.get(container);
  }

  public final IManagedContainer getContainer()
  {
    return container;
  }

  public final ExecutorService getExecutorService()
  {
    return executorService;
  }

  public CDOAdminClient[] getConnections()
  {
    return getElements();
  }

  public List<String> getConnectionURLs()
  {
    List<String> urls = new ArrayList<String>();
    for (CDOAdminClient connection : getConnections())
    {
      urls.add(connection.getURL());
    }

    return urls;
  }

  public CDOAdminClient getConnection(String url)
  {
    for (CDOAdminClient connection : getConnections())
    {
      if (ObjectUtil.equals(connection.getURL(), url))
      {
        return connection;
      }
    }

    return null;
  }

  public int addConnections(Collection<String> urls)
  {
    int count = 0;
    for (String url : urls)
    {
      if (addConnection(url))
      {
        ++count;
      }
    }

    return count;
  }

  public boolean addConnection(String url)
  {
    CDOAdminClient connection = new CDOAdminClientImpl(url, ISignalProtocol.DEFAULT_TIMEOUT, container);
    return addElement(connection);
  }

  public boolean removeConnection(CDOAdminClient connection)
  {
    return removeElement(connection);
  }
}
