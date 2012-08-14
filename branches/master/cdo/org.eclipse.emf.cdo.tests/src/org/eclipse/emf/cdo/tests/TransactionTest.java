/*
 * Copyright (c) 2004 - 2012 Eike Stepper (Berlin, Germany) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Simon McDuff - initial API and implementation
 *    Eike Stepper - maintenance
 */
package org.eclipse.emf.cdo.tests;

import org.eclipse.emf.cdo.CDOObject;
import org.eclipse.emf.cdo.common.commit.CDOCommitInfo;
import org.eclipse.emf.cdo.common.commit.CDOCommitInfoHandler;
import org.eclipse.emf.cdo.eresource.CDOResource;
import org.eclipse.emf.cdo.internal.net4j.protocol.CommitTransactionRequest;
import org.eclipse.emf.cdo.net4j.CDONet4jSession;
import org.eclipse.emf.cdo.server.IRepository;
import org.eclipse.emf.cdo.session.CDOSession;
import org.eclipse.emf.cdo.spi.common.commit.CDOCommitInfoUtil;
import org.eclipse.emf.cdo.tests.model1.Category;
import org.eclipse.emf.cdo.tests.model1.Company;
import org.eclipse.emf.cdo.tests.util.TestAdapter;
import org.eclipse.emf.cdo.transaction.CDOCommitContext;
import org.eclipse.emf.cdo.transaction.CDOPushTransaction;
import org.eclipse.emf.cdo.transaction.CDOTransaction;
import org.eclipse.emf.cdo.transaction.CDOTransactionConflictEvent;
import org.eclipse.emf.cdo.transaction.CDOTransactionHandler2;
import org.eclipse.emf.cdo.util.CDOUtil;
import org.eclipse.emf.cdo.util.CommitException;
import org.eclipse.emf.cdo.view.CDOView;

import org.eclipse.net4j.util.ReflectUtil;
import org.eclipse.net4j.util.event.IEvent;
import org.eclipse.net4j.util.event.IListener;
import org.eclipse.net4j.util.io.IOUtil;
import org.eclipse.net4j.util.lifecycle.LifecycleUtil;
import org.eclipse.net4j.util.om.OMPlatform;

import org.eclipse.emf.common.notify.Notification;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * See bug 213782, bug 201366
 * 
 * @author Simon McDuff
 */
public class TransactionTest extends AbstractCDOTest
{
  @Override
  protected void doSetUp() throws Exception
  {
    super.doSetUp();
    OMPlatform.INSTANCE.setDebugging(false);
  }

  public void testCommitAfterClose() throws Exception
  {
    CDOSession session = openSession();
    assertEquals(true, LifecycleUtil.isActive(session));
    assertEquals(false, session.isClosed());

    CDOTransaction transaction = session.openTransaction();
    CDOResource resource = transaction.getOrCreateResource(getResourcePath("/test1"));
    resource.getContents().add(getModel1Factory().createCompany());
    assertEquals(true, LifecycleUtil.isActive(transaction));
    assertEquals(false, transaction.isClosed());

    session.close();
    assertEquals(false, LifecycleUtil.isActive(session));
    assertEquals(true, session.isClosed());
    assertEquals(false, LifecycleUtil.isActive(transaction));
    assertEquals(true, transaction.isClosed());

    try
    {
      transaction.commit();
      fail("CommitException expected");
    }
    catch (CommitException expected)
    {
      // SUCCESS
    }
  }

  /**
   * See bug 335432
   */
  public void testLastUpdateTime() throws Exception
  {
    CDOSession session = openSession();
    CDOTransaction transaction = session.openTransaction();
    CDOResource resource = transaction.getOrCreateResource(getResourcePath("/test1"));
    resource.getContents().add(getModel1Factory().createCompany());

    long timeStamp = transaction.commit().getTimeStamp();
    assertEquals(timeStamp, session.getLastUpdateTime());
    assertEquals(timeStamp, transaction.getLastUpdateTime());
  }

  public void testCreateManySessions() throws Exception
  {
    {
      IOUtil.OUT().println("Opening session");
      CDOSession session = openSession();
      CDOTransaction transaction = session.openTransaction();
      transaction.createResource(getResourcePath("/test2"));
      transaction.commit();
      transaction.close();
      session.close();
    }

    for (int i = 0; i < 100; i++)
    {
      IOUtil.OUT().println("Session " + i);
      CDOSession session = openSession();
      CDOTransaction transaction = session.openTransaction();
      CDOResource resource = transaction.getResource(getResourcePath("/test2"));
      Category category = getModel1Factory().createCategory();
      resource.getContents().add(category);
      transaction.commit();
      transaction.close();
      session.close();
    }
  }

