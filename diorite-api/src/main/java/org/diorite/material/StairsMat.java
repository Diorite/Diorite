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
 * Representing stairs block.
 */
public interface StairsMat extends DirectionalMat
{
    byte UPSIDE_DOWN_FLAG = 0x4;

    /**
     * @return true if stairs are upside-down
     */
    boolean isUpsideDown();

    /**
     * Returns sub-type of Stairs based on upside-down state.
     *
     * @param upsideDown if staris should be upside-down.
     *
     * @return sub-type of Stairs
     */
    StairsMat getUpsideDown(boolean upsideDown);

    /**
     * Returns one of Stairs sub-type based on facing direction and upside-down state.
     * It will never return null.
     *
     * @param face       facing direction of stairs.
     * @param upsideDown if stairs should be upside-down.
     *
     * @return sub-type of Stairs
     */
    StairsMat getType(BlockFace face, boolean upsideDown);

    static byte combine(final BlockFace face, final boolean upside)
    {
        byte value = upside ? UPSIDE_DOWN_FLAG : 0x0;
        switch (face)
        {
            case WEST:
                value += 0x1;
                break;
            case SOUTH:
                value += 0x2;
                break;
            case NORTH:
                value += 0x3;
                break;
            default:
                break;
        }
        return value;
    }
}
