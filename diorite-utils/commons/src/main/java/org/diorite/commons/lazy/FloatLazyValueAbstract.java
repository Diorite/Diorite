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

import org.apache.commons.lang3.builder.ToStringBuilder;

import org.diorite.commons.objects.Resettable;

/**
 * Class to represent lazy init float values, lazy value is initialized on first {@link #get()} invoke by {@link #init()} method. <br>
 * Class also implements {@link Resettable} so cached value can be reset and new value will be created on next {@link #get()} method invoke.
 */
public abstract class FloatLazyValueAbstract extends AbstractLazyValue
{
    /**
     * Used to store cached value.
     */
    protected float cached;

    /**
     * Construct new FloatLazyValueAbstract object.
     */
    protected FloatLazyValueAbstract()
    {
    }

    /**
     * Method that will return cached value or initialize new if value isn't cached yet.
     *
     * @return value of this lazy value.
     */
    public float get()
    {
        if (this.isCached)
        {
            return this.cached;
        }
        else
        {
            this.cached = this.init();
            this.isCached = true;
            return this.cached;
        }
    }

    /**
     * Method that should return new value to cache. <br>
     * Invoked in {@link #get()} method when value isn't cached/initialized yet.
     *
     * @return new value to cache.
     */
    protected abstract float init();

    @Override
    public String toString()
    {
        return new ToStringBuilder(this).appendSuper(super.toString()).append("cached", this.cached).toString();
    }
}
