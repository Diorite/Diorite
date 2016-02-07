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

import java.nio.ByteOrder;

import org.diorite.utils.collections.arrays.NibbleArray;

/**
 * Represent class that can be used to pack one number type into another, like 4 bytes to int
 * using big endian or little endian byte order selected by implementation of this interface.
 */
public interface EndianUtil
{
    /**
     * Bit mask of nibble.
     */
    int  NIBBLE_MASK = 0xF;
    /**
     * Bit mask of byte.
     */
    int  BYTE_MASK   = 0xFF;
    /**
     * Bit mask of short.
     */
    int  SHORT_MASK  = 0xFFFF;
    /**
     * Bit mask of int.
     */
    long INT_MASK    = 0xFFFFFFFFL;
    /**
     * Bit mask of long.
     */
    long LONG_MASK   = 0xFFFFFFFFFFFFFFFFL;
    /**
     * Size of nibble in bits.
     */
    int  NIBBLE_SIZE = 4;

    /**
     * Size of byte in bits.
     */
    int BYTE_SIZE = 8;

    /**
     * Size of short in bits.
     */
    int SHORT_SIZE = 16;

    /**
     * Size of int in bits.
     */
    int INT_SIZE = 32;

    /**
     * Size of long in bits.
     */
    int LONG_SIZE = 64;

    /**
     * Returns {@link ByteOrder} used by this endian util.
     *
     * @return {@link ByteOrder} used by this endian util.
     */
    ByteOrder getEndianType();

    /**
     * Returns byte with 2 given nibbles in it.
     *
     * @param a nibble a.
     * @param b nibble b.
     *
     * @return byte with 2 given nibbles in it.
     */
    byte toByte(byte a, byte b);

    /**
     * Returns short with 2 given bytes in it.
     *
     * @param a byte a.
     * @param b byte b.
     *
     * @return short with 2 given bytes in it.
     */
    short toShort(byte a, byte b);

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
    short toShort(byte a, byte b, byte c, byte d);

    /**
     * Returns int with 2 given shorts in it.
     *
     * @param a short a.
     * @param b short b.
     *
     * @return int with 2 given shorts in it.
     */
    int toInt(short a, short b);

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
    int toInt(byte a, byte b, byte c, byte d);

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
    int toInt(byte a, byte b, byte c, byte d, byte e, byte f, byte g, byte h);

    /**
     * Returns long with 2 given ints in it.
     *
     * @param a int a.
     * @param b int b.
     *
     * @return long with 2 given ints in it.
     */
    long toLong(int a, int b);

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
    long toLong(short a, short b, short c, short d);

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
    long toLong(byte a, byte b, byte c, byte d, byte e, byte f, byte g, byte h);

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
    long toLong(byte a, byte b, byte c, byte d, byte e, byte f, byte g, byte h, byte i, byte j, byte k, byte l, byte m, byte n, byte o, byte p);

    /**
     * Returns byte from given array of nibbles. <br>
     * Array must have at least 2 elements in it.
     *
     * @param nibbles array of nibbles.
     *
     * @return byte from given array of nibbles.
     */
    byte toByteFromNibbles(byte[] nibbles);

    /**
     * Returns byte from given array of nibbles. <br>
     * Array must have at least start + 2 elements in it.
     *
     * @param nibbles array of nibbles.
     * @param start   first index of numer in array.
     *
     * @return byte from given array of nibbles.
     */
    byte toByteFromNibbles(byte[] nibbles, int start);

    /**
     * Returns byte from given array of nibbles. <br>
     * Array must have at least 2 elements in it.
     *
     * @param nibbles array of nibbles.
     *
     * @return byte from given array of nibbles.
     */
    byte toByte(NibbleArray nibbles);

    /**
     * Returns byte from given array of nibbles. <br>
     * Array must have at least start + 2 elements in it.
     *
     * @param nibbles array of nibbles.
     * @param start   first index of numer in array.
     *
     * @return byte from given array of nibbles.
     */
    byte toByte(NibbleArray nibbles, int start);

    /**
     * Returns short from given array of nibbles. <br>
     * Array must have at least 4 elements in it.
     *
     * @param nibbles array of nibbles.
     *
     * @return short from given array of nibbles.
     */
    short toShortFromNibbles(byte[] nibbles);

