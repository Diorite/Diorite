package org.diorite.impl.connection.packets.status;

import org.diorite.impl.connection.packets.PacketListener;
import org.diorite.impl.connection.packets.status.in.PacketStatusInPing;
import org.diorite.impl.connection.packets.status.in.PacketStatusInStart;

public interface PacketStatusInListener extends PacketListener
{
    void handle(PacketStatusInPing packet);

    void handle(PacketStatusInStart packet);
}

