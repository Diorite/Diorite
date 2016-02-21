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
import org.diorite.chat.component.BaseComponent;
import org.diorite.chat.component.TextComponent;
import org.diorite.chat.component.serialize.ComponentSerializer;

/**
 * Template handler for all BaseComponent objects.
 *
 * @see BaseComponent
 */
public class BaseComponentTemplateElement extends TemplateElement<BaseComponent>
{
    /**
     * Instance of template to direct-use.
     */
    public static final BaseComponentTemplateElement INSTANCE = new BaseComponentTemplateElement();

    /**
     * Construct new default template handler.
     */
    public BaseComponentTemplateElement()
    {
        super(BaseComponent.class);
    }

    @Override
    protected boolean canBeConverted0(final Class<?> c)
    {
        return BaseComponent.class.isAssignableFrom(c) || String.class.isAssignableFrom(c);
    }

    @Override
    protected BaseComponent convertObject0(final Object obj) throws UnsupportedOperationException
    {
        if (obj instanceof String)
        {
            try
            {
                return ComponentSerializer.safeParse((String) obj, '&');
            } catch (final Exception e)
            {
                return TextComponent.fromLegacyText((String) obj);
            }
        }
        throw this.getException(obj);
    }

    @Override
    protected BaseComponent convertDefault0(final Object obj, final Class<?> fieldType)
    {
        if (obj instanceof BaseComponent)
        {
            return (BaseComponent) obj;
        }
        if (obj instanceof String)
        {
            try
            {
                return TextComponent.join(ComponentSerializer.parse((String) obj));
            } catch (final Exception e)
            {
                return TextComponent.fromLegacyText((String) obj);
            }
        }
        throw new UnsupportedOperationException("Can't convert default value (" + obj.getClass().getName() + "): " + obj);
    }

    @Override
    public void appendValue(final Appendable writer, final CfgEntryData field, final Object source, final Object elementRaw, final int level, final ElementPlace elementPlace) throws IOException
    {
        final BaseComponent element = (elementRaw instanceof BaseComponent) ? ((BaseComponent) elementRaw) : this.validateType(elementRaw);
        StringTemplateElement.INSTANCE.appendValue(writer, field, source, StringTemplateElement.INSTANCE.validateType(ComponentSerializer.toString(element)), level, elementPlace);
    }
}
