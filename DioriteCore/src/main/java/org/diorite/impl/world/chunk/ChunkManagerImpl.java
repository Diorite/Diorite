package org.diorite.impl.world.chunk;

import java.util.Collection;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.Main;
import org.diorite.impl.world.world.WorldImpl;
import org.diorite.impl.world.world.generator.WorldGeneratorImpl;
import org.diorite.world.chunk.Chunk;
import org.diorite.world.chunk.ChunkManager;
import org.diorite.world.chunk.ChunkPos;

import gnu.trove.map.TLongObjectMap;
import gnu.trove.map.hash.TLongObjectHashMap;
import io.netty.util.internal.ConcurrentSet;

public class ChunkManagerImpl implements ChunkManager
{
    private final WorldImpl world;
    private final TLongObjectMap<ChunkImpl> chunks     = new TLongObjectHashMap<>(100); // change to ConcurrentHashMap<Long, ChunkImpl> if needed.
    private final Collection<Long>          generating = new ConcurrentSet<>();

    public ChunkManagerImpl(final WorldImpl world)
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
        final long posLong = pos.asLong();
        ChunkImpl chunk = this.chunks.get(posLong);
        if ((chunk == null) && generate) // TODO: fix double chunk generating when two players need generate this same chunk at similar time
        {
            if (this.generating.contains(posLong))
            {
                //noinspection StatementWithEmptyBody
                while (this.generating.contains(posLong))
                {
                    // wait for chunk
                }
                Main.debug("Wait chunk works!");
                return this.chunks.get(posLong);
            }
            this.generating.add(posLong);
            chunk = new ChunkImpl(this.world, pos);
            this.chunks.put(posLong, chunk);
            new WorldGeneratorImpl().generateChunk(chunk);
            this.generating.remove(posLong);
        }
        return chunk;
    }

    @Override
    public void unload(final Chunk chunk)
    {
        this.chunks.remove(chunk.getPos().asLong());
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
