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
package org.eclipse.emf.internal.cdo.object;

import org.eclipse.emf.cdo.CDOObject;
import org.eclipse.emf.cdo.CDOObjectReference;
import org.eclipse.emf.cdo.common.id.CDOIDReference;
import org.eclipse.emf.cdo.view.CDOView;

import org.eclipse.emf.ecore.EStructuralFeature;

/**
 * @author Eike Stepper
 * @since 4.0
 */
public class CDOObjectReferenceImpl implements CDOObjectReference
{
  private CDOView view;

  private CDOIDReference delegate;

  public CDOObjectReferenceImpl(CDOView view, CDOIDReference delegate)
  {
    this.view = view;
    this.delegate = delegate;
  }

  public CDOObject getTargetObject()
  {
    return view.getObject(delegate.getTargetID());
  }

  public CDOObject getSourceObject()
  {
    return view.getObject(delegate.getSourceID());
  }

  public EStructuralFeature getSourceFeature()
  {
    return delegate.getSourceFeature();
  }

  public int getSourceIndex()
  {
    return delegate.getSourceIndex();
  }

  @Override
  public String toString()
  {
    StringBuilder builder = new StringBuilder();
    builder.append(getSourceID());
    builder.append(".");
    builder.append(getSourceFeature().getName());
    int sourceIndex = getSourceIndex();
    if (sourceIndex != NO_INDEX)
    {
      builder.append("[");
      builder.append(sourceIndex);
      builder.append("]");
    }

    builder.append(" --> ");
    builder.append(getTargetID());
    return builder.toString();
  }

	public long getTargetID() {
		return delegate.getTargetID();
	}

	public long getSourceID() {
		return delegate.getSourceID();
	}

}
