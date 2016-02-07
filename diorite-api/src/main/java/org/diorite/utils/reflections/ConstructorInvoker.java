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

import java.lang.reflect.Constructor;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Class used to invoke previously prepared constructors,
 * constructors used by this class must be accessible.
 */
public class ConstructorInvoker
{
    private final Constructor<?> constructor;

    /**
     * Construct new invoker for given constructor, it don't check its accessible status.
     *
     * @param constructor constructor to wrap.
     */
    public ConstructorInvoker(final Constructor<?> constructor)
    {
        this.constructor = constructor;
    }

    /**
     * Invoke constructor and create new object.
     *
     * @param arguments arguments for constructor.
     *
     * @return new object.
     */
    public Object invoke(final Object... arguments)
    {
        try
        {
            return this.constructor.newInstance(arguments);
        } catch (final Exception e)
        {
            throw new RuntimeException("Cannot invoke constructor " + this.constructor, e);
        }
    }

    /**
     * @return wrapped constructor.
     */
    public Constructor<?> getConstructor()
    {
        return this.constructor;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("constructor", this.constructor).toString();
    }
}
