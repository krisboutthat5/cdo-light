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
package org.eclipse.emf.cdo.tests.config.impl;

import org.eclipse.emf.cdo.tests.config.IConfig;

import org.eclipse.net4j.util.ObjectUtil;

import java.util.Map;
import java.util.Set;

/**
 * @author Eike Stepper
 */
public abstract class Config implements IConfig
{
  private static final long serialVersionUID = 1L;

  private String name;

  private transient ConfigTest currentTest;

  public Config(String name)
  {
    this.name = name;
  }

  public String getName()
  {
    return name;
  }

  @Override
  public String toString()
  {
    return getName();
  }

  @Override
  public boolean equals(Object obj)
  {
    if (obj == this)
    {
      return true;
    }

    if (obj instanceof IConfig)
    {
      IConfig that = (IConfig)obj;
      return ObjectUtil.equals(name, that.getName());
    }

    return false;
  }

  @Override
  public int hashCode()
  {
    return ObjectUtil.hashCode(name);
  }

  public ConfigTest getCurrentTest()
  {
    return currentTest;
  }

  public void setCurrentTest(ConfigTest currentTest)
  {
    this.currentTest = currentTest;
  }

  public Map<String, Object> getTestProperties()
  {
    return currentTest.getTestProperties();
  }

  public Object getTestProperty(String key)
  {
    Map<String, Object> testProperties = getTestProperties();
    if (testProperties != null)
    {
      return testProperties.get(key);
    }

    return null;
  }

  public boolean isValid(Set<IConfig> configs)
  {
    return true;
  }

  public void setUp() throws Exception
  {
    if (currentTest == null)
    {
      throw new IllegalStateException("currentTest == null");
    }
  }

  public void tearDown() throws Exception
  {
  }
}
