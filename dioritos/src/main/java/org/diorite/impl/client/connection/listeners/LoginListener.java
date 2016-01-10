/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 Diorite (by Bartłomiej Mazur (aka GotoFinal))
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package org.diorite.impl.client.connection.listeners;

import java.util.UUID;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.DioriteCore;
import org.diorite.impl.auth.GameProfileImpl;
import org.diorite.impl.connection.CoreNetworkManager;
import org.diorite.impl.connection.packets.login.PacketLoginClientboundListener;
import org.diorite.impl.connection.packets.login.clientbound.PacketLoginClientboundDisconnect;
import org.diorite.impl.connection.packets.login.clientbound.PacketLoginClientboundEncryptionBegin;
import org.diorite.impl.connection.packets.login.clientbound.PacketLoginClientboundSetCompression;
import org.diorite.impl.connection.packets.login.clientbound.PacketLoginClientboundSuccess;
import org.diorite.cfg.DioriteConfig.OnlineMode;
import org.diorite.chat.component.BaseComponent;

public class LoginListener implements PacketLoginClientboundListener
{
    private final DioriteCore        core;
    private final CoreNetworkManager networkManager;

    public LoginListener(final DioriteCore core, final CoreNetworkManager networkManager)
    {
        this.core = core;
        this.networkManager = networkManager;
    }

    @Override
    public void handle(final PacketLoginClientboundEncryptionBegin packet)
    {
        System.out.println(packet);
    }

    @Override
    public void handle(final PacketLoginClientboundSuccess packet)
    {
        System.out.println(packet);
    }

    @Override
    public void handle(final PacketLoginClientboundDisconnect packet)
    {
        System.out.println(packet);
    }

    @Override
    public void handle(final PacketLoginClientboundSetCompression packet)
    {
        System.out.println(packet);
    }

    @Override
    public void disconnect(final BaseComponent message)
    {

    }
    // TODO

    @Override
    public GameProfileImpl getGameProfile()
    {
        return null;
    }

    @Override
    public OnlineMode getOnlineMode()
    {
        return null;
    }

    @Override
    public void setUUID(final UUID uuid)
    {

    }

    @Override
    public void setGameProfile(final GameProfileImpl newProfile)
    {

    }

    @Override
    public void setProtocolState(final ProtocolState state)
    {

    }

    @Override
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
