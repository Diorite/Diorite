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

import java.io.IOException;

import org.diorite.cfg.system.CfgEntryData;

/**
 * Base class for template elements handlers, with simpler methods and made for String representation of objects.
 *
 * @param <T> type of supported/handled element.
 */
public abstract class SimpleStringTemplateElement<T> extends SimpleTemplateElement<T>
{
    /**
     * Construct new template for given class, convert function and class type checking function.
     *
     * @param fieldType type of supported template element.
     */
    public SimpleStringTemplateElement(final Class<T> fieldType)
    {
        super(fieldType);
    }

    /**
     * Convert given object to string that will be saved in configuration file. <br>
     * String returned by this method must produce this same object via {@link #simpleConvert(Object)}.
     *
     * @param object object to be coverted.
     *
     * @return ready to save string.
     */
    protected abstract String convertToString(T object);

    @SuppressWarnings("unchecked")
    @Override
    public void appendValue(final Appendable writer, final CfgEntryData field, final Object source, final Object elementRaw, final int level, final ElementPlace elementPlace) throws IOException
    {
        final T element = (this.fieldType.isAssignableFrom(elementRaw.getClass())) ? (T) elementRaw : this.validateType(elementRaw);
        StringTemplateElement.INSTANCE.appendValue(writer, field, source, StringTemplateElement.INSTANCE.validateType(this.convertToString(element)), level, elementPlace);
    }
}
