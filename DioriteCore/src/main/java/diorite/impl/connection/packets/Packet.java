package diorite.impl.connection.packets;

import java.io.IOException;

public interface Packet<T extends PacketListener>
{
    void readPacket(PacketDataSerializer data) throws IOException;

    void writePacket(PacketDataSerializer data) throws IOException;

    void handle(T listener);
}