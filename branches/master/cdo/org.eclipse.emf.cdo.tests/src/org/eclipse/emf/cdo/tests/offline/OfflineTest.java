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
package org.eclipse.emf.cdo.tests.offline;

import org.eclipse.emf.cdo.CDOObject;
import org.eclipse.emf.cdo.common.CDOCommonRepository.IDGenerationLocation;
import org.eclipse.emf.cdo.common.commit.CDOCommitInfo;
import org.eclipse.emf.cdo.common.commit.CDOCommitInfoHandler;
import org.eclipse.emf.cdo.common.id.CDOID;
import org.eclipse.emf.cdo.common.lob.CDOBlob;
import org.eclipse.emf.cdo.common.lob.CDOClob;
import org.eclipse.emf.cdo.eresource.CDOResource;
import org.eclipse.emf.cdo.internal.server.syncing.OfflineClone;
import org.eclipse.emf.cdo.session.CDOSession;
import org.eclipse.emf.cdo.spi.server.InternalRepository;
import org.eclipse.emf.cdo.tests.AbstractSyncingTest;
import org.eclipse.emf.cdo.tests.bundle.OM;
import org.eclipse.emf.cdo.tests.config.impl.ConfigTest.CleanRepositoriesBefore;
import org.eclipse.emf.cdo.tests.model1.Company;
import org.eclipse.emf.cdo.tests.model3.File;
import org.eclipse.emf.cdo.tests.model3.Image;
import org.eclipse.emf.cdo.tests.util.TestListener;
import org.eclipse.emf.cdo.transaction.CDOTransaction;

import org.eclipse.net4j.util.event.IEvent;
import org.eclipse.net4j.util.io.IOUtil;

import org.eclipse.emf.spi.cdo.DefaultCDOMerger;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Eike Stepper
 */
@CleanRepositoriesBefore
public class OfflineTest extends AbstractSyncingTest
{
  public void testMasterCommits_ArrivalInClone() throws Exception
  {
    CDOSession session = openSession("master");
    CDOTransaction transaction = session.openTransaction();
    CDOResource resource = transaction.createResource("/my/resource");

    Company company = getModel1Factory().createCompany();
    company.setName("Test");

    // 2 * Root resource + folder + resource + company
    int expectedRevisions = 2 + 1 + 1 + 1;

    resource.getContents().add(company);
    long timeStamp = transaction.commit().getTimeStamp();
    checkRevisions(getRepository(), timeStamp, expectedRevisions);

    for (int i = 0; i < 10; i++)
    {
      company.setName("Test" + i);
      timeStamp = transaction.commit().getTimeStamp();
      expectedRevisions += 1; // Changed company
      checkRevisions(getRepository(), timeStamp, expectedRevisions);
    }

    for (int i = 0; i < 10; i++)
    {
      company.getCategories().add(getModel1Factory().createCategory());
      timeStamp = transaction.commit().getTimeStamp();
      expectedRevisions += 2; // Changed company + new category
      checkRevisions(getRepository(), timeStamp, expectedRevisions);
    }

    for (int i = 0; i < 10; i++)
    {
      company.getCategories().remove(0);
      timeStamp = transaction.commit().getTimeStamp();
      expectedRevisions += 2; // Changed company + detached category
      checkRevisions(getRepository(), timeStamp, expectedRevisions);
    }

    session.close();
  }

  protected void masterCommits_NotificationsFromClone() throws Exception
  {
    CDOSession masterSession = openSession("master");
    CDOTransaction transaction = masterSession.openTransaction();
    CDOResource resource = transaction.createResource("/my/resource");

    TestListener listener = new TestListener();
    CDOSession cloneSession = openSession();
    cloneSession.addListener(listener);

    Company company = getModel1Factory().createCompany();
    company.setName("Test");

    resource.getContents().add(company);
    long timeStamp = transaction.commit().getTimeStamp();
    assertEquals(true, cloneSession.waitForUpdate(timeStamp, DEFAULT_TIMEOUT));
    checkEvent(listener, 1, 3, 1, 0);

    for (int i = 0; i < 10; i++)
    {
      company.setName("Test" + i);
      timeStamp = transaction.commit().getTimeStamp();
      assertEquals(true, cloneSession.waitForUpdate(timeStamp, DEFAULT_TIMEOUT));
      checkEvent(listener, 0, 0, 1, 0);
    }

    for (int i = 0; i < 10; i++)
    {
      company.getCategories().add(getModel1Factory().createCategory());
      timeStamp = transaction.commit().getTimeStamp();
      assertEquals(true, cloneSession.waitForUpdate(timeStamp, DEFAULT_TIMEOUT));
      checkEvent(listener, 0, 1, 1, 0);
    }

    for (int i = 0; i < 10; i++)
    {
      company.getCategories().remove(0);
      timeStamp = transaction.commit().getTimeStamp();
      assertEquals(true, cloneSession.waitForUpdate(timeStamp, DEFAULT_TIMEOUT));
      checkEvent(listener, 0, 0, 1, 1);
    }

    cloneSession.close();
    masterSession.close();
  }

