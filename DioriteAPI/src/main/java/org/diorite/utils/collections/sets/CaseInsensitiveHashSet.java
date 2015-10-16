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

package org.diorite.utils.collections.sets;

import java.util.Collection;

import org.diorite.utils.collections.hash.CaseInsensitiveHashingStrategy;

import gnu.trove.set.hash.TCustomHashSet;

/**
 * Case insensitive Hash set for strings.
 */
public class CaseInsensitiveHashSet extends TCustomHashSet<String>
{
    private static final long serialVersionUID = 0;

    /**
     * Construct new CaseInsensitiveHashSet.
     */
    public CaseInsensitiveHashSet()
    {
        super(CaseInsensitiveHashingStrategy.INSTANCE);
    }

    /**
     * Construct new CaseInsensitiveHashSet.
     *
     * @param initialCapacity initial capacity of set.
     * @param loadFactor      load factor of set.
     */
    public CaseInsensitiveHashSet(final int initialCapacity, final float loadFactor)
    {
        super(CaseInsensitiveHashingStrategy.INSTANCE, initialCapacity, loadFactor);
    }

    /**
     * Construct new CaseInsensitiveHashSet.
     *
     * @param initialCapacity initial capacity of set.
     */
    public CaseInsensitiveHashSet(final int initialCapacity)
    {
        super(CaseInsensitiveHashingStrategy.INSTANCE, initialCapacity);
    }

    /**
     * Construct new CaseInsensitiveHashSet.
     *
     * @param collection collection to copy as set.
     */
    public CaseInsensitiveHashSet(final Collection<? extends String> collection)
    {
        super(CaseInsensitiveHashingStrategy.INSTANCE, collection);
    }
}
