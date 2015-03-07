package diorite.impl.map.chunk;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import diorite.impl.Main;
import diorite.impl.map.world.generator.MapGeneratorImpl;
import diorite.map.World;
import diorite.map.chunk.ChunkPos;

public class ChunkManagerImpl
{
    private final World world;
    private final Map<ChunkPos, ChunkImpl> chunks = new ConcurrentHashMap<>(50);

    public ChunkManagerImpl(final World world)
    {
        this.world = world;
    }

    public ChunkImpl getChunkAt(final ChunkPos pos)
    {
        return this.getChunkAt(pos, true);
    }

    public ChunkImpl getChunkAt(final int x, final int z)
    {
        return this.getChunkAt(new ChunkPos(x, z, this.world), true);
    }

    public ChunkImpl getChunkAt(final ChunkPos pos, final boolean generate)
    {
        ChunkImpl chunk = this.chunks.get(pos);
        if ((chunk == null) && generate)
        {
            Main.debug("[Chunk] creating chunk: "+pos);
            chunk = new ChunkImpl(pos);
            new MapGeneratorImpl().generateChunk(chunk);
            this.chunks.put(pos, chunk);
        }
        return chunk;
    }

    public void unload(final ChunkImpl chunk)
    {
        Main.debug("[Chunk] removing chunk: "+chunk.getPos());
        this.chunks.remove(chunk.getPos());
    }

    public ChunkImpl getChunkAt(final int x, final int z, final boolean generate)
    {
        return this.getChunkAt(new ChunkPos(x, z, this.world), generate);
    }
}
