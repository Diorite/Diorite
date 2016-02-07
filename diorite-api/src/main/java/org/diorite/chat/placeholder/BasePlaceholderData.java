/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 Diorite (by Bartłomiej Mazur (aka GotoFinal))
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

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Represent simple placeholder found in string, it contains full string to replace, object name and placeholder item.
 *
 * @param <T> type of placeholder item.
 */
class BasePlaceholderData<T> implements PlaceholderData<T>
{
    static final Map<String, PlaceholderData<?>> cache = new HashMap<>(100, .2f);

    protected final String             fullName;
    protected final String             objectName;
    protected final PlaceholderItem<T> item;

    BasePlaceholderData(final String fullName, final String objectName, final PlaceholderItem<T> item)
    {
        this.fullName = fullName.intern();
        this.objectName = objectName.intern();
        this.item = item;
    }

    @Override
    public String getFullName()
    {
        return (this.fullName == null) ? this.item.getType().getId() : this.fullName;
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
    public boolean equals(final Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (! (o instanceof BasePlaceholderData))
        {
            return false;
        }

        final BasePlaceholderData<?> that = (BasePlaceholderData<?>) o;

        return this.fullName.equals(that.fullName);
    }

    @Override
    public int hashCode()
    {
        return this.fullName.hashCode();
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("fullName", this.fullName).append("type", this.item.getType().getType().getName()).toString();
    }
}
