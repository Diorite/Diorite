package diorite.map.chunk;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class ChunkPos
{
    private final int x;
    private final int z;

    public ChunkPos(final int x, final int z)
    {
        this.x = x;
        this.z = z;
    }

    public int getX()
    {
        return this.x;
    }

    public int getZ()
    {
        return this.z;
    }

    @Override
    public int hashCode()
    {
        int result = this.x;
        result = (31 * result) + this.z;
        return result;
    }

    @Override
    public boolean equals(final Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (! (o instanceof ChunkPos))
        {
            return false;
        }

        final ChunkPos chunkPos = (ChunkPos) o;

        return (this.x == chunkPos.x) && (this.z == chunkPos.z);

    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("x", this.x).append("z", this.z).toString();
    }
}
