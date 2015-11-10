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

package org.diorite.impl.client.connection;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.client.connection.listeners.HandshakeListener;
import org.diorite.impl.connection.packets.PacketCodec;
import org.diorite.impl.connection.packets.PacketSizer;

import io.netty.channel.Channel;
import io.netty.channel.ChannelException;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;

public class ClientConnectionChannel extends ChannelInitializer<Channel>
{
    public static final int IP_TOS          = 24;
    public static final int TIMEOUT_SECONDS = 30;
    final ClientConnection clientConnection;

    ClientConnectionChannel(final ClientConnection clientConnection)
    {
        this.clientConnection = clientConnection;
    }

    @Override
    protected void initChannel(final Channel channel) throws Exception
    {
        try
        {
            channel.config().setOption(ChannelOption.IP_TOS, IP_TOS);
        } catch (final ChannelException ignored)
        {
        }
        try
        {
            channel.config().setOption(ChannelOption.TCP_NODELAY, false);
        } catch (final ChannelException ignored)
        {
        }
        channel.pipeline().addLast("timeout", new ReadTimeoutHandler(TIMEOUT_SECONDS)).addLast("sizer", new PacketSizer()).addLast("codec", new PacketCodec(this.clientConnection));
        final NetworkManager networkmanager = new NetworkManager(this.clientConnection.getCore());
        this.clientConnection.setConnection(networkmanager);

        channel.pipeline().addLast("packet_handler", networkmanager);
        networkmanager.setPacketListener(new HandshakeListener(this.clientConnection.getCore(), networkmanager));
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("connectionHandler", this.clientConnection).toString();
    }
}
