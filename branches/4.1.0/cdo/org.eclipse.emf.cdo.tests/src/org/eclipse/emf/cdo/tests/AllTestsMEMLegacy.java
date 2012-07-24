/*
 * Copyright (c) 2004 - 2012 Eike Stepper (Berlin, Germany) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Martin Fluegge - initial API and implementation
 */
package org.eclipse.emf.cdo.tests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Martin Fluegge
 */
public class AllTestsMEMLegacy extends AllConfigs
{
  public static Test suite()
  {
    return new AllTestsMEMLegacy().getTestSuite(AllConfigs.class.getName());
  }

  @Override
  protected void initConfigSuites(TestSuite parent)
  {
    addScenario(parent, COMBINED, MEM, JVM, LEGACY);
    addScenario(parent, COMBINED, MEM_AUDITS, JVM, LEGACY);
    addScenario(parent, COMBINED, MEM_BRANCHES, JVM, LEGACY);
  }
}
