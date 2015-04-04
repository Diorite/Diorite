package org.diorite.impl.connection.packets.play;

import org.diorite.impl.connection.packets.PacketListener;
import org.diorite.impl.connection.packets.play.out.*;

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

    void handle(PacketPlayOutDisconnect packet);

    void handle(PacketPlayOutPlayerInfo packet);

    void handle(PacketPlayOutPlayerListHeaderFooter packet);
}