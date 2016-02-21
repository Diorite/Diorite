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
import org.diorite.utils.SimpleEnum;
import org.diorite.utils.math.DioriteMathUtils;

/**
 * Template handler for all sime enum based objects.
 *
 * @see SimpleEnum
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public class SimpleEnumTemplateElement extends TemplateElement<SimpleEnum>
{
    /**
     * Instance of template to direct-use.
     */
    public static final SimpleEnumTemplateElement INSTANCE = new SimpleEnumTemplateElement();

    /**
     * Construct new default template handler.
     */
    public SimpleEnumTemplateElement()
    {
        super(SimpleEnum.class);
    }

    @Override
    protected boolean canBeConverted0(final Class<?> c)
    {
        return false;
    }

    @Override
    protected SimpleEnum convertObject0(final Object obj) throws UnsupportedOperationException
    {
        throw this.getException(obj);
    }

    @Override
    protected SimpleEnum convertDefault0(final Object obj, final Class<?> fieldType)
    {
        if (obj instanceof String)
        {
            return SimpleEnum.getSimpleEnumValueSafe(obj.toString(), DioriteMathUtils.asInt(obj.toString(), - 1), (Class<SimpleEnum>) fieldType);
        }
        throw this.getException(obj);
    }

    @Override
    public void appendValue(final Appendable writer, final CfgEntryData field, final Object source, final Object elementRaw, final int level, final ElementPlace elementPlace) throws IOException
    {
        final SimpleEnum element = (elementRaw instanceof SimpleEnum) ? ((SimpleEnum) elementRaw) : this.validateType(elementRaw);
        StringTemplateElement.INSTANCE.appendValue(writer, field, source, StringTemplateElement.INSTANCE.validateType(element.name()), level, elementPlace);

    }
}
