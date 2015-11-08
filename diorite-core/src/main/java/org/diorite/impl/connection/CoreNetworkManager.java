/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 Diorite (by Bartłomiej Mazur (aka GotoFinal))
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package org.diorite.impl.connection;

import javax.crypto.SecretKey;

import java.net.SocketAddress;
import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.TimeUnit;

import com.google.common.collect.Queues;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.DioriteCore;
import org.diorite.impl.connection.listeners.PacketPlayListener;
import org.diorite.impl.connection.packets.Packet;
import org.diorite.impl.connection.packets.PacketCompression;
import org.diorite.impl.connection.packets.PacketEncryptor;
import org.diorite.impl.connection.packets.PacketListener;
import org.diorite.impl.connection.packets.QueuedPacket;
import org.diorite.impl.connection.packets.play.client.PacketPlayClientKeepAlive;
import org.diorite.impl.connection.packets.play.server.PacketPlayServerKeepAlive;
import org.diorite.chat.component.BaseComponent;
import org.diorite.chat.component.TextComponent;
import org.diorite.chat.component.TranslatableComponent;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

public abstract class CoreNetworkManager extends SimpleChannelInboundHandler<Packet<? super PacketListener>>
{
    protected final DioriteCore core;
    protected final Queue<QueuedPacket> packetQueue = Queues.newConcurrentLinkedQueue();
    protected final int            playerTimeout;
    protected       Channel        channel;
    protected       SocketAddress  address;
    protected       PacketListener packetListener;
    protected       BaseComponent  disconnectMessage;
    protected boolean preparing = true;

    public CoreNetworkManager(final DioriteCore core)
    {
        this.core = core;
        this.playerTimeout = (int) TimeUnit.SECONDS.toMillis(this.core.getPlayerTimeout());
    }

    private long lastKeepAlive = System.currentTimeMillis();
    private long sentAlive     = System.currentTimeMillis();
    private int ping;

    public int getPing()
    {
        return this.ping;
    }

    public void setPing(final int ping)
    {
        this.ping = ping;
    }

    public void updateKeepAlive()
    {
        this.setPing((int) (System.currentTimeMillis() - this.sentAlive));
        this.lastKeepAlive = System.currentTimeMillis();
    }

    public abstract void handleClosed();

    public void checkAlive()
    {
        if (this.closed)
        {
            this.handleClosed();
            return;
        }
        if ((System.currentTimeMillis() - this.lastKeepAlive) > this.playerTimeout)
        {
            this.packetListener.disconnect(TextComponent.fromLegacyText("§4Timeout"));
        }
    }

    public void setProtocol(final EnumProtocol enumprotocol)
    {
        if (this.closed)
        {
            this.handleClosed();
            return;
        }
        this.channel.attr(this.core.getConnectionHandler().getProtocolKey()).set(enumprotocol);
        this.channel.config().setAutoRead(true);
    }

    public boolean isPreparing()
    {
        return this.preparing;
    }

    public void update()
    {
        if (this.closed)
        {
            this.handleClosed();
            return;
        }
        this.nextPacket();
        this.channel.flush();
    }

    public void sendPackets(final Packet<?>[] packets)
    {
        if (this.closed)
        {
            this.handleClosed();
            return;
        }
        if (this.isChannelOpen())
        {
            this.nextPacket();
            for (final Packet<?> packet : packets)
            {
                this.sendPacket(packet, null);
            }
        }
        else
        {
            for (final Packet<?> packet : packets)
            {
                this.packetQueue.add(new QueuedPacket(packet, null));
            }
        }
    }

    public void sendPacket(final Packet<?> packet)
    {
        if (this.closed)
        {
            this.handleClosed();
            return;
        }
        if (this.isChannelOpen())
        {
            this.nextPacket();
            this.sendPacket(packet, null);
        }
        else
        {
            this.packetQueue.add(new QueuedPacket(packet, null));
        }
    }

