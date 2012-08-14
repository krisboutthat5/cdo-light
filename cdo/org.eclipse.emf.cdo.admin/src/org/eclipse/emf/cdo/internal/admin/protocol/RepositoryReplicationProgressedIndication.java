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
package org.eclipse.emf.cdo.internal.admin.protocol;

import org.eclipse.emf.cdo.internal.admin.CDOAdminClientImpl;
import org.eclipse.emf.cdo.spi.common.admin.CDOAdminProtocolConstants;

import org.eclipse.net4j.signal.Indication;
import org.eclipse.net4j.util.io.ExtendedDataInputStream;

/**
 * @author Eike Stepper
 */
public class RepositoryReplicationProgressedIndication extends Indication
{
  public RepositoryReplicationProgressedIndication(CDOAdminClientProtocol protocol)
  {
    super(protocol, CDOAdminProtocolConstants.SIGNAL_REPOSITORY_REPLICATION_PROGRESSED);
  }

  @Override
  protected void indicating(ExtendedDataInputStream in) throws Exception
  {
    CDOAdminClientProtocol protocol = (CDOAdminClientProtocol)getProtocol();
    CDOAdminClientImpl admin = protocol.getInfraStructure();

    String name = in.readString();
    double totalWork = in.readDouble();
    double work = in.readDouble();
    admin.repositoryReplicationProgressed(name, totalWork, work);
  }
}
