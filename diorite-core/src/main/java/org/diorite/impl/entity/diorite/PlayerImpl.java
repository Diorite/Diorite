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

package org.diorite.impl.entity.diorite;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.BossBarImpl;
import org.diorite.impl.CoreMain;
import org.diorite.impl.DioriteCore;
import org.diorite.impl.connection.CoreNetworkManager;
import org.diorite.impl.connection.packets.play.clientbound.PacketPlayClientboundChat;
import org.diorite.impl.connection.packets.play.clientbound.PacketPlayClientboundCollect;
import org.diorite.impl.connection.packets.play.clientbound.PacketPlayClientboundEntityDestroy;
import org.diorite.impl.connection.packets.play.clientbound.PacketPlayClientboundGameStateChange;
import org.diorite.impl.connection.packets.play.clientbound.PacketPlayClientboundGameStateChange.ReasonCodes;
import org.diorite.impl.connection.packets.play.clientbound.PacketPlayClientboundOpenWindow;
import org.diorite.impl.connection.packets.play.clientbound.PacketPlayClientboundResourcePackSend;
import org.diorite.impl.connection.packets.play.clientbound.PacketPlayClientboundTabComplete;
import org.diorite.impl.connection.packets.play.clientbound.PacketPlayClientboundUpdateAttributes;
import org.diorite.impl.connection.packets.play.clientbound.PacketPlayClientboundWorldBorder;
import org.diorite.impl.connection.packets.play.clientbound.PacketPlayClientboundWorldParticles;
import org.diorite.impl.entity.IItem;
import org.diorite.impl.entity.IPlayer;
import org.diorite.impl.entity.tracker.BaseTracker;
import org.diorite.impl.input.InputAction;
import org.diorite.impl.input.InputActionType;
import org.diorite.impl.inventory.InventoryViewImpl;
import org.diorite.impl.world.WorldBorderImpl;
import org.diorite.impl.world.WorldImpl;
import org.diorite.impl.world.chunk.ChunkImpl;
import org.diorite.impl.world.chunk.PlayerChunksImpl;
import org.diorite.BossBar;
import org.diorite.GameMode;
import org.diorite.ImmutableLocation;
import org.diorite.Particle;
import org.diorite.auth.GameProfile;
import org.diorite.chat.ChatPosition;
import org.diorite.chat.component.BaseComponent;
import org.diorite.chat.component.TextComponent;
import org.diorite.command.sender.MessageOutput;
import org.diorite.entity.Entity;
import org.diorite.entity.Player;
import org.diorite.event.EventType;
import org.diorite.event.player.PlayerQuitEvent;
import org.diorite.inventory.Inventory;
import org.diorite.utils.math.DioriteRandom;
import org.diorite.utils.math.DioriteRandomUtils;
import org.diorite.utils.math.geometry.LookupShape;

import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntCollection;

// TODO: add Human or other entity not fully a player class for bots/npcs
class PlayerImpl extends HumanImpl implements IPlayer
{
    private final IntCollection           removeQueue = new IntArrayList(5);
    private final Collection<BossBarImpl> bossBars    = new ArrayList<>(5);

    private final CoreNetworkManager networkManager;
    private final PlayerChunksImpl   playerChunks;
    private       byte               viewDistance;
    private       byte               renderDistance;
    private       Locale             preferredLocale;
    private       InventoryViewImpl  inventoryView;

    // TODO: add saving/loading data to/from NBT
    PlayerImpl(final DioriteCore core, final GameProfile gameProfile, final CoreNetworkManager networkManager, final int id, final ImmutableLocation location)
    {
        super(core, gameProfile, id, location);
        this.networkManager = networkManager;
        this.renderDistance = core.getRenderDistance();
        this.playerChunks = new PlayerChunksImpl(this);
        this.messageOutput = new PacketMessageOutput(networkManager);
        this.inventoryView = new InventoryViewImpl(this);

        this.metadata.setBoolean(0, EntityBasicFlags.ACTION, true);
    }

    @Override
    public Locale getPreferredLocale()
    {
        return this.preferredLocale;
    }

