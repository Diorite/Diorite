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
import org.diorite.utils.math.FloatRange;

/**
 * Template handler for all float range objects.
 *
 * @see FloatRange
 */
public class FloatRangeTemplateElement extends TemplateElement<FloatRange>
{
    /**
     * Instance of template to direct-use.
     */
    public static final FloatRangeTemplateElement INSTANCE = new FloatRangeTemplateElement();

    /**
     * Construct new default template handler.
     */
    public FloatRangeTemplateElement()
    {
        super(FloatRange.class);
    }

    @Override
    protected boolean canBeConverted0(final Class<?> c)
    {
        return FloatRange.class.isAssignableFrom(c) || String.class.isAssignableFrom(c);
    }

    @Override
    protected FloatRange convertObject0(final Object obj) throws UnsupportedOperationException
    {
        final FloatRange convert = this.convert(obj);
        if (convert != null)
        {
            return convert;
        }
        throw this.getException(obj);
    }

    private FloatRange convert(final Object obj)
    {
        if (obj instanceof String)
        {
            final FloatRange floatRange = FloatRange.valueOf((String) obj);
            if (floatRange == null)
            {
                throw this.getException(obj);
            }
            return floatRange;
        }
        if (obj instanceof byte[])
        {
            final byte[] array = (byte[]) obj;
            if ((array.length != 2) || (array[0] > array[1]))
            {
                throw this.getException(obj);
            }
            return new FloatRange(array[0], array[1]);
        }
        if (obj instanceof short[])
        {
            final short[] array = (short[]) obj;
            if ((array.length != 2) || (array[0] > array[1]))
            {
                throw this.getException(obj);
            }
            return new FloatRange(array[0], array[1]);
        }
        if (obj instanceof int[])
        {
            final int[] array = (int[]) obj;
            if ((array.length != 2) || (array[0] > array[1]))
            {
                throw this.getException(obj);
            }
            return new FloatRange(array[0], array[1]);
        }
        if (obj instanceof float[])
        {
            final float[] array = (float[]) obj;
            if ((array.length != 2) || (array[0] > array[1]))
            {
                throw this.getException(obj);
            }
            return new FloatRange(array[0], array[1]);
        }
        if (obj instanceof double[])
        {
            final double[] array = (double[]) obj;
            if ((array.length != 2) || (array[0] > array[1]))
            {
                throw this.getException(obj);
            }
            return new FloatRange(array[0], array[1]);
        }
        if (obj instanceof long[])
        {
            final long[] array = (long[]) obj;
            if ((array.length != 2) || (array[0] > array[1]))
            {
                throw this.getException(obj);
            }
            return new FloatRange(array[0], array[1]);
        }
        return null;
    }

    @Override
    protected FloatRange convertDefault0(final Object obj, final Class<?> fieldType)
    {
        if (obj instanceof FloatRange)
        {
            return (FloatRange) obj;
        }
        final FloatRange convert = this.convert(obj);
        if (convert != null)
        {
            return convert;
        }
        throw this.getException(obj);
    }

    @Override
    public void appendValue(final Appendable writer, final CfgEntryData field, final Object source, final Object elementRaw, final int level, final ElementPlace elementPlace) throws IOException
    {
        final FloatRange element = (elementRaw instanceof FloatRange) ? ((FloatRange) elementRaw) : this.validateType(elementRaw);
        StringTemplateElement.INSTANCE.appendValue(writer, field, source, StringTemplateElement.INSTANCE.validateType(element.getMin() + ", " + element.getMax()), level, elementPlace);
    }

}
