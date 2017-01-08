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
 * Classes implementing this interface can get something from given object.
 */
public interface ReflectGetter<E> extends ReflectElement
{
    /**
     * Get value from given object.
     *
     * @param src
     *         object to get value from it.
     *
     * @return value from object.
     */
    @Nullable
    E get(@Nullable Object src);

    @Nullable
    @Override
    default Object invokeWith(Object... args) throws IllegalArgumentException
    {
        if (this.isStatic())
        {
            if (args.length == 0)
            {
                return this.get(null);
            }
            throw new IllegalArgumentException("Expected no parameters.");
        }
        if (args.length == 1)
        {
            return this.get(args[0]);
        }
        throw new IllegalArgumentException("Expected single parameter.");
    }

    /**
     * @return generic type.
     */
    Type getGenericType();

    /**
     * Returns {@link MethodHandle} for this getter method.
     *
     * @return {@link MethodHandle} for this getter method.
     */
    MethodHandle getGetter();

    /**
     * Returns {@link MethodHandle} for this getter method, and binds it to given object.
     *
     * @param object
     *         method handle will be bound to this object.
     *
     * @return {@link MethodHandle} for this getter method.
     *
     * @see MethodHandle#bindTo(Object)
     */
    default MethodHandle getGetter(Object object)
    {
        return this.getGetter().bindTo(object);
    }
}
