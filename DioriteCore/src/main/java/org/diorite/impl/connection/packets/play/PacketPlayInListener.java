package org.diorite.impl.connection.packets.play;

import org.diorite.impl.connection.packets.PacketListener;
import org.diorite.impl.connection.packets.play.in.PacketPlayInArmAnimation;
import org.diorite.impl.connection.packets.play.in.PacketPlayInBlockDig;
import org.diorite.impl.connection.packets.play.in.PacketPlayInBlockPlace;
import org.diorite.impl.connection.packets.play.in.PacketPlayInChat;
import org.diorite.impl.connection.packets.play.in.PacketPlayInCustomPayload;
import org.diorite.impl.connection.packets.play.in.PacketPlayInEntityAction;
import org.diorite.impl.connection.packets.play.in.PacketPlayInFlying;
import org.diorite.impl.connection.packets.play.in.PacketPlayInHeldItemSlot;
import org.diorite.impl.connection.packets.play.in.PacketPlayInKeepAlive;
import org.diorite.impl.connection.packets.play.in.PacketPlayInLook;
import org.diorite.impl.connection.packets.play.in.PacketPlayInPosition;
import org.diorite.impl.connection.packets.play.in.PacketPlayInPositionLook;
import org.diorite.impl.connection.packets.play.in.PacketPlayInSettings;

public interface PacketPlayInListener extends PacketListener
{
    void handle(PacketPlayInKeepAlive packet);

    void handle(PacketPlayInSettings packet);

    void handle(PacketPlayInCustomPayload packet);

    void handle(PacketPlayInHeldItemSlot packet);

    void handle(PacketPlayInPositionLook packet);

    void handle(PacketPlayInFlying packet);

    void handle(PacketPlayInPosition packet);

    void handle(PacketPlayInLook packet);

    void handle(PacketPlayInChat packet);

    void handle(PacketPlayInEntityAction packet);

    void handle(PacketPlayInArmAnimation packet);

    void handle(PacketPlayInBlockDig packet);

    void handle(PacketPlayInBlockPlace packet);
}