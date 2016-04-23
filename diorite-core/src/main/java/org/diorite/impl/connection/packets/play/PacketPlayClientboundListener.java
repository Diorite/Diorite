/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2016. Diorite (by Bartłomiej Mazur (aka GotoFinal))
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
import org.diorite.impl.connection.packets.play.clientbound.PacketPlayClientboundAbilities;
import org.diorite.impl.connection.packets.play.clientbound.PacketPlayClientboundBlockChange;
import org.diorite.impl.connection.packets.play.clientbound.PacketPlayClientboundCamera;
import org.diorite.impl.connection.packets.play.clientbound.PacketPlayClientboundChat;
import org.diorite.impl.connection.packets.play.clientbound.PacketPlayClientboundChunkUnload;
import org.diorite.impl.connection.packets.play.clientbound.PacketPlayClientboundCloseWindow;
import org.diorite.impl.connection.packets.play.clientbound.PacketPlayClientboundCollect;
import org.diorite.impl.connection.packets.play.clientbound.PacketPlayClientboundCustomPayload;
import org.diorite.impl.connection.packets.play.clientbound.PacketPlayClientboundDisconnect;
import org.diorite.impl.connection.packets.play.clientbound.PacketPlayClientboundEntity;
import org.diorite.impl.connection.packets.play.clientbound.PacketPlayClientboundEntityDestroy;
import org.diorite.impl.connection.packets.play.clientbound.PacketPlayClientboundEntityHeadRotation;
import org.diorite.impl.connection.packets.play.clientbound.PacketPlayClientboundEntityLook;
import org.diorite.impl.connection.packets.play.clientbound.PacketPlayClientboundEntityMetadata;
import org.diorite.impl.connection.packets.play.clientbound.PacketPlayClientboundEntityTeleport;
import org.diorite.impl.connection.packets.play.clientbound.PacketPlayClientboundGameStateChange;
import org.diorite.impl.connection.packets.play.clientbound.PacketPlayClientboundHeldItemSlot;
import org.diorite.impl.connection.packets.play.clientbound.PacketPlayClientboundKeepAlive;
import org.diorite.impl.connection.packets.play.clientbound.PacketPlayClientboundLogin;
import org.diorite.impl.connection.packets.play.clientbound.PacketPlayClientboundMapChunk;
import org.diorite.impl.connection.packets.play.clientbound.PacketPlayClientboundNamedEntitySpawn;
import org.diorite.impl.connection.packets.play.clientbound.PacketPlayClientboundNamedSoundEffect;
import org.diorite.impl.connection.packets.play.clientbound.PacketPlayClientboundOpenWindow;
import org.diorite.impl.connection.packets.play.clientbound.PacketPlayClientboundPlayerInfo;
import org.diorite.impl.connection.packets.play.clientbound.PacketPlayClientboundPlayerListHeaderFooter;
import org.diorite.impl.connection.packets.play.clientbound.PacketPlayClientboundPosition;
import org.diorite.impl.connection.packets.play.clientbound.PacketPlayClientboundRelEntityMove;
import org.diorite.impl.connection.packets.play.clientbound.PacketPlayClientboundRelEntityMoveLook;
import org.diorite.impl.connection.packets.play.clientbound.PacketPlayClientboundResourcePackSend;
import org.diorite.impl.connection.packets.play.clientbound.PacketPlayClientboundScoreboardDisplayObjective;
import org.diorite.impl.connection.packets.play.clientbound.PacketPlayClientboundSetSlot;
import org.diorite.impl.connection.packets.play.clientbound.PacketPlayClientboundSoundEffect;
import org.diorite.impl.connection.packets.play.clientbound.PacketPlayClientboundSpawnEntity;
import org.diorite.impl.connection.packets.play.clientbound.PacketPlayClientboundSpawnEntityExperienceOrb;
import org.diorite.impl.connection.packets.play.clientbound.PacketPlayClientboundSpawnEntityLiving;
import org.diorite.impl.connection.packets.play.clientbound.PacketPlayClientboundSpawnPosition;
import org.diorite.impl.connection.packets.play.clientbound.PacketPlayClientboundTabComplete;
import org.diorite.impl.connection.packets.play.clientbound.PacketPlayClientboundTitle;
import org.diorite.impl.connection.packets.play.clientbound.PacketPlayClientboundTransaction;
import org.diorite.impl.connection.packets.play.clientbound.PacketPlayClientboundUpdateAttributes;
import org.diorite.impl.connection.packets.play.clientbound.PacketPlayClientboundUpdateTime;
import org.diorite.impl.connection.packets.play.clientbound.PacketPlayClientboundWindowItems;
import org.diorite.impl.connection.packets.play.clientbound.PacketPlayClientboundWorldBorder;
import org.diorite.impl.connection.packets.play.clientbound.PacketPlayClientboundWorldDifficulty;
import org.diorite.impl.connection.packets.play.clientbound.PacketPlayClientboundWorldParticles;

