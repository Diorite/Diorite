package org.diorite.world.generator;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.world.World;

public abstract class WorldGeneratorInitializer<T extends WorldGenerator>
{
    protected final String name;

    protected WorldGeneratorInitializer(final String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return this.name;
    }

    public abstract T init(World world, String options);

    @Override
    public boolean equals(final Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (! (o instanceof WorldGeneratorInitializer))
        {
            return false;
        }

        //noinspection rawtypes
        final WorldGeneratorInitializer that = (WorldGeneratorInitializer) o;

        return this.name.equals(that.name);

    }

    @Override
    public int hashCode()
    {
        return this.name.hashCode();
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("name", this.name).toString();
    }
}
