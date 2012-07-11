/**
 * Copyright (c) 2004 - 2011 Eike Stepper (Berlin, Germany) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Simon McDuff - initial API and implementation
 *    Eike Stepper - maintenance
 */
package org.eclipse.emf.cdo.view;

import org.eclipse.emf.cdo.common.revision.CDORevision;
import org.eclipse.emf.cdo.common.util.CDOFetchRule;
import org.eclipse.emf.cdo.session.CDOCollectionLoadingPolicy;

import java.util.Collection;
import java.util.List;

/**
 * Collects and updates {@link CDOFetchRule fetch rules} for {@link CDORevision revisions}, usually based on
 * {@link CDOFeatureAnalyzer feature analyzer} statistics.
 * 
 * @author Simon McDuff
 * @since 2.0
 * @noextend This interface is not intended to be extended by clients.
 * @noimplement This interface is not intended to be implemented by clients.
 */
public interface CDOFetchRuleManager
{
  public long getContext();

  public List<CDOFetchRule> getFetchRules(Collection<Long> ids);

  public CDOCollectionLoadingPolicy getCollectionLoadingPolicy();
}
