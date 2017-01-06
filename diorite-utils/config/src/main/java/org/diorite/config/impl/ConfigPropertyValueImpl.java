/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017. Diorite (by Bartłomiej Mazur (aka GotoFinal))
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

package org.diorite.config.impl;

import javax.annotation.Nullable;

import org.apache.commons.lang3.Validate;

import org.diorite.commons.reflections.DioriteReflectionUtils;
import org.diorite.config.Config;
import org.diorite.config.ConfigPropertyTemplate;
import org.diorite.config.ConfigPropertyValue;

public class ConfigPropertyValueImpl<T> implements ConfigPropertyValue<T>
{
    private final Config config;

    private final     ConfigPropertyTemplate<T> template;
    @Nullable private T                         rawValue;

    public ConfigPropertyValueImpl(Config config, ConfigPropertyTemplate<T> template)
    {
        this.config = config;
        this.template = template;
    }

    public ConfigPropertyValueImpl(Config config, ConfigPropertyTemplate<T> template, @Nullable T value)
    {
        this.config = config;
        this.template = template;
        this.rawValue = value;
    }

    @Override
    public Config getDeclaringConfig()
    {
        return this.config;
    }

    @Override
    public ConfigPropertyTemplate<T> getProperty()
    {
        return this.template;
    }

    @Nullable
    @Override
    public T getRawValue()
    {
        return this.rawValue;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void setRawValue(@Nullable T value)
    {
        Class<T> rawType = this.template.getRawType();
        Class<?> primitiveRawType = DioriteReflectionUtils.getPrimitive(rawType);
        if (! primitiveRawType.isPrimitive())
        {
            if (! rawType.isInstance(value) && ! DioriteReflectionUtils.getWrapperClass(rawType).isInstance(value))
            {
                throw new IllegalArgumentException("Invalid object type: " + value + " in template property: " + this.template.getName() + " (" +
                                                   this.template.getGenericType() + ")");
            }
        }
        else if (value instanceof Number)
        {
            Number num = (Number) value;
            if (primitiveRawType == byte.class)
            {
                num = num.byteValue();
            }
            if (primitiveRawType == short.class)
            {
                num = num.shortValue();
            }
            if (primitiveRawType == int.class)
            {
                num = num.intValue();
            }
            if (primitiveRawType == long.class)
            {
                num = num.longValue();
            }
            if (primitiveRawType == float.class)
            {
                num = num.floatValue();
            }
            if (primitiveRawType == double.class)
            {
                num = num.doubleValue();
            }
            this.rawValue = (T) num;
            return;
        }
        else
        {
            throw new IllegalArgumentException("Invalid object type: " + value + " in template property: " + this.template.getName() + " (" +
                                               this.template.getGenericType() + ")");
        }
        this.rawValue = value;
    }

    @Override
    public void set(String[] path, @Nullable Object value) throws IllegalStateException
    {
        Validate.notNull(this.rawValue);
        NestedNodesHelper.set(this.rawValue, path, value);
    }

    @Override
    public Object get(String[] path) throws IllegalStateException
    {
        Validate.notNull(this.rawValue);
        return NestedNodesHelper.get(this.rawValue, path);
    }

    @Override
    public Object remove(String[] path) throws IllegalStateException
    {
        Validate.notNull(this.rawValue);
        return NestedNodesHelper.remove(this.rawValue, path);
    }
}
