package org.diorite.impl.connection.packets.play;

import org.diorite.impl.connection.packets.PacketListener;
import org.diorite.impl.connection.packets.play.out.PacketPlayOutAbilities;
import org.diorite.impl.connection.packets.play.out.PacketPlayOutBlockChange;
import org.diorite.impl.connection.packets.play.out.PacketPlayOutChat;
import org.diorite.impl.connection.packets.play.out.PacketPlayOutCustomPayload;
import org.diorite.impl.connection.packets.play.out.PacketPlayOutHeldItemSlot;
import org.diorite.impl.connection.packets.play.out.PacketPlayOutKeepAlive;
import org.diorite.impl.connection.packets.play.out.PacketPlayOutLogin;
import org.diorite.impl.connection.packets.play.out.PacketPlayOutMapChunkBulk;
import org.diorite.impl.connection.packets.play.out.PacketPlayOutPosition;
import org.diorite.impl.connection.packets.play.out.PacketPlayOutServerDifficulty;
import org.diorite.impl.connection.packets.play.out.PacketPlayOutSpawnPosition;
import org.diorite.impl.connection.packets.play.out.PacketPlayOutTabComplete;
import org.diorite.impl.connection.packets.play.out.PacketPlayOutUpdateAttributes;

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

    void handle(PacketPlayOutBlockChange packet);

    void handle(PacketPlayOutTabComplete packet);
}