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

package org.diorite.utils.collections.arrays.fastutil;

import java.util.NoSuchElementException;

import it.unimi.dsi.fastutil.floats.FloatIterator;

/**
 * Represent {@link FloatIterator} for float array.
 */
public class FastUtilFloatIterator extends FastUtilPrimitiveIterator<float[]> implements FloatIterator
{
    /**
     * Construct new FloatIterator for given primitive array.
     *
     * @param primitiveArray array to be iterated.
     */
    public FastUtilFloatIterator(final float[] primitiveArray)
    {
        super(primitiveArray);
    }

    @Override
    public void setValue(final Number number)
    {
        this.primitiveArray[this.index - 1] = number.floatValue();
    }

    /**
     * Set value on current index to given value.
     *
     * @param number value to set.
     */
    public void setValue(final float number)
    {
        this.primitiveArray[this.index - 1] = number;
    }

    @Override
    public boolean hasNext()
    {
        return this.index < this.primitiveArray.length;
    }

    @Override
    public Float next()
    {
        return this.nextFloat();
    }

    @Override
    public float nextFloat()
    {
        if (! this.hasNext())
        {
            throw new NoSuchElementException("Index >= Length, Index: " + this.index + ", Length: " + this.primitiveArray.length);
        }
        return this.primitiveArray[this.index++];
    }

    @Override
    public int skip(final int n)
    {
        if ((this.index + n) < this.primitiveArray.length)
        {
            this.index += n;
            return n;
        }
        final int r = this.primitiveArray.length - this.index - 1;
        this.index = this.primitiveArray.length - 1;
        return r;
    }
}
