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
package org.eclipse.net4j.signal;

/**
 * Represents the receiver side of a {@link Signal signal}.
 *
 * @author Eike Stepper
 */
public abstract class SignalReactor extends Signal
{
  /**
   * @since 2.0
   */
  public SignalReactor(SignalProtocol<?> protocol, short id, String name)
  {
    super(protocol, id, name);
  }

  /**
   * @since 2.0
   */
  public SignalReactor(SignalProtocol<?> protocol, short signalID)
  {
    super(protocol, signalID);
  }

  /**
   * @since 2.0
   */
  public SignalReactor(SignalProtocol<?> protocol, Enum<?> literal)
  {
    super(protocol, literal);
  }

  @Override
  String getInputMeaning()
  {
    return "Indicating"; //$NON-NLS-1$
  }

  @Override
  String getOutputMeaning()
  {
    return "Responding"; //$NON-NLS-1$
  }
}
