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
package org.eclipse.net4j.util.tests;

import org.eclipse.net4j.util.concurrent.ISynchronizer;
import org.eclipse.net4j.util.concurrent.SynchronizingCorrelator;

/**
 * @author Eike Stepper
 */
public class SynchronizingCorrelatorTest extends AbstractOMTest
{
  public void testPutConsumerFirst() throws Exception
  {
    final Boolean[] result = { false };
    final SynchronizingCorrelator<String, Boolean> correlator = new SynchronizingCorrelator<String, Boolean>();
    final Thread consumer = new Thread()
    {
      @Override
      public void run()
      {
        ISynchronizer<Boolean> eike = correlator.correlate("eike"); //$NON-NLS-1$
        result[0] = eike.get(5000);
        msg("RESULT: " + result[0]); //$NON-NLS-1$
      }
    };

    consumer.start();
    sleep(100);

    correlator.put("eike", true, DEFAULT_TIMEOUT); //$NON-NLS-1$
    consumer.join(DEFAULT_TIMEOUT);
    assertEquals(Boolean.TRUE, result[0]);
  }

  public void testPutConsumerFirst10() throws Exception
  {
    for (int i = 0; i < 10; i++)
    {
      testPutConsumerFirst();
    }
  }

  public void testBlockingPutConsumerFirst() throws Exception
  {
    final Boolean[] result = { false };
    final SynchronizingCorrelator<String, Boolean> correlator = new SynchronizingCorrelator<String, Boolean>();
    final Thread consumer = new Thread()
    {
      @Override
      public void run()
      {
        ISynchronizer<Boolean> eike = correlator.correlate("eike"); //$NON-NLS-1$
        result[0] = eike.get(5000);
        msg("RESULT: " + result[0]); //$NON-NLS-1$
      }
    };

    consumer.start();
    Thread.sleep(10);

    boolean consumed = correlator.put("eike", true, 1000); //$NON-NLS-1$
    msg("Consumed: " + consumed); //$NON-NLS-1$
    assertEquals(true, consumed);

    consumer.join(1000);
    assertEquals(Boolean.TRUE, result[0]);
  }

  public void testBlockingPutConsumerFirst10() throws Exception
  {
    for (int i = 0; i < 10; i++)
    {
      testBlockingPutConsumerFirst();
    }
  }

  public void _testPutProducerFirst() throws Exception
  {
    final Boolean[] result = { false };
    final SynchronizingCorrelator<String, Boolean> correlator = new SynchronizingCorrelator<String, Boolean>();
    correlator.put("eike", true, DEFAULT_TIMEOUT); //$NON-NLS-1$

    final Thread consumer = new Thread()
    {
      @Override
      public void run()
      {
        ISynchronizer<Boolean> eike = correlator.correlate("eike"); //$NON-NLS-1$
        result[0] = eike.get(5000);
        msg("RESULT: " + result[0]); //$NON-NLS-1$
      }
    };

    consumer.start();
    Thread.sleep(10);

    consumer.join(100);
    assertEquals(Boolean.TRUE, result[0]);
  }

  public void _testPutProducerFirst10() throws Exception
  {
    for (int i = 0; i < 10; i++)
    {
      _testPutProducerFirst();
    }
  }

  public void testBlockingPutProducerFirst() throws Exception
  {
    final Boolean[] result = { false };
    final SynchronizingCorrelator<String, Boolean> correlator = new SynchronizingCorrelator<String, Boolean>();
    boolean consumed = correlator.put("eike", true, 50); //$NON-NLS-1$
    msg("Consumed: " + consumed); //$NON-NLS-1$
    assertEquals(false, consumed);

    final Thread consumer = new Thread()
    {
      @Override
      public void run()
      {
        ISynchronizer<Boolean> eike = correlator.correlate("eike"); //$NON-NLS-1$
        result[0] = eike.get(5000);
        msg("RESULT: " + result[0]); //$NON-NLS-1$
      }
    };

    consumer.start();
    Thread.sleep(10);

    consumer.join(1000);
    assertEquals(Boolean.TRUE, result[0]);
  }

  public void testBlockingPutProducerFirst10() throws Exception
  {
    for (int i = 0; i < 10; i++)
    {
      testBlockingPutProducerFirst();
    }
  }
}
