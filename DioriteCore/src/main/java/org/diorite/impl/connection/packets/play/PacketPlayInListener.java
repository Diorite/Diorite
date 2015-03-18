package org.diorite.impl.connection.packets.play;

import org.diorite.impl.connection.packets.PacketListener;
import org.diorite.impl.connection.packets.play.in.*;

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

    void handle(PacketPlayInClientCommand packet);

    void handle(PacketPlayInCloseWindow packet);
}