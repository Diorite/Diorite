/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017. Diorite (by Bartłomiej Mazur (aka GotoFinal))
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

package org.diorite.impl.protocol.p16w50a;

import java.util.ArrayList;
import java.util.Collection;

import org.diorite.impl.protocol.p16w50a.clientbound.CS00Response;
import org.diorite.DioriteConfig;
import org.diorite.DioriteConfig.HostConfiguration;
import org.diorite.chat.ChatColor;
import org.diorite.chat.ChatMessage;
import org.diorite.core.DioriteCore;
import org.diorite.core.protocol.ProtocolVersion;
import org.diorite.core.protocol.connection.ActiveConnection;
import org.diorite.core.protocol.connection.ServerboundPacketHandler;
import org.diorite.core.protocol.packets.clientbound.ServerStateResponsePacket;
import org.diorite.core.protocol.packets.serverbound.LoginStartPacket;
import org.diorite.core.protocol.packets.serverbound.RequestServerStatePacket;
import org.diorite.event.events.connection.ServerPingEvent;
import org.diorite.gameprofile.GameProfile;
import org.diorite.ping.Favicon;
import org.diorite.ping.ServerPing;
import org.diorite.ping.ServerPingPlayerSample;
import org.diorite.ping.ServerPingServerData;

import net.engio.mbassy.listener.Handler;

public class PCp16w50aServerboundPacketHandler implements ServerboundPacketHandler
{
    private final ActiveConnection activeConnection;

    public PCp16w50aServerboundPacketHandler(ActiveConnection activeConnection)
    {
        this.activeConnection = activeConnection;
    }

    @Handler
    @Override
    public void handle(LoginStartPacket packet)
    {
        // TODO
    }

    @Handler
    @Override
    public void handle(RequestServerStatePacket packet)
    {
        DioriteCore dioriteCore = this.activeConnection.getDioriteCore();
        DioriteConfig dioriteConfig = dioriteCore.getConfig();
        HostConfiguration hostCfg = dioriteConfig.getHostConfigurationOrDefault(this.activeConnection.getServerAddress());
        ProtocolVersion<?> protocolVersion = this.activeConnection.getProtocolVersion();

        ServerPingServerData pingServerData = new ServerPingServerData(protocolVersion.getVersionName(), protocolVersion.getVersion());

        int sampleSize = hostCfg.getSampleSize();
        Collection<GameProfile> sample = new ArrayList<>(sampleSize);
        // TODO: players
        ServerPingPlayerSample sampleData = new ServerPingPlayerSample(dioriteConfig.getMaxPlayers(), 0, sample);

        Favicon preparedFavicon = hostCfg.getPreparedFavicon();

        ChatMessage motd = ChatColor.translateAlternateColorCodes(hostCfg.getMotd());
        ServerPing serverPing = new ServerPing(motd, preparedFavicon, pingServerData, sampleData);

        ServerPingEvent serverPingEvent = new ServerPingEvent(serverPing);
        dioriteCore.getEventManager().callEvent(serverPingEvent);
        serverPing = serverPingEvent.getServerPing();

        ServerStateResponsePacket responsePacket = new ServerStateResponsePacket(serverPing);
        this.activeConnection.callEvent(responsePacket);
    }

    @Handler
    @Override
    public void handle(ServerStateResponsePacket packet)
    {
        ServerPing ping = packet.getServerPing();
        assert ping != null;
        ServerPingPlayerSample playerData = ping.getPlayerData();
        ServerPingServerData serverData = ping.getServerData();

        CS00Response cs00Response = new CS00Response();
        cs00Response.setDescription(ping.getMotd());
        if (ping.getFavicon() != null)
        {
            cs00Response.setEncodedFavicon(ping.getFavicon().getEncoded());
        }
        cs00Response.setMaxPlayers(playerData.getMaxPlayers());
        cs00Response.setOnlinePlayers(playerData.getOnlinePlayers());
        cs00Response.setSample(playerData.getProfiles());
        cs00Response.setVersionNumber(serverData.getProtocol());
        cs00Response.setVersionString(serverData.getName());

        this.activeConnection.sendPacket(cs00Response);
    }
}
