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

package org.diorite.utils.math.endian;

import static org.diorite.utils.math.endian.EndianUtil.BYTE_MASK;
import static org.diorite.utils.math.endian.EndianUtil.BYTE_SIZE;
import static org.diorite.utils.math.endian.EndianUtil.INT_MASK;
import static org.diorite.utils.math.endian.EndianUtil.INT_SIZE;
import static org.diorite.utils.math.endian.EndianUtil.NIBBLE_MASK;
import static org.diorite.utils.math.endian.EndianUtil.NIBBLE_SIZE;
import static org.diorite.utils.math.endian.EndianUtil.SHORT_MASK;
import static org.diorite.utils.math.endian.EndianUtil.SHORT_SIZE;


import org.diorite.utils.collections.arrays.NibbleArray;

/**
 * Utility class to pack/unpack number types, like 2 ints into long etc... <br>
 * This class use big-endian order.
 */
@SuppressWarnings({"MultiplyOrDivideByPowerOfTwo", "MagicNumber"})
public final class BigEndianUtils
{
    private BigEndianUtils()
    {
    }

    private static final EndianUtil endianInst = new BigEndianInst();

    public static EndianUtil getAsInterface()
    {
        return endianInst;
    }

    public static byte toByte(final byte a, final byte b)
    {
        return (byte) ((a << NIBBLE_SIZE) | b);
    }

    public static short toShort(final byte a, final byte b)
    {
        return (short) ((a << BYTE_SIZE) | (b & BYTE_MASK));
    }

    public static short toShort(final byte a, final byte b, final byte c, final byte d)
    {
        return (short) ((a << (NIBBLE_SIZE * 3)) | ((b & NIBBLE_MASK) << (NIBBLE_SIZE * 2)) | ((c & NIBBLE_MASK) << NIBBLE_SIZE) | (d & NIBBLE_MASK));
    }

    public static int toInt(final short a, final short b)
    {
        return (a << SHORT_SIZE) | (b & SHORT_MASK);
    }

    public static int toInt(final byte a, final byte b, final byte c, final byte d)
    {
        return (a << (BYTE_SIZE * 3)) | ((b & BYTE_MASK) << (BYTE_SIZE * 2)) | ((c & BYTE_MASK) << BYTE_SIZE) | (d & BYTE_MASK);
    }

    public static int toInt(final byte a, final byte b, final byte c, final byte d, final byte e, final byte f, final byte g, final byte h)
    {
        return ((a & NIBBLE_MASK) << (NIBBLE_SIZE * 7)) | ((b & NIBBLE_MASK) << (NIBBLE_SIZE * 6)) | ((c & NIBBLE_MASK) << (NIBBLE_SIZE * 5)) | ((d & NIBBLE_MASK) << (NIBBLE_SIZE * 4)) | (e << (NIBBLE_SIZE * 3)) | ((f & NIBBLE_MASK) << (NIBBLE_SIZE * 2)) | ((g & NIBBLE_MASK) << NIBBLE_SIZE) | (h & NIBBLE_MASK);
    }

    public static long toLong(final int a, final int b)
    {
        return ((long) a << INT_SIZE) | (b & INT_MASK);
    }

    public static long toLong(final short a, final short b, final short c, final short d)
    {
        return ((long) a << (SHORT_SIZE * 3)) | (((long) b & SHORT_MASK) << (SHORT_SIZE * 2)) | (((long) c & SHORT_MASK) << SHORT_SIZE) | ((long) d & SHORT_MASK);
    }

    public static long toLong(final byte a, final byte b, final byte c, final byte d, final byte e, final byte f, final byte g, final byte h)
    {
        return (((long) a & BYTE_MASK) << (BYTE_SIZE * 7)) | (((long) b & BYTE_MASK) << (BYTE_SIZE * 6)) | (((long) c & BYTE_MASK) << (BYTE_SIZE * 5)) | (((long) d & BYTE_MASK) << (BYTE_SIZE * 4)) | ((long) e << (BYTE_SIZE * 3)) | (((long) f & BYTE_MASK) << (BYTE_SIZE * 2)) | (((long) g & BYTE_MASK) << BYTE_SIZE) | ((long) h & BYTE_MASK);
    }

