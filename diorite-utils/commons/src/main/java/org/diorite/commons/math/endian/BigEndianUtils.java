/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017. Diorite (by Bartłomiej Mazur (aka GotoFinal))
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

package org.diorite.commons.math.endian;

import static org.diorite.commons.math.endian.EndianUtil.BYTE_MASK;
import static org.diorite.commons.math.endian.EndianUtil.BYTE_SIZE;
import static org.diorite.commons.math.endian.EndianUtil.INT_MASK;
import static org.diorite.commons.math.endian.EndianUtil.INT_SIZE;
import static org.diorite.commons.math.endian.EndianUtil.NIBBLE_MASK;
import static org.diorite.commons.math.endian.EndianUtil.NIBBLE_SIZE;
import static org.diorite.commons.math.endian.EndianUtil.SHORT_MASK;
import static org.diorite.commons.math.endian.EndianUtil.SHORT_SIZE;


import org.diorite.commons.arrays.NibbleArray;

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
     * @param a
     *         nibble a.
     * @param b
     *         nibble b.
     *
     * @return byte with 2 given nibbles in it.
     */
    public static byte toByte(byte a, byte b)
    {
        return (byte) ((a << NIBBLE_SIZE) | b);
    }

    /**
     * Returns short with 2 given bytes in it.
     *
     * @param a
     *         byte a.
     * @param b
     *         byte b.
     *
     * @return short with 2 given bytes in it.
     */
    public static short toShort(byte a, byte b)
    {
        return (short) ((a << BYTE_SIZE) | (b & BYTE_MASK));
    }

    /**
     * Returns short with 4 given nibbles in it.
     *
     * @param a
     *         nibble a.
     * @param b
     *         nibble b.
     * @param c
     *         nibble c.
     * @param d
     *         nibble d.
     *
     * @return short with 4 given nibbles in it.
     */
    public static short toShort(byte a, byte b, byte c, byte d)
    {
        return (short) ((a << (NIBBLE_SIZE * 3)) | ((b & NIBBLE_MASK) << (NIBBLE_SIZE * 2)) | ((c & NIBBLE_MASK) << NIBBLE_SIZE) | (d & NIBBLE_MASK));
    }

    /**
     * Returns int with 2 given shorts in it.
     *
     * @param a
     *         short a.
     * @param b
     *         short b.
     *
     * @return int with 2 given shorts in it.
     */
    public static int toInt(short a, short b)
    {
        return (a << SHORT_SIZE) | (b & SHORT_MASK);
    }

    /**
     * Returns int with 4 given bytes in it.
     *
     * @param a
     *         byte a.
     * @param b
     *         byte b.
     * @param c
     *         byte c.
     * @param d
     *         byte d.
     *
     * @return int with 4 given bytes in it.
     */
    public static int toInt(byte a, byte b, byte c, byte d)
    {
        return (a << (BYTE_SIZE * 3)) | ((b & BYTE_MASK) << (BYTE_SIZE * 2)) | ((c & BYTE_MASK) << BYTE_SIZE) | (d & BYTE_MASK);
    }

    /**
     * Returns int with 8 given nibbles in it.
     *
     * @param a
     *         nibble a,
     * @param b
     *         nibble b,
     * @param c
     *         nibble c,
     * @param d
     *         nibble d,
     * @param e
     *         nibble e,
     * @param f
     *         nibble f,
     * @param g
     *         nibble g,
     * @param h
     *         nibble h,
     *
     * @return int with 8 given nibbles in it.
     */
    public static int toInt(byte a, byte b, byte c, byte d, byte e, byte f, byte g, byte h)
    {
        return ((a & NIBBLE_MASK) << (NIBBLE_SIZE * 7)) | ((b & NIBBLE_MASK) << (NIBBLE_SIZE * 6)) | ((c & NIBBLE_MASK) << (NIBBLE_SIZE * 5)) |
               ((d & NIBBLE_MASK) << (NIBBLE_SIZE * 4)) | (e << (NIBBLE_SIZE * 3)) | ((f & NIBBLE_MASK) << (NIBBLE_SIZE * 2)) |
               ((g & NIBBLE_MASK) << NIBBLE_SIZE) | (h & NIBBLE_MASK);
    }

    /**
     * Returns long with 2 given ints in it.
     *
     * @param a
     *         int a.
     * @param b
     *         int b.
     *
     * @return long with 2 given ints in it.
     */
    public static long toLong(int a, int b)
    {
        return ((long) a << INT_SIZE) | (b & INT_MASK);
    }

    /**
     * Returns long with 4 given shorts in it.
     *
     * @param a
     *         short a.
     * @param b
     *         short b.
     * @param c
     *         short c.
     * @param d
     *         short d.
     *
     * @return long with 4 given shorts in it.
     */
    public static long toLong(short a, short b, short c, short d)
    {
        return ((long) a << (SHORT_SIZE * 3)) | (((long) b & SHORT_MASK) << (SHORT_SIZE * 2)) | (((long) c & SHORT_MASK) << SHORT_SIZE) |
               ((long) d & SHORT_MASK);
    }

    /**
     * Returns long with 8 given bytes in it.
     *
     * @param a
     *         byte a.
     * @param b
     *         byte b.
     * @param c
     *         byte c.
     * @param d
     *         byte d.
     * @param e
     *         byte e.
     * @param f
     *         byte f.
     * @param g
     *         byte g.
     * @param h
     *         byte h.
     *
     * @return long with 8 given bytes in it.
     */
    public static long toLong(byte a, byte b, byte c, byte d, byte e, byte f, byte g, byte h)
    {
        return (((long) a & BYTE_MASK) << (BYTE_SIZE * 7)) | (((long) b & BYTE_MASK) << (BYTE_SIZE * 6)) | (((long) c & BYTE_MASK) << (BYTE_SIZE * 5)) |
               (((long) d & BYTE_MASK) << (BYTE_SIZE * 4)) | ((long) e << (BYTE_SIZE * 3)) | (((long) f & BYTE_MASK) << (BYTE_SIZE * 2)) |
               (((long) g & BYTE_MASK) << BYTE_SIZE) | ((long) h & BYTE_MASK);
    }

    /**
     * Returns long with 16 given nibbles in it.
     *
     * @param a
     *         nibble a.
     * @param b
     *         nibble b.
     * @param c
     *         nibble c.
     * @param d
     *         nibble d.
     * @param e
     *         nibble e.
     * @param f
     *         nibble f.
     * @param g
     *         nibble g.
     * @param h
     *         nibble h.
     * @param i
     *         nibble i.
     * @param j
     *         nibble j.
     * @param k
     *         nibble k.
     * @param l
     *         nibble l.
     * @param m
     *         nibble m.
     * @param n
     *         nibble n.
     * @param o
     *         nibble o.
     * @param p
     *         nibble p.
     *
     * @return long with 16 given nibbles in it.
     */
    public static long toLong(byte a, byte b, byte c, byte d, byte e, byte f, byte g, byte h, byte i, byte j, byte k, byte l, byte m, byte n, byte o, byte p)
    {
        return (((long) a & NIBBLE_MASK) << (NIBBLE_SIZE * 15)) | (((long) b & NIBBLE_MASK) << (NIBBLE_SIZE * 14)) |
               (((long) c & NIBBLE_MASK) << (NIBBLE_SIZE * 13)) | (((long) d & NIBBLE_MASK) << (NIBBLE_SIZE * 12)) |
               (((long) e & NIBBLE_MASK) << (NIBBLE_SIZE * 11)) | (((long) f & NIBBLE_MASK) << (NIBBLE_SIZE * 10)) |
               (((long) g & NIBBLE_MASK) << (NIBBLE_SIZE * 9)) | (((long) h & NIBBLE_MASK) << (NIBBLE_SIZE * 8)) |
               (((long) i & NIBBLE_MASK) << (NIBBLE_SIZE * 7)) | (((long) j & NIBBLE_MASK) << (NIBBLE_SIZE * 6)) |
               (((long) k & NIBBLE_MASK) << (NIBBLE_SIZE * 5)) | (((long) l & NIBBLE_MASK) << (NIBBLE_SIZE * 4)) | ((long) m << (NIBBLE_SIZE * 3)) |
               (((long) n & NIBBLE_MASK) << (NIBBLE_SIZE * 2)) | (((long) o & NIBBLE_MASK) << NIBBLE_SIZE) | ((long) p & NIBBLE_MASK);
    }

    /**
     * Returns byte from given array of nibbles. <br>
     * Array must have at least 2 elements in it.
     *
     * @param nibbles
     *         array of nibbles.
     *
     * @return byte from given array of nibbles.
     */
    public static byte toByteFromNibbles(byte[] nibbles)
    {
        return toByte(nibbles[0], nibbles[1]);
    }

    /**
     * Returns byte from given array of nibbles. <br>
     * Array must have at least start + 2 elements in it.
     *
     * @param nibbles
     *         array of nibbles.
     * @param start
     *         first index of numer in array.
     *
     * @return byte from given array of nibbles.
     */
    public static byte toByteFromNibbles(byte[] nibbles, int start)
    {
        return toByte(nibbles[start], nibbles[start + 1]);
    }

    /**
     * Returns byte from given array of nibbles. <br>
     * Array must have at least 2 elements in it.
     *
     * @param nibbles
     *         array of nibbles.
     *
     * @return byte from given array of nibbles.
     */
    public static byte toByte(NibbleArray nibbles)
    {
        return toByte(nibbles.get(0), nibbles.get(1));
    }

    /**
     * Returns byte from given array of nibbles. <br>
     * Array must have at least start + 2 elements in it.
     *
     * @param nibbles
     *         array of nibbles.
     * @param start
     *         first index of numer in array.
     *
     * @return byte from given array of nibbles.
     */
    public static byte toByte(NibbleArray nibbles, int start)
    {
        return toByte(nibbles.get(start), nibbles.get(start + 1));
    }

    /**
     * Returns short from given array of nibbles. <br>
     * Array must have at least 4 elements in it.
     *
     * @param nibbles
     *         array of nibbles.
     *
     * @return short from given array of nibbles.
     */
    public static short toShortFromNibbles(byte[] nibbles)
    {
        return toShort(nibbles[0], nibbles[1], nibbles[2], nibbles[3]);
    }

    /**
     * Returns short from given array of nibbles. <br>
     * Array must have at least start + 4 elements in it.
     *
     * @param nibbles
     *         array of nibbles.
     * @param start
     *         first index of numer in array.
     *
     * @return short from given array of nibbles.
     */
    public static short toShortFromNibbles(byte[] nibbles, int start)
    {
        return toShort(nibbles[start], nibbles[start + 1], nibbles[start + 2], nibbles[start + 3]);
    }

    /**
     * Returns short from given array of nibbles. <br>
     * Array must have at least 4 elements in it.
     *
     * @param nibbles
     *         array of nibbles.
     *
     * @return short from given array of nibbles.
     */
    public static short toShort(NibbleArray nibbles)
    {
        return toShort(nibbles.get(0), nibbles.get(1), nibbles.get(2), nibbles.get(3));
    }

    /**
     * Returns short from given array of nibbles. <br>
     * Array must have at least start + 4 elements in it.
     *
     * @param nibbles
     *         array of nibbles.
     * @param start
     *         first index of numer in array.
     *
     * @return short from given array of nibbles.
     */
    public static short toShort(NibbleArray nibbles, int start)
    {
        return toShort(nibbles.get(start), nibbles.get(start + 1), nibbles.get(start + 2), nibbles.get(start + 3));
    }

    /**
     * Returns short from given array of bytes. <br>
     * Array must have at least 2 elements in it.
     *
     * @param bytes
     *         array of bytes.
     *
     * @return short from given array of bytes.
     */
    public static short toShort(byte[] bytes)
    {
        return toShort(bytes[0], bytes[1]);
    }

    /**
     * Returns short from given array of bytes. <br>
     * Array must have at least start + 2 elements in it.
     *
     * @param bytes
     *         array of bytes.
     * @param start
     *         first index of numer in array.
     *
     * @return short from given array of bytes.
     */
    public static short toShort(byte[] bytes, int start)
    {
        return toShort(bytes[start], bytes[start + 1]);
    }

    /**
     * Returns int from given array of nibbles. <br>
     * Array must have at least 8 elements in it.
     *
     * @param nibbles
     *         array of nibbles.
     *
     * @return int from given array of nibbles.
     */
    public static int toIntFromNibbles(byte[] nibbles)
    {
        return toInt(nibbles[0], nibbles[1], nibbles[2], nibbles[3], nibbles[4], nibbles[5], nibbles[6], nibbles[7]);
    }

    /**
     * Returns int from given array of nibbles. <br>
     * Array must have at least start + 8 elements in it.
     *
     * @param nibbles
     *         array of nibbles.
     * @param start
     *         first index of numer in array.
     *
     * @return int from given array of nibbles.
     */
    public static int toIntFromNibbles(byte[] nibbles, int start)
    {
        return toInt(nibbles[start], nibbles[start + 1], nibbles[start + 2], nibbles[start + 3], nibbles[start + 4], nibbles[start + 5], nibbles[start + 6],
                     nibbles[start + 7]);
    }

    /**
     * Returns int from given array of nibbles. <br>
     * Array must have at least 8 elements in it.
     *
     * @param nibbles
     *         array of nibbles.
     *
     * @return int from given array of nibbles.
     */
    public static int toInt(NibbleArray nibbles)
    {
        return toInt(nibbles.get(0), nibbles.get(1), nibbles.get(2), nibbles.get(3), nibbles.get(4), nibbles.get(5), nibbles.get(6), nibbles.get(7));
    }

    /**
     * Returns int from given array of nibbles. <br>
     * Array must have at least start + 8 elements in it.
     *
     * @param nibbles
     *         array of nibbles.
     * @param start
     *         first index of numer in array.
     *
     * @return int from given array of nibbles.
     */
    public static int toInt(NibbleArray nibbles, int start)
    {
        return toInt(nibbles.get(start), nibbles.get(start + 1), nibbles.get(start + 2), nibbles.get(start + 3), nibbles.get(start + 4), nibbles.get(start + 5),
                     nibbles.get(start + 6), nibbles.get(start + 7));
    }

    /**
     * Returns int from given array of bytes. <br>
     * Array must have at least 4 elements in it.
     *
     * @param bytes
     *         array of bytes.
     *
     * @return int from given array of bytes.
     */
    public static int toInt(byte[] bytes)
    {
        return toInt(bytes[0], bytes[1], bytes[2], bytes[3]);
    }

    /**
     * Returns int from given array of bytes. <br>
     * Array must have at least start + 4 elements in it.
     *
     * @param bytes
     *         array of bytes.
     * @param start
     *         first index of numer in array.
     *
     * @return int from given array of bytes.
     */
    public static int toInt(byte[] bytes, int start)
    {
        return toInt(bytes[start], bytes[start + 1], bytes[start + 2], bytes[start + 3]);
    }

    /**
     * Returns int from given array of shorts. <br>
     * Array must have at least 2 elements in it.
     *
     * @param shorts
     *         array of shorts.
     *
     * @return int from given array of shorts.
     */
    public static int toInt(short[] shorts)
    {
        return toInt(shorts[0], shorts[1]);
    }

    /**
     * Returns int from given array of shorts. <br>
     * Array must have at least start + 2 elements in it.
     *
     * @param shorts
     *         array of shorts.
     * @param start
     *         first index of numer in array.
     *
     * @return int from given array of shorts.
     */
    public static int toInt(short[] shorts, int start)
    {
        return toInt(shorts[start], shorts[start + 1]);
    }

    /**
     * Returns long from given array of nibbles. <br>
     * Array must have at least 16 elements in it.
     *
     * @param nibbles
     *         array of nibbles.
     *
     * @return long from given array of nibbles.
     */
    public static long toLongFromNibbles(byte[] nibbles)
    {
        return toLong(nibbles[0], nibbles[1], nibbles[2], nibbles[3], nibbles[4], nibbles[5], nibbles[6], nibbles[7], nibbles[8], nibbles[9], nibbles[10],
                      nibbles[11], nibbles[12], nibbles[13], nibbles[14], nibbles[15]);
    }

    /**
     * Returns long from given array of nibbles. <br>
     * Array must have at least start + 16 elements in it.
     *
     * @param nibbles
     *         array of nibbles.
     * @param start
     *         first index of numer in array.
     *
     * @return long from given array of nibbles.
     */
    public static long toLongFromNibbles(byte[] nibbles, int start)
    {
        return toLong(nibbles[start], nibbles[start + 1], nibbles[start + 2], nibbles[start + 3], nibbles[start + 4], nibbles[start + 5], nibbles[start + 6],
                      nibbles[start + 7], nibbles[start + 8], nibbles[start + 9], nibbles[start + 10], nibbles[start + 11], nibbles[start + 12],
                      nibbles[start + 13], nibbles[start + 14], nibbles[start + 15]);
    }

    /**
     * Returns long from given array of nibbles. <br>
     * Array must have at least 16 elements in it.
     *
     * @param nibbles
     *         array of nibbles.
     *
     * @return long from given array of nibbles.
     */
    public static long toLong(NibbleArray nibbles)
    {
        return toLong(nibbles.get(0), nibbles.get(1), nibbles.get(2), nibbles.get(3), nibbles.get(4), nibbles.get(5), nibbles.get(6), nibbles.get(7),
                      nibbles.get(8), nibbles.get(9), nibbles.get(10), nibbles.get(11), nibbles.get(12), nibbles.get(13), nibbles.get(14), nibbles.get(15));
    }

    /**
     * Returns long from given array of nibbles. <br>
     * Array must have at least start + 16 elements in it.
     *
     * @param nibbles
     *         array of nibbles.
     * @param start
     *         first index of numer in array.
     *
     * @return long from given array of nibbles.
     */
    public static long toLong(NibbleArray nibbles, int start)
    {
        return toLong(nibbles.get(start), nibbles.get(start + 1), nibbles.get(start + 2), nibbles.get(start + 3), nibbles.get(start + 4),
                      nibbles.get(start + 5), nibbles.get(start + 6), nibbles.get(start + 7), nibbles.get(start + 8), nibbles.get(start + 9),
                      nibbles.get(start + 10), nibbles.get(start + 11), nibbles.get(start + 12), nibbles.get(start + 13), nibbles.get(start + 14),
                      nibbles.get(start + 15));
    }

    /**
     * Returns long from given array of bytes. <br>
     * Array must have at least 8 elements in it.
     *
     * @param bytes
     *         array of bytes.
     *
     * @return long from given array of bytes.
     */
    public static long toLong(byte[] bytes)
    {
        return toLong(bytes[0], bytes[1], bytes[2], bytes[3], bytes[4], bytes[5], bytes[6], bytes[7]);
    }

    /**
     * Returns long from given array of bytes. <br>
     * Array must have at least start + 8 elements in it.
     *
     * @param bytes
     *         array of bytes.
     * @param start
     *         first index of numer in array.
     *
     * @return long from given array of bytes.
     */
    public static long toLong(byte[] bytes, int start)
    {
        return toLong(bytes[start], bytes[start + 1], bytes[start + 2], bytes[start + 3], bytes[start + 4], bytes[start + 5], bytes[start + 6],
                      bytes[start + 7]);
    }

    /**
     * Returns long from given array of shorts. <br>
     * Array must have at least 4 elements in it.
     *
     * @param shorts
     *         array of shorts.
     *
     * @return long from given array of shorts.
     */
    public static long toLong(short[] shorts)
    {
        return toLong(shorts[0], shorts[1], shorts[2], shorts[3]);
    }

    /**
     * Returns long from given array of shorts. <br>
     * Array must have at least start + 4 elements in it.
     *
     * @param shorts
     *         array of shorts.
     * @param start
     *         first index of numer in array.
     *
     * @return long from given array of shorts.
     */
    public static long toLong(short[] shorts, int start)
    {
        return toLong(shorts[start], shorts[start + 1], shorts[start + 2], shorts[start + 3]);
    }

    /**
     * Returns long from given array of ints. <br>
     * Array must have at least 2 elements in it.
     *
     * @param ints
     *         array of ints.
     *
     * @return long from given array of ints.
     */
    public static long toLong(int[] ints)
    {
        return toLong(ints[0], ints[1]);
    }

    /**
     * Returns long from given array of ints. <br>
     * Array must have at least start + 2 elements in it.
     *
     * @param ints
     *         array of ints.
     * @param start
     *         first index of numer in array.
     *
     * @return long from given array of ints.
     */
    public static long toLong(int[] ints, int start)
    {
        return toLong(ints[start], ints[start + 1]);
    }

    /*
     * From byte
     */

    /**
     * Returns first nibble from given byte.
     *
     * @param x
     *         value for extracting a number from it.
     *
     * @return first nibble from given byte.
     */
    public static byte getNibbleA(byte x)
    {
        return (byte) ((x >> NIBBLE_SIZE) & NIBBLE_MASK);
    }

    /**
     * Returns second nibble from given byte.
     *
     * @param x
     *         value for extracting a number from it.
     *
     * @return second nibble from given byte.
     */
    public static byte getNibbleB(byte x)
    {
        return (byte) (x & NIBBLE_MASK);
    }

    /**
     * Returns nibble on given index from given byte.
     *
     * @param x
     *         value for extracting a number from it.
     * @param index
     *         index of number in value. (from 0)
     *
     * @return nibble on given index from given byte.
     */
    public static byte getNibble(byte x, int index)
    {
        index = validateIndex("nibbles", "byte", index, 2);
        return (byte) ((x >> (NIBBLE_SIZE * index)) & NIBBLE_MASK);
    }

    /*
     * From short
     */

    /**
     * Returns first byte from given short.
     *
     * @param x
     *         value for extracting a number from it.
     *
     * @return first byte from given short.
     */
    public static byte getByteA(short x)
    {
        return (byte) (x >> BYTE_SIZE);
    }

    /**
     * Returns second byte from given short.
     *
     * @param x
     *         value for extracting a number from it.
     *
     * @return second byte from given short.
     */
    public static byte getByteB(short x)
    {
        return (byte) x;
    }

    /**
     * Returns byte on given index from given short.
     *
     * @param x
     *         value for extracting a number from it.
     * @param index
     *         index of number in value. (from 0)
     *
     * @return byte on given index from given short.
     */
    public static byte getByte(short x, int index)
    {
        index = validateIndex("bytes", "short", index, 2);
        return (byte) (x >> (BYTE_SIZE * index));
    }

    /**
     * Returns first nibble from given short.
     *
     * @param x
     *         value for extracting a number from it.
     *
     * @return first nibble from given short.
     */
    public static byte getNibbleA(short x)
    {
        return (byte) ((x >> NIBBLE_SIZE) & NIBBLE_MASK);
    }

    /**
     * Returns second nibble from given short.
     *
     * @param x
     *         value for extracting a number from it.
     *
     * @return second nibble from given short.
     */
    public static byte getNibbleB(short x)
    {
        return (byte) ((x >> (NIBBLE_SIZE * 2)) & NIBBLE_MASK);
    }

    /**
     * Returns third nibble from given short.
     *
     * @param x
     *         value for extracting a number from it.
     *
     * @return third nibble from given short.
     */
    public static byte getNibbleC(short x)
    {
        return (byte) ((x >> (NIBBLE_SIZE * 3)) & NIBBLE_MASK);
    }

    /**
     * Returns fourth nibble from given short.
     *
     * @param x
     *         value for extracting a number from it.
     *
     * @return fourth nibble from given short.
     */
    public static byte getNibbleD(short x)
    {
        return (byte) (x & NIBBLE_MASK);
    }

    /**
     * Returns nibble on given index from given short.
     *
     * @param x
     *         value for extracting a number from it.
     * @param index
     *         index of number in value. (from 0)
     *
     * @return nibble on given index from given short.
     */
    public static byte getNibble(short x, int index)
    {
        index = validateIndex("nibbles", "short", index, 4);
        return (byte) (x >> ((NIBBLE_SIZE * index) & NIBBLE_MASK));
    }

    /*
     * From int
     */

    /**
     * Returns first short from given int.
     *
     * @param x
     *         value for extracting a number from it.
     *
     * @return first short from given int.
     */
    public static short getShortA(int x)
    {
        return (short) (x >> SHORT_SIZE);
    }

    /**
     * Returns second short from given int.
     *
     * @param x
     *         value for extracting a number from it.
     *
     * @return second short from given int.
     */
    public static short getShortB(int x)
    {
        return (short) x;
    }

    /**
     * Returns short on given index from given int.
     *
     * @param x
     *         value for extracting a number from it.
     * @param index
     *         index of number in value. (from 0)
     *
     * @return short on given index from given int.
     */
    public static short getShort(int x, int index)
    {
        index = validateIndex("shorts", "int", index, 2);
        return (byte) (x >> (SHORT_SIZE * index));
    }

    /**
     * Returns first byte from given int.
     *
     * @param x
     *         value for extracting a number from it.
     *
     * @return first byte from given int.
     */
    public static byte getByteA(int x)
    {
        return (byte) (x >> (BYTE_SIZE * 3));
    }

    /**
     * Returns second byte from given int.
     *
     * @param x
     *         value for extracting a number from it.
     *
     * @return second byte from given int.
     */
    public static byte getByteB(int x)
    {
        return (byte) (x >> (BYTE_SIZE * 2));
    }

    /**
     * Returns third byte from given int.
     *
     * @param x
     *         value for extracting a number from it.
     *
     * @return third byte from given int.
     */
    public static byte getByteC(int x)
    {
        return (byte) (x >> BYTE_SIZE);
    }

    /**
     * Returns fourth byte from given int.
     *
     * @param x
     *         value for extracting a number from it.
     *
     * @return fourth byte from given int.
     */
    public static byte getByteD(int x)
    {
        return (byte) (x);
    }

    /**
     * Returns byte on given index from given int.
     *
     * @param x
     *         value for extracting a number from it.
     * @param index
     *         index of number in value. (from 0)
     *
     * @return byte on given index from given int.
     */
    public static byte getByte(int x, int index)
    {
        index = validateIndex("bytes", "int", index, 4);
        return (byte) (x >> (BYTE_SIZE * index));
    }

    /**
     * Returns first nibble from given int.
     *
     * @param x
     *         value for extracting a number from it.
     *
     * @return first nibble from given int.
     */
    public static byte getNibbleA(int x)
    {
        return (byte) ((x >> NIBBLE_SIZE) & NIBBLE_MASK);
    }

    /**
     * Returns second nibble from given int.
     *
     * @param x
     *         value for extracting a number from it.
     *
     * @return second nibble from given int.
     */
    public static byte getNibbleB(int x)
    {
        return (byte) ((x >> (NIBBLE_SIZE * 2)) & NIBBLE_MASK);
    }

    /**
     * Returns third nibble from given int.
     *
     * @param x
     *         value for extracting a number from it.
     *
     * @return third nibble from given int.
     */
    public static byte getNibbleC(int x)
    {
        return (byte) ((x >> (NIBBLE_SIZE * 3)) & NIBBLE_MASK);
    }

    /**
     * Returns fourth nibble from given int.
     *
     * @param x
     *         value for extracting a number from it.
     *
     * @return fourth nibble from given int.
     */
    public static byte getNibbleD(int x)
    {
        return (byte) ((x >> (NIBBLE_SIZE * 4)) & NIBBLE_MASK);
    }

    /**
     * Returns fifth nibble from given int.
     *
     * @param x
     *         value for extracting a number from it.
     *
     * @return fifth nibble from given int.
     */
    public static byte getNibbleE(int x)
    {
        return (byte) ((x >> (NIBBLE_SIZE * 5)) & NIBBLE_MASK);
    }

    /**
     * Returns sixth nibble from given int.
     *
     * @param x
     *         value for extracting a number from it.
     *
     * @return sixth nibble from given int.
     */
    public static byte getNibbleF(int x)
    {
        return (byte) ((x >> (NIBBLE_SIZE * 6)) & NIBBLE_MASK);
    }

    /**
     * Returns seventh nibble from given int.
     *
     * @param x
     *         value for extracting a number from it.
     *
     * @return seventh nibble from given int.
     */
    public static byte getNibbleG(int x)
    {
        return (byte) ((x >> (NIBBLE_SIZE * 7)) & NIBBLE_MASK);
    }

    /**
     * Returns eight nibble from given int.
     *
     * @param x
     *         value for extracting a number from it.
     *
     * @return eight nibble from given int.
     */
    public static byte getNibbleH(int x)
    {
        return (byte) (x & NIBBLE_MASK);
    }

    /**
     * Returns nibble on given index from given int.
     *
     * @param x
     *         value for extracting a number from it.
     * @param index
     *         index of number in value. (from 0)
     *
     * @return nibble on given index from given int.
     */
    public static byte getNibble(int x, int index)
    {
        index = validateIndex("nibbles", "int", index, 8);
        return (byte) (x >> ((NIBBLE_SIZE * index) & NIBBLE_MASK));
    }

    /*
     * From long
     */

    /**
     * Returns first int from given long.
     *
     * @param x
     *         value for extracting a number from it.
     *
     * @return first int from given long.
     */
    public static int getIntA(long x)
    {
        return (int) (x >> INT_SIZE);
    }

    /**
     * Returns second int from given long.
     *
     * @param x
     *         value for extracting a number from it.
     *
     * @return second int from given long.
     */
    public static int getIntB(long x)
    {
        return (int) x;
    }

    /**
     * Returns int on given index from given long.
     *
     * @param x
     *         value for extracting a number from it.
     * @param index
     *         index of number in value. (from 0)
     *
     * @return int on given index from given long.
     */
    public static int getInt(long x, int index)
    {
        index = validateIndex("ints", "long", index, 2);
        return (byte) (x >> (INT_SIZE * index));
    }

    /**
     * Returns first short from given long.
     *
     * @param x
     *         value for extracting a number from it.
     *
     * @return first short from given long.
     */
    public static short getShortA(long x)
    {
        return (short) (x >> (SHORT_SIZE * 3));
    }

    /**
     * Returns second short from given long.
     *
     * @param x
     *         value for extracting a number from it.
     *
     * @return second short from given long.
     */
    public static short getShortB(long x)
    {
        return (short) (x >> (SHORT_SIZE * 2));
    }

    /**
     * Returns third short from given long.
     *
     * @param x
     *         value for extracting a number from it.
     *
     * @return third short from given long.
     */
    public static short getShortC(long x)
    {
        return (short) (x >> SHORT_SIZE);
    }

    /**
     * Returns fourth short from given long.
     *
     * @param x
     *         value for extracting a number from it.
     *
     * @return fourth short from given long.
     */
    public static short getShortD(long x)
    {
        return (short) (x);
    }

    /**
     * Returns short on given index from given long.
     *
     * @param x
     *         value for extracting a number from it.
     * @param index
     *         index of number in value. (from 0)
     *
     * @return short on given index from given long.
     */
    public static short getShort(long x, int index)
    {
        index = validateIndex("shorts", "long", index, 4);
        return (byte) (x >> (SHORT_SIZE * index));
    }

    /**
     * Returns first byte from given long.
     *
     * @param x
     *         value for extracting a number from it.
     *
     * @return first byte from given long.
     */
    public static byte getByteA(long x)
    {
        return (byte) (x >> (BYTE_SIZE * 7));
    }

    /**
     * Returns second byte from given long.
     *
     * @param x
     *         value for extracting a number from it.
     *
     * @return second byte from given long.
     */
    public static byte getByteB(long x)
    {
        return (byte) (x >> (BYTE_SIZE * 6));
    }

    /**
     * Returns third byte from given long.
     *
     * @param x
     *         value for extracting a number from it.
     *
     * @return third byte from given long.
     */
    public static byte getByteC(long x)
    {
        return (byte) (x >> (BYTE_SIZE * 5));
    }

    /**
     * Returns fourth byte from given long.
     *
     * @param x
     *         value for extracting a number from it.
     *
     * @return fourth byte from given long.
     */
    public static byte getByteD(long x)
    {
        return (byte) (x >> (BYTE_SIZE * 4));
    }

    /**
     * Returns fifth byte from given long.
     *
     * @param x
     *         value for extracting a number from it.
     *
     * @return fifth byte from given long.
     */
    public static byte getByteE(long x)
    {
        return (byte) (x >> (BYTE_SIZE * 3));
    }

    /**
     * Returns sixth byte from given long.
     *
     * @param x
     *         value for extracting a number from it.
     *
     * @return sixth byte from given long.
     */
    public static byte getByteF(long x)
    {
        return (byte) (x >> (BYTE_SIZE * 2));
    }

    /**
     * Returns seventh byte from given long.
     *
     * @param x
     *         value for extracting a number from it.
     *
     * @return seventh byte from given long.
     */
    public static byte getByteG(long x)
    {
        return (byte) (x >> BYTE_SIZE);
    }

    /**
     * Returns eighth byte from given long.
     *
     * @param x
     *         value for extracting a number from it.
     *
     * @return eighth byte from given long.
     */
    public static byte getByteH(long x)
    {
        return (byte) (x);
    }

    /**
     * Returns byte on given index from given long.
     *
     * @param x
     *         value for extracting a number from it.
     * @param index
     *         index of number in value. (from 0)
     *
     * @return byte on given index from given long.
     */
    public static byte getByte(long x, int index)
    {
        index = validateIndex("bytes", "long", index, 8);
        return (byte) (x >> (BYTE_SIZE * index));
    }

    /**
     * Returns first nibble from given long.
     *
     * @param x
     *         value for extracting a number from it.
     *
     * @return first nibble from given long.
     */
    public static byte getNibbleA(long x)
    {
        return (byte) ((x >> (NIBBLE_SIZE * 15)) & NIBBLE_MASK);
    }

    /**
     * Returns second nibble from given long.
     *
     * @param x
     *         value for extracting a number from it.
     *
     * @return second nibble from given long.
     */
    public static byte getNibbleB(long x)
    {
        return (byte) ((x >> (NIBBLE_SIZE * 14)) & NIBBLE_MASK);
    }

    /**
     * Returns third nibble from given long.
     *
     * @param x
     *         value for extracting a number from it.
     *
     * @return third nibble from given long.
     */
    public static byte getNibbleC(long x)
    {
        return (byte) ((x >> (NIBBLE_SIZE * 13)) & NIBBLE_MASK);
    }

    /**
     * Returns fourth nibble from given long.
     *
     * @param x
     *         value for extracting a number from it.
     *
     * @return fourth nibble from given long.
     */
    public static byte getNibbleD(long x)
    {
        return (byte) ((x >> (NIBBLE_SIZE * 12)) & NIBBLE_MASK);
    }

    /**
     * Returns fifth nibble from given long.
     *
     * @param x
     *         value for extracting a number from it.
     *
     * @return fifth nibble from given long.
     */
    public static byte getNibbleE(long x)
    {
        return (byte) ((x >> (NIBBLE_SIZE * 11)) & NIBBLE_MASK);
    }

    /**
     * Returns sixth nibble from given long.
     *
     * @param x
     *         value for extracting a number from it.
     *
     * @return sixth nibble from given long.
     */
    public static byte getNibbleF(long x)
    {
        return (byte) ((x >> (NIBBLE_SIZE * 10)) & NIBBLE_MASK);
    }

    /**
     * Returns seventh nibble from given long.
     *
     * @param x
     *         value for extracting a number from it.
     *
     * @return seventh nibble from given long.
     */
    public static byte getNibbleG(long x)
    {
        return (byte) ((x >> (NIBBLE_SIZE * 9)) & NIBBLE_MASK);
    }

    /**
     * Returns eighth nibble from given long.
     *
     * @param x
     *         value for extracting a number from it.
     *
     * @return eighth nibble from given long.
     */
    public static byte getNibbleH(long x)
    {
        return (byte) ((x >> (NIBBLE_SIZE * 8)) & NIBBLE_MASK);
    }

    /**
     * Returns ninth nibble from given long.
     *
     * @param x
     *         value for extracting a number from it.
     *
     * @return ninth nibble from given long.
     */
    public static byte getNibbleI(long x)
    {
        return (byte) ((x >> (NIBBLE_SIZE * 7)) & NIBBLE_MASK);
    }

    /**
     * Returns tenth nibble from given long.
     *
     * @param x
     *         value for extracting a number from it.
     *
     * @return tenth nibble from given long.
     */
    public static byte getNibbleJ(long x)
    {
        return (byte) ((x >> (NIBBLE_SIZE * 6)) & NIBBLE_MASK);
    }

    /**
     * Returns eleventh nibble from given long.
     *
     * @param x
     *         value for extracting a number from it.
     *
     * @return eleventh nibble from given long.
     */
    public static byte getNibbleK(long x)
    {
        return (byte) ((x >> (NIBBLE_SIZE * 5)) & NIBBLE_MASK);
    }

    /**
     * Returns twelfth nibble from given long.
     *
     * @param x
     *         value for extracting a number from it.
     *
     * @return twelfth nibble from given long.
     */
    public static byte getNibbleL(long x)
    {
        return (byte) ((x >> (NIBBLE_SIZE * 4)) & NIBBLE_MASK);
    }

    /**
     * Returns thirteenth nibble from given long.
     *
     * @param x
     *         value for extracting a number from it.
     *
     * @return thirteenth nibble from given long.
     */
    public static byte getNibbleM(long x)
    {
        return (byte) ((x >> (NIBBLE_SIZE * 3)) & NIBBLE_MASK);
    }

    /**
     * Returns fourteenth nibble from given long.
     *
     * @param x
     *         value for extracting a number from it.
     *
     * @return fourteenth nibble from given long.
     */
    public static byte getNibbleN(long x)
    {
        return (byte) ((x >> (NIBBLE_SIZE * 2)) & NIBBLE_MASK);
    }

    /**
     * Returns fifteenth nibble from given long.
     *
     * @param x
     *         value for extracting a number from it.
     *
     * @return fifteenth nibble from given long.
     */
    public static byte getNibbleO(long x)
    {
        return (byte) ((x >> NIBBLE_SIZE) & NIBBLE_MASK);
    }

    /**
     * Returns sixteenth nibble from given long.
     *
     * @param x
     *         value for extracting a number from it.
     *
     * @return sixteenth nibble from given long.
     */
    public static byte getNibbleP(long x)
    {
        return (byte) (x & NIBBLE_MASK);
    }

    /**
     * Returns nibble on given index from given long.
     *
     * @param x
     *         value for extracting a number from it.
     * @param index
     *         index of number in value. (from 0)
     *
     * @return nibble on given index from given long.
     */
    public static byte getNibble(long x, int index)
    {
        index = validateIndex("nibbles", "long", index, 16);
        return (byte) (x >> ((NIBBLE_SIZE * index) & NIBBLE_MASK));
    }

    private static int validateIndex(String smallType, String bigType, int x, int max)
    {
        int maxi = max - 1;
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
