/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 Diorite (by Bartłomiej Mazur (aka GotoFinal))
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
 * Template used by doubles.
 */
public class DoubleTemplateElement extends SimpleTemplateElement<Double>
{
    /**
     * Instance of template to direct-use.
     */
    public static final DoubleTemplateElement INSTANCE = new DoubleTemplateElement();

    /**
     * Construct new double template
     */
    public DoubleTemplateElement()
    {
        super(double.class, obj -> {
            if (obj instanceof Number)
            {
                return ((Number) obj).doubleValue();
            }
            throw new UnsupportedOperationException("Can't convert object (" + obj.getClass().getName() + ") to Double: " + obj);
        }, Number.class::isAssignableFrom);
    }

    @Override
    protected Double convertDefault0(final Object def, final Class<?> fieldType)
    {
        if (def instanceof Number)
        {
            return ((Number) def).doubleValue();
        }
        throw new UnsupportedOperationException("Can't convert default value (" + def.getClass().getName() + "): " + def);
    }
}
