package org.diorite.inventory.item.builder;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.Diorite;
import org.diorite.inventory.item.meta.SkullMeta;

/**
 * Represent builder of skull item meta data.
 */
public class SkullMetaBuilder implements ISkullMetaBuilder<SkullMetaBuilder, SkullMeta>
{
    /**
     * Wrapped item meta used by builder.
     */
    protected final SkullMeta meta;

    /**
     * Construct new meta builder, based on given meta.
     *
     * @param meta source meta to copy to this builder.
     */
    protected SkullMetaBuilder(final SkullMeta meta)
    {
        this.meta = meta.clone();
    }

    /**
     * Construct new meta builder.
     */
    protected SkullMetaBuilder()
    {
        this.meta = Diorite.getCore().getItemFactory().construct(SkullMeta.class);
    }

    @Override
    public SkullMeta meta()
    {
        return this.meta;
    }

    @Override
    public SkullMetaBuilder getBuilder()
    {
        return this;
    }

    /**
     * Returns new builder of item meta data.
     *
     * @return new builder of item meta data.
     */
    public SkullMetaBuilder start()
    {
        return new SkullMetaBuilder();
    }

    /**
     * Returns new builder of item meta data, based on given one.
     *
     * @param meta source meta to copy to new builder.
     *
     * @return new builder of item meta data.
     */
    public SkullMetaBuilder start(final SkullMeta meta)
    {
        return new SkullMetaBuilder(meta);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("meta", this.meta).toString();
    }
}
