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
import org.diorite.impl.connection.packets.play.client.PacketPlayClientAbilities;
import org.diorite.impl.connection.packets.play.client.PacketPlayClientArmAnimation;
import org.diorite.impl.connection.packets.play.client.PacketPlayClientBlockDig;
import org.diorite.impl.connection.packets.play.client.PacketPlayClientBlockPlace;
import org.diorite.impl.connection.packets.play.client.PacketPlayClientChat;
import org.diorite.impl.connection.packets.play.client.PacketPlayClientClientCommand;
import org.diorite.impl.connection.packets.play.client.PacketPlayClientCloseWindow;
import org.diorite.impl.connection.packets.play.client.PacketPlayClientCustomPayload;
import org.diorite.impl.connection.packets.play.client.PacketPlayClientEnchantItem;
import org.diorite.impl.connection.packets.play.client.PacketPlayClientEntityAction;
import org.diorite.impl.connection.packets.play.client.PacketPlayClientFlying;
import org.diorite.impl.connection.packets.play.client.PacketPlayClientHeldItemSlot;
import org.diorite.impl.connection.packets.play.client.PacketPlayClientKeepAlive;
import org.diorite.impl.connection.packets.play.client.PacketPlayClientLook;
import org.diorite.impl.connection.packets.play.client.PacketPlayClientPosition;
import org.diorite.impl.connection.packets.play.client.PacketPlayClientPositionLook;
import org.diorite.impl.connection.packets.play.client.PacketPlayClientResourcePackStatus;
import org.diorite.impl.connection.packets.play.client.PacketPlayClientSetCreativeSlot;
import org.diorite.impl.connection.packets.play.client.PacketPlayClientSettings;
import org.diorite.impl.connection.packets.play.client.PacketPlayClientSpectate;
import org.diorite.impl.connection.packets.play.client.PacketPlayClientSteerVehicle;
import org.diorite.impl.connection.packets.play.client.PacketPlayClientTabComplete;
import org.diorite.impl.connection.packets.play.client.PacketPlayClientTransaction;
import org.diorite.impl.connection.packets.play.client.PacketPlayClientUpdateSign;
import org.diorite.impl.connection.packets.play.client.PacketPlayClientUseEntity;
import org.diorite.impl.connection.packets.play.client.PacketPlayClientWindowClick;

public interface PacketPlayClientListener extends PacketPlayListener
{
    void handle(PacketPlayClientKeepAlive packet);

    void handle(PacketPlayClientSettings packet);

    void handle(PacketPlayClientCustomPayload packet);

    void handle(PacketPlayClientHeldItemSlot packet);

    void handle(PacketPlayClientPositionLook packet);

    void handle(PacketPlayClientFlying packet);

    void handle(PacketPlayClientPosition packet);

    void handle(PacketPlayClientLook packet);

    void handle(PacketPlayClientChat packet);

    void handle(PacketPlayClientEntityAction packet);

    void handle(PacketPlayClientArmAnimation packet);

    void handle(PacketPlayClientBlockDig packet);

    void handle(PacketPlayClientBlockPlace packet);

    void handle(PacketPlayClientClientCommand packet);

    void handle(PacketPlayClientCloseWindow packet);

    void handle(PacketPlayClientWindowClick packet);

    void handle(PacketPlayClientTabComplete packet);

    void handle(PacketPlayClientAbilities packet);

    void handle(PacketPlayClientResourcePackStatus packet);

    void handle(PacketPlayClientSetCreativeSlot packet);

    void handle(PacketPlayClientSpectate packet);

    void handle(PacketPlayClientEnchantItem packet);

    void handle(PacketPlayClientSteerVehicle packet);

    void handle(PacketPlayClientTransaction packet);

    void handle(PacketPlayClientUpdateSign packet);

    void handle(PacketPlayClientUseEntity packet);
}