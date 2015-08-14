package org.diorite.impl.connection.listeners;

import org.diorite.impl.connection.packets.PacketListener;
import org.diorite.impl.entity.PlayerImpl;

public interface PacketPlayListener extends PacketListener
{
    PlayerImpl getPlayer();
}
