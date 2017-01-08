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

import java.lang.invoke.MethodHandle;
import java.lang.reflect.Modifier;

/**
 * Represent reflective wrapper that support MethodHandles.
 */
public interface ReflectMethod extends ReflectElement
{
    /**
     * Returns if this method is a constructor.
     *
     * @return if this method is a constructor.
     */
    boolean isConstructor();

    /**
     * Returns true if given method is abstract.
     *
     * @return true if given method is abstract.
     */
    default boolean isAbstract()
    {
        return Modifier.isAbstract(this.getModifiers());
    }

    /**
     * Returns true if given method is native.
     *
     * @return true if given method is native.
     */
    default boolean isNative()
    {
        return Modifier.isNative(this.getModifiers());
    }

    /**
     * Returns true if given method is synchronized.
     *
     * @return true if given method is synchronized.
     */
    default boolean isSynchronized()
    {
        return Modifier.isSynchronized(this.getModifiers());
    }

    /**
     * Returns {@link MethodHandle} for this method.
     *
     * @return {@link MethodHandle} for this method.
     */
    MethodHandle getHandle();

    /**
     * Returns {@link MethodHandle} for this method, and binds it to given object.
     *
     * @param object
     *         method handle will be bound to this object.
     *
     * @return {@link MethodHandle} for this method.
     *
     * @see MethodHandle#bindTo(Object)
     */
    default MethodHandle getHandle(Object object)
    {
        return this.getHandle().bindTo(object);
    }
}
