package org.diorite.event.chunk;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.world.chunk.Chunk;
import org.diorite.world.chunk.ChunkPos;

/**
 * When map want generate new chunk.
 */
public class ChunkGenerateEvent extends ChunkEvent
{
    protected Chunk generatedChunk;

    /**
     * Construct new chunk pre load event.
     *
     * @param chunkPos pos of this chunk, must contains world.
     */
    public ChunkGenerateEvent(final ChunkPos chunkPos)
    {
        super(chunkPos);
    }

    /**
     * Returns chunk that was generated in this event, it will be
     * null at the beginning of pipeline.
     *
     * @return loaded chunk or null.
     */
    public Chunk getGeneratedChunk()
    {
        return this.generatedChunk;
    }

    /**
     * Set generated chunk, setting to to null will work like cancelling event at the end.
     *
     * @param generatedChunk new genwerated chunk.
     */
    public void setGeneratedChunk(final Chunk generatedChunk)
    {
        this.generatedChunk = generatedChunk;
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

        return ! ((this.generatedChunk != null) ? ! this.generatedChunk.equals(that.generatedChunk) : (that.generatedChunk != null));

    }

    @Override
    public int hashCode()
    {
        int result = super.hashCode();
        result = (31 * result) + ((this.generatedChunk != null) ? this.generatedChunk.hashCode() : 0);
        return result;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("generatedChunk", this.generatedChunk).toString();
    }
}
