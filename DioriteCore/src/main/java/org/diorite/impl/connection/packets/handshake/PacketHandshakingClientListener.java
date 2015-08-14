package org.diorite.impl.connection.packets.handshake;

import org.diorite.impl.connection.listeners.PacketHandshakeListener;
import org.diorite.impl.connection.packets.handshake.client.PacketHandshakingClientSetProtocol;

public interface PacketHandshakingClientListener extends PacketHandshakeListener
{
    void handle(PacketHandshakingClientSetProtocol packet);
}
