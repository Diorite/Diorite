package diorite.impl.map.chunk;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import diorite.impl.map.world.generator.MapGeneratorImpl;
import diorite.map.chunk.ChunkPos;

public class ChunkManagerImpl
{
    private final Map<ChunkPos, ChunkImpl> chunks = new ConcurrentHashMap<>(50);

    public ChunkImpl getChunkAt(final int x, final int z)
    {
        final ChunkPos pos = new ChunkPos(x, z);
        ChunkImpl chunk = this.chunks.get(pos);
        if (chunk == null)
        {
            chunk = new ChunkImpl(pos);
            new MapGeneratorImpl().generateChunk(chunk);
            this.chunks.put(pos, chunk);
        }
        return chunk;
    }
}
