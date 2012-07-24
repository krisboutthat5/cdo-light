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
package org.eclipse.emf.cdo.tests;

import org.eclipse.emf.cdo.common.branch.CDOBranch;
import org.eclipse.emf.cdo.common.commit.CDOCommitInfo;
import org.eclipse.emf.cdo.eresource.CDOResource;
import org.eclipse.emf.cdo.session.CDOSession;
import org.eclipse.emf.cdo.tests.config.IRepositoryConfig;
import org.eclipse.emf.cdo.tests.config.impl.ConfigTest.Requires;
import org.eclipse.emf.cdo.tests.model1.Company;
import org.eclipse.emf.cdo.transaction.CDOTransaction;
import org.eclipse.emf.cdo.util.CommitException;
import org.eclipse.emf.cdo.view.CDOView;

import org.eclipse.net4j.util.WrappedException;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;

/**
 * @author Eike Stepper
 */
@Requires(IRepositoryConfig.CAPABILITY_BRANCHING)
public class RevisionDeltaInBranchTest extends RevisionDeltaTest
{
  @Override
  protected void testStoreDelta(ListManipulator manipulator)
  {
    BasicEList<Company> referenceSub = new BasicEList<Company>();
    BasicEList<Company> referenceMain = new BasicEList<Company>();

    long timestampBaseBranch = 0L;
    int subBranchID = 0;

    // main branch
    {
      CDOSession session = openSession();
      CDOBranch mainBranch = session.getBranchManager().getMainBranch();

      CDOTransaction transaction = session.openTransaction();
      CDOResource resource = transaction.createResource(getResourcePath("/test1"));

      addCompaniesToList(resource.getContents(), 0, 10);
      addCompaniesToList(referenceMain, 0, 10);
      addCompaniesToList(referenceSub, 0, 10);

      try
      {
        CDOCommitInfo info = transaction.commit();
        assertEquals(mainBranch, info.getBranch());
        timestampBaseBranch = info.getTimeStamp();
      }
      catch (CommitException ex)
      {
        throw WrappedException.wrap(ex);
      }

      transaction.close();
      session.close();
    }

    clearCache(getRepository().getRevisionManager());

    // main branch - second batch
    {
      CDOSession session = openSession();
      CDOBranch mainBranch = session.getBranchManager().getMainBranch();

      CDOTransaction transaction = session.openTransaction();
      CDOResource resource = transaction.getResource(getResourcePath("/test1"));

      addCompaniesToList(resource.getContents(), 10, 15);
      addCompaniesToList(referenceMain, 10, 15);

      try
      {
        CDOCommitInfo info = transaction.commit();
        assertEquals(mainBranch, info.getBranch());
      }
      catch (CommitException ex)
      {
        throw WrappedException.wrap(ex);
      }

      transaction.close();
      session.close();
    }

    clearCache(getRepository().getRevisionManager());

    // sub branch - second batch
    {
      CDOSession session = openSession();
      CDOBranch mainBranch = session.getBranchManager().getMainBranch();
      CDOBranch subBranch = mainBranch.createBranch("subBranch", timestampBaseBranch);
      subBranchID = subBranch.getID();

      CDOTransaction transaction = session.openTransaction(subBranch);
      CDOResource resource = transaction.getResource(getResourcePath("/test1"));

      addCompaniesToList(resource.getContents(), 10, 20);
      addCompaniesToList(referenceSub, 10, 20);

      try
      {
        CDOCommitInfo info = transaction.commit();
        assertEquals(subBranch, info.getBranch());
      }
      catch (CommitException ex)
      {
        throw WrappedException.wrap(ex);
      }

      transaction.close();
      session.close();
    }

    clearCache(getRepository().getRevisionManager());

    // do manipulations in branch
    {
      CDOSession session = openSession();
      CDOBranch subBranch = session.getBranchManager().getBranch(subBranchID);
      CDOTransaction transaction = session.openTransaction(subBranch);
      CDOResource resource = transaction.getResource(getResourcePath("/test1"));

      manipulator.doManipulations(resource.getContents());
      manipulator.doManipulations(referenceSub);

      try
      {
        transaction.commit();
      }
      catch (CommitException ex)
      {
        throw WrappedException.wrap(ex);
      }

      transaction.close();
      session.close();
    }

    clearCache(getRepository().getRevisionManager());

    {
      CDOSession session = openSession();
      CDOBranch subBranch = session.getBranchManager().getBranch(subBranchID);
      CDOView view = session.openView(subBranch);
      CDOResource resource = view.getResource(getResourcePath("/test1"));

      assertEquals(referenceSub.size(), resource.getContents().size());

      for (int i = 0; i < referenceSub.size(); i++)
      {
        assertEquals(referenceSub.get(i).getName(), ((Company)resource.getContents().get(i)).getName());
      }

      view.close();
      session.close();
    }

    clearCache(getRepository().getRevisionManager());

    {
      CDOSession session = openSession();
      CDOView view = session.openView();
      CDOResource resource = view.getResource(getResourcePath("/test1"));

      assertEquals(referenceMain.size(), resource.getContents().size());

      for (int i = 0; i < referenceMain.size(); i++)
      {
        assertEquals(referenceMain.get(i).getName(), ((Company)resource.getContents().get(i)).getName());
      }

      view.close();
      session.close();
    }
  }

  @SuppressWarnings("unchecked")
  protected void addCompaniesToList(@SuppressWarnings("rawtypes") EList list, int from, int to)
  {
    for (int i = from; i < to; i++)
    {
      String name = "company " + i;
      Company company = getModel1Factory().createCompany();
      company.setName(name);
      list.add(company);
    }
  }
}
