/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2016. Diorite (by Bartłomiej Mazur (aka GotoFinal))
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
 * This class use little-endian order.
 */
@SuppressWarnings({"MultiplyOrDivideByPowerOfTwo", "MagicNumber"})
public final class LittleEndianUtils
{
    private LittleEndianUtils()
    {
    }

    private static final EndianUtil endianInst = new LittleEndianInst();

    /**
     * Returns instance of {@link EndianUtil} using this util class as implementation.
     *
     * @return instance of {@link EndianUtil} using this util class as implementation.
     */
    public static EndianUtil getAsInterface()
    {
        return endianInst;
    }

    /**
     * Returns byte with 2 given nibbles in it.
     *
     * @param a nibble a.
     * @param b nibble b.
     *
     * @return byte with 2 given nibbles in it.
     */
    public static byte toByte(final byte a, final byte b)
    {
        return (byte) ((b << NIBBLE_SIZE) | a);
    }

    /**
     * Returns short with 2 given bytes in it.
     *
     * @param a byte a.
     * @param b byte b.
     *
     * @return short with 2 given bytes in it.
     */
    public static short toShort(final byte a, final byte b)
    {
        return (short) ((b << BYTE_SIZE) | (a & BYTE_MASK));
    }

    /**
     * Returns short with 4 given nibbles in it.
     *
     * @param a nibble a.
     * @param b nibble b.
     * @param c nibble c.
     * @param d nibble d.
     *
     * @return short with 4 given nibbles in it.
     */
    public static short toShort(final byte a, final byte b, final byte c, final byte d)
    {
        return (short) ((d << (NIBBLE_SIZE * 3)) | ((c & NIBBLE_MASK) << (NIBBLE_SIZE * 2)) | ((b & NIBBLE_MASK) << NIBBLE_SIZE) | (a & NIBBLE_MASK));
    }

    /**
     * Returns int with 2 given shorts in it.
     *
     * @param a short a.
     * @param b short b.
     *
     * @return int with 2 given shorts in it.
     */
    public static int toInt(final short a, final short b)
    {
        return (b << SHORT_SIZE) | (a & SHORT_MASK);
    }

    /**
     * Returns int with 4 given bytes in it.
     *
     * @param a byte a.
     * @param b byte b.
     * @param c byte c.
     * @param d byte d.
     *
     * @return int with 4 given bytes in it.
     */
    public static int toInt(final byte a, final byte b, final byte c, final byte d)
    {
        return (d << (BYTE_SIZE * 3)) | ((c & BYTE_MASK) << (BYTE_SIZE * 2)) | ((b & BYTE_MASK) << BYTE_SIZE) | (a & BYTE_MASK);
    }

    /**
     * Returns int with 8 given nibbles in it.
     *
     * @param a nibble a,
     * @param b nibble b,
     * @param c nibble c,
     * @param d nibble d,
     * @param e nibble e,
     * @param f nibble f,
     * @param g nibble g,
     * @param h nibble h,
     *
     * @return int with 8 given nibbles in it.
     */
    public static int toInt(final byte a, final byte b, final byte c, final byte d, final byte e, final byte f, final byte g, final byte h)
    {
        return ((h & NIBBLE_MASK) << (NIBBLE_SIZE * 7)) | ((g & NIBBLE_MASK) << (NIBBLE_SIZE * 6)) | ((f & NIBBLE_MASK) << (NIBBLE_SIZE * 5)) | ((e & NIBBLE_MASK) << (NIBBLE_SIZE * 4)) | (d << (NIBBLE_SIZE * 3)) | ((c & NIBBLE_MASK) << (NIBBLE_SIZE * 2)) | ((b & NIBBLE_MASK) << NIBBLE_SIZE) | (a & NIBBLE_MASK);
    }

    /**
     * Returns long with 2 given ints in it.
     *
     * @param a int a.
     * @param b int b.
     *
     * @return long with 2 given ints in it.
     */
    public static long toLong(final int a, final int b)
    {
        return ((long) b << INT_SIZE) | (a & INT_MASK);
    }

    /**
     * Returns long with 4 given shorts in it.
     *
     * @param a short a.
     * @param b short b.
     * @param c short c.
     * @param d short d.
     *
     * @return long with 4 given shorts in it.
     */
    public static long toLong(final short a, final short b, final short c, final short d)
    {
        return ((long) d << (SHORT_SIZE * 3)) | (((long) c & SHORT_MASK) << (SHORT_SIZE * 2)) | (((long) b & SHORT_MASK) << SHORT_SIZE) | ((long) a & SHORT_MASK);
    }

