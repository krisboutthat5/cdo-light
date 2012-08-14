/*
 * Copyright (c) 2004 - 2012 Eike Stepper (Berlin, Germany) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *    Szabolcs B�rdy - initial API and implementation
 */
package org.eclipse.emf.cdo.tests.bugzilla;

import org.eclipse.emf.cdo.common.commit.CDOCommitInfo;
import org.eclipse.emf.cdo.common.revision.CDOIDAndVersion;
import org.eclipse.emf.cdo.common.revision.CDORevisionKey;
import org.eclipse.emf.cdo.eresource.CDOResource;
import org.eclipse.emf.cdo.session.CDOSession;
import org.eclipse.emf.cdo.tests.AbstractCDOTest;
import org.eclipse.emf.cdo.tests.config.IRepositoryConfig;
import org.eclipse.emf.cdo.tests.model1.Company;
import org.eclipse.emf.cdo.transaction.CDOTransaction;

import org.eclipse.net4j.util.io.IOUtil;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.util.EcoreUtil;

import java.util.List;

/**
 * @author Szabolcs B�rdy
 */
public class Bugzilla_342135_Test extends AbstractCDOTest
{
  @Requires(IRepositoryConfig.CAPABILITY_AUDITING)
  public void testDetachedCommitInfo() throws Exception
  {
    // Create test resource
    CDOSession session = openSession();
    CDOTransaction transaction = session.openTransaction();
    CDOResource resource = transaction.createResource(getResourcePath("/commitInfoResource"));

    // Create test object and commit
    Company c1 = getModel1Factory().createCompany();
    c1.setName("Test");
    resource.getContents().add(c1);

    transaction.commit();

    final URI uriC1 = EcoreUtil.getURI(c1);
    assertEquals(c1, transaction.getResourceSet().getEObject(uriC1, false));

    resource.getContents().remove(0); // remove object by index

    // Commit info
    CDOCommitInfo commitInfo = transaction.commit();
    List<CDORevisionKey> changedObjects = commitInfo.getChangedObjects();
    List<CDOIDAndVersion> detachedObjects = commitInfo.getDetachedObjects();

    IOUtil.OUT().println("Commit info: " + commitInfo);
    IOUtil.OUT().println("Commit info changed objects: " + changedObjects);
    IOUtil.OUT().println("Commit info detached objects: " + detachedObjects);

    assertEquals(false, detachedObjects.isEmpty());
    IOUtil.OUT().println();

    // Same commit info loaded from database
    CDOCommitInfo loadedCommitInfo = session.getCommitInfoManager().getCommitInfo(commitInfo.getTimeStamp());
    List<CDORevisionKey> loadedChangedObjects = loadedCommitInfo.getChangedObjects();
    List<CDOIDAndVersion> loadedDetachedObjects = loadedCommitInfo.getDetachedObjects();

    IOUtil.OUT().println("Loaded commit info: " + loadedCommitInfo);
    IOUtil.OUT().println("Commit info changed objects: " + loadedChangedObjects);
    IOUtil.OUT().println("Loaded commit info detached objects: " + loadedDetachedObjects);

    assertEquals(false, loadedDetachedObjects.isEmpty());
  }
}
