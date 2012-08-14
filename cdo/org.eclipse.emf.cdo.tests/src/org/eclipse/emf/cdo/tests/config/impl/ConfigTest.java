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

import org.eclipse.emf.cdo.common.revision.CDORevisionManager;
import org.eclipse.emf.cdo.server.IRepository;
import org.eclipse.emf.cdo.session.CDOSession;
import org.eclipse.emf.cdo.spi.common.revision.InternalCDORevisionManager;
import org.eclipse.emf.cdo.spi.server.InternalRepository;
import org.eclipse.emf.cdo.spi.server.InternalStore.NoExternalReferences;
import org.eclipse.emf.cdo.spi.server.InternalStore.NoFeatureMaps;
import org.eclipse.emf.cdo.spi.server.InternalStore.NoHandleRevisions;
import org.eclipse.emf.cdo.spi.server.InternalStore.NoLargeObjects;
import org.eclipse.emf.cdo.spi.server.InternalStore.NoQueryXRefs;
import org.eclipse.emf.cdo.spi.server.InternalStore.NoRawAccess;
import org.eclipse.emf.cdo.tests.config.IConstants;
import org.eclipse.emf.cdo.tests.config.IContainerConfig;
import org.eclipse.emf.cdo.tests.config.IModelConfig;
import org.eclipse.emf.cdo.tests.config.IRepositoryConfig;
import org.eclipse.emf.cdo.tests.config.IScenario;
import org.eclipse.emf.cdo.tests.config.ISessionConfig;
import org.eclipse.emf.cdo.tests.mango.MangoFactory;
import org.eclipse.emf.cdo.tests.mango.MangoPackage;
import org.eclipse.emf.cdo.tests.model1.Model1Factory;
import org.eclipse.emf.cdo.tests.model1.Model1Package;
import org.eclipse.emf.cdo.tests.model2.Model2Factory;
import org.eclipse.emf.cdo.tests.model2.Model2Package;
import org.eclipse.emf.cdo.tests.model3.Model3Factory;
import org.eclipse.emf.cdo.tests.model3.Model3Package;
import org.eclipse.emf.cdo.tests.model3.subpackage.SubpackageFactory;
import org.eclipse.emf.cdo.tests.model3.subpackage.SubpackagePackage;
import org.eclipse.emf.cdo.tests.model4.model4Factory;
import org.eclipse.emf.cdo.tests.model4.model4Package;
import org.eclipse.emf.cdo.tests.model4interfaces.model4interfacesPackage;
import org.eclipse.emf.cdo.tests.model5.Model5Factory;
import org.eclipse.emf.cdo.tests.model5.Model5Package;
import org.eclipse.emf.cdo.tests.model6.Model6Factory;
import org.eclipse.emf.cdo.tests.model6.Model6Package;

import org.eclipse.net4j.util.ObjectUtil;
import org.eclipse.net4j.util.WrappedException;
import org.eclipse.net4j.util.container.IManagedContainer;
import org.eclipse.net4j.util.io.IOUtil;
import org.eclipse.net4j.util.lifecycle.LifecycleUtil;
import org.eclipse.net4j.util.tests.AbstractOMTest;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import junit.framework.TestResult;

/**
 * @author Eike Stepper
 */
public abstract class ConfigTest extends AbstractOMTest implements IConstants
{
  @Inherited
  @Retention(RetentionPolicy.RUNTIME)
  @Target({ ElementType.TYPE, ElementType.METHOD })
  public @interface CleanRepositoriesBefore
  {
  }

  @Inherited
  @Retention(RetentionPolicy.RUNTIME)
  @Target({ ElementType.TYPE, ElementType.METHOD })
  public @interface CleanRepositoriesAfter
  {
  }

  @Inherited
  @Retention(RetentionPolicy.RUNTIME)
  @Target({ ElementType.TYPE, ElementType.METHOD })
  public @interface Requires
  {
    String[] value();
  }

