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
package org.eclipse.emf.cdo.internal.common.branch;

import org.eclipse.emf.cdo.common.branch.CDOBranch;
import org.eclipse.emf.cdo.common.branch.CDOBranchCreatedEvent;
import org.eclipse.emf.cdo.common.branch.CDOBranchHandler;
import org.eclipse.emf.cdo.common.branch.CDOBranchManager;
import org.eclipse.emf.cdo.common.branch.CDOBranchPoint;
import org.eclipse.emf.cdo.common.util.CDOTimeProvider;
import org.eclipse.emf.cdo.spi.common.branch.InternalCDOBranch;
import org.eclipse.emf.cdo.spi.common.branch.InternalCDOBranchManager;
import org.eclipse.emf.cdo.spi.common.branch.InternalCDOBranchManager.BranchLoader.BranchInfo;

import org.eclipse.net4j.util.collection.Pair;
import org.eclipse.net4j.util.container.Container;
import org.eclipse.net4j.util.event.Event;
import org.eclipse.net4j.util.ref.ReferenceValueMap;
import org.eclipse.net4j.util.ref.ReferenceValueMap.Soft;

import java.util.Map;

/**
 * @author Eike Stepper
 */
public class CDOBranchManagerImpl extends Container<CDOBranch> implements InternalCDOBranchManager
{
  private BranchLoader branchLoader;

  private CDOTimeProvider timeProvider;

  private InternalCDOBranch mainBranch;

  private Map<Integer, InternalCDOBranch> branches = createMap();

  public CDOBranchManagerImpl()
  {
  }

  public BranchLoader getBranchLoader()
  {
    return branchLoader;
  }

  public void setBranchLoader(BranchLoader branchLoader)
  {
    checkInactive();
    this.branchLoader = branchLoader;
  }

  public CDOTimeProvider getTimeProvider()
  {
    return timeProvider;
  }

  public void setTimeProvider(CDOTimeProvider timeProvider)
  {
    this.timeProvider = timeProvider;
  }

  public void initMainBranch(boolean local, long timeStamp)
  {
    mainBranch = new CDOBranchImpl.Main(this, local, timeStamp);
  }


  public CDOBranch[] getElements()
  {
    return new CDOBranch[] { getMainBranch() };
  }

  public InternalCDOBranch getMainBranch()
  {
    checkActive();
    return mainBranch;
  }

  public InternalCDOBranch getBranch(int branchID)
  {
    checkActive();
    if (branchID == CDOBranch.MAIN_BRANCH_ID)
    {
      return mainBranch;
    }

    InternalCDOBranch branch;
    synchronized (branches)
    {
      branch = branches.get(branchID);
      if (branch == null)
      {
        branch = new CDOBranchImpl(this, branchID, null);
        putBranch(branch);
      }
    }

    return branch;
  }

  public InternalCDOBranch getBranch(int id, String name)
  {
    synchronized (branches)
    {
      InternalCDOBranch branch = branches.get(id);
      if (branch == null)
      {
        branch = new CDOBranchImpl(this, id, name);
        putBranch(branch);
      }
      else if (branch.isProxy())
      {
        branch.setBranchInfo(name);
      }

      return branch;
    }
  }

  public InternalCDOBranch getBranch(int id, BranchInfo branchInfo)
  {
    String name = branchInfo.getName();
    return getBranch(id, name);
  }

  public InternalCDOBranch getBranch(String path)
  {
    if (path.startsWith(CDOBranch.PATH_SEPARATOR))
    {
      path = path.substring(1);
    }

    int sep = path.indexOf(CDOBranch.PATH_SEPARATOR);
    if (sep == -1)
    {
      if (CDOBranch.MAIN_BRANCH_NAME.equals(path))
      {
        return mainBranch;
      }

      return null;
    }

    String name = path.substring(0, sep);
    if (CDOBranch.MAIN_BRANCH_NAME.equals(name))
    {
      String rest = path.substring(sep + 1);
      return mainBranch.getBranch(rest);
    }

    return null;
  }

  public int getBranches(int startID, int endID, CDOBranchHandler handler)
  {
    checkActive();
    return branchLoader.loadBranches(startID, endID, handler);
  }

  /**
   * {@link #branches} must be synchronized by caller!
   */
  private boolean putBranch(InternalCDOBranch branch)
  {
    int id = branch.getID();
    if (branches.containsKey(id))
    {
      return false;
    }

    branches.put(id, branch);
    return true;
  }

  protected Soft<Integer, InternalCDOBranch> createMap()
  {
    return new ReferenceValueMap.Soft<Integer, InternalCDOBranch>();
  }

  @Override
  protected void doBeforeActivate() throws Exception
  {
    super.doBeforeActivate();
    checkState(branchLoader, "branchLoader"); //$NON-NLS-1$
    checkState(timeProvider, "timeProvider"); //$NON-NLS-1$
  }

  /**
   * @author Eike Stepper
   */
  private static final class BranchCreatedEvent extends Event implements CDOBranchCreatedEvent
  {
    private static final long serialVersionUID = 1L;

    private CDOBranch branch;

    public BranchCreatedEvent(CDOBranch branch)
    {
      super(branch.getBranchManager());
      this.branch = branch;
    }

    @Override
    public CDOBranchManager getSource()
    {
      return (CDOBranchManager)super.getSource();
    }

    public CDOBranch getBranch()
    {
      return branch;
    }
  }


}
