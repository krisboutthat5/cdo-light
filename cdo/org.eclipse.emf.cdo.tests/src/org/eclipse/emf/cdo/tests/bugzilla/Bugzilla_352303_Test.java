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
package org.eclipse.emf.cdo.tests.bugzilla;

import org.eclipse.emf.cdo.common.lob.CDOBlob;
import org.eclipse.emf.cdo.eresource.CDOResource;
import org.eclipse.emf.cdo.session.CDOSession;
import org.eclipse.emf.cdo.tests.AbstractCDOTest;
import org.eclipse.emf.cdo.tests.model3.Image;
import org.eclipse.emf.cdo.transaction.CDOTransaction;
import org.eclipse.emf.cdo.view.CDOView;

import org.eclipse.net4j.util.io.IOUtil;

import java.io.InputStream;

/**
 * @author Eike Stepper
 */
public class Bugzilla_352303_Test extends AbstractCDOTest
{
  @CleanRepositoriesBefore
  public void testReadBlob() throws Exception
  {
    commitBlob();

    getRepository().getRevisionManager().getCache().clear();

    CDOSession session = openSession();
    CDOView view = session.openView();
    CDOResource resource = view.getResource(getResourcePath("res"));

    Image image = (Image)resource.getContents().get(0);
    assertEquals(320, image.getWidth());
    assertEquals(200, image.getHeight());

    CDOBlob blob = image.getData();
    assertEquals(null, blob);
  }

  private void commitBlob() throws Exception
  {
    InputStream inputStream = null;

    try
    {
      Image image = getModel3Factory().createImage();
      image.setWidth(320);
      image.setHeight(200);

      CDOSession session = openSession();
      CDOTransaction transaction = session.openTransaction();
      CDOResource resource = transaction.createResource(getResourcePath("res"));
      resource.getContents().add(image);

      transaction.commit();
    }
    finally
    {
      IOUtil.close(inputStream);
    }
  }
}
