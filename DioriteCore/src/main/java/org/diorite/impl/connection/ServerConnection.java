package org.diorite.impl.connection;

import java.net.InetAddress;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import com.google.common.collect.Lists;
import com.google.common.util.concurrent.ThreadFactoryBuilder;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.ServerImpl;
import org.diorite.impl.utils.LazyInitVar;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.DefaultEventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.AttributeKey;

public class ServerConnection
{
    public final LazyInitVar<NioEventLoopGroup>     lazyInitNioEventLoopGroup     = new LazyInitVar<NioEventLoopGroup>()
    {
        @Override
        protected NioEventLoopGroup init()
        {
            return new NioEventLoopGroup(0, new ThreadFactoryBuilder().setNameFormat("Netty Client IO #%d").setDaemon(true).build());
        }
    };
    public final LazyInitVar<DefaultEventLoopGroup> lazyInitDefaultEventLoopGroup = new LazyInitVar<DefaultEventLoopGroup>()
    {
        @Override
        protected DefaultEventLoopGroup init()
        {
            return new DefaultEventLoopGroup(0, new ThreadFactoryBuilder().setNameFormat("Netty Local Client IO #%d").setDaemon(true).build());
        }
    };

    public final  AttributeKey<EnumProtocol> protocolKey = AttributeKey.valueOf("protocol");
    private final List<NetworkManager>       connections = Collections.synchronizedList(Lists.newArrayList());
    private final ServerImpl    server;
    private       ChannelFuture channelFuture;

    public ServerConnection(final ServerImpl server)
    {
        this.server = server;
    }

    public ServerImpl getServer()
    {
        return this.server;
    }

    public void init(final InetAddress address, final int port)
    {
        this.channelFuture = new ServerBootstrap().channel(NioServerSocketChannel.class).childHandler(new ServerConnectionChannel(this)).group(this.lazyInitNioEventLoopGroup.get()).localAddress(address, port).bind().syncUninterruptibly();
    }

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

    public void update()
    {
        synchronized (this.connections)
        {
            final Iterator<NetworkManager> iterator = this.connections.iterator();
            while (iterator.hasNext())
            {
                final NetworkManager networkManager = iterator.next();
                if (! networkManager.hasNoChannel())
                {
                    if (! networkManager.isChannelOpen())
                    {
                        if (! networkManager.isPreparing())
                        {
                            iterator.remove();
                            networkManager.checkConnection();
                        }
                    }
                    else
                    {
                        try
                        {
                            networkManager.update();
                        } catch (final Exception exception)
                        {
                            exception.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    public ChannelFuture getChannelFuture()
    {
        return this.channelFuture;
    }

    public List<NetworkManager> getConnections()
    {
        return this.connections;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("protocolKey", this.protocolKey).append("connections", this.connections).append("channelFuture", this.channelFuture).append("server", this.server).toString();
    }
}
