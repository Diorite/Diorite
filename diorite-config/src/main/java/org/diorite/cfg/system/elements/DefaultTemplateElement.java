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
import org.diorite.cfg.system.Template;
import org.diorite.cfg.system.TemplateCreator;

/**
 * Last template handler, trying get template for unknown object, or change it to string.
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public class DefaultTemplateElement extends TemplateElement<Object>
{
    /**
     * Instance of template to direct-use.
     */
    public static final DefaultTemplateElement INSTANCE = new DefaultTemplateElement();

    /**
     * Construct new default template handler.
     */
    public DefaultTemplateElement()
    {
        super(Object.class);
    }

    @Override
    protected Object convertObject0(final Object def)
    {
        return def;
    }

    @Override
    protected boolean canBeConverted0(final Class<?> clazz)
    {
        return true;
    }

    @Override
    protected Object convertDefault0(final Object obj, final Class<?> fieldType)
    {
        return obj;
    }

    @Override
    public void appendValue(final Appendable writer, final CfgEntryData field, final Object source, final Object element, final int level, final ElementPlace elementPlace) throws IOException
    {
        final Template template = TemplateCreator.getTemplate(element.getClass(), false);
        if (template != null)
        {
            writer.append('\n');
            template.dump(writer, element, level + 1, false, elementPlace);
        }
        else
        {
            StringTemplateElement.INSTANCE.appendValue(writer, field, source, StringTemplateElement.INSTANCE.validateType(element), level, elementPlace);
        }
    }

}
