package diorite.impl.connection.listeners;

import diorite.impl.connection.packets.status.in.PacketStatusInPing;
import diorite.impl.connection.packets.status.in.PacketStatusInStart;

public abstract interface PacketStatusInListener extends PacketListener
{
    public abstract void handle(PacketStatusInPing paramPacketStatusInPing);

    public abstract void handle(PacketStatusInStart paramPacketStatusInStart);
}

