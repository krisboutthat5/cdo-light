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
package org.eclipse.emf.cdo.spi.server;

import org.eclipse.emf.cdo.common.branch.CDOBranch;
import org.eclipse.emf.cdo.common.branch.CDOBranchPoint;
import org.eclipse.emf.cdo.common.id.CDOID;
import org.eclipse.emf.cdo.common.lock.IDurableLockingManager.LockArea;
import org.eclipse.emf.cdo.common.lock.IDurableLockingManager.LockGrade;

import org.eclipse.net4j.util.HexUtil;

import java.text.MessageFormat;
import java.util.Map;
import java.util.Random;

/**
 * @author Eike Stepper
 * @since 4.0
 * @noextend This interface is not intended to be extended by clients.
 */
public class DurableLockArea implements LockArea
{
  public static final int DEFAULT_DURABLE_LOCKING_ID_BYTES = 32;

  private String durableLockingID;

  private String userID;

  private CDOBranchPoint branchPoint;

  private boolean readOnly;

  private Map<CDOID, LockGrade> locks;

  public DurableLockArea(String durableLockingID, String userID, CDOBranchPoint branchPoint, boolean readOnly,
      Map<CDOID, LockGrade> locks)
  {
    this.durableLockingID = durableLockingID;
    this.userID = userID;
    this.branchPoint = branchPoint;
    this.readOnly = readOnly;
    this.locks = locks;
  }

  public String getDurableLockingID()
  {
    return durableLockingID;
  }

  public String getUserID()
  {
    return userID;
  }

  public CDOBranch getBranch()
  {
    return branchPoint.getBranch();
  }

  public long getTimeStamp()
  {
    return branchPoint.getTimeStamp();
  }

  public boolean isReadOnly()
  {
    return readOnly;
  }

  public Map<CDOID, LockGrade> getLocks()
  {
    return locks;
  }

  @Override
  public String toString()
  {
    return MessageFormat.format("DurableLockArea[id={0}, user={1}, branchPoint={2}, readOnly={3}, locks={4}]",
        durableLockingID, userID, branchPoint, readOnly, locks);
  }

  public static String createDurableLockingID()
  {
    return createDurableLockingID(DEFAULT_DURABLE_LOCKING_ID_BYTES);
  }

  public static String createDurableLockingID(int bytes)
  {
    byte[] buffer = new byte[bytes];

    Random random = new Random(System.currentTimeMillis());
    random.nextBytes(buffer);

    return HexUtil.bytesToHex(buffer);
  }
}
