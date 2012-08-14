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
package org.eclipse.net4j.tests.signal;

import org.eclipse.net4j.signal.RequestWithConfirmation;
import org.eclipse.net4j.signal.SignalProtocol;
import org.eclipse.net4j.util.io.ExtendedDataInputStream;
import org.eclipse.net4j.util.io.ExtendedDataOutputStream;

/**
 * @author Eike Stepper
 */
public class ArrayRequest extends RequestWithConfirmation<byte[]>
{
  private byte[] data;

  public ArrayRequest(SignalProtocol<?> protocol, byte[] data)
  {
    super(protocol, TestSignalProtocol.SIGNAL_ARRAY);
    this.data = data;
  }

  @Override
  protected void requesting(ExtendedDataOutputStream out) throws Exception
  {
    out.writeByteArray(data);
  }

  @Override
  protected byte[] confirming(ExtendedDataInputStream in) throws Exception
  {
    return in.readByteArray();
  }
}
