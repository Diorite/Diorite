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

package org.diorite.impl.client.connection.listeners;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.DioriteCore;
import org.diorite.impl.connection.CoreNetworkManager;
import org.diorite.impl.connection.packets.play.PacketPlayServerListener;
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
import org.diorite.chat.component.BaseComponent;

public class PlayListener implements PacketPlayServerListener
{
    private final DioriteCore        core;
    private final CoreNetworkManager networkManager;

    public PlayListener(final DioriteCore core, final CoreNetworkManager networkManager)
    {
        this.core = core;
        this.networkManager = networkManager;
    }

    @Override
    public void handle(final PacketPlayServerKeepAlive packet)
    {

    }

    @Override
    public void handle(final PacketPlayServerLogin packet)
    {

    }

    @Override
    public void handle(final PacketPlayServerCustomPayload packet)
    {

    }

    @Override
    public void handle(final PacketPlayServerServerDifficulty packet)
    {

    }

    @Override
    public void handle(final PacketPlayServerSpawnPosition packet)
    {

    }

    @Override
    public void handle(final PacketPlayServerAbilities packet)
    {

    }

    @Override
    public void handle(final PacketPlayServerHeldItemSlot packet)
    {

    }

    @Override
    public void handle(final PacketPlayServerPosition packet)
    {

    }

    @Override
    public void handle(final PacketPlayServerMapChunkBulk packet)
    {

    }

    @Override
    public void handle(final PacketPlayServerUpdateAttributes packet)
    {

    }

    @Override
    public void handle(final PacketPlayServerChat packet)
    {

    }

    @Override
    public void handle(final PacketPlayServerBlockChange packet)
    {

    }

    @Override
    public void handle(final PacketPlayServerTabComplete packet)
    {

    }

    @Override
    public void handle(final PacketPlayServerDisconnect packet)
    {

    }

    @Override
    public void handle(final PacketPlayServerPlayerInfo packet)
    {

    }

    @Override
    public void handle(final PacketPlayServerPlayerListHeaderFooter packet)
    {

    }

    @Override
    public void handle(final PacketPlayServerTitle packet)
    {

    }

    @Override
    public void handle(final PacketPlayServerResourcePackSend packet)
    {

    }

    @Override
    public void handle(final PacketPlayServerWorldParticles packet)
    {

    }

    @Override
    public void handle(final PacketPlayServerCollect packet)
    {

    }

    @Override
    public void handle(final PacketPlayServerSpawnEntity packet)
    {

    }

    @Override
    public void handle(final PacketPlayServerEntityMetadata packet)
    {

    }

    @Override
    public void handle(final PacketPlayServerGameStateChange packet)
    {

    }

    @Override
    public void handle(final PacketPlayServerOpenWindow packet)
    {

    }

    @Override
    public void handle(final PacketPlayServerCloseWindow packet)
    {

    }

    @Override
    public void handle(final PacketPlayServerTransaction packet)
    {

    }

    @Override
    public void handle(final PacketPlayServerSetSlot packet)
    {

    }

    @Override
    public void handle(final PacketPlayServerWindowItems packet)
    {

    }

    @Override
    public void handle(final PacketPlayServerEntityDestroy packet)
    {

    }

    @Override
    public void handle(final PacketPlayServerSpawnEntityLiving packet)
    {

    }

    @Override
    public void handle(final PacketPlayServerNamedEntitySpawn packet)
    {

    }

    @Override
    public void handle(final PacketPlayServerEntity packet)
    {

    }

    @Override
    public void handle(final PacketPlayServerRelEntityMove packet)
    {

    }

    @Override
    public void handle(final PacketPlayServerEntityLook packet)
    {

    }

    @Override
    public void handle(final PacketPlayServerRelEntityMoveLook packet)
    {

    }

    @Override
    public void handle(final PacketPlayServerEntityTeleport packet)
    {

    }

    @Override
    public void handle(final PacketPlayServerMapChunk packet)
    {

    }

    @Override
    public void disconnect(final BaseComponent message)
    {

    }

    @Override
    public DioriteCore getCore()
    {
        return this.core;
    }

    public CoreNetworkManager getNetworkManager()
    {
        return this.networkManager;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("core", this.core).append("networkManager", this.networkManager).toString();
    }
}
