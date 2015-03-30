package org.diorite.impl.world.chunk;

import org.diorite.utils.collections.NibbleArray;
import org.diorite.world.chunk.Chunk;

public final class ChunkNibbleArray extends NibbleArray
{

    public static final int CHUNK_NIBBLE_ARRAY_SIZE = Chunk.CHUNK_SIZE * Chunk.CHUNK_PART_HEIGHT * Chunk.CHUNK_SIZE;

    public ChunkNibbleArray()
    {
        super(CHUNK_NIBBLE_ARRAY_SIZE << 1);
    }

    public ChunkNibbleArray(final byte[] data)
    {
        super(data);
        if (data.length != CHUNK_NIBBLE_ARRAY_SIZE)
        {
            throw new IllegalArgumentException("ChunkNibbleArrays should be 2048 bytes not: " + data.length);
        }
    }

    public int get(final int blockMeta, final int blockData, final int blockID)
    {
        return this.get((blockData << 8) | (blockID << 4) | blockMeta);
    }

    public void set(final int blockMeta, final int blockData, final int blockID, final int value)
    {
        this.set((blockData << 8) | (blockID << 4) | blockMeta, (byte) value);
    }
}