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
 * Representing slab-type block.
 */
public interface SlabMat
{
    /**
     * @return {@link SlabTypeMat} type of slab.
     */
    SlabTypeMat getSlabType();

    /**
     * Returns sub-type of block, based on {@link SlabTypeMat}.
     *
     * @param type type of slab..
     *
     * @return sub-type of block
     */
    SlabMat getSlab(SlabTypeMat type);

    /**
     * @return {@link SlabTypeMat#UPPER} type of slab.
     *
     * @see SlabMat#getSlab(SlabTypeMat)
     */
    default SlabMat getUpperPart()
    {
        return this.getSlab(SlabTypeMat.UPPER);
    }

    /**
     * @return {@link SlabTypeMat#BOTTOM} type of slab.
     *
     * @see SlabMat#getSlab(SlabTypeMat)
     */
    default SlabMat getBottomPart()
    {
        return this.getSlab(SlabTypeMat.BOTTOM);
    }

    /**
     * @return {@link SlabTypeMat#FULL} type of slab.
     *
     * @see SlabMat#getSlab(SlabTypeMat)
     */
    default SlabMat getFullSlab()
    {
        return this.getSlab(SlabTypeMat.FULL);
    }

    /**
     * @return {@link SlabTypeMat#SMOOTH_FULL} type of slab.
     *
     * @see SlabMat#getSlab(SlabTypeMat)
     */
    default SlabMat getFullSmoothSlab()
    {
        return this.getSlab(SlabTypeMat.SMOOTH_FULL);
    }

    /**
     * @return true if this is one of double-slab block type.
     */
    default boolean isFullBlock()
    {
        return this.getSlabType().isFullBlock();
    }
}
