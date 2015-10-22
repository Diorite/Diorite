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

package org.diorite.nbt;

/**
 * NBT abstract type for numeric values.
 */
public abstract class NbtAbstractTagNumber extends NbtAbstractTag
{
    /**
     * Construct new NbtTagInt without name and 0 as value.
     */
    public NbtAbstractTagNumber()
    {
    }

    /**
     * Construct new NbtTagByte with given name and 0 as value.
     *
     * @param name name to be used.
     */
    public NbtAbstractTagNumber(final String name)
    {
        super(name);
    }

    /**
     * Clone constructor.
     *
     * @param nbtTagInt tag to be cloned.
     */
    protected NbtAbstractTagNumber(final NbtAbstractTagNumber nbtTagInt)
    {
        super(nbtTagInt);
    }

    /**
     * Returns value of this nbt tag.
     *
     * @return value of this nbt tag.
     */
    public abstract Number getNumberValue();

    /**
     * Set value of this nbt tag.
     *
     * @param i value to set.
     */
    public abstract void setNumberValue(final Number i);

    @Override
    public abstract Number getNBTValue();

    @Override
    public abstract NbtAbstractTagNumber clone();
}