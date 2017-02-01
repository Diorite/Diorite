/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017. Diorite (by Bartłomiej Mazur (aka GotoFinal))
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

package org.diorite.impl.protocol.connection;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.diorite.DioriteConfig;
import org.diorite.DioriteConfig.HostConfiguration;
import org.diorite.core.DioriteCore;
import org.diorite.core.protocol.connection.ActiveConnection;
import org.diorite.core.protocol.connection.ServerConnection;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.epoll.Epoll;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollServerSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import it.unimi.dsi.fastutil.objects.ObjectSets;

public class ServerConnectionImpl extends Thread implements ServerConnection
{
    private final       Logger logger = LoggerFactory.getLogger("[Netty]");
    public static final int    MILLIS = 500;

    final Set<DioriteActiveConnection<?>> connections = ObjectSets.synchronize(new ObjectOpenHashSet<>(200));
    private final DioriteCore core;
    private final Map<InetSocketAddress, ChannelFuture> channels = new ConcurrentHashMap<>(1);

    @SuppressWarnings("resource")
    public ServerConnectionImpl(DioriteCore core)
    {
        this.core = core;
        this.setDaemon(true);
        this.setName("{Diorite|SrvCon}");
    }

    @Override
    public DioriteCore getCore()
    {
        return this.core;
    }

    @Override
    public void init()
    {
        DioriteConfig config = this.core.getConfig();
        boolean epoll = config.getUseNativeTransport();
        for (InetSocketAddress address : config.getHosts())
        {
            HostConfiguration hostConfiguration = config.getHostConfigurationOrDefault(address);
            try
            {
                hostConfiguration.prepareFavicon();
            }
            catch (IOException e)
            {
                LoggerFactory.getLogger(address.toString()).error("Can't prepare favicon.", e);
            }
            if (hostConfiguration.isAcceptingPlayers())
            {
                this.bind(epoll, address);
            }
        }
    }

    public void unbind(boolean useEpoll, InetSocketAddress address)
    {
        // TODO: custom disconnect message?
        ChannelFuture remove = this.channels.remove(address);

    }

    @Override
    public void bind(boolean useEpoll, InetSocketAddress address)
    {
        if (this.channels.containsKey(address))
        {
            throw new IllegalStateException("Already bound to: " + address);
        }
        this.channels.put(address, this.initBootstrap(useEpoll, address).bind().syncUninterruptibly());
    }

    private ServerBootstrap initBootstrap(boolean useEpoll, InetSocketAddress address)
    {
        HostConfiguration hostConfiguration = this.core.getConfig().getHostConfigurationOrDefault(address);
        int threads = hostConfiguration.getNettyThreads();
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.localAddress(address);
        if ((Epoll.isAvailable()) && useEpoll)
        {
            serverBootstrap.channel(EpollServerSocketChannel.class);
            serverBootstrap.group(new EpollEventLoopGroup(threads, new ThreadFactoryBuilder().setNameFormat(address + "-Epoll#%d").setDaemon(true).build()));
            this.logger.debug("Using epoll channel type for: " + address);
        }
        else
        {
            serverBootstrap.channel(NioServerSocketChannel.class);
            serverBootstrap.group(new NioEventLoopGroup(threads, new ThreadFactoryBuilder().setNameFormat(address + "Netty-NIO#%d").setDaemon(true).build()));
            this.logger.debug("Using default channel type: " + address);
        }
        return serverBootstrap.childHandler(new ServerConnectionChannel(this, address, hostConfiguration));
    }

    @Override
    public void close()
    {
        if (this.channels.isEmpty())
        {
            throw new IllegalStateException("Connection not bound yet.");
        }
        try
        {
            for (ChannelFuture channelFuture : this.channels.values())
            {
                channelFuture.channel().close().sync();
            }
        }
        catch (InterruptedException e)
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
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void remove(ActiveConnection activeConnection)
    {
        this.connections.remove(activeConnection);
    }

    @Override
    public void update()
    {
        Iterator<DioriteActiveConnection<?>> iterator = this.connections.iterator();
        while (iterator.hasNext())
        {
            DioriteActiveConnection<?> connection = iterator.next();
            connection.checkAlive();
            if (! connection.hasNoChannel())
            {
                if (! connection.isChannelOpen())
                {
                    if (! connection.isPreparing())
                    {
                        iterator.remove();
                        connection.checkConnection();
                    }
                }
                else
                {
                    try
                    {
                        connection.update();
                    }
                    catch (Exception exception)
                    {
                        exception.printStackTrace();
                    }
                }
            }
        }
    }

    @Override
    public Set<DioriteActiveConnection<?>> getConnections()
    {
        return Collections.unmodifiableSet(this.connections);
    }
}
