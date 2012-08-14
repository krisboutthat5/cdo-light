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
package org.eclipse.net4j.db;

/**
 * Call-back that handles the values of, for example, a row in a database table.
 * 
 * @see DBUtil#select(java.sql.Connection, IDBRowHandler, String, org.eclipse.net4j.db.ddl.IDBField...)
 * @author Eike Stepper
 */
public interface IDBRowHandler
{
  public boolean handle(int row, Object... values);
}
