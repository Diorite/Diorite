package diorite.impl.connection.packets;

import java.io.IOException;

import diorite.impl.connection.listeners.PacketListener;

public interface Packet<T extends PacketListener>
{
   void readPacket(PacketDataSerializer data) throws IOException;

   void writePacket(PacketDataSerializer data) throws IOException;

    <E extends T> void  handle(E listener);
}