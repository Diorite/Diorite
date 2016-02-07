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

package org.diorite.utils.collections.arrays.primitive;

import java.util.NoSuchElementException;

/**
 * Represent Integer iterator for int array.
 */
public class IntIterator extends PrimitiveIterator<Integer, int[]>
{
    /**
     * Construct new IntIterator for given primitive array.
     *
     * @param primitiveArray array to be iterated.
     */
    public IntIterator(final int[] primitiveArray)
    {
        super(primitiveArray);
    }

    @Override
    public void setValue(final Integer number)
    {
        this.primitiveArray[this.index - 1] = number;
    }

    @Override
    public void setValue(final Number number)
    {
        this.primitiveArray[this.index - 1] = number.intValue();
    }

    @Override
    public boolean hasNext()
    {
        return this.index < this.primitiveArray.length;
    }

    @Override
    public Integer next()
    {
        if (! this.hasNext())
        {
            throw new NoSuchElementException("Index >= Length, Index: " + this.index + ", Length: " + this.primitiveArray.length);
        }
        return this.primitiveArray[this.index++];
    }
}
