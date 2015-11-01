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

package org.diorite.material;

import org.diorite.BlockFace;

/**
 * Represent blocks that can be placed in one axis, like NORTH-SOUTH
 */
public interface RotatableMat extends DirectionalMat
{
    /**
     * @return rotate axis of block.
     */
    RotateAxisMat getRotateAxis();

    /**
     * Returns sub-type of block, based on {@link RotateAxisMat}.
     *
     * @param axis rotate axis of block.
     *
     * @return sub-type of block
     */
    RotatableMat getRotated(RotateAxisMat axis);

    @Override
    default BlockFace getBlockFacing()
    {
        switch (this.getRotateAxis())
        {
            case UP_DOWN:
                return BlockFace.UP;
            case EAST_WEST:
                return BlockFace.EAST;
            case NORTH_SOUTH:
                return BlockFace.NORTH;
            case NONE:
                return BlockFace.SELF;
            default:
                return null;
        }
    }
}
