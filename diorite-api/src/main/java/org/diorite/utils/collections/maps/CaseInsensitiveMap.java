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

import org.diorite.utils.collections.hash.CaseInsensitiveHashingStrategy;

import gnu.trove.map.hash.TCustomHashMap;

public class CaseInsensitiveMap<V> extends TCustomHashMap<String, V>
{
    private static final long serialVersionUID = 0;

    public CaseInsensitiveMap()
    {
        super(CaseInsensitiveHashingStrategy.INSTANCE);
    }

    public CaseInsensitiveMap(final int initialCapacity)
    {
        super(CaseInsensitiveHashingStrategy.INSTANCE, initialCapacity);
    }

    public CaseInsensitiveMap(final int initialCapacity, final float loadFactor)
    {
        super(CaseInsensitiveHashingStrategy.INSTANCE, initialCapacity, loadFactor);
    }

    public CaseInsensitiveMap(final TCustomHashMap<? extends String, ? extends V> map)
    {
        super(CaseInsensitiveHashingStrategy.INSTANCE, map);
    }

    public CaseInsensitiveMap(final Map<? extends String, ? extends V> map)
    {
        super(CaseInsensitiveHashingStrategy.INSTANCE, map);
    }
}