    @Override
    public void setPreferredLocale(final Locale locale)
    {
        this.preferredLocale = locale;
    }

    @Override
    public boolean isValidSynchronizable()
    {
        return this.isOnline() && super.isValidSynchronizable();
    }

    @SuppressWarnings("ObjectEquality")
    @Override
    public void updateChunk(final ChunkImpl chunk, final ChunkImpl newChunk)
    {
        super.updateChunk(chunk, newChunk);
        if (! newChunk.isLoaded())
        {
            this.playerChunks.reRun(this.getLocation().getChunkPos());
        }
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
        this.getInventory().softUpdate();


        // send remove entity packets
        if (! this.removeQueue.isEmpty())
        {
            final int[] ids;
            synchronized (this.removeQueue)
            {
                ids = this.removeQueue.toIntArray();
                this.removeQueue.clear();
            }
            this.networkManager.sendPacket(new PacketPlayClientboundEntityDestroy(ids));
        }
    }

    @Override
    public void pickupItems()
    {
        // TODO: maybe don't pickup every tick?
        for (final IItem entity : this.getNearbyEntities(2, 2, 2, IItem.class, LookupShape.RECTANGLE))
        {
            if (entity.canPickup() && entity.pickUpItem(this))
            {
                this.networkManager.sendPacket(new PacketPlayClientboundCollect(entity.getId(), this.getId()));
                entity.remove(true);
            }
        }
    }

    @Override
    @SuppressWarnings("ObjectEquality")
    public void removeEntityFromView(final BaseTracker<?> e)
    {
        final int id = e.getId();
        if (id == - 1)
        {
            return;
        }
        if ((e.getTracker() instanceof IPlayer) || (this.getLastTickThread() == Thread.currentThread()))
        {
            this.networkManager.sendPacket(new PacketPlayClientboundEntityDestroy(id));
        }
        else
        {
            this.removeQueue.add(id);
        }
    }

    @Override
    @SuppressWarnings("ObjectEquality")
    public void removeEntityFromView(final Entity e)
    {
        final int id = e.getId();
        if (id == - 1)
        {
            return;
        }
        if ((e instanceof IPlayer) || (this.getLastTickThread() == Thread.currentThread()))
        {
            this.networkManager.sendPacket(new PacketPlayClientboundEntityDestroy(id));
        }
        else
        {
            this.removeQueue.add(id);
        }
    }

    @Override
    public CoreNetworkManager getNetworkManager()
    {
        return this.networkManager;
    }

    @Override
    public PlayerChunksImpl getPlayerChunks()
    {
        return this.playerChunks;
    }

    @Override
    public boolean isVisibleChunk(final int x, final int z)
    {
        return this.playerChunks.isVisible(x, z);
    }

    @Override
    public void setGameMode(final GameMode gameMode)
    {
        if (this.getGameMode().equals(gameMode))
        {
            return;
        }
        super.setGameMode(gameMode);
        this.networkManager.sendPacket(new PacketPlayClientboundGameStateChange(ReasonCodes.CHANGE_GAME_MODE, gameMode.ordinal()));
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
    public void setSprinting(final boolean isSprinting)
    {
        super.setSprinting(isSprinting);
        this.networkManager.sendPacket(new PacketPlayClientboundUpdateAttributes(0, this.attributes));
    }

    @Override
    public byte getViewDistance()
    {
        return this.viewDistance;
    }

    @Override
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
        this.networkManager.sendPacket(new PacketPlayClientboundResourcePackSend(resourcePack, "DIORITE"));
    }

    @Override
    public void setResourcePack(final String resourcePack, final String hash)
    {
        this.networkManager.sendPacket(new PacketPlayClientboundResourcePackSend(resourcePack, hash));
    }

    @Override
    public void chat(final String line)
    {
        //noinspection HardcodedFileSeparator
        if (line.startsWith("/"))
        {
            this.core.getInputThread().add(new InputAction(line.substring(1), true, this, InputActionType.COMMAND));
        }
        else
        {
            this.core.getInputThread().add(new InputAction(line, false, this, InputActionType.CHAT));
        }
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
        this.networkManager.sendPacket(new PacketPlayClientboundWorldParticles(particle, isLongDistance, x, y, z, offsetX, offsetY, offsetZ, particleData, particleCount, data));
    }

