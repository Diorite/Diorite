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

import org.diorite.material.blocks.DirtMat;
import org.diorite.material.blocks.QuartzBlockMat;
import org.diorite.material.blocks.SandMat;

/**
 * enum with some common types (variants) of blocks.
 * Block supproting variant may (and mostly it will) supprot only few of them.
 * If block don't supprot given variant, {@link #CLASSIC} should be ussed instead.
 */
public enum VariantMat
{
    /**
     * Default variant.
     */
    CLASSIC,
    /**
     * Smooth, polished, variant.
     */
    SMOOTH,
    /**
     * Chiseled variant.
     */
    CHISELED,
    /**
     * Mossy variant.
     */
    MOSSY,
    /**
     * Cracked variant.
     */
    CRACKED,
    /**
     * Red variant, used only by {@link SandMat} at this moment
     */
    RED,
    /**
     * Pillar vertical (up-down), currently used only by {@link QuartzBlockMat}
     */
    PILLAR_VERTICAL,
    /**
     * Pillar north-south, currently used only by {@link QuartzBlockMat}
     */
    PILLAR_NORTH_SOUTH,
    /**
     * Pillar east-west, currently used only by {@link QuartzBlockMat}
     */
    PILLAR_EAST_WEST,
    /**
     * Coarse variant, curranlty used only by {@link DirtMat}
     */
    COARSE,
    /**
     * Podzdol variant, curranlty used only by {@link DirtMat}
     */
    PODZOL
}
