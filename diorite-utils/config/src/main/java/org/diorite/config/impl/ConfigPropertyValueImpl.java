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

import java.util.Collection;
import java.util.Map;

import org.apache.commons.lang3.Validate;

import org.diorite.commons.reflections.DioriteReflectionUtils;
import org.diorite.config.Config;
import org.diorite.config.ConfigPropertyTemplate;
import org.diorite.config.ConfigPropertyValue;
import org.diorite.config.exceptions.ValidationException;
import org.diorite.config.serialization.snakeyaml.YamlCollectionCreator;

import groovy.transform.CompileStatic;
import groovy.transform.TypeCheckingMode;

public class ConfigPropertyValueImpl<T> implements ConfigPropertyValue<T>
{
    private final Config config;

    private final     ConfigPropertyTemplate<T> template;
    @Nullable private T                         rawValue;

    public ConfigPropertyValueImpl(Config config, ConfigPropertyTemplate<T> template)
    {
        Validate.notNull(config, "config can't be null");
        Validate.notNull(template, "template can't be null");
        this.config = config;
        this.template = template;
    }

    public ConfigPropertyValueImpl(Config config, ConfigPropertyTemplate<T> template, @Nullable T value)
    {
        this(config, template);
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

    @Nullable
    public T validate(@Nullable T input) throws ValidationException
    {
        try
        {
            return this.template.getValidator().validate(input, this.config);
        }
        catch (Exception e)
        {
            throw ValidationException.of(this, input, e);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public void setRawValue(@Nullable T value) throws ValidationException
    {
        value = this.validate(value);
        Class<T> rawType = this.template.getRawType();
        Class<?> primitiveRawType = DioriteReflectionUtils.getPrimitive(rawType);
        if (! primitiveRawType.isPrimitive())
        {
            if (value != null)
            {
                if (! rawType.isInstance(value) && ! DioriteReflectionUtils.getWrapperClass(rawType).isInstance(value))
                {
                    throw new IllegalArgumentException("Invalid object type: " + value + " in template property: " + this.template.getName() + " (" +
                                                       this.template.getGenericType() + ")");
                }
            }
            else
            {
                // keep collections not null if possible.
                try
                {
                    if (Collection.class.isAssignableFrom(rawType) || (Map.class.isAssignableFrom(rawType) && ! Config.class.isAssignableFrom(rawType)))
                    {
                        this.rawValue = YamlCollectionCreator.createCollection(rawType, 10);
                        return;
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }
        else if (! rawType.isPrimitive() && (value == null))
        {
            this.rawValue = null;
            return;
        }
        else
        {
            if (value instanceof Number)
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
            else if ((value instanceof Boolean) || (value instanceof Character))
            {
                this.rawValue = value;
                return;
            }
            else
            {
                throw new IllegalArgumentException("Invalid object type: " + (value == null ? "" : ("(" + value.getClass() + ") ")) + value +
                                                   " in template property: " + this.template.getName() + " (" +
                                                   this.template.getGenericType() + ")");
            }
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