    public static long toLong(final byte a, final byte b, final byte c, final byte d, final byte e, final byte f, final byte g, final byte h, final byte i, final byte j, final byte k, final byte l, final byte m, final byte n, final byte o, final byte p)
    {
        return (((long) a & NIBBLE_MASK) << (NIBBLE_SIZE * 15)) | (((long) b & NIBBLE_MASK) << (NIBBLE_SIZE * 14)) | (((long) c & NIBBLE_MASK) << (NIBBLE_SIZE * 13)) | (((long) d & NIBBLE_MASK) << (NIBBLE_SIZE * 12)) | (((long) e & NIBBLE_MASK) << (NIBBLE_SIZE * 11)) | (((long) f & NIBBLE_MASK) << (NIBBLE_SIZE * 10)) | (((long) g & NIBBLE_MASK) << (NIBBLE_SIZE * 9)) | (((long) h & NIBBLE_MASK) << (NIBBLE_SIZE * 8)) | (((long) i & NIBBLE_MASK) << (NIBBLE_SIZE * 7)) | (((long) j & NIBBLE_MASK) << (NIBBLE_SIZE * 6)) | (((long) k & NIBBLE_MASK) << (NIBBLE_SIZE * 5)) | (((long) l & NIBBLE_MASK) << (NIBBLE_SIZE * 4)) | ((long) m << (NIBBLE_SIZE * 3)) | (((long) n & NIBBLE_MASK) << (NIBBLE_SIZE * 2)) | (((long) o & NIBBLE_MASK) << NIBBLE_SIZE) | ((long) p & NIBBLE_MASK);
    }

    public static byte toByteFromNibbles(final byte[] nibbles)
    {
        return toByte(nibbles[0], nibbles[1]);
    }

    public static byte toByteFromNibbles(final byte[] nibbles, final int start)
    {
        return toByte(nibbles[start], nibbles[start + 1]);
    }

    public static byte toByte(final NibbleArray nibbles)
    {
        return toByte(nibbles.get(0), nibbles.get(1));
    }

    public static byte toByte(final NibbleArray nibbles, final int start)
    {
        return toByte(nibbles.get(start), nibbles.get(start + 1));
    }

    public static short toShortFromNibbles(final byte[] nibbles)
    {
        return toShort(nibbles[0], nibbles[1], nibbles[2], nibbles[3]);
    }

    public static short toShortFromNibbles(final byte[] nibbles, final int start)
    {
        return toShort(nibbles[start], nibbles[start + 1], nibbles[start + 2], nibbles[start + 3]);
    }

    public static short toShort(final NibbleArray nibbles)
    {
        return toShort(nibbles.get(0), nibbles.get(1), nibbles.get(2), nibbles.get(3));
    }

    public static short toShort(final NibbleArray nibbles, final int start)
    {
        return toShort(nibbles.get(start), nibbles.get(start + 1), nibbles.get(start + 2), nibbles.get(start + 3));
    }

    public static short toShort(final byte[] bytes)
    {
        return toShort(bytes[0], bytes[1]);
    }

    public static short toShort(final byte[] bytes, final int start)
    {
        return toShort(bytes[start], bytes[start + 1]);
    }

    public static int toIntFromNibbles(final byte[] nibbles)
    {
        return toInt(nibbles[0], nibbles[1], nibbles[2], nibbles[3], nibbles[4], nibbles[5], nibbles[6], nibbles[7]);
    }

    public static int toIntFromNibbles(final byte[] nibbles, final int start)
    {
        return toInt(nibbles[start], nibbles[start + 1], nibbles[start + 2], nibbles[start + 3], nibbles[start + 4], nibbles[start + 5], nibbles[start + 6], nibbles[start + 7]);
    }

    public static int toInt(final NibbleArray nibbles)
    {
        return toInt(nibbles.get(0), nibbles.get(1), nibbles.get(2), nibbles.get(3), nibbles.get(4), nibbles.get(5), nibbles.get(6), nibbles.get(7));
    }

    public static int toInt(final NibbleArray nibbles, final int start)
    {
        return toInt(nibbles.get(start), nibbles.get(start + 1), nibbles.get(start + 2), nibbles.get(start + 3), nibbles.get(start + 4), nibbles.get(start + 5), nibbles.get(start + 6), nibbles.get(start + 7));
    }

