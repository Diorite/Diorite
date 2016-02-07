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

package org.diorite.impl.world.chunk.palette;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.connection.packets.PacketDataSerializer;
import org.diorite.material.BlockMaterialData;

public class PaletteImpl implements Palette
{
    private PaletteData palette = new ArrayPaletteImpl();

    public PaletteImpl()
    {
    }

    @Override
    public Palette getNext()
    {
        this.palette = this.palette.getNext();

        return this;
    }

    @Override
    public int put(final int minecraftIDandData)
    {
        int k = this.palette.put(minecraftIDandData);
        if (k == - 1)
        {
            this.getNext();
            k = this.palette.put(minecraftIDandData);
        }
        return k;
    }

    @Override
    public int put(final int minecraftID, final byte minecafrData)
    {
        return this.palette.put(minecraftID, minecafrData);
    }

    @Override
    public int put(final BlockMaterialData data)
    {
        return this.palette.put(data);
    }

    @Override
    public BlockMaterialData get(final int sectionID)
    {
        return this.palette.get(sectionID);
    }

    @Override
    public int getAsInt(final int sectionID)
    {
        return this.palette.getAsInt(sectionID);
    }

    @Override
    public int size()
    {
        return this.palette.size();
    }

    @Override
    public int bitsPerBlock()
    {
        return this.palette.bitsPerBlock();
    }

    @Override
    public int byteSize()
    {
        return this.palette.byteSize();
    }

    @Override
    public void write(final PacketDataSerializer data)
    {
        data.writeByte(this.bitsPerBlock());
        this.palette.write(data);
    }

    public void read(final PacketDataSerializer data)
    {
        final int bpb = data.readUnsignedByte();
        if (bpb == 0)
        {
            this.palette = GlobalPaletteImpl.get();
        }
        if (bpb <= 4)
        {
            this.palette = new ArrayPaletteImpl();
        }
        else
        {
            this.palette = new MapPaletteImpl();
        }
        this.palette.read(data);
    }

    private PaletteImpl(final PaletteData data)
    {
        this.palette = data;
    }

    @Override
    public PaletteImpl clone()
    {
        return new PaletteImpl(this.palette.clone());
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("palette", this.palette).toString();
    }

}
