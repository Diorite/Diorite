package org.diorite.impl.connection.packets.login;

import org.diorite.impl.connection.packets.PacketListener;
import org.diorite.impl.connection.packets.login.out.PacketLoginOutDisconnect;
import org.diorite.impl.connection.packets.login.out.PacketLoginOutEncryptionBegin;
import org.diorite.impl.connection.packets.login.out.PacketLoginOutSetCompression;
import org.diorite.impl.connection.packets.login.out.PacketLoginOutSuccess;

public interface PacketLoginOutListener extends PacketListener
{
    void handle(PacketLoginOutEncryptionBegin packet);

    void handle(PacketLoginOutSuccess packet);

    void handle(PacketLoginOutDisconnect packet);

    void handle(PacketLoginOutSetCompression packet);
}