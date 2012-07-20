/*
 * Copyright (c) 2004 - 2012 Eike Stepper (Berlin, Germany) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Eike Stepper - initial API and implementation
 *    Caspar De Groot - maintenance
 */
package org.eclipse.net4j.internal.tcp;

import org.eclipse.net4j.buffer.IBuffer;
import org.eclipse.net4j.channel.ChannelException;
import org.eclipse.net4j.channel.IChannel;
import org.eclipse.net4j.connector.ConnectorException;
import org.eclipse.net4j.connector.ConnectorState;
import org.eclipse.net4j.internal.tcp.bundle.OM;
import org.eclipse.net4j.internal.tcp.messages.Messages;
import org.eclipse.net4j.protocol.IProtocol;
import org.eclipse.net4j.tcp.ITCPActiveSelectorListener;
import org.eclipse.net4j.tcp.ITCPConnector;
import org.eclipse.net4j.tcp.ITCPNegotiationContext;
import org.eclipse.net4j.tcp.ITCPSelector;
import org.eclipse.net4j.util.ReflectUtil.ExcludeFromDump;
import org.eclipse.net4j.util.WrappedException;
import org.eclipse.net4j.util.collection.RoundRobinBlockingQueue;
import org.eclipse.net4j.util.io.IOUtil;
import org.eclipse.net4j.util.lifecycle.LifecycleUtil;
import org.eclipse.net4j.util.om.trace.ContextTracer;
import org.eclipse.net4j.util.security.INegotiationContext;
import org.eclipse.net4j.util.security.NegotiationContext;
import org.eclipse.net4j.util.security.NegotiationException;

import org.eclipse.spi.net4j.Connector;
import org.eclipse.spi.net4j.InternalChannel;

import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;

/**
 * @author Eike Stepper
 */
public abstract class TCPConnector extends Connector implements ITCPConnector, ITCPActiveSelectorListener
{
  private static final ContextTracer TRACER = new ContextTracer(OM.DEBUG, TCPConnector.class);

  private SocketChannel socketChannel;

  private ITCPSelector selector;

  @ExcludeFromDump
  private SelectionKey selectionKey;

  private BlockingQueue<InternalChannel> writeQueue = new RoundRobinBlockingQueue<InternalChannel>();

  private IBuffer inputBuffer;

  private ControlChannel controlChannel;

  private String host;

  private int port;

  public TCPConnector()
  {
  }

  public String getHost()
  {
    return host;
  }

  public void setHost(String host)
  {
    this.host = host;
  }

  public int getPort()
  {
    return port;
  }

  public void setPort(int port)
  {
    this.port = port;
  }

  public ITCPSelector getSelector()
  {
    return selector;
  }

  public void setSelector(ITCPSelector selector)
  {
    this.selector = selector;
  }

  public SocketChannel getSocketChannel()
  {
    return socketChannel;
  }

  /**
   * SocketChannel must already be non-blocking!
   */
  public void setSocketChannel(SocketChannel socketChannel)
  {
    this.socketChannel = socketChannel;
  }

  public SelectionKey getSelectionKey()
  {
    return selectionKey;
  }

  public void setSelectionKey(SelectionKey selectionKey)
  {
    this.selectionKey = selectionKey;
  }

  public BlockingQueue<InternalChannel> getWriteQueue()
  {
    return writeQueue;
  }

  public void setWriteQueue(BlockingQueue<InternalChannel> writeQueue)
  {
    this.writeQueue = writeQueue;
  }

  public IBuffer getInputBuffer()
  {
    return inputBuffer;
  }

  public void setInputBuffer(IBuffer inputBuffer)
  {
    this.inputBuffer = inputBuffer;
  }

  public ControlChannel getControlChannel()
  {
    return controlChannel;
  }

  public void setControlChannel(ControlChannel controlChannel)
  {
    this.controlChannel = controlChannel;
  }

  @Override
  public String getURL()
  {
    StringBuilder builder = new StringBuilder();
    builder.append(getProtocolString());
    builder.append(host);
    if (port != DEFAULT_PORT)
    {
      builder.append(":");
      builder.append(port);
    }

    return builder.toString();
  }

