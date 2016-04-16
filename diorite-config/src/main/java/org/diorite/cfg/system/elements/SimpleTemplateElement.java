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

package org.diorite.cfg.system.elements;

/**
 * Base class for template elements handlers, with simpler methods.
 *
 * @param <T> type of supported/handled element.
 */
public abstract class SimpleTemplateElement<T> extends TemplateElement<T>
{
    /**
     * Construct new template for given class, convert function and class type checking function.
     *
     * @param fieldType type of supported template element.
     */
    public SimpleTemplateElement(final Class<T> fieldType)
    {
        super(fieldType);
    }

    /**
     * Function used to convert other types to this type (may throw errors).
     * Simple convert method, used both by {@link #convertDefault0(Object, Class)} and {@link #convertObject0(Object)} to make creating element simpler.
     *
     * @param obj object to convert.
     *
     * @return converted object.
     *
     * @throws UnsupportedOperationException when method can't convert object.
     */
    protected abstract T simpleConvert(final Object obj) throws UnsupportedOperationException;

    @Override
    protected T convertObject0(final Object obj)
    {
        return this.convert0(obj);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected T convertDefault0(final Object obj, final Class<?> fieldType)
    {
        if (this.fieldType.isAssignableFrom(obj.getClass()))
        {
            return (T) obj;
        }
        return this.convert0(obj);
    }

    private T convert0(final Object obj)
    {
        final T converted;
        try
        {
            converted = this.simpleConvert(obj);
        } catch (final Exception e)
        {
            if (e instanceof UnsupportedOperationException)
            {
                throw e;
            }
            throw this.getException(obj, e);
        }
        if (converted == null)
        {
            throw this.getException(obj);
        }
        return converted;
    }
}
