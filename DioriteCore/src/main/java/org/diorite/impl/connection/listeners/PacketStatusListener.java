package org.diorite.impl.connection.listeners;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.Server;
import org.diorite.chat.BaseComponent;
import org.diorite.chat.TextComponent;
import org.diorite.impl.ServerImpl;
import org.diorite.impl.connection.NetworkManager;
import org.diorite.impl.connection.packets.status.PacketStatusInListener;
import org.diorite.impl.connection.packets.status.in.PacketStatusInPing;
import org.diorite.impl.connection.packets.status.in.PacketStatusInStart;
import org.diorite.impl.connection.packets.status.out.PacketStatusOutPong;
import org.diorite.impl.connection.packets.status.out.PacketStatusOutServerInfo;
import org.diorite.impl.connection.ping.ServerPing;
import org.diorite.impl.connection.ping.ServerPingPlayerSample;
import org.diorite.impl.connection.ping.ServerPingServerData;

public class PacketStatusListener implements PacketStatusInListener
{
    private final ServerImpl     server;
    private final NetworkManager networkManager;

    public PacketStatusListener(final ServerImpl server, final NetworkManager networkManager)
    {
        super();
        this.server = server;
        this.networkManager = networkManager;
    }

    @Override
    public void handle(final PacketStatusInPing packet)
    {
        this.networkManager.handle(new PacketStatusOutPong(packet.getPing()));
    }

    @Override
    public void handle(final PacketStatusInStart packet)
    {

        final ServerPing ping = new ServerPing();
        ping.setFavicon(null);
        ping.setMotd(new TextComponent("test"));
        ping.setPlayerData(new ServerPingPlayerSample(10, 0));
        ping.setServerData(new ServerPingServerData(Server.NANE + " " + Server.VERSION, 47));
        this.networkManager.handle(new PacketStatusOutServerInfo(ping));
    }

    @Override
    public void disconnect(final BaseComponent message)
    {

    }

    public ServerImpl getServer()
    {
        return this.server;
    }

    public NetworkManager getNetworkManager()
    {
        return this.networkManager;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("server", this.server).append("networkManager", this.networkManager).toString();
    }
}
