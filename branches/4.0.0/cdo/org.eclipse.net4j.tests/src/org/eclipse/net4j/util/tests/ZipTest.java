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
package org.eclipse.net4j.util.tests;

import org.eclipse.net4j.util.io.ZIPUtil;

import java.io.File;
import java.io.IOException;

/**
 * @author Eike Stepper
 */
public class ZipTest extends AbstractOMTest
{
  public void testZip() throws Exception
  {
    File zipFile = newFile("src.zip"); //$NON-NLS-1$
    File sourceFolder = newFile("src"); //$NON-NLS-1$
    ZIPUtil.zip(sourceFolder, false, zipFile);
  }

  private static File newFile(String path) throws IOException
  {
    return new File(path).getCanonicalFile();
  }
}
