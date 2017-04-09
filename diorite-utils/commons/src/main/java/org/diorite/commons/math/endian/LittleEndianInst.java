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
 * EndianUtil implementation using {@link LittleEndianUtils} for implementation.
 */
class LittleEndianInst implements EndianUtil
{
    @Override
    public ByteOrder getEndianType()
    {
        return ByteOrder.LITTLE_ENDIAN;
    }

    @Override
    public byte toByte(byte a, byte b)
    {
        return LittleEndianUtils.toByte(a, b);
    }

    @Override
    public short toShort(byte a, byte b)
    {
        return LittleEndianUtils.toShort(a, b);
    }

    @Override
    public short toShort(byte a, byte b, byte c, byte d)
    {
        return LittleEndianUtils.toShort(a, b, c, d);
    }

    @Override
    public int toInt(short a, short b)
    {
        return LittleEndianUtils.toInt(a, b);
    }

    @Override
    public int toInt(byte a, byte b, byte c, byte d)
    {
        return LittleEndianUtils.toInt(a, b, c, d);
    }

    @Override
    public int toInt(byte a, byte b, byte c, byte d, byte e, byte f, byte g, byte h)
    {
        return LittleEndianUtils.toInt(a, b, c, d, e, f, g, h);
    }

    @Override
    public long toLong(int a, int b)
    {
        return LittleEndianUtils.toLong(a, b);
    }

    @Override
    public long toLong(short a, short b, short c, short d)
    {
        return LittleEndianUtils.toLong(a, b, c, d);
    }

    @Override
    public long toLong(byte a, byte b, byte c, byte d, byte e, byte f, byte g, byte h)
    {
        return LittleEndianUtils.toLong(a, b, c, d, e, f, g, h);
    }

    @Override
    public long toLong(byte a, byte b, byte c, byte d, byte e, byte f, byte g, byte h, byte i, byte j, byte k, byte l, byte m, byte n, byte o, byte p)
    {
        return LittleEndianUtils.toLong(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p);
    }

    @Override
    public byte toByteFromNibbles(byte[] nibbles)
    {
        return LittleEndianUtils.toByteFromNibbles(nibbles, 0);
    }

    @Override
    public byte toByteFromNibbles(byte[] nibbles, int start)
    {
        return LittleEndianUtils.toByteFromNibbles(nibbles, start);
    }

    @Override
    public byte toByte(NibbleArray nibbles)
    {
        return LittleEndianUtils.toByte(nibbles, 0);
    }

    @Override
    public byte toByte(NibbleArray nibbles, int start)
    {
        return LittleEndianUtils.toByte(nibbles, start);
    }

    @Override
    public short toShortFromNibbles(byte[] nibbles)
    {
        return LittleEndianUtils.toShortFromNibbles(nibbles, 0);
    }

    @Override
    public short toShortFromNibbles(byte[] nibbles, int start)
    {
        return LittleEndianUtils.toShortFromNibbles(nibbles, start);
    }

    @Override
    public short toShort(NibbleArray nibbles)
    {
        return LittleEndianUtils.toShort(nibbles, 0);
    }

    @Override
    public short toShort(NibbleArray nibbles, int start)
    {
        return LittleEndianUtils.toShort(nibbles, start);
    }

    @Override
    public short toShort(byte[] bytes)
    {
        return LittleEndianUtils.toShort(bytes, 0);
    }

    @Override
    public short toShort(byte[] bytes, int start)
    {
        return LittleEndianUtils.toShort(bytes, start);
    }

    @Override
    public int toIntFromNibbles(byte[] nibbles)
    {
        return LittleEndianUtils.toIntFromNibbles(nibbles, 0);
    }

    @Override
    public int toIntFromNibbles(byte[] nibbles, int start)
    {
        return LittleEndianUtils.toIntFromNibbles(nibbles, start);
    }

    @Override
    public int toInt(NibbleArray nibbles)
    {
        return LittleEndianUtils.toInt(nibbles, 0);
    }

    @Override
    public int toInt(NibbleArray nibbles, int start)
    {
        return LittleEndianUtils.toInt(nibbles, start);
    }

    @Override
    public int toInt(byte[] bytes)
    {
        return LittleEndianUtils.toInt(bytes, 0);
    }

    @Override
    public int toInt(byte[] bytes, int start)
    {
        return LittleEndianUtils.toInt(bytes, start);
    }

