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
package org.eclipse.emf.cdo.tests.util;

import org.eclipse.emf.cdo.common.branch.CDOBranchPoint;
import org.eclipse.emf.cdo.common.id.CDOID;
import org.eclipse.emf.cdo.common.revision.CDORevision;
import org.eclipse.emf.cdo.internal.common.revision.CDORevisionManagerImpl;
import org.eclipse.emf.cdo.spi.common.revision.InternalCDORevision;
import org.eclipse.emf.cdo.spi.common.revision.RevisionInfo;
import org.eclipse.emf.cdo.spi.common.revision.SyntheticCDORevision;

import org.eclipse.net4j.util.concurrent.ConcurrencyUtil;
import org.eclipse.net4j.util.event.IListener;

import java.util.List;

/**
 * @author Eike Stepper
 */
public class TestRevisionManager extends CDORevisionManagerImpl
{
  private Object lock = new Object();

  private long getRevisionsDelay;

  private int loadCounter;

  private int additionalCounter;

  public TestRevisionManager()
  {
  }

  public void setGetRevisionsDelay(long millis)
  {
    synchronized (lock)
    {
      getRevisionsDelay = millis;
    }
  }

  @Override
  public List<CDORevision> getRevisions(List<CDOID> ids, CDOBranchPoint branchPoint, int referenceChunk,
      int prefetchDepth, boolean loadOnDemand, SyntheticCDORevision[] synthetics)
  {
    if (getRevisionsDelay > 0)
    {
      long start = System.currentTimeMillis();
      for (;;)
      {
        ConcurrencyUtil.sleep(1L);
        synchronized (lock)
        {
          if (System.currentTimeMillis() > start + getRevisionsDelay)
          {
            break;
          }
        }
      }
    }

    return super.getRevisions(ids, branchPoint, referenceChunk, prefetchDepth, loadOnDemand, synthetics);
  }

  @Override
  public void addListener(IListener listener)
  {
    super.addListener(listener);
  }

  public void resetLoadCounter()
  {
    synchronized (lock)
    {
      loadCounter = 0;
    }
  }

  public int getLoadCounter()
  {
    synchronized (lock)
    {
      return loadCounter;
    }
  }

  public void resetAdditionalCounter()
  {
    synchronized (lock)
    {
      additionalCounter = 0;
    }
  }

  public int getAdditionalCounter()
  {
    synchronized (lock)
    {
      return additionalCounter;
    }
  }

  @Override
  protected List<InternalCDORevision> loadRevisions(List<RevisionInfo> infosToLoad, CDOBranchPoint branchPoint,
      int referenceChunk, int prefetchDepth)
  {
    List<InternalCDORevision> result = super.loadRevisions(infosToLoad, branchPoint, referenceChunk, prefetchDepth);

    synchronized (lock)
    {
      additionalCounter += result == null ? 0 : result.size();
      loadCounter += infosToLoad.size();
    }

    return result;
  }
}
