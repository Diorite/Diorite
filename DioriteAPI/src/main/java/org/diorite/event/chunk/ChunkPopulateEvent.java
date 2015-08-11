package org.diorite.event.chunk;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.world.chunk.Chunk;

/**
 * When map want populate some chunk.
 */
public class ChunkPopulateEvent extends ChunkEvent
{
    protected final Chunk   chunk;
    protected final boolean force;

    /**
     * Construct new chunk populate event.
     *
     * @param chunk chunk to populate, can't by null.
     * @param force
     */
    public ChunkPopulateEvent(final Chunk chunk, final boolean force)
    {
        super(chunk.getPos());
        this.chunk = chunk;
        this.force = force;
    }

    /**
     * @return true if chunk should be force-populated.
     */
    public boolean isForce()
    {
        return this.force;
    }

    /**
     * @return chunk to populate.
     */
    public Chunk getChunk()
    {
        return this.chunk;
    }

    /**
     * @return true if chunk is already populated.
     *
     * @see Chunk#isPopulated()
     */
    public boolean isPopulated()
    {
        return this.chunk.isPopulated();
    }

    @Override
    public boolean equals(final Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (! (o instanceof ChunkPopulateEvent))
        {
            return false;
        }
        if (! super.equals(o))
        {
            return false;
        }

        final ChunkPopulateEvent that = (ChunkPopulateEvent) o;

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
