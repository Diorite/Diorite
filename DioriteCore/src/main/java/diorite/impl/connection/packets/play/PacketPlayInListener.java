package diorite.impl.connection.packets.play;

import diorite.impl.connection.packets.PacketListener;
import diorite.impl.connection.packets.play.in.PacketPlayInKeepAlive;

public interface PacketPlayInListener extends PacketListener
{
    void handle(PacketPlayInKeepAlive packet);
}