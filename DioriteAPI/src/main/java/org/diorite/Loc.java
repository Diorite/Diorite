package org.diorite;

import org.diorite.utils.math.DioriteMathUtils;
import org.diorite.world.World;
import org.diorite.world.chunk.Chunk;
import org.diorite.world.chunk.ChunkPos;

public interface Loc
{
    double getX();

    double getY();

    double getZ();

    float getPitch();

    float getYaw();

    World getWorld();

    default Chunk getChunk()
    {
        return this.getWorld().getChunkAt(this.getChunkPos());
    }

    default double length()
    {
        return Math.sqrt(this.lengthSquared());
    }

    default double lengthSquared()
    {
        return DioriteMathUtils.square(this.getX()) + DioriteMathUtils.square(this.getY()) + DioriteMathUtils.square(this.getZ());
    }

    default double distance(final double x, final double y, final double z)
    {
        return Math.sqrt(this.distanceSquared(x, y, z));
    }

    default double distanceFromCenter(final double x, final double y, final double z)
    {
        return Math.sqrt(this.distanceSquaredFromCenter(x, y, z));
    }

    default double distance(final Loc location)
    {
        return Math.sqrt(this.distanceSquared(location));
    }

    default double distance(final BlockLocation location)
    {
        return Math.sqrt(this.distanceSquared(location));
    }

    default double distanceSquared(final double x, final double y, final double z)
    {
        final double deltaX = this.getX() - x;
        final double deltaY = this.getY() - y;
        final double deltaZ = this.getZ() - z;
        return DioriteMathUtils.square(deltaX) + DioriteMathUtils.square(deltaY) + DioriteMathUtils.square(deltaZ);
    }

    @SuppressWarnings("MagicNumber")
    default double distanceSquaredFromCenter(final double x, final double y, final double z)
    {
        final double deltaX = (this.getX() + 0.5) - x;
        final double deltaY = (this.getY() + 0.5) - y;
        final double deltaZ = (this.getZ() + 0.5) - z;
        return DioriteMathUtils.square(deltaX) + DioriteMathUtils.square(deltaY) + DioriteMathUtils.square(deltaZ);
    }

    default double distanceSquared(final Loc location)
    {
        return this.distanceSquared(location.getX(), location.getY(), location.getZ());
    }

    Loc crossProduct(Loc location);

    default boolean isInAABB(final Loc min, final Loc max)
    {
        return (this.getX() >= min.getX()) && (this.getX() <= max.getX()) && (this.getY() >= min.getY()) && (this.getY() <= max.getY()) && (this.getZ() >= min.getZ()) && (this.getZ() <= max.getZ());
    }

    default boolean isInSphere(final Loc origin, final double radius)
    {
        return (DioriteMathUtils.square(origin.getX() - this.getX()) + DioriteMathUtils.square(origin.getY() - this.getY()) + DioriteMathUtils.square(origin.getZ() - this.getZ())) <= DioriteMathUtils.square(radius);
    }

    default double distanceSquared(final BlockLocation location)
    {
        return this.distanceSquared(location.getX(), location.getY(), location.getZ());
    }

    Loc crossProduct(BlockLocation location);

    default boolean isInAABB(final BlockLocation min, final BlockLocation max)
    {
        return (this.getX() >= min.getX()) && (this.getX() <= max.getX()) && (this.getY() >= min.getY()) && (this.getY() <= max.getY()) && (this.getZ() >= min.getZ()) && (this.getZ() <= max.getZ());
    }

    default boolean isInSphere(final BlockLocation origin, final double radius)
    {
        return (DioriteMathUtils.square(origin.getX() - this.getX()) + DioriteMathUtils.square(origin.getY() - this.getY()) + DioriteMathUtils.square(origin.getZ() - this.getZ())) <= DioriteMathUtils.square(radius);
    }

    default ChunkPos getChunkPos()
    {
        return new ChunkPos((int) this.getX() >> 4, (int) this.getZ() >> 4, this.getWorld());
    }

    default BlockLocation toBlockLocation()
    {
        return new BlockLocation(DioriteMathUtils.floor(this.getX()), DioriteMathUtils.floor(this.getY()), DioriteMathUtils.floor(this.getZ()), this.getWorld());
    }

    default Location toLocation()
    {
        return new Location(this.getX(), this.getY(), this.getZ(), this.getYaw(), this.getPitch(), this.getWorld());
    }

    default ImmutableLocation toImmutableLocation()
    {
        return new ImmutableLocation(this.getX(), this.getY(), this.getZ(), this.getYaw(), this.getPitch(), this.getWorld());
    }
}
