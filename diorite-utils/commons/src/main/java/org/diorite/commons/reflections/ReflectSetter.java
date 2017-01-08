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

/**
 * Classes implementing this interface can set given object in other given object.
 */
public interface ReflectSetter<E> extends ReflectElement
{
    /**
     * Set value in given object.
     *
     * @param src
     *         object to set value in it.
     * @param obj
     *         new value.
     */
    void set(@Nullable Object src, @Nullable Object obj);

    @Nullable
    @Override
    default Object invokeWith(Object... args) throws IllegalArgumentException
    {
        if (this.isStatic())
        {
            if (args.length == 1)
            {
                this.set(null, args[0]);
                return null;
            }
            throw new IllegalArgumentException("Expected single parameter.");
        }
        if (args.length == 2)
        {
            this.set(args[0], args[1]);
            return null;
        }
        throw new IllegalArgumentException("Expected object instance and one parameter.");
    }

    /**
     * Returns {@link MethodHandle} for this setter method.
     *
     * @return {@link MethodHandle} for this setter method.
     */
    MethodHandle getSetter();

    /**
     * Returns {@link MethodHandle} for this setter method, and binds it to given object.
     *
     * @param object
     *         method handle will be bound to this object.
     *
     * @return {@link MethodHandle} for this setter method.
     *
     * @see MethodHandle#bindTo(Object)
     */
    default MethodHandle getSetter(Object object)
    {
        return this.getSetter().bindTo(object);
    }
}
