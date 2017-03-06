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
 * Represents some location including rotation.
 */
@SuppressWarnings("MagicNumber")
public class Location
{
    double x;
    double y;
    double z;
    float  yaw;
    float  pitch;
    @Nullable World world;

    /**
     * Create new instance of location for given coordinates.
     *
     * @param x
     *         one of coordinates.
     * @param z
     *         one of coordinates.
     * @param y
     *         one of coordinates.
     */
    public Location(double x, double z, double y)
    {
        this.x = x;
        this.z = z;
        this.y = y;
        this.yaw = 0;
        this.pitch = 0;
    }

    /**
     * Create new instance of location for given coordinates.
     *
     * @param x
     *         one of coordinates.
     * @param z
     *         one of coordinates.
     * @param y
     *         one of coordinates.
     * @param yaw
     *         yaw rotation.
     * @param pitch
     *         yaw rotation.
     */
    public Location(double x, double y, double z, float yaw, float pitch)
    {
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
    }

    /**
     * Create new instance of location for given coordinates.
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
    public Location(double x, double y, double z, @Nullable World world)
    {
        this.x = x;
        this.y = y;
        this.z = z;
        this.world = world;
    }

    /**
     * Create new instance of location for given coordinates.
     *
     * @param x
     *         one of coordinates.
     * @param z
     *         one of coordinates.
     * @param y
     *         one of coordinates.
     * @param yaw
     *         yaw rotation.
     * @param pitch
     *         yaw rotation.
     * @param world
     *         world of location.
     */
    public Location(double x, double y, double z, float yaw, float pitch, @Nullable World world)
    {
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
        this.world = world;
    }

    /**
     * Returns one of coordinates - x axis.
     *
     * @return one of coordinates - x axis.
     */
    public final double getX()
    {
        return this.x;
    }

    /**
     * Returns one of coordinates - x axis.
     *
     * @return one of coordinates - x axis.
     */
    public final int getBlockX()
    {
        return DioriteMathUtils.floor(this.x);
    }

    /**
     * Sets given coordinate to this location, this method depending on implementation of location may return new location instance or this same. <br>
     * {@link Location} return itself, {@link ImmutableLocation} returns new instance of {@link ImmutableLocation}.
     *
     * @param x
     *         new coordinate value.
     *
     * @return {@link Location} return itself, {@link ImmutableLocation} returns new instance of {@link ImmutableLocation}.
     */
    public Location setX(double x)
    {
        this.x = x;
        return this;
    }

    /**
     * Returns one of coordinates - y axis.
     *
     * @return one of coordinates - y axis.
     */
    public final double getY()
    {
        return this.y;
    }

    /**
     * Returns one of coordinates - y axis.
     *
     * @return one of coordinates - y axis.
     */
    public final int getBlockY()
    {
        return DioriteMathUtils.floor(this.y);
    }

    /**
     * Sets given coordinate to this location, this method depending on implementation of location may return new location instance or this same. <br>
     * {@link Location} return itself, {@link ImmutableLocation} returns new instance of {@link ImmutableLocation}.
     *
     * @param y
     *         new coordinate value.
     *
     * @return {@link Location} return itself, {@link ImmutableLocation} returns new instance of {@link ImmutableLocation}.
     */
    public Location setY(double y)
    {
        this.y = y;
        return this;
    }

    /**
     * Returns one of coordinates - z axis.
     *
     * @return one of coordinates - z axis.
     */
    public final double getZ()
    {
        return this.z;
    }

    /**
     * Returns one of coordinates - z axis.
     *
     * @return one of coordinates - z axis.
     */
    public final int getBlockZ()
    {
        return DioriteMathUtils.floor(this.z);
    }

    /**
     * Sets given coordinate to this location, this method depending on implementation of location may return new location instance or this same. <br>
     * {@link Location} return itself, {@link ImmutableLocation} returns new instance of {@link ImmutableLocation}.
     *
     * @param z
     *         new coordinate value.
     *
     * @return {@link Location} return itself, {@link ImmutableLocation} returns new instance of {@link ImmutableLocation}.
     */
    public Location setZ(double z)
    {
        this.z = z;
        return this;
    }

    /**
     * Returns one of rotation factors - pitch.
     *
     * @return one of rotation factors - pitch.
     */
    public final float getPitch()
    {
        return this.pitch;
    }

    /**
     * Sets given rotation factor to this location, this method depending on implementation of location may return new location instance or this same. <br>
     * {@link Location} return itself, {@link ImmutableLocation} returns new instance of {@link ImmutableLocation}.
     *
     * @param pitch
     *         new pitch rotation factor..
     *
     * @return {@link Location} return itself, {@link ImmutableLocation} returns new instance of {@link ImmutableLocation}.
     */
    public Location setPitch(float pitch)
    {
        this.pitch = pitch;
        return this;
    }

    /**
     * Returns one of rotation factors - yaw.
     *
     * @return one of rotation factors - yaw.
     */
    public final float getYaw()
    {
        return this.yaw;
    }