    /**
     * Returns long with 8 given bytes in it.
     *
     * @param a byte a.
     * @param b byte b.
     * @param c byte c.
     * @param d byte d.
     * @param e byte e.
     * @param f byte f.
     * @param g byte g.
     * @param h byte h.
     *
     * @return long with 8 given bytes in it.
     */
    public static long toLong(final byte a, final byte b, final byte c, final byte d, final byte e, final byte f, final byte g, final byte h)
    {
        return (((long) h & BYTE_MASK) << (BYTE_SIZE * 7)) | (((long) g & BYTE_MASK) << (BYTE_SIZE * 6)) | (((long) f & BYTE_MASK) << (BYTE_SIZE * 5)) | (((long) e & BYTE_MASK) << (BYTE_SIZE * 4)) | ((long) d << (BYTE_SIZE * 3)) | (((long) c & BYTE_MASK) << (BYTE_SIZE * 2)) | (((long) b & BYTE_MASK) << BYTE_SIZE) | ((long) a & BYTE_MASK);
    }

    /**
     * Returns long with 16 given nibbles in it.
     *
     * @param a nibble a.
     * @param b nibble b.
     * @param c nibble c.
     * @param d nibble d.
     * @param e nibble e.
     * @param f nibble f.
     * @param g nibble g.
     * @param h nibble h.
     * @param i nibble i.
     * @param j nibble j.
     * @param k nibble k.
     * @param l nibble l.
     * @param m nibble m.
     * @param n nibble n.
     * @param o nibble o.
     * @param p nibble p.
     *
     * @return long with 16 given nibbles in it.
     */
    public static long toLong(final byte a, final byte b, final byte c, final byte d, final byte e, final byte f, final byte g, final byte h, final byte i, final byte j, final byte k, final byte l, final byte m, final byte n, final byte o, final byte p)
    {
        return (((long) p & NIBBLE_MASK) << (NIBBLE_SIZE * 15)) | (((long) o & NIBBLE_MASK) << (NIBBLE_SIZE * 14)) | (((long) n & NIBBLE_MASK) << (NIBBLE_SIZE * 13)) | (((long) m & NIBBLE_MASK) << (NIBBLE_SIZE * 12)) | (((long) l & NIBBLE_MASK) << (NIBBLE_SIZE * 11)) | (((long) k & NIBBLE_MASK) << (NIBBLE_SIZE * 10)) | (((long) j & NIBBLE_MASK) << (NIBBLE_SIZE * 9)) | (((long) i & NIBBLE_MASK) << (NIBBLE_SIZE * 8)) | (((long) h & NIBBLE_MASK) << (NIBBLE_SIZE * 7)) | (((long) g & NIBBLE_MASK) << (NIBBLE_SIZE * 6)) | (((long) f & NIBBLE_MASK) << (NIBBLE_SIZE * 5)) | (((long) e & NIBBLE_MASK) << (NIBBLE_SIZE * 4)) | ((long) d << (NIBBLE_SIZE * 3)) | (((long) c & NIBBLE_MASK) << (NIBBLE_SIZE * 2)) | (((long) b & NIBBLE_MASK) << NIBBLE_SIZE) | ((long) a & NIBBLE_MASK);
    }

    /**
     * Returns byte from given array of nibbles. <br>
     * Array must have at least 2 elements in it.
     *
     * @param nibbles array of nibbles.
     *
     * @return byte from given array of nibbles.
     */
    public static byte toByteFromNibbles(final byte[] nibbles)
    {
        return toByte(nibbles[0], nibbles[1]);
    }

    /**
     * Returns byte from given array of nibbles. <br>
     * Array must have at least start + 2 elements in it.
     *
     * @param nibbles array of nibbles.
     * @param start   first index of numer in array.
     *
     * @return byte from given array of nibbles.
     */
    public static byte toByteFromNibbles(final byte[] nibbles, final int start)
    {
        return toByte(nibbles[start], nibbles[start + 1]);
    }

    /**
     * Returns byte from given array of nibbles. <br>
     * Array must have at least 2 elements in it.
     *
     * @param nibbles array of nibbles.
     *
     * @return byte from given array of nibbles.
     */
    public static byte toByte(final NibbleArray nibbles)
    {
        return toByte(nibbles.get(0), nibbles.get(1));
    }

    /**
     * Returns byte from given array of nibbles. <br>
     * Array must have at least start + 2 elements in it.
     *
     * @param nibbles array of nibbles.
     * @param start   first index of numer in array.
     *
     * @return byte from given array of nibbles.
     */
    public static byte toByte(final NibbleArray nibbles, final int start)
    {
        return toByte(nibbles.get(start), nibbles.get(start + 1));
    }

    /**
     * Returns short from given array of nibbles. <br>
     * Array must have at least 4 elements in it.
     *
     * @param nibbles array of nibbles.
     *
     * @return short from given array of nibbles.
     */
    public static short toShortFromNibbles(final byte[] nibbles)
    {
        return toShort(nibbles[0], nibbles[1], nibbles[2], nibbles[3]);
    }

