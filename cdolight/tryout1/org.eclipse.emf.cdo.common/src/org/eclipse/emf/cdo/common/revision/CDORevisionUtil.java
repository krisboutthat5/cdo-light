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
package org.eclipse.emf.cdo.common.revision;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import org.eclipse.emf.cdo.common.branch.CDOBranch;
import org.eclipse.emf.cdo.common.branch.CDOBranchManager;
import org.eclipse.emf.cdo.common.branch.CDOBranchPoint;
import org.eclipse.emf.cdo.common.commit.CDOChangeSet;
import org.eclipse.emf.cdo.common.commit.CDOChangeSetData;
import org.eclipse.emf.cdo.common.id.CDOIDUtil;
import org.eclipse.emf.cdo.common.revision.delta.CDORevisionDelta;
import org.eclipse.emf.cdo.internal.common.commit.CDOChangeSetDataImpl;
import org.eclipse.emf.cdo.internal.common.commit.CDOChangeSetImpl;
import org.eclipse.emf.cdo.internal.common.messages.Messages;
import org.eclipse.emf.cdo.internal.common.revision.CDOFeatureMapEntryImpl;
import org.eclipse.emf.cdo.internal.common.revision.CDORevisionCacheNonAuditing;
import org.eclipse.emf.cdo.internal.common.revision.CDORevisionImpl;
import org.eclipse.emf.cdo.internal.common.revision.CDORevisionManagerImpl;
import org.eclipse.emf.cdo.internal.common.revision.delta.CDORevisionDeltaImpl;
import org.eclipse.emf.cdo.spi.common.revision.CDOFeatureMapEntry;
import org.eclipse.emf.cdo.spi.common.revision.InternalCDORevision;
import org.eclipse.emf.cdo.spi.common.revision.InternalCDORevisionManager;
import org.eclipse.emf.cdo.spi.common.revision.ManagedRevisionProvider;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.FeatureMap;

/**
 * Various static helper methods for dealing with {@link CDORevision revisions}.
 * 
 * @author Eike Stepper
 */
public final class CDORevisionUtil
{
  public static final Object UNINITIALIZED = new Uninitialized();

  private CDORevisionUtil()
  {
  }

  /**
   * Creates and returns a new memory sensitive revision cache.
   * 
   * @since 4.0
   */
  public static CDORevisionCache createRevisionCache()
  {
    return CDORevisionCacheNonAuditing.INSTANCE;
  }

  /**
   * @since 4.0
   */
  public static CDORevisionManager createRevisionManager()
  {
    return new CDORevisionManagerImpl();
  }

  /**
   * @since 4.0
   */
  public static CDORevisionManager createRevisionManager(CDORevisionCache cache)
  {
    InternalCDORevisionManager revisionManager = (InternalCDORevisionManager)createRevisionManager();
    revisionManager.setCache(cache);
    return revisionManager;
  }



  /**
   * @since 4.0
   */
  public static String formatRevisionKey(CDORevisionKey key)
  {
    StringBuilder builder = new StringBuilder();
    CDOIDUtil.write(builder, key.getID());
    return builder.toString();
  }

  /**
   * @since 4.0
   */
  public static long parseRevisionKey(String source, CDOBranchManager branchManager)
  {
    StringTokenizer tokenizer = new StringTokenizer(source, ":");
    if (!tokenizer.hasMoreTokens())
    {
      throw new IllegalArgumentException("No ID segment");
    }

    String idSegment = tokenizer.nextToken();
    long id = CDOIDUtil.read(idSegment);

    if (!tokenizer.hasMoreTokens())
    {
      throw new IllegalArgumentException("No branch segment");
    }

    String branchSegment = tokenizer.nextToken();
    CDOBranch branch = branchManager.getBranch(Integer.parseInt(branchSegment));

    if (!tokenizer.hasMoreTokens())
    {
      throw new IllegalArgumentException("No version segment");
    }

    String versionSegment = tokenizer.nextToken();
    int version = Integer.parseInt(versionSegment);

    return id;
  }

  /**
   * @since 2.0
   */
  public static FeatureMap.Entry createFeatureMapEntry(EStructuralFeature feature, Object value)
  {
    return new CDOFeatureMapEntryImpl(feature, value);
  }

  /**
   * @since 3.0
   */
  public static CDOFeatureMapEntry createCDOFeatureMapEntry()
  {
    return new CDOFeatureMapEntryImpl();
  }

  /**
   * @since 4.0
   */
  public static CDORevisionDelta createDelta(CDORevision revision)
  {
    return new CDORevisionDeltaImpl(revision);
  }

  /**
   * @since 4.0
   */
  public static CDOChangeSetData createChangeSetData(Set<Long> ids, final CDOBranchPoint startPoint,
      final CDOBranchPoint endPoint, final CDORevisionManager revisionManager)
  {
    CDORevisionProvider startProvider = new ManagedRevisionProvider(revisionManager, startPoint);
    CDORevisionProvider endProvider = new ManagedRevisionProvider(revisionManager, endPoint);
    return createChangeSetData(ids, startProvider, endProvider);
  }

