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

package org.diorite.impl.entity.tracker;

import javax.vecmath.Vector3f;

import java.util.Collection;
import java.util.Iterator;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.CoreMain;
import org.diorite.impl.connection.packets.play.clientbound.PacketPlayClientbound;
import org.diorite.impl.connection.packets.play.clientbound.PacketPlayClientboundEntityMetadata;
import org.diorite.impl.connection.packets.play.clientbound.PacketPlayClientboundEntityTeleport;
import org.diorite.impl.connection.packets.play.clientbound.PacketPlayClientboundRelEntityMoveLook;
import org.diorite.impl.entity.IEntity;
import org.diorite.impl.entity.IPlayer;
import org.diorite.impl.entity.meta.entry.EntityMetadataEntry;
import org.diorite.utils.collections.sets.ConcurrentSet;

@SuppressWarnings({"ObjectEquality"})
public abstract class BaseTracker<T extends IEntity>
{
    protected final T      tracker;
    protected final int    id;
    protected       double xLoc;
    protected       double yLoc;
    protected       double zLoc;
    protected final float  yaw;
    protected final float  pitch;
    protected       float  headRot;
    protected       float  velX;
    protected       float  velY;
    protected       float  velZ;

    protected boolean isMoving;
    protected boolean forceLocationUpdate;

    protected final Collection<IPlayer> tracked = new ConcurrentSet<>(5, .3F, 3);

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
        final Vector3f vel = entity.getVelocity();
        this.velX = vel.x;
        this.velY = vel.y;
        this.velZ = vel.z;
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

    public void tick(final int tps, final Iterable<IPlayer> players)
    {
        if (this.first)
        {
            this.first = false;
            this.xLoc = this.tracker.getX();
            this.yLoc = this.tracker.getY();
            this.zLoc = this.tracker.getZ();
            final Vector3f vel = this.tracker.getVelocity();
            this.velX = vel.x;
            this.velY = vel.y;
            this.velZ = vel.z;
            this.updatePlayers(players);
            this.sendToAllExceptOwn(new PacketPlayClientboundEntityTeleport(this.tracker));
            return;
        }
        double deltaX = 0;
        double deltaY = 0;
        double deltaZ = 0;
        this.isMoving = this.forceLocationUpdate || (this.xLoc != this.tracker.getX()) || (this.yLoc != this.tracker.getY()) || (this.zLoc != this.tracker.getZ());
        if (this.isMoving) // TODO: rethink
        {
            this.forceLocationUpdate = false;
            deltaX = this.tracker.getX() - this.xLoc;
            deltaY = this.tracker.getY() - this.yLoc;
            deltaZ = this.tracker.getZ() - this.zLoc;
            this.xLoc = this.tracker.getX();
            this.yLoc = this.tracker.getY();
            this.zLoc = this.tracker.getZ();
            final Vector3f vel = this.tracker.getVelocity();
            this.velX = vel.x;
            this.velY = vel.y;
            this.velZ = vel.z;
            this.updatePlayers(players);
        }
        if (this.tracker.getId() == - 1)
        {
            this.tracked.forEach(p -> p.removeEntityFromView(this));
        }

        if (this.isMoving && ! this.tracked.isEmpty())
        {
            CoreMain.debug("Updating location.");
            if ((deltaX < 4) && (deltaX > - 4) && (deltaY < 4) && (deltaY > - 4) && (deltaZ < 4) && (deltaZ > - 4))
            {
                this.sendToAllExceptOwn(new PacketPlayClientboundRelEntityMoveLook(this.tracker, deltaX, deltaY, deltaZ));
            }
            else
            {
                this.sendToAllExceptOwn(new PacketPlayClientboundEntityTeleport(this.tracker));
            }
        }

        //if (this.tracker.getHeadPitch() != this.headRot)
        //{
        //    this.headRot = this.tracker.getHeadPitch();
        //    this.sendToAllExceptOwn(new PacketPlayClientboundEntityHeadRotation(this.id, this.headRot));
        //}

        // meta update
        final Collection<EntityMetadataEntry<?>> meta = this.tracker.getMetadata().popOutdatedEntries();
        if ((meta != null) && ! meta.isEmpty())
        {
            this.sendToAll(new PacketPlayClientboundEntityMetadata(this.tracker, meta));
        }
    }

    public void sendToAll(final PacketPlayClientbound packet)
    {
        this.tracked.forEach(p -> p.getNetworkManager().sendPacket(packet));
    }

    public void sendToAllExceptOwn(final PacketPlayClientbound packet)
    {
        this.sendToAll(packet);
    }

    public void sendToAll(final PacketPlayClientbound[] packet)
    {
        this.tracked.forEach(p -> p.getNetworkManager().sendPackets(packet));
    }

    public void sendToAllExceptOwn(final PacketPlayClientbound[] packet)
    {
        this.sendToAll(packet);
    }

    public void updatePlayers(final Iterable<IPlayer> players)
    {
        players.forEach(this::updatePlayer);
    }

    public void updatePlayer(final IPlayer player)
    {
        final int range = this.getTrackRange();
        if (player != this.tracker)
        {
            final double dX = player.getX() - this.xLoc;
            final double dZ = player.getZ() - this.zLoc;
            final boolean isInTrackedRange = ! ((dX < - range) || (dX > range) || (dZ < - range) || (dZ > range));
            final boolean isTracked = this.tracked.contains(player);
            if (isTracked && ! isInTrackedRange)
            {
                this.remove(player);
            }
            else if (! isTracked && isInTrackedRange)
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
        for (final Iterator<IPlayer> iterator = this.tracked.iterator(); iterator.hasNext(); )
        {
            final IPlayer p = iterator.next();
            iterator.remove();
            p.removeEntityFromView(this.tracker);
        }
    }

    public void remove(final IPlayer player)
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
