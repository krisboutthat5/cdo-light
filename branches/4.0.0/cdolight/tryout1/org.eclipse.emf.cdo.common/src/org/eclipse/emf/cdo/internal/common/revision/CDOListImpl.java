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
package org.eclipse.emf.cdo.internal.common.revision;

import java.util.Collection;

import org.eclipse.emf.cdo.common.model.CDOModelUtil;
import org.eclipse.emf.cdo.common.model.CDOType;
import org.eclipse.emf.cdo.common.revision.CDOList;
import org.eclipse.emf.cdo.common.revision.CDOListFactory;
import org.eclipse.emf.cdo.spi.common.revision.InternalCDOList;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.net4j.util.collection.MoveableArrayList;

/**
 * @author Simon McDuff
 */
public class CDOListImpl extends MoveableArrayList<Object> implements InternalCDOList
{
  public static final CDOListFactory FACTORY = new CDOListFactory()
  {
    public CDOList createList(int initialCapacity, int size, int initialChunk)
    {
      return new CDOListImpl(initialCapacity, size);
    }
  };

  private static final long serialVersionUID = 1L;

  private transient boolean frozen;

  public CDOListImpl(int initialCapacity, int size)
  {
    super(initialCapacity);
    for (int j = 0; j < size; j++)
    {
      this.add(UNINITIALIZED);
    }
  }

  public InternalCDOList clone(EClassifier classifier)
  {
    CDOType type = CDOModelUtil.getType(classifier);
    int size = size();
    InternalCDOList list = new CDOListImpl(size, 0);
    for (int j = 0; j < size; j++)
    {
      Object value = this.get(j);
      list.add(j, type.copyValue(value));
    }

    return list;
  }

  public Object get(int index, boolean resolve)
  {
    return super.get(index);
  }



  protected void handleAdjustReference(int index, Object element)
  {
  }

  public void freeze()
  {
    frozen = true;
  }

  private void checkFrozen()
  {
    if (frozen)
    {
      throw new IllegalStateException("Cannot modify a frozen list");
    }
  }

  @Override
  public boolean add(Object o)
  {
    checkFrozen();
    return super.add(o);
  }

  @Override
  public boolean remove(Object o)
  {
    checkFrozen();
    return super.remove(o);
  }

  @Override
  public boolean addAll(Collection<? extends Object> c)
  {
    checkFrozen();
    return super.addAll(c);
  }

  @Override
  public boolean addAll(int index, Collection<? extends Object> c)
  {
    checkFrozen();
    return super.addAll(index, c);
  }

  @Override
  public boolean removeAll(Collection<?> c)
  {
    checkFrozen();
    return super.removeAll(c);
  }

  @Override
  public boolean retainAll(Collection<?> c)
  {
    checkFrozen();
    return super.retainAll(c);
  }

  @Override
  public void clear()
  {
    checkFrozen();
    super.clear();
  }

  @Override
  public Object set(int index, Object element)
  {
    checkFrozen();
    return super.set(index, element);
  }

  @Override
  public void add(int index, Object element)
  {
    checkFrozen();
    super.add(index, element);
  }

  @Override
  public Object remove(int index)
  {
    checkFrozen();
    return super.remove(index);
  }

  public void setWithoutFrozenCheck(int index, Object element)
  {
    super.set(index, element);
  }
}