  /**
   * @since 4.0
   */
  public static CDOChangeSetData createChangeSetData(Set<Long> ids, CDORevisionProvider startProvider,
      CDORevisionProvider endProvider)
  {
    List<CDORevision> newObjects = new ArrayList<CDORevision>();
    List<CDORevisionDelta> changedObjects = new ArrayList<CDORevisionDelta>();
    List<Long> detachedObjects = new ArrayList<Long>();
    for (Long id : ids)
    {
      CDORevision startRevision = startProvider.getRevision(id);
      CDORevision endRevision = endProvider.getRevision(id);

      if (startRevision == null && endRevision != null)
      {
        newObjects.add(endRevision);
      }
      else if (endRevision == null && startRevision != null)
      {
        detachedObjects.add(id);
      }
      else if (startRevision != null && endRevision != null)
      {
        if (!startRevision.equals(endRevision))
        {
          CDORevisionDelta delta = endRevision.compare(startRevision);
          if (!delta.isEmpty())
          {
            changedObjects.add(delta);
          }
        }
      }
    }

    return createChangeSetData(newObjects, changedObjects, detachedObjects);
  }

  /**
   * @since 4.0
   */
  public static CDOChangeSetData createChangeSetData(List<CDORevision> newObjects,
      List<CDORevisionDelta> changedObjects, List<Long> detachedObjects)
  {
    return new CDOChangeSetDataImpl(newObjects, changedObjects, detachedObjects);
  }

  /**
   * @since 4.0
   */
  public static CDOChangeSet createChangeSet(CDOChangeSetData data)
  {
    return new CDOChangeSetImpl(data);
  }


  /**
   * @since 4.0
   */
  public static String getResourceNodePath(CDORevision revision, CDORevisionProvider provider)
  {
    EAttribute nameFeature = (EAttribute)revision.getEClass().getEStructuralFeature("name");

    StringBuilder builder = new StringBuilder();
    getResourceNodePath((InternalCDORevision)revision, provider, nameFeature, builder);

    builder.insert(0, "/");
    return builder.toString();
  }

  private static void getResourceNodePath(InternalCDORevision revision, CDORevisionProvider provider,
      EAttribute nameFeature, StringBuilder result)
  {
    String name = (String)revision.get(nameFeature, 0);
    if (name != null)
    {
      if (result.length() != 0)
      {
        result.insert(0, "/");
      }

      result.insert(0, name);
    }

    long folder = revision.getContainerID();
    if (!CDOIDUtil.isNull(folder))
    {
      InternalCDORevision container = (InternalCDORevision)provider.getRevision(folder);
      getResourceNodePath(container, provider, nameFeature, result);
    }
  }



  /**
   * @since 4.0
   */
  public static abstract class AllRevisionsDumper
  {
    private Map<CDOBranch, List<CDORevision>> map;

    public AllRevisionsDumper(Map<CDOBranch, List<CDORevision>> map)
    {
      this.map = map;
    }

    public Map<CDOBranch, List<CDORevision>> getMap()
    {
      return map;
    }

    public void dump()
    {
      ArrayList<CDOBranch> branches = new ArrayList<CDOBranch>(map.keySet());
      Collections.sort(branches);

      dumpStart(branches);
      for (CDOBranch branch : branches)
      {
        dumpBranch(branch);

        List<CDORevision> revisions = map.get(branch);
        Collections.sort(revisions, new CDORevisionComparator());

        for (CDORevision revision : revisions)
        {
          dumpRevision(revision);
        }
      }

      dumpEnd(branches);
    }

    protected void dumpStart(List<CDOBranch> branches)
    {
    }

    protected void dumpEnd(List<CDOBranch> branches)
    {
    }

    protected abstract void dumpBranch(CDOBranch branch);

    protected abstract void dumpRevision(CDORevision revision);

    /**
     * @author Eike Stepper
     */
    public static abstract class Stream extends AllRevisionsDumper
    {
      private PrintStream out;

      public Stream(Map<CDOBranch, List<CDORevision>> map, PrintStream out)
      {
        super(map);
        this.out = out;
      }

      public PrintStream out()
      {
        return out;
      }

    }
  }

  /**
   * @author Eike Stepper
   * @since 4.0
   */
  public static class CDORevisionComparator implements Comparator<CDORevisionKey>
  {
    public CDORevisionComparator()
    {
    }

    public int compare(CDORevisionKey rev1, CDORevisionKey rev2)
    {
      return rev1.getID() < rev2.getID() ? -1 : rev1.getID() == rev2.getID() ? 0 : 1;
    }
  }

  /**
   * @author Eike Stepper
   */
  private static final class Uninitialized
  {
    public Uninitialized()
    {
    }

    @Override
    public String toString()
    {
      return Messages.getString("CDORevisionUtil.0"); //$NON-NLS-1$
    }
  }
}
