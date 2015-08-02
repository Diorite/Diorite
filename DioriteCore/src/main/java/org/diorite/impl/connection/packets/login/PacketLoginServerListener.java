package org.diorite.impl.connection.packets.login;

import org.diorite.impl.connection.packets.PacketListener;
import org.diorite.impl.connection.packets.login.server.PacketLoginServerDisconnect;
import org.diorite.impl.connection.packets.login.server.PacketLoginServerEncryptionBegin;
import org.diorite.impl.connection.packets.login.server.PacketLoginServerSetCompression;
import org.diorite.impl.connection.packets.login.server.PacketLoginServerSuccess;

public interface PacketLoginServerListener extends PacketListener
{
    void handle(PacketLoginServerEncryptionBegin packet);

    void handle(PacketLoginServerSuccess packet);

    void handle(PacketLoginServerDisconnect packet);

    void handle(PacketLoginServerSetCompression packet);
}