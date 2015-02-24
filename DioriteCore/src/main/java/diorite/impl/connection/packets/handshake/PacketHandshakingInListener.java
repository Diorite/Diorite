package diorite.impl.connection.packets.handshake;

import diorite.impl.connection.packets.PacketListener;
import diorite.impl.connection.packets.handshake.in.PacketHandshakingInSetProtocol;

public interface PacketHandshakingInListener extends PacketListener
{
    void handle(PacketHandshakingInSetProtocol packet);
}
