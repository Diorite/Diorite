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

import java.lang.reflect.Type;
import java.util.function.Function;

import org.apache.commons.lang3.builder.ToStringBuilder;

import org.diorite.config.Config;
import org.diorite.config.ConfigPropertyTemplate;

public class ConfigPropertyTemplateImpl<T> implements ConfigPropertyTemplate<T>
{
    private final Class<T>            rawType;
    private final Type                genericType;
    private final String              name;
    private       Function<Config, T> defaultValueSupplier;

    public ConfigPropertyTemplateImpl(Class<T> rawType, Type genericType, String name, Function<Config, T> defaultValueSupplier)
    {
        this.rawType = rawType;
        this.genericType = genericType;
        this.name = name;
        this.defaultValueSupplier = defaultValueSupplier;
    }

    @Override
    public Class<T> getRawType()
    {
        return this.rawType;
    }

    @Override
    public Type getGenericType()
    {
        return this.genericType;
    }

    @Override
    public String getName()
    {
        return this.name;
    }

    @SuppressWarnings("unchecked")
    @Override
    public T getDefault(Config config)
    {
        T def = this.defaultValueSupplier.apply(config);
        if ((def == null) && this.rawType.isPrimitive())
        {
            return (T) this.getPrimitiveDefault();
        }
        return def;
    }

    private Object getPrimitiveDefault()
    {
        Class<T> rawType = this.rawType;
        if (rawType == boolean.class)
        {
            return false;
        }
        if (rawType == byte.class)
        {
            return (byte) 0;
        }
        if (rawType == short.class)
        {
            return (short) 0;
        }
        if (rawType == char.class)
        {
            return '\0';
        }
        if (rawType == int.class)
        {
            return 0;
        }
        if (rawType == long.class)
        {
            return 0L;
        }
        if (rawType == float.class)
        {
            return 0.0F;
        }
        if (rawType == double.class)
        {
            return 0.0;
        }
        throw new InternalError("Unknown primitive type:" + rawType);
    }

    public void setDefaultValueSupplier(Function<Config, T> defaultValueSupplier)
    {
        this.defaultValueSupplier = defaultValueSupplier;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this).appendSuper(super.toString()).append("rawType", this.rawType).append("genericType", this.genericType)
                                        .append("name", this.name).toString();
    }
}
