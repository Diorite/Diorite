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

package org.diorite.impl.entity.diorite;

import java.util.UUID;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.DioriteCore;
import org.diorite.impl.connection.packets.play.server.PacketPlayServer;
import org.diorite.impl.connection.packets.play.server.PacketPlayServerCollect;
import org.diorite.impl.connection.packets.play.server.PacketPlayServerEntityMetadata;
import org.diorite.impl.connection.packets.play.server.PacketPlayServerSpawnEntity;
import org.diorite.impl.entity.EntityObject;
import org.diorite.impl.entity.IItem;
import org.diorite.impl.entity.meta.entry.EntityMetadataItemStackEntry;
import org.diorite.impl.entity.tracker.BaseTracker;
import org.diorite.ImmutableLocation;
import org.diorite.entity.EntityType;
import org.diorite.entity.Human;
import org.diorite.inventory.item.ItemStack;
import org.diorite.utils.math.DioriteMathUtils;
import org.diorite.utils.others.NamedUUID;

class ItemImpl extends EntityImpl implements IItem, EntityObject
{
    private static final double JOIN_DISTANCE           = 3; // TODO config.
    private static final int    JOIN_DISTANCE_THRESHOLD = 3; // TODO config.

    private static final int DESPAWN_TIME = 30000; // 5 min, TODO: add config value for that.

    // used for joining items when it moves away.
    private int xLastJoinPos;
    private int yLastJoinPos;
    private int zLastJoinPos;

    private int pickupDelay;

    private NamedUUID thrower;

    ItemImpl(final UUID uuid, final DioriteCore core, final int id, final ImmutableLocation location)
    {
        super(uuid, core, id, location);
        this.setBoundingBox(BASE_SIZE.create(this));
    }

    @Override
    public void initMetadata()
    {
        super.initMetadata();
        this.getMetadata().add(new EntityMetadataItemStackEntry(META_KEY_ITEM, null));
    }

    @Override
    public ItemStack getItemStack()
    {
        return this.getMetadata().getItemStack(META_KEY_ITEM);
    }

    @Override
    public void setItemStack(final ItemStack item)
    {
        this.getMetadata().add(new EntityMetadataItemStackEntry(META_KEY_ITEM, item));
    }

    @Override
    public int getPickupDelay()
    {
        return this.pickupDelay;
    }

    @Override
    public void setPickupDelay(final int pickupDelay)
    {
        this.pickupDelay = pickupDelay;
    }

    @Override
    public boolean canPickup()
    {
        return this.pickupDelay == 0;
    }

    @Override
    public NamedUUID getThrower()
    {
        return this.thrower;
    }

    @Override
    public void setThrower(final NamedUUID thrower)
    {
        this.thrower = thrower;
    }

    @Override
    public boolean pickUpItem(final Human human)
    {
        final ItemStack[] left = human.getInventory().add(this.getItemStack());
        if (left.length != 0)
        {
            this.setItemStack(left[0]);
            return false;
        }

        this.getWorld().getEntityTrackers().getTracker(human).sendToAll(new PacketPlayServerCollect(this.getId(), human.getId()));
        this.remove(true);
        return true;
    }

    private int timeLived = 0; // in centiseconds, 1/100 of second.

    private void updateJoinPos()
    {
        this.xLastJoinPos = (int) this.getX();
        this.yLastJoinPos = (int) this.getY();
        this.zLastJoinPos = (int) this.getZ();
    }

    private void joinNearbyItem(final boolean force)
    {
        if (force || (Math.abs(this.getY() - this.yLastJoinPos) > JOIN_DISTANCE_THRESHOLD) || (Math.abs(this.getX() - this.xLastJoinPos) > JOIN_DISTANCE_THRESHOLD) || (Math.abs(this.getZ() - this.zLastJoinPos) > JOIN_DISTANCE_THRESHOLD))
        {
            this.getNearbyEntities(JOIN_DISTANCE, JOIN_DISTANCE, JOIN_DISTANCE, ItemImpl.class).forEach((item) -> item.joinItem(this));
        }
    }

    private void joinItem(final ItemImpl item)
    {
        //noinspection ObjectEquality
        if (this == item)
        {
            throw new IllegalStateException("Can't join to itself!");
        }
        ItemStack oi = item.getItemStack();
        final ItemStack i = this.getItemStack();
        if ((oi == null) || (i == null) || ! oi.getMaterial().equals(i.getMaterial()))
        {
            return;
        }
        oi = i.combine(oi);
        this.setItemStack(i);
        this.timeLived = 0;
        item.setItemStack(oi);
        item.timeLived = 0;
        this.updateJoinPos();
        if (oi == null)
        {
            item.remove(true);
        }
        else
        {
            item.updateJoinPos();
        }
    }

    @Override
    public void onSpawn(final BaseTracker<?> tracker)
    {
        super.onSpawn(tracker);
        if (this.isAiEnabled())
        {
            this.joinNearbyItem(true);
        }
    }

    @Override
    public void doTick(final int tps)
    {
        super.doTick(tps);
        if (! this.isAiEnabled())
        {
            return;
        }
        if (this.pickupDelay > 0)
        {
            this.pickupDelay -= DioriteMathUtils.centisecondsPerTick(tps);
            if (this.pickupDelay < 0)
            {
                this.pickupDelay = 0;
            }
        }
        this.timeLived += DioriteMathUtils.centisecondsPerTick(tps);
        if (this.timeLived >= DESPAWN_TIME)
        {
            this.remove(true);
            return;
        }
        this.joinNearbyItem(false);
    }

    @Override
    public void doPhysics()
    {
        if ((this.timeLived % 100) == 0)
        {
            this.getTracker().forceLocationUpdate();
        }
        super.doPhysics();
        if (this.isOnGround())
        {
            this.velocityX = 0;
            this.velocityY = 0;
            this.velocityZ = 0;
            return;
        }
        double mod = PHYSIC_GRAVITY_CONST_1;
        final double friction = 0.6;// TODO: add this to block params
        if (this.isOnGround())
        {
            mod = friction * mod;
        }
        this.velocityX *= mod;
        this.velocityZ *= mod;
        this.velocityY *= PHYSIC_GRAVITY_CONST_1;
        this.velocityY -= PHYSIC_GRAVITY_CONST_2;
        if (this.isOnGround())
        {
            this.velocityY *= - 0.5D;
        }
    }

    @Override
    public PacketPlayServer getSpawnPacket()
    {
        return new PacketPlayServerSpawnEntity(this);
    }

    @Override
    public PacketPlayServer[] getSpawnPackets()
    {
        return new PacketPlayServer[]{this.getSpawnPacket(), new PacketPlayServerEntityMetadata(this, true)};
    }

    @Override
    public void setAiEnabled(final boolean aiEnabled)
    {
        super.setAiEnabled(aiEnabled);
//        this.timeLived = -1; not sure about that
    }

    @Override
    public EntityType getType()
    {
        return EntityType.ITEM;
    }

    @Override
    public int getEntityObjectData()
    {
        return 1;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("item", this.getItemStack()).toString();
    }
}
