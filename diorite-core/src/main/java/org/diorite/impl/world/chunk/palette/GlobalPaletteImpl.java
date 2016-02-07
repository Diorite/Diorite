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

import org.diorite.impl.connection.packets.PacketDataSerializer;
import org.diorite.material.BlockMaterialData;
import org.diorite.material.Material;

public class GlobalPaletteImpl implements PaletteData
{
    private static final   GlobalPaletteImpl inst                   = new GlobalPaletteImpl();
    protected static final int               DEFAULT_BITS_PER_BLOCK = 13;

    private GlobalPaletteImpl()
    {
    }

    @Override
    public void read(final PacketDataSerializer data)
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
    public int put(final int minecraftIDandData)
    {
        return minecraftIDandData;
    }

    @Override
    public int put(final int minecraftID, final byte minecafrData)
    {
        return (minecraftID << 4) | minecafrData;
    }

    @Override
    public int put(final BlockMaterialData data)
    {
        return (data.getId() << 4) | data.getType();
    }

    @Override
    public int getAsInt(final int sectionID)
    {
        return sectionID;
    }

    @Override
    public BlockMaterialData get(final int sectionID)
    {
        final BlockMaterialData data = (BlockMaterialData) BlockMaterialData.getByID(sectionID >> 4, sectionID & 15);
        if (data == null)
        {
            return Material.AIR;
        }
        return data;
    }

    @Override
    public int size()
    {
        return Material.getAllItemMaterialsCount();
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
    public void write(final PacketDataSerializer data)
    {
        data.writeVarInt(0);
    }
}
