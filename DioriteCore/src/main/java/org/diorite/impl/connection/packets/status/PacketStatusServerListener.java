package org.diorite.impl.connection.packets.status;

import org.diorite.impl.connection.packets.PacketListener;
import org.diorite.impl.connection.packets.status.server.PacketStatusServerPong;
import org.diorite.impl.connection.packets.status.server.PacketStatusServerServerInfo;

public interface PacketStatusServerListener extends PacketListener
{
    void handle(PacketStatusServerServerInfo packet);

    void handle(PacketStatusServerPong packet);
}
