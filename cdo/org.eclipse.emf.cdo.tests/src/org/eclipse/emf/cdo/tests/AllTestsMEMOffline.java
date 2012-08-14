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

import org.eclipse.emf.cdo.tests.config.IScenario;
import org.eclipse.emf.cdo.tests.config.impl.ConfigTest;
import org.eclipse.emf.cdo.tests.offline.FailoverTest;
import org.eclipse.emf.cdo.tests.offline.OfflineDelayed2Test;
import org.eclipse.emf.cdo.tests.offline.OfflineLockReplicationTest;
import org.eclipse.emf.cdo.tests.offline.OfflineLockingTest;
import org.eclipse.emf.cdo.tests.offline.OfflineTest;

import java.util.List;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Eike Stepper
 */
public class AllTestsMEMOffline extends AllConfigs
{
  public static Test suite()
  {
    return new AllTestsMEMOffline().getTestSuite(AllConfigs.class.getName());
  }

  @Override
  protected void initConfigSuites(TestSuite parent)
  {
    addScenario(parent, COMBINED, MEM_OFFLINE, JVM, NATIVE);
  }

  @Override
  protected void initTestClasses(List<Class<? extends ConfigTest>> testClasses, IScenario scenario)
  {
    testClasses.add(OfflineLockingTest.class);
    testClasses.add(OfflineLockReplicationTest.class);
    testClasses.add(OfflineTest.class);
    testClasses.add(OfflineDelayed2Test.class);
    testClasses.add(FailoverTest.class);

    // MEM does not support raw replication
    // testClasses.add(OfflineRawTest.class);
    // testClasses.add(OfflineLockRawReplicationTest.class);
  }
}
