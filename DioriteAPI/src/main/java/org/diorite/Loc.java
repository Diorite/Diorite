package org.diorite;

import org.diorite.map.World;
import org.diorite.map.chunk.ChunkPos;
import org.diorite.utils.math.DioriteMathUtils;

public abstract class Loc
{
    public abstract double getX();

    public abstract double getY();

    public abstract double getZ();

    public abstract float getPitch();

    public abstract float getYaw();

    public abstract World getWorld();

    public double length()
    {
        return Math.sqrt(this.lengthSquared());
    }

    public double lengthSquared()
    {
        return DioriteMathUtils.square(this.getX()) + DioriteMathUtils.square(this.getY()) + DioriteMathUtils.square(this.getZ());
    }

    public double distance(final double x, final double y, final double z)
    {
        return Math.sqrt(this.distanceSquared(x, y, z));
    }

    public double distanceFromCenter(final double x, final double y, final double z)
    {
        return Math.sqrt(this.distanceSquaredFromCenter(x, y, z));
    }

    public double distance(final Loc location)
    {
        return Math.sqrt(this.distanceSquared(location));
    }

    public double distance(final BlockLocation location)
    {
        return Math.sqrt(this.distanceSquared(location));
    }

    public double distanceSquared(final double x, final double y, final double z)
    {
        final double deltaX = this.getX() - x;
        final double deltaY = this.getY() - y;
        final double deltaZ = this.getZ() - z;
        return DioriteMathUtils.square(deltaX) + DioriteMathUtils.square(deltaY) + DioriteMathUtils.square(deltaZ);
    }

    @SuppressWarnings("MagicNumber")
    public double distanceSquaredFromCenter(final double x, final double y, final double z)
    {
        final double deltaX = (this.getX() + 0.5) - x;
        final double deltaY = (this.getY() + 0.5) - y;
        final double deltaZ = (this.getZ() + 0.5) - z;
        return DioriteMathUtils.square(deltaX) + DioriteMathUtils.square(deltaY) + DioriteMathUtils.square(deltaZ);
    }

    public double distanceSquared(final Loc location)
    {
        return this.distanceSquared(location.getX(), location.getY(), location.getZ());
    }

    public abstract Loc crossProduct(Loc location);

    public boolean isInAABB(final Loc min, final Loc max)
    {
        return (this.getX() >= min.getX()) && (this.getX() <= max.getX()) && (this.getY() >= min.getY()) && (this.getY() <= max.getY()) && (this.getZ() >= min.getZ()) && (this.getZ() <= max.getZ());
    }

    public boolean isInSphere(final Loc origin, final double radius)
    {
        return (DioriteMathUtils.square(origin.getX() - this.getX()) + DioriteMathUtils.square(origin.getY() - this.getY()) + DioriteMathUtils.square(origin.getZ() - this.getZ())) <= DioriteMathUtils.square(radius);
    }

    public double distanceSquared(final BlockLocation location)
    {
        return this.distanceSquared(location.getX(), location.getY(), location.getZ());
    }

    public abstract Loc crossProduct(BlockLocation location);

    public boolean isInAABB(final BlockLocation min, final BlockLocation max)
    {
        return (this.getX() >= min.getX()) && (this.getX() <= max.getX()) && (this.getY() >= min.getY()) && (this.getY() <= max.getY()) && (this.getZ() >= min.getZ()) && (this.getZ() <= max.getZ());
    }

    public boolean isInSphere(final BlockLocation origin, final double radius)
    {
        return (DioriteMathUtils.square(origin.getX() - this.getX()) + DioriteMathUtils.square(origin.getY() - this.getY()) + DioriteMathUtils.square(origin.getZ() - this.getZ())) <= DioriteMathUtils.square(radius);
    }

    public ChunkPos getChunkPos()
    {
        return new ChunkPos((int) this.getX() >> 4, (int) this.getZ() >> 4, this.getWorld());
    }

    public BlockLocation toBlockLocation()
    {
        return new BlockLocation((int) this.getX(), (int) this.getY(), (int) this.getZ());
    }

    public Location toLocation()
    {
        return new Location(this.getX(), this.getY(), this.getZ(), this.getYaw(), this.getPitch());
    }

    public ImmutableLocation toImmutableLocation()
    {
        return new ImmutableLocation(this.getX(), this.getY(), this.getZ(), this.getYaw(), this.getPitch());
    }
}
