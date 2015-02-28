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
        ChunkImpl chunk = this.chunks.get(new ChunkPos(x, z));
        if (chunk == null)
        {
            chunk = new ChunkImpl();
            new MapGeneratorImpl().generateChunk(chunk);
            this.chunks.put(new ChunkPos(x, z), chunk);
        }
        return chunk;
    }
}
