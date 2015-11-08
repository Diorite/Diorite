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

package org.diorite.impl.world.tick;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.world.chunk.ChunkImpl;
import org.diorite.impl.world.chunk.ChunkManagerImpl;
import org.diorite.utils.math.endian.BigEndianUtils;
import org.diorite.world.World;

import gnu.trove.iterator.TLongIterator;

public class ChunkTickGroup implements TickGroupImpl
{
    private final ChunkGroup chunks;

    public ChunkTickGroup(final ChunkGroup chunks)
    {
        this.chunks = chunks;
    }

    @Override
    public void doTick(final int tps)
    {
        if (! this.chunks.isLoaded())
        {
            return;
        }
        final ChunkManagerImpl cm = this.chunks.getWorld().getChunkManager();
        for (final TLongIterator it = this.chunks.getChunks().iterator(); it.hasNext(); )
        {
            final long key = it.next();
            final int x = BigEndianUtils.getIntA(key);
            final int z = BigEndianUtils.getIntB(key);
            final ChunkImpl chunk = cm.getChunk(x, z);
            if ((chunk == null) || ! chunk.isLoaded())
            {
                return;
            }
            this.tickChunk(chunk, tps);
        }
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("chunks", this.chunks).toString();
    }

    @Override
    public boolean removeWorld(final World world)
    {
        if (world.equals(this.chunks.getWorld()))
        {
            this.chunks.clear();
            return true;
        }
        return false;
    }

    @Override
    public boolean isEmpty()
    {
        return ! this.chunks.isLoaded();
    }
}

