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

import java.nio.ByteOrder;

import org.diorite.commons.arrays.NibbleArray;

/**
 * EndianUtil implementation using {@link BigEndianUtils} for implementation.
 */
class BigEndianInst implements EndianUtil
{
    @Override
    public ByteOrder getEndianType()
    {
        return ByteOrder.BIG_ENDIAN;
    }

    @Override
    public byte toByte(byte a, byte b)
    {
        return BigEndianUtils.toByte(a, b);
    }

    @Override
    public short toShort(byte a, byte b)
    {
        return BigEndianUtils.toShort(a, b);
    }

    @Override
    public short toShort(byte a, byte b, byte c, byte d)
    {
        return BigEndianUtils.toShort(a, b, c, d);
    }

    @Override
    public int toInt(short a, short b)
    {
        return BigEndianUtils.toInt(a, b);
    }

    @Override
    public int toInt(byte a, byte b, byte c, byte d)
    {
        return BigEndianUtils.toInt(a, b, c, d);
    }

    @Override
    public int toInt(byte a, byte b, byte c, byte d, byte e, byte f, byte g, byte h)
    {
        return BigEndianUtils.toInt(a, b, c, d, e, f, g, h);
    }

    @Override
    public long toLong(int a, int b)
    {
        return BigEndianUtils.toLong(a, b);
    }

    @Override
    public long toLong(short a, short b, short c, short d)
    {
        return BigEndianUtils.toLong(a, b, c, d);
    }

    @Override
    public long toLong(byte a, byte b, byte c, byte d, byte e, byte f, byte g, byte h)
    {
        return BigEndianUtils.toLong(a, b, c, d, e, f, g, h);
    }

    @Override
    public long toLong(byte a, byte b, byte c, byte d, byte e, byte f, byte g, byte h, byte i, byte j, byte k, byte l, byte m, byte n, byte o, byte p)
    {
        return BigEndianUtils.toLong(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p);
    }

    @Override
    public byte toByteFromNibbles(byte[] nibbles)
    {
        return BigEndianUtils.toByteFromNibbles(nibbles, 0);
    }

    @Override
    public byte toByteFromNibbles(byte[] nibbles, int start)
    {
        return BigEndianUtils.toByteFromNibbles(nibbles, start);
    }

    @Override
    public byte toByte(NibbleArray nibbles)
    {
        return BigEndianUtils.toByte(nibbles, 0);
    }

    @Override
    public byte toByte(NibbleArray nibbles, int start)
    {
        return BigEndianUtils.toByte(nibbles, start);
    }

    @Override
    public short toShortFromNibbles(byte[] nibbles)
    {
        return BigEndianUtils.toShortFromNibbles(nibbles, 0);
    }

    @Override
    public short toShortFromNibbles(byte[] nibbles, int start)
    {
        return BigEndianUtils.toShortFromNibbles(nibbles, start);
    }

    @Override
    public short toShort(NibbleArray nibbles)
    {
        return BigEndianUtils.toShort(nibbles, 0);
    }

    @Override
    public short toShort(NibbleArray nibbles, int start)
    {
        return BigEndianUtils.toShort(nibbles, start);
    }

    @Override
    public short toShort(byte[] bytes)
    {
        return BigEndianUtils.toShort(bytes, 0);
    }

    @Override
    public short toShort(byte[] bytes, int start)
    {
        return BigEndianUtils.toShort(bytes, start);
    }

    @Override
    public int toIntFromNibbles(byte[] nibbles)
    {
        return BigEndianUtils.toIntFromNibbles(nibbles, 0);
    }

    @Override
    public int toIntFromNibbles(byte[] nibbles, int start)
    {
        return BigEndianUtils.toIntFromNibbles(nibbles, start);
    }

    @Override
    public int toInt(NibbleArray nibbles)
    {
        return BigEndianUtils.toInt(nibbles, 0);
    }

    @Override
    public int toInt(NibbleArray nibbles, int start)
    {
        return BigEndianUtils.toInt(nibbles, start);
    }

    @Override
    public int toInt(byte[] bytes)
    {
        return BigEndianUtils.toInt(bytes, 0);
    }

    @Override
    public int toInt(byte[] bytes, int start)
    {
        return BigEndianUtils.toInt(bytes, start);
    }

