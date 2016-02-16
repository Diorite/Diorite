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

package org.diorite.utils.collections.maps;

import java.util.Map;

import org.diorite.utils.SimpleEnum;
import org.diorite.utils.collections.hash.SimpleEnumHashingStrategy;

import it.unimi.dsi.fastutil.Hash;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenCustomHashMap;

/**
 * Map with simple enum objects as keys.
 *
 * @param <K> simple enum key type.
 * @param <V> value type.
 */
@SuppressWarnings({"unchecked", "rawtypes"})
public class SimpleEnumMap<K extends SimpleEnum<K>, V> extends Object2ObjectOpenCustomHashMap<K, V>
{
    private static final long serialVersionUID = 0;

    /**
     * Creates a new hash map with initial expected {@link Hash#DEFAULT_INITIAL_SIZE} entries and {@link Hash#DEFAULT_LOAD_FACTOR} as load factor.
     */
    public SimpleEnumMap()
    {
        super((Strategy) SimpleEnumHashingStrategy.INSTANCE);
    }

    /**
     * Creates a new hash map.
     * <br>
     * <p>The actual table size will be the least power of two greater than <code>expected</code>/<code>f</code>.
     *
     * @param expected the expected number of elements in the hash set.
     * @param f        the load factor.
     */
    public SimpleEnumMap(final int expected, final float f)
    {
        super(expected, f, (Strategy) SimpleEnumHashingStrategy.INSTANCE);
    }

    /**
     * Creates a new hash map with {@link Hash#DEFAULT_LOAD_FACTOR} as load factor.
     *
     * @param expected the expected number of elements in the hash map.
     */
    public SimpleEnumMap(final int expected)
    {
        super(expected, (Strategy) SimpleEnumHashingStrategy.INSTANCE);
    }

    /**
     * Creates a new hash map copying a given one.
     *
     * @param m a {@link Map} to be copied into the new hash map.
     * @param f the load factor.
     */
    public SimpleEnumMap(final Map<? extends K, ? extends V> m, final float f)
    {
        super(m, f, (Strategy) SimpleEnumHashingStrategy.INSTANCE);
    }

    /**
     * Creates a new hash map with {@link Hash#DEFAULT_LOAD_FACTOR} as load factor copying a given one.
     *
     * @param m a {@link Map} to be copied into the new hash map.
     */
    public SimpleEnumMap(final Map<? extends K, ? extends V> m)
    {
        super(m, (Strategy) SimpleEnumHashingStrategy.INSTANCE);
    }

    /**
     * Creates a new hash map copying a given type-specific one.
     *
     * @param m a type-specific map to be copied into the new hash map.
     * @param f the load factor.
     */
    public SimpleEnumMap(final Object2ObjectMap<K, V> m, final float f)
    {
        super(m, f, (Strategy) SimpleEnumHashingStrategy.INSTANCE);
    }

    /**
     * Creates a new hash map with {@link Hash#DEFAULT_LOAD_FACTOR} as load factor copying a given type-specific one.
     *
     * @param m a type-specific map to be copied into the new hash map.
     */
    public SimpleEnumMap(final Object2ObjectMap<K, V> m)
    {
        super(m, (Strategy) SimpleEnumHashingStrategy.INSTANCE);
    }

    /**
     * Creates a new hash map using the elements of two parallel arrays.
     *
     * @param k the array of keys of the new hash map.
     * @param v the array of corresponding values in the new hash map.
     * @param f the load factor.
     *
     * @throws IllegalArgumentException if <code>k</code> and <code>v</code> have different lengths.
     */
    public SimpleEnumMap(final K[] k, final V[] v, final float f)
    {
        super(k, v, f, (Strategy) SimpleEnumHashingStrategy.INSTANCE);
    }

    /**
     * Creates a new hash map with {@link Hash#DEFAULT_LOAD_FACTOR} as load factor using the elements of two parallel arrays.
     *
     * @param k the array of keys of the new hash map.
     * @param v the array of corresponding values in the new hash map.
     *
     * @throws IllegalArgumentException if <code>k</code> and <code>v</code> have different lengths.
     */
    public SimpleEnumMap(final K[] k, final V[] v)
    {
        super(k, v, (Strategy) SimpleEnumHashingStrategy.INSTANCE);
    }
}
