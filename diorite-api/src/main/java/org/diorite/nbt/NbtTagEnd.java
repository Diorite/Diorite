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

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Represent nbt tag element, placed at the end of nbt container etc..
 */
public class NbtTagEnd extends NbtAbstractTag
{
    private static final long serialVersionUID = 0;

    /**
     * Construct new NbtTagEnd.
     */
    public NbtTagEnd()
    {
    }

    /**
     * Clone constructor.
     *
     * @param nbtTagEnd tag to be cloned.
     */
    protected NbtTagEnd(final NbtTagEnd nbtTagEnd)
    {
        super(nbtTagEnd);
    }

    @Override
    public Void getNBTValue()
    {
        return null;
    }

    @Override
    public NbtTagType getTagType()
    {
        return NbtTagType.END;
    }

    @SuppressWarnings("CloneDoesntCallSuperClone")
    @Override
    public NbtTagEnd clone()
    {
        return new NbtTagEnd(this);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).toString();
    }
}