  public String getProtocolString()
  {
    return "tcp://";
  }

  public void handleRegistration(ITCPSelector selector, SocketChannel socketChannel)
  {
    try
    {
      int interest = isClient() ? SelectionKey.OP_CONNECT : SelectionKey.OP_READ;
      selectionKey = socketChannel.register(selector.getSocketSelector(), interest, this);
      if (isServer())
      {
        leaveConnecting();
      }
    }
    catch (Exception ex)
    {
      deferredActivate(false);
    }
  }

  public void handleConnect(ITCPSelector selector, SocketChannel channel)
  {
    try
    {
      if (channel.finishConnect())
      {
        selector.orderConnectInterest(selectionKey, true, false);
        selector.orderReadInterest(selectionKey, true, true);
        leaveConnecting();
      }
    }
    catch (Exception ex)
    {
      deferredActivate(false);
    }
  }

  public void handleRead(ITCPSelector selector, SocketChannel socketChannel)
  {
    try
    {
      if (inputBuffer == null)
      {
        inputBuffer = getConfig().getBufferProvider().provideBuffer();
      }

      ByteBuffer byteBuffer = inputBuffer.startGetting(socketChannel);
      if (byteBuffer != null)
      {
        short channelID = inputBuffer.getChannelID();
        InternalChannel channel = channelID == ControlChannel.CONTROL_CHANNEL_INDEX ? controlChannel
            : getChannel(channelID);
        if (channel != null)
        {
          channel.handleBufferFromMultiplexer(inputBuffer);
        }
        else
        {
          if (TRACER.isEnabled())
          {
            TRACER.trace("Discarding buffer from unknown channel"); //$NON-NLS-1$
          }

          inputBuffer.release();
        }

        inputBuffer = null;
      }
    }
    catch (NegotiationException ex)
    {
      OM.LOG.error(ex);
      setNegotiationException(ex);
      deactivateAsync();
    }
    catch (ClosedChannelException ex)
    {
      if (TRACER.isEnabled())
      {
        TRACER.trace("Socket channel closed: " + socketChannel); //$NON-NLS-1$
      }

      deactivateAsync();
    }
    catch (Exception ex)
    {
      if (isActive())
      {
        OM.LOG.error(ex);
        deactivateAsync();
      }
    }
  }

  /**
   * Called by an {@link IChannel} each time a new buffer is available for multiplexing. This or another buffer can be
   * dequeued from the outputQueue of the {@link IChannel}.
   */
  public void multiplexChannel(InternalChannel channel)
  {
    synchronized (writeQueue)
    {
      boolean firstChannel = writeQueue.isEmpty();

      try
      {
        writeQueue.put(channel);
      }
      catch (InterruptedException ex)
      {
        throw WrappedException.wrap(ex);
      }

      if (firstChannel)
      {
        if (selectionKey != null)
        {
          doOrderWriteInterest(true);
        }
      }
    }
  }

  public void handleWrite(ITCPSelector selector, SocketChannel socketChannel)
  {
    try
    {
      synchronized (writeQueue)
      {
        InternalChannel channel = writeQueue.peek();
        if (channel != null)
        {
          Queue<IBuffer> bufferQueue = channel.getSendQueue();
          if (bufferQueue != null)
          {
            IBuffer buffer = bufferQueue.peek();
            if (buffer != null)
            {
              if (buffer.write(socketChannel))
              {
                writeQueue.poll();
                bufferQueue.poll();
                buffer.release();
              }
            }
          }
        }

        if (writeQueue.isEmpty())
        {
          if (selectionKey != null)
          {
            doOrderWriteInterest(false);
          }
        }
      }
    }
    catch (NullPointerException ignore)
    {
    }
    catch (ClosedChannelException ex)
    {
      if (TRACER.isEnabled())
      {
        TRACER.trace("Socket channel closed: " + socketChannel); //$NON-NLS-1$
      }

      deactivateAsync();
    }
    catch (Exception ex)
    {
      if (isActive())
      {
        OM.LOG.error(ex);
        deactivateAsync();
      }
    }
  }