public interface PacketPlayClientboundListener extends PacketPlayListener
{
    void handle(PacketPlayClientboundKeepAlive packet);

    void handle(PacketPlayClientboundLogin packet);

    void handle(PacketPlayClientboundCustomPayload packet);

    void handle(PacketPlayClientboundWorldDifficulty packet);

    void handle(PacketPlayClientboundSpawnPosition packet);

    void handle(PacketPlayClientboundAbilities packet);

    void handle(PacketPlayClientboundHeldItemSlot packet);

    void handle(PacketPlayClientboundPosition packet);

    void handle(PacketPlayClientboundUpdateAttributes packet);

    void handle(PacketPlayClientboundChat packet);

    void handle(PacketPlayClientboundBlockChange packet);

    void handle(PacketPlayClientboundTabComplete packet);

    void handle(PacketPlayClientboundDisconnect packet);

    void handle(PacketPlayClientboundPlayerInfo packet);

    void handle(PacketPlayClientboundPlayerListHeaderFooter packet);

    void handle(PacketPlayClientboundTitle packet);

    void handle(PacketPlayClientboundResourcePackSend packet);

    void handle(PacketPlayClientboundWorldParticles packet);

    void handle(PacketPlayClientboundCollect packet);

    void handle(PacketPlayClientboundSpawnEntity packet);

    void handle(PacketPlayClientboundEntityMetadata packet);

    void handle(PacketPlayClientboundGameStateChange packet);

    void handle(PacketPlayClientboundOpenWindow packet);

    void handle(PacketPlayClientboundCloseWindow packet);

    void handle(PacketPlayClientboundTransaction packet);

    void handle(PacketPlayClientboundSetSlot packet);

    void handle(PacketPlayClientboundWindowItems packet);

    void handle(PacketPlayClientboundEntityDestroy packet);

    void handle(PacketPlayClientboundSpawnEntityLiving packet);

    void handle(PacketPlayClientboundNamedEntitySpawn packet);

    void handle(PacketPlayClientboundEntity packet);

    void handle(PacketPlayClientboundRelEntityMove packet);

    void handle(PacketPlayClientboundEntityLook packet);

    void handle(PacketPlayClientboundRelEntityMoveLook packet);

    void handle(PacketPlayClientboundEntityTeleport packet);

    void handle(PacketPlayClientboundMapChunk packet);

    void handle(PacketPlayClientboundChunkUnload packet);

    void handle(PacketPlayClientboundSoundEffect packet);

    void handle(PacketPlayClientboundNamedSoundEffect packet);

    void handle(PacketPlayClientboundWorldBorder packet);

    void handle(PacketPlayClientboundScoreboardDisplayObjective packet);

    void handle(PacketPlayClientboundUpdateTime packet);

    void handle(PacketPlayClientboundSpawnEntityExperienceOrb packet);

    void handle(PacketPlayClientboundCamera packet);

    void handle(PacketPlayClientboundEntityHeadRotation packet);
}