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

package org.diorite.cfg.system.elements.math;

import java.io.IOException;

import org.diorite.cfg.system.CfgEntryData;
import org.diorite.cfg.system.elements.StringTemplateElement;
import org.diorite.cfg.system.elements.TemplateElement;
import org.diorite.utils.math.LongRange;

/**
 * Template handler for all long range objects.
 *
 * @see LongRange
 */
public class LongRangeTemplateElement extends TemplateElement<LongRange>
{
    /**
     * Instance of template to direct-use.
     */
    public static final LongRangeTemplateElement INSTANCE = new LongRangeTemplateElement();

    /**
     * Construct new default template handler.
     */
    public LongRangeTemplateElement()
    {
        super(LongRange.class);
    }

    @Override
    protected boolean canBeConverted0(final Class<?> c)
    {
        return LongRange.class.isAssignableFrom(c) || String.class.isAssignableFrom(c);
    }

    @Override
    protected LongRange convertObject0(final Object obj) throws UnsupportedOperationException
    {
        final LongRange convert = this.convert(obj);
        if (convert != null)
        {
            return convert;
        }
        throw this.getException(obj);
    }

    private LongRange convert(final Object obj)
    {
        if (obj instanceof String)
        {
            final LongRange longRange = LongRange.valueOf((String) obj);
            if (longRange == null)
            {
                throw this.getException(obj);
            }
            return longRange;
        }
        if (obj instanceof byte[])
        {
            final byte[] array = (byte[]) obj;
            if ((array.length != 2) || (array[0] > array[1]))
            {
                throw this.getException(obj);
            }
            return new LongRange(array[0], array[1]);
        }
        if (obj instanceof short[])
        {
            final short[] array = (short[]) obj;
            if ((array.length != 2) || (array[0] > array[1]))
            {
                throw this.getException(obj);
            }
            return new LongRange(array[0], array[1]);
        }
        if (obj instanceof int[])
        {
            final int[] array = (int[]) obj;
            if ((array.length != 2) || (array[0] > array[1]))
            {
                throw this.getException(obj);
            }
            return new LongRange(array[0], array[1]);
        }
        if (obj instanceof long[])
        {
            final long[] array = (long[]) obj;
            if ((array.length != 2) || (array[0] > array[1]))
            {
                throw this.getException(obj);
            }
            return new LongRange(array[0], array[1]);
        }
        return null;
    }

    @Override
    protected LongRange convertDefault0(final Object obj, final Class<?> fieldType)
    {
        if (obj instanceof LongRange)
        {
            return (LongRange) obj;
        }
        final LongRange convert = this.convert(obj);
        if (convert != null)
        {
            return convert;
        }
        throw this.getException(obj);
    }

    @Override
    public void appendValue(final Appendable writer, final CfgEntryData field, final Object source, final Object elementRaw, final int level, final ElementPlace elementPlace) throws IOException
    {
        final LongRange element = (elementRaw instanceof LongRange) ? ((LongRange) elementRaw) : this.validateType(elementRaw);
        StringTemplateElement.INSTANCE.appendValue(writer, field, source, StringTemplateElement.INSTANCE.validateType(element.getMin() + ", " + element.getMax()), level, elementPlace);
    }

}
