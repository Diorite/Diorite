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

package org.diorite.utils.collections.sets;

import java.util.Collection;
import java.util.Iterator;

import org.diorite.utils.collections.hash.CaseInsensitiveHashingStrategy;

import it.unimi.dsi.fastutil.Hash;
import it.unimi.dsi.fastutil.objects.ObjectCollection;
import it.unimi.dsi.fastutil.objects.ObjectOpenCustomHashSet;

/**
 * Case insensitive Hash set for strings.
 */
public class CaseInsensitiveHashSet extends ObjectOpenCustomHashSet<String>
{
    private static final long serialVersionUID = 0;

    /**
     * Creates a new hash set.
     * <br>
     * <p>The actual table size will be the least power of two greater than <code>expected</code>/<code>f</code>.
     *
     * @param expected the expected number of elements in the hash set.
     * @param f        the load factor.
     */
    public CaseInsensitiveHashSet(final int expected, final float f)
    {
        super(expected, f, CaseInsensitiveHashingStrategy.INSTANCE);
    }

    /**
     * Creates a new hash set with {@link Hash#DEFAULT_LOAD_FACTOR} as load factor.
     *
     * @param expected the expected number of elements in the hash set.
     */
    public CaseInsensitiveHashSet(final int expected)
    {
        super(expected, CaseInsensitiveHashingStrategy.INSTANCE);
    }

    /**
     * Creates a new hash set with initial expected {@link Hash#DEFAULT_INITIAL_SIZE} elements and {@link Hash#DEFAULT_LOAD_FACTOR} as load factor.
     */
    public CaseInsensitiveHashSet()
    {
        super(CaseInsensitiveHashingStrategy.INSTANCE);
    }

    /**
     * Creates a new hash set copying a given collection.
     *
     * @param c a {@link Collection} to be copied into the new hash set.
     * @param f the load factor.
     */
    public CaseInsensitiveHashSet(final Collection<? extends String> c, final float f)
    {
        super(c, f, CaseInsensitiveHashingStrategy.INSTANCE);
    }

    /**
     * Creates a new hash set with {@link Hash#DEFAULT_LOAD_FACTOR} as load factor copying a given collection.
     *
     * @param c a {@link Collection} to be copied into the new hash set.
     */
    public CaseInsensitiveHashSet(final Collection<? extends String> c)
    {
        super(c, CaseInsensitiveHashingStrategy.INSTANCE);
    }

    /**
     * Creates a new hash set copying a given type-specific collection.
     *
     * @param c a type-specific collection to be copied into the new hash set.
     * @param f the load factor.
     */
    public CaseInsensitiveHashSet(final ObjectCollection<? extends String> c, final float f)
    {
        super(c, f, CaseInsensitiveHashingStrategy.INSTANCE);
    }

    /**
     * Creates a new hash set with {@link Hash#DEFAULT_LOAD_FACTOR} as load factor copying a given type-specific collection.
     *
     * @param c a type-specific collection to be copied into the new hash set.
     */
    public CaseInsensitiveHashSet(final ObjectCollection<? extends String> c)
    {
        super(c, CaseInsensitiveHashingStrategy.INSTANCE);
    }

    /**
     * Creates a new hash set using elements provided by a type-specific iterator.
     *
     * @param i a type-specific iterator whose elements will fill the set.
     * @param f the load factor.
     */
    public CaseInsensitiveHashSet(final Iterator<? extends String> i, final float f)
    {
        super(i, f, CaseInsensitiveHashingStrategy.INSTANCE);
    }

    /**
     * Creates a new hash set with {@link Hash#DEFAULT_LOAD_FACTOR} as load factor using elements provided by a type-specific iterator.
     *
     * @param i a type-specific iterator whose elements will fill the set.
     */
    public CaseInsensitiveHashSet(final Iterator<? extends String> i)
    {
        super(i, CaseInsensitiveHashingStrategy.INSTANCE);
    }

    /**
     * Creates a new hash set and fills it with the elements of a given array.
     *
     * @param a      an array whose elements will be used to fill the set.
     * @param offset the first element to use.
     * @param length the number of elements to use.
     * @param f      the load factor.
     */
    public CaseInsensitiveHashSet(final String[] a, final int offset, final int length, final float f)
    {
        super(a, offset, length, f, CaseInsensitiveHashingStrategy.INSTANCE);
    }

    /**
     * Creates a new hash set with {@link Hash#DEFAULT_LOAD_FACTOR} as load factor and fills it with the elements of a given array.
     *
     * @param a      an array whose elements will be used to fill the set.
     * @param offset the first element to use.
     * @param length the number of elements to use.
     */
    public CaseInsensitiveHashSet(final String[] a, final int offset, final int length)
    {
        super(a, offset, length, CaseInsensitiveHashingStrategy.INSTANCE);
    }

    /**
     * Creates a new hash set copying the elements of an array.
     *
     * @param a an array to be copied into the new hash set.
     * @param f the load factor.
     */
    public CaseInsensitiveHashSet(final String[] a, final float f)
    {
        super(a, f, CaseInsensitiveHashingStrategy.INSTANCE);
    }

    /**
     * Creates a new hash set with {@link Hash#DEFAULT_LOAD_FACTOR} as load factor copying the elements of an array.
     *
     * @param a an array to be copied into the new hash set.
     */
    public CaseInsensitiveHashSet(final String[] a)
    {
        super(a, CaseInsensitiveHashingStrategy.INSTANCE);
    }
}
