/*
 * Copyright (c) 2004 - 2012 Eike Stepper (Berlin, Germany) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Eike Stepper - initial API and implementation
 *    Stefan Winkler - Bug 283998: [DB] Chunk reading for multiple chunks fails
 *    Victor Roldan Betancort - Bug 283998: [DB] Chunk reading for multiple chunks fails
 */
package org.eclipse.emf.cdo.server.internal.lissome;

import org.eclipse.emf.cdo.common.revision.CDORevision;
import org.eclipse.emf.cdo.server.lissome.ILissomeStoreChunkReader;
import org.eclipse.emf.cdo.spi.server.StoreChunkReader;

import org.eclipse.emf.ecore.EStructuralFeature;

import java.util.List;

/**
 * @author Eike Stepper
 */
public class LissomeStoreChunkReader extends StoreChunkReader implements ILissomeStoreChunkReader
{
  public LissomeStoreChunkReader(LissomeStoreReader accessor, CDORevision revision, EStructuralFeature feature)
  {
    super(accessor, revision, feature);
  }

  @Override
  public LissomeStoreReader getAccessor()
  {
    return (LissomeStoreReader)super.getAccessor();
  }

  @Override
  public void addSimpleChunk(int index)
  {
    super.addSimpleChunk(index);
    // if (referenceMapping instanceof IListMapping2)
    // {
    // ((IListMapping2)referenceMapping).addSimpleChunkWhere(getAccessor(), getRevision().getID(), builder, index);
    // }
    // else
    // {
    // builder.append(CDODBSchema.LIST_IDX);
    // builder.append('=');
    // builder.append(index);
    // }
  }

  @Override
  public void addRangedChunk(int fromIndex, int toIndex)
  {
    super.addRangedChunk(fromIndex, toIndex);
    // if (referenceMapping instanceof IListMapping2)
    // {
    // ((IListMapping2)referenceMapping).addRangedChunkWhere(getAccessor(), getRevision().getID(), builder, fromIndex,
    // toIndex);
    // }
    // else
    // {
    // builder.append(CDODBSchema.LIST_IDX);
    //      builder.append(" BETWEEN "); //$NON-NLS-1$
    // builder.append(fromIndex);
    //      builder.append(" AND "); //$NON-NLS-1$
    // builder.append(toIndex - 1);
    // }
  }

  public List<Chunk> executeRead()
  {
    List<Chunk> chunks = getChunks();
    // if (chunks.size() > 1)
    // {
    // builder.insert(0, '(');
    // builder.append(')');
    // }
    //
    // referenceMapping.readChunks(this, chunks, builder.toString());
    return chunks;
  }
}
