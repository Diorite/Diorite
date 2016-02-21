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

package org.diorite.cfg.system.elements.primitives;

/**
 * Template used by longs.
 */
public class LongTemplateElement extends PrimitiveTemplateElement<Long>
{
    /**
     * Instance of template to direct-use.
     */
    public static final LongTemplateElement INSTANCE = new LongTemplateElement();

    /**
     * Construct new long template
     */
    public LongTemplateElement()
    {
        super(long.class);
    }

    @Override
    protected boolean canBeConverted0(final Class<?> c)
    {
        return Number.class.isAssignableFrom(c);
    }

    @Override
    protected Long convertObject0(final Object obj) throws UnsupportedOperationException
    {
        if (obj instanceof Number)
        {
            return ((Number) obj).longValue();
        }
        throw this.getException(obj);
    }

    @Override
    protected Long convertDefault0(final Object obj, final Class<?> fieldType)
    {
        if (obj instanceof Number)
        {
            return ((Number) obj).longValue();
        }
        throw this.getException(obj);
    }
}