  @Inherited
  @Retention(RetentionPolicy.RUNTIME)
  @Target({ ElementType.TYPE, ElementType.METHOD })
  public @interface Skips
  {
    String[] value();
  }

  public ConfigTest()
  {
  }

  private IScenario scenario;

  public synchronized IScenario getScenario()
  {
    if (scenario == null)
    {
      setScenario(getDefaultScenario());
    }

    return scenario;
  }

  public synchronized void setScenario(IScenario scenario)
  {
    this.scenario = scenario;
    if (scenario != null)
    {
      scenario.setCurrentTest(this);
    }
  }

  private Properties homeProperties;

  private Map<String, Object> testProperties;

  public synchronized Properties getHomeProperties()
  {
    if (homeProperties == null)
    {
      homeProperties = new Properties();
      String home = System.getProperty("user.home");
      if (home != null)
      {
        File file = new File(home, ".cdo_config_test.properties");
        if (file.exists())
        {
          FileInputStream stream = IOUtil.openInputStream(file);

          try
          {
            homeProperties.load(stream);
          }
          catch (IOException ex)
          {
            throw WrappedException.wrap(ex);
          }
          finally
          {
            IOUtil.close(stream);
          }
        }
      }
    }

    return homeProperties;
  }

  public synchronized void setHomeProperties(Properties homeProperties)
  {
    this.homeProperties = homeProperties;
  }

  // /////////////////////////////////////////////////////////////////////////
  // //////////////////////// Container //////////////////////////////////////

  /**
   * @category Container
   */
  public IContainerConfig getContainerConfig()
  {
    return getScenario().getContainerConfig();
  }

  /**
   * @category Container
   */
  public boolean hasClientContainer()
  {
    return getContainerConfig().hasClientContainer();
  }

  /**
   * @category Container
   */
  public boolean hasServerContainer()
  {
    return getContainerConfig().hasServerContainer();
  }

  /**
   * @category Container
   */
  public IManagedContainer getClientContainer()
  {
    return getContainerConfig().getClientContainer();
  }

  /**
   * @category Container
   */
  public IManagedContainer getServerContainer()
  {
    return getContainerConfig().getServerContainer();
  }

  // /////////////////////////////////////////////////////////////////////////
  // //////////////////////// CDORepositoryInfo /////////////////////////////////////

  /**
   * @category CDORepositoryInfo
   */
  public IRepositoryConfig getRepositoryConfig()
  {
    return getScenario().getRepositoryConfig();
  }

  /**
   * @category CDORepositoryInfo
   */
  public Map<String, String> getRepositoryProperties()
  {
    return getRepositoryConfig().getRepositoryProperties();
  }

  /**
   * @category CDORepositoryInfo
   */
  public InternalRepository getRepository(String name, boolean activate)
  {
    return getRepositoryConfig().getRepository(name, activate);
  }

  /**
   * @category CDORepositoryInfo
   */
  public InternalRepository getRepository(String name)
  {
    return getRepositoryConfig().getRepository(name);
  }

  /**
   * @category CDORepositoryInfo
   */
  public InternalRepository getRepository()
  {
    return getRepositoryConfig().getRepository(IRepositoryConfig.REPOSITORY_NAME);
  }

  public void registerRepository(IRepository repository)
  {
    getRepositoryConfig().registerRepository((InternalRepository)repository);
  }

  public InternalRepository restartRepository()
  {
    return restartRepository(IRepositoryConfig.REPOSITORY_NAME);
  }

  public InternalRepository restartRepository(String name)
  {
    try
    {
      getRepositoryConfig().setRestarting(true);
      InternalRepository repo = getRepository(name);
      LifecycleUtil.deactivate(repo);
      return getRepository(name);
    }
    finally
    {
      getRepositoryConfig().setRestarting(false);
    }
  }

  // /////////////////////////////////////////////////////////////////////////
  // //////////////////////// Session ////////////////////////////////////////

  /**
   * @category Session
   */
  public ISessionConfig getSessionConfig()
  {
    return getScenario().getSessionConfig();
  }