    @Override
    public int toInt(short[] shorts)
    {
        return LittleEndianUtils.toInt(shorts, 0);
    }

    @Override
    public int toInt(short[] shorts, int start)
    {
        return LittleEndianUtils.toInt(shorts, start);
    }

    @Override
    public long toLongFromNibbles(byte[] nibbles)
    {
        return LittleEndianUtils.toLongFromNibbles(nibbles, 0);
    }

    @Override
    public long toLongFromNibbles(byte[] nibbles, int start)
    {
        return LittleEndianUtils.toLongFromNibbles(nibbles, start);
    }

    @Override
    public long toLong(NibbleArray nibbles)
    {
        return LittleEndianUtils.toLong(nibbles, 0);
    }

    @Override
    public long toLong(NibbleArray nibbles, int start)
    {
        return LittleEndianUtils.toLong(nibbles, start);
    }

    @Override
    public long toLong(byte[] bytes)
    {
        return LittleEndianUtils.toLong(bytes, 0);
    }

    @Override
    public long toLong(byte[] bytes, int start)
    {
        return LittleEndianUtils.toLong(bytes, start);
    }

    @Override
    public long toLong(short[] shorts)
    {
        return LittleEndianUtils.toLong(shorts, 0);
    }

    @Override
    public long toLong(short[] shorts, int start)
    {
        return LittleEndianUtils.toLong(shorts, start);
    }

    @Override
    public long toLong(int[] ints)
    {
        return LittleEndianUtils.toLong(ints, 0);
    }

    @Override
    public long toLong(int[] ints, int start)
    {
        return LittleEndianUtils.toLong(ints, start);
    }

    @Override
    public byte getNibbleA(byte x)
    {
        return LittleEndianUtils.getNibbleA(x);
    }

    @Override
    public byte getNibbleB(byte x)
    {
        return LittleEndianUtils.getNibbleB(x);
    }

    @Override
    public byte getNibble(byte x, int index)
    {
        return LittleEndianUtils.getNibble(x, index);
    }

    @Override
    public byte getByteA(short x)
    {
        return LittleEndianUtils.getByteA(x);
    }

    @Override
    public byte getByteB(short x)
    {
        return LittleEndianUtils.getByteB(x);
    }

    @Override
    public byte getByte(short x, int index)
    {
        return LittleEndianUtils.getByte(x, index);
    }

    @Override
    public byte getNibbleA(short x)
    {
        return LittleEndianUtils.getNibbleA(x);
    }

    @Override
    public byte getNibbleB(short x)
    {
        return LittleEndianUtils.getNibbleB(x);
    }

    @Override
    public byte getNibbleC(short x)
    {
        return LittleEndianUtils.getNibbleC(x);
    }

    @Override
    public byte getNibbleD(short x)
    {
        return LittleEndianUtils.getNibbleD(x);
    }

    @Override
    public byte getNibble(short x, int index)
    {
        return LittleEndianUtils.getNibble(x, index);
    }

    @Override
    public short getShortA(int x)
    {
        return LittleEndianUtils.getShortA(x);
    }

    @Override
    public short getShortB(int x)
    {
        return LittleEndianUtils.getShortB(x);
    }

    @Override
    public short getShort(int x, int index)
    {
        return LittleEndianUtils.getShort(x, index);
    }

    @Override
    public byte getByteA(int x)
    {
        return LittleEndianUtils.getByteA(x);
    }

    @Override
    public byte getByteB(int x)
    {
        return LittleEndianUtils.getByteB(x);
    }

    @Override
    public byte getByteC(int x)
    {
        return LittleEndianUtils.getByteC(x);
    }

    @Override
    public byte getByteD(int x)
    {
        return LittleEndianUtils.getByteD(x);
    }

    @Override
    public byte getByte(int x, int index)
    {
        return LittleEndianUtils.getByte(x, index);
    }

    @Override
    public byte getNibbleA(int x)
    {
        return LittleEndianUtils.getNibbleA(x);
    }

    @Override
    public byte getNibbleB(int x)
    {
        return LittleEndianUtils.getNibbleB(x);
    }

    @Override
    public byte getNibbleC(int x)
    {
        return LittleEndianUtils.getNibbleC(x);
    }

    @Override
    public byte getNibbleD(int x)
    {
        return LittleEndianUtils.getNibbleD(x);
    }

    @Override
    public byte getNibbleE(int x)
    {
        return LittleEndianUtils.getNibbleE(x);
    }