    /**
     * Returns short from given array of nibbles. <br>
     * Array must have at least start + 4 elements in it.
     *
     * @param nibbles array of nibbles.
     * @param start   first index of numer in array.
     *
     * @return short from given array of nibbles.
     */
    short toShortFromNibbles(byte[] nibbles, int start);

    /**
     * Returns short from given array of nibbles. <br>
     * Array must have at least 4 elements in it.
     *
     * @param nibbles array of nibbles.
     *
     * @return short from given array of nibbles.
     */
    short toShort(NibbleArray nibbles);

    /**
     * Returns short from given array of nibbles. <br>
     * Array must have at least start + 4 elements in it.
     *
     * @param nibbles array of nibbles.
     * @param start   first index of numer in array.
     *
     * @return short from given array of nibbles.
     */
    short toShort(NibbleArray nibbles, int start);

    /**
     * Returns short from given array of bytes. <br>
     * Array must have at least 2 elements in it.
     *
     * @param bytes array of bytes.
     *
     * @return short from given array of bytes.
     */
    short toShort(byte[] bytes);

    /**
     * Returns short from given array of bytes. <br>
     * Array must have at least start + 2 elements in it.
     *
     * @param bytes array of bytes.
     * @param start first index of numer in array.
     *
     * @return short from given array of bytes.
     */
    short toShort(byte[] bytes, int start);

    /**
     * Returns int from given array of nibbles. <br>
     * Array must have at least 8 elements in it.
     *
     * @param nibbles array of nibbles.
     *
     * @return int from given array of nibbles.
     */
    int toIntFromNibbles(byte[] nibbles);

    /**
     * Returns int from given array of nibbles. <br>
     * Array must have at least start + 8 elements in it.
     *
     * @param nibbles array of nibbles.
     * @param start   first index of numer in array.
     *
     * @return int from given array of nibbles.
     */
    int toIntFromNibbles(byte[] nibbles, int start);

    /**
     * Returns int from given array of nibbles. <br>
     * Array must have at least 8 elements in it.
     *
     * @param nibbles array of nibbles.
     *
     * @return int from given array of nibbles.
     */
    int toInt(NibbleArray nibbles);

    /**
     * Returns int from given array of nibbles. <br>
     * Array must have at least start + 8 elements in it.
     *
     * @param nibbles array of nibbles.
     * @param start   first index of numer in array.
     *
     * @return int from given array of nibbles.
     */
    int toInt(NibbleArray nibbles, int start);

    /**
     * Returns int from given array of bytes. <br>
     * Array must have at least 4 elements in it.
     *
     * @param bytes array of bytes.
     *
     * @return int from given array of bytes.
     */
    int toInt(byte[] bytes);

    /**
     * Returns int from given array of bytes. <br>
     * Array must have at least start + 4 elements in it.
     *
     * @param bytes array of bytes.
     * @param start first index of numer in array.
     *
     * @return int from given array of bytes.
     */
    int toInt(byte[] bytes, int start);

    /**
     * Returns int from given array of shorts. <br>
     * Array must have at least 2 elements in it.
     *
     * @param shorts array of shorts.
     *
     * @return int from given array of shorts.
     */
    int toInt(short[] shorts);

    /**
     * Returns int from given array of shorts. <br>
     * Array must have at least start + 2 elements in it.
     *
     * @param shorts array of shorts.
     * @param start  first index of numer in array.
     *
     * @return int from given array of shorts.
     */
    int toInt(short[] shorts, int start);

    /**
     * Returns long from given array of nibbles. <br>
     * Array must have at least 16 elements in it.
     *
     * @param nibbles array of nibbles.
     *
     * @return long from given array of nibbles.
     */
    long toLongFromNibbles(byte[] nibbles);

    /**
     * Returns long from given array of nibbles. <br>
     * Array must have at least start + 16 elements in it.
     *
     * @param nibbles array of nibbles.
     * @param start   first index of numer in array.
     *
     * @return long from given array of nibbles.
     */
    long toLongFromNibbles(byte[] nibbles, int start);

    /**
     * Returns long from given array of nibbles. <br>
     * Array must have at least 16 elements in it.
     *
     * @param nibbles array of nibbles.
     *
     * @return long from given array of nibbles.
     */
    long toLong(NibbleArray nibbles);

