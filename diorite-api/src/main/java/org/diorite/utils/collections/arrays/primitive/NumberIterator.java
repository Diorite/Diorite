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

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Represent Number iterator, this is wrapper for any {@link PrimitiveIterator}
 */
public class NumberIterator implements Iterator<Number>
{
    /**
     * Wrapped primitive iterator.
     */
    protected final PrimitiveIterator<?, ?> it;

    /**
     * Construct new NumberIterator for given primitive iterator.
     *
     * @param it iterator to be wrapped/used.
     */
    public NumberIterator(final PrimitiveIterator<?, ?> it)
    {
        this.it = it;
    }

    /**
     * Set value on current index to given value.
     *
     * @param number value to set.
     */
    public void setValue(final Number number)
    {
        this.it.setValue(number);
    }

    @Override
    public boolean hasNext()
    {
        return this.it.hasNext();
    }

    @Override
    public Number next()
    {
        final Object o = this.it.next();
        if (o instanceof Number)
        {
            return (Number) this.it.next();
        }
        if (o instanceof Character)
        {
            //noinspection UnnecessaryUnboxing
            return (int) ((Character) o).charValue();
        }
        if (o instanceof Boolean)
        {
            return (Boolean) o ? 1 : 0;
        }
        throw new AssertionError("Unknown primitive type.");
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("it", this.it).toString();
    }
}
