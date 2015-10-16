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
 * Class with methods to create long from two ints, and get ints from long.
 */
public final class IntsToLong
{
    /**
     * Bits of integer
     */
    public static final long BITS  = 0xFFFFFFFFL;
    /**
     * Size of integer
     */
    public static final int  SHIFT = 32;

    private IntsToLong()
    {
    }

    /**
     * Creates a long value from 2 ints.
     *
     * @param a first value.
     * @param b second value.
     *
     * @return a long which is the concatenation of a and b
     */
    public static long pack(final int a, final int b)
    {
        return ((long) a << SHIFT) | (b & BITS);
    }

    /**
     * Gets the first 32-bit integer value from an long key
     *
     * @param key to get from
     *
     * @return the first 32-bit integer value in the key
     */
    public static int getA(final long key)
    {
        return (int) ((key >> SHIFT) & BITS);
    }

    /**
     * Gets the second 32-bit integer value from an long key
     *
     * @param key to get from
     *
     * @return the second 32-bit integer value in the key
     */
    public static int getB(final long key)
    {
        return (int) (key & BITS);
    }

    /**
     * Get both integers from an long key.
     *
     * @param key to get from
     *
     * @return int array with both (2) values.
     */
    public static int[] get(final long key)
    {
        return new int[]{getA(key), getB(key)};
    }
}
