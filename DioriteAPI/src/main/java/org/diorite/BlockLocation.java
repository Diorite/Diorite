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

import org.diorite.material.BlockMaterialData;
import org.diorite.utils.math.DioriteMathUtils;
import org.diorite.world.Block;
import org.diorite.world.World;
import org.diorite.world.chunk.ChunkPos;

public class BlockLocation
{
    public static final BlockLocation ZERO = new BlockLocation(0, 0, 0);
    private final int   x;
    private final int   y;
    private final int   z;
    private final World world;

    public BlockLocation(final int x, final int y, final int z)
    {
        this.x = x;
        this.y = y;
        this.z = z;
        this.world = null;
    }

    public BlockLocation(final int x, final int y, final int z, final World world)
    {
        this.x = x;
        this.y = y;
        this.z = z;
        this.world = world;
    }

    public BlockLocation(final double x, final double y, final double z)
    {
        this.x = DioriteMathUtils.floor(x);
        this.y = DioriteMathUtils.floor(y);
        this.z = DioriteMathUtils.floor(z);
        this.world = null;
    }

    public BlockLocation(final double x, final double y, final double z, final World world)
    {
        this.x = DioriteMathUtils.floor(x);
        this.y = DioriteMathUtils.floor(y);
        this.z = DioriteMathUtils.floor(z);
        this.world = world;
    }

    public int getX()
    {
        return this.x;
    }

    public int getY()
    {
        return this.y;
    }

    public int getZ()
    {
        return this.z;
    }

    public World getWorld()
    {
        return this.world;
    }

    public BlockLocation setWorld(final World world)
    {
        return new BlockLocation(this.x, this.y, this.z, world);
    }

    public void setBlock(final BlockMaterialData mat)
    {
        this.world.setBlock(this.x, this.y, this.z, mat);
    }

    public Block getBlock()
    {
        return this.world.getBlock(this.x, this.y, this.z);
    }

    public BlockLocation addX(final int x)
    {
        return new BlockLocation(this.x + x, this.y, this.z, this.world);
    }

    public BlockLocation addY(final int y)
    {
        return new BlockLocation(this.x, this.y + y, this.z, this.world);
    }

    public BlockLocation addZ(final int z)
    {
        return new BlockLocation(this.x, this.y, this.z + z, this.world);
    }

    public BlockLocation add(final int x, final int y, final int z)
    {
        return new BlockLocation(this.x + x, this.y + y, this.z + z, this.world);
    }

    public BlockLocation add(final BlockLocation loc)
    {
        return new BlockLocation(this.x + loc.x, this.y + loc.y, this.z + loc.z, (this.world == null) ? loc.world : this.world);
    }

    public BlockLocation subtractX(final int x)
    {
        return new BlockLocation(this.x - x, this.y, this.z, this.world);
    }

    public BlockLocation subtractY(final int y)
    {
        return new BlockLocation(this.x, this.y - y, this.z, this.world);
    }

    public BlockLocation subtractZ(final int z)
    {
        return new BlockLocation(this.x, this.y, this.z - z, this.world);
    }

    public BlockLocation subtract(final int x, final int y, final int z)
    {
        return new BlockLocation(this.x - x, this.y - y, this.z - z, this.world);
    }

    public BlockLocation subtract(final BlockLocation loc)
    {
        return new BlockLocation(this.x - loc.x, this.y - loc.y, this.z - loc.z, (this.world == null) ? loc.world : this.world);
    }


    public double length()
    {
        return Math.sqrt(this.lengthSquared());
    }

    public double lengthSquared()
    {
        return DioriteMathUtils.square(this.x) + DioriteMathUtils.square(this.y) + DioriteMathUtils.square(this.z);
    }

    public double distance(final double x, final double y, final double z)
    {
        return Math.sqrt(this.distanceSquared(x, y, z));
    }

    public double distanceFromCenter(final double x, final double y, final double z)
    {
        return Math.sqrt(this.distanceSquaredFromCenter(x, y, z));
    }

    public double distance(final BlockLocation location)
    {
        return Math.sqrt(this.distanceSquared(location));
    }

    public double distance(final Loc location)
    {
        return Math.sqrt(this.distanceSquared(location));
    }

