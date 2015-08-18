package org.diorite.impl.client.connection.listeners;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.DioriteCore;
import org.diorite.impl.connection.CoreNetworkManager;
import org.diorite.impl.connection.packets.login.PacketLoginServerListener;
import org.diorite.impl.connection.packets.login.server.PacketLoginServerDisconnect;
import org.diorite.impl.connection.packets.login.server.PacketLoginServerEncryptionBegin;
import org.diorite.impl.connection.packets.login.server.PacketLoginServerSetCompression;
import org.diorite.impl.connection.packets.login.server.PacketLoginServerSuccess;
import org.diorite.chat.component.BaseComponent;

public class LoginListener implements PacketLoginServerListener
{
    private final DioriteCore        core;
    private final CoreNetworkManager networkManager;

    public LoginListener(final DioriteCore core, final CoreNetworkManager networkManager)
    {
        this.core = core;
        this.networkManager = networkManager;
    }

    @Override
    public void handle(final PacketLoginServerEncryptionBegin packet)
    {

    }

    @Override
    public void handle(final PacketLoginServerSuccess packet)
    {

    }

    @Override
    public void handle(final PacketLoginServerDisconnect packet)
    {

    }

    @Override
    public void handle(final PacketLoginServerSetCompression packet)
    {

    }

    @Override
    public void disconnect(final BaseComponent message)
    {

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
