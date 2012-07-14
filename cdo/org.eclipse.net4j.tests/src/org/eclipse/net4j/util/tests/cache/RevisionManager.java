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
package org.eclipse.net4j.util.tests.cache;

import org.eclipse.net4j.util.cache.Cache;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author Eike Stepper
 */
public class RevisionManager extends Cache<Revision>
{
  private Map<Integer, TimeLine> timeLines = new HashMap<Integer, TimeLine>();

  public RevisionManager()
  {
  }

  public Revision getRevision(int id, int version)
  {
    TimeLine timeLine = getTimeLine(id);
    return timeLine.getRevision(version);
  }

  public void evictElements(int elementCount)
  {
  }

  protected TimeLine getTimeLine(int id)
  {
    TimeLine timeLine = timeLines.get(id);
    if (timeLine == null)
    {
      timeLine = new TimeLine(id);
      timeLines.put(id, timeLine);
    }

    return timeLine;
  }

  protected Revision loadRevision(int id, int version)
  {
    Revision revision = new Revision(this, id, version);
    return revision;
  }

  protected void finalizeRevision(Revision revision)
  {
    // TimeLine timeLine = getTimeLine(revision.getID());
    // timeLine.addRevision(revision);
  }

  @Override
  protected void unreachableElement(Reference<? extends Revision> reference)
  {
  }

  /**
   * @author Eike Stepper
   */
  private class TimeLine
  {
    private int id;

    private List<Reference<Revision>> revisions = new ArrayList<Reference<Revision>>();

    public TimeLine(int id)
    {
      this.id = id;
    }

    public Revision getRevision(int version)
    {
      for (Iterator<Reference<Revision>> it = revisions.iterator(); it.hasNext();)
      {
        Reference<Revision> reference = it.next();
        Revision revision = reference.get();
        if (revision != null)
        {
          if (revision.getVersion() == version)
          {
            return revision;
          }
        }
        else
        {
          it.remove();
          break;
        }
      }

      long time = System.currentTimeMillis();
      Revision revision = loadRevision(id, version);
      time = System.currentTimeMillis() - time;

      addRevision(revision);
      return revision;
    }

    public void addRevision(Revision revision)
    {
      revisions.add(new CacheElement(revision, getReferenceQueue()));
    }
  }

  /**
   * @author Eike Stepper
   */
  private static final class CacheElement extends SoftReference<Revision>
  {
    private int id;

    private int version;

    // private Reference<Revision> ref;

    public CacheElement(Revision revision, ReferenceQueue<Revision> queue)
    {
      super(revision, queue);
      // ref = new WeakReference<Revision>(revision, queue);
      id = revision.getID();
      version = revision.getVersion();
    }

    @Override
    public String toString()
    {
      return "R" + id + "v" + version + (get() == null ? "" : " UNCLEARED"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
    }
  }
}
