package org.diorite.impl.server.connection.server;

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
import org.diorite.impl.connection.packets.handshake.PacketHandshakingClientListener;
import org.diorite.impl.connection.packets.handshake.client.PacketHandshakingClientSetProtocol;
import org.diorite.impl.connection.packets.login.server.PacketLoginServerDisconnect;
import org.diorite.chat.component.BaseComponent;
import org.diorite.chat.component.TextComponent;

public class HandshakeListener implements PacketHandshakingClientListener
{
    public static final  int                    CLEANUP_THROTTLE = 200;
    public static final  int                    CURRENT_PROTOCOL = 47;
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
    public void handle(final PacketHandshakingClientSetProtocol packet)
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
                if (packet.getProtocolVersion() > CURRENT_PROTOCOL)
                {
                    this.disconnect(TextComponent.fromLegacyText("Outdated server, we are still on 1.8"));
                }
                else if (packet.getProtocolVersion() < CURRENT_PROTOCOL)
                {
                    this.disconnect(TextComponent.fromLegacyText("Outdated client, we are on 1.8"));
                }
                else
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
        this.networkManager.sendPacket(new PacketLoginServerDisconnect(baseComponent));
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
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("server", this.core).append("networkManager", this.networkManager).toString();
    }
}