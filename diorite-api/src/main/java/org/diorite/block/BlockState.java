/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2016. Diorite (by Bartłomiej Mazur (aka GotoFinal))
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

package org.diorite.block;

import org.diorite.material.BlockMaterialData;
import org.diorite.world.World;

/**
 * Represents block state.
 */
public interface BlockState
{
    /**
     * @return x coordinate of block in this block state in {@link World}.
     */
    int getX();

    /**
     * @return y coordinate of block in this block state in {@link World}.
     */
    int getY();

    /**
     * @return z coordinate of block in this block state in {@link World}.
     */
    int getZ();

    /**
     * @return block of this block state.
     */
    Block getBlock();

    /**
     * @return type (id and sub-id) of block in this block state.
     */
    BlockMaterialData getType();

    /**
     * Method to set type (id and sub-id) of block in this block state.
     * This method will only update local copy of it (this wrapper), block will be updated on map after calling {@link #update()} method.
     *
     * @param type new type (id and sub-id) of block in this block state.
     */
    void setType(BlockMaterialData type);

    /**
     * @return {@link World} where block of this block state exist.
     */
    World getWorld();

    /**
     * @return {@link BlockLocation} of block of this block state.
     */
    default BlockLocation getLocation()
    {
        return new BlockLocation(this.getX(), this.getY(), this.getZ(), this.getWorld());
    }

    /**
     * Updates block of this block state.
     *
     * @return true if update was successful.
     */
    boolean update();

    /**
     * Updates block of this block state.
     *
     * @param force to force update.
     *
     * @return true if update was successful.
     */
    boolean update(boolean force);

    /**
     * Updates block of this block state.
     *
     * @param force to force update.
     * @param applyPhysics to apply physics to the block.
     *
     * @return true if update was successful.
     */
    boolean update(boolean force, boolean applyPhysics);

    /**
     * @return true if block of this block state if placed.
     */
    boolean isPlaced();
}
