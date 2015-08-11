package org.diorite.event.chunk;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.world.chunk.Chunk;

/**
 * When map want generate new chunk.
 */
public class ChunkGenerateEvent extends ChunkEvent
{
    protected final Chunk chunk;

    /**
     * Construct new chunk generate event.
     *
     * @param chunk chunk to generate.
     */
    public ChunkGenerateEvent(final Chunk chunk)
    {
        super(chunk.getPos());
        this.chunk = chunk;
    }

    /**
     * Returns chunk that was generated in this event.
     *
     * @return generated chunk.
     */
    public Chunk getChunk()
    {
        return this.chunk;
    }

    @Override
    public boolean equals(final Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (! (o instanceof ChunkGenerateEvent))
        {
            return false;
        }
        if (! super.equals(o))
        {
            return false;
        }

        final ChunkGenerateEvent that = (ChunkGenerateEvent) o;

        return this.chunk.equals(that.chunk);

    }

    @Override
    public int hashCode()
    {
        int result = super.hashCode();
        result = (31 * result) + this.chunk.hashCode();
        return result;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("chunk", this.chunk).toString();
    }
}
