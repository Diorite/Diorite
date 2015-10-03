package org.diorite.impl.client.connection;

import java.net.InetAddress;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.CoreMain;
import org.diorite.impl.DioriteCore;
import org.diorite.impl.connection.ConnectionHandler;
import org.diorite.impl.connection.EnumProtocol;
import org.diorite.utils.lazy.LazyValue;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.Epoll;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.AttributeKey;
import io.netty.util.concurrent.DefaultExecutorServiceFactory;

public class ClientConnection extends Thread implements ConnectionHandler
{
    public static final int MILLIS = 500;
    public final LazyValue<NioEventLoopGroup>   nioEventLoopGroupLazyValue;
    public final LazyValue<EpollEventLoopGroup> epollEventLoopGroupLazyValue;
    private final AttributeKey<EnumProtocol> protocolKey = AttributeKey.valueOf("protocol");
    private       NetworkManager connection;
    private final DioriteCore    core;
    private       ChannelFuture  channelFuture;

    @SuppressWarnings("resource")
    public ClientConnection(final DioriteCore core)
    {
        this.core = core;
        this.setDaemon(true);
        this.setName("{Diorite|SrvCon}");
        this.nioEventLoopGroupLazyValue = new LazyValue<>(() -> new NioEventLoopGroup(0, new DefaultExecutorServiceFactory("Netty Client")));
        this.epollEventLoopGroupLazyValue = new LazyValue<>(() -> new EpollEventLoopGroup(0, new DefaultExecutorServiceFactory("Netty Epoll Client")));
    }

    @Override
    public AttributeKey<EnumProtocol> getProtocolKey()
    {
        return this.protocolKey;
    }

    @Override
    public DioriteCore getCore()
    {
        return this.core;
    }

    @Override
    public void init(final InetAddress address, final int port, final boolean useEpoll)
    {
        final Class<? extends SocketChannel> socketChannelClass;
        final LazyValue<? extends EventLoopGroup> lazyInit;
        if ((Epoll.isAvailable()) && useEpoll)
        {
            socketChannelClass = EpollSocketChannel.class;
            lazyInit = this.epollEventLoopGroupLazyValue;
            CoreMain.debug("[Netty] Using epoll channel type");
        }
        else
        {
            socketChannelClass = NioSocketChannel.class;
            lazyInit = this.nioEventLoopGroupLazyValue;
            CoreMain.debug("[Netty] Using default channel type");
        }
        this.channelFuture = new Bootstrap().channel(socketChannelClass).handler(new ClientConnectionChannel(this)).group(lazyInit.get()).remoteAddress(address, port).connect().syncUninterruptibly();
    }

    public void setConnection(final NetworkManager connection)
    {
        this.connection = connection;
    }

    @Override
    public void close()
    {
        try
        {
            this.channelFuture.channel().close().sync();
        } catch (final InterruptedException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void run()
    {
    }

    public ChannelFuture getChannelFuture()
    {
        return this.channelFuture;
    }

    public NetworkManager getConnection()
    {
        return this.connection;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("protocolKey", this.protocolKey).append("connection", this.connection).append("channelFuture", this.channelFuture).append("server", this.core).toString();
    }
}
