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

package org.diorite.utils.collections.comparators;

import java.io.Serializable;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Comparator to sort map by values instead of keys.
 * <pre>
 * {@code
 * Map<K, V> map = new HashMap<>();
 * // elements
 * SortedMap<K, V> sortedMap = new TreeMap<>(new ByValueComparator<K, V>(map));
 * }
 * </pre>
 *
 * @param <U> type of map key.
 * @param <T> type of map value.
 */
public class ByValueComparator<U extends Comparable<U>, T extends Comparable<T>> implements Comparator<U>, Serializable
{
    private static final long serialVersionUID = 0;
    /**
     * Sort order of comparator.
     */
    protected final boolean   ascend;
    /**
     * Reference to map.
     */
    protected final Map<U, T> map;

    /**
     * Construct new comparator with empty hashmap.
     *
     * @param ascend sort order.
     */
    public ByValueComparator(final boolean ascend)
    {
        this.ascend = ascend;
        this.map = new HashMap<>(32);
    }

    /**
     * Construct new comparator for given map.
     *
     * @param map map that you want sort by values.
     */
    public ByValueComparator(final Map<U, T> map)
    {
        this.map = map;
        this.ascend = true;
    }

    /**
     * Construct new comparator for given map.
     *
     * @param map    map that you want sort by values.
     * @param ascend sort order.
     */
    public ByValueComparator(final Map<U, T> map, final boolean ascend)
    {
        this.map = map;
        this.ascend = ascend;
    }

    /**
     * Returns map used by this comparator.
     *
     * @return map used by this comparator.
     */
    public Map<U, T> getMap()
    {
        return this.map;
    }

    @Override
    public int compare(final U o1, final U o2)
    {
        final int result = this.ascend ? this.map.get(o1).compareTo(this.map.get(o2)) : this.map.get(o2).compareTo(this.map.get(o1));
        if (result != 0)
        {
            return result;
        }
        return this.ascend ? o1.compareTo(o2) : o2.compareTo(o1);
    }

    @Override
    public ByValueComparator<U, T> reversed()
    {
        return new ByValueComparator<>(this.map, ! this.ascend);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("ascend", this.ascend).toString();
    }
}
