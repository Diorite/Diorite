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

package org.diorite.material.blocks;

/**
 * enum will all stone type slabs
 */
public enum StoneSlabTypeMat
{
    STONE(0x00, false),
    SANDSTONE(0x01, false),
    WOODEN(0x02, false),
    COBBLESTONE(0x03, false),
    BRICKS(0x04, false),
    STONE_BRICKS(0x05, false),
    NETHER_BRICKS(0x06, false),
    QUARTZ(0x07, false),
    RED_SANDSTONE(0x00, true);

    private final byte    flag;
    private final boolean isSecondStoneSlabID;

    StoneSlabTypeMat(final int flag, final boolean isFirstId)
    {
        this.isSecondStoneSlabID = isFirstId;
        this.flag = (byte) flag;
    }

    /**
     * @return sub-id flag for that type.
     */
    public byte getFlag()
    {
        return this.flag;
    }

    /**
     * @return is this type is on second slab it.
     */
    public boolean isSecondStoneSlabID()
    {
        return this.isSecondStoneSlabID;
    }
}
