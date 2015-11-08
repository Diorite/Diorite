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
        System.out.println(packet);
    }

    @Override
    public void handle(final PacketLoginServerSuccess packet)
    {
        System.out.println(packet);
    }

    @Override
    public void handle(final PacketLoginServerDisconnect packet)
    {
        System.out.println(packet);
    }

    @Override
    public void handle(final PacketLoginServerSetCompression packet)
    {
        System.out.println(packet);
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
