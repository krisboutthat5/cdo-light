/**
 * Copyright (c) 2004 - 2011 Eike Stepper (Berlin, Germany) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Eike Stepper - initial API and implementation
 *    Simon McDuff - maintenance
 */
package org.eclipse.emf.cdo.spi.common.revision;

import org.eclipse.emf.cdo.common.branch.CDOBranch;
import org.eclipse.emf.cdo.common.branch.CDOBranchPoint;
import org.eclipse.emf.cdo.common.model.CDOClassInfo;
import org.eclipse.emf.cdo.common.protocol.CDODataInput;
import org.eclipse.emf.cdo.common.protocol.CDODataOutput;
import org.eclipse.emf.cdo.common.revision.CDOList;
import org.eclipse.emf.cdo.common.revision.CDORevision;
import org.eclipse.emf.cdo.common.revision.CDORevisionData;

import org.eclipse.emf.ecore.EStructuralFeature;

import java.io.IOException;

/**
 * @author Eike Stepper
 * @since 2.0
 */
public interface InternalCDORevision extends CDORevision, CDORevisionData
{
  /**
   * @since 3.0
   */
  public CDOClassInfo getClassInfo();

  public void setID(long id);

  public void setResourceID(long resourceID);

  public void setContainerID(long containerID);

  public void setContainingFeatureID(int containingFeatureID);

  /**
   * @since 3.0
   */
  public void adjustForCommit();

  public void add(EStructuralFeature feature, int index, Object value);

  public void clear(EStructuralFeature feature);

  public Object move(EStructuralFeature feature, int targetIndex, int sourceIndex);

  public Object remove(EStructuralFeature feature, int index);

  public Object set(EStructuralFeature feature, int index, Object value);

  public void unset(EStructuralFeature feature);

  /**
   * Should never return {@link InternalCDORevision#NIL}
   */
  public Object getValue(EStructuralFeature feature);

  public Object setValue(EStructuralFeature feature, Object value);

  public void setList(EStructuralFeature feature, InternalCDOList list);

  public CDOList getList(EStructuralFeature feature);

  /**
   * @param initialCapacity
   *          the initialCapacity of a new list to be created if this revision has no list so far (its size will always
   *          be 0), or -1 to skip list creation and return <code>null</code> in this case.
   */
  public CDOList getList(EStructuralFeature feature, int initialCapacity);

  /**
   * @since 3.0
   */
  public void read(CDODataInput in) throws IOException;

  /**
   * @since 3.0
   */
  public void write(CDODataOutput out, int referenceChunk) throws IOException;


  /**
   * @since 3.0
   */
  public InternalCDORevisionDelta compare(CDORevision origin);

  /**
   * @since 3.0
   */
  public InternalCDORevision copy();

  /**
   * @since 4.0
   */
  public void freeze();
}
