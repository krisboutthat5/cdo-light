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
package org.eclipse.emf.cdo.tests.performance.framework;

import org.eclipse.emf.cdo.tests.config.IScenario;
import org.eclipse.emf.cdo.tests.config.impl.ConfigTest;
import org.eclipse.emf.cdo.tests.config.impl.ConfigTestSuite;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Eike Stepper
 */
public abstract class PerformanceTestSuite extends ConfigTestSuite
{
  public static final int DEFAULT_RUNS_PER_TEST_CASE = 10;

  private final List<PerformanceRecord> performanceRecords = new ArrayList<PerformanceRecord>();

  private final int runsPerTestCase;

  public PerformanceTestSuite(int runsPerTestCase)
  {
    this.runsPerTestCase = runsPerTestCase;
  }

  public PerformanceTestSuite()
  {
    this(DEFAULT_RUNS_PER_TEST_CASE);
  }

  public int getRunsPerTestCase()
  {
    return runsPerTestCase;
  }

  protected PerformanceRecord createPerformanceRecord(IScenario scenario, String testName, String testCaseName, int runs)
  {
    PerformanceRecord performanceRecord = new PerformanceRecord(scenario, testName, testCaseName, runs);
    performanceRecords.add(performanceRecord);
    return performanceRecord;
  }

  protected IPerformanceRecordAnalyzer createPerformanceRecordAnalyzer()
  {
    return new PrintStreamPerformanceRecordAnalyzer();
  }

  @Override
  protected void prepareTest(ConfigTest configTest)
  {
    super.prepareTest(configTest);
    if (configTest instanceof PerformanceTest)
    {
      PerformanceTest performanceTest = (PerformanceTest)configTest;
      performanceTest.setSuite(this);
    }
  }

  @Override
  protected void mainSuiteFinished()
  {
    super.mainSuiteFinished();

    IPerformanceRecordAnalyzer performanceRecordAnalyzer = createPerformanceRecordAnalyzer();
    performanceRecordAnalyzer.analyze(performanceRecords);
  }
}
