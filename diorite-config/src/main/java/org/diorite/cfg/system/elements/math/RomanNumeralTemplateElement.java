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
import org.diorite.utils.math.RomanNumeral;

/**
 * Template handler for all roman numbers objects.
 *
 * @see RomanNumeral
 */
public class RomanNumeralTemplateElement extends TemplateElement<RomanNumeral>
{
    /**
     * Instance of template to direct-use.
     */
    public static final RomanNumeralTemplateElement INSTANCE = new RomanNumeralTemplateElement();

    /**
     * Construct new default template handler.
     */
    public RomanNumeralTemplateElement()
    {
        super(RomanNumeral.class);
    }

    @Override
    protected boolean canBeConverted0(final Class<?> c)
    {
        return RomanNumeral.class.isAssignableFrom(c) || String.class.isAssignableFrom(c);
    }

    @Override
    protected RomanNumeral convertObject0(final Object obj) throws UnsupportedOperationException
    {
        if (obj instanceof String)
        {
            try
            {
                return new RomanNumeral((String) obj, false);
            } catch (final Exception e)
            {
                throw this.getException(obj, e);
            }
        }
        if (obj instanceof Number)
        {
            final Number n = (Number) obj;
            return new RomanNumeral(n.intValue(), false);
        }
        throw this.getException(obj);
    }

    @Override
    protected RomanNumeral convertDefault0(final Object obj, final Class<?> fieldType)
    {
        if (obj instanceof RomanNumeral)
        {
            return (RomanNumeral) obj;
        }
        if (obj instanceof String)
        {
            try
            {
                return new RomanNumeral((String) obj, false);
            } catch (final Exception e)
            {
                throw this.getException(obj, e);
            }
        }
        if (obj instanceof Number)
        {
            final Number n = (Number) obj;
            return new RomanNumeral(n.intValue(), false);
        }
        throw this.getException(obj);
    }

    @Override
    public void appendValue(final Appendable writer, final CfgEntryData field, final Object source, final Object elementRaw, final int level, final ElementPlace elementPlace) throws IOException
    {
        final RomanNumeral element = (elementRaw instanceof RomanNumeral) ? ((RomanNumeral) elementRaw) : this.validateType(elementRaw);
        StringTemplateElement.INSTANCE.appendValue(writer, field, source, StringTemplateElement.INSTANCE.validateType(element.toString()), level, elementPlace);
    }
}
