package org.diorite.impl.world.io.anvil;

import java.io.File;

import org.diorite.impl.world.chunk.ChunkImpl;
import org.diorite.impl.world.io.ChunkRegion;
import org.diorite.nbt.NbtTagCompound;

class AnvilRegion extends ChunkRegion
{
    AnvilRegion(final File file, final int x, final int z)
    {
        super(file, x, z);
    }

    @Override
    public void close()
    {

    }

    @Override
    public ChunkImpl loadChunk(final int x, final int z)
    {
        return null;
    }

    @Override
    public void deleteChunk(final int x, final int z)
    {

    }

    @Override
    public void saveChunk(final int x, final int z, final NbtTagCompound data)
    {

    }
}