    /**
     * Returns short from given array of nibbles. <br>
     * Array must have at least start + 4 elements in it.
     *
     * @param nibbles array of nibbles.
     * @param start   first index of numer in array.
     *
     * @return short from given array of nibbles.
     */
    public static short toShortFromNibbles(final byte[] nibbles, final int start)
    {
        return toShort(nibbles[start], nibbles[start + 1], nibbles[start + 2], nibbles[start + 3]);
    }

    /**
     * Returns short from given array of nibbles. <br>
     * Array must have at least 4 elements in it.
     *
     * @param nibbles array of nibbles.
     *
     * @return short from given array of nibbles.
     */
    public static short toShort(final NibbleArray nibbles)
    {
        return toShort(nibbles.get(0), nibbles.get(1), nibbles.get(2), nibbles.get(3));
    }

    /**
     * Returns short from given array of nibbles. <br>
     * Array must have at least start + 4 elements in it.
     *
     * @param nibbles array of nibbles.
     * @param start   first index of numer in array.
     *
     * @return short from given array of nibbles.
     */
    public static short toShort(final NibbleArray nibbles, final int start)
    {
        return toShort(nibbles.get(start), nibbles.get(start + 1), nibbles.get(start + 2), nibbles.get(start + 3));
    }

    /**
     * Returns short from given array of bytes. <br>
     * Array must have at least 2 elements in it.
     *
     * @param bytes array of bytes.
     *
     * @return short from given array of bytes.
     */
    public static short toShort(final byte[] bytes)
    {
        return toShort(bytes[0], bytes[1]);
    }

    /**
     * Returns short from given array of bytes. <br>
     * Array must have at least start + 2 elements in it.
     *
     * @param bytes array of bytes.
     * @param start first index of numer in array.
     *
     * @return short from given array of bytes.
     */
    public static short toShort(final byte[] bytes, final int start)
    {
        return toShort(bytes[start], bytes[start + 1]);
    }

    /**
     * Returns int from given array of nibbles. <br>
     * Array must have at least 8 elements in it.
     *
     * @param nibbles array of nibbles.
     *
     * @return int from given array of nibbles.
     */
    public static int toIntFromNibbles(final byte[] nibbles)
    {
        return toInt(nibbles[0], nibbles[1], nibbles[2], nibbles[3], nibbles[4], nibbles[5], nibbles[6], nibbles[7]);
    }

    /**
     * Returns int from given array of nibbles. <br>
     * Array must have at least start + 8 elements in it.
     *
     * @param nibbles array of nibbles.
     * @param start   first index of numer in array.
     *
     * @return int from given array of nibbles.
     */
    public static int toIntFromNibbles(final byte[] nibbles, final int start)
    {
        return toInt(nibbles[start], nibbles[start + 1], nibbles[start + 2], nibbles[start + 3], nibbles[start + 4], nibbles[start + 5], nibbles[start + 6], nibbles[start + 7]);
    }

    /**
     * Returns int from given array of nibbles. <br>
     * Array must have at least 8 elements in it.
     *
     * @param nibbles array of nibbles.
     *
     * @return int from given array of nibbles.
     */
    public static int toInt(final NibbleArray nibbles)
    {
        return toInt(nibbles.get(0), nibbles.get(1), nibbles.get(2), nibbles.get(3), nibbles.get(4), nibbles.get(5), nibbles.get(6), nibbles.get(7));
    }

    /**
     * Returns int from given array of nibbles. <br>
     * Array must have at least start + 8 elements in it.
     *
     * @param nibbles array of nibbles.
     * @param start   first index of numer in array.
     *
     * @return int from given array of nibbles.
     */
    public static int toInt(final NibbleArray nibbles, final int start)
    {
        return toInt(nibbles.get(start), nibbles.get(start + 1), nibbles.get(start + 2), nibbles.get(start + 3), nibbles.get(start + 4), nibbles.get(start + 5), nibbles.get(start + 6), nibbles.get(start + 7));
    }

    /**
     * Returns int from given array of bytes. <br>
     * Array must have at least 4 elements in it.
     *
     * @param bytes array of bytes.
     *
     * @return int from given array of bytes.
     */
    public static int toInt(final byte[] bytes)
    {
        return toInt(bytes[0], bytes[1], bytes[2], bytes[3]);
    }

    /**
     * Returns int from given array of bytes. <br>
     * Array must have at least start + 4 elements in it.
     *
     * @param bytes array of bytes.
     * @param start first index of numer in array.
     *
     * @return int from given array of bytes.
     */
    public static int toInt(final byte[] bytes, final int start)
    {
        return toInt(bytes[start], bytes[start + 1], bytes[start + 2], bytes[start + 3]);
    }

