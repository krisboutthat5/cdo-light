/*
 * Copyright (c) 2004 - 2012 Eike Stepper (Berlin, Germany) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Caspar De Groot - initial API and implementation
 */
package org.eclipse.emf.cdo.tests.offline;

/**
 * @author Caspar De Groot
 */
public class OfflineLockRawReplicationTest extends OfflineLockReplicationTest
{
  @Override
  protected boolean isRawReplication()
  {
    return true;
  }
}
