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

package org.diorite.world.chunk;

import javax.annotation.Nullable;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.world.World;

/**
 * Represents position of chunk.
 */
public final class ChunkPosition
{
    private final           int   x;
    private final           int   z;
    private final @Nullable World world;

    /**
     * Construct new chunk position object.
     *
     * @param x
     *         one of chunk coordinates.
     * @param z
     *         one of chunk coordinates.
     * @param world
     *         world of chunk.
     */
    public ChunkPosition(int x, int z, @Nullable World world)
    {
        this.x = x;
        this.z = z;
        this.world = world;
    }

    /**
     * Construct new chunk position object.
     *
     * @param x
     *         one of chunk coordinates.
     * @param z
     *         one of chunk coordinates.
     */
    public ChunkPosition(int x, int z)
    {
        this.x = x;
        this.z = z;
        this.world = null;
    }

    /**
     * Returns coordinate of chunk in x axis.
     *
     * @return coordinate of chunk in x axis.
     */
    public int getX()
    {
        return this.x;
    }

    /**
     * Returns coordinate of chunk in z axis.
     *
     * @return coordinate of chunk in z axis.
     */
    public int getZ()
    {
        return this.z;
    }

    /**
     * Returns world of this chunk.
     *
     * @return world of this chunk.
     */
    @Nullable
    public World getWorld()
    {
        return this.world;
    }

    public ChunkPosition setWorld(World world)
    {
        return new ChunkPosition(this.x, this.z, world);
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
        return this.world.getChunkAt(this);
    }

    /**
     * Adds other position and return result as new object.
     *
     * @param position
     *         position of other chunk.
     *
     * @return new instance of chunk location.
     */
    public ChunkPosition add(ChunkPosition position)
    {
        return new ChunkPosition(this.x + position.x, this.z + position.z, this.world);
    }

    /**
     * Adds other position and return result as new object.
     *
     * @param x
     *         one of other chunk coordinates.
     * @param z
     *         one of other chunk coordinates.
     *
     * @return new instance of chunk location.
     */
    public ChunkPosition add(int x, int z)
    {
        return new ChunkPosition(this.x + x, this.z + z, this.world);
    }

    /**
     * Subtracts other position and return result as new object.
     *
     * @param position
     *         position of other chunk.
     *
     * @return new instance of chunk location.
     */
    public ChunkPosition subtract(ChunkPosition position)
    {
        return new ChunkPosition(this.x - position.x, this.z - position.z, this.world);
    }

    /**
     * Subtracts other position and return result as new object.
     *
     * @param x
     *         one of other chunk coordinates.
     * @param z
     *         one of other chunk coordinates.
     *
     * @return new instance of chunk location.
     */
    public ChunkPosition subtract(int x, int z)
    {
        return new ChunkPosition(this.x - x, this.z - z, this.world);
    }

    /**
     * Zero this location's coordinate components.
     *
     * @return new instance of chunk location.
     */
    public ChunkPosition zero()
    {
        return new ChunkPosition(this.x, this.z, this.world);
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
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (! (o instanceof ChunkPosition))
        {
            return false;
        }

        ChunkPosition chunkPosition = (ChunkPosition) o;

        // check world only if both of them use it
        return (this.x == chunkPosition.x) && (this.z == chunkPosition.z) && ((this.world == null) || (chunkPosition.world == null) || (this.world.equals(
                chunkPosition.world)));

    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("x", this.x).append("z", this.z)
                                                                          .append("world", (this.world == null) ? null : this.world.getName()).toString();
    }
}
