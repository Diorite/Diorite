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

import java.lang.reflect.Field;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Class used to access previously prepared fields,
 * fields used by this class must be accessible.
 *
 * @param <T> type of field.
 */
public class FieldAccessor<T>
{
    protected final Field field;

    /**
     * Construct new invoker for given constructor, it don't check its accessible status.
     *
     * @param field field to wrap.
     */
    public FieldAccessor(final Field field)
    {
        this.field = field;
    }

    /**
     * get value of this field.
     *
     * @param target target object, use null for static fields.
     *
     * @return value of this field.
     */
    @SuppressWarnings("unchecked")
    public T get(final Object target)
    {
        try
        {
            return (T) this.field.get(target);
        } catch (final IllegalAccessException e)
        {
            throw new RuntimeException("Cannot access reflection.", e);
        }
    }

    /**
     * set value fo this field.
     *
     * @param target target object, use null for static fields.
     * @param value  new value.
     */
    public void set(final Object target, final Object value)
    {
        try
        {
            this.field.set(target, value);
        } catch (final IllegalAccessException e)
        {
            throw new RuntimeException("Cannot access reflection.", e);
        }
    }

    /**
     * Check if given object class contains this field.
     *
     * @param target object to check.
     *
     * @return true if class contains this field.
     */
    public boolean hasField(final Object target)
    {
        return this.field.getDeclaringClass().isAssignableFrom(target.getClass());
    }

    /**
     * @return wrapped field.
     */
    public Field getField()
    {
        return this.field;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("field", this.field).toString();
    }
}
