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

import org.diorite.utils.collections.hash.CaseInsensitiveHashingStrategy;

import it.unimi.dsi.fastutil.Hash;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenCustomHashMap;

/**
 * Case Insensitive string map.
 *
 * @param <V> type of values.
 */
public class CaseInsensitiveMap<V> extends Object2ObjectOpenCustomHashMap<String, V>
{
    private static final long serialVersionUID = 0;

    /**
     * Creates a new hash map with initial expected {@link Hash#DEFAULT_INITIAL_SIZE} entries and {@link Hash#DEFAULT_LOAD_FACTOR} as load factor.
     */
    public CaseInsensitiveMap()
    {
        super(CaseInsensitiveHashingStrategy.INSTANCE);
    }

    /**
     * Creates a new hash map.
     * <br>
     * <p>The actual table size will be the least power of two greater than <code>expected</code>/<code>f</code>.
     *
     * @param expected the expected number of elements in the hash set.
     * @param f        the load factor.
     */
    public CaseInsensitiveMap(final int expected, final float f)
    {
        super(expected, f, CaseInsensitiveHashingStrategy.INSTANCE);
    }

    /**
     * Creates a new hash map with {@link Hash#DEFAULT_LOAD_FACTOR} as load factor.
     *
     * @param expected the expected number of elements in the hash map.
     */
    public CaseInsensitiveMap(final int expected)
    {
        super(expected, CaseInsensitiveHashingStrategy.INSTANCE);
    }

    /**
     * Creates a new hash map copying a given one.
     *
     * @param m a {@link Map} to be copied into the new hash map.
     * @param f the load factor.
     */
    public CaseInsensitiveMap(final Map<? extends String, ? extends V> m, final float f)
    {
        super(m, f, CaseInsensitiveHashingStrategy.INSTANCE);
    }

    /**
     * Creates a new hash map with {@link Hash#DEFAULT_LOAD_FACTOR} as load factor copying a given one.
     *
     * @param m a {@link Map} to be copied into the new hash map.
     */
    public CaseInsensitiveMap(final Map<? extends String, ? extends V> m)
    {
        super(m, CaseInsensitiveHashingStrategy.INSTANCE);
    }

    /**
     * Creates a new hash map copying a given type-specific one.
     *
     * @param m a type-specific map to be copied into the new hash map.
     * @param f the load factor.
     */
    public CaseInsensitiveMap(final Object2ObjectMap<String, V> m, final float f)
    {
        super(m, f, CaseInsensitiveHashingStrategy.INSTANCE);
    }

    /**
     * Creates a new hash map with {@link Hash#DEFAULT_LOAD_FACTOR} as load factor copying a given type-specific one.
     *
     * @param m a type-specific map to be copied into the new hash map.
     */
    public CaseInsensitiveMap(final Object2ObjectMap<String, V> m)
    {
        super(m, CaseInsensitiveHashingStrategy.INSTANCE);
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
    public CaseInsensitiveMap(final String[] k, final V[] v, final float f)
    {
        super(k, v, f, CaseInsensitiveHashingStrategy.INSTANCE);
    }

    /**
     * Creates a new hash map with {@link Hash#DEFAULT_LOAD_FACTOR} as load factor using the elements of two parallel arrays.
     *
     * @param k the array of keys of the new hash map.
     * @param v the array of corresponding values in the new hash map.
     *
     * @throws IllegalArgumentException if <code>k</code> and <code>v</code> have different lengths.
     */
    public CaseInsensitiveMap(final String[] k, final V[] v)
    {
        super(k, v, CaseInsensitiveHashingStrategy.INSTANCE);
    }
}
