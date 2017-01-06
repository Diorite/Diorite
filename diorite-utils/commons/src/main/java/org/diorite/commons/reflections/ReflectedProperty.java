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

package org.diorite.commons.reflections;

import javax.annotation.Nullable;

import java.lang.invoke.MethodHandle;
import java.lang.reflect.Type;

/**
 * Class that connect {@link ReflectGetter} and {@link ReflectSetter},
 * allowing to get and set values using one object.
 *
 * @param <E>
 *         type of value.
 */
public class ReflectedProperty<E> implements ReflectGetter<E>, ReflectSetter<E>
{
    @Nullable
    private final ReflectGetter<E> getter;
    @Nullable
    private final ReflectSetter<E> setter;

    /**
     * Construct new reflect element using given getter and setter instance.
     *
     * @param getter
     *         getter for value.
     * @param setter
     *         setter for value.
     */
    public ReflectedProperty(@Nullable ReflectGetter<E> getter, @Nullable ReflectSetter<E> setter)
    {
        this.getter = getter;
        this.setter = setter;
    }

    ReflectedProperty(FieldAccessor<?> accessor)
    {
        ReflectField<E> reflectField = new ReflectField<>(accessor);
        this.getter = reflectField;
        this.setter = reflectField;
    }

    ReflectedProperty(MethodInvoker getter, MethodInvoker setter)
    {
        this.getter = new ReflectMethodGetter<>(getter);
        this.setter = new ReflectMethodSetter<>(setter);
    }

    ReflectedProperty(MethodInvoker getter, FieldAccessor<?> accessor)
    {
        this.getter = new ReflectMethodGetter<>(getter);
        this.setter = new ReflectField<>(accessor);
    }

    ReflectedProperty(FieldAccessor<?> accessor, MethodInvoker setter)
    {
        this.getter = new ReflectField<>(accessor);
        this.setter = new ReflectMethodSetter<>(setter);
    }

    @Override
    public E get(@Nullable Object src)
    {
        if (this.getter == null)
        {
            throw new IllegalStateException("Getter not provided.");
        }
        return this.getter.get(src);
    }

    @Nullable
    @Override
    public Object invokeWith(Object... args) throws IllegalArgumentException
    {
        if (this.isStatic())
        {
            if (args.length == 0)
            {
                return this.get(null);
            }
            if (args.length == 1)
            {
                this.set(null, args[0]);
                return null;
            }
            throw new IllegalArgumentException("Expected none or single parameter.");
        }
        if (args.length == 1)
        {
            return this.get(args[0]);
        }
        if (args.length == 2)
        {
            this.set(args[0], args[1]);
            return null;
        }
        throw new IllegalArgumentException("Expected object instance and none or one parameter.");
    }

    @Override
    public String getName()
    {
        if (this.getter instanceof FieldAccessor)
        {
            return this.getter.getName();
        }
        if (this.setter instanceof FieldAccessor)
        {
            return this.setter.getName();
        }
        String baseName = null;
        if (this.getter != null)
        {
            baseName = this.getter.getName();
            if (baseName.startsWith("get"))
            {
                char first = baseName.charAt(3);
                baseName.substring(4);
                return Character.toLowerCase(first) + baseName;
            }
            if (baseName.startsWith("is"))
            {
                char first = baseName.charAt(2);
                baseName.substring(3);
                return Character.toLowerCase(first) + baseName;
            }
            if (baseName.startsWith("has"))
            {
                char first = baseName.charAt(3);
                baseName.substring(4);
                return Character.toLowerCase(first) + baseName;
            }
            return baseName;
        }
        if (this.setter != null)
        {
            baseName = this.setter.getName();
            if (baseName.startsWith("set"))
            {
                char first = baseName.charAt(3);
                baseName.substring(4);
                return Character.toLowerCase(first) + baseName;
            }
            return baseName;
        }
        throw new IllegalStateException("Both setter and getter is null.");
    }

    @Override
    public void set(@Nullable Object src, @Nullable Object obj)
    {
        if (this.setter == null)
        {
            throw new IllegalStateException("Setter not provided.");
        }
        this.setter.set(src, obj);
    }

    @Override
    public MethodHandle getSetter()
    {
        if (this.setter == null)
        {
            throw new IllegalStateException("Setter not provided.");
        }
        return this.setter.getSetter();
    }

    @Override
    public MethodHandle getGetter()
    {
        if (this.getter == null)
        {
            throw new IllegalStateException("Getter not provided.");
        }
        return this.getter.getGetter();
    }

    @Override
    public Type getGenericType()
    {
        if (this.getter == null)
        {
            throw new IllegalStateException("Missing getter instance");
        }
        else
        {
            return this.getter.getGenericType();
        }
    }

    @Override
    public int getModifiers()
    {
        if (this.getter != null)
        {
            return this.getter.getModifiers();
        }
        if (this.setter != null)
        {
            return this.setter.getModifiers();
        }
        throw new IllegalStateException("Missing getter/setter instance");
    }

    @Override
    public void ensureAccessible()
    {
        if (this.getter != null)
        {
            this.getter.ensureAccessible();
        }
        if (this.setter != null)
        {
            this.setter.ensureAccessible();
        }
    }
}
