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
 * NBT type for float values.
 */
public class NbtTagFloat extends NbtAbstractTagNumber
{
    private static final long serialVersionUID = 0;

    /**
     * Value of nbt tag.
     */
    protected float value;

    /**
     * Construct new NbtTagDouble without name and 0 as value.
     */
    public NbtTagFloat()
    {
    }

    /**
     * Construct new NbtTagFloat with given name and 0 as value.
     *
     * @param name name to be used.
     */
    public NbtTagFloat(final String name)
    {
        super(name);
    }

    /**
     * Construct new NbtTagFloat with given name and value.
     *
     * @param name  name to be used.
     * @param value value to be used.
     */
    public NbtTagFloat(final String name, final float value)
    {
        super(name);
        this.value = value;
    }

    /**
     * Construct new NbtTagFloat with given name and value.
     *
     * @param name  name to be used.
     * @param value value to be used.
     */
    public NbtTagFloat(final String name, final double value)
    {
        super(name);
        this.value = (float) value;
    }

    /**
     * Clone constructor.
     *
     * @param nbtTagFloat tag to be cloned.
     */
    protected NbtTagFloat(final NbtTagFloat nbtTagFloat)
    {
        super(nbtTagFloat);
        this.value = nbtTagFloat.value;
    }

    /**
     * Returns value of this nbt tag.
     *
     * @return value of this nbt tag.
     */
    public float getValue()
    {
        return this.value;
    }

    /**
     * Set value of this nbt tag.
     *
     * @param f value to set.
     */
    public void setValue(final float f)
    {
        this.value = f;
    }

    @Override
    public Float getNumberValue()
    {
        return this.value;
    }

    @Override
    public void setNumberValue(final Number i)
    {
        this.value = i.floatValue();
    }

    @Override
    public Float getNBTValue()
    {
        return this.value;
    }

    @Override
    public NbtTagType getTagType()
    {
        return NbtTagType.FLOAT;
    }

    @Override
    public void write(final NbtOutputStream outputStream, final boolean anonymous) throws IOException
    {
        super.write(outputStream, anonymous);
        outputStream.writeFloat(this.value);
    }

    @Override
    public void read(final NbtInputStream inputStream, final boolean anonymous, final NbtLimiter limiter) throws IOException
    {
        super.read(inputStream, anonymous, limiter);
        limiter.incrementElementsCount(1);

        this.value = inputStream.readFloat();
    }

    @SuppressWarnings("CloneDoesntCallSuperClone")
    @Override
    public NbtTagFloat clone()
    {
        return new NbtTagFloat(this);
    }

    @Override
    public boolean equals(final Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (! (o instanceof NbtTagFloat))
        {
            return false;
        }
        if (! super.equals(o))
        {
            return false;
        }

        final NbtTagFloat that = (NbtTagFloat) o;
        return Float.compare(that.value, this.value) == 0;
    }

    @Override
    public int hashCode()
    {
        int result = super.hashCode();
        result = (31 * result) + ((this.value != + 0.0f) ? Float.floatToIntBits(this.value) : 0);
        return result;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("value", this.value).toString();
    }
}