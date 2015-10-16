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

package org.diorite.utils.collections.maps;

import java.util.Map;

import org.diorite.utils.SimpleEnum;
import org.diorite.utils.collections.hash.SimpleEnumHashingStrategy;

import gnu.trove.map.hash.TCustomHashMap;

/**
 * Map with simple enum objects as keys.
 *
 * @param <K> simple enum key type.
 * @param <V> value type.
 */
public class SimpleEnumMap<K extends SimpleEnum<K>, V> extends TCustomHashMap<K, V>
{
    private static final long serialVersionUID = 0;

    /**
     * Construct new SimpleEnumMap.
     */
    public SimpleEnumMap()
    {
        super(SimpleEnumHashingStrategy.INSTANCE);
    }

    /**
     * Construct new SimpleEnumMap.
     *
     * @param initialCapacity initial capacity of map.
     */
    public SimpleEnumMap(final int initialCapacity)
    {
        super(SimpleEnumHashingStrategy.INSTANCE, initialCapacity);
    }

    /**
     * Construct new SimpleEnumMap.
     *
     * @param initialCapacity initial capacity of map.
     * @param loadFactor      load factor of map.
     */
    public SimpleEnumMap(final int initialCapacity, final float loadFactor)
    {
        super(SimpleEnumHashingStrategy.INSTANCE, initialCapacity, loadFactor);
    }

    /**
     * Construct new SimpleEnumMap.
     *
     * @param map map to copy.
     */
    public SimpleEnumMap(final TCustomHashMap<? extends K, ? extends V> map)
    {
        super(SimpleEnumHashingStrategy.INSTANCE, map);
    }

    /**
     * Construct new SimpleEnumMap.
     *
     * @param map map to copy.
     */
    public SimpleEnumMap(final Map<? extends K, ? extends V> map)
    {
        super(SimpleEnumHashingStrategy.INSTANCE, map);
    }
}
