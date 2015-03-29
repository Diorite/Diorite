package org.diorite.impl.world.chunk;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.world.WorldImpl;
import org.diorite.impl.world.generator.ChunkBuilderImpl;
import org.diorite.world.chunk.Chunk;
import org.diorite.world.chunk.ChunkManager;
import org.diorite.world.chunk.ChunkPos;

import gnu.trove.map.TLongObjectMap;
import gnu.trove.map.hash.TLongObjectHashMap;

public class ChunkManagerImpl implements ChunkManager
{
    private final WorldImpl world;
    private final TLongObjectMap<Chunk> chunks     = new TLongObjectHashMap<>(100); // change to ConcurrentHashMap<Long, ChunkImpl> if needed.
    private final Map<Long, Object>     generating = new ConcurrentHashMap<>(10);

    public ChunkManagerImpl(final WorldImpl world)
    {
        this.world = world;
    }

    @Override
    public Chunk getChunkAt(final ChunkPos pos)
    {
        return this.getChunkAt(pos, true);
    }

    @Override
    public Chunk getChunkAt(final int x, final int z)
    {
        return this.getChunkAt(new ChunkPos(x, z, this.world), true);
    }

    @Override
    public Chunk getChunkAt(ChunkPos pos, final boolean generate)
    {
        final long posLong = pos.asLong();
        Chunk chunk = this.chunks.get(posLong);
        if ((chunk == null) && generate)
        {
            final Object lock = this.generating.get(posLong);
            if (lock != null)
            {
                try
                {
                    //noinspection SynchronizationOnLocalVariableOrMethodParameter
                    synchronized (lock)
                    {
                        lock.wait();
                    }
                } catch (final InterruptedException e)
                {
                    e.printStackTrace();
                }
                return this.chunks.get(posLong);
            }
            this.generating.put(posLong, new Object());
            pos = pos.setWorld(this.world);
            this.chunks.put(posLong, chunk = this.world.getGenerator().generate(new ChunkBuilderImpl(), pos).createChunk(pos));
            final Object obj = this.generating.remove(posLong);
            if (obj != null)
            {
                //noinspection SynchronizationOnLocalVariableOrMethodParameter
                synchronized (obj)
                {
                    obj.notifyAll();
                }
            }
        }
        return chunk;
    }

    @Override
    public void unload(final Chunk chunk)
    {
        this.chunks.remove(chunk.getPos().asLong());
    }

    @Override
    public Chunk getChunkAt(final int x, final int z, final boolean generate)
    {
        return this.getChunkAt(new ChunkPos(x, z, this.world), generate);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("world", this.world).append("chunks", this.chunks.keySet()).toString();
    }
}
