/**
 * Copyright (c) 2004 - 2011 Eike Stepper (Berlin, Germany) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Eike Stepper - initial API and implementation
 */
package org.eclipse.emf.cdo.internal.common.commit;

import java.util.Collections;
import java.util.List;

import org.eclipse.emf.cdo.common.branch.CDOBranch;
import org.eclipse.emf.cdo.common.commit.CDOChangeKind;
import org.eclipse.emf.cdo.common.commit.CDOChangeSetData;
import org.eclipse.emf.cdo.common.commit.CDOCommitInfo;
import org.eclipse.emf.cdo.common.commit.CDOCommitInfoManager;
import org.eclipse.emf.cdo.common.model.CDOPackageUnit;
import org.eclipse.emf.cdo.common.revision.CDORevision;
import org.eclipse.emf.cdo.common.revision.delta.CDORevisionDelta;

/**
 * @author Eike Stepper
 */
public class FailureCommitInfo implements CDOCommitInfo
{

  public FailureCommitInfo()
  {
  }


  public String getUserID()
  {
    return null;
  }

  public String getComment()
  {
    return null;
  }

  public CDOCommitInfoManager getCommitInfoManager()
  {
    return null;
  }

  public boolean isEmpty()
  {
    return true;
  }

  public List<CDOPackageUnit> getNewPackageUnits()
  {
    return Collections.emptyList();
  }

  public List<CDORevision> getNewObjects()
  {
    return Collections.emptyList();
  }

  public List<CDORevisionDelta> getChangedObjects()
  {
    return Collections.emptyList();
  }

  public List<Long> getDetachedObjects()
  {
    return Collections.emptyList();
  }

  public CDOChangeKind getChangeKind(long id)
  {
    return null;
  }

  public CDOChangeSetData copy()
  {
    return this;
  }

  public void merge(CDOChangeSetData changeSetData)
  {
    throw new UnsupportedOperationException();
  }
}
