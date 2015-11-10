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

/**
 * enum with all slab types.
 */
public enum SlabTypeMat
{
    /**
     * Single slab on bottom half of block.
     */
    BOTTOM(false, 0x00),
    /**
     * Single slab on upper half of block.
     */
    UPPER(false, 0x08),
    /**
     * Double slab, full block.
     */
    FULL(true, 0x00),
    /**
     * Double slab, full block, only few slab blocks have this type.
     */
    SMOOTH_FULL(true, 0x08);

    private final boolean fullBlock;
    private final byte    flag;

    SlabTypeMat(final boolean fullBlock, final int flag)
    {
        this.fullBlock = fullBlock;
        this.flag = (byte) flag;
    }

    /**
     * @return if this is one of full block types. {@link #FULL} {@link #SMOOTH_FULL}
     */
    public boolean isFullBlock()
    {
        return this.fullBlock;
    }

    /**
     * @return sub-id flag used by all slab blocks.
     */
    public byte getFlag()
    {
        return this.flag;
    }
}
