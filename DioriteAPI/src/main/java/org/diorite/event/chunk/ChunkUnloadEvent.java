package org.diorite.event.chunk;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.world.chunk.Chunk;

public class ChunkUnloadEvent extends ChunkEvent
{
    protected final Chunk   chunk;
    protected       boolean safe;

    /**
     * Construct new chunk pre load event.
     *
     * @param chunk chunk to unload.
     * @param safe  if it should use safe save. (check for uses of chunk etc...)
     */
    public ChunkUnloadEvent(final Chunk chunk, final boolean safe)
    {
        super(chunk.getPos());
        this.chunk = chunk;
        this.safe = safe;
    }

    /**
     * Returns chunk that is unloading in this event.
     *
     * @return chunk to unload.
     */
    public Chunk getChunk()
    {
        return this.chunk;
    }

    /**
     * @return if it should use safe save. (check for uses of chunk etc...)
     */
    public boolean isSafe()
    {
        return this.safe;
    }

    /**
     * Change safe mode.
     *
     * @param safe if it should use safe save. (check for uses of chunk etc...)
     */
    public void setSafe(final boolean safe)
    {
        this.safe = safe;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("chunk", this.chunk).toString();
    }
}
