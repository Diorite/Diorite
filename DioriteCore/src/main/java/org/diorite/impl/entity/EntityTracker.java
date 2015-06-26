package org.diorite.impl.entity;

import java.util.Set;

import org.diorite.utils.collections.sets.ConcurrentSet;
import org.diorite.utils.math.DioriteMathUtils;

@SuppressWarnings({"ObjectEquality", "MagicNumber"})
public class EntityTracker
{
    private final EntityImpl tracker;
    private       int        trackRange;
    private       int        xLoc;
    private       int        yLoc;
    private       int        zLoc;
    private       int        yaw;
    private       int        pitch;
    private       int        headRot;

    private final Set<PlayerImpl> tracked = new ConcurrentSet<>(5, .3F, 3);

    public EntityTracker(final EntityImpl entity, final int trackRange)
    {
        this.tracker = entity;
        this.trackRange = trackRange;
        this.xLoc = DioriteMathUtils.floor(entity.getX() * 32.0D);
        this.yLoc = DioriteMathUtils.floor(entity.getY() * 32.0D);
        this.zLoc = DioriteMathUtils.floor(entity.getZ() * 32.0D);
        this.yaw = DioriteMathUtils.floor((entity.getYaw() * 256.0F) / 360.0F);
        this.pitch = DioriteMathUtils.floor((entity.getPitch() * 256.0F) / 360.0F);
        this.headRot = DioriteMathUtils.floor((entity.getHeadRotation() * 256.0F) / 360.0F);
    }

    public void updatePlayers(final Iterable<PlayerImpl> players)
    {
        players.forEach(this::updatePlayer);
    }

    public void updatePlayer(final PlayerImpl player)
    {
        if (player != this.tracker)
        {
            final double dX = player.getX() - (this.xLoc / 32);
            final double dZ = player.getZ() - (this.zLoc / 32);
            if ((dX >= - this.trackRange) && (dX <= this.trackRange) && (dZ >= - this.trackRange) && (dZ <= this.trackRange))
            {

            }
        }
    }

    @Override
    public boolean equals(final Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (! (o instanceof EntityTracker))
        {
            return false;
        }

        final EntityTracker that = (EntityTracker) o;

        return this.tracker.equals(that.tracker);

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
