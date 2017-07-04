/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017. Diorite (by Bartłomiej Mazur (aka GotoFinal))
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

package org.diorite.commons.arrays.fastutil;

import java.util.NoSuchElementException;

import it.unimi.dsi.fastutil.chars.AbstractCharBidirectionalIterator;

class CharIterator extends AbstractCharBidirectionalIterator
{
    private final char[] array;
    private int pos = 0;

    CharIterator(char[] primitiveArray)
    {
        this.array = primitiveArray;
    }

    @Override
    public boolean hasNext()
    {
        return this.pos < this.array.length;
    }

    @Override
    public boolean hasPrevious()
    {
        return this.pos > 0;
    }

    @Override
    public char nextChar()
    {
        if (! this.hasNext())
        {
            throw new NoSuchElementException();
        }
        return this.array[this.pos++];
    }

    @Override
    public char previousChar()
    {
        if (! this.hasPrevious())
        {
            throw new NoSuchElementException();
        }
        return this.array[-- this.pos];
    }

    @Override
    public int back(int n)
    {
        if (n < 0)
        {
            return this.skip(- n);
        }
        int newPos = this.pos - n;
        if (newPos >= 0)
        {
            this.pos = newPos;
            return n;
        }
        else
        {
            int oldPos = this.pos;
            this.pos = 0;
            return oldPos;
        }
    }

    @Override
    public int skip(int n)
    {
        if (n < 0)
        {
            return this.back(- n);
        }
        int newPos = this.pos + n;
        if (newPos <= this.array.length)
        {
            this.pos = newPos;
            return n;
        }
        else
        {
            int oldPos = this.pos;
            this.pos = this.array.length;
            return this.pos - oldPos;
        }
    }
}
