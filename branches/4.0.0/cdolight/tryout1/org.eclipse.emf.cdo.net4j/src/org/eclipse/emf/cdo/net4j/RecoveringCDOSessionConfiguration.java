/**
 * Copyright (c) 2004 - 2011 Eike Stepper (Berlin, Germany) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Caspar De Groot - initial API and implementation
 */
package org.eclipse.emf.cdo.net4j;

/**
 * @author Caspar De Groot
 * @since 4.0
 * @noextend This interface is not intended to be extended by clients.
 * @noimplement This interface is not intended to be implemented by clients.
 */
public interface RecoveringCDOSessionConfiguration extends CDOSessionConfiguration
{
  public boolean isHeartBeatEnabled();

  public void setHeartBeatEnabled(boolean enabled);

  public long getHeartBeatPeriod();

  public void setHeartBeatPeriod(long period);

  public long getHeartBeatTimeout();

  public void setHeartBeatTimeout(long timeout);

  public long getConnectorTimeout();

  public void setConnectorTimeout(long timeout);
}
