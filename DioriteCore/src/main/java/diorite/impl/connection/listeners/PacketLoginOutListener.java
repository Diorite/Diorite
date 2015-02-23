package diorite.impl.connection.listeners;

import diorite.impl.connection.packets.login.out.PacketLoginOutDisconnect;

public interface PacketLoginOutListener extends PacketListener
{
//    void handle(PacketLoginOutEncryptionBegin packet);

//    void handle(PacketLoginOutSuccess packet);

    void handle(PacketLoginOutDisconnect packet);

//    void handle(PacketLoginOutSetCompression packet);
}