package org.diorite.impl.connection.listeners;

import org.diorite.impl.connection.packets.PacketListener;

public interface PacketHandshakeListener extends PacketListener
{
    int CURRENT_PROTOCOL = 47;
}
