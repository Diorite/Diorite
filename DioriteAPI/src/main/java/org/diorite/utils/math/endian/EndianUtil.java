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

import java.nio.ByteOrder;

import org.diorite.utils.collections.arrays.NibbleArray;

/**
 * Represent class that can be used to pack one number type into another, like 4 bytes to int
 * using big endian or little endian byte order selected by implementation of this interface.
 */
public interface EndianUtil
{
    int  NIBBLE_MASK = 0xF;
    int  BYTE_MASK   = 0xFF;
    int  SHORT_MASK  = 0xFFFF;
    long INT_MASK    = 0xFFFFFFFFL;
    long LONG_MASK   = 0xFFFFFFFFFFFFFFFFL;
    int  NIBBLE_SIZE = 4;
    int  BYTE_SIZE   = 8;
    int  SHORT_SIZE  = 16;
    int  INT_SIZE    = 32;
    int  LONG_SIZE   = 64;

    ByteOrder getEndianType();

    byte toByte(byte a, byte b);

    short toShort(byte a, byte b);

    short toShort(byte a, byte b, byte c, byte d);

    int toInt(short a, short b);

    int toInt(byte a, byte b, byte c, byte d);

    int toInt(byte a, byte b, byte c, byte d, byte e, byte f, byte g, byte h);

    long toLong(int a, int b);

    long toLong(short a, short b, short c, short d);

    long toLong(byte a, byte b, byte c, byte d, byte e, byte f, byte g, byte h);

    long toLong(byte a, byte b, byte c, byte d, byte e, byte f, byte g, byte h, byte i, byte j, byte k, byte l, byte m, byte n, byte o, byte p);

    byte toByteFromNibbles(byte[] nibbles);

    byte toByteFromNibbles(byte[] nibbles, int start);

    byte toByte(NibbleArray nibbles);

    byte toByte(NibbleArray nibbles, int start);

    short toShortFromNibbles(byte[] nibbles);

    short toShortFromNibbles(byte[] nibbles, int start);

    short toShort(NibbleArray nibbles);

    short toShort(NibbleArray nibbles, int start);

    short toShort(byte[] bytes);

    short toShort(byte[] bytes, int start);

    int toIntFromNibbles(byte[] nibbles);

    int toIntFromNibbles(byte[] nibbles, int start);

    int toInt(NibbleArray nibbles);

    int toInt(NibbleArray nibbles, int start);

    int toInt(byte[] bytes);

    int toInt(byte[] bytes, int start);

    int toInt(short[] shorts);

    int toInt(short[] shorts, int start);

    long toLongFromNibbles(byte[] nibbles);

    long toLongFromNibbles(byte[] nibbles, int start);

    long toLong(NibbleArray nibbles);

    long toLong(NibbleArray nibbles, int start);

    long toLong(byte[] bytes);

    long toLong(byte[] bytes, int start);

    long toLong(short[] shorts);

    long toLong(short[] shorts, int start);

    long toLong(int[] ints);

    long toLong(int[] ints, int start);

    /**
     * From byte
     */
    byte getNibbleA(byte x);

    byte getNibbleB(byte x);

    byte getNibble(byte x, int index);

    /*
     * From short
     */

    byte getByteA(short x);

    byte getByteB(short x);

    byte getByte(short x, int index);

    byte getNibbleA(short x);

    byte getNibbleB(short x);

    byte getNibbleC(short x);

    byte getNibbleD(short x);

    byte getNibble(short x, int index);

    /*
     * From int
     */

    short getShortA(int x);

    short getShortB(int x);

    short getShort(int x, int index);

    byte getByteA(int x);

    byte getByteB(int x);

    byte getByteC(int x);

    byte getByteD(int x);

    byte getByte(int x, int index);

    byte getNibbleA(int x);

    byte getNibbleB(int x);

    byte getNibbleC(int x);

    byte getNibbleD(int x);

    byte getNibbleE(int x);

    byte getNibbleF(int x);

    byte getNibbleG(int x);

    byte getNibbleH(int x);

    byte getNibble(int x, int index);

    /*
     * From long
     */

    int getIntA(long x);

    int getIntB(long x);

    int getInt(long x, int index);

    short getShortA(long x);

    short getShortB(long x);

    short getShortC(long x);

    short getShortD(long x);

    short getShort(long x, int index);

    byte getByteA(long x);

    byte getByteB(long x);

    byte getByteC(long x);

    byte getByteD(long x);

    byte getByteE(long x);

    byte getByteF(long x);

    byte getByteG(long x);

    byte getByteH(long x);

    byte getByte(long x, int index);

    byte getNibbleA(long x);

    byte getNibbleB(long x);

    byte getNibbleC(long x);

    byte getNibbleD(long x);

    byte getNibbleE(long x);

    byte getNibbleF(long x);

    byte getNibbleG(long x);

    byte getNibbleH(long x);

    byte getNibbleI(long x);

    byte getNibbleJ(long x);

    byte getNibbleK(long x);

    byte getNibbleL(long x);

    byte getNibbleM(long x);

    byte getNibbleN(long x);

    byte getNibbleO(long x);

    byte getNibbleP(long x);

    byte getNibble(long x, int index);
}
