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
import org.diorite.utils.math.IntRange;

/**
 * Template handler for all int range objects.
 *
 * @see IntRange
 */
public class IntRangeTemplateElement extends TemplateElement<IntRange>
{
    /**
     * Instance of template to direct-use.
     */
    public static final IntRangeTemplateElement INSTANCE = new IntRangeTemplateElement();

    /**
     * Construct new default template handler.
     */
    public IntRangeTemplateElement()
    {
        super(IntRange.class);
    }

    @Override
    protected boolean canBeConverted0(final Class<?> c)
    {
        return IntRange.class.isAssignableFrom(c) || String.class.isAssignableFrom(c);
    }

    @Override
    protected IntRange convertObject0(final Object obj) throws UnsupportedOperationException
    {
        final IntRange convert = this.convert(obj);
        if (convert != null)
        {
            return convert;
        }
        throw this.getException(obj);
    }

    private IntRange convert(final Object obj)
    {
        if (obj instanceof String)
        {
            final IntRange intRange = IntRange.valueOf((String) obj);
            if (intRange == null)
            {
                throw this.getException(obj);
            }
            return intRange;
        }
        if (obj instanceof byte[])
        {
            final byte[] array = (byte[]) obj;
            if ((array.length != 2) || (array[0] > array[1]))
            {
                throw this.getException(obj);
            }
            return new IntRange(array[0], array[1]);
        }
        if (obj instanceof short[])
        {
            final short[] array = (short[]) obj;
            if ((array.length != 2) || (array[0] > array[1]))
            {
                throw this.getException(obj);
            }
            return new IntRange(array[0], array[1]);
        }
        if (obj instanceof int[])
        {
            final int[] array = (int[]) obj;
            if ((array.length != 2) || (array[0] > array[1]))
            {
                throw this.getException(obj);
            }
            return new IntRange(array[0], array[1]);
        }
        return null;
    }

    @Override
    protected IntRange convertDefault0(final Object obj, final Class<?> fieldType)
    {
        if (obj instanceof IntRange)
        {
            return (IntRange) obj;
        }
        final IntRange convert = this.convert(obj);
        if (convert != null)
        {
            return convert;
        }
        throw this.getException(obj);
    }

    @Override
    public void appendValue(final Appendable writer, final CfgEntryData field, final Object source, final Object elementRaw, final int level, final ElementPlace elementPlace) throws IOException
    {
        final IntRange element = (elementRaw instanceof IntRange) ? ((IntRange) elementRaw) : this.validateType(elementRaw);
        StringTemplateElement.INSTANCE.appendValue(writer, field, source, StringTemplateElement.INSTANCE.validateType(element.getMin() + ", " + element.getMax()), level, elementPlace);
    }

}