  public void testClientCommits() throws Exception
  {
    InternalRepository clone = getRepository();
    InternalRepository master = getRepository("master");

    TestListener listener = new TestListener();
    CDOSession masterSession = openSession(master.getName());
    masterSession.addListener(listener);

    Company company = getModel1Factory().createCompany();
    company.setName("Test");

    CDOSession cloneSession = openSession();
    waitForOnline(cloneSession.getRepositoryInfo());

    CDOTransaction transaction = cloneSession.openTransaction();
    CDOResource resource = transaction.createResource("/my/resource");

    resource.getContents().add(company);
    transaction.commit();

    IEvent[] events = listener.getEvents();
    assertEquals(1, events.length);

    checkRevision(company, master, "master");
    checkRevision(company, clone, "clone");
  }

  public void testDisconnectAndSyncAddition() throws Exception
  {
    TestListener listener = new TestListener();
    InternalRepository clone = getRepository();
    waitForOnline(clone);

    {
      getOfflineConfig().stopMasterTransport();
      waitForOffline(clone);

      CDOSession masterSession = openSession("master");
      CDOTransaction masterTransaction = masterSession.openTransaction();
      CDOResource masterResource = masterTransaction.createResource("/master/resource");

      masterResource.getContents().add(getModel1Factory().createCompany());
      masterTransaction.commit();

      masterResource.getContents().add(getModel1Factory().createCompany());
      masterTransaction.commit();

      masterTransaction.close();
      masterSession.addListener(listener);

      getOfflineConfig().startMasterTransport();
      waitForOnline(clone);
    }

    Company company = getModel1Factory().createCompany();
    company.setName("Test");

    CDOSession session = openSession();
    CDOTransaction transaction = session.openTransaction();
    CDOResource resource = transaction.createResource("/my/resource");

    resource.getContents().add(company);
    transaction.commit();

    IEvent[] events = listener.getEvents();
    assertEquals(1, events.length);
  }

  public void testDisconnectAndSyncChange() throws Exception
  {
    InternalRepository clone = getRepository();
    waitForOnline(clone);

    {
      getOfflineConfig().stopMasterTransport();
      waitForOffline(clone);

      CDOSession masterSession = openSession("master");
      CDOTransaction masterTransaction = masterSession.openTransaction();
      CDOResource masterResource = masterTransaction.createResource("/master/resource");

      Company comp = getModel1Factory().createCompany();
      masterResource.getContents().add(comp);
      masterTransaction.commit();

      comp.setName("MODIFICATION");
      masterTransaction.commit();
      masterTransaction.close();

      getOfflineConfig().startMasterTransport();
      waitForOnline(clone);
    }

    CDOSession session = openSession();
    CDOTransaction transaction = session.openTransaction();
    CDOResource resource = transaction.getResource("/master/resource");

    Company company = (Company)resource.getContents().get(0);
    assertEquals("MODIFICATION", company.getName());
  }

  public void testDisconnectAndSyncRemoval() throws Exception
  {
    InternalRepository clone = getRepository();
    waitForOnline(clone);

    {
      getOfflineConfig().stopMasterTransport();
      waitForOffline(clone);

      CDOSession masterSession = openSession("master");
      CDOTransaction masterTransaction = masterSession.openTransaction();
      CDOResource masterResource = masterTransaction.createResource("/master/resource");

      Company comp = getModel1Factory().createCompany();
      masterResource.getContents().add(comp);
      masterTransaction.commit();

      masterResource.getContents().remove(comp);
      masterTransaction.commit();
      masterTransaction.close();

      getOfflineConfig().startMasterTransport();
      waitForOnline(clone);
    }

    CDOSession session = openSession();
    CDOTransaction transaction = session.openTransaction();
    CDOResource resource = transaction.getResource("/master/resource");

    assertEquals(0, resource.getContents().size());
  }

