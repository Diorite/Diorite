package org.diorite.impl.client.connection.listeners;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.DioriteCore;
import org.diorite.impl.connection.CoreNetworkManager;
import org.diorite.impl.connection.packets.handshake.PacketHandshakingServerListener;
import org.diorite.chat.component.BaseComponent;

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