    /**
     * Returns int from given array of shorts. <br>
     * Array must have at least 2 elements in it.
     *
     * @param shorts array of shorts.
     *
     * @return int from given array of shorts.
     */
    public static int toInt(final short[] shorts)
    {
        return toInt(shorts[0], shorts[1]);
    }

    /**
     * Returns int from given array of shorts. <br>
     * Array must have at least start + 2 elements in it.
     *
     * @param shorts array of shorts.
     * @param start  first index of numer in array.
     *
     * @return int from given array of shorts.
     */
    public static int toInt(final short[] shorts, final int start)
    {
        return toInt(shorts[start], shorts[start + 1]);
    }

    /**
     * Returns long from given array of nibbles. <br>
     * Array must have at least 16 elements in it.
     *
     * @param nibbles array of nibbles.
     *
     * @return long from given array of nibbles.
     */
    public static long toLongFromNibbles(final byte[] nibbles)
    {
        return toLong(nibbles[0], nibbles[1], nibbles[2], nibbles[3], nibbles[4], nibbles[5], nibbles[6], nibbles[7], nibbles[8], nibbles[9], nibbles[10], nibbles[11], nibbles[12], nibbles[13], nibbles[14], nibbles[15]);
    }

    /**
     * Returns long from given array of nibbles. <br>
     * Array must have at least start + 16 elements in it.
     *
     * @param nibbles array of nibbles.
     * @param start   first index of numer in array.
     *
     * @return long from given array of nibbles.
     */
    public static long toLongFromNibbles(final byte[] nibbles, final int start)
    {
        return toLong(nibbles[start], nibbles[start + 1], nibbles[start + 2], nibbles[start + 3], nibbles[start + 4], nibbles[start + 5], nibbles[start + 6], nibbles[start + 7], nibbles[start + 8], nibbles[start + 9], nibbles[start + 10], nibbles[start + 11], nibbles[start + 12], nibbles[start + 13], nibbles[start + 14], nibbles[start + 15]);
    }

    /**
     * Returns long from given array of nibbles. <br>
     * Array must have at least 16 elements in it.
     *
     * @param nibbles array of nibbles.
     *
     * @return long from given array of nibbles.
     */
    public static long toLong(final NibbleArray nibbles)
    {
        return toLong(nibbles.get(0), nibbles.get(1), nibbles.get(2), nibbles.get(3), nibbles.get(4), nibbles.get(5), nibbles.get(6), nibbles.get(7), nibbles.get(8), nibbles.get(9), nibbles.get(10), nibbles.get(11), nibbles.get(12), nibbles.get(13), nibbles.get(14), nibbles.get(15));
    }

    /**
     * Returns long from given array of nibbles. <br>
     * Array must have at least start + 16 elements in it.
     *
     * @param nibbles array of nibbles.
     * @param start   first index of numer in array.
     *
     * @return long from given array of nibbles.
     */
    public static long toLong(final NibbleArray nibbles, final int start)
    {
        return toLong(nibbles.get(start), nibbles.get(start + 1), nibbles.get(start + 2), nibbles.get(start + 3), nibbles.get(start + 4), nibbles.get(start + 5), nibbles.get(start + 6), nibbles.get(start + 7), nibbles.get(start + 8), nibbles.get(start + 9), nibbles.get(start + 10), nibbles.get(start + 11), nibbles.get(start + 12), nibbles.get(start + 13), nibbles.get(start + 14), nibbles.get(start + 15));
    }

    /**
     * Returns long from given array of bytes. <br>
     * Array must have at least 8 elements in it.
     *
     * @param bytes array of bytes.
     *
     * @return long from given array of bytes.
     */
    public static long toLong(final byte[] bytes)
    {
        return toLong(bytes[0], bytes[1], bytes[2], bytes[3], bytes[4], bytes[5], bytes[6], bytes[7]);
    }

    /**
     * Returns long from given array of bytes. <br>
     * Array must have at least start + 8 elements in it.
     *
     * @param bytes array of bytes.
     * @param start first index of numer in array.
     *
     * @return long from given array of bytes.
     */
    public static long toLong(final byte[] bytes, final int start)
    {
        return toLong(bytes[start], bytes[start + 1], bytes[start + 2], bytes[start + 3], bytes[start + 4], bytes[start + 5], bytes[start + 6], bytes[start + 7]);
    }

    /**
     * Returns long from given array of shorts. <br>
     * Array must have at least 4 elements in it.
     *
     * @param shorts array of shorts.
     *
     * @return long from given array of shorts.
     */
    public static long toLong(final short[] shorts)
    {
        return toLong(shorts[0], shorts[1], shorts[2], shorts[3]);
    }

