package org.diorite.impl.client.connection.listeners;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.slf4j.Logger;

import org.diorite.impl.DioriteCore;
import org.diorite.impl.connection.CoreNetworkManager;
import org.diorite.impl.connection.packets.status.PacketStatusServerListener;
import org.diorite.impl.connection.packets.status.server.PacketStatusServerPong;
import org.diorite.impl.connection.packets.status.server.PacketStatusServerServerInfo;
import org.diorite.chat.component.BaseComponent;

public class StatusListener implements PacketStatusServerListener
{
    private final DioriteCore        core;
    private final CoreNetworkManager networkManager;

    public StatusListener(final DioriteCore core, final CoreNetworkManager networkManager)
    {
        this.core = core;
        this.networkManager = networkManager;
    }

    @Override
    public void handle(final PacketStatusServerServerInfo packet)
    {

    }

    @Override
    public void handle(final PacketStatusServerPong packet)
    {

    }

    @Override
    public void disconnect(final BaseComponent message)
    {
        this.networkManager.close(message, true);
    }

    @Override
    public Logger getLogger()
    {
        return this.core.getLogger();
    }

    @Override
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
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("core", this.core).append("networkManager", this.networkManager).toString();
    }
}
