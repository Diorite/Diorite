/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2016. Diorite (by Bartłomiej Mazur (aka GotoFinal))
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

package org.diorite.impl.server.connection;

import java.net.InetAddress;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

import com.google.common.collect.Sets;
import com.google.common.util.concurrent.ThreadFactoryBuilder;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.CoreMain;
import org.diorite.impl.DioriteCore;
import org.diorite.impl.connection.ConnectionHandler;
import org.diorite.impl.connection.CoreNetworkManager;
import org.diorite.impl.connection.EnumProtocol;
import org.diorite.utils.lazy.LazyValue;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.Epoll;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollServerSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.ServerSocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.AttributeKey;

public class ServerConnection extends Thread implements ConnectionHandler
{
    public static final int MILLIS = 500;
    public final LazyValue<NioEventLoopGroup>   nioEventLoopGroupLazyValue;
    public final LazyValue<EpollEventLoopGroup> epollEventLoopGroupLazyValue;

    private final AttributeKey<EnumProtocol> protocolKey = AttributeKey.valueOf("protocol");
    private final Collection<NetworkManager> connections = Collections.synchronizedSet(Sets.newConcurrentHashSet());
    private final DioriteCore   core;
    private       ChannelFuture channelFuture;

    @SuppressWarnings("resource")
    public ServerConnection(final DioriteCore core)
    {
        this.core = core;
        this.setDaemon(true);
        this.setName("{Diorite|SrvCon}");
        final int threads = core.getConfig().getNettyThreads();
        this.nioEventLoopGroupLazyValue = new LazyValue<>(() -> new NioEventLoopGroup(threads, new ThreadFactoryBuilder().setNameFormat("Diorite-Netty#%d").setDaemon(true).build()));
        this.epollEventLoopGroupLazyValue = new LazyValue<>(() -> new EpollEventLoopGroup(threads, new ThreadFactoryBuilder().setNameFormat("Diorite-Netty-Epoll#%d").setDaemon(true).build()));
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
        final Class<? extends ServerSocketChannel> socketChannelClass;
        final LazyValue<? extends EventLoopGroup> lazyInit;
        if ((Epoll.isAvailable()) && useEpoll)
        {
            socketChannelClass = EpollServerSocketChannel.class;
            lazyInit = this.epollEventLoopGroupLazyValue;
            CoreMain.debug("[Netty] Using epoll channel type");
        }
        else
        {
            socketChannelClass = NioServerSocketChannel.class;
            lazyInit = this.nioEventLoopGroupLazyValue;
            CoreMain.debug("[Netty] Using default channel type");
        }
        this.channelFuture = new ServerBootstrap().channel(socketChannelClass).childHandler(new ServerConnectionChannel(this)).group(lazyInit.get()).localAddress(address, port).bind().syncUninterruptibly();
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
        while (this.core.isRunning())
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

    public void remove(final CoreNetworkManager networkManager)
    {
        this.connections.remove(networkManager);
    }

    public void update()
    {
        final Iterator<NetworkManager> iterator = this.connections.iterator();
        while (iterator.hasNext())
        {
            final CoreNetworkManager networkManager = iterator.next();
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
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("protocolKey", this.protocolKey).append("connections", this.connections).append("channelFuture", this.channelFuture).append("server", this.core).toString();
    }
}
