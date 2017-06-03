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

import org.diorite.BlockLocation;
import org.diorite.Location;

/**
 * Represent object that store chunks.
 */
public interface ChunkContainer
{
    /**
     * Returns size in chunks of this container on X axis.
     *
     * @return size in chunks of this container on X axis.
     */
    int getSizeInChunksX();

    /**
     * Returns size in chunks of this container on Z axis.
     *
     * @return size in chunks of this container on Z axis.
     */
    int getSizeInChunksZ();

    /**
     * Returns chunk on given chunk location.
     *
     * @param x
     *         one of chunk coordinates.
     * @param z
     *         one of chunk coordinates.
     *
     * @return chunk on given chunk location.
     */
    Chunk getChunkAt(int x, int z);

    /**
     * Transform given map coordinates to valid chunk position on this world.
     *
     * @param x
     *         one of map coordinates.
     * @param z
     *         one of map coordinates.
     *
     * @return chunk position on this world that contains given location.
     */
    ChunkPosition toChunkPosition(double x, double z);

    /**
     * Returns chunk on given chunk location. <br>
     * {@link ChunkPosition#getWorld()} is ignored.
     *
     * @param chunkPosition
     *         location of chunk in world.
     *
     * @return chunk on given chunk location.
     */
    default Chunk getChunkAt(ChunkPosition chunkPosition)
    {
        return this.getChunkAt(chunkPosition.getX(), chunkPosition.getZ());
    }

    /**
     * Returns chunk on given map location. <br>
     *
     * @param x
     *         one of map coordinates.
     * @param z
     *         one of map coordinates.
     *
     * @return chunk on given map location.
     */
    default Chunk getChunkByLocation(double x, double z)
    {
        return this.getChunkAt(this.toChunkPosition(x, z));
    }

    /**
     * Returns chunk on given map location. <br>
     *
     * @param location
     *         location in world.
     *
     * @return chunk on given map location.
     */
    default Chunk getChunkByLocation(Location location)
    {
        return this.getChunkAt(this.toChunkPosition(location));
    }

    /**
     * Returns chunk on given map location. <br>
     *
     * @param location
     *         location in world.
     *
     * @return chunk on given map location.
     */
    default Chunk getChunkByLocation(BlockLocation location)
    {
        return this.getChunkAt(this.toChunkPosition(location));
    }

    /**
     * Transform given map coordinates to valid chunk position on this world.
     *
     * @param location
     *         location on map.
     *
     * @return chunk position on this world that contains given location.
     */
    default ChunkPosition toChunkPosition(BlockLocation location)
    {
        return this.toChunkPosition(location.getX(), location.getZ());
    }

    /**
     * Transform given map coordinates to valid chunk position on this world.
     *
     * @param location
     *         location on map.
     *
     * @return chunk position on this world that contains given location.
     */
    default ChunkPosition toChunkPosition(Location location)
    {
        return this.toChunkPosition(location.getX(), location.getZ());
    }
}
