package org.diorite.impl.client.connection;

import java.net.InetSocketAddress;

import org.diorite.impl.DioriteCore;
import org.diorite.impl.connection.CoreNetworkManager;

import io.netty.channel.Channel;

public class NetworkManager extends CoreNetworkManager
{
    public NetworkManager(final DioriteCore core, final Channel channel)
    {
        super(core);
        this.setChannel(channel);
        this.setAddress(new InetSocketAddress(core.getHostname(), core.getPort()));
    }

    @Override
    public void setPing(final int ping)
    {
        super.setPing(ping);
    }

    @Override
    public void handleClosed()
    {
        // TODO: disconnect from server.
    }
}
