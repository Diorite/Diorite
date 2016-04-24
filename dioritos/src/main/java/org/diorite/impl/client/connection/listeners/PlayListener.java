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

package org.diorite.impl.client.connection.listeners;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.DioriteCore;
import org.diorite.impl.connection.CoreNetworkManager;
import org.diorite.impl.connection.packets.play.PacketPlayClientboundListener;
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
import org.diorite.impl.connection.packets.play.clientbound.PacketPlayClientboundSpawnEntityPainting;
import org.diorite.impl.connection.packets.play.clientbound.PacketPlayClientboundSpawnEntityWeather;
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
import org.diorite.impl.entity.IPlayer;
import org.diorite.chat.component.BaseComponent;

public class PlayListener implements PacketPlayClientboundListener
{
    private final DioriteCore        core;
    private final CoreNetworkManager networkManager;

    public PlayListener(final DioriteCore core, final CoreNetworkManager networkManager)
    {
        this.core = core;
        this.networkManager = networkManager;
    }

    @Override
    public void handle(final PacketPlayClientboundKeepAlive packet)
    {

    }

    @Override
    public void handle(final PacketPlayClientboundLogin packet)
    {

    }

    @Override
    public void handle(final PacketPlayClientboundCustomPayload packet)
    {

    }

    @Override
    public void handle(final PacketPlayClientboundWorldDifficulty packet)
    {

    }

    @Override
    public void handle(final PacketPlayClientboundSpawnPosition packet)
    {

    }

    @Override
    public void handle(final PacketPlayClientboundAbilities packet)
    {

    }

    @Override
    public void handle(final PacketPlayClientboundHeldItemSlot packet)
    {

    }

    @Override
    public void handle(final PacketPlayClientboundPosition packet)
    {

    }

    @Override
    public void handle(final PacketPlayClientboundUpdateAttributes packet)
    {

    }

    @Override
    public void handle(final PacketPlayClientboundChat packet)
    {

    }

    @Override
    public void handle(final PacketPlayClientboundBlockChange packet)
    {

    }

    @Override
    public void handle(final PacketPlayClientboundTabComplete packet)
    {

    }

    @Override
    public void handle(final PacketPlayClientboundDisconnect packet)
    {

    }

    @Override
    public void handle(final PacketPlayClientboundPlayerInfo packet)
    {

    }

    @Override
    public void handle(final PacketPlayClientboundPlayerListHeaderFooter packet)
    {

    }

    @Override
    public void handle(final PacketPlayClientboundTitle packet)
    {

    }

    @Override
    public void handle(final PacketPlayClientboundResourcePackSend packet)
    {

    }

    @Override
    public void handle(final PacketPlayClientboundWorldParticles packet)
    {

    }

    @Override
    public void handle(final PacketPlayClientboundCollect packet)
    {

    }

    @Override
    public void handle(final PacketPlayClientboundSpawnEntity packet)
    {

    }

    @Override
    public void handle(final PacketPlayClientboundEntityMetadata packet)
    {

    }

    @Override
    public void handle(final PacketPlayClientboundGameStateChange packet)
    {

    }

    @Override
    public void handle(final PacketPlayClientboundOpenWindow packet)
    {

    }

    @Override
    public void handle(final PacketPlayClientboundCloseWindow packet)
    {

    }

    @Override
    public void handle(final PacketPlayClientboundTransaction packet)
    {

    }

    @Override
    public void handle(final PacketPlayClientboundSetSlot packet)
    {

    }

    @Override
    public void handle(final PacketPlayClientboundWindowItems packet)
    {

    }

    @Override
    public void handle(final PacketPlayClientboundEntityDestroy packet)
    {

    }

    @Override
    public void handle(final PacketPlayClientboundSpawnEntityLiving packet)
    {

    }

    @Override
    public void handle(final PacketPlayClientboundNamedEntitySpawn packet)
    {

    }

    @Override
    public void handle(final PacketPlayClientboundEntity packet)
    {

    }

    @Override
    public void handle(final PacketPlayClientboundRelEntityMove packet)
    {

    }

    @Override
    public void handle(final PacketPlayClientboundEntityLook packet)
    {

    }

    @Override
    public void handle(final PacketPlayClientboundRelEntityMoveLook packet)
    {

    }

    @Override
    public void handle(final PacketPlayClientboundEntityTeleport packet)
    {

    }

    @Override
    public void handle(final PacketPlayClientboundMapChunk packet)
    {

    }

    @Override
    public void handle(final PacketPlayClientboundChunkUnload packet)
    {

    }

    @Override
    public void handle(final PacketPlayClientboundSoundEffect packet)
    {

    }

    @Override
    public void handle(final PacketPlayClientboundNamedSoundEffect packet)
    {

    }

    @Override
    public void handle(final PacketPlayClientboundWorldBorder packet)
    {

    }

    @Override
    public void handle(final PacketPlayClientboundScoreboardDisplayObjective packet)
    {

    }

    @Override
    public void handle(final PacketPlayClientboundUpdateTime packet)
    {

    }

    @Override
    public void handle(final PacketPlayClientboundSpawnEntityExperienceOrb packet)
    {

    }

    @Override
    public void handle(final PacketPlayClientboundCamera packet)
    {

    }

    @Override
    public void handle(final PacketPlayClientboundEntityHeadRotation packet)
    {

    }

    @Override
    public void handle(final PacketPlayClientboundSpawnEntityWeather packet)
    {

    }

    @Override
    public void handle(final PacketPlayClientboundSpawnEntityPainting packet)
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

    @Override
    public IPlayer getPlayer()
    {
        return null; // TODO
    }
}
