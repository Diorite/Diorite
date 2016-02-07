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

import java.util.function.Function;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Represent single placeholder item, like "name" in player.name placeholder.
 *
 * @param <T> type of object needed to get data for placeholder.
 */
class BasePlaceholderItem<T> implements PlaceholderItem<T>
{
    protected final PlaceholderType<T>  type;
    protected final String              id;
    protected final Function<T, Object> func;

    /**
     * Construct new placeholder item, using given type and functiom.
     *
     * @param type type of placeholder, like that "player" in player.name.
     * @param id   id/name of placeholder, like that "name" in player.name.
     * @param func function that should return {@link String} or {@link org.diorite.chat.component.BaseComponent}, when using BaseComponent you may add click events, hovers events and all that stuff.
     */
    BasePlaceholderItem(final PlaceholderType<T> type, final String id, final Function<T, Object> func)
    {
        this.type = type;
        this.id = id.intern();
        this.func = func;
    }

    @Override
    public PlaceholderType<T> getType()
    {
        return this.type;
    }

    @Override
    public String getId()
    {
        return this.id;
    }

    @Override
    public Object apply(final T obj, final Object[] args)
    {
        return this.func.apply(obj);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("type", this.type).append("id", this.id).toString();
    }
}
