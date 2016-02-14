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

package org.diorite.utils.lazy;

import java.util.Collection;
import java.util.function.Supplier;

import org.apache.commons.lang3.Validate;

/**
 * Class to represent lazy init object values that use {@link Supplier} passed in constructor to initialize value in {@link #init()} method. <br>
 * Class is extending {@link LazyValueAbstract}
 *
 * @param <T> type of lazy init object.
 *
 * @see LazyValueAbstract
 */
@SuppressWarnings("ClassHasNoToStringMethod")
public class LazyValue<T> extends LazyValueAbstract<T>
{
    /**
     * supplier used by {@link #init()} method.
     */
    protected final Supplier<T> supplier;

    /**
     * Construct new LazyValue with given supplier for value.
     *
     * @param supplier supplier used to initialize value in {@link #init()} method.
     */
    public LazyValue(final Supplier<T> supplier)
    {
        Validate.notNull(supplier, "supplier can't be null!");
        this.supplier = supplier;
    }

    /**
     * Construct new ByteLazyValue with given supplier for value.
     *
     * @param collection created instance will be added to this list.
     * @param supplier   supplier used to initialize value in {@link #init()} method.
     */
    public LazyValue(final Collection<? super LazyValue<T>> collection, final Supplier<T> supplier)
    {
        this.supplier = supplier;
        collection.add(this);
    }

    @Override
    protected T init()
    {
        return this.supplier.get();
    }
}
