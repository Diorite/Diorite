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

package org.diorite.inject.binder;

import javax.annotation.Nullable;

import java.util.Collection;
import java.util.Collections;

import org.diorite.inject.InjectionException;
import org.diorite.inject.binder.qualifier.QualifierData;
import org.diorite.inject.impl.data.InjectValueData;

@FunctionalInterface
public interface DynamicProvider<T>
{
    /**
     * Provides a fully-constructed and injected instance of {@code T}.
     *
     * @param object
     *         instance of object where injected member is.
     * @param data
     *         qualifiers of member.
     *
     * @return fully-constructed and injected instance of {@code T}.
     */
    @Nullable
    T tryToGet(Object object, QualifierData data);

    /**
     * Provides a fully-constructed and injected instance of {@code T}, or default value if injection returned null value.
     *
     * @param object
     *         instance of object where injected member is.
     * @param data
     *         qualifiers of member.
     * @param def
     *         default value to use.
     *
     * @return fully-constructed and injected instance of {@code T}.
     */
    default T orDefault(Object object, QualifierData data, T def)
    {
        T t = this.tryToGet(object, data);
        if (t == null)
        {
            return def;
        }
        return t;
    }

    /**
     * Provides a fully-constructed and injected instance of {@code T}, or throws error if injection returned null value.
     *
     * @param object
     *         instance of object where injected member is.
     * @param data
     *         qualifiers of member.
     *
     * @return fully-constructed and injected instance of {@code T}.
     *
     * @throws InjectionException
     *         if injection returned null value.
     */
    default T getNotNull(Object object, QualifierData data) throws InjectionException
    {
        T t = this.tryToGet(object, data);
        if (t == null)
        {
            throw new InjectionException("Injected null values, but requested non-null value");
        }
        return t;
    }

    /**
     * Returns list of injected values that this provider depends on, empty collection if none.
     *
     * @return list of injected values that this provider depends on, empty collection if none.
     */
    default Collection<? extends InjectValueData<?, ?>> getInjectValues()
    {
        return Collections.emptyList();
    }
}
