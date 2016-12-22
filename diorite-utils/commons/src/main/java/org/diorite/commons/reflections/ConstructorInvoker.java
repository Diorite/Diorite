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
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import org.diorite.commons.DioriteUtils;

/**
 * Class used to invoke previously prepared constructors,
 * constructors used by this class must be accessible.
 */
public class ConstructorInvoker<T> implements ReflectMethod
{
    private final Constructor<T> constructor;

    /**
     * Construct new invoker for given constructor, it don't check its accessible status.
     *
     * @param constructor
     *         constructor to wrap.
     */
    @SuppressWarnings("unchecked")
    public ConstructorInvoker(Constructor<?> constructor)
    {
        this.constructor = (Constructor<T>) constructor;
    }

    /**
     * Invoke constructor and create new object.
     *
     * @param arguments
     *         arguments for constructor.
     *
     * @return new object.
     */
    public T invoke(Object... arguments)
    {
        try
        {
            return this.constructor.newInstance(arguments);
        }
        catch (InstantiationException | IllegalAccessException | IllegalArgumentException e)
        {
            throw new RuntimeException("Cannot invoke constructor " + this.constructor, e);
        }
        catch (InvocationTargetException e)
        {
            throw DioriteUtils.sneakyThrow(e.getCause());
        }
        catch (Exception e)
        {
            throw DioriteUtils.sneakyThrow(e);
        }
    }

    /**
     * @return wrapped constructor.
     */
    public Constructor<T> getConstructor()
    {
        return this.constructor;
    }

    @Nullable
    private MethodHandle cached;

    @Override
    public boolean isConstructor()
    {
        return true;
    }

    @Override
    public boolean isStatic()
    {
        return false;
    }

    @Override
    public void ensureAccessible()
    {
        this.constructor.setAccessible(true);
    }

    @Override
    public MethodHandle getHandle()
    {
        if (this.cached != null)
        {
            return this.cached;
        }
        try
        {
            return this.cached = MethodHandles.lookup().unreflectConstructor(this.constructor);
        }
        catch (IllegalAccessException e)
        {
            throw new RuntimeException("Cannot access reflection.", e);
        }
    }

    @Override
    public String toString()
    {
        return this.constructor.toString();
    }
}
