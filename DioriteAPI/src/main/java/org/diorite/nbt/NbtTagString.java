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

package org.diorite.nbt;

import java.io.IOException;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * NBT type for string values.
 */
public class NbtTagString extends NbtAbstractTag
{
    private static final long serialVersionUID = 0;

    /**
     * Value of nbt tag.
     */
    protected String value;

    /**
     * Construct new NbtTagString without name and 0 as value.
     */
    public NbtTagString()
    {
    }

    /**
     * Construct new NbtTagString with given name and 0 as value.
     *
     * @param name name to be used.
     */
    public NbtTagString(final String name)
    {
        super(name);
    }

    /**
     * Construct new NbtTagString with given name and value.
     *
     * @param name  name to be used.
     * @param value value to be used.
     */
    public NbtTagString(final String name, final String value)
    {
        super(name);
        this.value = value;
    }

    /**
     * Clone constructor.
     *
     * @param nbtTagString tag to be cloned.
     */
    protected NbtTagString(final NbtTagString nbtTagString)
    {
        super(nbtTagString);
        this.value = nbtTagString.value;
    }

    /**
     * Returns value of this nbt tag.
     *
     * @return value of this nbt tag.
     */
    public String getValue()
    {
        return this.value;
    }

    /**
     * Set value of this nbt tag.
     *
     * @param s value to set.
     */
    public void setValue(final String s)
    {
        this.value = s;
    }

    @Override
    public String getNBTValue()
    {
        return this.value;
    }

    @Override
    public NbtTagType getTagType()
    {
        return NbtTagType.STRING;
    }

    @Override
    public void write(final NbtOutputStream outputStream, final boolean anonymous) throws IOException
    {
        super.write(outputStream, anonymous);
        if (this.value == null)
        {
            outputStream.writeShort(- 1);
            return;
        }
        final byte[] outputBytes = this.value.getBytes(NbtTag.STRING_CHARSET);
        outputStream.writeShort(outputBytes.length);
        outputStream.write(outputBytes);
    }

    @Override
    public void read(final NbtInputStream inputStream, final boolean anonymous, final NbtLimiter limiter) throws IOException
    {
        super.read(inputStream, anonymous, limiter);
        limiter.incrementElementsCount(1);

        final int size = inputStream.readShort();
        if (size == - 1)
        {
            this.value = null;
            return;
        }
        final byte[] data = new byte[size];
        inputStream.readFully(data);
        this.value = new String(data, NbtTag.STRING_CHARSET).intern();
    }

    @SuppressWarnings("CloneDoesntCallSuperClone")
    @Override
    public NbtTagString clone()
    {
        return new NbtTagString(this);
    }

    @Override
    public boolean equals(final Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (! (o instanceof NbtTagString))
        {
            return false;
        }
        if (! super.equals(o))
        {
            return false;
        }

        final NbtTagString that = (NbtTagString) o;
        return ! ((this.value != null) ? ! this.value.equals(that.value) : (that.value != null));
    }

    @Override
    public int hashCode()
    {
        int result = super.hashCode();
        result = (31 * result) + ((this.value != null) ? this.value.hashCode() : 0);
        return result;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("value", this.value).toString();
    }
}