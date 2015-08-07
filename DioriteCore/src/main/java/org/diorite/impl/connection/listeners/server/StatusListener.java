package org.diorite.impl.connection.listeners.server;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.DioriteCore;
import org.diorite.impl.cfg.DioriteConfigImpl;
import org.diorite.impl.connection.CoreNetworkManager;
import org.diorite.impl.connection.packets.status.PacketStatusClientListener;
import org.diorite.impl.connection.packets.status.client.PacketStatusClientPing;
import org.diorite.impl.connection.packets.status.client.PacketStatusClientStart;
import org.diorite.impl.connection.packets.status.server.PacketStatusServerPong;
import org.diorite.impl.connection.packets.status.server.PacketStatusServerServerInfo;
import org.diorite.impl.connection.ping.ServerPing;
import org.diorite.impl.connection.ping.ServerPingPlayerSample;
import org.diorite.impl.connection.ping.ServerPingServerData;
import org.diorite.Core;
import org.diorite.chat.ChatColor;
import org.diorite.chat.component.BaseComponent;
import org.diorite.chat.component.TextComponent;

public class StatusListener implements PacketStatusClientListener
{
    private final DioriteCore        core;
    private final CoreNetworkManager networkManager;

    public StatusListener(final DioriteCore core, final CoreNetworkManager networkManager)
    {
        super();
        this.core = core;
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
        final DioriteConfigImpl cfg = DioriteCore.getInstance().getConfig();
        ping.setFavicon(null);
        ping.setMotd(ChatColor.translateAlternateColorCodes(cfg.getMotd()));
        ping.setPlayerData(new ServerPingPlayerSample(cfg.getMaxPlayers(), DioriteCore.getInstance().getOnlinePlayers().size()));
        ping.setServerData(new ServerPingServerData(Core.NANE + " " + this.core.getVersion(), HandshakeListener.CURRENT_PROTOCOL));
        this.networkManager.sendPacket(new PacketStatusServerServerInfo(ping));
    }

    @Override
    public void disconnect(final BaseComponent message)
    {
        this.networkManager.close(message, true);
    }

    public DioriteCore getCore()
    {
        return this.core;
    }

    public CoreNetworkManager getNetworkManager()
    {
        return this.networkManager;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("server", this.core).append("networkManager", this.networkManager).toString();
    }
}
