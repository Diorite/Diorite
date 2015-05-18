package org.diorite.impl.connection.listeners;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

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
import org.diorite.Server;
import org.diorite.cfg.DioriteConfig;
import org.diorite.chat.ChatColor;
import org.diorite.chat.component.BaseComponent;
import org.diorite.chat.component.TextComponent;

public class StatusListener implements PacketStatusInListener
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
    public void handle(final PacketStatusInPing packet)
    {
        this.networkManager.sendPacket(new PacketStatusOutPong(packet.getPing()));
        this.networkManager.setPing((int) packet.getPing());
        this.disconnect(new TextComponent());
    }

    @Override
    public void handle(final PacketStatusInStart packet)
    {
        final ServerPing ping = new ServerPing();
        final DioriteConfig cfg = ServerImpl.getInstance().getConfig();
        ping.setFavicon(null);
        ping.setMotd(ChatColor.translateAlternateColorCodes(cfg.getMotd()));
        ping.setPlayerData(new ServerPingPlayerSample(cfg.getMaxPlayers(), ServerImpl.getInstance().getOnlinePlayers().size()));
        ping.setServerData(new ServerPingServerData(Server.NANE + " " + Server.VERSION, HandshakeListener.CURRENT_PROTOCOL));
        this.networkManager.sendPacket(new PacketStatusOutServerInfo(ping));
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