    @Override
    public byte getNibbleF(int x)
    {
        return LittleEndianUtils.getNibbleF(x);
    }

    @Override
    public byte getNibbleG(int x)
    {
        return LittleEndianUtils.getNibbleG(x);
    }

    @Override
    public byte getNibbleH(int x)
    {
        return LittleEndianUtils.getNibbleH(x);
    }

    @Override
    public byte getNibble(int x, int index)
    {
        return LittleEndianUtils.getNibble(x, index);
    }

    @Override
    public int getIntA(long x)
    {
        return LittleEndianUtils.getIntA(x);
    }

    @Override
    public int getIntB(long x)
    {
        return LittleEndianUtils.getIntB(x);
    }

    @Override
    public int getInt(long x, int index)
    {
        return LittleEndianUtils.getInt(x, index);
    }

    @Override
    public short getShortA(long x)
    {
        return LittleEndianUtils.getShortA(x);
    }

    @Override
    public short getShortB(long x)
    {
        return LittleEndianUtils.getShortB(x);
    }

    @Override
    public short getShortC(long x)
    {
        return LittleEndianUtils.getShortC(x);
    }

    @Override
    public short getShortD(long x)
    {
        return LittleEndianUtils.getShortD(x);
    }

    @Override
    public short getShort(long x, int index)
    {
        return LittleEndianUtils.getShort(x, index);
    }

    @Override
    public byte getByteA(long x)
    {
        return LittleEndianUtils.getByteA(x);
    }

    @Override
    public byte getByteB(long x)
    {
        return LittleEndianUtils.getByteB(x);
    }

    @Override
    public byte getByteC(long x)
    {
        return LittleEndianUtils.getByteC(x);
    }

    @Override
    public byte getByteD(long x)
    {
        return LittleEndianUtils.getByteD(x);
    }

    @Override
    public byte getByteE(long x)
    {
        return LittleEndianUtils.getByteE(x);
    }

    @Override
    public byte getByteF(long x)
    {
        return LittleEndianUtils.getByteF(x);
    }

    @Override
    public byte getByteG(long x)
    {
        return LittleEndianUtils.getByteG(x);
    }

    @Override
    public byte getByteH(long x)
    {
        return LittleEndianUtils.getByteH(x);
    }

    @Override
    public byte getByte(long x, int index)
    {
        return LittleEndianUtils.getByte(x, index);
    }

    @Override
    public byte getNibbleA(long x)
    {
        return LittleEndianUtils.getNibbleA(x);
    }

    @Override
    public byte getNibbleB(long x)
    {
        return LittleEndianUtils.getNibbleB(x);
    }

    @Override
    public byte getNibbleC(long x)
    {
        return LittleEndianUtils.getNibbleC(x);
    }

    @Override
    public byte getNibbleD(long x)
    {
        return LittleEndianUtils.getNibbleD(x);
    }

    @Override
    public byte getNibbleE(long x)
    {
        return LittleEndianUtils.getNibbleE(x);
    }

    @Override
    public byte getNibbleF(long x)
    {
        return LittleEndianUtils.getNibbleF(x);
    }

    @Override
    public byte getNibbleG(long x)
    {
        return LittleEndianUtils.getNibbleG(x);
    }

    @Override
    public byte getNibbleH(long x)
    {
        return LittleEndianUtils.getNibbleH(x);
    }

    @Override
    public byte getNibbleI(long x)
    {
        return LittleEndianUtils.getNibbleI(x);
    }

    @Override
    public byte getNibbleJ(long x)
    {
        return LittleEndianUtils.getNibbleJ(x);
    }

    @Override
    public byte getNibbleK(long x)
    {
        return LittleEndianUtils.getNibbleK(x);
    }

    @Override
    public byte getNibbleL(long x)
    {
        return LittleEndianUtils.getNibbleL(x);
    }

    @Override
    public byte getNibbleM(long x)
    {
        return LittleEndianUtils.getNibbleM(x);
    }

    @Override
    public byte getNibbleN(long x)
    {
        return LittleEndianUtils.getNibbleN(x);
    }

    @Override
    public byte getNibbleO(long x)
    {
        return LittleEndianUtils.getNibbleO(x);
    }

    @Override
    public byte getNibbleP(long x)
    {
        return LittleEndianUtils.getNibbleP(x);
    }

    @Override
    public byte getNibble(long x, int index)
    {
        return LittleEndianUtils.getNibble(x, index);
    }
}
