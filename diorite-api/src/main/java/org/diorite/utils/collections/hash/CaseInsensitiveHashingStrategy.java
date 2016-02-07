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

package org.diorite.utils.collections.hash;

import it.unimi.dsi.fastutil.Hash.Strategy;

/**
 * Case insensitive hashing strategy.
 */
public class CaseInsensitiveHashingStrategy implements Strategy<String>
{
    private static final long serialVersionUID = 0;

    /**
     * Protected constructor, use {@link #INSTANCE} to get instance.
     */
    protected CaseInsensitiveHashingStrategy()
    {
    }

    /**
     * Static instance of this hashing strategy.
     */
    public static final CaseInsensitiveHashingStrategy INSTANCE = new CaseInsensitiveHashingStrategy();

    @Override
    public int hashCode(final String s)
    {
        return s.toLowerCase().hashCode();
    }

    @Override
    public boolean equals(final String s1, final String s2)
    {
        return (s1.equals(s2)) || (((s2 != null)) && (s1.toLowerCase().equals(s2.toLowerCase())));
    }
}
