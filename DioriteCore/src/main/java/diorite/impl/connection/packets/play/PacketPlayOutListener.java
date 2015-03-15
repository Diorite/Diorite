package diorite.impl.connection.packets.play;

import diorite.impl.connection.packets.PacketListener;
import diorite.impl.connection.packets.play.out.PacketPlayOutAbilities;
import diorite.impl.connection.packets.play.out.PacketPlayOutChat;
import diorite.impl.connection.packets.play.out.PacketPlayOutCustomPayload;
import diorite.impl.connection.packets.play.out.PacketPlayOutHeldItemSlot;
import diorite.impl.connection.packets.play.out.PacketPlayOutKeepAlive;
import diorite.impl.connection.packets.play.out.PacketPlayOutLogin;
import diorite.impl.connection.packets.play.out.PacketPlayOutMapChunkBulk;
import diorite.impl.connection.packets.play.out.PacketPlayOutPosition;
import diorite.impl.connection.packets.play.out.PacketPlayOutServerDifficulty;
import diorite.impl.connection.packets.play.out.PacketPlayOutSpawnPosition;
import diorite.impl.connection.packets.play.out.PacketPlayOutUpdateAttributes;

public interface PacketPlayOutListener extends PacketListener
{
    void handle(PacketPlayOutKeepAlive packet);

    void handle(PacketPlayOutLogin packet);

    void handle(PacketPlayOutCustomPayload packet);

    void handle(PacketPlayOutServerDifficulty packet);

    void handle(PacketPlayOutSpawnPosition packet);

    void handle(PacketPlayOutAbilities packet);

    void handle(PacketPlayOutHeldItemSlot packet);

    void handle(PacketPlayOutPosition packet);

    void handle(PacketPlayOutMapChunkBulk packet);

    void handle(PacketPlayOutUpdateAttributes packet);

    void handle(PacketPlayOutChat packet);
}