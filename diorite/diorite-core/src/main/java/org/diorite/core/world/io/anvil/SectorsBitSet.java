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

package org.diorite.core.world.io.anvil;

import java.util.BitSet;

@SuppressWarnings("ClassHasNoToStringMethod")
class SectorsBitSet extends BitSet
{
    private static final long serialVersionUID = 0;

    private int largestIndex;

    SectorsBitSet()
    {
    }

    SectorsBitSet(int nbits)
    {
        super(nbits);
    }

    private void checkLargest(int index)
    {
        if (index >= this.largestIndex)
        {
            this.largestIndex = index + 1;
        }
    }

    public int getLargestIndex()
    {
        return this.largestIndex;
    }

    @Override
    public void flip(int bitIndex)
    {
        this.checkLargest(bitIndex);
        super.flip(bitIndex);
    }

    @Override
    public void flip(int fromIndex, int toIndex)
    {
        this.checkLargest(toIndex);
        super.flip(fromIndex, toIndex);
    }

    @Override
    public void set(int bitIndex)
    {
        this.checkLargest(bitIndex);
        super.set(bitIndex);
    }

    @Override
    public void set(int bitIndex, boolean value)
    {
        this.checkLargest(bitIndex);
        super.set(bitIndex, value);
    }

    @Override
    public void set(int fromIndex, int toIndex)
    {
        this.checkLargest(toIndex);
        super.set(fromIndex, toIndex);
    }

    @Override
    public void set(int fromIndex, int toIndex, boolean value)
    {
        this.checkLargest(toIndex);
        super.set(fromIndex, toIndex, value);
    }

    @Override
    public void clear(int bitIndex)
    {
        this.checkLargest(bitIndex);
        super.clear(bitIndex);
    }

    @Override
    public void clear(int fromIndex, int toIndex)
    {
        this.checkLargest(toIndex);
        super.clear(fromIndex, toIndex);
    }

    @Override
    public boolean get(int bitIndex)
    {
        this.checkLargest(bitIndex);
        return super.get(bitIndex);
    }

    @Override
    public BitSet get(int fromIndex, int toIndex)
    {
        this.checkLargest(toIndex);
        return super.get(fromIndex, toIndex);
    }
}
