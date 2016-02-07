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

package org.diorite.world.bag;

import org.diorite.material.BlockMaterialData;

import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.shorts.Short2ShortMap;
import it.unimi.dsi.fastutil.shorts.Short2ShortOpenHashMap;

public class SimpleBlockBag implements BlockBag
{
    private final Long2ObjectMap<ChunkBag> changes; // TODO, ugh

    public SimpleBlockBag(final int size)
    {
        this.changes = new Long2ObjectOpenHashMap<>(size);
    }

    @Override
    public BlockMaterialData getBlock(final int x, final int y, final int z)
    {
        return null;
    }

    @Override
    public void setBlock(final int x, final int y, final int z, final BlockMaterialData material)
    {

    }

    private final class ChunkBag
    {
        private final Short2ShortMap changes;

        ChunkBag(final int size)
        {
            this.changes = new Short2ShortOpenHashMap(size);
        }

        BlockMaterialData getBlock(int x, int y, int z)
        {
//            return changes.get()
            return null;
        }
    }
}
