package org.diorite.impl.world.tick;

import java.lang.ref.WeakReference;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.Tickable;
import org.diorite.impl.world.WorldImpl;
import org.diorite.impl.world.chunk.ChunkImpl;
import org.diorite.world.TickGroup;
import org.diorite.world.chunk.Chunk;

import gnu.trove.TLongCollection;
import gnu.trove.set.hash.TLongHashSet;

@FunctionalInterface
public interface TickGroupImpl extends Tickable, TickGroup
{
    @Override
    default void tickChunk(final Chunk chunk, final int tps)
    {
        this.tickChunk((ChunkImpl) chunk, tps);
    }

    default void tickChunk(final ChunkImpl chunk, final int tps)
    {
        chunk.getTileEntities().values().forEach(t -> t.doTick(tps));
        // TODO
    }

    @SuppressWarnings("MagicNumber")
    static class ChunkGroup
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

        @Override
        public String toString()
        {
            return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("world", this.world).append("chunks", this.chunks).toString();
        }
    }
}
