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

import java.util.Collection;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.world.WorldImpl;
import org.diorite.impl.world.chunk.ChunkImpl;
import org.diorite.utils.collections.WeakCollection;
import org.diorite.world.World;

public class WorldsTickGroup implements TickGroupImpl
{
    private final WeakCollection<WorldImpl> worlds;

    public WorldsTickGroup(final Collection<WorldImpl> worlds)
    {
        this.worlds = WeakCollection.usingHashSet(worlds.size());
        this.worlds.addAll(worlds);
    }

    public WeakCollection<WorldImpl> getWorlds()
    {
        return this.worlds;
    }

    @Override
    public void doTick(final int tps)
    {
        this.worlds.forEach(w -> {
            w.doTick(tps);
            w.getChunkManager().getLoadedChunks().stream().filter(ChunkImpl::isLoaded).forEach(c -> this.tickChunk(c, tps));
        });
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("worlds", this.worlds).toString();
    }

    @Override
    public boolean removeWorld(final World world)
    {
        return this.worlds.remove(world);
    }

    @Override
    public boolean isEmpty()
    {
        return this.worlds.isEmpty();
    }
}