    /**
     * Returns long from given array of nibbles. <br>
     * Array must have at least start + 16 elements in it.
     *
     * @param nibbles array of nibbles.
     * @param start   first index of numer in array.
     *
     * @return long from given array of nibbles.
     */
    long toLong(NibbleArray nibbles, int start);

    /**
     * Returns long from given array of bytes. <br>
     * Array must have at least 8 elements in it.
     *
     * @param bytes array of bytes.
     *
     * @return long from given array of bytes.
     */
    long toLong(byte[] bytes);

    /**
     * Returns long from given array of bytes. <br>
     * Array must have at least start + 8 elements in it.
     *
     * @param bytes array of bytes.
     * @param start first index of numer in array.
     *
     * @return long from given array of bytes.
     */
    long toLong(byte[] bytes, int start);

    /**
     * Returns long from given array of shorts. <br>
     * Array must have at least 4 elements in it.
     *
     * @param shorts array of shorts.
     *
     * @return long from given array of shorts.
     */
    long toLong(short[] shorts);

    /**
     * Returns long from given array of shorts. <br>
     * Array must have at least start + 4 elements in it.
     *
     * @param shorts array of shorts.
     * @param start  first index of numer in array.
     *
     * @return long from given array of shorts.
     */
    long toLong(short[] shorts, int start);

    /**
     * Returns long from given array of ints. <br>
     * Array must have at least 2 elements in it.
     *
     * @param ints array of ints.
     *
     * @return long from given array of ints.
     */
    long toLong(int[] ints);

    /**
     * Returns long from given array of ints. <br>
     * Array must have at least start + 2 elements in it.
     *
     * @param ints  array of ints.
     * @param start first index of numer in array.
     *
     * @return long from given array of ints.
     */
    long toLong(int[] ints, int start);

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
    byte getNibbleA(byte x);

    /**
     * Returns second nibble from given byte.
     *
     * @param x value for extracting a number from it.
     *
     * @return second nibble from given byte.
     */
    byte getNibbleB(byte x);

    /**
     * Returns nibble on given index from given byte.
     *
     * @param x     value for extracting a number from it.
     * @param index index of number in value. (from 0)
     *
     * @return nibble on given index from given byte.
     */
    byte getNibble(byte x, int index);

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
    byte getByteA(short x);

    /**
     * Returns second byte from given short.
     *
     * @param x value for extracting a number from it.
     *
     * @return second byte from given short.
     */
    byte getByteB(short x);

    /**
     * Returns byte on given index from given short.
     *
     * @param x     value for extracting a number from it.
     * @param index index of number in value. (from 0)
     *
     * @return byte on given index from given short.
     */
    byte getByte(short x, int index);

    /**
     * Returns first nibble from given short.
     *
     * @param x value for extracting a number from it.
     *
     * @return first nibble from given short.
     */
    byte getNibbleA(short x);

    /**
     * Returns second nibble from given short.
     *
     * @param x value for extracting a number from it.
     *
     * @return second nibble from given short.
     */
    byte getNibbleB(short x);

    /**
     * Returns third nibble from given short.
     *
     * @param x value for extracting a number from it.
     *
     * @return third nibble from given short.
     */
    byte getNibbleC(short x);

    /**
     * Returns fourth nibble from given short.
     *
     * @param x value for extracting a number from it.
     *
     * @return fourth nibble from given short.
     */
    byte getNibbleD(short x);

    /**
     * Returns nibble on given index from given short.
     *
     * @param x     value for extracting a number from it.
     * @param index index of number in value. (from 0)
     *
     * @return nibble on given index from given short.
     */
    byte getNibble(short x, int index);

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
    short getShortA(int x);

    /**
     * Returns second short from given int.
     *
     * @param x value for extracting a number from it.
     *
     * @return second short from given int.
     */
    short getShortB(int x);

    /**
     * Returns short on given index from given int.
     *
     * @param x     value for extracting a number from it.
     * @param index index of number in value. (from 0)
     *
     * @return short on given index from given int.
     */
    short getShort(int x, int index);

    /**
     * Returns first byte from given int.
     *
     * @param x value for extracting a number from it.
     *
     * @return first byte from given int.
     */
    byte getByteA(int x);