  public void testCreateManyTransactions() throws Exception
  {
    IOUtil.OUT().println("Opening session");
    CDOSession session = openSession();
    CDOTransaction transaction = session.openTransaction();
    CDOResource resource = transaction.createResource(getResourcePath("/test2"));
    transaction.commit();
    transaction.close();

    long lastDuration = 0;
    for (int i = 0; i < 100; i++)
    {
      IOUtil.OUT().println("Transaction " + i + "    (" + lastDuration + ")");
      lastDuration = System.currentTimeMillis();
      transaction = session.openTransaction();
      resource = transaction.getResource(getResourcePath("/test2"));

      Category category = getModel1Factory().createCategory();
      resource.getContents().add(category);
      transaction.commit();
      transaction.close();
      lastDuration = System.currentTimeMillis() - lastDuration;
    }

    session.close();
  }

  public void testCreateManySessionsAndTransactionsMultiThread() throws Exception
  {
    final long TIMEOUT = 2 * 120L;
    final int THREADS = 5;

    final List<Exception> exceptions = new ArrayList<Exception>();
    final CountDownLatch latch = new CountDownLatch(THREADS);
    List<Thread> threadList = new ArrayList<Thread>();
    for (int i = 0; i < THREADS; i++)
    {
      final int id = i;
      threadList.add(new Thread("TEST-THREAD-" + id)
      {
        @Override
        public void run()
        {
          try
          {
            IOUtil.OUT().println("Thread " + id + ": Started");
            for (int i = 0; i < 100; i++)
            {
              CDOSession session = openSession();
              CDOTransaction transaction = session.openTransaction();

              IOUtil.OUT().println("Thread " + id + ": Session + Transaction " + i);
              transaction.close();
              session.close();
            }

            IOUtil.OUT().println("Thread " + id + ": Done");
          }
          catch (Exception ex)
          {
            System.out.println("Thread " + id + ": " + ex.getClass().getName() + ": " + ex.getMessage());
            synchronized (exceptions)
            {
              exceptions.add(ex);
            }
          }
          finally
          {
            latch.countDown();
          }
        }
      });
    }

    for (Thread thread : threadList)
    {
      thread.start();
    }

    boolean timedOut = !latch.await(TIMEOUT, TimeUnit.SECONDS);

    for (Exception ex : exceptions)
    {
      System.out.println();
      System.out.println();
      IOUtil.print(ex);
    }

    if (timedOut)
    {
      fail("Timeout after " + TIMEOUT + " seconds");
    }
    else
    {
      assertEquals(0, exceptions.size());
    }
  }

  public void testPushModeNewObjects() throws Exception
  {
    IOUtil.OUT().println("Creating category1");
    Category category1 = getModel1Factory().createCategory();
    category1.setName("category1");

    IOUtil.OUT().println("Creating category2");
    Category category2 = getModel1Factory().createCategory();
    category2.setName("category2");

    IOUtil.OUT().println("Creating category3");
    Category category3 = getModel1Factory().createCategory();
    category3.setName("category3");

    IOUtil.OUT().println("Creating company");
    Company company = getModel1Factory().createCompany();
    company.setName("Foundation");

    IOUtil.OUT().println("Adding categories");
    company.getCategories().add(category1);
    category1.getCategories().add(category2);
    category1.getCategories().add(category3);

    CDOSession session = openSession();
    CDOTransaction transaction = session.openTransaction();

    CDOPushTransaction pushTransaction = new CDOPushTransaction(transaction);
    File file = pushTransaction.getFile();

    CDOResource resource = pushTransaction.getOrCreateResource(getResourcePath("/test1"));
    resource.getContents().add(company);

    pushTransaction.commit();
    pushTransaction.close();
    transaction = session.openTransaction();
    pushTransaction = new CDOPushTransaction(transaction, file);
    pushTransaction.push();

    session.close();
    session = openSession();

    CDOView view = session.openView();
    resource = view.getResource(getResourcePath("/test1"));
    company = (Company)resource.getContents().get(0);
    assertEquals("Foundation", company.getName());
    assertEquals(1, company.getCategories().size());
    assertEquals(2, company.getCategories().get(0).getCategories().size());
    session.close();
  }

