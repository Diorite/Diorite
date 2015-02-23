package diorite.impl.connection;

import java.net.InetAddress;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import com.google.common.collect.Lists;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.DefaultEventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.AttributeKey;
import diorite.impl.ServerImpl;
import diorite.impl.utils.LazyInitVar;

public class ServerConnection
{
    public final  AttributeKey<EnumProtocol>         protocolKey                   = AttributeKey.valueOf("protocol");
    public final  LazyInitVar<NioEventLoopGroup>     lazyInitNioEventLoopGroup     = new LazyInitNioEventLoopGroup();
    public final  LazyInitVar<DefaultEventLoopGroup> lazyInitDefaultEventLoopGroup = new LazyInitDefaultEventLoopGroup();
    private final List<NetworkManager>               connections                   = Collections.synchronizedList(Lists.newArrayList());
    private ChannelFuture channelFuture;

    private final ServerImpl server;

    public ServerImpl getServer()
    {
        return this.server;
    }

    public ServerConnection(final ServerImpl server)
    {
        this.server = server;
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
//                            if (networkManager.c())
//                            {
//                                CrashReport crashreport = CrashReport.a(exception, "Ticking memory connection");
//                                CrashReportSystemDetails crashreportsystemdetails = crashreport.a("Ticking connection");
//
//                                crashreportsystemdetails.a("Connection", new CrashReportNetworkManager(this, networkManager));
//                                throw new ReportedException(crashreport);
//                            }
//                            d.warn("Failed to handle packet for " + networkManager.getSocketAddress(), exception);
//                            ChatComponentText chatcomponenttext = new ChatComponentText("Internal server error");
//
//                            networkManager.a(new PacketPlayOutKickDisconnect(chatcomponenttext), new NetworkManagerCloseFuture(this, networkManager, chatcomponenttext), new GenericFutureListener[0]);
//                            networkManager.k();
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
}