    /**
     * Returns long from given array of shorts. <br>
     * Array must have at least start + 4 elements in it.
     *
     * @param shorts array of shorts.
     * @param start  first index of numer in array.
     *
     * @return long from given array of shorts.
     */
    public static long toLong(final short[] shorts, final int start)
    {
        return toLong(shorts[start], shorts[start + 1], shorts[start + 2], shorts[start + 3]);
    }

    /**
     * Returns long from given array of ints. <br>
     * Array must have at least 2 elements in it.
     *
     * @param ints array of ints.
     *
     * @return long from given array of ints.
     */
    public static long toLong(final int[] ints)
    {
        return toLong(ints[0], ints[1]);
    }

    /**
     * Returns long from given array of ints. <br>
     * Array must have at least start + 2 elements in it.
     *
     * @param ints  array of ints.
     * @param start first index of numer in array.
     *
     * @return long from given array of ints.
     */
    public static long toLong(final int[] ints, final int start)
    {
        return toLong(ints[start], ints[start + 1]);
    }

    /*
     * From byte
     */

    /**
     * Returns first nibble from given byte.
     *
     * @param x value for extracting a number from it.
     *
     * @return first nibble from given byte.
     */
    public static byte getNibbleA(final byte x)
    {
        return (byte) (x & NIBBLE_MASK);
    }

    /**
     * Returns second nibble from given byte.
     *
     * @param x value for extracting a number from it.
     *
     * @return second nibble from given byte.
     */
    public static byte getNibbleB(final byte x)
    {
        return (byte) ((x >> NIBBLE_SIZE) & NIBBLE_MASK);
    }

    /**
     * Returns nibble on given index from given byte.
     *
     * @param x     value for extracting a number from it.
     * @param index index of number in value. (from 0)
     *
     * @return nibble on given index from given byte.
     */
    public static byte getNibble(final byte x, final int index)
    {
        validateIndex("nibbles", "byte", index, 2);
        return (byte) ((x >> (NIBBLE_SIZE * index)) & NIBBLE_MASK);
    }

    /*
     * From short
     */

    /**
     * Returns first byte from given short.
     *
     * @param x value for extracting a number from it.
     *
     * @return first byte from given short.
     */
    public static byte getByteA(final short x)
    {
        return (byte) x;
    }

    /**
     * Returns second byte from given short.
     *
     * @param x value for extracting a number from it.
     *
     * @return second byte from given short.
     */
    public static byte getByteB(final short x)
    {
        return (byte) (x >> BYTE_SIZE);
    }

    /**
     * Returns byte on given index from given short.
     *
     * @param x     value for extracting a number from it.
     * @param index index of number in value. (from 0)
     *
     * @return byte on given index from given short.
     */
    public static byte getByte(final short x, final int index)
    {
        validateIndex("bytes", "short", index, 2);
        return (byte) (x >> (BYTE_SIZE * index));
    }

    /**
     * Returns first nibble from given short.
     *
     * @param x value for extracting a number from it.
     *
     * @return first nibble from given short.
     */
    public static byte getNibbleA(final short x)
    {
        return (byte) (x & NIBBLE_MASK);
    }

    /**
     * Returns second nibble from given short.
     *
     * @param x value for extracting a number from it.
     *
     * @return second nibble from given short.
     */
    public static byte getNibbleB(final short x)
    {
        return (byte) ((x >> (NIBBLE_SIZE * 3)) & NIBBLE_MASK);
    }

    /**
     * Returns third nibble from given short.
     *
     * @param x value for extracting a number from it.
     *
     * @return third nibble from given short.
     */
    public static byte getNibbleC(final short x)
    {
        return (byte) ((x >> (NIBBLE_SIZE * 2)) & NIBBLE_MASK);
    }

    /**
     * Returns fourth nibble from given short.
     *
     * @param x value for extracting a number from it.
     *
     * @return fourth nibble from given short.
     */
    public static byte getNibbleD(final short x)
    {
        return (byte) ((x >> NIBBLE_SIZE) & NIBBLE_MASK);
    }

    /**
     * Returns nibble on given index from given short.
     *
     * @param x     value for extracting a number from it.
     * @param index index of number in value. (from 0)
     *
     * @return nibble on given index from given short.
     */
    public static byte getNibble(final short x, final int index)
    {
        validateIndex("nibbles", "short", index, 4);
        return (byte) (x >> ((NIBBLE_SIZE * index) & NIBBLE_MASK));
    }

    /*
     * From int
     */

    /**
     * Returns first short from given int.
     *
     * @param x value for extracting a number from it.
     *
     * @return first short from given int.
     */
    public static short getShortA(final int x)
    {
        return (short) x;
    }

    /**
     * Returns second short from given int.
     *
     * @param x value for extracting a number from it.
     *
     * @return second short from given int.
     */
    public static short getShortB(final int x)
    {
        return (short) (x >> SHORT_SIZE);
    }