  public void testPushModeDeltas() throws Exception
  {
    IOUtil.OUT().println("Creating category1");
    Category category1 = getModel1Factory().createCategory();
    category1.setName("category1");

    IOUtil.OUT().println("Creating company");
    Company company = getModel1Factory().createCompany();
    company.setName("Foundation");

    IOUtil.OUT().println("Adding categories");
    company.getCategories().add(category1);

    CDOSession session = openSession();
    CDOTransaction transaction = session.openTransaction();

    CDOPushTransaction pushTransaction = new CDOPushTransaction(transaction);
    File file = pushTransaction.getFile();

    CDOResource resource = pushTransaction.getOrCreateResource(getResourcePath("/test1"));
    resource.getContents().add(company);

    pushTransaction.commit();

    IOUtil.OUT().println("Creating category2");
    Category category2 = getModel1Factory().createCategory();
    category2.setName("category2");
    category1.getCategories().add(category2);

    IOUtil.OUT().println("Creating category3");
    Category category3 = getModel1Factory().createCategory();
    category3.setName("category3");
    category1.getCategories().add(category3);

    pushTransaction.commit();
    pushTransaction.close();

    transaction = session.openTransaction();
    pushTransaction = new CDOPushTransaction(transaction, file);
    pushTransaction.push();

    session.close();
    session = openSession();

    CDOView view = session.openView();
    resource = view.getResource(getResourcePath("/test1"));
    company = (Company)resource.getContents().get(0);
    assertEquals("Foundation", company.getName());
    assertEquals(1, company.getCategories().size());
    assertEquals(2, company.getCategories().get(0).getCategories().size());
    session.close();
  }

  public void testAutoRollbackOnConflictEvent() throws Exception
  {
    final CDOSession session1 = openSession();
    final CDOTransaction transaction1 = session1.openTransaction();

    CDOResource resource1 = transaction1.createResource(getResourcePath("/test"));
    Category category1 = getModel1Factory().createCategory();
    resource1.getContents().add(category1);
    transaction1.commit();

    final CDOSession session2 = openSession();
    final CDOTransaction transaction2 = session2.openTransaction();
    transaction2.addListener(new IListener()
    {
      public void notifyEvent(IEvent event)
      {
        if (event instanceof CDOTransactionConflictEvent)
        {
          transaction2.rollback();
        }
      }
    });

    final CountDownLatch rollback = new CountDownLatch(1);
    transaction2.addTransactionHandler(new CDOTransactionHandler2()
    {
      public void rolledBackTransaction(CDOTransaction transaction)
      {
        IOUtil.OUT().println("rollback");
        rollback.countDown();
      }

      public void committingTransaction(CDOTransaction transaction, CDOCommitContext commitContext)
      {
      }

      public void committedTransaction(CDOTransaction transaction, CDOCommitContext commitContext)
      {
      }
    });

    CDOResource resource2 = transaction2.getResource(getResourcePath("/test"));
    Category category2 = (Category)resource2.getContents().get(0);
    category2.setName("session2");

    category1.setName("session1");
    transaction1.commit();

    rollback.await(DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS);
    category2.setName("session2");
    transaction2.commit();
  }

  /**
   * Bug 283131
   */
  public void testRollbackWithNotification() throws CommitException
  {
    Category category = getModel1Factory().createCategory();
    category.setName("name1");

    CDOSession session = openSession();
    CDOTransaction transaction = session.openTransaction();
    CDOResource resource1 = transaction.createResource(getResourcePath("/test"));

    resource1.getContents().add(category);
    transaction.commit();

    category.setName("name2");

    TestAdapter testAdapter = new TestAdapter();
    category.eAdapters().add(testAdapter);

    transaction.rollback();

    Notification[] notifications = testAdapter.getNotifications();
    assertEquals(1, notifications.length);

    Notification notification = notifications[0];
    assertEquals("name2", notification.getOldStringValue());
    assertEquals("name1", notification.getNewStringValue());
  }

  public void testManualRollbackOnConflictException() throws Exception
  {
    CDOSession session1 = openSession();
    CDOTransaction transaction1 = session1.openTransaction();

    CDOResource resource1 = transaction1.createResource(getResourcePath("/test"));
    Category category1 = getModel1Factory().createCategory();
    resource1.getContents().add(category1);
    transaction1.commit();

    CDOSession session2 = openSession();
    CDOTransaction transaction2 = session2.openTransaction();

    CDOResource resource2 = transaction2.getResource(getResourcePath("/test"));
    Category category2 = (Category)resource2.getContents().get(0);
    category2.setName("session2");

    category1.setName("session1");
    commitAndSync(transaction1, transaction2);
    IOUtil.OUT().println("After transaction1.commit(): " + CDOUtil.getCDOObject(category1).cdoRevision());

    CDOObject cdoCategory2 = CDOUtil.getCDOObject(category2);

    try
    {
      IOUtil.OUT().println("Before transaction2.commit(): " + cdoCategory2.cdoRevision());
      category2.setName("session2");

      transaction2.commit();
      fail("CommitException expected");
    }
    catch (CommitException expected)
    {
      IOUtil.OUT().println("Before transaction2.rollback(): " + cdoCategory2.cdoRevision());
      transaction2.rollback();
      IOUtil.OUT().println("After transaction2.rollback(): " + cdoCategory2.cdoRevision());
    }

    category2.setName("session2");

    IOUtil.OUT().println("Before transaction2.commit():");
    CDOCommitInfoUtil.dump(IOUtil.OUT(), transaction2.getChangeSetData());

    transaction2.commit();
    IOUtil.OUT().println("After transaction2.commit(): " + cdoCategory2.cdoRevision());
    assertEquals(3, cdoCategory2.cdoRevision().getVersion());
  }

