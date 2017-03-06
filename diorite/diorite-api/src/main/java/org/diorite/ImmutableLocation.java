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
import javax.annotation.concurrent.Immutable;
import javax.vecmath.Vector3d;

import org.diorite.commons.math.DioriteMathUtils;
import org.diorite.world.World;

/**
 * Represents some location including rotation.
 */
@SuppressWarnings("MagicNumber")
public final class ImmutableLocation extends Location
{
    public static final ImmutableLocation ZERO = new ImmutableLocation(0, 0, 0);

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
    public ImmutableLocation(double x, double z, double y)
    {
        super(x, z, y);
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
    public ImmutableLocation(double x, double y, double z, float yaw, float pitch)
    {
        super(x, y, z, yaw, pitch);
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
    public ImmutableLocation(double x, double y, double z, @Nullable World world)
    {
        super(x, y, z, world);
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
    public ImmutableLocation(double x, double y, double z, float yaw, float pitch, @Nullable World world)
    {
        super(x, y, z, yaw, pitch, world);
    }

    @Override
    public final ImmutableLocation add(double x, double y, double z)
    {
        return new ImmutableLocation(this.x + x, this.y + y, this.z + z, this.yaw, this.pitch, this.world);
    }

    @Override
    public final ImmutableLocation setX(double x)
    {
        return new ImmutableLocation(x, this.y, this.z, this.yaw, this.pitch, this.world);
    }

    @Override
    public final ImmutableLocation setY(double y)
    {
        return new ImmutableLocation(this.x, y, this.z, this.yaw, this.pitch, this.world);
    }

    @Override
    public final ImmutableLocation setZ(double z)
    {
        return new ImmutableLocation(this.x, this.y, z, this.yaw, this.pitch, this.world);
    }

    @Override
    public final ImmutableLocation setPitch(float pitch)
    {
        return new ImmutableLocation(this.x, this.y, this.z, this.yaw, pitch, this.world);
    }

    @Override
    public final ImmutableLocation setYaw(float yaw)
    {
        return new ImmutableLocation(this.x, this.y, this.z, yaw, this.pitch, this.world);
    }

    @Override
    public final ImmutableLocation setWorld(@Nullable World world)
    {
        return new ImmutableLocation(this.x, this.y, this.z, this.yaw, this.pitch, world);
    }

    @Override
    public final ImmutableLocation setDirection(Vector3d vector)
    {
        /*
         * Sin = Opp / Hyp
         * Cos = Adj / Hyp
         * Tan = Opp / Adj
         *
         * x = -Opp
         * z = Adj
         */
        float pitch;
        float yaw = this.yaw;
        double _2PI = 2 * Math.PI;
        double x = vector.x;
        double z = vector.z;

        if ((x == 0) && (z == 0))
        {
            pitch = (vector.y > 0) ? - 90 : 90;
            return new ImmutableLocation(this.x, this.y, this.z, yaw, pitch, this.world);
        }

        double theta = Math.atan2(- x, z);
        yaw = (float) Math.toDegrees((theta + _2PI) % _2PI);

        double x2 = DioriteMathUtils.square(x);
        double z2 = DioriteMathUtils.square(z);
        double xz = Math.sqrt(x2 + z2);
        pitch = (float) Math.toDegrees(Math.atan(- vector.y / xz));
        return new ImmutableLocation(this.x, this.y, this.z, yaw, pitch, this.world);
    }

    @Override
    public final ImmutableLocation add(Location location)
    {
        if (location.getWorld() != this.world)
        {
            throw new IllegalArgumentException("Cannot add Locations of differing worlds");
        }
        return new ImmutableLocation(this.x + location.getX(), this.y + location.getY(), this.z + location.getZ(), this.yaw, this.pitch, this.world);
    }

    @Override
    public final ImmutableLocation add(Vector3d vector)
    {
        return new ImmutableLocation(this.x + vector.x, this.y + vector.y, this.z + vector.z);
    }

    @Override
    public final ImmutableLocation subtract(Location location)
    {
        if (location.getWorld() != this.world)
        {
            throw new IllegalArgumentException("Cannot add Locations of differing worlds");
        }
        return new ImmutableLocation(this.x - location.getX(), this.y - location.getY(), this.z - location.getZ(), this.yaw, this.pitch, this.world);
    }

    @Override
    public final ImmutableLocation subtract(Vector3d vector)
    {
        return new ImmutableLocation(this.x - vector.x, this.y - vector.y, this.z - vector.z);
    }

    @Override
    public final ImmutableLocation subtract(double x, double y, double z)
    {
        return new ImmutableLocation(this.x - x, this.y - y, this.z - z);
    }

    @Override
    public final ImmutableLocation multiply(double m)
    {
        return new ImmutableLocation(this.x * m, this.y * m, this.z * m, this.yaw, this.pitch, this.world);
    }

    @Override
    public final ImmutableLocation zero()
    {
        return new ImmutableLocation(0, 0, 0, this.yaw, this.pitch, this.world);
    }

    @Override
    public final ImmutableLocation clone()
    {
        return new ImmutableLocation(this.x, this.y, this.z, this.yaw, this.pitch, this.world);
    }
}
