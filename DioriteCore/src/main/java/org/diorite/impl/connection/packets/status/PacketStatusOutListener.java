package org.diorite.impl.connection.packets.status;

import org.diorite.impl.connection.packets.PacketListener;
import org.diorite.impl.connection.packets.status.out.PacketStatusOutPong;
import org.diorite.impl.connection.packets.status.out.PacketStatusOutServerInfo;

public interface PacketStatusOutListener extends PacketListener
{
    void handle(PacketStatusOutServerInfo packet);

    void handle(PacketStatusOutPong packet);
}
