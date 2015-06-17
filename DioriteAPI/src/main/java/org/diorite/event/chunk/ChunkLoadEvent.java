package org.diorite.event.chunk;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.world.chunk.Chunk;
import org.diorite.world.chunk.ChunkPos;

/**
 * When map want load next chunk.
 */
public class ChunkLoadEvent extends ChunkEvent
{
    protected Chunk   loadedChunk;
    protected boolean needBeGenerated;

    /**
     * Construct new chunk load event.
     *
     * @param chunkPos pos of this chunk, must contains world.
     */
    public ChunkLoadEvent(final ChunkPos chunkPos)
    {
        super(chunkPos);
    }

    /**
     * Returns chunk that was loaded in this event, it will be
     * null at the beginning of pipeline or when chunk don't ezist.
     *
     * @return loaded chunk or null.
     */
    public Chunk getLoadedChunk()
    {
        return this.loadedChunk;
    }

    /**
     * After load pipeline, if this value if true, chunk will be regenerated.
     *
     * @return true if chunk should be regenerated.
     */
    public boolean isNeedBeGenerated()
    {
        return this.needBeGenerated;
    }

    /**
     * After load pipeline, if this value if true, chunk will be regenerated.
     *
     * @param needBeGenerated if chunk should be regenerated.
     */
    public void setNeedBeGenerated(final boolean needBeGenerated)
    {
        this.needBeGenerated = needBeGenerated;
    }

    /**
     * Set loaded chunk, setting it to null will force diorite to generate new one.
     *
     * @param loadedChunk new loaded chunk.
     */
    public void setLoadedChunk(final Chunk loadedChunk)
    {
        this.loadedChunk = loadedChunk;
    }

    @Override
    public boolean equals(final Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (! (o instanceof ChunkLoadEvent))
        {
            return false;
        }
        if (! super.equals(o))
        {
            return false;
        }

        final ChunkLoadEvent that = (ChunkLoadEvent) o;

        return ! ((this.loadedChunk != null) ? ! this.loadedChunk.equals(that.loadedChunk) : (that.loadedChunk != null));

    }

    @Override
    public int hashCode()
    {
        int result = super.hashCode();
        result = (31 * result) + ((this.loadedChunk != null) ? this.loadedChunk.hashCode() : 0);
        return result;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("loadedChunk", this.loadedChunk).toString();
    }
}
