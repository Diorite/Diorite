package org.diorite.impl.world.io.requests;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.world.chunk.ChunkImpl;
import org.diorite.impl.world.io.ChunkIO;

public class ChunkLoadRequest extends Request<ChunkImpl>
{
    private final int x;
    private final int z;

    public ChunkLoadRequest(final int priority, final int x, final int z)
    {
        super(priority);
        this.x = x;
        this.z = z;
    }

    @Override
    public void run(final ChunkIO io)
    {
        io.loadChunk(this.x, this.z);
    }

    @Override
    public int getX()
    {
        return this.x;
    }

    @Override
    public int getZ()
    {
        return this.z;
    }

    @SuppressWarnings("EqualsBetweenInconvertibleTypes")
    @Override
    public boolean equals(final Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (! (o instanceof ChunkLoadRequest))
        {
            return false;
        }
        if (! super.equals(o))
        {
            return false;
        }

        final ChunkLoadRequest that = (ChunkLoadRequest) o;

        return (this.x == that.x) && (this.z == that.z);
    }

    @Override
    public int hashCode()
    {
        int result = super.hashCode();
        result = (31 * result) + this.x;
        result = (31 * result) + this.z;
        return result;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("x", this.x).append("z", this.z).toString();
    }
}
