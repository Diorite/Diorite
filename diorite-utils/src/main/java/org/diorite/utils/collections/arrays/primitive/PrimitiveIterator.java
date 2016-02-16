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

import java.util.Iterator;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Iterator for primitive arrays, it use primitive array but return wrapper types.
 */
public abstract class PrimitiveIterator<WRAPPER, ARRAY> implements Iterator<WRAPPER>
{
    /**
     * Primitive array, line int[] etc...
     */
    protected final ARRAY primitiveArray;
    /**
     * Current index of iterator.
     */
    protected       int   index;

    /**
     * Construct new PrimitiveIterator for given primitive array. <br>
     * Throws error if array is null or object isn't primitive array at all.
     *
     * @param primitiveArray array to be iterated.
     */
    protected PrimitiveIterator(final ARRAY primitiveArray)
    {
        Validate.notNull(primitiveArray, "Array can't be null!");
        Validate.isTrue(primitiveArray.getClass().isArray() && primitiveArray.getClass().getComponentType().isPrimitive(), "Argument must be an primitive array!");
        this.primitiveArray = primitiveArray;
    }

    /**
     * Set value on current index to given value.
     *
     * @param number value to set.
     */
    public abstract void setValue(WRAPPER number);

    /**
     * Set value on current index to given value.
     *
     * @param number value to set.
     */
    public abstract void setValue(Number number);

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("array", this.primitiveArray).append("index", this.index).toString();
    }
}
