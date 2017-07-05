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

package org.diorite.core.world.chunk.palette;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.commons.math.DioriteMathUtils;
import org.diorite.core.material.InternalBlockType;
import org.diorite.core.protocol.AbstractPacketDataSerializer;
import org.diorite.material.Blocks;

public class ArrayPaletteImpl implements PaletteData
{
    private static final int SIZE = 16;

    protected final InternalBlockType[] pattern;
    protected int lastIndex = 1;

    public ArrayPaletteImpl()
    {
        this.pattern = new InternalBlockType[SIZE];
        this.pattern[0] = (InternalBlockType) Blocks.AIR;
    }

    private ArrayPaletteImpl(InternalBlockType[] pattern)
    {
        this.pattern = pattern;
    }

    @Override
    public int put(InternalBlockType data)
    {
        InternalBlockType[] pattern = this.pattern;
        for (int i = 0; i < this.lastIndex; i++)
        {
            InternalBlockType materialData = pattern[i];
            if (materialData.getMinecraftIdAndData() == data.getMinecraftIdAndData())
            {
                return i;
            }
        }
        if (this.lastIndex >= pattern.length)
        {
            return - 1;
        }
        int index = this.lastIndex++;
        pattern[index] = data;
        return index;
    }

    @Override
    public int put(int minecraftIdAndData)
    {
        InternalBlockType[] pattern = this.pattern;
        for (int i = 0; i < this.lastIndex; i++)
        {
            InternalBlockType materialData = pattern[i];
            if (materialData.getMinecraftIdAndData() == minecraftIdAndData)
            {
                return i;
            }
        }
        if (this.lastIndex >= this.pattern.length)
        {
            return - 1;
        }
        InternalBlockType mat = this.getRegistry().getByMinecraftIdAndData(minecraftIdAndData);
        if (mat == null)
        {
            mat = this.getRegistry().getByMinecraftIdAndData(minecraftIdAndData >> 4, 0);
            if (mat == null)
            {
                mat = (InternalBlockType) Blocks.AIR;
//                throw new IllegalArgumentException("Unknown material: " + minecraftIDandData + " (" + (minecraftIDandData >> 4) + ":" + (minecraftIDandData
// & 15) + ")");
            }
        }
        int index = this.lastIndex++;
        pattern[index] = mat;
        return index;
    }

    @Override
    public int getAsInt(int sectionID)
    {
        InternalBlockType mat = this.pattern[sectionID];
        if (mat == null)
        {
            return 0;
        }
        return mat.getMinecraftIdAndData();
    }

    @Override
    public InternalBlockType get(int sectionID)
    {
        InternalBlockType mat = this.pattern[sectionID];
        if (mat == null)
        {
            return (InternalBlockType) Blocks.AIR;
        }
        return mat;
    }

    @Override
    public int size()
    {
        return this.lastIndex;
    }

    @Override
    public int bitsPerBlock()
    {
        return 4;
    }

    @Override
    public int byteSize()
    {
        int bytes = DioriteMathUtils.varintSize(this.lastIndex);
        for (int var2 = 0; var2 < this.lastIndex; ++ var2)
        {
            bytes += DioriteMathUtils.varintSize(this.pattern[var2].getMinecraftIdAndData());
        }
        return bytes;
    }

    @Override
    public void write(AbstractPacketDataSerializer data)
    {
        data.writeVarInt(this.lastIndex);
        for (InternalBlockType materialData : this.pattern)
        {
            if (materialData == null)
            {
                break;
            }
            data.writeVarInt(materialData.getMinecraftIdAndData());
        }
    }

    @SuppressWarnings("MagicNumber")
    @Override
    public void read(AbstractPacketDataSerializer data)
    {
        int size = data.readVarInt();
        for (int i = 0; i < size; i++)
        {
            int id = data.readVarInt();
            InternalBlockType mat = this.getRegistry().getByMinecraftIdAndData(id);
            if (mat == null)
            {
                throw new IllegalArgumentException("Unknown material: " + id + " (" + (id >> 4) + ":" + (id & 15) + ")");
            }
            this.pattern[i] = mat;
        }
    }

    @Override
    public PaletteData getNext()
    {
        return new MapPaletteImpl(this);
    }

    @Override
    public ArrayPaletteImpl clone()
    {
        return new ArrayPaletteImpl(this.pattern.clone());
    }

//    @Override
//    public void clear()
//    {
//        Arrays.fill(this.pattern, - 1);
//    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("pattern", this.pattern).toString();
    }
}