    /**
     * Sets given rotation factor to this location, this method depending on implementation of location may return new location instance or this same. <br>
     * {@link Location} return itself, {@link ImmutableLocation} returns new instance of {@link ImmutableLocation}.
     *
     * @param yaw
     *         new yaw rotation factor..
     *
     * @return {@link Location} return itself, {@link ImmutableLocation} returns new instance of {@link ImmutableLocation}.
     */
    public Location setYaw(float yaw)
    {
        this.yaw = yaw;
        return this;
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
     * Sets world of this location, this method depending on implementation of location may return new location instance or this same. <br>
     * {@link Location} return itself, {@link ImmutableLocation} returns new instance of {@link ImmutableLocation}.
     *
     * @param world
     *         new world to use.
     *
     * @return {@link Location} return itself, {@link ImmutableLocation} returns new instance of {@link ImmutableLocation}.
     */
    public Location setWorld(@Nullable World world)
    {
        this.world = world;
        return this;
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
        return new Location((this.y * location.z) - (this.z * location.y), (this.z * location.x) - (this.x * location.z),
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
    public final Location crossProduct(BlockLocation location)
    {
        if (location.world != this.world)
        {
            throw new IllegalArgumentException("Cannot do cross world check");
        }
        return new Location((this.y * location.z) - (this.z * location.y), (this.z * location.x) - (this.x * location.z),
                            (this.x * location.y) - (this.y * location.x));
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
        return this.world.getBlockAt((int) this.x, (int) this.y, (int) this.z);
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
        double deltaX = this.x - x;
        double deltaY = this.y - y;
        double deltaZ = this.z - z;
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
        double deltaX = (Math.floor(this.x) + 0.5) - (Math.floor(x) + 0.5);
        double deltaY = (Math.floor(this.y) + 0.5) - (Math.floor(y) + 0.5);
        double deltaZ = (Math.floor(this.z) + 0.5) - (Math.floor(z) + 0.5);
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
     * Gets a unit-vector pointing in the direction that this Location is
     * facing.
     *
     * @return a vector pointing the direction of this location's {@link #getPitch() pitch} and {@link #getYaw() yaw}
     */
    public final Vector3d getDirection()
    {
        Vector3d vector = new Vector3d();

        double rotX = this.yaw;
        double rotY = this.pitch;

        vector.y = - Math.sin(Math.toRadians(rotY));

        double xz = Math.cos(Math.toRadians(rotY));

        vector.x = - xz * Math.sin(Math.toRadians(rotX));
        vector.z = xz * Math.cos(Math.toRadians(rotX));

        return vector;
    }

    /**
     * Sets the {@link #getYaw() yaw} and {@link #getPitch() pitch} to point in the direction of the vector.<br>
     * {@link Location} return itself, {@link ImmutableLocation} returns new instance of {@link ImmutableLocation}.
     *
     * @param vector
     *         the direction vector
     *
     * @return {@link Location} return itself, {@link ImmutableLocation} returns new instance of {@link ImmutableLocation}.
     */
    public Location setDirection(Vector3d vector)
    {
        /*
         * Sin = Opp / Hyp
         * Cos = Adj / Hyp
         * Tan = Opp / Adj
         *
         * x = -Opp
         * z = Adj
         */
        double _2PI = 2 * Math.PI;
        double x = vector.x;
        double z = vector.z;

        if ((x == 0) && (z == 0))
        {
            this.pitch = (vector.y > 0) ? - 90 : 90;
            return this;
        }

        double theta = Math.atan2(- x, z);
        this.yaw = (float) Math.toDegrees((theta + _2PI) % _2PI);

        double x2 = DioriteMathUtils.square(x);
        double z2 = DioriteMathUtils.square(z);
        double xz = Math.sqrt(x2 + z2);
        this.pitch = (float) Math.toDegrees(Math.atan(- vector.y / xz));

        return this;
    }

    /**
     * Adds given coordinates to this location, this method depending on implementation of location may return new location instance or this same. <br>
     * {@link Location} return itself, {@link ImmutableLocation} returns new instance of {@link ImmutableLocation}.
     *
     * @param x
     *         one of coordinates.
     * @param z
     *         one of coordinates.
     * @param y
     *         one of coordinates.
     *
     * @return {@link Location} return itself, {@link ImmutableLocation} returns new instance of {@link ImmutableLocation}.
     */
    public Location add(double x, double y, double z)
    {
        this.x += x;
        this.y += y;
        this.z += z;
        return this;
    }

    /**
     * Adds the location by another. (without rotation)<br>
     * {@link Location} return itself, {@link ImmutableLocation} returns new instance of {@link ImmutableLocation}.
     *
     * @param location
     *         The other location
     *
     * @return {@link Location} return itself, {@link ImmutableLocation} returns new instance of {@link ImmutableLocation}.
     *
     * @throws IllegalArgumentException
     *         for differing worlds
     */
    public Location add(Location location)
    {
        if ((this.world != null) && (location.world != this.world))
        {
            throw new IllegalArgumentException("Cannot add Locations of differing worlds");
        }

        this.x += location.x;
        this.y += location.y;
        this.z += location.z;
        return this;
    }

    /**
     * Adds the location by a vector.<br>
     * {@link Location} return itself, {@link ImmutableLocation} returns new instance of {@link ImmutableLocation}.
     *
     * @param vector
     *         Vector to use
     *
     * @return {@link Location} return itself, {@link ImmutableLocation} returns new instance of {@link ImmutableLocation}.
     */
    public Location add(Vector3d vector)
    {
        this.x += vector.x;
        this.y += vector.y;
        this.z += vector.z;
        return this;
    }

    /**
     * Subtracts the location by another. (without rotation)<br>
     * {@link Location} return itself, {@link ImmutableLocation} returns new instance of {@link ImmutableLocation}.
     *
     * @param location
     *         The other location
     *
     * @return {@link Location} return itself, {@link ImmutableLocation} returns new instance of {@link ImmutableLocation}.
     *
     * @throws IllegalArgumentException
     *         for differing worlds
     */
    public Location subtract(Location location)
    {
        if ((this.world != null) && (location.world != this.world))
        {
            throw new IllegalArgumentException("Cannot subtract Locations of differing worlds");
        }

        this.x -= location.x;
        this.y -= location.y;
        this.z -= location.z;
        return this;
    }

    /**
     * Subtracts the location by a vector.<br>
     * {@link Location} return itself, {@link ImmutableLocation} returns new instance of {@link ImmutableLocation}.
     *
     * @param vector
     *         The vector to use
     *
     * @return {@link Location} return itself, {@link ImmutableLocation} returns new instance of {@link ImmutableLocation}.
     */
    public Location subtract(Vector3d vector)
    {
        this.x -= vector.x;
        this.y -= vector.y;
        this.z -= vector.z;
        return this;
    }

    /**
     * Subtracts the location by another. Not world-aware and orientation independent.<br>
     * {@link Location} return itself, {@link ImmutableLocation} returns new instance of {@link ImmutableLocation}.
     *
     * @param x
     *         X coordinate
     * @param y
     *         Y coordinate
     * @param z
     *         Z coordinate
     *
     * @return {@link Location} return itself, {@link ImmutableLocation} returns new instance of {@link ImmutableLocation}.
     */
    public Location subtract(double x, double y, double z)
    {
        this.x -= x;
        this.y -= y;
        this.z -= z;
        return this;
    }

    /**
     * Performs scalar multiplication, multiplying all coordinates with a scalar.<br>
     * {@link Location} return itself, {@link ImmutableLocation} returns new instance of {@link ImmutableLocation}.
     *
     * @param m
     *         The factor
     *
     * @return {@link Location} return itself, {@link ImmutableLocation} returns new instance of {@link ImmutableLocation}.
     */
    public Location multiply(double m)
    {
        this.x *= m;
        this.y *= m;
        this.z *= m;
        return this;
    }

    /**
     * Zero this location's coordinate components.<br>
     * {@link Location} return itself, {@link ImmutableLocation} returns new instance of {@link ImmutableLocation}.
     *
     * @return {@link Location} return itself, {@link ImmutableLocation} returns new instance of {@link ImmutableLocation}.
     */
    public Location zero()
    {
        this.x = 0;
        this.y = 0;
        this.z = 0;
        return this;
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
     * Returns copy of this location as mutable location.
     *
     * @return copy of this location as mutable location.
     */
    public final Location copyAsLocation()
    {
        return new Location(this.x, this.y, this.z, this.yaw, this.pitch, this.world);
    }

    /**
     * Returns copy of this location as immutable location.
     *
     * @return copy of this location as immutable location.
     */
    public final ImmutableLocation copyAsImmutableLocation()
    {
        return new ImmutableLocation(this.x, this.y, this.z, this.yaw, this.pitch, this.world);
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

    /**
     * Returns clone of location.
     *
     * @return clone of location.
     */
    @Override
    public Location clone()
    {
        return new Location(this.x, this.y, this.z, this.yaw, this.pitch, this.world);
    }

    @Override
    public final boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (! (o instanceof Location))
        {
            return false;
        }
        Location location = (Location) o;
        return (Double.compare(location.x, this.x) == 0) &&
               (Double.compare(location.y, this.y) == 0) &&
               (Double.compare(location.z, this.z) == 0) &&
               (Float.compare(location.yaw, this.yaw) == 0) &&
               (Float.compare(location.pitch, this.pitch) == 0) &&
               Objects.equals(this.world, location.world);
    }

    @Override
    public final int hashCode()
    {
        return Objects.hash(this.x, this.y, this.z, this.yaw, this.pitch, this.world);
    }

    @Override
    public final String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("x", this.x).append("y", this.y)
                                                                          .append("z", this.z).append("yaw", this.yaw).append("pitch", this.pitch)
                                                                          .append("world", (this.world == null) ? null : this.world.getName()).toString();
    }

    /**
     * Safely converts a double (location coordinate) to an int (block coordinate)
     *
     * @param loc
     *         Precise coordinate
     *
     * @return Block coordinate
     */
    public static int locToBlock(double loc)
    {
        return DioriteMathUtils.floor(loc);
    }
}
