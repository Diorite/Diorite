package diorite.impl.connection.listeners;

import diorite.impl.connection.packets.login.out.PacketLoginOutDisconnect;

public abstract interface PacketLoginOutListener extends PacketListener
{
//    public abstract void a(PacketLoginOutEncryptionBegin paramPacketLoginOutEncryptionBegin);

//    public abstract void a(PacketLoginOutSuccess paramPacketLoginOutSuccess);

    public abstract void handle(PacketLoginOutDisconnect paramPacketLoginOutDisconnect);

//    public abstract void a(PacketLoginOutSetCompression paramPacketLoginOutSetCompression);
}