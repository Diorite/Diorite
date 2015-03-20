package org.diorite.impl.world.chunk;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.world.world.generator.WorldGeneratorImpl;
import org.diorite.world.World;
import org.diorite.world.chunk.Chunk;
import org.diorite.world.chunk.ChunkManager;
import org.diorite.world.chunk.ChunkPos;

public class ChunkManagerImpl implements ChunkManager
{
    private final World world;
    private final Map<ChunkPos, ChunkImpl> chunks = new ConcurrentHashMap<>(50);

    public ChunkManagerImpl(final World world)
    {
        this.world = world;
    }

    @Override
    public ChunkImpl getChunkAt(final ChunkPos pos)
    {
        return this.getChunkAt(pos, true);
    }

    @Override
    public ChunkImpl getChunkAt(final int x, final int z)
    {
        return this.getChunkAt(new ChunkPos(x, z, this.world), true);
    }

    @Override
    public ChunkImpl getChunkAt(final ChunkPos pos, final boolean generate)
    {
        ChunkImpl chunk = this.chunks.get(pos);
        if ((chunk == null) && generate)
        {
            chunk = new ChunkImpl(pos);
            this.chunks.put(pos, chunk);
            new WorldGeneratorImpl().generateChunk(chunk);
        }
        return chunk;
    }

    @Override
    public void unload(final Chunk chunk)
    {
        this.chunks.remove(chunk.getPos());
    }

    @Override
    public ChunkImpl getChunkAt(final int x, final int z, final boolean generate)
    {
        return this.getChunkAt(new ChunkPos(x, z, this.world), generate);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("world", this.world).append("chunks", this.chunks.keySet()).toString();
    }
}
