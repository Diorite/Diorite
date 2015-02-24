package diorite.impl.connection.packets.login;

import diorite.impl.connection.packets.PacketListener;
import diorite.impl.connection.packets.login.out.PacketLoginOutDisconnect;
import diorite.impl.connection.packets.login.out.PacketLoginOutEncryptionBegin;
import diorite.impl.connection.packets.login.out.PacketLoginOutSetCompression;
import diorite.impl.connection.packets.login.out.PacketLoginOutSuccess;

public interface PacketLoginOutListener extends PacketListener
{
    void handle(PacketLoginOutEncryptionBegin packet);

    void handle(PacketLoginOutSuccess packet);

    void handle(PacketLoginOutDisconnect packet);

    void handle(PacketLoginOutSetCompression packet);
}