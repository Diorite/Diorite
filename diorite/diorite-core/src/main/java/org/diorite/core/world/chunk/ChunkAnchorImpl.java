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

package org.diorite.core.world.chunk;

import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.atomic.AtomicBoolean;

import org.diorite.commons.sets.ConcurrentSet;
import org.diorite.plugin.Plugin;
import org.diorite.world.chunk.ChunkAnchor;
import org.diorite.world.chunk.ChunkPosition;

public class ChunkAnchorImpl implements ChunkAnchor
{
    private final Plugin plugin;
    private final AtomicBoolean             active                     = new AtomicBoolean(true);
    private final Collection<ChunkPosition> affectedChunks             = new ConcurrentSet<>();
    private final Collection<ChunkPosition> affectedChunksUnmodifiable = Collections.unmodifiableCollection(this.affectedChunks);

    public ChunkAnchorImpl(Plugin plugin) {this.plugin = plugin;}

    @Override
    public Plugin getPlugin()
    {
        return this.plugin;
    }

    @Override
    public boolean isActive()
    {
        return this.active.get();
    }

    @Override
    public void setActive(boolean active)
    {
        this.active.set(active);
    }

    @Override
    public void clearChunks()
    {
        this.affectedChunks.clear();
    }

    @Override
    public boolean containsChunk(ChunkPosition chunk)
    {
        return this.affectedChunks.contains(chunk);
    }

    @Override
    public Collection<ChunkPosition> getAffectedChunks()
    {
        return this.affectedChunksUnmodifiable;
    }
}
