package diorite.impl.connection.listeners;

import java.util.UUID;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.mojang.authlib.GameProfile;

import diorite.Server;
import diorite.chat.BaseComponent;
import diorite.chat.TextComponent;
import diorite.impl.ServerImpl;
import diorite.impl.connection.packets.status.in.PacketStatusInPing;
import diorite.impl.connection.packets.status.in.PacketStatusInStart;
import diorite.impl.connection.packets.status.out.PacketStatusOutServerInfo;
import diorite.impl.connection.ping.ServerPingPlayerSample;
import diorite.impl.connection.NetworkManager;
import diorite.impl.connection.packets.status.out.PacketStatusOutPong;
import diorite.impl.connection.ping.ServerPing;
import diorite.impl.connection.ping.ServerPingServerData;

public class PacketStatusListener implements PacketStatusInListener
{
    private final ServerImpl     server;
    private final NetworkManager networkManager;

    public PacketStatusListener(final ServerImpl minecraftserver, final NetworkManager networkmanager)
    {
        super();
        this.server = minecraftserver;
        this.networkManager = networkmanager;
    }

    @Override
    public void handle(final PacketStatusInStart packetstatusinstart)
    {

        final ServerPing ping = new ServerPing();
        ping.setFavicon(null);
        ping.setMotd(TextComponent.fromLegacyText("§9Ten server bazuje na silniku Diorite\n§3Kup już teraz, jedyne 299$ miesięcznie.")[0]);
        ping.setPlayerData(new ServerPingPlayerSample(69, 23, new GameProfile(UUID.randomUUID(), "jeeej, jakiś tam gracz.")));
        ping.setServerData(new ServerPingServerData(Server.NANE + " " + Server.VERSION, 47));
        this.networkManager.handle(new PacketStatusOutServerInfo(ping));
    }

    @Override
    public void handle(final PacketStatusInPing ping)
    {
        this.networkManager.handle(new PacketStatusOutPong(ping.getPing()));
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
