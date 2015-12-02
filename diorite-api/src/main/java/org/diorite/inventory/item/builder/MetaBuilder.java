package org.diorite.inventory.item.builder;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.Diorite;
import org.diorite.inventory.item.meta.ItemMeta;

/**
 * Represent builder of basic meta data.
 */
public class MetaBuilder implements IMetaBuilder<MetaBuilder, ItemMeta>
{
    /**
     * Wrapped item meta used by builder.
     */
    protected final ItemMeta meta;

    /**
     * Construct new meta builder, based on given meta.
     *
     * @param meta source meta to copy to this builder.
     */
    protected MetaBuilder(final ItemMeta meta)
    {
        this.meta = meta.clone();
    }

    /**
     * Construct new meta builder.
     */
    protected MetaBuilder()
    {
        this.meta = Diorite.getCore().getItemFactory().construct(ItemMeta.class);
    }

    @Override
    public ItemMeta meta()
    {
        return this.meta;
    }

    @Override
    public MetaBuilder getBuilder()
    {
        return this;
    }

    /**
     * Returns new builder of item meta data.
     *
     * @return new builder of item meta data.
     */
    public MetaBuilder start()
    {
        return new MetaBuilder();
    }

    /**
     * Returns new builder of item meta data, based on given one.
     *
     * @param meta source meta to copy to new builder.
     *
     * @return new builder of item meta data.
     */
    public MetaBuilder start(final ItemMeta meta)
    {
        return new MetaBuilder(meta);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("meta", this.meta).toString();
    }
}
