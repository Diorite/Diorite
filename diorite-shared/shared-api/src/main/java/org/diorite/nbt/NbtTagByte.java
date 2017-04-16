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

package org.diorite.nbt;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import java.io.IOException;

/**
 * NBT type for byte values.
 */
public final class NbtTagByte extends NbtAbstractTagNumber
{
    private static final long serialVersionUID = 0;
    /**
     * Value of nbt tag.
     */
    private byte value;

    /**
     * Construct new NbtTagByte without name and 0 as value.
     */
    public NbtTagByte()
    {
    }

    /**
     * Construct new NbtTagByte with given name and 0 as value.
     *
     * @param name
     *         name to be used.
     */
    public NbtTagByte(@Nullable String name)
    {
        super(name);
    }

    /**
     * Construct new NbtTagByte with given name and value.
     *
     * @param name
     *         name to be used.
     * @param value
     *         value to be used.
     */
    public NbtTagByte(@Nullable String name, byte value)
    {
        super(name);
        this.value = value;
    }

    /**
     * Construct new NbtTagByte with given name and value.
     *
     * @param name
     *         name to be used.
     * @param value
     *         value to be used.
     */
    public NbtTagByte(@Nullable String name, int value)
    {
        super(name);
        this.value = (byte) value;
    }

    /**
     * Clone constructor.
     *
     * @param nbtTagByte
     *         tag to be cloned.
     */
    private NbtTagByte(NbtTagByte nbtTagByte)
    {
        super(nbtTagByte);
        this.value = nbtTagByte.value;
    }

    /**
     * Returns value of this nbt tag.
     *
     * @return value of this nbt tag.
     */
    public byte getValue()
    {
        return this.value;
    }

    /**
     * Set value of this nbt tag.
     *
     * @param b
     *         value to set.
     */
    public void setValue(byte b)
    {
        this.value = b;
    }

    @Override
    public Byte getNumberValue()
    {
        return this.value;
    }

    @Override
    public void setNumberValue(Number i)
    {
        this.value = i.byteValue();
    }

    @Override
    @Nonnull
    public Byte getNBTValue()
    {
        return this.value;
    }

    @Override
    public NbtTagType getTagType()
    {
        return NbtTagType.BYTE;
    }

    @Override
    public void write(NbtOutputStream outputStream, boolean anonymous) throws IOException
    {
        super.write(outputStream, anonymous);
        outputStream.write(this.value);
    }

    @Override
    public void read(NbtInputStream inputStream, boolean anonymous, NbtLimiter limiter) throws IOException
    {
        super.read(inputStream, anonymous, limiter);
        limiter.incrementElementsCount(1);

        this.value = inputStream.readByte();
    }

    @SuppressWarnings("CloneDoesntCallSuperClone")
    @Override
    public NbtTagByte clone()
    {
        return new NbtTagByte(this);
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (! (o instanceof NbtTagByte))
        {
            return false;
        }
        if (! super.equals(o))
        {
            return false;
        }

        NbtTagByte that = (NbtTagByte) o;
        return this.value == that.value;
    }

    @Override
    public int hashCode()
    {
        int result = super.hashCode();
        result = (31 * result) + (int) this.value;
        return result;
    }

    @Override
    public String toString()
    {
        return this.value + "b";
    }
}