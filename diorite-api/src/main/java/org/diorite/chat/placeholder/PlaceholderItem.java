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

/**
 * Represent single placeholder item, like "name" in player.name placeholder.
 *
 * @param <T> type of object needed to get data for placeholder.
 */
public interface PlaceholderItem<T>
{
    /**
     * Returns type of placeholder item, like that "player" in player.name.
     *
     * @return type of placeholder.
     */
    PlaceholderType<T> getType();

    /**
     * Returns id/name of placeholder item, like that "name" in player.name.
     *
     * @return id of placeholder item.
     */
    String getId();

    /**
     * Get data for this placeholder item as String or BaseComponent, like player.name should return here name of given player. <br>
     * If method return other object than String or BaseComponent it will be changed to string by {@link Object#toString()} method.
     *
     * @param obj  object to fetch the data needed for placeholder.
     * @param args arguments of item
     *
     * @return String of BaseComponent to use instead of placeholder.
     */
    Object apply(T obj, Object[] args);
}