    /**
     * Returns short on given index from given int.
     *
     * @param x     value for extracting a number from it.
     * @param index index of number in value. (from 0)
     *
     * @return short on given index from given int.
     */
    public static short getShort(final int x, final int index)
    {
        validateIndex("shorts", "int", index, 2);
        return (byte) (x >> (SHORT_SIZE * index));
    }

    /**
     * Returns first byte from given int.
     *
     * @param x value for extracting a number from it.
     *
     * @return first byte from given int.
     */
    public static byte getByteA(final int x)
    {
        return (byte) (x);
    }

    /**
     * Returns second byte from given int.
     *
     * @param x value for extracting a number from it.
     *
     * @return second byte from given int.
     */
    public static byte getByteB(final int x)
    {
        return (byte) (x >> BYTE_SIZE);
    }

    /**
     * Returns third byte from given int.
     *
     * @param x value for extracting a number from it.
     *
     * @return third byte from given int.
     */
    public static byte getByteC(final int x)
    {
        return (byte) (x >> (BYTE_SIZE * 2));
    }

    /**
     * Returns fourth byte from given int.
     *
     * @param x value for extracting a number from it.
     *
     * @return fourth byte from given int.
     */
    public static byte getByteD(final int x)
    {
        return (byte) (x >> (BYTE_SIZE * 3));
    }

    /**
     * Returns byte on given index from given int.
     *
     * @param x     value for extracting a number from it.
     * @param index index of number in value. (from 0)
     *
     * @return byte on given index from given int.
     */
    public static byte getByte(final int x, final int index)
    {
        validateIndex("bytes", "int", index, 4);
        return (byte) (x >> (BYTE_SIZE * index));
    }

    /**
     * Returns first nibble from given int.
     *
     * @param x value for extracting a number from it.
     *
     * @return first nibble from given int.
     */
    public static byte getNibbleA(final int x)
    {
        return (byte) (x & NIBBLE_MASK);
    }

    /**
     * Returns second nibble from given int.
     *
     * @param x value for extracting a number from it.
     *
     * @return second nibble from given int.
     */
    public static byte getNibbleB(final int x)
    {
        return (byte) ((x >> (NIBBLE_SIZE * 7)) & NIBBLE_MASK);
    }

    /**
     * Returns third nibble from given int.
     *
     * @param x value for extracting a number from it.
     *
     * @return third nibble from given int.
     */
    public static byte getNibbleC(final int x)
    {
        return (byte) ((x >> (NIBBLE_SIZE * 6)) & NIBBLE_MASK);
    }

    /**
     * Returns fourth nibble from given int.
     *
     * @param x value for extracting a number from it.
     *
     * @return fourth nibble from given int.
     */
    public static byte getNibbleD(final int x)
    {
        return (byte) ((x >> (NIBBLE_SIZE * 5)) & NIBBLE_MASK);
    }

    /**
     * Returns fifth nibble from given int.
     *
     * @param x value for extracting a number from it.
     *
     * @return fifth nibble from given int.
     */
    public static byte getNibbleE(final int x)
    {
        return (byte) ((x >> (NIBBLE_SIZE * 4)) & NIBBLE_MASK);
    }

    /**
     * Returns sixth nibble from given int.
     *
     * @param x value for extracting a number from it.
     *
     * @return sixth nibble from given int.
     */
    public static byte getNibbleF(final int x)
    {
        return (byte) ((x >> (NIBBLE_SIZE * 3)) & NIBBLE_MASK);
    }

    /**
     * Returns seventh nibble from given int.
     *
     * @param x value for extracting a number from it.
     *
     * @return seventh nibble from given int.
     */
    public static byte getNibbleG(final int x)
    {
        return (byte) ((x >> (NIBBLE_SIZE * 2)) & NIBBLE_MASK);
    }

    /**
     * Returns eight nibble from given int.
     *
     * @param x value for extracting a number from it.
     *
     * @return eight nibble from given int.
     */
    public static byte getNibbleH(final int x)
    {
        return (byte) ((x >> NIBBLE_SIZE) & NIBBLE_MASK);
    }

    /**
     * Returns nibble on given index from given int.
     *
     * @param x     value for extracting a number from it.
     * @param index index of number in value. (from 0)
     *
     * @return nibble on given index from given int.
     */
    public static byte getNibble(final int x, final int index)
    {
        validateIndex("nibbles", "int", index, 8);
        return (byte) (x >> ((NIBBLE_SIZE * index) & NIBBLE_MASK));
    }
    /*
     * From long
     */

    /**
     * Returns first int from given long.
     *
     * @param x value for extracting a number from it.
     *
     * @return first int from given long.
     */
    public static int getIntA(final long x)
    {
        return (int) x;
    }

