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

package org.diorite.world;

import java.util.Optional;

import org.diorite.BlockFace;
import org.diorite.BlockLocation;
import org.diorite.material.BlockMaterialData;
import org.diorite.scheduler.Synchronizable;
import org.diorite.utils.math.geometry.BoundingBox;
import org.diorite.world.chunk.Chunk;

/**
 * Wrapper class for blocks, blocks are stored in {@link Chunk} as number arrays.
 * This class is used only for a temporary representation of block.
 * Values stored by wrapper aren't updated when oryginal block is edited
 */
public interface Block
{
    // TODO: meta-data of block, BlockState etc...

    /**
     * @return x coordinate of block in {@link World}
     */
    int getX();

    /**
     * @return y coordinate of block in {@link World}
     */
    int getY();

    /**
     * @return z coordinate of block in {@link World}
     */
    int getZ();

    /**
     * @return {@link World} where block exist.
     */
    World getWorld();

    /**
     * @return type (id and sub-id) of block
     */
    BlockMaterialData getType();

    /**
     * Returns tile entity of this block if exist.
     *
     * @return tile entity of this block.
     */
    Optional<TileEntity> getTileEntity();

    /**
     * Method to set type (id and sub-id) of block.
     * This method will only update local copy of it (this wrapper), block will be updated on map after calling {@link #update()} method
     *
     * @param type new type (id and sub-id) of block
     */
    void setType(BlockMaterialData type);

    Biome getBiome();

    /**
     * update wrapper data, get current data from map.
     */
    void update();

    /**
     * Get relative block using x/y/z coordinates
     * May return null if y {@literal >} maxHeight
     *
     * @param x number of blocks to go in x axis direction
     * @param y number of blocks to go in y axis direction
     * @param z number of blocks to go in z axis direction
     *
     * @return relative {@link Block}
     */
    Block getRelative(int x, int y, int z);

    /**
     * Get relative block in selected direction
     * May return null if y {@literal >} maxHeight
     *
     * @param face  direction
     * @param multi values from direction will be multipled by this value
     *
     * @return relative {@link Block}
     *
     * @see #getRelative(int, int, int)
     */
    default Block getRelative(final BlockFace face, final int multi)
    {
        return this.getRelative(face.getModX() * multi, face.getModY() * multi, face.getModZ() * multi);
    }

    /**
     * Get relative block
     * May return null if y {@literal >} maxHeight
     *
     * @param face direction
     *
     * @return relative {@link Block}
     *
     * @see #getRelative(BlockFace, int)
     * @see #getRelative(int, int, int)
     */
    default Block getRelative(final BlockFace face)
    {
        return this.getRelative(face, 1);
    }

    /**
     * @return {@link BlockLocation} of block
     */
    default BlockLocation getLocation()
    {
        return new BlockLocation(this.getX(), this.getY(), this.getZ(), this.getWorld());
    }

    /**
     * @return {@link Chunk} where block exist
     */
    default Chunk getChunk()
    {
        return this.getWorld().getChunkManager().getChunk(this.getLocation().getChunkPos());
    }

    /**
     * By default it uses {@link #getChunk()}
     *
     * @return object that can be used in scheduler.
     */
    default Synchronizable getSynchronizable()
    {
        return this.getChunk();
    }

    BoundingBox getBoundingBox();
}
