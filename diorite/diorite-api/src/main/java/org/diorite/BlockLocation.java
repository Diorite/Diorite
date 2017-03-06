/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017. Diorite (by Bartłomiej Mazur (aka GotoFinal))
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

package org.diorite;

import javax.annotation.Nullable;
import javax.vecmath.Vector3d;

import java.util.Objects;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.block.Block;
import org.diorite.commons.math.DioriteMathUtils;
import org.diorite.world.World;
import org.diorite.world.chunk.Chunk;
import org.diorite.world.chunk.ChunkPosition;

/**
 * Represents location of some block.
 */
@SuppressWarnings("MagicNumber")
public final class BlockLocation
{
    public static final BlockLocation ZERO = new BlockLocation(0, 0, 0);
    final           int   x;
    final           int   y;
    final           int   z;
    final @Nullable World world;

    /**
     * Create new block location instance for given coordinates.
     *
     * @param x
     *         one of coordinates.
     * @param z
     *         one of coordinates.
     * @param y
     *         one of coordinates.
     */
    public BlockLocation(int x, int y, int z)
    {
        this.x = x;
        this.y = y;
        this.z = z;
        this.world = null;
    }

    /**
     * Create new block location instance for given coordinates.
     *
     * @param x
     *         one of coordinates.
     * @param z
     *         one of coordinates.
     * @param y
     *         one of coordinates.
     * @param world
     *         world of location.
     */
    public BlockLocation(int x, int y, int z, @Nullable World world)
    {
        this.x = x;
        this.y = y;
        this.z = z;
        this.world = world;
    }

    /**
     * Create new block location instance for given coordinates.
     *
     * @param x
     *         one of coordinates.
     * @param z
     *         one of coordinates.
     * @param y
     *         one of coordinates.
     */
    public BlockLocation(double x, double y, double z)
    {
        this.x = DioriteMathUtils.floor(x);
        this.y = DioriteMathUtils.floor(y);
        this.z = DioriteMathUtils.floor(z);
        this.world = null;
    }

    /**
     * Create new block location instance for given coordinates.
     *
     * @param x
     *         one of coordinates.
     * @param z
     *         one of coordinates.
     * @param y
     *         one of coordinates.
     * @param world
     *         world of location.
     */
    public BlockLocation(double x, double y, double z, @Nullable World world)
    {
        this.x = DioriteMathUtils.floor(x);
        this.y = DioriteMathUtils.floor(y);
        this.z = DioriteMathUtils.floor(z);
        this.world = world;
    }

    /**
     * Returns one of coordinates - x axis.
     *
     * @return one of coordinates - x axis.
     */
    public final int getX()
    {
        return this.x;
    }

    /**
     * Returns one of coordinates - y axis.
     *
     * @return one of coordinates - y axis.
     */
    public final int getY()
    {
        return this.y;
    }

    /**
     * Returns one of coordinates - z axis.
     *
     * @return one of coordinates - z axis.
     */
    public final int getZ()
    {
        return this.z;
    }

    /**
     * Returns world of this location.
     *
     * @return world of this location.
     */
    @Nullable
    public final World getWorld()
    {
        return this.world;
    }

    /**
     * Create new location with this same data but different world.
     *
     * @param world
     *         new world to use.
     *
     * @return new instance of location.
     */
    public final BlockLocation setWorld(World world)
    {
        return new BlockLocation(this.x, this.y, this.z, world);
    }

    /**
     * Returns chunk on this location.
     *
     * @return chunk on this location.
     *
     * @throws IllegalStateException
     *         if world instance is null.
     */
    public final Chunk getChunk()
    {
        if (this.world == null)
        {
            throw new IllegalStateException("Can't get chunk from location without world");
        }
        return this.world.getChunkAt(this.getChunkPosition());
    }

    /**
     * Returns block on this location.
     *
     * @return block on this location.
     *
     * @throws IllegalStateException
     *         if world instance is null.
     */
    public final Block getBlock()
    {
        if (this.world == null)
        {
            throw new IllegalStateException("Can't get block from location without world");
        }
        return this.world.getBlockAt(this.x, this.y, this.z);
    }

