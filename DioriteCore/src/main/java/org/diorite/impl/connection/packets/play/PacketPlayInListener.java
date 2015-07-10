package org.diorite.impl.connection.packets.play;

import org.diorite.impl.connection.packets.PacketListener;
import org.diorite.impl.connection.packets.play.in.PacketPlayInAbilities;
import org.diorite.impl.connection.packets.play.in.PacketPlayInArmAnimation;
import org.diorite.impl.connection.packets.play.in.PacketPlayInBlockDig;
import org.diorite.impl.connection.packets.play.in.PacketPlayInBlockPlace;
import org.diorite.impl.connection.packets.play.in.PacketPlayInChat;
import org.diorite.impl.connection.packets.play.in.PacketPlayInClientCommand;
import org.diorite.impl.connection.packets.play.in.PacketPlayInCloseWindow;
import org.diorite.impl.connection.packets.play.in.PacketPlayInCustomPayload;
import org.diorite.impl.connection.packets.play.in.PacketPlayInEnchantItem;
import org.diorite.impl.connection.packets.play.in.PacketPlayInEntityAction;
import org.diorite.impl.connection.packets.play.in.PacketPlayInFlying;
import org.diorite.impl.connection.packets.play.in.PacketPlayInHeldItemSlot;
import org.diorite.impl.connection.packets.play.in.PacketPlayInKeepAlive;
import org.diorite.impl.connection.packets.play.in.PacketPlayInLook;
import org.diorite.impl.connection.packets.play.in.PacketPlayInPosition;
import org.diorite.impl.connection.packets.play.in.PacketPlayInPositionLook;
import org.diorite.impl.connection.packets.play.in.PacketPlayInResourcePackStatus;
import org.diorite.impl.connection.packets.play.in.PacketPlayInSetCreativeSlot;
import org.diorite.impl.connection.packets.play.in.PacketPlayInSettings;
import org.diorite.impl.connection.packets.play.in.PacketPlayInSpectate;
import org.diorite.impl.connection.packets.play.in.PacketPlayInSteerVehicle;
import org.diorite.impl.connection.packets.play.in.PacketPlayInTabComplete;
import org.diorite.impl.connection.packets.play.in.PacketPlayInTransaction;
import org.diorite.impl.connection.packets.play.in.PacketPlayInUpdateSign;
import org.diorite.impl.connection.packets.play.in.PacketPlayInWindowClick;

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

    void handle(PacketPlayInWindowClick packet);

    void handle(PacketPlayInTabComplete packet);

    void handle(PacketPlayInAbilities packet);

    void handle(PacketPlayInResourcePackStatus packet);

    void handle(PacketPlayInSetCreativeSlot packet);

    void handle(PacketPlayInSpectate packet);

    void handle(PacketPlayInEnchantItem packet);

    void handle(PacketPlayInSteerVehicle packet);

    void handle(PacketPlayInTransaction packet);

    void handle(PacketPlayInUpdateSign packet);
}