    public static int toInt(final byte[] bytes)
    {
        return toInt(bytes[0], bytes[1], bytes[2], bytes[3]);
    }

    public static int toInt(final byte[] bytes, final int start)
    {
        return toInt(bytes[start], bytes[start + 1], bytes[start + 2], bytes[start + 3]);
    }

    public static int toInt(final short[] shorts)
    {
        return toInt(shorts[0], shorts[1]);
    }

    public static int toInt(final short[] shorts, final int start)
    {
        return toInt(shorts[start], shorts[start + 1]);
    }

    public static long toLongFromNibbles(final byte[] nibbles)
    {
        return toLong(nibbles[0], nibbles[1], nibbles[2], nibbles[3], nibbles[4], nibbles[5], nibbles[6], nibbles[7], nibbles[8], nibbles[9], nibbles[10], nibbles[11], nibbles[12], nibbles[13], nibbles[14], nibbles[15]);
    }

    public static long toLongFromNibbles(final byte[] nibbles, final int start)
    {
        return toLong(nibbles[start], nibbles[start + 1], nibbles[start + 2], nibbles[start + 3], nibbles[start + 4], nibbles[start + 5], nibbles[start + 6], nibbles[start + 7], nibbles[start + 8], nibbles[start + 9], nibbles[start + 10], nibbles[start + 11], nibbles[start + 12], nibbles[start + 13], nibbles[start + 14], nibbles[start + 15]);
    }

    public static long toLong(final NibbleArray nibbles)
    {
        return toLong(nibbles.get(0), nibbles.get(1), nibbles.get(2), nibbles.get(3), nibbles.get(4), nibbles.get(5), nibbles.get(6), nibbles.get(7), nibbles.get(8), nibbles.get(9), nibbles.get(10), nibbles.get(11), nibbles.get(12), nibbles.get(13), nibbles.get(14), nibbles.get(15));
    }

    public static long toLong(final NibbleArray nibbles, final int start)
    {
        return toLong(nibbles.get(start), nibbles.get(start + 1), nibbles.get(start + 2), nibbles.get(start + 3), nibbles.get(start + 4), nibbles.get(start + 5), nibbles.get(start + 6), nibbles.get(start + 7), nibbles.get(start + 8), nibbles.get(start + 9), nibbles.get(start + 10), nibbles.get(start + 11), nibbles.get(start + 12), nibbles.get(start + 13), nibbles.get(start + 14), nibbles.get(start + 15));
    }

    public static long toLong(final byte[] bytes)
    {
        return toLong(bytes[0], bytes[1], bytes[2], bytes[3], bytes[4], bytes[5], bytes[6], bytes[7]);
    }

    public static long toLong(final byte[] bytes, final int start)
    {
        return toLong(bytes[start], bytes[start + 1], bytes[start + 2], bytes[start + 3], bytes[start + 4], bytes[start + 5], bytes[start + 6], bytes[start + 7]);
    }

    public static long toLong(final short[] shorts)
    {
        return toLong(shorts[0], shorts[1], shorts[2], shorts[3]);
    }

    public static long toLong(final short[] shorts, final int start)
    {
        return toLong(shorts[start], shorts[start + 1], shorts[start + 2], shorts[start + 3]);
    }

    public static long toLong(final int[] ints)
    {
        return toLong(ints[0], ints[1]);
    }

    public static long toLong(final int[] ints, final int start)
    {
        return toLong(ints[start], ints[start + 1]);
    }

    /*
     * From byte
     */

    public static byte getNibbleA(final byte x)
    {
        return (byte) ((x >> NIBBLE_SIZE) & NIBBLE_MASK);
    }

    public static byte getNibbleB(final byte x)
    {
        return (byte) (x & NIBBLE_MASK);
    }

    public static byte getNibble(final byte x, int index)
    {
        index = validateIndex("nibbles", "byte", index, 2);
        return (byte) ((x >> (NIBBLE_SIZE * index)) & NIBBLE_MASK);
    }

    /*
     * From short
     */

    public static byte getByteA(final short x)
    {
        return (byte) (x >> BYTE_SIZE);
    }

    public static byte getByteB(final short x)
    {
        return (byte) x;
    }

