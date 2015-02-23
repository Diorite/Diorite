package diorite.impl.connection.listeners;

import diorite.impl.connection.packets.status.out.PacketStatusOutServerInfo;
import diorite.impl.connection.packets.status.out.PacketStatusOutPong;

public interface PacketStatusOutListener extends PacketListener
{
    void handle(PacketStatusOutServerInfo packet);

    void handle(PacketStatusOutPong packet);
}