    public double distanceSquared(final double x, final double y, final double z)
    {
        final double deltaX = (double) this.x - x;
        final double deltaY = (double) this.y - y;
        final double deltaZ = (double) this.z - z;
        return DioriteMathUtils.square(deltaX) + DioriteMathUtils.square(deltaY) + DioriteMathUtils.square(deltaZ);
    }

    @SuppressWarnings("MagicNumber")
    public double distanceSquaredFromCenter(final double x, final double y, final double z)
    {
        final double deltaX = ((double) this.x + 0.5) - x;
        final double deltaY = ((double) this.y + 0.5) - y;
        final double deltaZ = ((double) this.z + 0.5) - z;
        return DioriteMathUtils.square(deltaX) + DioriteMathUtils.square(deltaY) + DioriteMathUtils.square(deltaZ);
    }

    public double distanceSquared(final Loc location)
    {
        return this.distanceSquared(location.getX(), location.getY(), location.getZ());
    }

    public BlockLocation crossProduct(final Loc location)
    {
        return new BlockLocation((int) ((this.y * location.getZ()) - (this.z * location.getY())), (int) ((this.z * location.getX()) - (this.x * location.getZ())), (int) ((this.x * location.getY()) - (this.y * location.getX())));
    }

    public boolean isInAABB(final Loc min, final Loc max)
    {
        return (this.x >= min.getX()) && (this.x <= max.getX()) && (this.y >= min.getY()) && (this.y <= max.getY()) && (this.z >= min.getZ()) && (this.z <= max.getZ());
    }

    public boolean isInSphere(final Loc origin, final double radius)
    {
        return (DioriteMathUtils.square(origin.getX() - this.x) + DioriteMathUtils.square(origin.getY() - this.y) + DioriteMathUtils.square(origin.getZ() - this.z)) <= DioriteMathUtils.square(radius);
    }

    public double distanceSquared(final BlockLocation location)
    {
        return this.distanceSquared(location.getX(), location.getY(), location.getZ());
    }

    public BlockLocation crossProduct(final BlockLocation location)
    {
        return new BlockLocation((this.y * location.getZ()) - (this.z * location.getY()), (this.z * location.getX()) - (this.x * location.getZ()), (this.x * location.getY()) - (this.y * location.getX()), this.world);
    }

    public boolean isInAABB(final BlockLocation min, final BlockLocation max)
    {
        return (this.x >= min.x) && (this.x <= max.x) && (this.y >= min.y) && (this.y <= max.y) && (this.z >= min.z) && (this.z <= max.z);
    }

    public boolean isInSphere(final BlockLocation origin, final double radius)
    {
        return (DioriteMathUtils.square(origin.x - this.x) + DioriteMathUtils.square(origin.y - this.y) + DioriteMathUtils.square(origin.z - this.z)) <= DioriteMathUtils.square(radius);
    }

    public ChunkPos getChunkPos()
    {
        return new ChunkPos(this.x >> 4, this.z >> 4, this.world);
    }

    @SuppressWarnings("MagicNumber")
    public long asLong()
    {
        return ((((long) this.x) & 0x3FFFFFF) << 38) | ((((long) this.y) & 0xFFF) << 26) | (((long) this.z) & 0x3FFFFFF);
    }

    public ImmutableLocation toLocation()
    {
        return new ImmutableLocation(this.x, this.y, this.z, this.world);
    }

    @SuppressWarnings("MagicNumber")
    public static BlockLocation fromLong(final long pos)
    {
        final int x = (int) (pos >> 38);
        final int y = (int) ((pos >> 26) & 0xFFF);
        final int z = (int) ((pos << 38) >> 38);
        return new BlockLocation(x, y, z);
    }

    @Override
    public int hashCode()
    {
        int result = this.x;
        result = (31 * result) + this.y;
        result = (31 * result) + this.z;
        return result;
    }

    @Override
    public boolean equals(final Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (! (o instanceof BlockLocation))
        {
            return false;
        }

        final BlockLocation that = (BlockLocation) o;

        return (this.x == that.x) && (this.y == that.y) && (this.z == that.z);

    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("x", this.x).append("y", this.y).append("z", this.z).toString();
    }
}
