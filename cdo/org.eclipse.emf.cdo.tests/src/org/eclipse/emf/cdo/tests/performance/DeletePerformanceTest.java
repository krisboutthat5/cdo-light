/*
 * Copyright (c) 2004 - 2012 Eike Stepper (Berlin, Germany) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *    Stefan Winkler - initial API and implementation
 */
package org.eclipse.emf.cdo.tests.performance;

import org.eclipse.emf.cdo.eresource.CDOResource;
import org.eclipse.emf.cdo.session.CDOSession;
import org.eclipse.emf.cdo.tests.model1.Category;
import org.eclipse.emf.cdo.tests.model1.Company;
import org.eclipse.emf.cdo.tests.performance.framework.PerformanceTest;
import org.eclipse.emf.cdo.transaction.CDOTransaction;
import org.eclipse.emf.cdo.util.CDOUtil;
import org.eclipse.emf.cdo.util.CommitException;

import java.util.Random;

/**
 * @author Stefan Winkler
 */
public class DeletePerformanceTest extends PerformanceTest
{
  private static final int AMOUNT_ELEMENTS = 10000;

  private Random random = new Random();

  private Company initModel() throws CommitException
  {
    msg("Initializing model ...");
    CDOSession session = openSession();
    CDOTransaction transaction = session.openTransaction();
    CDOResource resource = transaction.createResource(getResourcePath("res"));

    Company company = getModel1Factory().createCompany();

    for (int i = 0; i < AMOUNT_ELEMENTS; i++)
    {
      Category cat = getModel1Factory().createCategory();
      cat.setName(Integer.toString(i));
      company.getCategories().add(cat);
    }

    resource.getContents().add(company);
    msg("Committing model ...");
    transaction.commit();
    msg("Done.");

    return company;
  }

  @CleanRepositoriesBefore
  public void _testDeleteRandom() throws Exception
  {
    Company company = initModel();
    CDOTransaction transaction = (CDOTransaction)CDOUtil.getCDOObject(company).cdoView();

    msg("Starting to remove elements ...");

    for (int i = 0; i < AMOUNT_ELEMENTS / 4; i++)
    {
      int currentSize = AMOUNT_ELEMENTS - i;
      int indexToRemove = random.nextInt(currentSize - 1);

      company.getCategories().remove(indexToRemove);

      startProbing();
      transaction.commit();
      stopProbing();
    }
  }

  @CleanRepositoriesBefore
  public void testDeleteEveryOther() throws Exception
  {
    Company company = initModel();
    CDOTransaction transaction = (CDOTransaction)CDOUtil.getCDOObject(company).cdoView();

    msg("Starting to remove elements ...");

    for (int i = 0; i < AMOUNT_ELEMENTS / 4; i++)
    {
      int indexToRemove = AMOUNT_ELEMENTS / 2 - i;

      company.getCategories().remove(indexToRemove);

      startProbing();
      transaction.commit();
      stopProbing();
    }
  }

  @CleanRepositoriesBefore
  public void _testDeleteAtBeginning() throws Exception
  {
    Company company = initModel();
    CDOTransaction transaction = (CDOTransaction)CDOUtil.getCDOObject(company).cdoView();

    msg("Starting to remove elements ...");

    for (int i = 0; i < AMOUNT_ELEMENTS / 4; i++)
    {
      int indexToRemove = 0;

      company.getCategories().remove(indexToRemove);

      startProbing();
      transaction.commit();
      stopProbing();
    }
  }
}
