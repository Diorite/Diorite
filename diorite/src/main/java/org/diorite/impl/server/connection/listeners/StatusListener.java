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

package org.diorite.impl.server.connection.listeners;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.slf4j.Logger;

import org.diorite.impl.DioriteCore;
import org.diorite.impl.cfg.DioriteConfigImpl;
import org.diorite.impl.connection.CoreNetworkManager;
import org.diorite.impl.connection.packets.status.PacketStatusServerboundListener;
import org.diorite.impl.connection.packets.status.serverbound.PacketStatusServerboundPing;
import org.diorite.impl.connection.packets.status.serverbound.PacketStatusServerboundStart;
import org.diorite.impl.connection.packets.status.clientbound.PacketStatusClientboundPong;
import org.diorite.impl.connection.packets.status.clientbound.PacketStatusClientboundServerInfo;
import org.diorite.impl.connection.ping.ServerPing;
import org.diorite.impl.connection.ping.ServerPingPlayerSample;
import org.diorite.impl.connection.ping.ServerPingServerData;
import org.diorite.Core;
import org.diorite.chat.ChatColor;
import org.diorite.chat.component.BaseComponent;
import org.diorite.chat.component.TextComponent;

public class StatusListener implements PacketStatusServerboundListener
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
    public void handle(final PacketStatusServerboundPing packet)
    {
        this.networkManager.sendPacket(new PacketStatusClientboundPong(packet.getPing()));
        this.networkManager.setPing((int) packet.getPing());
        this.disconnect(new TextComponent());
    }

    @Override
    public void handle(final PacketStatusServerboundStart packet)
    {
        final ServerPing ping = new ServerPing();
        final DioriteConfigImpl cfg = DioriteCore.getInstance().getConfig();
        ping.setFavicon(null);
        ping.setMotd(ChatColor.translateAlternateColorCodes(cfg.getMotd()));
        ping.setPlayerData(new ServerPingPlayerSample(cfg.getMaxPlayers(), DioriteCore.getInstance().getOnlinePlayers().size()));
        ping.setServerData(new ServerPingServerData(Core.NANE + " " + this.core.getVersion(), HandshakeListener.CURRENT_PROTOCOL));
        this.networkManager.sendPacket(new PacketStatusClientboundServerInfo(ping));
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
