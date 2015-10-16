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

import java.lang.ref.WeakReference;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.Tickable;
import org.diorite.impl.world.WorldImpl;
import org.diorite.impl.world.chunk.ChunkImpl;
import org.diorite.world.TickGroup;
import org.diorite.world.World;
import org.diorite.world.chunk.Chunk;

import gnu.trove.TLongCollection;
import gnu.trove.set.hash.TLongHashSet;

public interface TickGroupImpl extends Tickable, TickGroup
{
    @Override
    default void tickChunk(final Chunk chunk, final int tps)
    {
        this.tickChunk((ChunkImpl) chunk, tps);
    }

    default void tickChunk(final ChunkImpl chunk, final int tps)
    {
        chunk.setLastTickThread(Thread.currentThread());
        chunk.getTileEntities().forEachValue(t -> {
            t.doTick(tps);
            return true;
        });
        chunk.getEntities().forEach(e -> e.doTick(tps));
        // TODO random block update and other shit
    }

    boolean removeWorld(World world);

    boolean isEmpty();

    @SuppressWarnings("MagicNumber")
    class ChunkGroup
    {
        private final WeakReference<WorldImpl> world;
        public static final int             CHUNKS_PER_FILE = 1024;
        private final       TLongCollection chunks          = new TLongHashSet(CHUNKS_PER_FILE / 2, .5f);

        public ChunkGroup(final WorldImpl world)
        {
            this.world = new WeakReference<>(world);
        }

        public TLongCollection getChunks()
        {
            return this.chunks;
        }

        public WorldImpl getWorld()
        {
            return this.world.get();
        }

        public boolean isLoaded()
        {
            return ! this.chunks.isEmpty() && (this.world.get() != null);
        }

        public void clear()
        {
            this.world.clear();
            this.chunks.clear();
        }

        @Override
        public String toString()
        {
            return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("world", this.world).append("chunks", this.chunks).toString();
        }
    }
}
