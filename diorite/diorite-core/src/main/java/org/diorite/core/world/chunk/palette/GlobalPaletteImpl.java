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

import org.diorite.core.material.InternalBlockType;
import org.diorite.core.protocol.AbstractPacketDataSerializer;
import org.diorite.material.Blocks;

public final class GlobalPaletteImpl implements PaletteData
{
    private static final GlobalPaletteImpl inst                   = new GlobalPaletteImpl();
    private static final int               DEFAULT_BITS_PER_BLOCK = 13;

    private GlobalPaletteImpl()
    {
    }

    @Override
    public void read(AbstractPacketDataSerializer data)
    {
        data.readVarInt();
    }

    public static GlobalPaletteImpl get()
    {
        return inst;
    }

    @Override
    public PaletteData getNext()
    {
        throw new IllegalArgumentException("This is last pattern mode.");
    }

    @Override
    public PaletteData clone()
    {
        return this; // no need to clone
    }

    @Override
    public int put(int minecraftIDandData)
    {
        return minecraftIDandData;
    }

    @Override
    public int put(int minecraftID, byte minecraftData)
    {
        return (minecraftID << 4) | minecraftData;
    }

    @Override
    public int put(InternalBlockType data)
    {
        return data.getMinecraftIdAndData();
    }

    @Override
    public int getAsInt(int sectionID)
    {
        return sectionID;
    }

    @Override
    public InternalBlockType get(int sectionID)
    {
        InternalBlockType data = this.getRegistry().getByMinecraftIdAndData(sectionID);
        if (data == null)
        {
            return (InternalBlockType) Blocks.AIR;
        }
        return data;
    }

    @Override
    public int size()
    {
        return this.getRegistry().size();
    }

    @Override
    public int byteSize()
    {
        return 1;
    }

    @Override
    public int bitsPerBlock()
    {
        return DEFAULT_BITS_PER_BLOCK;
    }

    @Override
    public void write(AbstractPacketDataSerializer data)
    {
        data.writeVarInt(0);
    }
}