    private void updateAbilities()
    {
        this.getAbilities().setDirty();
        this.getAbilities().send(this.networkManager);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("name", this.getGameProfile().getName()).append("uuid", this.getGameProfile().getId()).toString();
    }

    @Override
    public void openInventory(final Inventory inv)
    {
        Validate.notNull(inv, "Inventory can't be null");
        this.inventoryView = new InventoryViewImpl(this, inv);
        this.networkManager.sendPacket(new PacketPlayClientboundOpenWindow(inv.getWindowId(), inv.getType(), new TextComponent(""), inv.size()));
        CoreMain.debug("Inventory view: " + this.inventoryView);
    }

    @Override
    public InventoryViewImpl getInventoryView()
    {
        return this.inventoryView;
    }

    @Override
    public void closeInventory(final int id)
    {
        CoreMain.debug("Closing inventory with ID " + id);
        if (this.inventoryView.getId() != id)
        {
            CoreMain.debug("InventoryView ID != Close Inventory ID. Sync loosed?");
        }
        super.closeInventory(id);
        this.inventoryView = new InventoryViewImpl(this);
    }

    @Override
    public void sendTabCompletes(final List<String> strs)
    {
        this.networkManager.sendPacket(new PacketPlayClientboundTabComplete(strs));
    }

    @Override
    public void updateInventory()
    {
        this.getInventory().update(this);
    }

    protected final DioriteRandom random = DioriteRandomUtils.newRandom();

    @Override
    public DioriteRandom getRandom()
    {
        return this.random;
    }

    @Override
    public void onLogout()
    {
        this.remove(true);

        EventType.callEvent(new PlayerQuitEvent(this));
    }

    @Override
    public void sendWorldBorderUpdate()
    {
        final WorldBorderImpl wb = this.getWorld().getWorldBorder();
        final PacketPlayClientboundWorldBorder p = new PacketPlayClientboundWorldBorder(wb.getCenter().getX(), wb.getCenter().getZ(), wb.getSize(), wb.getTargetSize(), wb.getTargetSizeReachTime(), wb.getPortalTeleportBoundary(), wb.getWarningTime(), wb.getWarningDistance());
        this.networkManager.sendPacket(p);
    }

    @Override
    public Player getSenderEntity()
    {
        return this;
    }

    @Override
    public void addBossBar(final BossBar bossBar)
    {
        final BossBarImpl bossBarImpl = (BossBarImpl) bossBar;
        this.bossBars.add(bossBarImpl);
        bossBarImpl.addHolder(this);
    }

    @Override
    public void removeBossBar(final BossBar bossBar)
    {
        final BossBarImpl bossBarImpl = (BossBarImpl) bossBar;
        this.bossBars.remove(bossBarImpl);
        bossBarImpl.removeHolder(this);
    }

    @Override
    public Collection<BossBar> getBossBars(final boolean includeParents)
    {
        final Collection<BossBar> temp = new ArrayList<>(this.bossBars);
        if (includeParents)
        {
            temp.addAll(this.getWorld().getBossBars(true));
        }
        return temp;
    }

    @Override
    protected void worldChange(final WorldImpl oldW, final WorldImpl newW)
    {
        super.worldChange(oldW, newW);
        oldW.getBossBars(false).forEach(bossBar -> ((BossBarImpl) bossBar).removeHolder(this));
        newW.getBossBars(false).forEach(bossBar -> ((BossBarImpl) bossBar).addHolder(this));
    }

    private static class PacketMessageOutput implements MessageOutput
    {
        private final CoreNetworkManager networkManager;

        private PacketMessageOutput(final CoreNetworkManager networkManager)
        {
            this.networkManager = networkManager;
        }

        @Override
        public void sendMessage(final ChatPosition position, final BaseComponent component)
        {
            this.networkManager.sendPacket(new PacketPlayClientboundChat(component, position));
        }

        @Override
        public String toString()
        {
            return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("networkManager", this.networkManager).toString();
        }
    }
}