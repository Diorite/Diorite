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
    public byte toByte(final byte a, final byte b)
    {
        return LittleEndianUtils.toByte(a, b);
    }

    @Override
    public short toShort(final byte a, final byte b)
    {
        return LittleEndianUtils.toShort(a, b);
    }

    @Override
    public short toShort(final byte a, final byte b, final byte c, final byte d)
    {
        return LittleEndianUtils.toShort(a, b, c, d);
    }

    @Override
    public int toInt(final short a, final short b)
    {
        return LittleEndianUtils.toInt(a, b);
    }

    @Override
    public int toInt(final byte a, final byte b, final byte c, final byte d)
    {
        return LittleEndianUtils.toInt(a, b, c, d);
    }

    @Override
    public int toInt(final byte a, final byte b, final byte c, final byte d, final byte e, final byte f, final byte g, final byte h)
    {
        return LittleEndianUtils.toInt(a, b, c, d, e, f, g, h);
    }

    @Override
    public long toLong(final int a, final int b)
    {
        return LittleEndianUtils.toLong(a, b);
    }

    @Override
    public long toLong(final short a, final short b, final short c, final short d)
    {
        return LittleEndianUtils.toLong(a, b, c, d);
    }

    @Override
    public long toLong(final byte a, final byte b, final byte c, final byte d, final byte e, final byte f, final byte g, final byte h)
    {
        return LittleEndianUtils.toLong(a, b, c, d, e, f, g, h);
    }

    @Override
    public long toLong(final byte a, final byte b, final byte c, final byte d, final byte e, final byte f, final byte g, final byte h, final byte i, final byte j, final byte k, final byte l, final byte m, final byte n, final byte o, final byte p)
    {
        return LittleEndianUtils.toLong(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p);
    }

    @Override
    public byte toByteFromNibbles(final byte[] nibbles)
    {
        return LittleEndianUtils.toByteFromNibbles(nibbles, 0);
    }

    @Override
    public byte toByteFromNibbles(final byte[] nibbles, final int start)
    {
        return LittleEndianUtils.toByteFromNibbles(nibbles, start);
    }

    @Override
    public byte toByte(final NibbleArray nibbles)
    {
        return LittleEndianUtils.toByte(nibbles, 0);
    }

    @Override
    public byte toByte(final NibbleArray nibbles, final int start)
    {
        return LittleEndianUtils.toByte(nibbles, start);
    }

    @Override
    public short toShortFromNibbles(final byte[] nibbles)
    {
        return LittleEndianUtils.toShortFromNibbles(nibbles, 0);
    }

    @Override
    public short toShortFromNibbles(final byte[] nibbles, final int start)
    {
        return LittleEndianUtils.toShortFromNibbles(nibbles, start);
    }

    @Override
    public short toShort(final NibbleArray nibbles)
    {
        return LittleEndianUtils.toShort(nibbles, 0);
    }

    @Override
    public short toShort(final NibbleArray nibbles, final int start)
    {
        return LittleEndianUtils.toShort(nibbles, start);
    }

    @Override
    public short toShort(final byte[] bytes)
    {
        return LittleEndianUtils.toShort(bytes, 0);
    }

    @Override
    public short toShort(final byte[] bytes, final int start)
    {
        return LittleEndianUtils.toShort(bytes, start);
    }

    @Override
    public int toIntFromNibbles(final byte[] nibbles)
    {
        return LittleEndianUtils.toIntFromNibbles(nibbles, 0);
    }

    @Override
    public int toIntFromNibbles(final byte[] nibbles, final int start)
    {
        return LittleEndianUtils.toIntFromNibbles(nibbles, start);
    }

    @Override
    public int toInt(final NibbleArray nibbles)
    {
        return LittleEndianUtils.toInt(nibbles, 0);
    }

    @Override
    public int toInt(final NibbleArray nibbles, final int start)
    {
        return LittleEndianUtils.toInt(nibbles, start);
    }

    @Override
    public int toInt(final byte[] bytes)
    {
        return LittleEndianUtils.toInt(bytes, 0);
    }

    @Override
    public int toInt(final byte[] bytes, final int start)
    {
        return LittleEndianUtils.toInt(bytes, start);
    }

    @Override
    public int toInt(final short[] shorts)
    {
        return LittleEndianUtils.toInt(shorts, 0);
    }

    @Override
    public int toInt(final short[] shorts, final int start)
    {
        return LittleEndianUtils.toInt(shorts, start);
    }

    @Override
    public long toLongFromNibbles(final byte[] nibbles)
    {
        return LittleEndianUtils.toLongFromNibbles(nibbles, 0);
    }

    @Override
    public long toLongFromNibbles(final byte[] nibbles, final int start)
    {
        return LittleEndianUtils.toLongFromNibbles(nibbles, start);
    }

    @Override
    public long toLong(final NibbleArray nibbles)
    {
        return LittleEndianUtils.toLong(nibbles, 0);
    }

    @Override
    public long toLong(final NibbleArray nibbles, final int start)
    {
        return LittleEndianUtils.toLong(nibbles, start);
    }

    @Override
    public long toLong(final byte[] bytes)
    {
        return LittleEndianUtils.toLong(bytes, 0);
    }

    @Override
    public long toLong(final byte[] bytes, final int start)
    {
        return LittleEndianUtils.toLong(bytes, start);
    }

    @Override
    public long toLong(final short[] shorts)
    {
        return LittleEndianUtils.toLong(shorts, 0);
    }

    @Override
    public long toLong(final short[] shorts, final int start)
    {
        return LittleEndianUtils.toLong(shorts, start);
    }

    @Override
    public long toLong(final int[] ints)
    {
        return LittleEndianUtils.toLong(ints, 0);
    }

    @Override
    public long toLong(final int[] ints, final int start)
    {
        return LittleEndianUtils.toLong(ints, start);
    }

    @Override
    public byte getNibbleA(final byte x)
    {
        return LittleEndianUtils.getNibbleA(x);
    }

    @Override
    public byte getNibbleB(final byte x)
    {
        return LittleEndianUtils.getNibbleB(x);
    }

    @Override
    public byte getNibble(final byte x, final int index)
    {
        return LittleEndianUtils.getNibble(x, index);
    }

    @Override
    public byte getByteA(final short x)
    {
        return LittleEndianUtils.getByteA(x);
    }

    @Override
    public byte getByteB(final short x)
    {
        return LittleEndianUtils.getByteB(x);
    }

    @Override
    public byte getByte(final short x, final int index)
    {
        return LittleEndianUtils.getByte(x, index);
    }

    @Override
    public byte getNibbleA(final short x)
    {
        return LittleEndianUtils.getNibbleA(x);
    }

    @Override
    public byte getNibbleB(final short x)
    {
        return LittleEndianUtils.getNibbleB(x);
    }

    @Override
    public byte getNibbleC(final short x)
    {
        return LittleEndianUtils.getNibbleC(x);
    }

    @Override
    public byte getNibbleD(final short x)
    {
        return LittleEndianUtils.getNibbleD(x);
    }

    @Override
    public byte getNibble(final short x, final int index)
    {
        return LittleEndianUtils.getNibble(x, index);
    }

    @Override
    public short getShortA(final int x)
    {
        return LittleEndianUtils.getShortA(x);
    }

    @Override
    public short getShortB(final int x)
    {
        return LittleEndianUtils.getShortB(x);
    }

    @Override
    public short getShort(final int x, final int index)
    {
        return LittleEndianUtils.getShort(x, index);
    }

    @Override
    public byte getByteA(final int x)
    {
        return LittleEndianUtils.getByteA(x);
    }

    @Override
    public byte getByteB(final int x)
    {
        return LittleEndianUtils.getByteB(x);
    }

    @Override
    public byte getByteC(final int x)
    {
        return LittleEndianUtils.getByteC(x);
    }

    @Override
    public byte getByteD(final int x)
    {
        return LittleEndianUtils.getByteD(x);
    }

    @Override
    public byte getByte(final int x, final int index)
    {
        return LittleEndianUtils.getByte(x, index);
    }

    @Override
    public byte getNibbleA(final int x)
    {
        return LittleEndianUtils.getNibbleA(x);
    }

    @Override
    public byte getNibbleB(final int x)
    {
        return LittleEndianUtils.getNibbleB(x);
    }

    @Override
    public byte getNibbleC(final int x)
    {
        return LittleEndianUtils.getNibbleC(x);
    }

    @Override
    public byte getNibbleD(final int x)
    {
        return LittleEndianUtils.getNibbleD(x);
    }

    @Override
    public byte getNibbleE(final int x)
    {
        return LittleEndianUtils.getNibbleE(x);
    }

    @Override
    public byte getNibbleF(final int x)
    {
        return LittleEndianUtils.getNibbleF(x);
    }

    @Override
    public byte getNibbleG(final int x)
    {
        return LittleEndianUtils.getNibbleG(x);
    }

    @Override
    public byte getNibbleH(final int x)
    {
        return LittleEndianUtils.getNibbleH(x);
    }

    @Override
    public byte getNibble(final int x, final int index)
    {
        return LittleEndianUtils.getNibble(x, index);
    }

    @Override
    public int getIntA(final long x)
    {
        return LittleEndianUtils.getIntA(x);
    }

    @Override
    public int getIntB(final long x)
    {
        return LittleEndianUtils.getIntB(x);
    }

    @Override
    public int getInt(final long x, final int index)
    {
        return LittleEndianUtils.getInt(x, index);
    }

    @Override
    public short getShortA(final long x)
    {
        return LittleEndianUtils.getShortA(x);
    }

    @Override
    public short getShortB(final long x)
    {
        return LittleEndianUtils.getShortB(x);
    }

    @Override
    public short getShortC(final long x)
    {
        return LittleEndianUtils.getShortC(x);
    }

    @Override
    public short getShortD(final long x)
    {
        return LittleEndianUtils.getShortD(x);
    }

    @Override
    public short getShort(final long x, final int index)
    {
        return LittleEndianUtils.getShort(x, index);
    }

    @Override
    public byte getByteA(final long x)
    {
        return LittleEndianUtils.getByteA(x);
    }

    @Override
    public byte getByteB(final long x)
    {
        return LittleEndianUtils.getByteB(x);
    }

    @Override
    public byte getByteC(final long x)
    {
        return LittleEndianUtils.getByteC(x);
    }

    @Override
    public byte getByteD(final long x)
    {
        return LittleEndianUtils.getByteD(x);
    }

    @Override
    public byte getByteE(final long x)
    {
        return LittleEndianUtils.getByteE(x);
    }

    @Override
    public byte getByteF(final long x)
    {
        return LittleEndianUtils.getByteF(x);
    }

    @Override
    public byte getByteG(final long x)
    {
        return LittleEndianUtils.getByteG(x);
    }

    @Override
    public byte getByteH(final long x)
    {
        return LittleEndianUtils.getByteH(x);
    }

    @Override
    public byte getByte(final long x, final int index)
    {
        return LittleEndianUtils.getByte(x, index);
    }

    @Override
    public byte getNibbleA(final long x)
    {
        return LittleEndianUtils.getNibbleA(x);
    }

    @Override
    public byte getNibbleB(final long x)
    {
        return LittleEndianUtils.getNibbleB(x);
    }

    @Override
    public byte getNibbleC(final long x)
    {
        return LittleEndianUtils.getNibbleC(x);
    }

    @Override
    public byte getNibbleD(final long x)
    {
        return LittleEndianUtils.getNibbleD(x);
    }

    @Override
    public byte getNibbleE(final long x)
    {
        return LittleEndianUtils.getNibbleE(x);
    }

    @Override
    public byte getNibbleF(final long x)
    {
        return LittleEndianUtils.getNibbleF(x);
    }

    @Override
    public byte getNibbleG(final long x)
    {
        return LittleEndianUtils.getNibbleG(x);
    }

    @Override
    public byte getNibbleH(final long x)
    {
        return LittleEndianUtils.getNibbleH(x);
    }

    @Override
    public byte getNibbleI(final long x)
    {
        return LittleEndianUtils.getNibbleI(x);
    }

    @Override
    public byte getNibbleJ(final long x)
    {
        return LittleEndianUtils.getNibbleJ(x);
    }

    @Override
    public byte getNibbleK(final long x)
    {
        return LittleEndianUtils.getNibbleK(x);
    }

    @Override
    public byte getNibbleL(final long x)
    {
        return LittleEndianUtils.getNibbleL(x);
    }

    @Override
    public byte getNibbleM(final long x)
    {
        return LittleEndianUtils.getNibbleM(x);
    }

    @Override
    public byte getNibbleN(final long x)
    {
        return LittleEndianUtils.getNibbleN(x);
    }

    @Override
    public byte getNibbleO(final long x)
    {
        return LittleEndianUtils.getNibbleO(x);
    }

    @Override
    public byte getNibbleP(final long x)
    {
        return LittleEndianUtils.getNibbleP(x);
    }

    @Override
    public byte getNibble(final long x, final int index)
    {
        return LittleEndianUtils.getNibble(x, index);
    }
}
