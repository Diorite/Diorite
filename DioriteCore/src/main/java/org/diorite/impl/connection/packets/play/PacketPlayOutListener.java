package org.diorite.impl.connection.packets.play;

import org.diorite.impl.connection.packets.PacketListener;
import org.diorite.impl.connection.packets.play.out.PacketPlayOutAbilities;
import org.diorite.impl.connection.packets.play.out.PacketPlayOutBlockChange;
import org.diorite.impl.connection.packets.play.out.PacketPlayOutChat;
import org.diorite.impl.connection.packets.play.out.PacketPlayOutCloseWindow;
import org.diorite.impl.connection.packets.play.out.PacketPlayOutCollect;
import org.diorite.impl.connection.packets.play.out.PacketPlayOutCustomPayload;
import org.diorite.impl.connection.packets.play.out.PacketPlayOutDisconnect;
import org.diorite.impl.connection.packets.play.out.PacketPlayOutEntity;
import org.diorite.impl.connection.packets.play.out.PacketPlayOutEntityDestroy;
import org.diorite.impl.connection.packets.play.out.PacketPlayOutEntityLook;
import org.diorite.impl.connection.packets.play.out.PacketPlayOutEntityMetadata;
import org.diorite.impl.connection.packets.play.out.PacketPlayOutEntityTeleport;
import org.diorite.impl.connection.packets.play.out.PacketPlayOutGameStateChange;
import org.diorite.impl.connection.packets.play.out.PacketPlayOutHeldItemSlot;
import org.diorite.impl.connection.packets.play.out.PacketPlayOutKeepAlive;
import org.diorite.impl.connection.packets.play.out.PacketPlayOutLogin;
import org.diorite.impl.connection.packets.play.out.PacketPlayOutMapChunkBulk;
import org.diorite.impl.connection.packets.play.out.PacketPlayOutNamedEntitySpawn;
import org.diorite.impl.connection.packets.play.out.PacketPlayOutOpenWindow;
import org.diorite.impl.connection.packets.play.out.PacketPlayOutPlayerInfo;
import org.diorite.impl.connection.packets.play.out.PacketPlayOutPlayerListHeaderFooter;
import org.diorite.impl.connection.packets.play.out.PacketPlayOutPosition;
import org.diorite.impl.connection.packets.play.out.PacketPlayOutRelEntityMove;
import org.diorite.impl.connection.packets.play.out.PacketPlayOutRelEntityMoveLook;
import org.diorite.impl.connection.packets.play.out.PacketPlayOutResourcePackSend;
import org.diorite.impl.connection.packets.play.out.PacketPlayOutServerDifficulty;
import org.diorite.impl.connection.packets.play.out.PacketPlayOutSetSlot;
import org.diorite.impl.connection.packets.play.out.PacketPlayOutSpawnEntity;
import org.diorite.impl.connection.packets.play.out.PacketPlayOutSpawnEntityLiving;
import org.diorite.impl.connection.packets.play.out.PacketPlayOutSpawnPosition;
import org.diorite.impl.connection.packets.play.out.PacketPlayOutTabComplete;
import org.diorite.impl.connection.packets.play.out.PacketPlayOutTitle;
import org.diorite.impl.connection.packets.play.out.PacketPlayOutTransaction;
import org.diorite.impl.connection.packets.play.out.PacketPlayOutUpdateAttributes;
import org.diorite.impl.connection.packets.play.out.PacketPlayOutWindowItems;
import org.diorite.impl.connection.packets.play.out.PacketPlayOutWorldParticles;

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

    void handle(PacketPlayOutTitle packet);

    void handle(PacketPlayOutResourcePackSend packet);

    void handle(PacketPlayOutWorldParticles packet);

    void handle(PacketPlayOutCollect packet);

    void handle(PacketPlayOutSpawnEntity packet);

    void handle(PacketPlayOutEntityMetadata packet);

    void handle(PacketPlayOutGameStateChange packet);

    void handle(PacketPlayOutOpenWindow packet);

    void handle(PacketPlayOutCloseWindow packet);

    void handle(PacketPlayOutTransaction packet);

    void handle(PacketPlayOutSetSlot packet);

    void handle(PacketPlayOutWindowItems packet);

    void handle(PacketPlayOutEntityDestroy packet);

    void handle(PacketPlayOutSpawnEntityLiving packet);

    void handle(PacketPlayOutNamedEntitySpawn packet);

    void handle(PacketPlayOutEntity packet);

    void handle(PacketPlayOutRelEntityMove packet);

    void handle(PacketPlayOutEntityLook packet);

    void handle(PacketPlayOutRelEntityMoveLook packet);

    void handle(PacketPlayOutEntityTeleport packet);
}