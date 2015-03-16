package org.diorite.impl.connection.packets.handshake;

import org.diorite.impl.connection.packets.PacketListener;
import org.diorite.impl.connection.packets.handshake.in.PacketHandshakingInSetProtocol;

public interface PacketHandshakingInListener extends PacketListener
{
    void handle(PacketHandshakingInSetProtocol packet);
}
