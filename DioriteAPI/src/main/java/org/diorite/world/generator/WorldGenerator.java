package org.diorite.world.generator;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.world.World;
import org.diorite.world.chunk.ChunkPos;

public abstract class WorldGenerator
{
    protected final World  world;
    protected final String name;
    protected final String options;

    public WorldGenerator(final World world, final String name, final String options)
    {
        this.world = world;
        this.name = name;
        this.options = options;
    }

    public String getName()
    {
        return this.name;
    }

    public String getOptions()
    {
        return this.options;
    }

    public World getWorld()
    {
        return this.world;
    }

    public abstract ChunkBuilder generate(ChunkBuilder builder, ChunkPos pos);

    @Override
    public boolean equals(final Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (! (o instanceof WorldGenerator))
        {
            return false;
        }

        final WorldGenerator that = (WorldGenerator) o;

        return this.name.equals(that.name) && ! ((this.options != null) ? ! this.options.equals(that.options) : (that.options != null)) && this.world.equals(that.world);

    }

    @Override
    public int hashCode()
    {
        int result = this.world.hashCode();
        result = (31 * result) + this.name.hashCode();
        result = (31 * result) + ((this.options != null) ? this.options.hashCode() : 0);
        return result;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("world", this.world.getName()).append("name", this.name).append("options", this.options).toString();
    }
}
