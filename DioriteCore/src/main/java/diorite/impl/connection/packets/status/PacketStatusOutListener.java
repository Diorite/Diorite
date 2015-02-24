package diorite.impl.connection.packets.status;

import diorite.impl.connection.packets.PacketListener;
import diorite.impl.connection.packets.status.out.PacketStatusOutServerInfo;
import diorite.impl.connection.packets.status.out.PacketStatusOutPong;

public interface PacketStatusOutListener extends PacketListener
{
    void handle(PacketStatusOutServerInfo packet);

    void handle(PacketStatusOutPong packet);
}