  protected void doOrderWriteInterest(boolean on)
  {
    selector.orderWriteInterest(selectionKey, isClient(), on);
  }

  @Override
  protected void registerChannelWithPeer(short channelID, long timeout, IProtocol<?> protocol) throws ChannelException
  {
    try
    {
      if (!controlChannel.registerChannel(channelID, timeout, protocol))
      {
        throw new ChannelException("Failed to register channel with peer"); //$NON-NLS-1$
      }
    }
    catch (RuntimeException ex)
    {
      throw ex;
    }
    catch (Exception ex)
    {
      throw new ConnectorException(ex);
    }
  }

  @Override
  protected void deregisterChannelFromPeer(InternalChannel channel) throws ChannelException
  {
    if (channel != null && channel.getClass() != ControlChannel.class)
    {
      if (controlChannel != null && isConnected())
      {
        controlChannel.deregisterChannel(channel.getID());
      }
    }
  }

  @Override
  protected INegotiationContext createNegotiationContext()
  {
    return new TCPNegotiationContext();
  }

  @Override
  protected void doBeforeActivate() throws Exception
  {
    super.doBeforeActivate();
    if (socketChannel == null)
    {
      throw new IllegalStateException("socketChannel == null"); //$NON-NLS-1$
    }

    if (selector == null)
    {
      throw new IllegalStateException("selector == null"); //$NON-NLS-1$
    }
  }

  @Override
  protected void doActivate() throws Exception
  {
    super.doActivate();
    controlChannel = new ControlChannel(this);
    controlChannel.activate();
    selector.orderRegistration(socketChannel, isClient(), this);
  }

  @Override
  protected void doDeactivate() throws Exception
  {
    cancelSelectionKey();

    LifecycleUtil.deactivate(controlChannel);
    controlChannel = null;

    IOUtil.closeSilent(socketChannel);
    socketChannel = null;
    super.doDeactivate();
  }

  protected void deactivateAsync()
  {
    // Cancel the selection immediately
    cancelSelectionKey();

    // Do the rest of the deactivation asynchronously
    getConfig().getReceiveExecutor().execute(new Runnable()
    {
      public void run()
      {
        deactivate();
      }
    });
  }

  private void cancelSelectionKey()
  {
    if (selectionKey != null)
    {
      selectionKey.cancel();
      selectionKey = null;
    }
  }

  /**
   * @author Eike Stepper
   */
  private final class TCPNegotiationContext extends NegotiationContext implements ITCPNegotiationContext
  {
    private IBuffer buffer;

    private boolean failed;

    public TCPNegotiationContext()
    {
    }

    public TCPConnector getConnector()
    {
      return TCPConnector.this;
    }

    public void setUserID(String userID)
    {
      TCPConnector.this.setUserID(userID);
    }

    public ByteBuffer getBuffer()
    {
      buffer = getConfig().getBufferProvider().provideBuffer();
      ByteBuffer byteBuffer = buffer.startPutting(ControlChannel.CONTROL_CHANNEL_INDEX);
      byteBuffer.put(ControlChannel.OPCODE_NEGOTIATION);
      return byteBuffer;
    }

    public void transmitBuffer(ByteBuffer byteBuffer)
    {
      if (buffer.getByteBuffer() != byteBuffer)
      {
        throw new IllegalArgumentException("The passed buffer is not the last that was produced"); //$NON-NLS-1$
      }

      controlChannel.sendBuffer(buffer);
      if (failed)
      {
        deactivate();
      }
    }

    @Override
    public void setFinished(boolean success)
    {
      if (success)
      {
        TCPConnector.this.setState(ConnectorState.CONNECTED);
      }
      else
      {
        OM.LOG.error(Messages.getString("TCPConnector.6") + TCPConnector.this); //$NON-NLS-1$
        failed = true;
      }

      super.setFinished(success);
    }
  }
}
