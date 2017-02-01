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

package org.diorite.impl.protocol.any.serverbound;

import javax.annotation.Nullable;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.logging.log4j.LogManager;

import org.diorite.DioriteConfig.HostConfiguration;
import org.diorite.chat.ChatMessage;
import org.diorite.core.DioriteCore;
import org.diorite.core.protocol.Protocol;
import org.diorite.core.protocol.ProtocolVersion;
import org.diorite.core.protocol.connection.ActiveConnection;
import org.diorite.core.protocol.connection.internal.ProtocolState;
import org.diorite.core.protocol.connection.internal.ServerboundPacketListener;

import net.engio.mbassy.listener.Handler;

public class ServerboundHandshakeListener implements ServerboundPacketListener
{
    public static final  int                    CLEANUP_THROTTLE = 200;
    private static final Map<InetAddress, Long> throttleTracker  = new ConcurrentHashMap<>(100, 0.2f, 8);
    private static       int                    throttleCounter  = 0;
    private final DioriteCore       core;
    private final ActiveConnection  activeConnection;
    private final HostConfiguration hostConfiguration;

    private @Nullable H00Handshake packet;

    @Nullable
    public H00Handshake getPacket()
    {
        return this.packet;
    }

    public ServerboundHandshakeListener(ActiveConnection activeConnection)
    {
        this.core = activeConnection.getDioriteCore();
        this.activeConnection = activeConnection;
        this.hostConfiguration = this.core.getConfig().getHostConfigurationOrDefault(activeConnection.getServerAddress());
    }

    private volatile boolean handled = false;

    @Handler
    public void handle(H00Handshake packet)
    {
        if (this.handled)
        {
            this.disconnect(null);
            return;
        }
        this.handled = true;
        this.packet = packet;
        assert packet.getState() != null;
        switch (packet.getState())
        {
            case LOGIN:
            {
                this.activeConnection.setProtocol(ProtocolState.LOGIN);
                try
                {
                    long currentTime = System.currentTimeMillis();

                    long connectionThrottle = this.hostConfiguration.getConnectionThrottle();
                    InetSocketAddress socketAddress = (InetSocketAddress) this.activeConnection.getSocketAddress();
                    assert socketAddress != null;
                    InetAddress address = socketAddress.getAddress();

                    if ((throttleTracker.containsKey(address)) && (! "127.0.0.1".equals(address.getHostAddress())) &&
                        ((currentTime - throttleTracker.get(address)) < connectionThrottle))
                    {
                        throttleTracker.put(address, currentTime);
                        this.disconnect(ChatMessage.fromLegacy("Connection throttled! Please wait before reconnecting."));
                        return;
                    }
                    throttleTracker.put(address, currentTime);
                    throttleCounter += 1;
                    if (throttleCounter > CLEANUP_THROTTLE)
                    {
                        throttleCounter = 0;
                        throttleTracker.entrySet().removeIf(entry -> entry.getValue() > connectionThrottle);
                    }
                }
                catch (Throwable t)
                {
                    LogManager.getLogger().debug("Failed to check connection throttle", t);
                }
                Protocol<?> protocol = this.core.getProtocol();
                ProtocolVersion<?> protocolVersion = protocol.getVersion(packet.getProtocolVersion());
                if (protocolVersion == null)
                {
                    // We still need some protocol version to handle disconnect message
                    protocolVersion = protocol.getDefault();
                    this.activeConnection.setProtocolVersion(protocolVersion);
                    protocolVersion.setListener(this.activeConnection, ProtocolState.LOGIN);
                    this.core.debug("Player failed to join, invalid protocol version (" + packet.getProtocolVersion() + "). Supported versions: " +
                                    protocol.getAvailableVersions() + ")");
                    this.disconnect(ChatMessage.fromLegacy("Outdated server or client, we are on " + protocol.getAvailableVersionNames()));
                    return;
                }
                this.activeConnection.setProtocolVersion(protocolVersion);
                protocolVersion.setListener(this.activeConnection, ProtocolState.LOGIN);
                break;
            }
            case STATUS:
            {
                this.activeConnection.setProtocol(ProtocolState.STATUS);
                Protocol<?> protocol = this.core.getProtocol();
                ProtocolVersion<?> protocolVersion = protocol.getVersion(packet.getProtocolVersion());
                if (protocolVersion == null)
                {
                    protocolVersion = protocol.getDefault();
                }
                this.activeConnection.setProtocolVersion(protocolVersion);
                protocolVersion.setListener(this.activeConnection, ProtocolState.STATUS);
                break;
            }
            default:
                throw new UnsupportedOperationException("Invalid intention " + packet.getState());
        }
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("core", this.core)
                                                                          .append("activeConnection", this.activeConnection).toString();
    }

    @Override
    public void disconnect(@Nullable ChatMessage disconnectMessage)
    {
//        this.activeConnection.sendPacket(new PacketLoginClientboundDisconnect(baseComponent));
        this.activeConnection.close(disconnectMessage, true);
    }
}