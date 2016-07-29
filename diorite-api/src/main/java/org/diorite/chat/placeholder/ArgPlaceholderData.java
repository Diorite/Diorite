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

package org.diorite.chat.placeholder;

import java.util.function.Supplier;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

class ArgPlaceholderData<T> implements PlaceholderData<T>
{
    protected final String             fullName;
    protected final String             objectName;
    protected final PlaceholderItem<T> item;
    protected final Object[]           args;
    protected final boolean            containsSubPlaceholders;

    // TODO: how to parse and use nested placeholders?
    ArgPlaceholderData(final String fullName, final String objectName, final PlaceholderItem<T> item, final Object[] args)
    {
        this.fullName = fullName.intern();
        this.objectName = objectName.intern();
        this.item = item;
        this.args = args;
        for (final Object arg : args)
        {
            if (arg instanceof Supplier)
            {
                this.containsSubPlaceholders = true;
                return;
            }
        }
        this.containsSubPlaceholders = false;
    }

    ArgPlaceholderData(final String fullName, final String objectName, final PlaceholderItem<T> item, final Object[] args, final boolean containsSubPlaceholders)
    {
        this.fullName = fullName.intern();
        this.objectName = objectName.intern();
        this.item = item;
        this.args = args;
        this.containsSubPlaceholders = containsSubPlaceholders;
    }

    @Override
    public String getFullName()
    {
        return this.fullName;
    }

    @Override
    public String getObjectName()
    {
        return this.objectName;
    }

    @Override
    public PlaceholderItem<T> getItem()
    {
        return this.item;
    }

    @Override
    public Object[] getArguments()
    {
        return this.args;
    }

    @Override
    public boolean containsSubPlaceholders()
    {
        return this.containsSubPlaceholders;
    }

    @Override
    public boolean equals(final Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (! (o instanceof ArgPlaceholderData))
        {
            return false;
        }

        final ArgPlaceholderData<?> that = (ArgPlaceholderData<?>) o;
        return (this.containsSubPlaceholders == that.containsSubPlaceholders) && this.fullName.equals(that.fullName);
    }

    @Override
    public int hashCode()
    {
        int result = this.fullName.hashCode();
        result = (31 * result) + (this.containsSubPlaceholders ? 1 : 0);
        return result;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("fullName", this.fullName).append("objectName", this.objectName).append("item", this.item).append("args", this.args).append("containsSubPlaceholders", this.containsSubPlaceholders).toString();
    }
}
