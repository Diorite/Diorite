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

package org.diorite.impl.connection;

import org.slf4j.Logger;

import org.diorite.inject.Injection;
import org.diorite.inject.Named;

public class ServerConnection extends Thread// implements ConnectionHandler
{
    @Named("diorite") private final Logger logger = Injection.inject();
//    public static final                    int    MILLIS = 500;
//    public final LazyValue<NioEventLoopGroup>   nioEventLoopGroupLazyValue;
//    public final LazyValue<EpollEventLoopGroup> epollEventLoopGroupLazyValue;
//
//    private final AttributeKey<EnumProtocol> protocolKey = AttributeKey.valueOf("protocol");
//    private final Collection<NetworkManager> connections = Collections.synchronizedSet(Sets.newConcurrentHashSet());
//    private final DioriteCore   core;
//    private       ChannelFuture channelFuture;
//
//    @SuppressWarnings("resource")
//    public ServerConnection(DioriteCore core)
//    {
//        this.core = core;
//        this.setDaemon(true);
//        this.setName("{Diorite|SrvCon}");
//        int threads = core.getConfig().getNettyThreads();
//        this.nioEventLoopGroupLazyValue = new LazyValue<>(() -> new NioEventLoopGroup(threads, new ThreadFactoryBuilder().setNameFormat("Diorite-Netty#%d")
//                                                                                                                         .setDaemon(true).build()));
//        this.epollEventLoopGroupLazyValue = new LazyValue<>(() -> new EpollEventLoopGroup(threads, new ThreadFactoryBuilder().setNameFormat
//
// ("Diorite-Netty-Epoll#%d")
//                                                                                                                             .setDaemon(true).build()));
//    }
//
//    @Override
//    public AttributeKey<EnumProtocol> getProtocolKey()
//    {
//        return this.protocolKey;
//    }
//
//    @Override
//    public DioriteCore getCore()
//    {
//        return this.core;
//    }
//
//    @Override
//    public void init(InetAddress address, int port, boolean useEpoll)
//    {
//        Class<? extends ServerSocketChannel> socketChannelClass;
//        LazyValue<? extends EventLoopGroup> lazyInit;
//        if ((Epoll.isAvailable()) && useEpoll)
//        {
//            socketChannelClass = EpollServerSocketChannel.class;
//            lazyInit = this.epollEventLoopGroupLazyValue;
//            logger.debug("[Netty] Using epoll channel type");
//        }
//        else
//        {
//            socketChannelClass = NioServerSocketChannel.class;
//            lazyInit = this.nioEventLoopGroupLazyValue;
//            logger.debug("[Netty] Using default channel type");
//        }
//        this.channelFuture = new ServerBootstrap().channel(socketChannelClass).childHandler(new ServerConnectionChannel(this)).group(lazyInit.get())
//                                                  .localAddress(address, port).bind().syncUninterruptibly();
//    }
//
//    @Override
//    public void close()
//    {
//        try
//        {
//            this.channelFuture.channel().close().sync();
//        }
//        catch (InterruptedException e)
//        {
//            e.printStackTrace();
//        }
//    }
//
//    @Override
//    public void run()
//    {
//        while (this.core.isRunning())
//        {
//            this.update();
//            try
//            {
//                Thread.sleep(MILLIS);
//            }
//            catch (InterruptedException e)
//            {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    public void remove(CoreNetworkManager networkManager)
//    {
//        this.connections.remove(networkManager);
//    }
//
//    public void update()
//    {
//        Iterator<NetworkManager> iterator = this.connections.iterator();
//        while (iterator.hasNext())
//        {
//            CoreNetworkManager networkManager = iterator.next();
//            networkManager.checkAlive();
//            if (! networkManager.hasNoChannel())
//            {
//                if (! networkManager.isChannelOpen())
//                {
//                    if (! networkManager.isPreparing())
//                    {
//                        iterator.remove();
//                        networkManager.checkConnection();
//                    }
//                }
//                else
//                {
//                    try
//                    {
//                        networkManager.update();
//                    }
//                    catch (Exception exception)
//                    {
//                        exception.printStackTrace();
//                    }
//                }
//            }
//        }
//    }
//
//    public ChannelFuture getChannelFuture()
//    {
//        return this.channelFuture;
//    }
//
//    public Collection<NetworkManager> getConnections()
//    {
//        return this.connections;
//    }
}
