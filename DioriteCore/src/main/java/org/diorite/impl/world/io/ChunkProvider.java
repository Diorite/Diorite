package org.diorite.impl.world.io;

import java.io.Closeable;

import org.diorite.nbt.NbtTagCompound;

public interface ChunkProvider extends Closeable
{
    NbtTagCompound getChunkData(int chunkX, int chunkZ);

    void saveChunkData(int x, int z, NbtTagCompound data);
}