    /**
     * Returns second byte from given int.
     *
     * @param x value for extracting a number from it.
     *
     * @return second byte from given int.
     */
    byte getByteB(int x);

    /**
     * Returns third byte from given int.
     *
     * @param x value for extracting a number from it.
     *
     * @return third byte from given int.
     */
    byte getByteC(int x);

    /**
     * Returns fourth byte from given int.
     *
     * @param x value for extracting a number from it.
     *
     * @return fourth byte from given int.
     */
    byte getByteD(int x);

    /**
     * Returns byte on given index from given int.
     *
     * @param x     value for extracting a number from it.
     * @param index index of number in value. (from 0)
     *
     * @return byte on given index from given int.
     */
    byte getByte(int x, int index);

    /**
     * Returns first nibble from given int.
     *
     * @param x value for extracting a number from it.
     *
     * @return first nibble from given int.
     */
    byte getNibbleA(int x);

    /**
     * Returns second nibble from given int.
     *
     * @param x value for extracting a number from it.
     *
     * @return second nibble from given int.
     */
    byte getNibbleB(int x);

    /**
     * Returns third nibble from given int.
     *
     * @param x value for extracting a number from it.
     *
     * @return third nibble from given int.
     */
    byte getNibbleC(int x);

    /**
     * Returns fourth nibble from given int.
     *
     * @param x value for extracting a number from it.
     *
     * @return fourth nibble from given int.
     */
    byte getNibbleD(int x);

    /**
     * Returns fifth nibble from given int.
     *
     * @param x value for extracting a number from it.
     *
     * @return fifth nibble from given int.
     */
    byte getNibbleE(int x);

    /**
     * Returns sixth nibble from given int.
     *
     * @param x value for extracting a number from it.
     *
     * @return sixth nibble from given int.
     */
    byte getNibbleF(int x);

    /**
     * Returns seventh nibble from given int.
     *
     * @param x value for extracting a number from it.
     *
     * @return seventh nibble from given int.
     */
    byte getNibbleG(int x);

    /**
     * Returns eight nibble from given int.
     *
     * @param x value for extracting a number from it.
     *
     * @return eight nibble from given int.
     */
    byte getNibbleH(int x);

    /**
     * Returns nibble on given index from given int.
     *
     * @param x     value for extracting a number from it.
     * @param index index of number in value. (from 0)
     *
     * @return nibble on given index from given int.
     */
    byte getNibble(int x, int index);

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
    int getIntA(long x);

    /**
     * Returns second int from given long.
     *
     * @param x value for extracting a number from it.
     *
     * @return second int from given long.
     */
    int getIntB(long x);

    /**
     * Returns int on given index from given long.
     *
     * @param x     value for extracting a number from it.
     * @param index index of number in value. (from 0)
     *
     * @return int on given index from given long.
     */
    int getInt(long x, int index);

    /**
     * Returns first short from given long.
     *
     * @param x value for extracting a number from it.
     *
     * @return first short from given long.
     */
    short getShortA(long x);

    /**
     * Returns second short from given long.
     *
     * @param x value for extracting a number from it.
     *
     * @return second short from given long.
     */
    short getShortB(long x);

    /**
     * Returns third short from given long.
     *
     * @param x value for extracting a number from it.
     *
     * @return third short from given long.
     */
    short getShortC(long x);

    /**
     * Returns fourth short from given long.
     *
     * @param x value for extracting a number from it.
     *
     * @return fourth short from given long.
     */
    short getShortD(long x);

    /**
     * Returns short on given index from given long.
     *
     * @param x     value for extracting a number from it.
     * @param index index of number in value. (from 0)
     *
     * @return short on given index from given long.
     */
    short getShort(long x, int index);

    /**
     * Returns first byte from given long.
     *
     * @param x value for extracting a number from it.
     *
     * @return first byte from given long.
     */
    byte getByteA(long x);

    /**
     * Returns second byte from given long.
     *
     * @param x value for extracting a number from it.
     *
     * @return second byte from given long.
     */
    byte getByteB(long x);

    /**
     * Returns third byte from given long.
     *
     * @param x value for extracting a number from it.
     *
     * @return third byte from given long.
     */
    byte getByteC(long x);

