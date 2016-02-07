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

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.utils.others.Resetable;

/**
 * Class to represent lazy init double values, lazy value is initialized on first {@link #get()} invoke by {@link #init()} method. <br>
 * Class also implements {@link Resetable} so cached value can be reset and new value will be created on next {@link #get()} method invoke.
 */
public abstract class DoubleLazyValueAbstract implements Resetable
{
    /**
     * Used to store cached value.
     */
    protected double  cached;
    /**
     * Determine if value was already initialized.
     */
    protected boolean isCached;

    /**
     * Construct new DoubleLazyValueAbstract object.
     */
    protected DoubleLazyValueAbstract()
    {
    }

    /**
     * Method that will return cached value or initialize new if value isn't cached yet.
     *
     * @return value of this lazy value.
     */
    public double get()
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
    protected abstract double init();

    /**
     * Returns true if value was already initialized.
     *
     * @return true if value was already initialized.
     */
    public boolean isCached()
    {
        return this.isCached;
    }

    @Override
    public void reset()
    {
        this.isCached = false;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("isCached", this.isCached).append("cached", this.cached).toString();
    }
}
