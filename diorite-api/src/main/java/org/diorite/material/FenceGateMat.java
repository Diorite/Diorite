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
 * Representing fence gate blocks.
 */
@SuppressWarnings("JavaDoc")
public interface FenceGateMat extends DirectionalMat, OpenableMat
{
    /**
     * bit flag for open gate state.
     */
    byte OPEN_FLAG = 0x4;

    /**
     * Returns one of gate sub-type based on facing direction and open state.
     * It will never return null.
     *
     * @param face facing direction of gate.
     * @param open if gate should be open.
     *
     * @return sub-type of gate
     */
    FenceGateMat getType(BlockFace face, boolean open);

    static byte combine(final BlockFace face, final boolean open)
    {
        byte result = open ? OPEN_FLAG : 0x0;
        switch (face)
        {
            case WEST:
                result |= 0x1;
                break;
            case NORTH:
                result |= 0x2;
                break;
            case EAST:
                result |= 0x3;
                break;
            default:
                break;
        }
        return result;
    }
}
