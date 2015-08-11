package org.diorite.impl.world.tick;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.world.chunk.ChunkImpl;
import org.diorite.impl.world.chunk.ChunkManagerImpl;
import org.diorite.utils.math.pack.IntsToLong;
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
            final int x = IntsToLong.getA(key);
            final int z = IntsToLong.getB(key);
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

