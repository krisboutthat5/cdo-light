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
import org.eclipse.emf.cdo.tests.config.IConstants;
import org.eclipse.emf.cdo.tests.config.IContainerConfig;
import org.eclipse.emf.cdo.tests.config.IModelConfig;
import org.eclipse.emf.cdo.tests.config.IRepositoryConfig;
import org.eclipse.emf.cdo.tests.config.IScenario;
import org.eclipse.emf.cdo.tests.config.ISessionConfig;

import org.eclipse.net4j.util.WrappedException;
import org.eclipse.net4j.util.collection.CaseInsensitiveStringSet;
import org.eclipse.net4j.util.io.IOUtil;
import org.eclipse.net4j.util.om.OMPlatform;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.MessageFormat;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Eike Stepper
 */
public class Scenario implements IScenario
{
  public static final String STATE_FILE = "cdo_config_test.state";

  private static final long serialVersionUID = 1L;

  private IContainerConfig containerConfig;

  private IRepositoryConfig repositoryConfig;

  private ISessionConfig sessionConfig;

  private IModelConfig modelConfig;

  private transient Set<IConfig> configs;

  private transient ConfigTest currentTest;

  public Scenario()
  {
  }

  public Scenario(IContainerConfig containerConfig, IRepositoryConfig repositoryConfig, ISessionConfig sessionConfig,
      IModelConfig modelConfig)
  {
    this.containerConfig = containerConfig;
    this.repositoryConfig = repositoryConfig;
    this.sessionConfig = sessionConfig;
    this.modelConfig = modelConfig;
  }

  public IContainerConfig getContainerConfig()
  {
    return containerConfig;
  }

  public Scenario setContainerConfig(IContainerConfig containerConfig)
  {
    configs = null;
    this.containerConfig = containerConfig;
    if (containerConfig != null)
    {
      containerConfig.setCurrentTest(currentTest);
    }

    return this;
  }

  public IRepositoryConfig getRepositoryConfig()
  {
    return repositoryConfig;
  }

  public Scenario setRepositoryConfig(IRepositoryConfig repositoryConfig)
  {
    configs = null;
    this.repositoryConfig = repositoryConfig;
    if (repositoryConfig != null)
    {
      repositoryConfig.setCurrentTest(currentTest);
    }

    return this;
  }

  public ISessionConfig getSessionConfig()
  {
    return sessionConfig;
  }

  public Scenario setSessionConfig(ISessionConfig sessionConfig)
  {
    configs = null;
    this.sessionConfig = sessionConfig;
    if (sessionConfig != null)
    {
      sessionConfig.setCurrentTest(currentTest);
    }

    return this;
  }

  public IModelConfig getModelConfig()
  {
    return modelConfig;
  }

  public Scenario setModelConfig(IModelConfig modelConfig)
  {
    configs = null;
    this.modelConfig = modelConfig;
    if (modelConfig != null)
    {
      modelConfig.setCurrentTest(currentTest);
    }

    return this;
  }

  @Override
  public String toString()
  {
    return MessageFormat.format("Scenario[{0}, {1}, {2}, {3}]", //
        getContainerConfig(), getRepositoryConfig(), getSessionConfig(), getModelConfig());
  }

  public Set<IConfig> getConfigs()
  {
    if (configs == null)
    {
      configs = new HashSet<IConfig>();
      configs.add(getContainerConfig());
      configs.add(getRepositoryConfig());
      configs.add(getSessionConfig());
      configs.add(getModelConfig());
    }

    return configs;
  }

  public Set<String> getCapabilities()
  {
    Set<String> capabilities = new CaseInsensitiveStringSet();
    containerConfig.initCapabilities(capabilities);
    repositoryConfig.initCapabilities(capabilities);
    sessionConfig.initCapabilities(capabilities);
    modelConfig.initCapabilities(capabilities);
    return capabilities;
  }

  public boolean isValid()
  {
    Set<IConfig> configs = getConfigs();
    for (IConfig config : configs)
    {
      if (!config.isValid(configs))
      {
        return false;
      }
    }

    return true;
  }

  public ConfigTest getCurrentTest()
  {
    return currentTest;
  }

  public void setCurrentTest(ConfigTest currentTest)
  {
    this.currentTest = currentTest;
    if (containerConfig != null)
    {
      containerConfig.setCurrentTest(currentTest);
    }

    if (repositoryConfig != null)
    {
      repositoryConfig.setCurrentTest(currentTest);
    }

    if (sessionConfig != null)
    {
      sessionConfig.setCurrentTest(currentTest);
    }

    if (modelConfig != null)
    {
      modelConfig.setCurrentTest(currentTest);
    }
  }

  public void setUp() throws Exception
  {
    try
    {
      getContainerConfig().setUp();
    }
    finally
    {
      try
      {
        getRepositoryConfig().setUp();
      }
      finally
      {
        try
        {
          getSessionConfig().setUp();
        }
        finally
        {
          getModelConfig().setUp();
        }
      }
    }
  }

  public void tearDown() throws Exception
  {
    try
    {
      getModelConfig().tearDown();
    }
    catch (Exception ex)
    {
      IOUtil.print(ex);
    }

    try
    {
      getSessionConfig().tearDown();
    }
    catch (Exception ex)
    {
      IOUtil.print(ex);
    }

    try
    {
      getRepositoryConfig().tearDown();
    }
    catch (Exception ex)
    {
      IOUtil.print(ex);
    }

    try
    {
      getContainerConfig().tearDown();
    }
    catch (Exception ex)
    {
      IOUtil.print(ex);
    }
  }

  public void save()
  {
    File file = getStateFile();
    ObjectOutputStream stream = null;

    try
    {
      stream = new ObjectOutputStream(IOUtil.openOutputStream(file));
      stream.writeObject(this);
    }
    catch (Exception ex)
    {
      throw WrappedException.wrap(ex);
    }
    finally
    {
      IOUtil.close(stream);
    }
  }

  public static IScenario load()
  {
    File file = getStateFile();
    if (file.exists())
    {
      FileInputStream stream = IOUtil.openInputStream(file);

      try
      {
        return (IScenario)new ObjectInputStream(stream).readObject();
      }
      catch (Exception ex)
      {
        throw WrappedException.wrap(ex);
      }
      finally
      {
        IOUtil.close(stream);
      }
    }

    return null;
  }

  public static File getStateFile()
  {
    String home = OMPlatform.INSTANCE.getProperty("user.home");
    if (home != null)
    {
      return new File(home, STATE_FILE);
    }

    return new File(STATE_FILE);
  }

  public static IScenario getDefault()
  {
    return Default.INSTANCE;
  }

  /**
   * @author Eike Stepper
   */
  private static final class Default extends Scenario
  {
    public static final IScenario INSTANCE = new Default();

    private static final long serialVersionUID = 1L;

    private Default()
    {
      setContainerConfig(IConstants.COMBINED);
      setRepositoryConfig(IConstants.MEM_BRANCHES);
      setSessionConfig(IConstants.JVM);
      setModelConfig(IConstants.NATIVE);
    }
  }
}
