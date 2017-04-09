/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017. Diorite (by Bartłomiej Mazur (aka GotoFinal))
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

package org.diorite.commons.lazy;

import java.util.Collection;

import org.diorite.commons.function.supplier.FloatSupplier;

/**
 * Class to represent lazy init float values that use {@link FloatSupplier} passed in constructor to initialize value in {@link #init()} method. <br>
 * Class is extending {@link FloatLazyValueAbstract}
 *
 * @see FloatLazyValueAbstract
 */
@SuppressWarnings("ClassHasNoToStringMethod")
public class FloatLazyValue extends FloatLazyValueAbstract
{
    /**
     * supplier used by {@link #init()} method.
     */
    protected final FloatSupplier supplier;

    /**
     * Construct new FloatLazyValue with given supplier for value.
     *
     * @param supplier
     *         supplier used to initialize value in {@link #init()} method.
     */
    public FloatLazyValue(FloatSupplier supplier)
    {
        this.supplier = supplier;
    }

    /**
     * Construct new FloatLazyValue with given supplier for value.
     *
     * @param collection
     *         created instance will be added to this list.
     * @param supplier
     *         supplier used to initialize value in {@link #init()} method.
     */
    public FloatLazyValue(Collection<? super FloatLazyValue> collection, FloatSupplier supplier)
    {
        this.supplier = supplier;
        collection.add(this);
    }

    @Override
    protected float init()
    {
        return this.supplier.getAsFloat();
    }
}