    /**
     * Adds given number to x coordinate of this location.
     *
     * @param x
     *         value to add.
     *
     * @return new location instance.
     */
    public final BlockLocation addX(int x)
    {
        return new BlockLocation(this.x + x, this.y, this.z, this.world);
    }

    /**
     * Adds given number to y coordinate of this location.
     *
     * @param y
     *         value to add.
     *
     * @return new location instance.
     */
    public final BlockLocation addY(int y)
    {
        return new BlockLocation(this.x, this.y + y, this.z, this.world);
    }

    /**
     * Adds given number to z coordinate of this location.
     *
     * @param z
     *         value to add.
     *
     * @return new location instance.
     */
    public final BlockLocation addZ(int z)
    {
        return new BlockLocation(this.x, this.y, this.z + z, this.world);
    }

    /**
     * Adds given location to this location.
     *
     * @param x
     *         one of coordinates.
     * @param y
     *         one of coordinates.
     * @param z
     *         one of coordinates.
     *
     * @return new location instance.
     */
    public final BlockLocation add(int x, int y, int z)
    {
        return new BlockLocation(this.x + x, this.y + y, this.z + z, this.world);
    }

    /**
     * Adds given location to this location.
     *
     * @param location
     *         location to add.
     *
     * @return new location instance.
     *
     * @throws IllegalArgumentException
     *         for differing worlds
     */
    public final BlockLocation add(BlockLocation location)
    {
        if ((this.world != null) && (location.getWorld() != this.world))
        {
            throw new IllegalArgumentException("Cannot add Locations of differing worlds");
        }
        return new BlockLocation(this.x + location.x, this.y + location.y, this.z + location.z, (this.world == null) ? location.world : this.world);
    }

    /**
     * Adds given location to this location.
     *
     * @param location
     *         location to add.
     *
     * @return new location instance.
     *
     * @throws IllegalArgumentException
     *         for differing worlds
     */
    public final BlockLocation add(Location location)
    {
        if ((this.world != null) && (location.getWorld() != this.world))
        {
            throw new IllegalArgumentException("Cannot add Locations of differing worlds");
        }
        return new BlockLocation(this.x + location.x, this.y + location.y, this.z + location.z, (this.world == null) ? location.world : this.world);
    }

    /**
     * Subtracts given number from x coordinate of this location.
     *
     * @param x
     *         value to add.
     *
     * @return new location instance.
     */
    public final BlockLocation subtractX(int x)
    {
        return new BlockLocation(this.x - x, this.y, this.z, this.world);
    }

    /**
     * Subtracts given number from y coordinate of this location.
     *
     * @param y
     *         value to add.
     *
     * @return new location instance.
     */
    public final BlockLocation subtractY(int y)
    {
        return new BlockLocation(this.x, this.y - y, this.z, this.world);
    }

    /**
     * Subtracts given number from z coordinate of this location.
     *
     * @param z
     *         value to add.
     *
     * @return new location instance.
     */
    public final BlockLocation subtractZ(int z)
    {
        return new BlockLocation(this.x, this.y, this.z - z, this.world);
    }

    /**
     * Subtracts given location from this location.
     *
     * @param x
     *         one of coordinates.
     * @param y
     *         one of coordinates.
     * @param z
     *         one of coordinates.
     *
     * @return new location instance.
     */
    public final BlockLocation subtract(int x, int y, int z)
    {
        return new BlockLocation(this.x - x, this.y - y, this.z - z, this.world);
    }

    /**
     * Subtracts given location from this location.
     *
     * @param location
     *         location to add.
     *
     * @return new location instance.
     *
     * @throws IllegalArgumentException
     *         for differing worlds
     */
    public final BlockLocation subtract(BlockLocation location)
    {
        if ((this.world != null) && (location.getWorld() != this.world))
        {
            throw new IllegalArgumentException("Cannot subtract Locations of differing worlds");
        }
        return new BlockLocation(this.x - location.x, this.y - location.y, this.z - location.z, (this.world == null) ? location.world : this.world);
    }

