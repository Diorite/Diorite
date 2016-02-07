/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2016. Diorite (by Bartłomiej Mazur (aka GotoFinal))
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

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.logging.log4j.LogManager;
import org.slf4j.Logger;

import org.diorite.impl.DioriteCore;
import org.diorite.impl.connection.CoreNetworkManager;
import org.diorite.impl.connection.EnumProtocol;
import org.diorite.impl.connection.packets.handshake.PacketHandshakingServerboundListener;
import org.diorite.impl.connection.packets.handshake.serverbound.PacketHandshakingServerboundSetProtocol;
import org.diorite.impl.connection.packets.login.clientbound.PacketLoginClientboundDisconnect;
import org.diorite.chat.component.BaseComponent;
import org.diorite.chat.component.TextComponent;

public class HandshakeListener implements PacketHandshakingServerboundListener
{
    public static final  int                    CLEANUP_THROTTLE = 200;
    private static final Map<InetAddress, Long> throttleTracker  = new ConcurrentHashMap<>(100, 0.2f, 8);
    private static       int                    throttleCounter  = 0;
    private final DioriteCore        core;
    private final CoreNetworkManager networkManager;

    public HandshakeListener(final DioriteCore core, final CoreNetworkManager networkManager)
    {
        this.core = core;
        this.networkManager = networkManager;
    }

    @Override
    public void handle(final PacketHandshakingServerboundSetProtocol packet)
    {

        switch (packet.getRequestType())
        {
            case LOGIN:
                this.networkManager.setProtocol(EnumProtocol.LOGIN);
                try
                {
                    final long currentTime = System.currentTimeMillis();
                    final long connectionThrottle = this.core.getConnectionThrottle();
                    final InetAddress address = ((InetSocketAddress) this.networkManager.getSocketAddress()).getAddress();

                    if ((throttleTracker.containsKey(address)) && (! "127.0.0.1".equals(address.getHostAddress())) && ((currentTime - throttleTracker.get(address)) < connectionThrottle))
                    {
                        throttleTracker.put(address, currentTime);
                        this.disconnect(TextComponent.fromLegacyText("Connection throttled! Please wait before reconnecting."));
                        return;
                    }
                    throttleTracker.put(address, currentTime);
                    throttleCounter += 1;
                    if (throttleCounter > CLEANUP_THROTTLE)
                    {
                        throttleCounter = 0;

                        final Iterator<Map.Entry<InetAddress, Long>> it = throttleTracker.entrySet().iterator();
                        while (it.hasNext())
                        {
                            final Map.Entry<InetAddress, Long> entry = it.next();
                            if (entry.getValue() > connectionThrottle)
                            {
                                it.remove();
                            }
                        }
                    }
                } catch (final Throwable t)
                {
                    LogManager.getLogger().debug("Failed to check connection throttle", t);
                }
//                if (packet.getProtocolVersion() > CURRENT_PROTOCOL)
//                {
//                    CoreMain.debug("Player fail to join, invalid protocol version (" + packet.getProtocolVersion() + " > " + CURRENT_PROTOCOL + ")");
//                    this.disconnect(TextComponent.fromLegacyText("Outdated server, we are still on 1.9"));
//                }
//                else if (packet.getProtocolVersion() < CURRENT_PROTOCOL)
//                {
//                    CoreMain.debug("Player fail to join, invalid protocol version (" + packet.getProtocolVersion() + " < " + CURRENT_PROTOCOL + ")");
//                    this.disconnect(TextComponent.fromLegacyText("Outdated client, we are on 1.9"));
//                }
//                else
            {
                this.networkManager.setPacketListener(new LoginListener(this.core, this.networkManager, packet.getServerAddress() + ":" + packet.getServerPort()));
            }
            break;
            case STATUS:
                this.networkManager.setProtocol(EnumProtocol.STATUS);
                this.networkManager.setPacketListener(new StatusListener(this.core, this.networkManager));
                break;
            default:
                throw new UnsupportedOperationException("Invalid intention " + packet.getRequestType());
        }
    }

    @Override
    public void disconnect(final BaseComponent baseComponent)
    {
        this.networkManager.sendPacket(new PacketLoginClientboundDisconnect(baseComponent));
        this.networkManager.close(baseComponent, true);
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

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("core", this.core).append("networkManager", this.networkManager).toString();
    }
}