    @Override
    public int toInt(short[] shorts)
    {
        return BigEndianUtils.toInt(shorts, 0);
    }

    @Override
    public int toInt(short[] shorts, int start)
    {
        return BigEndianUtils.toInt(shorts, start);
    }

    @Override
    public long toLongFromNibbles(byte[] nibbles)
    {
        return BigEndianUtils.toLongFromNibbles(nibbles, 0);
    }

    @Override
    public long toLongFromNibbles(byte[] nibbles, int start)
    {
        return BigEndianUtils.toLongFromNibbles(nibbles, start);
    }

    @Override
    public long toLong(NibbleArray nibbles)
    {
        return BigEndianUtils.toLong(nibbles, 0);
    }

    @Override
    public long toLong(NibbleArray nibbles, int start)
    {
        return BigEndianUtils.toLong(nibbles, start);
    }

    @Override
    public long toLong(byte[] bytes)
    {
        return BigEndianUtils.toLong(bytes, 0);
    }

    @Override
    public long toLong(byte[] bytes, int start)
    {
        return BigEndianUtils.toLong(bytes, start);
    }

    @Override
    public long toLong(short[] shorts)
    {
        return BigEndianUtils.toLong(shorts, 0);
    }

    @Override
    public long toLong(short[] shorts, int start)
    {
        return BigEndianUtils.toLong(shorts, start);
    }

    @Override
    public long toLong(int[] ints)
    {
        return BigEndianUtils.toLong(ints, 0);
    }

    @Override
    public long toLong(int[] ints, int start)
    {
        return BigEndianUtils.toLong(ints, start);
    }

    @Override
    public byte getNibbleA(byte x)
    {
        return BigEndianUtils.getNibbleA(x);
    }

    @Override
    public byte getNibbleB(byte x)
    {
        return BigEndianUtils.getNibbleB(x);
    }

    @Override
    public byte getNibble(byte x, int index)
    {
        return BigEndianUtils.getNibble(x, index);
    }

    @Override
    public byte getByteA(short x)
    {
        return BigEndianUtils.getByteA(x);
    }

    @Override
    public byte getByteB(short x)
    {
        return BigEndianUtils.getByteB(x);
    }

    @Override
    public byte getByte(short x, int index)
    {
        return BigEndianUtils.getByte(x, index);
    }

    @Override
    public byte getNibbleA(short x)
    {
        return BigEndianUtils.getNibbleA(x);
    }

    @Override
    public byte getNibbleB(short x)
    {
        return BigEndianUtils.getNibbleB(x);
    }

    @Override
    public byte getNibbleC(short x)
    {
        return BigEndianUtils.getNibbleC(x);
    }

    @Override
    public byte getNibbleD(short x)
    {
        return BigEndianUtils.getNibbleD(x);
    }

    @Override
    public byte getNibble(short x, int index)
    {
        return BigEndianUtils.getNibble(x, index);
    }

    @Override
    public short getShortA(int x)
    {
        return BigEndianUtils.getShortA(x);
    }

    @Override
    public short getShortB(int x)
    {
        return BigEndianUtils.getShortB(x);
    }

    @Override
    public short getShort(int x, int index)
    {
        return BigEndianUtils.getShort(x, index);
    }

    @Override
    public byte getByteA(int x)
    {
        return BigEndianUtils.getByteA(x);
    }

    @Override
    public byte getByteB(int x)
    {
        return BigEndianUtils.getByteB(x);
    }

    @Override
    public byte getByteC(int x)
    {
        return BigEndianUtils.getByteC(x);
    }

    @Override
    public byte getByteD(int x)
    {
        return BigEndianUtils.getByteD(x);
    }

    @Override
    public byte getByte(int x, int index)
    {
        return BigEndianUtils.getByte(x, index);
    }

    @Override
    public byte getNibbleA(int x)
    {
        return BigEndianUtils.getNibbleA(x);
    }

    @Override
    public byte getNibbleB(int x)
    {
        return BigEndianUtils.getNibbleB(x);
    }

    @Override
    public byte getNibbleC(int x)
    {
        return BigEndianUtils.getNibbleC(x);
    }

    @Override
    public byte getNibbleD(int x)
    {
        return BigEndianUtils.getNibbleD(x);
    }

    @Override
    public byte getNibbleE(int x)
    {
        return BigEndianUtils.getNibbleE(x);
    }

