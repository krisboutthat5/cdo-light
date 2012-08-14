/*
 * Copyright (c) 2004 - 2012 Eike Stepper (Berlin, Germany) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Eike Stepper - initial API and implementation
 *    Caspar De Groot - maintenance
 */
package org.eclipse.net4j.internal.tcp;

import org.eclipse.net4j.tcp.ITCPAcceptor;
import org.eclipse.net4j.tcp.TCPUtil;
import org.eclipse.net4j.tcp.TCPUtil.AcceptorData;

import org.eclipse.spi.net4j.AcceptorFactory;

/**
 * @author Eike Stepper
 */
public class TCPAcceptorFactory extends AcceptorFactory
{
  public TCPAcceptorFactory()
  {
    super(TCPUtil.FACTORY_TYPE);
  }

  /**
   * Allows derived classes to override the TYPE identifier
   */
  protected TCPAcceptorFactory(String type)
  {
    super(type);
  }

  public TCPAcceptor create(String description)
  {
    AcceptorData data = new AcceptorData(description);

    TCPAcceptor acceptor = createAcceptor();
    acceptor.setAddress(data.getAddress());
    acceptor.setPort(data.getPort());
    return acceptor;
  }

  protected TCPAcceptor createAcceptor()
  {
    return new TCPAcceptor();
  }

  @Override
  public String getDescriptionFor(Object object)
  {
    if (object instanceof ITCPAcceptor)
    {
      ITCPAcceptor acceptor = (ITCPAcceptor)object;
      return new AcceptorData(acceptor).toString();
    }

    return null;
  }
}
