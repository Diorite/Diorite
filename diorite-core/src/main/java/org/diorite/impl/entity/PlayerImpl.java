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

package org.diorite.impl.entity;

import java.util.List;
import java.util.Locale;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.DioriteCore;
import org.diorite.impl.auth.GameProfileImpl;
import org.diorite.impl.connection.CoreNetworkManager;
import org.diorite.impl.connection.packets.play.server.PacketPlayServerChat;
import org.diorite.impl.connection.packets.play.server.PacketPlayServerEntityDestroy;
import org.diorite.impl.connection.packets.play.server.PacketPlayServerGameStateChange;
import org.diorite.impl.connection.packets.play.server.PacketPlayServerGameStateChange.ReasonCodes;
import org.diorite.impl.connection.packets.play.server.PacketPlayServerResourcePackSend;
import org.diorite.impl.connection.packets.play.server.PacketPlayServerTabComplete;
import org.diorite.impl.connection.packets.play.server.PacketPlayServerUpdateAttributes;
import org.diorite.impl.connection.packets.play.server.PacketPlayServerWorldParticles;
import org.diorite.impl.entity.tracker.BaseTracker;
import org.diorite.impl.world.chunk.PlayerChunksImpl;
import org.diorite.GameMode;
import org.diorite.ImmutableLocation;
import org.diorite.Particle;
import org.diorite.chat.ChatPosition;
import org.diorite.chat.component.BaseComponent;
import org.diorite.entity.Entity;
import org.diorite.entity.Player;
import org.diorite.event.EventType;
import org.diorite.event.player.PlayerQuitEvent;
import org.diorite.utils.math.DioriteRandom;
import org.diorite.utils.math.DioriteRandomUtils;
import org.diorite.utils.math.endian.BigEndianUtils;

import gnu.trove.TIntCollection;
import gnu.trove.list.array.TIntArrayList;

// TODO: add Human or other entity not fully a player class for bots/npcs
public class PlayerImpl extends HumanImpl implements Player
{
    protected final TIntCollection removeQueue = new TIntArrayList(5, - 1);

    protected final CoreNetworkManager networkManager;
    protected final PlayerChunksImpl   playerChunks;
    protected       byte               viewDistance;
    protected       byte               renderDistance;
    protected       Locale             preferedLocale;

    // TODO: add saving/loading data to/from NBT
    public PlayerImpl(final DioriteCore core, final int id, final GameProfileImpl gameProfile, final CoreNetworkManager networkManager, final ImmutableLocation location)
    {
        super(core, gameProfile, id, location);
        this.networkManager = networkManager;
        this.renderDistance = core.getRenderDistance();
        this.playerChunks = new PlayerChunksImpl(this);
    }

    @Override
    public Locale getPreferedLocale()
    {
        return this.preferedLocale;
    }

    @Override
    public void setPreferedLocale(final Locale locale)
    {
        this.preferedLocale = locale;
    }

    @Override
    public boolean isValidSynchronizable()
    {
        return this.isOnline() && super.isValidSynchronizable();
    }

    @Override
    public void doTick(final int tps)
    {
        super.doTick(tps);
        if (this.playerChunks == null) // sometimes it is null on first tick o.O
        {
            return;
        }
        this.playerChunks.doTick(tps);
        this.inventory.softUpdate();


        // send remove entity packets
        if (! this.removeQueue.isEmpty())
        {
            final int[] ids;
            synchronized (this.removeQueue)
            {
                ids = this.removeQueue.toArray();
                this.removeQueue.clear();
            }
            this.networkManager.sendPacket(new PacketPlayServerEntityDestroy(ids));
        }

        // TODO: maybe don't pickup every tick?
        for (final ItemImpl entity : this.getNearbyEntities(1, 2, 1, ItemImpl.class))
        {
            entity.pickUpItem(this);
        }
    }

    @SuppressWarnings("ObjectEquality")
    public void removeEntityFromView(final BaseTracker<?> e)
    {
        final int id = e.getId();
        if (id == - 1)
        {
            return;
        }
        if ((e.getTracker() instanceof PlayerImpl) || (this.lastTickThread == Thread.currentThread()))
        {
            this.networkManager.sendPacket(new PacketPlayServerEntityDestroy(id));
        }
        else
        {
            this.removeQueue.add(id);
        }
    }

