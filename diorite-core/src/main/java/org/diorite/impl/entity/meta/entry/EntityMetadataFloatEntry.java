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
public class EntityMetadataFloatEntry extends EntityMetadataEntry<Float>
{
    private float value;

    public EntityMetadataFloatEntry(final int index, final float value)
    {
        super(index);
        this.value = value;
    }

    public EntityMetadataFloatEntry(final int index, final double value)
    {
        super(index);
        this.value = (float) value;
    }

    public float getValue()
    {
        return this.value;
    }

    public void setValue(final float value)
    {
        this.value = value;
    }

    public void setValue(final double value)
    {
        this.value = (float) value;
    }

    @Override
    public EntityMetadataType getDataType()
    {
        return EntityMetadataType.FLOAT;
    }

    @Override
    public Float getData()
    {
        return this.value;
    }

    @Override
    public void setData(final Float data)
    {
        this.value = data;
    }
}