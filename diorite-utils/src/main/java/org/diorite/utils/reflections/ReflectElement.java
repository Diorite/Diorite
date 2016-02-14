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

package org.diorite.utils.reflections;

import java.lang.reflect.Type;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Class that connect {@link ReflectGetter} and {@link ReflectSetter},
 * allowing to get and set values using one object.
 *
 * @param <E> type of value.
 */
public class ReflectElement<E> implements ReflectGetter<E>, ReflectSetter<E>
{
    private final ReflectGetter<E> getter;
    private final ReflectSetter<E> setter;

    /**
     * Construct new reflect element using given getter and setter instance.
     *
     * @param getter getter for value.
     * @param setter setter for value.
     */
    public ReflectElement(final ReflectGetter<E> getter, final ReflectSetter<E> setter)
    {
        this.getter = getter;
        this.setter = setter;
    }

    ReflectElement(final FieldAccessor<?> accessor)
    {
        final ReflectField<E> reflectField = new ReflectField<>(accessor);
        this.getter = reflectField;
        this.setter = reflectField;
    }

    ReflectElement(final MethodInvoker getter, final MethodInvoker setter)
    {
        this.getter = new ReflectMethodGetter<>(getter);
        this.setter = new ReflectMethodSetter<>(setter);
    }

    ReflectElement(final MethodInvoker getter, final FieldAccessor<?> accessor)
    {
        this.getter = new ReflectMethodGetter<>(getter);
        this.setter = new ReflectField<>(accessor);
    }

    ReflectElement(final FieldAccessor<?> accessor, final MethodInvoker setter)
    {
        this.getter = new ReflectField<>(accessor);
        this.setter = new ReflectMethodSetter<>(setter);
    }

    @Override
    public E get(final Object src)
    {
        return this.getter.get(src);
    }

    @Override
    public void set(final Object src, final Object obj)
    {
        this.setter.set(src, obj);
    }

    @Override
    public Type getGenericType()
    {
        return (this.getter == null) ? null : this.getter.getGenericType();
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("getter", this.getter).append("setter", this.setter).toString();
    }
}
