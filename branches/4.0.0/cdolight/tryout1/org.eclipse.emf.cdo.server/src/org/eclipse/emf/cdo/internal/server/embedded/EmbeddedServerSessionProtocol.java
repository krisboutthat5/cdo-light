/**
 * Copyright (c) 2004 - 2011 Eike Stepper (Berlin, Germany) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Eike Stepper - initial API and implementation
 */
package org.eclipse.emf.cdo.internal.server.embedded;

import org.eclipse.emf.cdo.common.CDOCommonRepository;
import org.eclipse.emf.cdo.common.commit.CDOCommitInfo;
import org.eclipse.emf.cdo.session.remote.CDORemoteSessionMessage;
import org.eclipse.emf.cdo.spi.common.CDOAuthenticationResult;
import org.eclipse.emf.cdo.spi.common.branch.InternalCDOBranch;
import org.eclipse.emf.cdo.spi.server.ISessionProtocol;
import org.eclipse.emf.cdo.spi.server.InternalRepository;
import org.eclipse.emf.cdo.spi.server.InternalSession;

import org.eclipse.net4j.util.lifecycle.Lifecycle;

/**
 * @author Eike Stepper
 */
public class EmbeddedServerSessionProtocol extends Lifecycle implements ISessionProtocol
{
  // A separate session protocol instance is required because the getSession() methods are ambiguous!
  private EmbeddedClientSessionProtocol clientSessionProtocol;

  private InternalSession session;

  public EmbeddedServerSessionProtocol(EmbeddedClientSessionProtocol clientSessionProtocol)
  {
    this.clientSessionProtocol = clientSessionProtocol;
  }

  public EmbeddedClientSessionProtocol getClientSessionProtocol()
  {
    return clientSessionProtocol;
  }

  public InternalSession openSession(InternalRepository repository, boolean passiveUpdateEnabled)
  {
    session = repository.getSessionManager().openSession(this);
    session.setPassiveUpdateEnabled(passiveUpdateEnabled);
    return session;
  }

  public InternalSession getSession()
  {
    return session;
  }

  public CDOAuthenticationResult sendAuthenticationChallenge(byte[] randomToken) throws Exception
  {
    return clientSessionProtocol.handleAuthenticationChallenge(randomToken);
  }

  public void sendRepositoryTypeNotification(CDOCommonRepository.Type oldType, CDOCommonRepository.Type newType)
  {
    EmbeddedClientSession clientSession = clientSessionProtocol.getSession();
    clientSession.handleRepositoryTypeChanged(oldType, newType);
  }

  public void sendRepositoryStateNotification(CDOCommonRepository.State oldState, CDOCommonRepository.State newState)
  {
    EmbeddedClientSession clientSession = clientSessionProtocol.getSession();
    clientSession.handleRepositoryStateChanged(oldState, newState);
  }

  public void sendCommitNotification(CDOCommitInfo commitInfo)
  {
    EmbeddedClientSession clientSession = clientSessionProtocol.getSession();
    clientSession.handleCommitNotification(commitInfo);
  }

  public void sendRemoteSessionNotification(InternalSession sender, byte opcode)
  {
    throw new UnsupportedOperationException();
  }

  public void sendRemoteMessageNotification(InternalSession sender, CDORemoteSessionMessage message)
  {
    throw new UnsupportedOperationException();
  }
}
