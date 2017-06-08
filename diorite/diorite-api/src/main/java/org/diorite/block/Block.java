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

import org.diorite.BlockLocation;
import org.diorite.material.BlockType;
import org.diorite.world.World;

/**
 * Represent block on map, this object does not store any state of block, each operator fetch new data from block source.
 */
public interface Block
{
    /**
     * Returns type of this block.
     *
     * @return type of this block.
     */
    BlockType getType();

    /**
     * Returns location of this block. (relative to world)
     *
     * @return location of this block. (relative to world)
     */
    BlockLocation getLocation();

    /**
     * Returns location of this block on `x` axis of world.
     *
     * @return location of this block on `x` axis of world.
     */
    int getX();

    /**
     * Returns location of this block on `y` axis of world.
     *
     * @return location of this block on `y` axis of world.
     */
    int getY();

    /**
     * Returns location of this block on `z` axis of world.
     *
     * @return location of this block on `z` axis of world.
     */
    int getZ();

    /**
     * Returns world where block is located.
     *
     * @return world where block is located.
     */
    World getWorld();
}
