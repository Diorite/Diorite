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

package org.diorite.world;

import org.diorite.BlockLocation;
import org.diorite.Location;
import org.diorite.block.Block;
import org.diorite.commons.objects.Nameable;
import org.diorite.world.chunk.Chunk;
import org.diorite.world.chunk.ChunkPosition;

/**
 * Represents minecraft world, if world isn't loaded then all methods that need to fetch data from it will result in exception being thrown.
 */
public interface World extends Nameable
{
    /**
     * Returns name of world. Name of world is unique, but different worlds may use this same name over time (after removing it and regenerating), if you need
     * something more unique, use UUID of world.
     *
     * @return name of world.
     */
    @Override
    String getName();

    /**
     * Returns true if world is loaded.
     *
     * @return true if world is loaded.
     */
    boolean isLoaded();

    /**
     * Returns true if this is valid world instance. <br>
     * This may return false if world is unloaded and not tracked by server anymore, so after loading world again this instance will be still empty.
     *
     * @return true if this is valid world instance.
     */
    boolean isValid();

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
     * Returns block at given location.
     *
     * @param x
     *         one of chunk coordinates.
     * @param y
     *         one of chunk coordinates.
     * @param z
     *         one of chunk coordinates.
     *
     * @return block at given location.
     */
    Block getBlockAt(int x, int y, int z);

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

    /**
     * Returns block at given location.
     *
     * @param location
     *         location of block.
     *
     * @return block at given location.
     */
    default Block getBlockAt(Location location)
    {
        return this.getBlockAt(location.getBlockX(), location.getBlockY(), location.getBlockZ());
    }

    /**
     * Returns block at given location.
     *
     * @param location
     *         location of block.
     *
     * @return block at given location.
     */
    default Block getBlockAt(BlockLocation location)
    {
        return this.getBlockAt(location.getX(), location.getY(), location.getZ());
    }
}
