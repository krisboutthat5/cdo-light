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

import org.eclipse.emf.cdo.session.CDOSession;
import org.eclipse.emf.cdo.session.CDOSessionConfigurationFactory;

import org.eclipse.net4j.util.event.INotifier;

/**
 * @author Eike Stepper
 * @since 3.0
 * @noextend This interface is not intended to be extended by clients.
 * @noimplement This interface is not intended to be implemented by clients.
 */
public interface IRepositorySynchronizer extends INotifier
{
  public int getRetryInterval();

  public void setRetryInterval(int retryInterval);

  public ISynchronizableRepository getLocalRepository();

  public CDOSessionConfigurationFactory getRemoteSessionConfigurationFactory();

  public CDOSession getRemoteSession();

  public int getMaxRecommits();

  public void setMaxRecommits(int maxRecommits);

  public int getRecommitInterval();

  public void setRecommitInterval(int recommitInterval);
}
