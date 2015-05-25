package org.diorite.impl.world.chunk;

import java.util.Collection;
import java.util.concurrent.ForkJoinPool;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.Tickable;
import org.diorite.utils.collections.WeakCollection;
import org.diorite.world.chunk.ChunkPos;

/**
 * One or more chunk files per group, don't split files between multiple groups.
 */
public class ChunkGroup implements Tickable, Runnable
{
    private final Collection<ChunkImpl> chunks   = WeakCollection.usingHashSet(1000, true);
    private final ForkJoinPool          pool     = new ForkJoinPool(1);
    private final ForkJoinPool          savePool = new ForkJoinPool(1);

    @Override
    public void doTick()
    {
        this.pool.submit(this);
    }

    public boolean addChunk(final ChunkImpl chunk)
    {
        return this.isIn(chunk.getPos()) && this.chunks.add(chunk);
    }

    public boolean saveChunk(final ChunkImpl chunk)
    {
        if (this.chunks.contains(chunk))
        {
            this.savePool.submit(() -> chunk.getWorld().getWorldFile().saveChunk(chunk));
            return true;
        }
        return false;
    }

    public boolean saveChunk(final ChunkImpl chunk, final Runnable action)
    {
        if (this.chunks.contains(chunk))
        {
            this.savePool.submit(() -> {
                action.run();
                chunk.getWorld().getWorldFile().saveChunk(chunk);
            });
            return true;
        }
        return false;
    }

    public boolean isIn(final ChunkPos pos)
    {
        return true; // TODO
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("chunks", this.chunks).toString();
    }

    @Override
    public void run()
    {
        this.chunks.forEach(Tickable::doTick);
    }

    public class ChunkGroupThread extends Thread
    {
        public ChunkGroupThread()
        {
            super(ChunkGroup.this, "ChunkTick@" + System.identityHashCode(ChunkGroup.this));
            this.setDaemon(true);
        }
    }
}
