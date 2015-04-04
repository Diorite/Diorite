package org.diorite.impl.connection;

import java.net.InetAddress;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

import com.google.common.collect.Sets;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.ServerImpl;
import org.diorite.utils.LazyInitVar;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.AttributeKey;
import io.netty.util.concurrent.DefaultExecutorServiceFactory;

public class ServerConnection extends Thread
{
    public static final int                            MILLIS                    = 500;
    public final        LazyInitVar<NioEventLoopGroup> lazyInitNioEventLoopGroup = new LazyInitVar<NioEventLoopGroup>()
    {
        @Override
        protected NioEventLoopGroup init()
        {
            return new NioEventLoopGroup(0, new DefaultExecutorServiceFactory("Netty Client"));
        }
    };

    public final  AttributeKey<EnumProtocol> protocolKey = AttributeKey.valueOf("protocol");
    private final Collection<NetworkManager> connections = Collections.synchronizedSet(Sets.newConcurrentHashSet());
    private final ServerImpl    server;
    private       ChannelFuture channelFuture;

    public ServerConnection(final ServerImpl server)
    {
        this.server = server;
        this.setDaemon(true);
        this.setName("{Diorite|SrvCon}");
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

    @Override
    public void run()
    {
        while (this.server.isRunning())
        {
            this.update();
            try
            {
                Thread.sleep(MILLIS);
            } catch (final InterruptedException e)
            {
                e.printStackTrace();
            }
        }
    }

    public void remove(final NetworkManager networkManager)
    {
        this.connections.remove(networkManager);
    }

    public void update()
    {
        final Iterator<NetworkManager> iterator = this.connections.iterator();
        while (iterator.hasNext())
        {
            final NetworkManager networkManager = iterator.next();
            networkManager.checkAlive();
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

    public ChannelFuture getChannelFuture()
    {
        return this.channelFuture;
    }

    public Collection<NetworkManager> getConnections()
    {
        return this.connections;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("protocolKey", this.protocolKey).append("connections", this.connections).append("channelFuture", this.channelFuture).append("server", this.server).toString();
    }
}
