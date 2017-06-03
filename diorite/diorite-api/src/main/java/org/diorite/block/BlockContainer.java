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

package org.diorite.block;

import javax.annotation.Nullable;

import org.diorite.BlockLocation;
import org.diorite.Location;

/**
 * Represent something that can contains blocks, like a world map or chunk.
 */
public interface BlockContainer
{
    /**
     * Returns parent block container, null if none.
     *
     * @return parent block container, null if none.
     */
    @Nullable
    BlockContainer getParent();

    /**
     * Returns point on parent container that is origin of this container. <br>
     * So {@code assertEquals(this.getBlockAt(location), this.getParent().getBlockAt(this.getRelativeOrigin().add(location))) }
     *
     * @return point on parent container that is origin of this container.
     *
     * @exception IllegalStateException
     *         if this container does not have parent.
     */
    BlockLocation getRelativeOrigin() throws IllegalStateException;

    /**
     * Returns size of this container on X axis.
     *
     * @return size of this container on X axis.
     */
    int getSizeX();

    /**
     * Returns size of this container on Y axis.
     *
     * @return size of this container on Y axis.
     */
    int getSizeY();

    /**
     * Returns size of this container on Z axis.
     *
     * @return size of this container on Z axis.
     */
    int getSizeZ();

    /**
     * Returns block at given position, or throws exception if position can't be resolved to block. (block outside container)
     *
     * @param x
     *         location of block on x axis.
     * @param y
     *         location of block on y axis.
     * @param z
     *         location of block on z axis.
     *
     * @return block at given position, or throws exception if position can't be resolved to block.
     *
     * @exception IllegalArgumentException
     *         if position can't be resolved to block.
     */
    Block getBlockAt(int x, int y, int z) throws IllegalArgumentException;

    /**
     * Returns block at given position, or throws exception if position can't be resolved to block.
     *
     * @param blockLocation
     *         location of block.
     *
     * @return block at given position, or throws exception if position can't be resolved to block.
     *
     * @exception IllegalArgumentException
     *         if position can't be resolved to block.
     */
    default Block getBlockAt(BlockLocation blockLocation) throws IllegalArgumentException
    {
        return this.getBlockAt(blockLocation.getX(), blockLocation.getY(), blockLocation.getZ());
    }

    /**
     * Returns block at given position, or throws exception if position can't be resolved to block.
     *
     * @param location
     *         location of block.
     *
     * @return block at given position, or throws exception if position can't be resolved to block.
     *
     * @exception IllegalArgumentException
     *         if position can't be resolved to block.
     */
    default Block getBlockAt(Location location)
    {
        return this.getBlockAt(location.getBlockX(), location.getBlockY(), location.getBlockZ());
    }
}
