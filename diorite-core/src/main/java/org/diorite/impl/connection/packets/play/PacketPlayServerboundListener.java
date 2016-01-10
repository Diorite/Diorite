/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 Diorite (by Bartłomiej Mazur (aka GotoFinal))
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package org.diorite.impl.connection.packets.play;

import org.diorite.impl.connection.listeners.PacketPlayListener;
import org.diorite.impl.connection.packets.play.serverbound.PacketPlayServerboundAbilities;
import org.diorite.impl.connection.packets.play.serverbound.PacketPlayServerboundArmAnimation;
import org.diorite.impl.connection.packets.play.serverbound.PacketPlayServerboundBlockDig;
import org.diorite.impl.connection.packets.play.serverbound.PacketPlayServerboundBlockPlace;
import org.diorite.impl.connection.packets.play.serverbound.PacketPlayServerboundChat;
import org.diorite.impl.connection.packets.play.serverbound.PacketPlayServerboundCommand;
import org.diorite.impl.connection.packets.play.serverbound.PacketPlayServerboundCloseWindow;
import org.diorite.impl.connection.packets.play.serverbound.PacketPlayServerboundCustomPayload;
import org.diorite.impl.connection.packets.play.serverbound.PacketPlayServerboundEnchantItem;
import org.diorite.impl.connection.packets.play.serverbound.PacketPlayServerboundEntityAction;
import org.diorite.impl.connection.packets.play.serverbound.PacketPlayServerboundFlying;
import org.diorite.impl.connection.packets.play.serverbound.PacketPlayServerboundHeldItemSlot;
import org.diorite.impl.connection.packets.play.serverbound.PacketPlayServerboundKeepAlive;
import org.diorite.impl.connection.packets.play.serverbound.PacketPlayServerboundLook;
import org.diorite.impl.connection.packets.play.serverbound.PacketPlayServerboundPosition;
import org.diorite.impl.connection.packets.play.serverbound.PacketPlayServerboundPositionLook;
import org.diorite.impl.connection.packets.play.serverbound.PacketPlayServerboundResourcePackStatus;
import org.diorite.impl.connection.packets.play.serverbound.PacketPlayServerboundSetCreativeSlot;
import org.diorite.impl.connection.packets.play.serverbound.PacketPlayServerboundSettings;
import org.diorite.impl.connection.packets.play.serverbound.PacketPlayServerboundSpectate;
import org.diorite.impl.connection.packets.play.serverbound.PacketPlayServerboundSteerVehicle;
import org.diorite.impl.connection.packets.play.serverbound.PacketPlayServerboundTabComplete;
import org.diorite.impl.connection.packets.play.serverbound.PacketPlayServerboundTeleportAccept;
import org.diorite.impl.connection.packets.play.serverbound.PacketPlayServerboundTransaction;
import org.diorite.impl.connection.packets.play.serverbound.PacketPlayServerboundUpdateSign;
import org.diorite.impl.connection.packets.play.serverbound.PacketPlayServerboundUseEntity;
import org.diorite.impl.connection.packets.play.serverbound.PacketPlayServerboundUseItem;
import org.diorite.impl.connection.packets.play.serverbound.PacketPlayServerboundWindowClick;

public interface PacketPlayServerboundListener extends PacketPlayListener
{
    void handle(PacketPlayServerboundKeepAlive packet);

    void handle(PacketPlayServerboundSettings packet);

    void handle(PacketPlayServerboundCustomPayload packet);

    void handle(PacketPlayServerboundHeldItemSlot packet);

    void handle(PacketPlayServerboundPositionLook packet);

    void handle(PacketPlayServerboundFlying packet);

    void handle(PacketPlayServerboundPosition packet);

    void handle(PacketPlayServerboundLook packet);

    void handle(PacketPlayServerboundChat packet);

    void handle(PacketPlayServerboundEntityAction packet);

    void handle(PacketPlayServerboundArmAnimation packet);

    void handle(PacketPlayServerboundBlockDig packet);

    void handle(PacketPlayServerboundBlockPlace packet);

    void handle(PacketPlayServerboundCommand packet);

    void handle(PacketPlayServerboundCloseWindow packet);

    void handle(PacketPlayServerboundWindowClick packet);

    void handle(PacketPlayServerboundTabComplete packet);

    void handle(PacketPlayServerboundAbilities packet);

    void handle(PacketPlayServerboundResourcePackStatus packet);

    void handle(PacketPlayServerboundSetCreativeSlot packet);

    void handle(PacketPlayServerboundSpectate packet);

    void handle(PacketPlayServerboundEnchantItem packet);

    void handle(PacketPlayServerboundSteerVehicle packet);

    void handle(PacketPlayServerboundTransaction packet);

    void handle(PacketPlayServerboundUpdateSign packet);

    void handle(PacketPlayServerboundUseEntity packet);

    void handle(PacketPlayServerboundTeleportAccept packet);

    void handle(PacketPlayServerboundUseItem packet);
}