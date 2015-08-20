package org.diorite.impl.client.connection;

import org.diorite.impl.DioriteCore;
import org.diorite.impl.client.connection.listeners.HandshakeListener;
import org.diorite.impl.connection.CoreNetworkManager;
import org.diorite.impl.connection.packets.handshake.RequestType;

import io.netty.channel.ChannelHandlerContext;

public class NetworkManager extends CoreNetworkManager
{
    public NetworkManager(final DioriteCore core)
    {
        super(core);
    }

    @Override
    public void setPing(final int ping)
    {
        super.setPing(ping);
    }

    @Override
    public void channelActive(final ChannelHandlerContext channelHandlerContext) throws Exception
    {
        super.channelActive(channelHandlerContext);
        if (this.packetListener instanceof HandshakeListener)
        {
            ((HandshakeListener) this.packetListener).startConnection(RequestType.LOGIN);
        }
    }

    @Override
    public void handleClosed()
    {
        // TODO: disconnect from server.
    }
}
