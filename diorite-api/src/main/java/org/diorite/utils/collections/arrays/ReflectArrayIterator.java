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

package org.diorite.utils.collections.arrays;

import java.lang.reflect.Array;
import java.util.Iterator;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Iterator implementation for array based on reflections, perfect if you don't know type of array and it can be a primitive type.
 *
 * @see Array
 */
public class ReflectArrayIterator implements Iterator<Object>, Iterable<Object>
{
    private final Object array;
    private int currentIndex = 0;

    /**
     * Construct new iterator for given array object, if object isn't array {@link IllegalArgumentException} will be thrown.
     *
     * @param array array to use in iterator.
     */
    public ReflectArrayIterator(final Object array)
    {
        if (! array.getClass().isArray())
        {
            throw new IllegalArgumentException("not an array");
        }
        else
        {
            this.array = array;
        }
    }

    @Override
    public boolean hasNext()
    {
        return this.currentIndex < Array.getLength(this.array);
    }

    @SuppressWarnings("IteratorNextCanNotThrowNoSuchElementException")
    @Override
    public Object next()
    {
        return Array.get(this.array, this.currentIndex++);
    }

    @Override
    public void remove()
    {
        throw new UnsupportedOperationException("cannot remove items from an array");
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("array", this.array).append("currentIndex", this.currentIndex).toString();
    }

    @Override
    public Iterator<Object> iterator()
    {
        return new ReflectArrayIterator(this.array);
    }
}