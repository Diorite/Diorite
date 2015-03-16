package org.diorite.impl.connection.packets.login;

import org.diorite.impl.connection.packets.PacketListener;
import org.diorite.impl.connection.packets.login.in.PacketLoginInEncryptionBegin;
import org.diorite.impl.connection.packets.login.in.PacketLoginInStart;

public interface PacketLoginInListener extends PacketListener
{
    void tick();

    void handle(PacketLoginInStart packet);

    void handle(PacketLoginInEncryptionBegin packet);
}