package org.diorite.impl.multithreading;

import java.lang.ref.WeakReference;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.world.chunk.Chunk;

public class ChunkUnloadAction
{
    private final WeakReference<Chunk> chunk;
    private final long time = System.currentTimeMillis();

    public ChunkUnloadAction(final Chunk chunk)
    {
        this.chunk = new WeakReference<>(chunk);
    }

    public WeakReference<Chunk> getChunk()
    {
        return this.chunk;
    }

    public long getTime()
    {
        return this.time;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("chunk", this.chunk).append("time", this.time).toString();
    }
}