    /**
     * Returns fourth byte from given long.
     *
     * @param x value for extracting a number from it.
     *
     * @return fourth byte from given long.
     */
    byte getByteD(long x);

    /**
     * Returns fifth byte from given long.
     *
     * @param x value for extracting a number from it.
     *
     * @return fifth byte from given long.
     */
    byte getByteE(long x);

    /**
     * Returns sixth byte from given long.
     *
     * @param x value for extracting a number from it.
     *
     * @return sixth byte from given long.
     */
    byte getByteF(long x);

    /**
     * Returns seventh byte from given long.
     *
     * @param x value for extracting a number from it.
     *
     * @return seventh byte from given long.
     */
    byte getByteG(long x);

    /**
     * Returns eighth byte from given long.
     *
     * @param x value for extracting a number from it.
     *
     * @return eighth byte from given long.
     */
    byte getByteH(long x);

    /**
     * Returns byte on given index from given long.
     *
     * @param x     value for extracting a number from it.
     * @param index index of number in value. (from 0)
     *
     * @return byte on given index from given long.
     */
    byte getByte(long x, int index);

    /**
     * Returns first nibble from given long.
     *
     * @param x value for extracting a number from it.
     *
     * @return first nibble from given long.
     */
    byte getNibbleA(long x);

    /**
     * Returns second nibble from given long.
     *
     * @param x value for extracting a number from it.
     *
     * @return second nibble from given long.
     */
    byte getNibbleB(long x);

    /**
     * Returns third nibble from given long.
     *
     * @param x value for extracting a number from it.
     *
     * @return third nibble from given long.
     */
    byte getNibbleC(long x);

    /**
     * Returns fourth nibble from given long.
     *
     * @param x value for extracting a number from it.
     *
     * @return fourth nibble from given long.
     */
    byte getNibbleD(long x);

    /**
     * Returns fifth nibble from given long.
     *
     * @param x value for extracting a number from it.
     *
     * @return fifth nibble from given long.
     */
    byte getNibbleE(long x);

    /**
     * Returns sixth nibble from given long.
     *
     * @param x value for extracting a number from it.
     *
     * @return sixth nibble from given long.
     */
    byte getNibbleF(long x);

    /**
     * Returns seventh nibble from given long.
     *
     * @param x value for extracting a number from it.
     *
     * @return seventh nibble from given long.
     */
    byte getNibbleG(long x);

    /**
     * Returns eighth nibble from given long.
     *
     * @param x value for extracting a number from it.
     *
     * @return eighth nibble from given long.
     */
    byte getNibbleH(long x);

    /**
     * Returns ninth nibble from given long.
     *
     * @param x value for extracting a number from it.
     *
     * @return ninth nibble from given long.
     */
    byte getNibbleI(long x);

    /**
     * Returns tenth nibble from given long.
     *
     * @param x value for extracting a number from it.
     *
     * @return tenth nibble from given long.
     */
    byte getNibbleJ(long x);

    /**
     * Returns eleventh nibble from given long.
     *
     * @param x value for extracting a number from it.
     *
     * @return eleventh nibble from given long.
     */
    byte getNibbleK(long x);

    /**
     * Returns twelfth nibble from given long.
     *
     * @param x value for extracting a number from it.
     *
     * @return twelfth nibble from given long.
     */
    byte getNibbleL(long x);

    /**
     * Returns thirteenth nibble from given long.
     *
     * @param x value for extracting a number from it.
     *
     * @return thirteenth nibble from given long.
     */
    byte getNibbleM(long x);

    /**
     * Returns fourteenth nibble from given long.
     *
     * @param x value for extracting a number from it.
     *
     * @return fourteenth nibble from given long.
     */
    byte getNibbleN(long x);

    /**
     * Returns fifteenth nibble from given long.
     *
     * @param x value for extracting a number from it.
     *
     * @return fifteenth nibble from given long.
     */
    byte getNibbleO(long x);

    /**
     * Returns sixteenth nibble from given long.
     *
     * @param x value for extracting a number from it.
     *
     * @return sixteenth nibble from given long.
     */
    byte getNibbleP(long x);

    /**
     * Returns nibble on given index from given long.
     *
     * @param x     value for extracting a number from it.
     * @param index index of number in value. (from 0)
     *
     * @return nibble on given index from given long.
     */
    byte getNibble(long x, int index);
}
