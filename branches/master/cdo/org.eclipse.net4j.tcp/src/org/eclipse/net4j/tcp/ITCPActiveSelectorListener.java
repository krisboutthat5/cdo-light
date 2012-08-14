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
package org.eclipse.net4j.tcp;

import org.eclipse.net4j.connector.IConnector;

import java.nio.channels.SocketChannel;

/**
 * Call-back that handles the possible calls from a {@link ITCPSelector selector} to an active consumer, usually a
 * {@link IConnector connector}.
 * 
 * @author Eike Stepper
 * @noimplement This interface is not intended to be implemented by clients.
 * @noextend This interface is not intended to be extended by clients.
 */
public interface ITCPActiveSelectorListener
{
  public void handleRegistration(ITCPSelector selector, SocketChannel socketChannel);

  public void handleConnect(ITCPSelector selector, SocketChannel socketChannel);

  public void handleRead(ITCPSelector selector, SocketChannel socketChannel);

  public void handleWrite(ITCPSelector selector, SocketChannel socketChannel);
}
