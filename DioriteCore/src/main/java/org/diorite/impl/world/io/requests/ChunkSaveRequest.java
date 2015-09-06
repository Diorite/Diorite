package org.diorite.impl.world.io.requests;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.world.chunk.ChunkImpl;
import org.diorite.impl.world.io.ChunkIO;

public class ChunkSaveRequest extends Request<Void>
{
    private final ChunkImpl data;

    public ChunkSaveRequest(final int priority, final ChunkImpl data)
    {
        super(priority);
        this.data = data;
    }

    public ChunkImpl getData()
    {
        return this.data;
    }

    @Override
    public void run(final ChunkIO io)
    {
        if (this.data.isLoaded())
        {
            io.saveChunk(this.data);
        }
        this.setResult(null);
    }

    @Override
    public int getX()
    {
        return this.data.getX();
    }

    @Override
    public int getZ()
    {
        return this.data.getZ();
    }

    @SuppressWarnings("EqualsBetweenInconvertibleTypes")
    @Override
    public boolean equals(final Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (! (o instanceof ChunkSaveRequest))
        {
            return false;
        }
        if (! super.equals(o))
        {
            return false;
        }

        final ChunkSaveRequest that = (ChunkSaveRequest) o;

        return this.data.equals(that.data);
    }

    @Override
    public int hashCode()
    {
        int result = super.hashCode();
        result = (31 * result) + this.data.hashCode();
        return result;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("data", this.data).toString();
    }
}
