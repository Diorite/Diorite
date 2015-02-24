package diorite.impl.connection.packets.login;

import diorite.impl.connection.packets.PacketListener;
import diorite.impl.connection.packets.login.in.PacketLoginInEncryptionBegin;
import diorite.impl.connection.packets.login.in.PacketLoginInStart;

public interface PacketLoginInListener extends PacketListener
{
    void tick();

    void handle(PacketLoginInStart packet);

    void handle(PacketLoginInEncryptionBegin packet);
}