  /**
   * @category Session
   */
  public void startTransport() throws Exception
  {
    getSessionConfig().startTransport();
  }

  /**
   * @category Session
   */
  public void stopTransport() throws Exception
  {
    getSessionConfig().stopTransport();
  }

  public String getURIPrefix()
  {
    return getSessionConfig().getURIPrefix();
  }

  /**
   * @category Session
   */
  public CDOSession openSession()
  {
    determineCodeLink();
    return getSessionConfig().openSession();
  }

  /**
   * @category Session
   */
  public CDOSession openSession(String repositoryName)
  {
    determineCodeLink();
    return getSessionConfig().openSession(repositoryName);
  }

  // /////////////////////////////////////////////////////////////////////////
  // //////////////////////// Model //////////////////////////////////////////

  /**
   * @category Model
   */
  public IModelConfig getModelConfig()
  {
    return getScenario().getModelConfig();
  }

  /**
   * @category Model
   */
  public MangoFactory getMangoFactory()
  {
    return getModelConfig().getMangoFactory();
  }

  /**
   * @category Model
   */
  public MangoPackage getMangoPackage()
  {
    return getModelConfig().getMangoPackage();
  }

  /**
   * @category Model
   */
  public Model1Factory getModel1Factory()
  {
    return getModelConfig().getModel1Factory();
  }

  /**
   * @category Model
   */
  public Model1Package getModel1Package()
  {
    return getModelConfig().getModel1Package();
  }

  /**
   * @category Model
   */
  public Model2Factory getModel2Factory()
  {
    return getModelConfig().getModel2Factory();
  }

  /**
   * @category Model
   */
  public Model2Package getModel2Package()
  {
    return getModelConfig().getModel2Package();
  }

  /**
   * @category Model
   */
  public Model3Factory getModel3Factory()
  {
    return getModelConfig().getModel3Factory();
  }

  /**
   * @category Model
   */
  public Model3Package getModel3Package()
  {
    return getModelConfig().getModel3Package();
  }

  /**
   * @category Model
   */
  public SubpackageFactory getModel3SubpackageFactory()
  {
    return getModelConfig().getModel3SubPackageFactory();
  }

  /**
   * @category Model
   */
  public SubpackagePackage getModel3SubpackagePackage()
  {
    return getModelConfig().getModel3SubPackagePackage();
  }

  /**
   * @category Model
   */
  public model4Factory getModel4Factory()
  {
    return getModelConfig().getModel4Factory();
  }

  /**
   * @category Model
   */
  public model4Package getModel4Package()
  {
    return getModelConfig().getModel4Package();
  }

  /**
   * @category Model
   */
  public model4interfacesPackage getModel4InterfacesPackage()
  {
    return getModelConfig().getModel4InterfacesPackage();
  }

  /**
   * @category Model
   */
  public Model5Factory getModel5Factory()
  {
    return getModelConfig().getModel5Factory();
  }

  /**
   * @category Model
   */
  public Model5Package getModel5Package()
  {
    return getModelConfig().getModel5Package();
  }

  /**
   * @category Model
   */
  public Model6Factory getModel6Factory()
  {
    return getModelConfig().getModel6Factory();
  }

  /**
   * @category Model
   */
  public Model6Package getModel6Package()
  {
    return getModelConfig().getModel6Package();
  }

  // /////////////////////////////////////////////////////////////////////////
  // /////////////////////////////////////////////////////////////////////////

  public synchronized Map<String, Object> getTestProperties()
  {
    if (testProperties == null)
    {
      testProperties = new HashMap<String, Object>();
    }

    return testProperties;
  }

  public boolean isValid()
  {
    return true;
  }

  @Override
  public String toString()
  {
    return MessageFormat.format("{0}.{1} [{2}, {3}, {4}, {5}]", getClass().getSimpleName(), getName(),
        getContainerConfig(), getRepositoryConfig(), getSessionConfig(), getModelConfig());
  }

