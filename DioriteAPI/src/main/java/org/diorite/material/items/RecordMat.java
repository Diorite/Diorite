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

package org.diorite.material.items;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.Sound;
import org.diorite.material.ItemMaterialData;

public abstract class RecordMat extends ItemMaterialData
{
    protected final Sound sound;

    protected RecordMat(final String recordName, final int id)
    {
        super("RECORD_" + recordName.toUpperCase(), id, "minecraft:record_" + recordName.toLowerCase(), 1, recordName.toUpperCase(), (short) 0x00);
        this.sound = Sound.getByEnumName("RECORD_" + recordName.toUpperCase());
    }

    protected RecordMat(final String enumName, final int id, final String minecraftId, final String typeName, final short type, final Sound sound)
    {
        super(enumName, id, minecraftId, 1, typeName, type);
        this.sound = sound;
    }

    protected RecordMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final short type, final Sound sound)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type);
        this.sound = sound;
    }

    public Sound getSound()
    {
        return this.sound;
    }

    @Override
    public abstract RecordMat getType(final int type);

    @Override
    public abstract RecordMat getType(final String type);

    @Override
    public abstract RecordMat[] types();

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("sound", this.sound).toString();
    }
}
