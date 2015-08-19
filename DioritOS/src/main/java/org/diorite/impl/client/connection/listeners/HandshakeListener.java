package org.diorite.impl.client.connection.listeners;

import java.net.InetSocketAddress;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.DioriteCore;
import org.diorite.impl.auth.GameProfile;
import org.diorite.impl.connection.CoreNetworkManager;
import org.diorite.impl.connection.EnumProtocol;
import org.diorite.impl.connection.listeners.PacketHandshakeListener;
import org.diorite.impl.connection.packets.handshake.PacketHandshakingServerListener;
import org.diorite.impl.connection.packets.handshake.RequestType;
import org.diorite.impl.connection.packets.handshake.client.PacketHandshakingClientSetProtocol;
import org.diorite.impl.connection.packets.login.client.PacketLoginClientStart;
import org.diorite.chat.component.BaseComponent;
import org.diorite.utils.DioriteUtils;

public class HandshakeListener implements PacketHandshakingServerListener
{
    private final DioriteCore        core;
    private final CoreNetworkManager networkManager;

    public HandshakeListener(final DioriteCore core, final CoreNetworkManager networkManager)
    {
        this.core = core;
        this.networkManager = networkManager;
    }

    @Override
    public void disconnect(final BaseComponent baseComponent)
    {
        this.networkManager.close(baseComponent, true);
    }

    public void startConnection(final RequestType type)
    {
        switch (type)
        {
            case STATUS:
                // TODO finish
                break;
            case LOGIN:
                this.networkManager.setPacketListener(new LoginListener(this.core, this.networkManager));
                // TODO finish
                final InetSocketAddress address = (InetSocketAddress) this.networkManager.getSocketAddress();
                this.networkManager.sendPacket(new PacketHandshakingClientSetProtocol(PacketHandshakeListener.CURRENT_PROTOCOL, RequestType.LOGIN, address.getPort(), address.getHostName()));
                this.networkManager.setProtocol(EnumProtocol.LOGIN);
                this.networkManager.sendPacket(new PacketLoginClientStart(new GameProfile(DioriteUtils.getCrackedUuid("player"), "player")));
                break;
        }
    }

    public CoreNetworkManager getNetworkManager()
    {
        return this.networkManager;
    }

    @Override
    public DioriteCore getCore()
    {
        return this.core;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("core", this.core).append("networkManager", this.networkManager).toString();
    }
}