    public static byte getByte(final short x, int index)
    {
        index = validateIndex("bytes", "short", index, 2);
        return (byte) (x >> (BYTE_SIZE * index));
    }

    public static byte getNibbleA(final short x)
    {
        return (byte) ((x >> NIBBLE_SIZE) & NIBBLE_MASK);
    }

    public static byte getNibbleB(final short x)
    {
        return (byte) ((x >> (NIBBLE_SIZE * 2)) & NIBBLE_MASK);
    }

    public static byte getNibbleC(final short x)
    {
        return (byte) ((x >> (NIBBLE_SIZE * 3)) & NIBBLE_MASK);
    }

    public static byte getNibbleD(final short x)
    {
        return (byte) (x & NIBBLE_MASK);
    }

    public static byte getNibble(final short x, int index)
    {
        index = validateIndex("nibbles", "short", index, 4);
        return (byte) (x >> ((NIBBLE_SIZE * index) & NIBBLE_MASK));
    }

    /*
     * From int
     */

    public static short getShortA(final int x)
    {
        return (short) (x >> SHORT_SIZE);
    }

    public static short getShortB(final int x)
    {
        return (short) x;
    }

    public static short getShort(final int x, int index)
    {
        index = validateIndex("shorts", "int", index, 2);
        return (byte) (x >> (SHORT_SIZE * index));
    }

    public static byte getByteA(final int x)
    {
        return (byte) (x >> (BYTE_SIZE * 3));
    }

    public static byte getByteB(final int x)
    {
        return (byte) (x >> (BYTE_SIZE * 2));
    }

    public static byte getByteC(final int x)
    {
        return (byte) (x >> BYTE_SIZE);
    }

    public static byte getByteD(final int x)
    {
        return (byte) (x);
    }

    public static byte getByte(final int x, int index)
    {
        index = validateIndex("bytes", "int", index, 4);
        return (byte) (x >> (BYTE_SIZE * index));
    }

    public static byte getNibbleA(final int x)
    {
        return (byte) ((x >> NIBBLE_SIZE) & NIBBLE_MASK);
    }

    public static byte getNibbleB(final int x)
    {
        return (byte) ((x >> (NIBBLE_SIZE * 2)) & NIBBLE_MASK);
    }

    public static byte getNibbleC(final int x)
    {
        return (byte) ((x >> (NIBBLE_SIZE * 3)) & NIBBLE_MASK);
    }

    public static byte getNibbleD(final int x)
    {
        return (byte) ((x >> (NIBBLE_SIZE * 4)) & NIBBLE_MASK);
    }

    public static byte getNibbleE(final int x)
    {
        return (byte) ((x >> (NIBBLE_SIZE * 5)) & NIBBLE_MASK);
    }

    public static byte getNibbleF(final int x)
    {
        return (byte) ((x >> (NIBBLE_SIZE * 6)) & NIBBLE_MASK);
    }

    public static byte getNibbleG(final int x)
    {
        return (byte) ((x >> (NIBBLE_SIZE * 7)) & NIBBLE_MASK);
    }

    public static byte getNibbleH(final int x)
    {
        return (byte) (x & NIBBLE_MASK);
    }

    public static byte getNibble(final int x, int index)
    {
        index = validateIndex("nibbles", "int", index, 8);
        return (byte) (x >> ((NIBBLE_SIZE * index) & NIBBLE_MASK));
    }

    /*
     * From long
     */

    public static int getIntA(final long x)
    {
        return (int) (x >> INT_SIZE);
    }

    public static int getIntB(final long x)
    {
        return (int) x;
    }

    public static int getInt(final long x, int index)
    {
        index = validateIndex("ints", "long", index, 2);
        return (byte) (x >> (INT_SIZE * index));
    }

    public static short getShortA(final long x)
    {
        return (short) (x >> (SHORT_SIZE * 3));
    }

    public static short getShortB(final long x)
    {
        return (short) (x >> (SHORT_SIZE * 2));
    }

    public static short getShortC(final long x)
    {
        return (short) (x >> SHORT_SIZE);
    }

    public static short getShortD(final long x)
    {
        return (short) (x);
    }

    public static short getShort(final long x, int index)
    {
        index = validateIndex("shorts", "long", index, 4);
        return (byte) (x >> (SHORT_SIZE * index));
    }

