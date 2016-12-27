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

import java.lang.reflect.Modifier;

/**
 * Represent some reflect element, field, method or constructor.
 */
public interface ReflectElement
{
    /**
     * Invoke this reflect element: <br/>
     * For constructor - invoke with all arguments. <br/>
     * For static method - invoke with all arguments. <br/>
     * For non-static method - first argument is object instance. <br/>
     * For static fields - get -> no arguments, set -> single argument. <br/>
     * For non-static fields - get -> object instance, set -> object instance and value. <br/>
     *
     * @param args
     *         all arguments to use for invoking.
     *
     * @return return value if any.
     */
    @Nullable
    Object invokeWith(Object... args) throws IllegalArgumentException;

    /**
     * Returns modifiers of element.
     *
     * @return modifiers of element.
     */
    int getModifiers();

    /**
     * Returns true if this element is static. <br/>
     * Returns false for constructor.
     *
     * @return true if this element is static.
     */
    default boolean isStatic()
    {
        return Modifier.isStatic(this.getModifiers());
    }

    /**
     * Returns true if given element is public.
     *
     * @return true if given element is public.
     */
    default boolean isPublic()
    {
        return Modifier.isPublic(this.getModifiers());
    }

    /**
     * Returns true if given element is private.
     *
     * @return true if given element is private.
     */
    default boolean isPrivate()
    {
        return Modifier.isPrivate(this.getModifiers());
    }

    /**
     * Returns true if given element is protected.
     *
     * @return true if given element is protected.
     */
    default boolean isProtected()
    {
        return Modifier.isProtected(this.getModifiers());
    }

    /**
     * Returns true if given element is final.
     *
     * @return true if given element is final.
     */
    default boolean isFinal()
    {
        return Modifier.isFinal(this.getModifiers());
    }

    /**
     * Returns true if given element is transient.
     *
     * @return true if given element is transient.
     */
    default boolean isTransient()
    {
        return Modifier.isTransient(this.getModifiers());
    }

    /**
     * Returns true if given element is volatile.
     *
     * @return true if given element is volatile.
     */
    default boolean isVolatile()
    {
        return Modifier.isVolatile(this.getModifiers());
    }

    /**
     * Ensure that given executable is accessible.
     */
    void ensureAccessible();
}