    @Override
    public byte getNibbleF(int x)
    {
        return BigEndianUtils.getNibbleF(x);
    }

    @Override
    public byte getNibbleG(int x)
    {
        return BigEndianUtils.getNibbleG(x);
    }

    @Override
    public byte getNibbleH(int x)
    {
        return BigEndianUtils.getNibbleH(x);
    }

    @Override
    public byte getNibble(int x, int index)
    {
        return BigEndianUtils.getNibble(x, index);
    }

    @Override
    public int getIntA(long x)
    {
        return BigEndianUtils.getIntA(x);
    }

    @Override
    public int getIntB(long x)
    {
        return BigEndianUtils.getIntB(x);
    }

    @Override
    public int getInt(long x, int index)
    {
        return BigEndianUtils.getInt(x, index);
    }

    @Override
    public short getShortA(long x)
    {
        return BigEndianUtils.getShortA(x);
    }

    @Override
    public short getShortB(long x)
    {
        return BigEndianUtils.getShortB(x);
    }

    @Override
    public short getShortC(long x)
    {
        return BigEndianUtils.getShortC(x);
    }

    @Override
    public short getShortD(long x)
    {
        return BigEndianUtils.getShortD(x);
    }

    @Override
    public short getShort(long x, int index)
    {
        return BigEndianUtils.getShort(x, index);
    }

    @Override
    public byte getByteA(long x)
    {
        return BigEndianUtils.getByteA(x);
    }

    @Override
    public byte getByteB(long x)
    {
        return BigEndianUtils.getByteB(x);
    }

    @Override
    public byte getByteC(long x)
    {
        return BigEndianUtils.getByteC(x);
    }

    @Override
    public byte getByteD(long x)
    {
        return BigEndianUtils.getByteD(x);
    }

    @Override
    public byte getByteE(long x)
    {
        return BigEndianUtils.getByteE(x);
    }

    @Override
    public byte getByteF(long x)
    {
        return BigEndianUtils.getByteF(x);
    }

    @Override
    public byte getByteG(long x)
    {
        return BigEndianUtils.getByteG(x);
    }

    @Override
    public byte getByteH(long x)
    {
        return BigEndianUtils.getByteH(x);
    }

    @Override
    public byte getByte(long x, int index)
    {
        return BigEndianUtils.getByte(x, index);
    }

    @Override
    public byte getNibbleA(long x)
    {
        return BigEndianUtils.getNibbleA(x);
    }

    @Override
    public byte getNibbleB(long x)
    {
        return BigEndianUtils.getNibbleB(x);
    }

    @Override
    public byte getNibbleC(long x)
    {
        return BigEndianUtils.getNibbleC(x);
    }

    @Override
    public byte getNibbleD(long x)
    {
        return BigEndianUtils.getNibbleD(x);
    }

    @Override
    public byte getNibbleE(long x)
    {
        return BigEndianUtils.getNibbleE(x);
    }

    @Override
    public byte getNibbleF(long x)
    {
        return BigEndianUtils.getNibbleF(x);
    }

    @Override
    public byte getNibbleG(long x)
    {
        return BigEndianUtils.getNibbleG(x);
    }

    @Override
    public byte getNibbleH(long x)
    {
        return BigEndianUtils.getNibbleH(x);
    }

    @Override
    public byte getNibbleI(long x)
    {
        return BigEndianUtils.getNibbleI(x);
    }

    @Override
    public byte getNibbleJ(long x)
    {
        return BigEndianUtils.getNibbleJ(x);
    }

    @Override
    public byte getNibbleK(long x)
    {
        return BigEndianUtils.getNibbleK(x);
    }

    @Override
    public byte getNibbleL(long x)
    {
        return BigEndianUtils.getNibbleL(x);
    }

    @Override
    public byte getNibbleM(long x)
    {
        return BigEndianUtils.getNibbleM(x);
    }

    @Override
    public byte getNibbleN(long x)
    {
        return BigEndianUtils.getNibbleN(x);
    }

    @Override
    public byte getNibbleO(long x)
    {
        return BigEndianUtils.getNibbleO(x);
    }

    @Override
    public byte getNibbleP(long x)
    {
        return BigEndianUtils.getNibbleP(x);
    }

    @Override
    public byte getNibble(long x, int index)
    {
        return BigEndianUtils.getNibble(x, index);
    }
}
