package diorite.impl.connection.listeners;

import diorite.impl.connection.packets.handshake.in.PacketHandshakingInSetProtocol;

public interface PacketHandshakingInListener extends PacketListener
{
    void handle(PacketHandshakingInSetProtocol paramPacketHandshakingInSetProtocol);
}
