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

import org.diorite.impl.connection.packets.PacketListener;
import org.diorite.impl.connection.packets.play.server.PacketPlayServerAbilities;
import org.diorite.impl.connection.packets.play.server.PacketPlayServerBlockChange;
import org.diorite.impl.connection.packets.play.server.PacketPlayServerChat;
import org.diorite.impl.connection.packets.play.server.PacketPlayServerCloseWindow;
import org.diorite.impl.connection.packets.play.server.PacketPlayServerCollect;
import org.diorite.impl.connection.packets.play.server.PacketPlayServerCustomPayload;
import org.diorite.impl.connection.packets.play.server.PacketPlayServerDisconnect;
import org.diorite.impl.connection.packets.play.server.PacketPlayServerEntity;
import org.diorite.impl.connection.packets.play.server.PacketPlayServerEntityDestroy;
import org.diorite.impl.connection.packets.play.server.PacketPlayServerEntityLook;
import org.diorite.impl.connection.packets.play.server.PacketPlayServerEntityMetadata;
import org.diorite.impl.connection.packets.play.server.PacketPlayServerEntityTeleport;
import org.diorite.impl.connection.packets.play.server.PacketPlayServerGameStateChange;
import org.diorite.impl.connection.packets.play.server.PacketPlayServerHeldItemSlot;
import org.diorite.impl.connection.packets.play.server.PacketPlayServerKeepAlive;
import org.diorite.impl.connection.packets.play.server.PacketPlayServerLogin;
import org.diorite.impl.connection.packets.play.server.PacketPlayServerMapChunk;
import org.diorite.impl.connection.packets.play.server.PacketPlayServerMapChunkBulk;
import org.diorite.impl.connection.packets.play.server.PacketPlayServerNamedEntitySpawn;
import org.diorite.impl.connection.packets.play.server.PacketPlayServerOpenWindow;
import org.diorite.impl.connection.packets.play.server.PacketPlayServerPlayerInfo;
import org.diorite.impl.connection.packets.play.server.PacketPlayServerPlayerListHeaderFooter;
import org.diorite.impl.connection.packets.play.server.PacketPlayServerPosition;
import org.diorite.impl.connection.packets.play.server.PacketPlayServerRelEntityMove;
import org.diorite.impl.connection.packets.play.server.PacketPlayServerRelEntityMoveLook;
import org.diorite.impl.connection.packets.play.server.PacketPlayServerResourcePackSend;
import org.diorite.impl.connection.packets.play.server.PacketPlayServerServerDifficulty;
import org.diorite.impl.connection.packets.play.server.PacketPlayServerSetSlot;
import org.diorite.impl.connection.packets.play.server.PacketPlayServerSpawnEntity;
import org.diorite.impl.connection.packets.play.server.PacketPlayServerSpawnEntityLiving;
import org.diorite.impl.connection.packets.play.server.PacketPlayServerSpawnPosition;
import org.diorite.impl.connection.packets.play.server.PacketPlayServerTabComplete;
import org.diorite.impl.connection.packets.play.server.PacketPlayServerTitle;
import org.diorite.impl.connection.packets.play.server.PacketPlayServerTransaction;
import org.diorite.impl.connection.packets.play.server.PacketPlayServerUpdateAttributes;
import org.diorite.impl.connection.packets.play.server.PacketPlayServerWindowItems;
import org.diorite.impl.connection.packets.play.server.PacketPlayServerWorldParticles;

public interface PacketPlayServerListener extends PacketListener
{
    void handle(PacketPlayServerKeepAlive packet);

    void handle(PacketPlayServerLogin packet);

    void handle(PacketPlayServerCustomPayload packet);

    void handle(PacketPlayServerServerDifficulty packet);

    void handle(PacketPlayServerSpawnPosition packet);

    void handle(PacketPlayServerAbilities packet);

    void handle(PacketPlayServerHeldItemSlot packet);

    void handle(PacketPlayServerPosition packet);

    void handle(PacketPlayServerMapChunkBulk packet);

    void handle(PacketPlayServerUpdateAttributes packet);

    void handle(PacketPlayServerChat packet);

    void handle(PacketPlayServerBlockChange packet);

    void handle(PacketPlayServerTabComplete packet);

    void handle(PacketPlayServerDisconnect packet);

    void handle(PacketPlayServerPlayerInfo packet);

    void handle(PacketPlayServerPlayerListHeaderFooter packet);

    void handle(PacketPlayServerTitle packet);

    void handle(PacketPlayServerResourcePackSend packet);

    void handle(PacketPlayServerWorldParticles packet);

    void handle(PacketPlayServerCollect packet);

    void handle(PacketPlayServerSpawnEntity packet);

    void handle(PacketPlayServerEntityMetadata packet);

    void handle(PacketPlayServerGameStateChange packet);

    void handle(PacketPlayServerOpenWindow packet);

    void handle(PacketPlayServerCloseWindow packet);

    void handle(PacketPlayServerTransaction packet);

    void handle(PacketPlayServerSetSlot packet);

    void handle(PacketPlayServerWindowItems packet);

    void handle(PacketPlayServerEntityDestroy packet);

    void handle(PacketPlayServerSpawnEntityLiving packet);

    void handle(PacketPlayServerNamedEntitySpawn packet);

    void handle(PacketPlayServerEntity packet);

    void handle(PacketPlayServerRelEntityMove packet);

    void handle(PacketPlayServerEntityLook packet);

    void handle(PacketPlayServerRelEntityMoveLook packet);

    void handle(PacketPlayServerEntityTeleport packet);

    void handle(PacketPlayServerMapChunk packet);
}