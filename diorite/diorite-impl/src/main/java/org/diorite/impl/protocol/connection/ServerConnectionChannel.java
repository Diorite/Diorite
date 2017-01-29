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

import java.net.InetSocketAddress;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.diorite.impl.protocol.any.PlaceholderPacketListener;
import org.diorite.impl.protocol.any.serverbound.ServerboundHandshakeListener;
import org.diorite.DioriteConfig.HostConfiguration;
import org.diorite.core.DioriteCore;

import io.netty.channel.Channel;
import io.netty.channel.ChannelException;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;

public class ServerConnectionChannel extends ChannelInitializer<Channel>
{
    private static final Logger logger = LoggerFactory.getLogger("Netty");

    public static final int IP_TOS          = 24;
    public static final int TIMEOUT_SECONDS = 30;
    final ServerConnectionImpl serverConnection;
    final InetSocketAddress    serverAddress;
    final HostConfiguration    hostConfiguration;

    ServerConnectionChannel(ServerConnectionImpl serverConnection, InetSocketAddress serverAddress, HostConfiguration hostConfiguration)
    {
        this.serverConnection = serverConnection;
        this.serverAddress = serverAddress;
        this.hostConfiguration = hostConfiguration;
    }

    @Override
    protected void initChannel(Channel channel) throws Exception
    {
        System.out.println(channel.remoteAddress());
        try
        {
            channel.config().setOption(ChannelOption.IP_TOS, IP_TOS);
        }
        catch (ChannelException e)
        {
            logger.debug("Can't set IP_TOS to: " + IP_TOS, e);
        }
        try
        {
            channel.config().setOption(ChannelOption.TCP_NODELAY, false);
        }
        catch (ChannelException e)
        {
            logger.debug("Can't set TCP_NODELAY to: false", e);
        }
        DioriteCore core = this.serverConnection.getCore();
        DioriteActiveConnection<?> activeConnection =
                new DioriteActiveConnection<>(core, this.serverAddress, new PlaceholderPacketListener(), core.getProtocol().getDefault());
        channel.pipeline()
               .addLast("timeout", new ReadTimeoutHandler(TIMEOUT_SECONDS))
               .addLast("sizer", new PacketSizeCodec(this.serverConnection, activeConnection))
               .addLast("codec", new PacketCodec(this.serverConnection, activeConnection));
        // TODO: legacy ping?
        activeConnection.setPacketListener(new ServerboundHandshakeListener(activeConnection));
        this.serverConnection.connections.add(activeConnection);
        channel.pipeline().addLast("packet_handler", activeConnection);
//        activeConnection.setPacketListener(new HandshakeListener(this.serverConnection.getCore(), activeConnection));
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("connectionHandler", this.serverConnection).toString();
    }
}
