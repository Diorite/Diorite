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

package org.diorite.impl.entity.tracker;

import java.util.Collection;
import java.util.Iterator;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.connection.packets.play.server.PacketPlayServer;
import org.diorite.impl.connection.packets.play.server.PacketPlayServerEntityMetadata;
import org.diorite.impl.connection.packets.play.server.PacketPlayServerEntityTeleport;
import org.diorite.impl.connection.packets.play.server.PacketPlayServerRelEntityMoveLook;
import org.diorite.impl.entity.EntityImpl;
import org.diorite.impl.entity.PlayerImpl;
import org.diorite.impl.entity.meta.entry.EntityMetadataEntry;
import org.diorite.utils.collections.sets.ConcurrentSet;

@SuppressWarnings({"ObjectEquality", "MagicNumber"})
public abstract class BaseTracker<T extends EntityImpl & Trackable>
{
    protected final T      tracker;
    protected final int    id;
    protected       double xLoc;
    protected       double yLoc;
    protected       double zLoc;
    protected       float  yaw;
    protected       float  pitch;
    protected       float  headRot;
    protected       float  velX;
    protected       float  velY;
    protected       float  velZ;

    protected boolean isMoving;
    protected boolean forceLocationUpdate;

    protected final Collection<PlayerImpl> tracked = new ConcurrentSet<>(5, .3F, 3);

    public BaseTracker(final T entity)
    {
        this.tracker = entity;
        this.id = entity.getId();
        this.xLoc = entity.getX();
        this.yLoc = entity.getY();
        this.zLoc = entity.getZ();
        this.yaw = entity.getYaw();
        this.pitch = entity.getPitch();
        this.headRot = entity.getHeadPitch();
        this.velX = entity.getVelocityX();
        this.velY = entity.getVelocityY();
        this.velZ = entity.getVelocityZ();
    }

    public T getTracker()
    {
        return this.tracker;
    }

    public int getId()
    {
        return this.id;
    }

    public void forceLocationUpdate()
    {
        this.forceLocationUpdate = true;
    }

    private boolean first = true;

    public void tick(final int tps, final Iterable<PlayerImpl> players)
    {
        if (this.first)
        {
            this.first = false;
            this.xLoc = this.tracker.getX();
            this.yLoc = this.tracker.getY();
            this.zLoc = this.tracker.getZ();
            this.velX = this.tracker.getVelocityX();
            this.velY = this.tracker.getVelocityY();
            this.velZ = this.tracker.getVelocityZ();
            this.updatePlayers(players);
            this.sendToAllExceptOwn(new PacketPlayServerEntityTeleport(this.tracker));
            return;
        }
        final double deltaX;
        final double deltaY;
        final double deltaZ;
        this.isMoving = this.forceLocationUpdate || (this.velY != 0) || (this.velZ != 0) || (this.velX != 0);
//        if (this.isMoving) TODO: readd
        {
            this.forceLocationUpdate = false;
            deltaX = this.tracker.getX() - this.xLoc;
            deltaY = this.tracker.getY() - this.yLoc;
            deltaZ = this.tracker.getZ() - this.zLoc;
            this.xLoc = this.tracker.getX();
            this.yLoc = this.tracker.getY();
            this.zLoc = this.tracker.getZ();
            this.velX = this.tracker.getVelocityX();
            this.velY = this.tracker.getVelocityY();
            this.velZ = this.tracker.getVelocityZ();
            this.updatePlayers(players);
        }
        if (this.tracker.getId() == - 1)
        {
            this.tracked.forEach(p -> p.removeEntityFromView(this));
        }

//        if (this.isMoving && ! this.tracked.isEmpty()) TODO: readd
        {
            if ((deltaX < 4) && (deltaX > - 4) && (deltaY < 4) && (deltaY > - 4) && (deltaZ < 4) && (deltaZ > - 4))
            {
                this.sendToAllExceptOwn(new PacketPlayServerRelEntityMoveLook(this.tracker, deltaX, deltaY, deltaZ));
            }
            else
            {
                this.sendToAllExceptOwn(new PacketPlayServerEntityTeleport(this.tracker));
            }
        }

        // meta update
        final Collection<EntityMetadataEntry<?>> meta = this.tracker.getMetadata().popOutdatedEntries();
        if ((meta != null) && ! meta.isEmpty())
        {
            this.sendToAll(new PacketPlayServerEntityMetadata(this.tracker, meta));
        }

    }

    public void sendToAll(final PacketPlayServer packet)
    {
        this.tracked.forEach(p -> p.getNetworkManager().sendPacket(packet));
    }

    public void sendToAllExceptOwn(final PacketPlayServer packet)
    {
        this.sendToAll(packet);
    }

    public void sendToAll(final PacketPlayServer[] packet)
    {
        this.tracked.forEach(p -> p.getNetworkManager().sendPackets(packet));
    }

    public void sendToAllExceptOwn(final PacketPlayServer[] packet)
    {
        this.sendToAll(packet);
    }

    public void updatePlayers(final Iterable<PlayerImpl> players)
    {
        players.forEach(this::updatePlayer);
    }

    public void updatePlayer(final PlayerImpl player)
    {
        final int range = this.getTrackRange();
        if (player != this.tracker)
        {
            final double dX = player.getX() - (this.xLoc / 32);
            final double dZ = player.getZ() - (this.zLoc / 32);
            if (((dX < - range) || (dX > range) || (dZ < - range) || (dZ > range)))
            {
                this.remove(player);
                return;
            }
            if (! this.tracked.contains(player))
            {
                this.tracked.add(player);
                this.spawn();
            }
        }
    }

    public void spawn()
    {
        this.sendToAllExceptOwn(this.tracker.getSpawnPackets());
    }

    public int getTrackRange()
    {
        return this.tracker.getTrackRange();
    }

    @Override
    public boolean equals(final Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (! (o instanceof BaseTracker))
        {
            return false;
        }

        final BaseTracker<?> that = (BaseTracker<?>) o;

        return this.tracker.equals(that.tracker);

    }

    public void despawn()
    {
        for (final Iterator<PlayerImpl> iterator = this.tracked.iterator(); iterator.hasNext(); )
        {
            final PlayerImpl p = iterator.next();
            iterator.remove();
            p.removeEntityFromView(this.tracker);
        }
    }

    public void remove(final PlayerImpl player)
    {
        if (this.tracked.remove(player))
        {
            player.removeEntityFromView(this.tracker);
        }
    }

    @Override
    public int hashCode()
    {
        return this.tracker.hashCode();
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("tracker", this.tracker).append("id", this.id).toString();
    }
}
