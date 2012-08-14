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
package org.eclipse.emf.cdo.server.internal.admin.protocol;

import org.eclipse.emf.cdo.common.CDOCommonRepository.State;
import org.eclipse.emf.cdo.spi.common.admin.CDOAdminProtocolConstants;

import org.eclipse.net4j.signal.Request;
import org.eclipse.net4j.util.io.ExtendedDataOutputStream;

/**
 * @author Eike Stepper
 */
public class RepositoryStateChangedRequest extends Request
{
  private String name;

  private State oldState;

  private State newState;

  public RepositoryStateChangedRequest(CDOAdminServerProtocol serverProtocol, String name, State oldState,
      State newState)
  {
    super(serverProtocol, CDOAdminProtocolConstants.SIGNAL_REPOSITORY_STATE_CHANGED);
    this.name = name;
    this.oldState = oldState;
    this.newState = newState;
  }

  @Override
  protected void requesting(ExtendedDataOutputStream out) throws Exception
  {
    out.writeString(name);
    out.writeEnum(oldState);
    out.writeEnum(newState);
  }
}
