package org.diorite.impl.world.io;

import java.io.File;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.world.chunk.ChunkImpl;
import org.diorite.nbt.NbtTagCompound;

public class ChunkRegion
{
    protected final int  x;
    protected final int  z;
    protected final File file;

    public ChunkRegion(final File file, final int x, final int z)
    {
        this.x = x;
        this.z = z;
        this.file = file;
    }

    public int getX()
    {
        return this.x;
    }

    public int getZ()
    {
        return this.z;
    }

    public void close()
    {
    }

    public ChunkImpl loadChunk(final int x, final int z) // local cords, like from 0 to 31 on default anvil
    {
        return null;
    }

    public void deleteChunk(final int x, final int z)
    {
    }

    public void saveChunk(final int x, final int z, final NbtTagCompound data)
    {

    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("x", this.x).append("z", this.z).append("file", this.file).toString();
    }
}
