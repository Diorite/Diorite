package org.diorite.impl.connection.packets.status;

import org.diorite.impl.connection.listeners.PacketStatusListener;
import org.diorite.impl.connection.packets.status.client.PacketStatusClientPing;
import org.diorite.impl.connection.packets.status.client.PacketStatusClientStart;

public interface PacketStatusClientListener extends PacketStatusListener
{
    void handle(PacketStatusClientPing packet);

    void handle(PacketStatusClientStart packet);
}

