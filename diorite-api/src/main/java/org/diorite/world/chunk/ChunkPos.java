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

package org.diorite.world.chunk;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.utils.math.DioriteMathUtils;
import org.diorite.world.World;

public class ChunkPos
{
    private final int   x;
    private final int   z;
    private final World world;

    public ChunkPos(final int x, final int z, final World world)
    {
        this.x = x;
        this.z = z;
        this.world = world;
    }

    public ChunkPos(final int x, final int z)
    {
        this.x = x;
        this.z = z;
        this.world = null;
    }

    public int getX()
    {
        return this.x;
    }

    public int getZ()
    {
        return this.z;
    }

    public World getWorld()
    {
        return this.world;
    }

    public ChunkPos setWorld(final World world)
    {
        return new ChunkPos(this.x, this.z, world);
    }

    public ChunkPos add(final ChunkPos other)
    {
        return new ChunkPos(this.x + other.x, this.z + other.z, this.world);
    }

    public ChunkPos add(final int x, final int z)
    {
        return new ChunkPos(this.x + x, this.z + z);
    }

    public double length()
    {
        return Math.sqrt(this.lengthSquared());
    }

    public double lengthSquared()
    {
        return DioriteMathUtils.square(this.x) + DioriteMathUtils.square(this.z);
    }

    public double distance(final double x, final double z)
    {
        return Math.sqrt(this.distanceSquared(x, z));
    }

    public double distance(final ChunkPos location)
    {
        return Math.sqrt(this.distanceSquared(location));
    }

    public double distanceSquared(final double x, final double z)
    {
        final double deltaX = this.x - x;
        final double deltaZ = this.z - z;
        return DioriteMathUtils.square(deltaX) + DioriteMathUtils.square(deltaZ);
    }

    public double distanceSquared(final ChunkPos location)
    {
        return this.distanceSquared(location.getX(), location.getZ());
    }

    public boolean isInAABB(final ChunkPos min, final ChunkPos max)
    {
        return (this.x >= min.getX()) && (this.x <= max.getX()) && (this.z >= min.getZ()) && (this.z <= max.getZ());
    }

    public boolean isInSphere(final ChunkPos origin, final double radius)
    {
        return (DioriteMathUtils.square(origin.getX() - this.x) + DioriteMathUtils.square(origin.getZ() - this.z)) <= DioriteMathUtils.square(radius);
    }

    @SuppressWarnings("MagicNumber")
    public long asLong()
    {
        return (((long) this.x) << 32) | (this.z & 0xffffffffL);
    }

    @SuppressWarnings("MagicNumber")
    public static ChunkPos fromLong(final long l)
    {
        return new ChunkPos((int) (l >> 32), (int) l);
    }

    @Override
    public int hashCode()
    {
        int result = this.x;
        result = (31 * result) + this.z;
        result = (31 * result) + ((this.world != null) ? this.world.hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(final Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (! (o instanceof ChunkPos))
        {
            return false;
        }

        final ChunkPos chunkPos = (ChunkPos) o;

        // check world only if both of them use it
        return (this.x == chunkPos.x) && (this.z == chunkPos.z) && ((this.world == null) || (chunkPos.world == null) || (this.world.equals(chunkPos.world)));

    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("x", this.x).append("z", this.z).toString();
    }

    public static ChunkPos fromWorldPos(final int x, final int z, final World world)
    {
        return new ChunkPos(x >> 4, z >> 4, world);
    }
}
