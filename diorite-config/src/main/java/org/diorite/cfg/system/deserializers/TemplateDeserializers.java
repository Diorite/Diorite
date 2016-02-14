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

package org.diorite.cfg.system.deserializers;

import org.diorite.cfg.system.ConfigField;
import org.diorite.utils.pipeline.BasePipeline;
import org.diorite.utils.pipeline.Pipeline;
import org.diorite.utils.reflections.DioriteReflectionUtils;

public final class TemplateDeserializers
{
    private static final Pipeline<TemplateDeserializer<?>> elements = new BasePipeline<>();

    private TemplateDeserializers()
    {
    }

    /**
     * @return editable pipeline with template elements.
     *
     * @see TemplateDeserializer
     */
    public static Pipeline<TemplateDeserializer<?>> getElements()
    {
        return elements;
    }

    /**
     * This method is trying to get element deserializer from template pipeline using few methods:
     * (using next only if prev method fail and returns null)
     * 1. By name using: {@link Class#getName()}
     * 2. By name using: {@link Class#getSimpleName()}, if that also fail then it will use first
     * 3. By type: template which {@link TemplateDeserializer#isValidType(Class)} method will return true.
     *
     * @param clazz class to find template deserializer for it.
     *
     * @return template element deserializer.
     */
    public static TemplateDeserializer<?> getElement(Class<?> clazz)
    {
        clazz = DioriteReflectionUtils.getPrimitive(clazz);
        TemplateDeserializer<?> element = elements.get(clazz.getName());
        if (element != null)
        {
            return element;
        }
        element = elements.get(clazz.getSimpleName());
        if (element != null)
        {
            return element;
        }
        for (final TemplateDeserializer<?> e : elements)
        {
            if (e.isValidType(clazz))
            {
                return e;
            }
        }
        return null;
    }

    /**
     * This method is trying to get element deserializer from template pipeline using few methods:
     * (using next only if prev method fail and returns null)
     * 1. By name using: {@link Class#getName()}
     * 2. By name using: {@link Class#getSimpleName()}, if that also fail then it will use first
     * 3. By type: template which {@link TemplateDeserializer#isValidType(Class)} method will return true.
     *
     * @param field field to find template deserializer for it.
     *
     * @return template element deserializer.
     */
    public static TemplateDeserializer<?> getElement(final ConfigField field)
    {
        return getElement(field.getField().getType());
    }

    static
    {
        // TODO?
    }
}