    /**
     * Returns second int from given long.
     *
     * @param x value for extracting a number from it.
     *
     * @return second int from given long.
     */
    public static int getIntB(final long x)
    {
        return (int) (x >> INT_SIZE);
    }

    /**
     * Returns int on given index from given long.
     *
     * @param x     value for extracting a number from it.
     * @param index index of number in value. (from 0)
     *
     * @return int on given index from given long.
     */
    public static int getInt(final long x, final int index)
    {
        validateIndex("ints", "long", index, 2);
        return (byte) (x >> (INT_SIZE * index));
    }

    /**
     * Returns first short from given long.
     *
     * @param x value for extracting a number from it.
     *
     * @return first short from given long.
     */
    public static short getShortA(final long x)
    {
        return (short) (x);
    }

    /**
     * Returns second short from given long.
     *
     * @param x value for extracting a number from it.
     *
     * @return second short from given long.
     */
    public static short getShortB(final long x)
    {
        return (short) (x >> SHORT_SIZE);
    }

    /**
     * Returns third short from given long.
     *
     * @param x value for extracting a number from it.
     *
     * @return third short from given long.
     */
    public static short getShortC(final long x)
    {
        return (short) (x >> (SHORT_SIZE * 2));
    }

    /**
     * Returns fourth short from given long.
     *
     * @param x value for extracting a number from it.
     *
     * @return fourth short from given long.
     */
    public static short getShortD(final long x)
    {
        return (short) (x >> (SHORT_SIZE * 3));
    }

    /**
     * Returns short on given index from given long.
     *
     * @param x     value for extracting a number from it.
     * @param index index of number in value. (from 0)
     *
     * @return short on given index from given long.
     */
    public static short getShort(final long x, final int index)
    {
        validateIndex("shorts", "long", index, 4);
        return (byte) (x >> (SHORT_SIZE * index));
    }

    /**
     * Returns first byte from given long.
     *
     * @param x value for extracting a number from it.
     *
     * @return first byte from given long.
     */
    public static byte getByteA(final long x)
    {
        return (byte) (x);
    }

    /**
     * Returns second byte from given long.
     *
     * @param x value for extracting a number from it.
     *
     * @return second byte from given long.
     */
    public static byte getByteB(final long x)
    {
        return (byte) (x >> BYTE_SIZE);
    }

    /**
     * Returns third byte from given long.
     *
     * @param x value for extracting a number from it.
     *
     * @return third byte from given long.
     */
    public static byte getByteC(final long x)
    {
        return (byte) (x >> (BYTE_SIZE * 2));
    }

    /**
     * Returns fourth byte from given long.
     *
     * @param x value for extracting a number from it.
     *
     * @return fourth byte from given long.
     */
    public static byte getByteD(final long x)
    {
        return (byte) (x >> (BYTE_SIZE * 3));
    }

    /**
     * Returns fifth byte from given long.
     *
     * @param x value for extracting a number from it.
     *
     * @return fifth byte from given long.
     */
    public static byte getByteE(final long x)
    {
        return (byte) (x >> (BYTE_SIZE * 4));
    }

    /**
     * Returns sixth byte from given long.
     *
     * @param x value for extracting a number from it.
     *
     * @return sixth byte from given long.
     */
    public static byte getByteF(final long x)
    {
        return (byte) (x >> (BYTE_SIZE * 5));
    }

    /**
     * Returns seventh byte from given long.
     *
     * @param x value for extracting a number from it.
     *
     * @return seventh byte from given long.
     */
    public static byte getByteG(final long x)
    {
        return (byte) (x >> (BYTE_SIZE * 6));
    }

    /**
     * Returns eighth byte from given long.
     *
     * @param x value for extracting a number from it.
     *
     * @return eighth byte from given long.
     */
    public static byte getByteH(final long x)
    {
        return (byte) (x >> (BYTE_SIZE * 7));
    }

    /**
     * Returns byte on given index from given long.
     *
     * @param x     value for extracting a number from it.
     * @param index index of number in value. (from 0)
     *
     * @return byte on given index from given long.
     */
    public static byte getByte(final long x, final int index)
    {
        validateIndex("bytes", "long", index, 8);
        return (byte) (x >> (BYTE_SIZE * index));
    }

    /**
     * Returns first nibble from given long.
     *
     * @param x value for extracting a number from it.
     *
     * @return first nibble from given long.
     */
    public static byte getNibbleA(final long x)
    {
        return (byte) (x & NIBBLE_MASK);
    }

    /**
     * Returns second nibble from given long.
     *
     * @param x value for extracting a number from it.
     *
     * @return second nibble from given long.
     */
    public static byte getNibbleB(final long x)
    {
        return (byte) ((x >> NIBBLE_SIZE) & NIBBLE_MASK);
    }