    @SafeVarargs
    public final void sendPacket(final Packet<?> packet, final GenericFutureListener<? extends Future<? super Void>> listener, final GenericFutureListener<? extends Future<? super Void>>... listeners)
    {
        if (this.closed)
        {
            this.handleClosed();
            return;
        }
        if (this.isChannelOpen())
        {
            this.nextPacket();
            this.sendPacket(packet, ArrayUtils.add(listeners, 0, listener));
        }
        else
        {
            this.packetQueue.add(new QueuedPacket(packet, ArrayUtils.add(listeners, 0, listener)));
        }
    }

    private void sendPacket(final Packet<?> packet, final GenericFutureListener<? extends Future<? super Void>>[] listeners)
    {
        if (this.closed)
        {
            this.handleClosed();
            return;
        }
        if ((packet instanceof PacketPlayServerKeepAlive) || (packet instanceof PacketPlayClientKeepAlive))
        {
            this.sentAlive = System.currentTimeMillis();
        }
        final EnumProtocol ep1 = EnumProtocol.getByPacketClass(packet);
        final EnumProtocol ep2 = this.channel.attr(this.core.getConnectionHandler().getProtocolKey()).get();
        if (ep2 != ep1)
        {
            this.channel.config().setAutoRead(false);
        }
        if (this.channel.eventLoop().inEventLoop())
        {
            if (ep1 != ep2)
            {
                this.setProtocol(ep1);
            }
            final ChannelFuture channelfuture = this.channel.writeAndFlush(packet);
            if (listeners != null)
            {
                channelfuture.addListeners(listeners);
            }
            channelfuture.addListener(ChannelFutureListener.FIRE_EXCEPTION_ON_FAILURE);
        }
        else
        {
            this.channel.eventLoop().execute(() -> {
                if (ep1 != ep2)
                {
                    this.setProtocol(ep1);
                }
                final ChannelFuture channelFuture = this.channel.writeAndFlush(packet);
                if (listeners != null)
                {
                    channelFuture.addListeners(listeners);
                }
                channelFuture.addListener(ChannelFutureListener.FIRE_EXCEPTION_ON_FAILURE);
            });
        }
    }

    private void nextPacket()
    {
        if (this.closed)
        {
            this.handleClosed();
            return;
        }
        if (this.isChannelOpen())
        {
            while (! this.packetQueue.isEmpty())
            {
                final QueuedPacket queuedpacket = this.packetQueue.poll();
                this.sendPacket(queuedpacket.getPacket(), queuedpacket.getListeners());
            }
        }
    }

    @SuppressWarnings("TypeMayBeWeakened")
    public void enableEncryption(final SecretKey secretkey)
    {
        if (this.closed)
        {
            this.handleClosed();
            return;
        }
        this.channel.pipeline().addBefore("sizer", "encryption", new PacketEncryptor(MinecraftEncryption.getCipher(1, secretkey), MinecraftEncryption.getCipher(2, secretkey)));
    }

    @Override
    protected void messageReceived(final ChannelHandlerContext channelHandlerContext, final Packet<? super PacketListener> packet) throws Exception
    {
        if (this.closed)
        {
            this.handleClosed();
            return;
        }
        if (this.channel.isOpen())
        {
            packet.handle(this.packetListener);
        }
    }

    @Override
    public void exceptionCaught(final ChannelHandlerContext channelHandlerContext, final Throwable throwable)
    {
        if (this.closed)
        {
            this.handleClosed();
            return;
        }
        if (this.channel.isOpen())
        {
            this.close(new TranslatableComponent("disconnect.genericReason", "Internal Exception: " + throwable));
            this.channel.close();
            this.handleClosed();
        }
        else
        {
            this.preparing = false;
            this.packetQueue.clear();
            this.disconnectMessage = new TranslatableComponent("disconnect.genericReason", "Internal Exception: " + throwable);
            this.handleClosed();
        }
        //throwable.printStackTrace();
    }

