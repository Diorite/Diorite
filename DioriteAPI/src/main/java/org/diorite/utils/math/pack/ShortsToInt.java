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

package org.diorite.utils.math.pack;

/**
 * Class with methods to create int from two shorts, and get shorts from int.
 */
public final class ShortsToInt
{
    /**
     * Bits of short
     */
    public static final int BITS  = 0xFFFF;
    /**
     * Size of short
     */
    public static final int SHIFT = 16;

    private ShortsToInt()
    {
    }

    /**
     * Creates a int value from 2 shorts.
     *
     * @param a first value.
     * @param b second value.
     *
     * @return a int which is the concatenation of a and b
     */
    public static int pack(final short a, final short b)
    {
        return ((int) a << SHIFT) | (b & BITS);
    }

    /**
     * Gets the first 16-bit short value from an int key
     *
     * @param key to get from
     *
     * @return the first 16-bit short value in the key
     */
    public static short getA(final int key)
    {
        return (short) ((key >> SHIFT) & BITS);
    }

    /**
     * Gets the second 16-bit short value from an int key
     *
     * @param key to get from
     *
     * @return the second 16-bit short value in the key
     */
    public static short getB(final int key)
    {
        return (short) (key & BITS);
    }

    /**
     * Get both shorts from an int key.
     *
     * @param key to get from
     *
     * @return short array with both (2) values.
     */
    public static short[] get(final int key)
    {
        return new short[]{getA(key), getB(key)};
    }
}
