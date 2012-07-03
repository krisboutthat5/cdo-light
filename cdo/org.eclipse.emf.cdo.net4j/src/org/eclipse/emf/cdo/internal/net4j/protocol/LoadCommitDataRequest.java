/***************************************************************************
 * Copyright (c) 2004 - 2011 Eike Stepper (Berlin, Germany) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Eike Stepper - initial API and implementation
 **************************************************************************/
package org.eclipse.emf.cdo.internal.net4j.protocol;

import org.eclipse.emf.cdo.common.commit.CDOCommitData;
import org.eclipse.emf.cdo.common.protocol.CDODataInput;
import org.eclipse.emf.cdo.common.protocol.CDODataOutput;
import org.eclipse.emf.cdo.common.protocol.CDOProtocolConstants;

import java.io.IOException;

/**
 * @author Eike Stepper
 */
public class LoadCommitDataRequest extends CDOClientRequest<CDOCommitData>
{
  private long timeStamp;

  public LoadCommitDataRequest(CDOClientProtocol protocol, long timeStamp)
  {
    super(protocol, CDOProtocolConstants.SIGNAL_LOAD_COMMIT_DATA);
    this.timeStamp = timeStamp;
  }

  @Override
  protected void requesting(CDODataOutput out) throws IOException
  {
    out.writeLong(timeStamp);
  }

  @Override
  protected CDOCommitData confirming(CDODataInput in) throws IOException
  {
    return in.readCDOCommitData();
  }
}
