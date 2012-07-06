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
package org.eclipse.net4j.signal;

import org.eclipse.net4j.buffer.BufferInputStream;
import org.eclipse.net4j.buffer.BufferOutputStream;
import org.eclipse.net4j.util.io.ExtendedDataInputStream;
import org.eclipse.net4j.util.io.ExtendedDataOutputStream;
import org.eclipse.net4j.util.lifecycle.LifecycleUtil;
import org.eclipse.net4j.util.om.monitor.OMMonitor;
import org.eclipse.net4j.util.om.monitor.TimeoutMonitor;

import org.eclipse.internal.net4j.bundle.OM;

import java.util.TimerTask;
import java.util.concurrent.ExecutorService;

/**
 * @author Eike Stepper
 * @since 2.0
 */
public abstract class IndicationWithMonitoring extends IndicationWithResponse
{
  private ReportingMonitor monitor;

  /**
   * @since 2.0
   */
  public IndicationWithMonitoring(SignalProtocol<?> protocol, short id, String name)
  {
    super(protocol, id, name);
  }

  /**
   * @since 2.0
   */
  public IndicationWithMonitoring(SignalProtocol<?> protocol, short signalID)
  {
    super(protocol, signalID);
  }

  /**
   * @since 2.0
   */
  public IndicationWithMonitoring(SignalProtocol<?> protocol, Enum<?> literal)
  {
    super(protocol, literal);
  }

  @Override
  protected void execute(BufferInputStream in, BufferOutputStream out) throws Exception
  {
    try
    {
      super.execute(in, out);
    }
    finally
    {
      if (monitor != null)
      {
        monitor.done();
        monitor = null;
      }
    }
  }

  @Override
  protected final void indicating(ExtendedDataInputStream in) throws Exception
  {
    int monitorProgressSeconds = in.readInt();
    int monitorTimeoutSeconds = in.readInt();

    monitor = new ReportingMonitor(monitorProgressSeconds, monitorTimeoutSeconds);
    monitor.begin(OMMonitor.HUNDRED);

    indicating(in, monitor.fork(getIndicatingWorkPercent()));
  }

  @Override
  protected final void responding(ExtendedDataOutputStream out) throws Exception
  {
    responding(out, monitor.fork(OMMonitor.HUNDRED - getIndicatingWorkPercent()));
  }

  protected abstract void indicating(ExtendedDataInputStream in, OMMonitor monitor) throws Exception;

  protected abstract void responding(ExtendedDataOutputStream out, OMMonitor monitor) throws Exception;

  /**
   * @since 2.0
   */
  protected ExecutorService getMonitoringExecutorService()
  {
    return getProtocol().getExecutorService();
  }

  protected int getIndicatingWorkPercent()
  {
    return 99;
  }

  void setMonitorCanceled()
  {
    monitor.cancel();
  }

  /**
   * @author Eike Stepper
   */
  private final class ReportingMonitor extends TimeoutMonitor
  {
    private TimerTask sendProgressTask = new TimerTask()
    {
      @Override
      public void run()
      {
        try
        {
          sendProgress();
        }
        catch (Exception ex)
        {
          OM.LOG.error("ReportingMonitorTask failed", ex);
        }
      }
    };

    public ReportingMonitor(int monitorProgressSeconds, int monitorTimeoutSeconds)
    {
      super(monitorTimeoutSeconds * 1000L);
      long period = monitorProgressSeconds * 1000L;
      scheduleAtFixedRate(sendProgressTask, period, period);
    }

    @Override
    public void cancel(RuntimeException cancelException)
    {
      sendProgressTask.cancel();
      super.cancel(cancelException);
    }

    @Override
    public void done()
    {
      sendProgressTask.cancel();
      super.done();
    }

    private void sendProgress()
    {
      try
      {
        new MonitorProgressRequest(getProtocol(), -getCorrelationID(), getTotalWork(), getWork()).sendAsync();
      }
      catch (Exception ex)
      {
        if (LifecycleUtil.isActive(getProtocol().getChannel()))
        {
          OM.LOG.error(ex);
        }
      }
    }
  }
}
