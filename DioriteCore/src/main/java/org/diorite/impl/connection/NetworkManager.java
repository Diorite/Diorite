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

import org.diorite.impl.ServerImpl;
import org.diorite.impl.connection.packets.Packet;
import org.diorite.impl.connection.packets.PacketCompressor;
import org.diorite.impl.connection.packets.PacketDecompressor;
import org.diorite.impl.connection.packets.PacketDecrypter;
import org.diorite.impl.connection.packets.PacketEncrypter;
import org.diorite.impl.connection.packets.PacketListener;
import org.diorite.impl.connection.packets.QueuedPacket;
import org.diorite.impl.connection.packets.play.out.PacketPlayOutDisconnect;
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

public class NetworkManager extends SimpleChannelInboundHandler<Packet<? super PacketListener>>
{
    private final ServerImpl server;
    private final Queue<QueuedPacket> packetQueue = Queues.newConcurrentLinkedQueue();
    private final int            playerTimeout;
    private       Channel        channel;
    private       SocketAddress  address;
    private       PacketListener packetListener;
    private       BaseComponent  disconnectMessage;
    private boolean preparing = true;

    public NetworkManager(final ServerImpl server)
    {
        this.server = server;
        this.playerTimeout = (int) TimeUnit.SECONDS.toMillis(this.server.getPlayerTimeout());
    }

    private long lastKeepAlive = System.currentTimeMillis();

    public void updateKeepAlive()
    {
        this.lastKeepAlive = System.currentTimeMillis();
    }

    public void checkAlive()
    {
        if ((System.currentTimeMillis() - this.lastKeepAlive) > this.playerTimeout)
        {
            final TextComponent msg = new TextComponent("§4Timeout");
            this.sendPacket(new PacketPlayOutDisconnect(msg));
            this.close(msg);
        }
    }

    public void setProtocol(final EnumProtocol enumprotocol)
    {
        this.channel.attr(this.server.getServerConnection().protocolKey).set(enumprotocol);
        this.channel.config().setAutoRead(true);
    }

    public boolean isPreparing()
    {
        return this.preparing;
    }

    public void update()
    {
        this.nextPacket();
        this.channel.flush();
    }

    public void sendPacket(final Packet<?> packet)
    {
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
        final EnumProtocol ep1 = EnumProtocol.getByPacketClass(packet);
        final EnumProtocol ep2 = this.channel.attr(this.server.getServerConnection().protocolKey).get();
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
        this.channel.pipeline().addBefore("splitter", "decrypt", new PacketDecrypter(MinecraftEncryption.getCipher(2, secretkey)));
        this.channel.pipeline().addBefore("prepender", "encrypt", new PacketEncrypter(MinecraftEncryption.getCipher(1, secretkey)));
    }

    @Override
    protected void messageReceived(final ChannelHandlerContext channelHandlerContext, final Packet<? super PacketListener> packet) throws Exception
    {
        if (this.channel.isOpen())
        {
            packet.handle(this.packetListener);
        }
    }

    @Override
    public void exceptionCaught(final ChannelHandlerContext channelHandlerContext, final Throwable throwable)
    {
        this.close(new TranslatableComponent("disconnect.genericReason", "Internal Exception: " + throwable));
        throwable.printStackTrace();
    }

    @Override
    public void channelActive(final ChannelHandlerContext channelHandlerContext)
            throws Exception
    {
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
        this.close(new TranslatableComponent("disconnect.endOfStream"));
    }

    public BaseComponent getDisconnectMessage()
    {
        return this.disconnectMessage;
    }

    public void setDisconnectMessage(final BaseComponent disconnectMessage)
    {
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
        if (! this.isChannelOpen())
        {
            if (this.disconnectMessage != null)
            {
                this.packetListener.disconnect(this.disconnectMessage);
            }
            else if (this.packetListener != null)
            {
                this.packetListener.disconnect(new TextComponent("Disconnected"));
            }
        }
    }

    public void setCompression(final int i)
    {
        if (i >= 0)
        {
            if ((this.channel.pipeline().get("decompress") instanceof PacketDecompressor))
            {
                ((PacketDecompressor) this.channel.pipeline().get("decompress")).a(i);
            }
            else
            {
                this.channel.pipeline().addBefore("decoder", "decompress", new PacketDecompressor(i));
            }
            if ((this.channel.pipeline().get("compress") instanceof PacketCompressor))
            {
                ((PacketCompressor) this.channel.pipeline().get("decompress")).a(i);
            }
            else
            {
                this.channel.pipeline().addBefore("encoder", "compress", new PacketCompressor(i));
            }
        }
        else
        {
            if ((this.channel.pipeline().get("decompress") instanceof PacketDecompressor))
            {
                this.channel.pipeline().remove("decompress");
            }
            if ((this.channel.pipeline().get("compress") instanceof PacketCompressor))
            {
                this.channel.pipeline().remove("compress");
            }
        }
    }

    public void enableAutoRead()
    {
        this.channel.config().setAutoRead(false);
    }

    public void close(final BaseComponent baseComponent)
    {
        this.preparing = false;
        this.packetQueue.clear();
        if (this.channel.isOpen())
        {
            this.channel.close();
            this.disconnectMessage = baseComponent;
        }
    }

    public PacketListener getPacketListener()
    {
        return this.packetListener;
    }

    public void setPacketListener(final PacketListener packetListener)
    {
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
