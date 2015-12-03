package org.diorite.inventory.item.builder;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.Diorite;
import org.diorite.inventory.item.meta.FireworkMeta;

/**
 * Represent builder of firework item meta data.
 */
public class FireworkMetaBuilder implements IFireworkMetaBuilder<FireworkMetaBuilder, FireworkMeta>
{
    /**
     * Wrapped item meta used by builder.
     */
    protected final FireworkMeta meta;

    /**
     * Construct new meta builder, based on given meta.
     *
     * @param meta source meta to copy to this builder.
     */
    protected FireworkMetaBuilder(final FireworkMeta meta)
    {
        this.meta = meta.clone();
    }

    /**
     * Construct new meta builder.
     */
    protected FireworkMetaBuilder()
    {
        this.meta = Diorite.getCore().getItemFactory().construct(FireworkMeta.class);
    }

    @Override
    public FireworkMeta meta()
    {
        return this.meta;
    }

    @Override
    public FireworkMetaBuilder getBuilder()
    {
        return this;
    }

    /**
     * Returns new builder of item meta data.
     *
     * @return new builder of item meta data.
     */
    public FireworkMetaBuilder start()
    {
        return new FireworkMetaBuilder();
    }

    /**
     * Returns new builder of item meta data, based on given one.
     *
     * @param meta source meta to copy to new builder.
     *
     * @return new builder of item meta data.
     */
    public FireworkMetaBuilder start(final FireworkMeta meta)
    {
        return new FireworkMetaBuilder(meta);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("meta", this.meta).toString();
    }
}
