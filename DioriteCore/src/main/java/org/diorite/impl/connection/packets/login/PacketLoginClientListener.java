package org.diorite.impl.connection.packets.login;

import org.diorite.impl.connection.listeners.PacketLoginListener;
import org.diorite.impl.connection.packets.login.client.PacketLoginClientEncryptionBegin;
import org.diorite.impl.connection.packets.login.client.PacketLoginClientStart;

public interface PacketLoginClientListener extends PacketLoginListener
{
    void tick();

    void handle(PacketLoginClientStart packet);

    void handle(PacketLoginClientEncryptionBegin packet);
}