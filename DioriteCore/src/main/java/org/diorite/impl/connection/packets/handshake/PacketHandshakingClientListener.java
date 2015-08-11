package org.diorite.impl.connection.packets.handshake;

import org.diorite.impl.connection.packets.PacketListener;
import org.diorite.impl.connection.packets.handshake.client.PacketHandshakingClientSetProtocol;

public interface PacketHandshakingClientListener extends PacketListener
{
    void handle(PacketHandshakingClientSetProtocol packet);
}
