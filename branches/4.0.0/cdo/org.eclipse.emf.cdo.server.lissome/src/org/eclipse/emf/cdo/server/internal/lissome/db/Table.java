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
package org.eclipse.emf.cdo.server.internal.lissome.db;

import org.eclipse.emf.cdo.common.CDOCommonRepository.IDGenerationLocation;
import org.eclipse.emf.cdo.server.internal.lissome.LissomeStore;
import org.eclipse.emf.cdo.spi.server.InternalRepository;

import org.eclipse.net4j.db.DBType;
import org.eclipse.net4j.db.ddl.IDBField;
import org.eclipse.net4j.db.ddl.IDBTable;

/**
 * @author Eike Stepper
 */
public class Table
{
  protected Index index;

  protected IDBTable table;

  public Table(Index index, String name)
  {
    this.index = index;
    table = index.addTable(name);
  }

  public Index getIndex()
  {
    return index;
  }

  public LissomeStore getStore()
  {
    return index.getStore();
  }

  public InternalRepository getRepository()
  {
    return getStore().getRepository();
  }

  public IDGenerationLocation getIDGenerationLocation()
  {
    return getRepository().getIDGenerationLocation();
  }

  public boolean isSupportingAudits()
  {
    return getRepository().isSupportingAudits();
  }

  public boolean isSupportingBranches()
  {
    return getRepository().isSupportingBranches();
  }

  @Override
  public String toString()
  {
    return table.toString();
  }

  protected IDBField addCDOIDField(String name)
  {
    if (getIDGenerationLocation() == IDGenerationLocation.CLIENT)
    {
      return table.addField(name, DBType.BINARY, 64);
    }

    return table.addField(name, DBType.BIGINT);
  }
}
