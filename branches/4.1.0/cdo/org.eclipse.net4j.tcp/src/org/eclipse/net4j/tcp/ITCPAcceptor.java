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

import org.eclipse.net4j.acceptor.IAcceptor;
import org.eclipse.net4j.internal.tcp.bundle.OM;
import org.eclipse.net4j.tcp.ssl.SSLUtil;

/**
 * An {@link IAcceptor acceptor} that implements non-blocking multiplexed TCP transport, optionally with {@link SSLUtil
 * SSL}.
 * 
 * @author Eike Stepper
 * @noextend This interface is not intended to be extended by clients.
 * @noimplement This interface is not intended to be implemented by clients.
 */
public interface ITCPAcceptor extends IAcceptor
{
  public static final String DEFAULT_ADDRESS = "0.0.0.0"; //$NON-NLS-1$

  /**
   * The value of the <i>org.eclipse.net4j.tcp.port</i> bundle/system property if defined, the value <i>2036</i>
   * otherwise.
   */
  public static final int DEFAULT_PORT = OM.getDefaultPort();

  /**
   * @since 4.0
   */
  public ITCPSelector getSelector();

  public String getAddress();

  public int getPort();
}