    /**
     * Returns third nibble from given long.
     *
     * @param x value for extracting a number from it.
     *
     * @return third nibble from given long.
     */
    public static byte getNibbleC(final long x)
    {
        return (byte) ((x >> (NIBBLE_SIZE * 2)) & NIBBLE_MASK);
    }

    /**
     * Returns fourth nibble from given long.
     *
     * @param x value for extracting a number from it.
     *
     * @return fourth nibble from given long.
     */
    public static byte getNibbleD(final long x)
    {
        return (byte) ((x >> (NIBBLE_SIZE * 3)) & NIBBLE_MASK);
    }

    /**
     * Returns fifth nibble from given long.
     *
     * @param x value for extracting a number from it.
     *
     * @return fifth nibble from given long.
     */
    public static byte getNibbleE(final long x)
    {
        return (byte) ((x >> (NIBBLE_SIZE * 4)) & NIBBLE_MASK);
    }

    /**
     * Returns sixth nibble from given long.
     *
     * @param x value for extracting a number from it.
     *
     * @return sixth nibble from given long.
     */
    public static byte getNibbleF(final long x)
    {
        return (byte) ((x >> (NIBBLE_SIZE * 5)) & NIBBLE_MASK);
    }

    /**
     * Returns seventh nibble from given long.
     *
     * @param x value for extracting a number from it.
     *
     * @return seventh nibble from given long.
     */
    public static byte getNibbleG(final long x)
    {
        return (byte) ((x >> (NIBBLE_SIZE * 6)) & NIBBLE_MASK);
    }

    /**
     * Returns eighth nibble from given long.
     *
     * @param x value for extracting a number from it.
     *
     * @return eighth nibble from given long.
     */
    public static byte getNibbleH(final long x)
    {
        return (byte) ((x >> (NIBBLE_SIZE * 7)) & NIBBLE_MASK);
    }

    /**
     * Returns ninth nibble from given long.
     *
     * @param x value for extracting a number from it.
     *
     * @return ninth nibble from given long.
     */
    public static byte getNibbleI(final long x)
    {
        return (byte) ((x >> (NIBBLE_SIZE * 8)) & NIBBLE_MASK);
    }

    /**
     * Returns tenth nibble from given long.
     *
     * @param x value for extracting a number from it.
     *
     * @return tenth nibble from given long.
     */
    public static byte getNibbleJ(final long x)
    {
        return (byte) ((x >> (NIBBLE_SIZE * 9)) & NIBBLE_MASK);
    }

    /**
     * Returns eleventh nibble from given long.
     *
     * @param x value for extracting a number from it.
     *
     * @return eleventh nibble from given long.
     */
    public static byte getNibbleK(final long x)
    {
        return (byte) ((x >> (NIBBLE_SIZE * 10)) & NIBBLE_MASK);
    }

    /**
     * Returns twelfth nibble from given long.
     *
     * @param x value for extracting a number from it.
     *
     * @return twelfth nibble from given long.
     */
    public static byte getNibbleL(final long x)
    {
        return (byte) ((x >> (NIBBLE_SIZE * 11)) & NIBBLE_MASK);
    }

    /**
     * Returns thirteenth nibble from given long.
     *
     * @param x value for extracting a number from it.
     *
     * @return thirteenth nibble from given long.
     */
    public static byte getNibbleM(final long x)
    {
        return (byte) ((x >> (NIBBLE_SIZE * 12)) & NIBBLE_MASK);
    }

    /**
     * Returns fourteenth nibble from given long.
     *
     * @param x value for extracting a number from it.
     *
     * @return fourteenth nibble from given long.
     */
    public static byte getNibbleN(final long x)
    {
        return (byte) ((x >> (NIBBLE_SIZE * 13)) & NIBBLE_MASK);
    }

    /**
     * Returns fifteenth nibble from given long.
     *
     * @param x value for extracting a number from it.
     *
     * @return fifteenth nibble from given long.
     */
    public static byte getNibbleO(final long x)
    {
        return (byte) ((x >> (NIBBLE_SIZE * 14)) & NIBBLE_MASK);
    }

    /**
     * Returns sixteenth nibble from given long.
     *
     * @param x value for extracting a number from it.
     *
     * @return sixteenth nibble from given long.
     */
    public static byte getNibbleP(final long x)
    {
        return (byte) ((x >> (NIBBLE_SIZE * 15)) & NIBBLE_MASK);
    }

    /**
     * Returns nibble on given index from given long.
     *
     * @param x     value for extracting a number from it.
     * @param index index of number in value. (from 0)
     *
     * @return nibble on given index from given long.
     */
    public static byte getNibble(final long x, final int index)
    {
        validateIndex("nibbles", "long", index, 16);
        return (byte) (x >> ((NIBBLE_SIZE * index) & NIBBLE_MASK));
    }

    private static void validateIndex(final String smallType, final String bigType, final int x, final int max)
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
    }
}