  @Override
  public TestResult run()
  {
    try
    {
      return super.run();
    }
    catch (ConfigTestException ex)
    {
      throw ex;
    }
    catch (Throwable ex)
    {
      throw new ConfigTestException("Error in " + this, ex);
    }
  }

  @Override
  public void runBare() throws Throwable
  {
    try
    {
      super.runBare();
    }
    catch (AssertionError error)
    {
      throw error;
    }
    catch (Throwable ex)
    {
      // We wrap anything that is not a test failure
      throw new ConfigTestException("Error in " + this, ex);
    }
  }

  /**
   * Constructs a test-specific resource path of the format "/TestClass_testMethod/resourceName". Using this instead of
   * (just) a hardcoded name for the test resource, ensures that the test method is isolated from all others at the
   * resource level.
   * 
   * @param resourceName
   *          - the test-local name of the resource (or null if one wants to receive the path of the test-local resource
   *          folder
   * @return the full path of the resource
   */
  public final String getResourcePath(String resourceName)
  {
    StringBuilder builder = new StringBuilder();
    builder.append('/');
    builder.append(getClass().getSimpleName()); // Name of this test class
    builder.append('_');
    builder.append(getName()); // Name of the executing test method

    if (resourceName == null || resourceName.length() == 0 || resourceName.charAt(0) != '/')
    {
      builder.append('/');
    }

    if (resourceName != null)
    {
      builder.append(resourceName);
    }

    return builder.toString();
  }

  protected boolean isConfig(Config config)
  {
    return ObjectUtil.equals(getContainerConfig(), config) //
        || ObjectUtil.equals(getRepositoryConfig(), config) //
        || ObjectUtil.equals(getSessionConfig(), config) //
        || ObjectUtil.equals(getModelConfig(), config);
  }

  protected void skipStoreWithoutExternalReferences()
  {
    skipTest(getRepository().getStore() instanceof NoExternalReferences);
  }

  protected void skipStoreWithoutQueryXRefs()
  {
    skipTest(getRepository().getStore() instanceof NoQueryXRefs);
  }

  protected void skipStoreWithoutLargeObjects()
  {
    skipTest(getRepository().getStore() instanceof NoLargeObjects);
  }

  protected void skipStoreWithoutFeatureMaps()
  {
    skipTest(getRepository().getStore() instanceof NoFeatureMaps);
  }

  protected void skipStoreWithoutHandleRevisions()
  {
    skipTest(getRepository().getStore() instanceof NoHandleRevisions);
  }

  protected void skipStoreWithoutRawAccess()
  {
    skipTest(getRepository().getStore() instanceof NoRawAccess);
  }

  protected void clearCache(CDORevisionManager revisionManager)
  {
    ((InternalCDORevisionManager)revisionManager).getCache().clear();
  }

  public void restartScenario() throws Exception
  {
    IOUtil.OUT().println("RESTARTING SCENARIO");
    stopTransport();

    IScenario scenario = getScenario();
    scenario.tearDown();
    scenario.setUp();

    startTransport();
    IOUtil.OUT().println("RESTARTING SCENARIO - FINISHED");
  }

  protected IScenario getDefaultScenario()
  {
    IScenario scenario;

    try
    {
      scenario = Scenario.load();
      if (scenario == null)
      {
        scenario = Scenario.getDefault();
      }
    }
    catch (Exception ex)
    {
      scenario = Scenario.getDefault();
    }

    return scenario;
  }

  @Override
  public void setUp() throws Exception
  {
    getScenario();
    super.setUp();
  }

  @Override
  protected void doSetUp() throws Exception
  {
    super.doSetUp();
    getScenario().setUp();
  }

  @Override
  protected void doTearDown() throws Exception
  {
    try
    {
      getScenario().tearDown();
    }
    catch (Exception ex)
    {
      IOUtil.print(ex);
    }

    try
    {
      super.doTearDown();
    }
    catch (Exception ex)
    {
      IOUtil.print(ex);
    }
  }
}
