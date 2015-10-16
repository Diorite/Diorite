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

package org.diorite;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.world.World;

public class ImmutableLocation implements Loc
{
    public static final ImmutableLocation ZERO = new ImmutableLocation(0, 0, 0);
    private final double x;
    private final double y;
    private final double z;
    private final float  yaw;
    private final float  pitch;
    private final World  world;

    public ImmutableLocation(final double x, final double z, final double y)
    {
        this.x = x;
        this.z = z;
        this.y = y;
        this.yaw = 0;
        this.pitch = 0;
        this.world = null;
    }

    public ImmutableLocation(final double x, final double y, final double z, final float yaw, final float pitch)
    {
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
        this.world = null;
    }

    public ImmutableLocation(final double x, final double y, final double z, final World world)
    {
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = 0;
        this.pitch = 0;
        this.world = world;
    }

    public ImmutableLocation(final double x, final double y, final double z, final float yaw, final float pitch, final World world)
    {
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
        this.world = world;
    }

    @Override
    public double getX()
    {
        return this.x;
    }

    public ImmutableLocation setX(final double x)
    {
        return new ImmutableLocation(x, this.y, this.z, this.yaw, this.pitch, this.world);
    }

    @Override
    public double getY()
    {
        return this.y;
    }

    public ImmutableLocation setY(final double y)
    {
        return new ImmutableLocation(this.x, y, this.z, this.yaw, this.pitch, this.world);
    }

    @Override
    public double getZ()
    {
        return this.z;
    }

    public ImmutableLocation setZ(final double z)
    {
        return new ImmutableLocation(this.x, this.y, z, this.yaw, this.pitch, this.world);
    }

    @Override
    public float getPitch()
    {
        return this.pitch;
    }

    @Override
    public float getYaw()
    {
        return this.yaw;
    }

    public ImmutableLocation setYaw(final float yaw)
    {
        return new ImmutableLocation(this.x, this.y, this.z, yaw, this.pitch, this.world);
    }

    @Override
    public World getWorld()
    {
        return this.world;
    }

    @Override
    public ImmutableLocation crossProduct(final Loc location)
    {
        return new ImmutableLocation((this.y * location.getZ()) - (this.z * location.getY()), (this.z * location.getX()) - (this.x * location.getZ()), (this.x * location.getY()) - (this.y * location.getX()));
    }

    @Override
    public ImmutableLocation crossProduct(final BlockLocation location)
    {
        return new ImmutableLocation((this.y * location.getZ()) - (this.z * location.getY()), (this.z * location.getX()) - (this.x * location.getZ()), (this.x * location.getY()) - (this.y * location.getX()));
    }

    public ImmutableLocation setPitch(final float pitch)
    {
        return new ImmutableLocation(this.x, this.y, this.z, this.yaw, pitch, this.world);
    }

    public ImmutableLocation addX(final double x)
    {
        return new ImmutableLocation(this.x + x, this.y, this.z, this.yaw, this.pitch, this.world);
    }

    public ImmutableLocation addY(final double y)
    {
        return new ImmutableLocation(this.x, this.y + y, this.z, this.yaw, this.pitch, this.world);
    }

    public ImmutableLocation addZ(final double z)
    {
        return new ImmutableLocation(this.x, this.y, this.z + z, this.yaw, this.pitch, this.world);
    }

    public ImmutableLocation addYaw(final float yaw)
    {
        return new ImmutableLocation(this.x, this.y, this.z, this.yaw + yaw, this.pitch, this.world);
    }

    public ImmutableLocation addPitch(final float pitch)
    {
        return new ImmutableLocation(this.x, this.y, this.z, this.yaw, this.pitch + pitch, this.world);
    }

    public ImmutableLocation getInWorld(final World world)
    {
        return new ImmutableLocation(this.x, this.y, this.z, this.yaw, this.pitch, world);
    }

    @Override
    public int hashCode()
    {
        int result;
        long temp;
        temp = Double.doubleToLongBits(this.x);
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(this.y);
        result = (31 * result) + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(this.z);
        result = (31 * result) + (int) (temp ^ (temp >>> 32));
        result = (31 * result) + ((this.yaw != + 0.0f) ? Float.floatToIntBits(this.yaw) : 0);
        result = (31 * result) + ((this.pitch != + 0.0f) ? Float.floatToIntBits(this.pitch) : 0);
        return result;
    }

    @Override
    public boolean equals(final Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (! (o instanceof ImmutableLocation))
        {
            return false;
        }

        final ImmutableLocation that = (ImmutableLocation) o;

        return (this.x == that.x) && (this.y == that.y) && (this.z == that.z);

    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("x", this.x).append("y", this.y).append("z", this.z).append("yaw", this.yaw).append("pitch", this.pitch).toString();
    }
}
