package org.diorite.impl.connection.listeners.server;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.ServerImpl;
import org.diorite.impl.cfg.DioriteConfigImpl;
import org.diorite.impl.connection.NetworkManager;
import org.diorite.impl.connection.packets.status.PacketStatusClientListener;
import org.diorite.impl.connection.packets.status.client.PacketStatusClientPing;
import org.diorite.impl.connection.packets.status.client.PacketStatusClientStart;
import org.diorite.impl.connection.packets.status.server.PacketStatusServerPong;
import org.diorite.impl.connection.packets.status.server.PacketStatusServerServerInfo;
import org.diorite.impl.connection.ping.ServerPing;
import org.diorite.impl.connection.ping.ServerPingPlayerSample;
import org.diorite.impl.connection.ping.ServerPingServerData;
import org.diorite.Server;
import org.diorite.chat.ChatColor;
import org.diorite.chat.component.BaseComponent;
import org.diorite.chat.component.TextComponent;

public class StatusListener implements PacketStatusClientListener
{
    private final ServerImpl     server;
    private final NetworkManager networkManager;

    public StatusListener(final ServerImpl server, final NetworkManager networkManager)
    {
        super();
        this.server = server;
        this.networkManager = networkManager;
    }

    @Override
    public void handle(final PacketStatusClientPing packet)
    {
        this.networkManager.sendPacket(new PacketStatusServerPong(packet.getPing()));
        this.networkManager.setPing((int) packet.getPing());
        this.disconnect(new TextComponent());
    }

    @Override
    public void handle(final PacketStatusClientStart packet)
    {
        final ServerPing ping = new ServerPing();
        final DioriteConfigImpl cfg = ServerImpl.getInstance().getConfig();
        ping.setFavicon(null);
        ping.setMotd(ChatColor.translateAlternateColorCodes(cfg.getMotd()));
        ping.setPlayerData(new ServerPingPlayerSample(cfg.getMaxPlayers(), ServerImpl.getInstance().getOnlinePlayers().size()));
        ping.setServerData(new ServerPingServerData(Server.NANE + " " + this.server.getVersion(), HandshakeListener.CURRENT_PROTOCOL));
        this.networkManager.sendPacket(new PacketStatusServerServerInfo(ping));
    }

    @Override
    public void disconnect(final BaseComponent message)
    {
        this.networkManager.close(message, true);
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
