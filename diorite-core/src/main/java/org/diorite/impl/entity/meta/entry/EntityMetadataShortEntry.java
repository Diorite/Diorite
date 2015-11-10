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

package org.diorite.impl.entity.meta.entry;

import org.diorite.impl.entity.meta.EntityMetadataType;

@SuppressWarnings("ClassHasNoToStringMethod")
public class EntityMetadataShortEntry extends EntityMetadataEntry<Short>
{
    private short value;

    public EntityMetadataShortEntry(final int index, final short value)
    {
        super(index);
        this.value = value;
    }

    public EntityMetadataShortEntry(final int index, final int value)
    {
        super(index);
        this.value = (short) value;
    }

    public short getValue()
    {
        return this.value;
    }

    public void setValue(final short value)
    {
        this.value = value;
    }

    public void setValue(final int value)
    {
        this.value = (short) value;
    }

    @Override
    public EntityMetadataType getDataType()
    {
        return EntityMetadataType.SHORT;
    }

    @Override
    public Short getData()
    {
        return this.value;
    }

    @Override
    public void setData(final Short data)
    {
        this.value = data;
    }
}