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

import org.diorite.core.DioriteCore;
import org.diorite.core.material.InternalBlockRegistry;
import org.diorite.core.material.InternalBlockType;
import org.diorite.core.protocol.AbstractPacketDataSerializer;
import org.diorite.material.Blocks;

public interface Palette
{
    Palette getNext();

    default InternalBlockRegistry getRegistry()
    {
        return DioriteCore.getDiorite().getBlockRegistry();
    }

//    void clear();

    int put(int minecraftIdAndData); // returns -1 if id failed to add as pattern can't fit more ids

    default int put(int minecraftID, byte minecraftData)
    {
        return this.put(((minecraftID << 4) | minecraftData));
    }

    default int put(InternalBlockType data)
    {
        return this.put(data.getMinecraftIdAndData());
    }

    int getAsInt(int sectionID);

    default InternalBlockType get(int sectionID)
    {
        int data = this.getAsInt(sectionID);
        InternalBlockType mat = this.getRegistry().getByMinecraftIdAndData(data);
        if (mat == null)
        {
            return (InternalBlockType) Blocks.AIR;
        }
        return mat;
    }

    int size();

    int bitsPerBlock();

    int byteSize();

    void write(AbstractPacketDataSerializer data);
}
