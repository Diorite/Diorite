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
 * Class with methods to create byte from two nibbles (half of byte), and get nibbles from byte.
 */
public final class NibblesToByte
{
    /**
     * Bits of nibble
     */
    public static final int BITS  = 0xf;
    /**
     * Size of nibble
     */
    public static final int SHIFT = 4;

    private NibblesToByte()
    {
    }

    /**
     * Creates a byte value from 2 nibble-bytes.
     *
     * @param a first value.
     * @param b second value.
     *
     * @return a byte which is the concatenation of a and b
     */
    public static byte pack(final byte a, final byte b)
    {
        return (byte) ((a << SHIFT) | (b & BITS));
    }

    /**
     * Gets the first 4-bit byte value from an byte key
     *
     * @param key to get from
     *
     * @return the first 4-bit byte value in the key
     */
    public static byte getA(final byte key)
    {
        return (byte) ((key >> SHIFT) & BITS);
    }

    /**
     * Gets the second 4-bit byte value from an byte key
     *
     * @param key to get from
     *
     * @return the second 4-bit byte value in the key
     */
    public static byte getB(final byte key)
    {
        return (byte) (key & BITS);
    }

    /**
     * Get both nibbles from an byte key.
     *
     * @param key to get from
     *
     * @return bytes array with both (2) values.
     */
    public static byte[] get(final byte key)
    {
        return new byte[]{getA(key), getB(key)};
    }
}
