package diorite.impl.connection.listeners;

import diorite.impl.connection.packets.status.out.PacketStatusOutServerInfo;
import diorite.impl.connection.packets.status.out.PacketStatusOutPong;

public abstract interface PacketStatusOutListener extends PacketListener
{
    public abstract void handle(PacketStatusOutServerInfo paramPacketStatusOutServerInfo);

    public abstract void handle(PacketStatusOutPong paramPacketStatusOutPong);
}