    @Override
    public void channelActive(final ChannelHandlerContext channelHandlerContext) throws Exception
    {
        if (this.closed)
        {
            this.handleClosed();
            return;
        }
        super.channelActive(channelHandlerContext);
        this.channel = channelHandlerContext.channel();
        this.address = this.channel.remoteAddress();

        this.preparing = false;
        try
        {
            this.setProtocol(EnumProtocol.HANDSHAKING);
        } catch (final Throwable throwable)
        {
            throwable.printStackTrace();
        }
    }

    @Override
    public void channelInactive(final ChannelHandlerContext channelHandlerContext)
    {
        if (this.closed)
        {
            this.handleClosed();
            return;
        }
        this.core.sync(() -> {
            this.close(new TranslatableComponent("disconnect.endOfStream"));
            this.handleClosed();
        });
    }

    public BaseComponent getDisconnectMessage()
    {
        return this.disconnectMessage;
    }

    public void setDisconnectMessage(final BaseComponent disconnectMessage)
    {
        if (this.closed)
        {
            this.handleClosed();
            return;
        }
        this.disconnectMessage = disconnectMessage;
    }

    public boolean hasNoChannel()
    {
        return this.channel == null;
    }

    public boolean isChannelOpen()
    {
        return (this.channel != null) && this.channel.isOpen();
    }

    public void checkConnection()
    {
        if (this.closed)
        {
            this.handleClosed();
            return;
        }
        if (! this.isChannelOpen())
        {
            if (this.disconnectMessage != null)
            {
                this.packetListener.disconnect(this.disconnectMessage);
            }
            else if (this.packetListener != null)
            {
                this.packetListener.disconnect(TextComponent.fromLegacyText("Disconnected"));
            }
        }
    }

    public void setCompression(final int i)
    {
        if (this.closed)
        {
            this.handleClosed();
            return;
        }
        if (i >= 0)
        {
            if ((this.channel.pipeline().get("compression") instanceof PacketCompression))
            {
                ((PacketCompression) this.channel.pipeline().get("compression")).setThreshold(i);
            }
            else
            {
                this.channel.pipeline().addBefore("codec", "compression", new PacketCompression(i));
            }
        }
        else
        {
            if ((this.channel.pipeline().get("compression") instanceof PacketCompression))
            {
                this.channel.pipeline().remove("compression");
            }
        }
    }

    public void enableAutoRead()
    {
        this.channel.config().setAutoRead(false);
    }

    private volatile boolean closed = false;

    public void close(final BaseComponent baseComponent, final boolean wasSafe)
    {
        if (this.closed)
        {
            return;
        }
        this.core.sync(() -> {
            if (this.closed)
            {
                return;
            }
            this.preparing = false;
            this.packetQueue.clear();
            if (! wasSafe)
            {
                if (this.packetListener instanceof PacketPlayListener)
                {
                    final PacketPlayListener listener = (PacketPlayListener) this.packetListener;
                    if (this.core.getPlayersManager().getRawPlayers().containsValue(listener.getPlayer()))
                    {
                        listener.disconnect(baseComponent);
                    }
                }
            }
            this.closed = true;
            if (this.channel.isOpen())
            {
                this.channel.close();
                this.disconnectMessage = baseComponent;
            }
            this.handleClosed();
        });
    }

    public void close(final BaseComponent baseComponent)
    {
        this.core.sync(() -> this.close(baseComponent, false));
    }

    public PacketListener getPacketListener()
    {
        return this.packetListener;
    }

    public void setPacketListener(final PacketListener packetListener)
    {
        if (this.closed)
        {
            this.handleClosed();
            return;
        }
        Objects.requireNonNull(packetListener, "PacketLisener can't be null!");
        this.packetListener = packetListener;
    }

    public SocketAddress getSocketAddress()
    {
        return this.address;
    }

    public void setAddress(final SocketAddress address)
    {
        this.address = address;
    }

    public Channel getChannel()
    {
        return this.channel;
    }

    public void setChannel(final Channel channel)
    {
        this.channel = channel;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("channel", this.channel).append("address", this.address).append("packetListener", this.packetListener).toString();
    }
}