    /**
     * Subtracts given location from this location.
     *
     * @param location
     *         location to add.
     *
     * @return new location instance.
     *
     * @throws IllegalArgumentException
     *         for differing worlds
     */
    public final BlockLocation subtract(Location location)
    {
        if ((this.world != null) && (location.getWorld() != this.world))
        {
            throw new IllegalArgumentException("Cannot subtract Locations of differing worlds");
        }
        return new BlockLocation(this.x - location.x, this.y - location.y, this.z - location.z, (this.world == null) ? location.world : this.world);
    }

    /**
     * Performs scalar multiplication, multiplying all coordinates with a scalar.
     *
     * @param m
     *         The factor
     *
     * @return new instance of location.
     */
    public final BlockLocation multiply(double m)
    {
        return new BlockLocation(this.x * m, this.y * m, this.z * m, this.world);
    }

    /**
     * Returns length of this location used as vector.
     *
     * @return length of this location used as vector.
     */
    public final double length()
    {
        return Math.sqrt(this.lengthSquared());
    }

    /**
     * Returns squared length of this location used as vector. <br>
     * This method is faster than {@link #length()} and should be used when possible for comparing locations.
     *
     * @return squared length of this location used as vector.
     */
    public final double lengthSquared()
    {
        return DioriteMathUtils.square(this.x) + DioriteMathUtils.square(this.y) + DioriteMathUtils.square(this.z);
    }

    /**
     * Returns distance to other location, for comparing distances you should use faster, squared version of method! {@link #distanceSquared(double, double,
     * double)}
     *
     * @param x
     *         one of coordinates.
     * @param z
     *         one of coordinates.
     * @param y
     *         one of coordinates.
     *
     * @return distance to given location.
     */
    public final double distance(double x, double y, double z)
    {
        return Math.sqrt(this.distanceSquared(x, y, z));
    }

    /**
     * Returns distance to other location from center of blocks, for comparing distances you should use faster, squared version of method! {@link
     * #distanceSquared(double, double, double)}
     *
     * @param x
     *         one of coordinates.
     * @param z
     *         one of coordinates.
     * @param y
     *         one of coordinates.
     *
     * @return distance to given location.
     */
    public final double distanceFromCenter(double x, double y, double z)
    {
        return Math.sqrt(this.distanceSquaredFromCenter(x, y, z));
    }

    /**
     * Returns distance to other location, for comparing distances you should use faster, squared version of method! {@link #distanceSquared(Location)}
     *
     * @param location
     *         other location.
     *
     * @return distance to given location.
     */
    public final double distance(Location location)
    {
        return Math.sqrt(this.distanceSquared(location));
    }

    /**
     * Returns distance to other location, for comparing distances you should use faster, squared version of method! {@link #distanceSquared(BlockLocation)}
     *
     * @param location
     *         other location.
     *
     * @return distance to given location.
     */
    public final double distance(BlockLocation location)
    {
        return Math.sqrt(this.distanceSquared(location));
    }

    /**
     * Returns squared distance to other location, this method is faster than {@link #distance(double, double, double)} so should be used for comparision when
     * exact value isn't needed.
     *
     * @param x
     *         one of coordinates.
     * @param z
     *         one of coordinates.
     * @param y
     *         one of coordinates.
     *
     * @return squared distance to given location.
     */
    public final double distanceSquared(double x, double y, double z)
    {
        double deltaX = (double) this.x - x;
        double deltaY = (double) this.y - y;
        double deltaZ = (double) this.z - z;
        return DioriteMathUtils.square(deltaX) + DioriteMathUtils.square(deltaY) + DioriteMathUtils.square(deltaZ);
    }