  public void testLongCommit() throws Exception
  {
    OMPlatform.INSTANCE.setDebugging(true);

    CDOCommitInfoHandler handler = new CDOCommitInfoHandler()
    {
      public void handleCommitInfo(CDOCommitInfo commitInfo)
      {
        sleep(3000L);
      }
    };

    IRepository repository = getRepository();
    repository.addCommitInfoHandler(handler);

    try
    {
      CDOSession session = openSession();
      if (session instanceof CDONet4jSession)
      {
        ((CDONet4jSession)session).options().setCommitTimeout(1);

      }
      CDOTransaction transaction = session.openTransaction();
      CDOResource resource = transaction.getOrCreateResource(getResourcePath("/test1"));
      resource.getContents().add(getModel1Factory().createCompany());

      transaction.commit();
    }
    finally
    {
      repository.removeCommitInfoHandler(handler);
    }
  }

  public void _testLongCommit2() throws Exception
  {
    OMPlatform.INSTANCE.setDebugging(true);
    Field field = ReflectUtil.getField(CommitTransactionRequest.class, "sleepMillisForTesting");

    try
    {
      ReflectUtil.setValue(field, null, 1000L);

      org.eclipse.emf.cdo.net4j.CDONet4jSession session = (org.eclipse.emf.cdo.net4j.CDONet4jSession)openSession();
      session.options().setCommitTimeout(60);

      CDOTransaction transaction = session.openTransaction();
      CDOResource resource = transaction.getOrCreateResource(getResourcePath("/test1"));

      for (int i = 0; i < 300; i++)
      {
        resource.getContents().add(getModel1Factory().createCompany());
      }

      transaction.commit();
    }
    finally
    {
      ReflectUtil.setValue(field, null, 0L);
    }
  }

  /**
   * Bug 353167.
   */
  public void testReattachCommit() throws Exception
  {
    Company company = getModel1Factory().createCompany();

    CDOSession session = openSession();
    CDOTransaction transaction = session.openTransaction();
    CDOResource resource = transaction.getOrCreateResource(getResourcePath("/test1"));
    resource.getContents().add(company);
    transaction.commit();

    resource.getContents().remove(company);
    resource.getContents().add(company);
    transaction.commit();
  }

  /**
   * Bug 353167.
   */
  public void testReattachModifyCommit() throws Exception
  {
    {
      Company company = getModel1Factory().createCompany();

      CDOSession session = openSession();
      CDOTransaction transaction = session.openTransaction();
      CDOResource resource = transaction.getOrCreateResource(getResourcePath("/test1"));
      resource.getContents().add(company);
      transaction.commit();

      resource.getContents().remove(company);
      resource.getContents().add(company);
      company.setName("ESC");
      transaction.commit();
    }

    CDOSession session2 = openSession();
    CDOTransaction transaction2 = session2.openTransaction();
    CDOResource resource2 = transaction2.getOrCreateResource(getResourcePath("/test1"));
    Company company2 = (Company)resource2.getContents().get(0);
    assertEquals("ESC", company2.getName());
  }

  /**
   * Bug 353167.
   */
  public void testReattachCommitWithSavepoints() throws Exception
  {
    Company company = getModel1Factory().createCompany();

    CDOSession session = openSession();
    CDOTransaction transaction = session.openTransaction();
    CDOResource resource = transaction.getOrCreateResource(getResourcePath("/test1"));
    resource.getContents().add(company);
    transaction.commit();

    resource.getContents().remove(company);
    transaction.setSavepoint();

    resource.getContents().add(company);
    transaction.setSavepoint();
    transaction.commit();
  }

  /**
   * Bug 353167.
   */
  public void testReattachModifyCommitWithSavepoints() throws Exception
  {
    {
      Company company = getModel1Factory().createCompany();

      CDOSession session = openSession();
      CDOTransaction transaction = session.openTransaction();
      CDOResource resource = transaction.getOrCreateResource(getResourcePath("/test1"));
      resource.getContents().add(company);
      transaction.commit();

      resource.getContents().remove(company);
      // transaction.setSavepoint();

      resource.getContents().add(company);
      transaction.setSavepoint();

      company.setName("ESC");

      transaction.commit();
    }

    CDOSession session2 = openSession();
    CDOTransaction transaction2 = session2.openTransaction();
    CDOResource resource2 = transaction2.getOrCreateResource(getResourcePath("/test1"));
    Company company2 = (Company)resource2.getContents().get(0);
    assertEquals("ESC", company2.getName());
  }
}