  public void testDisconnectAndCommit() throws Exception
  {
    OfflineClone clone = (OfflineClone)getRepository();
    waitForOnline(clone);

    getOfflineConfig().stopMasterTransport();
    waitForOffline(clone);

    Company company = getModel1Factory().createCompany();
    company.setName("Test");

    CDOSession session = openSession();
    CDOTransaction transaction = session.openTransaction();
    CDOResource resource = transaction.createResource("/my/resource");

    resource.getContents().add(company);
    CDOCommitInfo commitInfo = transaction.commit();
    assertEquals(true, commitInfo.getBranch().isLocal());
    assertEquals(true, transaction.getBranch().isLocal());
  }

  public void testDisconnectAndCommitAndMerge() throws Exception
  {
    OfflineClone clone = (OfflineClone)getRepository();
    waitForOnline(clone);

    CDOSession session = openSession();
    CDOTransaction transaction = session.openTransaction();
    CDOResource resource = transaction.createResource("/my/resource");
    resource.getContents().add(getModel1Factory().createCompany());
    transaction.commit();

    getOfflineConfig().stopMasterTransport();
    waitForOffline(clone);

    resource.getContents().add(getModel1Factory().createCompany());
    CDOCommitInfo commitInfo = transaction.commit();

    getOfflineConfig().startMasterTransport();
    waitForOnline(clone);

    DefaultCDOMerger.PerFeature.ManyValued merger = new DefaultCDOMerger.PerFeature.ManyValued();

    transaction.setBranch(session.getBranchManager().getMainBranch());
    transaction.merge(commitInfo, merger);

    assertEquals(1, transaction.getNewObjects().size());
    CDOObject offlineCompany = transaction.getNewObjects().values().iterator().next();
    if (getRepositoryConfig().getIDGenerationLocation() != IDGenerationLocation.CLIENT)
    {
      assertEquals(CDOID.Type.TEMP_OBJECT, offlineCompany.cdoID().getType());
    }

    commitInfo = transaction.commit();
    assertEquals(CDOID.Type.OBJECT, offlineCompany.cdoID().getType());
  }

  /**
   * @since 4.0
   */
  public void _testDisconnectAndCommitAndMergeWithNewPackages() throws Exception
  {
    OfflineClone clone = (OfflineClone)getRepository();
    waitForOnline(clone);

    getOfflineConfig().stopMasterTransport();
    waitForOffline(clone);

    Company company = getModel1Factory().createCompany();
    company.setName("Test");

    CDOSession session = openSession();
    CDOTransaction transaction = session.openTransaction();
    CDOResource resource = transaction.createResource("/my/resource");

    resource.getContents().add(company);
    CDOCommitInfo commitInfo = transaction.commit();

    getOfflineConfig().startMasterTransport();
    waitForOnline(clone);

    transaction.setBranch(session.getBranchManager().getMainBranch());
    transaction.merge(commitInfo, new DefaultCDOMerger.PerFeature.ManyValued());

    transaction.commit();
  }

  public void testManyCommitInfos_Initial() throws Exception
  {
    OfflineClone clone = (OfflineClone)getRepository();
    waitForOnline(clone);

    getOfflineConfig().stopMasterTransport();
    waitForOffline(clone);

    CDOSession masterSession = openSession("master");
    CDOTransaction transaction = masterSession.openTransaction();
    CDOResource resource = transaction.createResource("/my/resource");

    for (int i = 0; i < 10; i++)
    {
      Company company = getModel1Factory().createCompany();
      company.setName("Company" + i);
      resource.getContents().add(company);
    }

    transaction.setCommitComment("Creation");
    long timeStamp = transaction.commit().getTimeStamp();
    msg(timeStamp);

    for (int k = 0; k < 10; k++)
    {
      sleep(SLEEP_MILLIS);
      for (int i = 0; i < 10; i++)
      {
        Company company = (Company)resource.getContents().get(i);
        company.setName("Company" + i + "_" + transaction.getBranch().getID() + "_" + k);
      }

      transaction.setCommitComment("Modification");
      timeStamp = transaction.commit().getTimeStamp();
      msg(timeStamp);
    }

    masterSession.close();
    getOfflineConfig().startMasterTransport();
    waitForOnline(clone);

    final List<CDOCommitInfo> result = new ArrayList<CDOCommitInfo>();
    CDOSession session = openSession();
    session.getCommitInfoManager().getCommitInfos(null, 0L, 0L, new CDOCommitInfoHandler()
    {
      public void handleCommitInfo(CDOCommitInfo commitInfo)
      {
        result.add(commitInfo);
        commitInfo.getNewPackageUnits();
      }
    });

    for (CDOCommitInfo commitInfo : result)
    {
      System.out.println("-----> " + commitInfo);
    }

    assertEquals(12, result.size());
  }