    /**
     * Returns squared distance to other location from center of blocks, this method is faster than {@link #distanceFromCenter(double, double, double)} so
     * should be used for comparision when exact value isn't needed.
     *
     * @param x
     *         one of coordinates.
     * @param z
     *         one of coordinates.
     * @param y
     *         one of coordinates.
     *
     * @return squared distance to given location.
     */
    public final double distanceSquaredFromCenter(double x, double y, double z)
    {
        double deltaX = ((double) this.x + 0.5) - x;
        double deltaY = ((double) this.y + 0.5) - y;
        double deltaZ = ((double) this.z + 0.5) - z;
        return DioriteMathUtils.square(deltaX) + DioriteMathUtils.square(deltaY) + DioriteMathUtils.square(deltaZ);
    }

    /**
     * Returns squared distance to other location, this method is faster than {@link #distance(Location)} so should be used for comparision when
     * exact value isn't needed.
     *
     * @param location
     *         other location.
     *
     * @return squared distance to given location.
     *
     * @throws IllegalArgumentException
     *         for differing worlds
     */
    public final double distanceSquared(Location location)
    {
        World otherWorld = location.world;
        if (otherWorld != this.world)
        {
            throw new IllegalArgumentException("Cannot measure distance between " + (this.world == null ? null : this.world.getName()) + " and " +
                                               (otherWorld == null ? null : otherWorld.getName()));
        }
        return this.distanceSquared(location.x, location.y, location.z);
    }

    /**
     * Returns squared distance to other location, this method is faster than {@link #distance(BlockLocation)} so should be used for comparision when
     * exact value isn't needed.
     *
     * @param location
     *         other location.
     *
     * @return squared distance to given location.
     *
     * @throws IllegalArgumentException
     *         for differing worlds
     */
    public final double distanceSquared(BlockLocation location)
    {
        World otherWorld = location.world;
        if (otherWorld != this.world)
        {
            throw new IllegalArgumentException("Cannot measure distance between " + (this.world == null ? null : this.world.getName()) + " and " +
                                               (otherWorld == null ? null : otherWorld.getName()));
        }
        return this.distanceSquared(location.x, location.y, location.z);
    }

    /**
     * Returns cross product between this location and given one.
     *
     * @param location
     *         other location to cross with.
     *
     * @return cross product between this location and given one.
     *
     * @throws IllegalArgumentException
     *         for differing worlds
     */
    public final Location crossProduct(Location location)
    {
        if (location.world != this.world)
        {
            throw new IllegalArgumentException("Cannot do cross world check");
        }
        return new Location((this.y * location.z) - (this.z * location.y),
                            (this.z * location.x) - (this.x * location.z),
                            (this.x * location.y) - (this.y * location.x));
    }

    /**
     * Returns cross product between this location and given one.
     *
     * @param location
     *         other location to cross with.
     *
     * @return cross product between this location and given one.
     *
     * @throws IllegalArgumentException
     *         for differing worlds
     */
    public final BlockLocation crossProduct(BlockLocation location)
    {
        if (location.world != this.world)
        {
            throw new IllegalArgumentException("Cannot do cross world check");
        }
        return new BlockLocation((this.y * location.z) - (this.z * location.y),
                                 (this.z * location.x) - (this.x * location.z),
                                 (this.x * location.y) - (this.y * location.x));
    }

    /**
     * Checks if this location is inside axis aligned box defined by two other locations.
     *
     * @param min
     *         min corner of box.
     * @param max
     *         max corner of box.
     *
     * @return true if this location is inside axis aligned box defined by two other locations.
     *
     * @throws IllegalArgumentException
     *         for differing worlds
     */
    public final boolean isInAABB(Location min, Location max)
    {
        if (max.world != min.world)
        {
            throw new IllegalArgumentException("Min and max point must be on this same world!");
        }
        if (min.world != this.world)
        {
            throw new IllegalArgumentException("Cannot do cross world check");
        }
        return (this.x >= min.x) && (this.x <= max.x) && (this.y >= min.y) && (this.y <= max.y) && (this.z >= min.z) && (this.z <= max.z);
    }