    @SuppressWarnings("ObjectEquality")
    public void removeEntityFromView(final Entity e)
    {
        final int id = e.getId();
        if (id == - 1)
        {
            return;
        }
        if ((e instanceof PlayerImpl) || (this.lastTickThread == Thread.currentThread()))
        {
            this.networkManager.sendPacket(new PacketPlayServerEntityDestroy(id));
        }
        else
        {
            this.removeQueue.add(id);
        }
    }

    public CoreNetworkManager getNetworkManager()
    {
        return this.networkManager;
    }

    public PlayerChunksImpl getPlayerChunks()
    {
        return this.playerChunks;
    }

    public boolean isVisibleChunk(final int x, final int z)
    {
        return this.playerChunks.getVisibleChunks().contains(BigEndianUtils.toLong(x, z));
    }

    @Override
    public void setGameMode(final GameMode gameMode)
    {
        if (this.gameMode.equals(gameMode))
        {
            return;
        }
        super.setGameMode(gameMode);
        this.networkManager.sendPacket(new PacketPlayServerGameStateChange(ReasonCodes.CHANGE_GAME_MODE, gameMode.ordinal()));
    }

    @Override
    public int getPing()
    {
        return this.networkManager.getPing();
    }

    @Override
    public void kick(final BaseComponent s)
    {
        this.core.sync(() -> this.networkManager.close(s, false));
    }

    @Override
    public void sendMessage(final ChatPosition position, final BaseComponent component)
    {
        this.networkManager.sendPacket(new PacketPlayServerChat(component, position));
    }

    @Override
    public void setSprinting(final boolean isSprinting)
    {
        super.setSprinting(isSprinting);
        this.networkManager.sendPacket(new PacketPlayServerUpdateAttributes(0, this.attributes));
    }

    @Override
    public byte getViewDistance()
    {
        return this.viewDistance;
    }

    public void setViewDistance(final byte viewDistance)
    {
        this.viewDistance = viewDistance;
    }

    @Override
    public byte getRenderDistance()
    {
        return this.renderDistance;
    }

    @Override
    public void setRenderDistance(final byte renderDistance)
    {
        this.renderDistance = renderDistance;
    }

    @Override
    public void setResourcePack(final String resourcePack)
    {
        this.networkManager.sendPacket(new PacketPlayServerResourcePackSend(resourcePack, "DIORITE"));
    }

    @Override
    public void setResourcePack(final String resourcePack, final String hash)
    {
        this.networkManager.sendPacket(new PacketPlayServerResourcePackSend(resourcePack, hash));
    }

    @Override
    public void setCanFly(final boolean value)
    {
        super.setCanFly(value);
        this.updateAbilities();
    }

    @Override
    public void setCanFly(final boolean value, final double flySpeed)
    {
        super.setCanFly(value, flySpeed);
        this.updateAbilities();
    }

    @Override
    public void setFlySpeed(final double flySpeed)
    {
        super.setFlySpeed(flySpeed);
        this.updateAbilities();
    }

    @Override
    public void setWalkSpeed(final double walkSpeed)
    {
        super.setWalkSpeed(walkSpeed);
        this.updateAbilities();
    }

    @Override
    public void showParticle(final Particle particle, final boolean isLongDistance, final float x, final float y, final float z, final float offsetX, final float offsetY, final float offsetZ, final float particleData, final int particleCount, final int... data)
    {
        this.networkManager.sendPacket(new PacketPlayServerWorldParticles(particle, isLongDistance, x, y, z, offsetX, offsetY, offsetZ, particleData, particleCount, data));
    }

    private void updateAbilities()
    {
        this.abilities.send(this.networkManager);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("name", this.gameProfile.getName()).append("uuid", this.gameProfile.getId()).toString();
    }

    @Override
    public void sendTabCompletes(final List<String> strs)
    {
        this.networkManager.sendPacket(new PacketPlayServerTabComplete(strs));
    }

    @Override
    public void updateInventory()
    {
        this.inventory.update(this);
    }

    protected final DioriteRandom random = DioriteRandomUtils.newRandom();

    @Override
    public DioriteRandom getRandom()
    {
        return this.random;
    }

    public void onLogout()
    {
        this.remove(true);

        EventType.callEvent(new PlayerQuitEvent(this));
    }

    @Override
    public Player getPlayer()
    {
        return this;
    }
}
