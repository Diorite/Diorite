package org.diorite.impl.entity.tracker;

import java.util.Collection;
import java.util.Iterator;

import org.diorite.impl.connection.packets.play.out.PacketPlayOut;
import org.diorite.impl.connection.packets.play.out.PacketPlayOutEntityTeleport;
import org.diorite.impl.connection.packets.play.out.PacketPlayOutRelEntityMoveLook;
import org.diorite.impl.entity.EntityImpl;
import org.diorite.impl.entity.PlayerImpl;
import org.diorite.utils.collections.sets.ConcurrentSet;

@SuppressWarnings({"ObjectEquality", "MagicNumber"})
public abstract class BaseTracker<T extends EntityImpl & Trackable>
{
    protected final T      tracker;
    protected final int    id;
    protected       double xLoc;
    protected       double yLoc;
    protected       double zLoc;
    protected final float  yaw;
    protected final float  pitch;
    protected final float  headRot;

    protected boolean isMoving;

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
    }

    public T getTracker()
    {
        return this.tracker;
    }

    public int getId()
    {
        return this.id;
    }

    private boolean first = true;

    public void tick(final int tps, final Iterable<PlayerImpl> players)
    {
        if (this.first)
        {
            this.first = false;
            this.updatePlayers(players);
            this.sendToAllExceptOwn(new PacketPlayOutEntityTeleport(this.tracker));
            return;
        }
        double deltaX = 0, deltaY = 0, deltaZ = 0;
        this.isMoving = (this.tracker.getLocation().distanceSquared(this.xLoc, this.yLoc, this.zLoc) > 0.1);
        if (this.isMoving)
        {
            deltaX = this.tracker.getX() - this.xLoc;
            deltaY = this.tracker.getY() - this.yLoc;
            deltaZ = this.tracker.getZ() - this.zLoc;
            this.xLoc = this.tracker.getX();
            this.yLoc = this.tracker.getY();
            this.zLoc = this.tracker.getZ();
            this.isMoving = true;
            this.updatePlayers(players);
        }
        if (this.tracker.getId() == - 1)
        {
            this.tracked.forEach(p -> p.removeEntityFromView(this));
        }

        if (this.isMoving && ! this.tracked.isEmpty())
        {
            if ((deltaX < 4) && (deltaX > -4) && (deltaY < 4) && (deltaY > -4)  && (deltaZ < 4) && (deltaZ > -4) )
            {
                this.sendToAllExceptOwn(new PacketPlayOutRelEntityMoveLook(this.tracker, deltaX, deltaY, deltaZ));
            }
            else
            {
                this.sendToAllExceptOwn(new PacketPlayOutEntityTeleport(this.tracker));
            }
        }
    }

    public void sendToAll(final PacketPlayOut packet)
    {
        this.tracked.forEach(p -> p.getNetworkManager().sendPacket(packet));
    }

    public void sendToAllExceptOwn(final PacketPlayOut packet)
    {
        this.sendToAll(packet);
    }

    public void sendToAll(final PacketPlayOut[] packet)
    {
        this.tracked.forEach(p -> p.getNetworkManager().sendPackets(packet));
    }

    public void sendToAllExceptOwn(final PacketPlayOut[] packet)
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

}