    /**
     * Checks if this location is inside sphere defined by center and radius.
     *
     * @param origin
     *         center of sphere
     * @param radius
     *         size of sphere
     *
     * @return true if this location is inside sphere defined by center and radius.
     *
     * @throws IllegalArgumentException
     *         for differing worlds
     */
    public final boolean isInSphere(Location origin, double radius)
    {
        if (origin.world != this.world)
        {
            throw new IllegalArgumentException("Cannot do cross world check");
        }
        return (DioriteMathUtils.square(origin.x - this.x) + DioriteMathUtils.square(origin.y - this.y) + DioriteMathUtils.square(origin.z - this.z)) <=
               DioriteMathUtils.square(radius);
    }

    /**
     * Checks if this location is inside axis aligned box defined by two other locations.
     *
     * @param min
     *         min corner of box.
     * @param max
     *         max corner of box.
     *
     * @return true if this location is inside axis aligned box defined by two other locations.
     *
     * @throws IllegalArgumentException
     *         for differing worlds
     */
    public final boolean isInAABB(BlockLocation min, BlockLocation max)
    {
        if (max.world != min.world)
        {
            throw new IllegalArgumentException("Min and max point must be on this same world!");
        }
        if (min.world != this.world)
        {
            throw new IllegalArgumentException("Cannot do cross world check");
        }
        return (this.x >= min.x) && (this.x <= max.x) && (this.y >= min.y) && (this.y <= max.y) && (this.z >= min.z) && (this.z <= max.z);
    }

    /**
     * Checks if this location is inside sphere defined by center and radius.
     *
     * @param origin
     *         center of sphere
     * @param radius
     *         size of sphere
     *
     * @return true if this location is inside sphere defined by center and radius.
     *
     * @throws IllegalArgumentException
     *         for differing worlds
     */
    public final boolean isInSphere(BlockLocation origin, double radius)
    {
        if (origin.world != this.world)
        {
            throw new IllegalArgumentException("Cannot do cross world check");
        }
        return (DioriteMathUtils.square(origin.x - this.x) + DioriteMathUtils.square(origin.y - this.y) + DioriteMathUtils.square(origin.z - this.z)) <=
               DioriteMathUtils.square(radius);
    }

    /**
     * Returns position of chunk that contains this location.
     *
     * @return position of chunk that contains this location.
     *
     * @throws IllegalStateException
     *         if world instance is null.
     */
    public final ChunkPosition getChunkPosition() throws IllegalStateException
    {
        if (this.world == null)
        {
            throw new IllegalStateException("Can't get chunk from location without world");
        }
        return this.world.toChunkPosition(this);
    }

    /**
     * Returns block location pointing on this same block as this location.
     *
     * @return block location pointing on this same block as this location.
     */
    public final BlockLocation toBlockLocation()
    {
        return new BlockLocation(DioriteMathUtils.floor(this.x), DioriteMathUtils.floor(this.y), DioriteMathUtils.floor(this.z), this.world);
    }

    /**
     * Returns this location as mutable location.
     *
     * @return this location as mutable location.
     */
    public final Location toLocation()
    {
        return new Location(this.x, this.y, this.z, this.world);
    }

    /**
     * Returns this location as immutable location.
     *
     * @return this location as immutable location.
     */
    public final ImmutableLocation toImmutableLocation()
    {
        return new ImmutableLocation(this.x, this.y, this.z, this.world);
    }

    /**
     * Constructs a new {@link Vector3d} based on this Location
     *
     * @return New Vector containing the coordinates represented by this Location
     */
    public final Vector3d toVector()
    {
        return new Vector3d(this.x, this.y, this.z);
    }

    @Override
    public final boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (! (o instanceof BlockLocation))
        {
            return false;
        }
        BlockLocation that = (BlockLocation) o;
        return (this.x == that.x) &&
               (this.y == that.y) &&
               (this.z == that.z) &&
               Objects.equals(this.world, that.world);
    }

    @Override
    public final int hashCode()
    {
        return Objects.hash(this.x, this.y, this.z, this.world);
    }

    @Override
    public final String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("x", this.x).append("y", this.y)
                                                                          .append("z", this.z)
                                                                          .append("world", (this.world == null) ? null : this.world.getName()).toString();
    }
}
