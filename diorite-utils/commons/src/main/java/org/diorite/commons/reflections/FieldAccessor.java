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
import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;

import org.diorite.commons.DioriteUtils;

/**
 * Class used to access previously prepared fields,
 * fields used by this class must be accessible.
 *
 * @param <T>
 *         type of field.
 */
public class FieldAccessor<T> implements ReflectGetter<T>, ReflectSetter<T>
{
    protected final Field field;

    /**
     * Construct new invoker for given constructor, it don't check its accessible status.
     *
     * @param field
     *         field to wrap.
     */
    public FieldAccessor(Field field)
    {
        this.field = field;
    }

    @Override
    @Nullable
    @SuppressWarnings("unchecked")
    public T get(@Nullable Object target)
    {
        try
        {
            return (T) this.field.get(target);
        }
        catch (IllegalArgumentException | IllegalAccessException e)
        {
            throw new RuntimeException("Cannot access reflection.", e);
        }
        catch (Exception e)
        {
            throw DioriteUtils.sneakyThrow(e);
        }
    }

    @SuppressWarnings("unchecked")
    public Class<T> getType()
    {
        return (Class<T>) field.getType();
    }

    @Override
    public Type getGenericType()
    {
        return this.field.getGenericType();
    }

    @Override
    public void set(@Nullable Object target, @Nullable Object value)
    {
        try
        {
            this.field.set(target, value);
        }
        catch (IllegalArgumentException | IllegalAccessException e)
        {
            throw new RuntimeException("Cannot access reflection.", e);
        }
        catch (Exception e)
        {
            throw DioriteUtils.sneakyThrow(e);
        }
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
        return this.field.getName();
    }

    @Override
    public int getModifiers()
    {
        return this.field.getModifiers();
    }

    public boolean makeNonFinal()
    {
        if (! this.isFinal())
        {
            return true;
        }
        try
        {
            this.ensureAccessible();
            FieldAccessor<?> modifiersField = DioriteReflectionUtils.getField(Field.class, "modifiers");
            modifiersField.ensureAccessible();
            modifiersField.set(this.field, this.field.getModifiers() & ~ Modifier.FINAL);
            return true;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void ensureAccessible()
    {
        DioriteReflectionUtils.getAccess(this.field);
    }

    /**
     * Check if given object class contains this field.
     *
     * @param target
     *         object to check.
     *
     * @return true if class contains this field.
     */
    public boolean hasField(Object target)
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

    @Nullable
    private MethodHandle cachedGetter;
    @Nullable
    private MethodHandle cachedSetter;
    @Nullable
    private VarHandle    cached;

    @Override
    public MethodHandle getGetter()
    {
        if (this.cachedGetter != null)
        {
            return this.cachedGetter;
        }
        try
        {
            return this.cachedGetter = MethodHandles.lookup().unreflectGetter(this.field);
        }
        catch (IllegalAccessException e)
        {
            throw new RuntimeException("Cannot access reflection.", e);
        }
    }

    @Override
    public MethodHandle getSetter()
    {
        if (this.cachedSetter != null)
        {
            return this.cachedSetter;
        }
        try
        {
            return this.cachedSetter = MethodHandles.lookup().unreflectSetter(this.field);
        }
        catch (IllegalAccessException e)
        {
            throw new RuntimeException("Cannot access reflection.", e);
        }
    }

    /**
     * Returns {@link VarHandle} for given field. <br>
     * Field must be visible!
     *
     * @return {@link VarHandle} for given field.
     */
    public VarHandle getHandle()
    {
        if (this.cached != null)
        {
            return this.cached;
        }
        try
        {
            return this.cached = MethodHandles.lookup().unreflectVarHandle(this.field);
        }
        catch (IllegalAccessException e)
        {
            throw new RuntimeException("Cannot access reflection.", e);
        }
    }

    @Override
    public String toString()
    {
        return this.field.toString();
    }
}