    public static byte getByteA(final long x)
    {
        return (byte) (x >> (BYTE_SIZE * 7));
    }

    public static byte getByteB(final long x)
    {
        return (byte) (x >> (BYTE_SIZE * 6));
    }

    public static byte getByteC(final long x)
    {
        return (byte) (x >> (BYTE_SIZE * 5));
    }

    public static byte getByteD(final long x)
    {
        return (byte) (x >> (BYTE_SIZE * 4));
    }

    public static byte getByteE(final long x)
    {
        return (byte) (x >> (BYTE_SIZE * 3));
    }

    public static byte getByteF(final long x)
    {
        return (byte) (x >> (BYTE_SIZE * 2));
    }

    public static byte getByteG(final long x)
    {
        return (byte) (x >> BYTE_SIZE);
    }

    public static byte getByteH(final long x)
    {
        return (byte) (x);
    }

    public static byte getByte(final long x, int index)
    {
        index = validateIndex("bytes", "long", index, 8);
        return (byte) (x >> (BYTE_SIZE * index));
    }

    public static byte getNibbleA(final long x)
    {
        return (byte) ((x >> (NIBBLE_SIZE * 15)) & NIBBLE_MASK);
    }

    public static byte getNibbleB(final long x)
    {
        return (byte) ((x >> (NIBBLE_SIZE * 14)) & NIBBLE_MASK);
    }

    public static byte getNibbleC(final long x)
    {
        return (byte) ((x >> (NIBBLE_SIZE * 13)) & NIBBLE_MASK);
    }

    public static byte getNibbleD(final long x)
    {
        return (byte) ((x >> (NIBBLE_SIZE * 12)) & NIBBLE_MASK);
    }

    public static byte getNibbleE(final long x)
    {
        return (byte) ((x >> (NIBBLE_SIZE * 11)) & NIBBLE_MASK);
    }

    public static byte getNibbleF(final long x)
    {
        return (byte) ((x >> (NIBBLE_SIZE * 10)) & NIBBLE_MASK);
    }

    public static byte getNibbleG(final long x)
    {
        return (byte) ((x >> (NIBBLE_SIZE * 9)) & NIBBLE_MASK);
    }

    public static byte getNibbleH(final long x)
    {
        return (byte) ((x >> (NIBBLE_SIZE * 8)) & NIBBLE_MASK);
    }

    public static byte getNibbleI(final long x)
    {
        return (byte) ((x >> (NIBBLE_SIZE * 7)) & NIBBLE_MASK);
    }

    public static byte getNibbleJ(final long x)
    {
        return (byte) ((x >> (NIBBLE_SIZE * 6)) & NIBBLE_MASK);
    }

    public static byte getNibbleK(final long x)
    {
        return (byte) ((x >> (NIBBLE_SIZE * 5)) & NIBBLE_MASK);
    }

    public static byte getNibbleL(final long x)
    {
        return (byte) ((x >> (NIBBLE_SIZE * 4)) & NIBBLE_MASK);
    }

    public static byte getNibbleM(final long x)
    {
        return (byte) ((x >> (NIBBLE_SIZE * 3)) & NIBBLE_MASK);
    }

    public static byte getNibbleN(final long x)
    {
        return (byte) ((x >> (NIBBLE_SIZE * 2)) & NIBBLE_MASK);
    }

    public static byte getNibbleO(final long x)
    {
        return (byte) ((x >> NIBBLE_SIZE) & NIBBLE_MASK);
    }

    public static byte getNibbleP(final long x)
    {
        return (byte) (x & NIBBLE_MASK);
    }

    public static byte getNibble(final long x, int index)
    {
        index = validateIndex("nibbles", "long", index, 16);
        return (byte) (x >> ((NIBBLE_SIZE * index) & NIBBLE_MASK));
    }

    private static int validateIndex(final String smallType, final String bigType, final int x, final int max)
    {
        final int maxi = max - 1;
        if (x > maxi)
        {
            throw new IndexOutOfBoundsException("There are " + max + " " + smallType + " in one " + bigType + ", you selected: " + (x + 1));
        }
        if (x < 0)
        {
            throw new IndexOutOfBoundsException("Index can't be negative.");
        }
        return (x * - 1) + maxi;
    }
}