  /**
   * See bug 364548.
   */
  public void testEmptyCommit() throws Exception
  {
    InternalRepository master = getRepository("master");

    TestListener listener = new TestListener();
    CDOSession masterSession = openSession(master.getName());
    masterSession.addListener(listener);

    CDOSession cloneSession = openSession();
    waitForOnline(cloneSession.getRepositoryInfo());

    CDOTransaction transaction = cloneSession.openTransaction();
    transaction.commit();
  }

  public void _testDisconnectAndSyncBLOB() throws Exception
  {
    TestListener listener = new TestListener();
    InternalRepository clone = getRepository();
    waitForOnline(clone);

    {
      getOfflineConfig().stopMasterTransport();
      waitForOffline(clone);

      CDOSession masterSession = openSession("master");
      CDOTransaction masterTransaction = masterSession.openTransaction();
      CDOResource masterResource = masterTransaction.createResource("/master/resource");

      InputStream inputStream = null;

      try
      {
        inputStream = OM.BUNDLE.getInputStream("copyright.txt");
        CDOBlob blob = new CDOBlob(inputStream);

        Image image = getModel3Factory().createImage();
        image.setWidth(320);
        image.setHeight(200);
        image.setData(blob);

        masterResource.getContents().add(image);
      }
      finally
      {
        IOUtil.close(inputStream);
      }

      masterTransaction.commit();
      masterTransaction.close();
      masterSession.addListener(listener);

      getOfflineConfig().startMasterTransport();
      waitForOnline(clone);
    }

    CDOSession session = openSession();
    CDOTransaction transaction = session.openTransaction();
    CDOResource resource = transaction.getResource("/master/resource");

    Image image = (Image)resource.getContents().get(0);

    InputStream fromDisk = null;

    try
    {
      fromDisk = OM.BUNDLE.getInputStream("copyright.txt");
      IOUtil.equals(fromDisk, image.getData().getContents());
    }
    finally
    {
      IOUtil.close(fromDisk);
    }
  }

  public void _testDisconnectAndSyncCLOB() throws Exception
  {
    TestListener listener = new TestListener();
    InternalRepository clone = getRepository();
    waitForOnline(clone);

    {
      getOfflineConfig().stopMasterTransport();
      waitForOffline(clone);

      CDOSession masterSession = openSession("master");
      CDOTransaction masterTransaction = masterSession.openTransaction();
      CDOResource masterResource = masterTransaction.createResource("/master/resource");

      InputStream inputStream = null;

      try
      {
        inputStream = OM.BUNDLE.getInputStream("copyright.txt");
        CDOClob clob = new CDOClob(new InputStreamReader(inputStream));

        File file = getModel3Factory().createFile();
        file.setName("copyright.txt");
        file.setData(clob);

        masterResource.getContents().add(file);
      }
      finally
      {
        IOUtil.close(inputStream);
      }

      try
      {
        inputStream = OM.BUNDLE.getInputStream("copyright.txt");
        CDOClob clob = new CDOClob(new InputStreamReader(inputStream));

        File file = getModel3Factory().createFile();
        file.setName("plugin.properties");
        file.setData(clob);

        masterResource.getContents().add(file);
      }
      finally
      {
        IOUtil.close(inputStream);
      }

      masterTransaction.commit();
      masterTransaction.close();
      masterSession.addListener(listener);

      getOfflineConfig().startMasterTransport();
      waitForOnline(clone);
    }

    CDOSession session = openSession();
    CDOTransaction transaction = session.openTransaction();
    CDOResource resource = transaction.getResource("/master/resource");

    File file = (File)resource.getContents().get(0);

    InputStream fromDisk = null;

    try
    {
      fromDisk = OM.BUNDLE.getInputStream("copyright.txt");
      IOUtil.equals(new InputStreamReader(fromDisk), file.getData().getContents());
    }
    finally
    {
      IOUtil.close(fromDisk);
    }
  }
}
