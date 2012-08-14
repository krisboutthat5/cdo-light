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
package org.eclipse.emf.cdo.tests.config;

import org.eclipse.emf.cdo.common.CDOCommonRepository.IDGenerationLocation;
import org.eclipse.emf.cdo.server.IRepositoryProvider;
import org.eclipse.emf.cdo.server.IStore;
import org.eclipse.emf.cdo.spi.server.InternalRepository;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Map;

/**
 * @author Eike Stepper
 */
public interface IRepositoryConfig extends IConfig, IRepositoryProvider
{
  public static final String REPOSITORY_NAME = "repo1";

  public static final String CAPABILITY_AUDITING = "repository.auditing";

  public static final String CAPABILITY_BRANCHING = "repository.branching";

  public static final String CAPABILITY_UUIDS = "repository.uuids";

  public static final String CAPABILITY_OFFLINE = "repository.offline";

  public static final String CAPABILITY_RESTARTABLE = "repository.restartable";

  public boolean isSupportingAudits();

  public boolean isSupportingBranches();

  public IDGenerationLocation getIDGenerationLocation();

  public Map<String, String> getRepositoryProperties();

  public InternalRepository getRepository(String name, boolean activate);

  public InternalRepository getRepository(String name);

  public void registerRepository(InternalRepository repository);

  public void setRestarting(boolean on);

  public IStore createStore(String repoName);

  /**
   * @author Eike Stepper
   */
  @Inherited
  @Retention(RetentionPolicy.RUNTIME)
  @Target({ ElementType.TYPE, ElementType.METHOD })
  public @interface CallAddRepository
  {
  }
}
