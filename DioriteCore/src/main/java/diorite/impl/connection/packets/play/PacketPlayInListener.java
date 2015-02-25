package diorite.impl.connection.packets.play;

import diorite.impl.connection.packets.PacketListener;
import diorite.impl.connection.packets.play.in.PacketPlayInChat;
import diorite.impl.connection.packets.play.in.PacketPlayInCustomPayload;
import diorite.impl.connection.packets.play.in.PacketPlayInFlying;
import diorite.impl.connection.packets.play.in.PacketPlayInHeldItemSlot;
import diorite.impl.connection.packets.play.in.PacketPlayInKeepAlive;
import diorite.impl.connection.packets.play.in.PacketPlayInLook;
import diorite.impl.connection.packets.play.in.PacketPlayInPosition;
import diorite.impl.connection.packets.play.in.PacketPlayInPositionLook;
import diorite.impl.connection.packets.play.in.PacketPlayInSettings;

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
}