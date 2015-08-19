package org.diorite.impl.client.connection;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.client.connection.listeners.HandshakeListener;
import org.diorite.impl.connection.EnumProtocol;
import org.diorite.impl.connection.packets.PacketCodec;
import org.diorite.impl.connection.packets.PacketSplitter;
import org.diorite.impl.connection.packets.handshake.RequestType;

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
        channel.pipeline().addLast("timeout", new ReadTimeoutHandler(TIMEOUT_SECONDS)).addLast("sizer", new PacketSplitter()).addLast("codec", new PacketCodec(this.clientConnection));
        final NetworkManager networkmanager = new NetworkManager(this.clientConnection.getCore(), channel);
        networkmanager.setProtocol(EnumProtocol.HANDSHAKING);
        this.clientConnection.setConnection(networkmanager);

        channel.pipeline().addLast("packet_handler", networkmanager);
        final HandshakeListener hl = new HandshakeListener(this.clientConnection.getCore(), networkmanager);
        networkmanager.setPacketListener(hl);
        hl.startConnection(RequestType.LOGIN);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("connectionHandler", this.clientConnection).toString();
    }
}
