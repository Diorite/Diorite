package org.diorite.inventory.item.builder;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.Diorite;
import org.diorite.inventory.item.meta.EntityItemMeta;

/**
 * Represent builder of entity item meta data.
 */
public class EntityItemMetaBuilder implements IEntityItemMetaBuilder<EntityItemMetaBuilder, EntityItemMeta>
{
    /**
     * Wrapped item meta used by builder.
     */
    protected final EntityItemMeta meta;

    /**
     * Construct new meta builder, based on given meta.
     *
     * @param meta source meta to copy to this builder.
     */
    protected EntityItemMetaBuilder(final EntityItemMeta meta)
    {
        this.meta = meta.clone();
    }

    /**
     * Construct new meta builder.
     */
    protected EntityItemMetaBuilder()
    {
        this.meta = Diorite.getCore().getItemFactory().construct(EntityItemMeta.class);
    }

    @Override
    public EntityItemMeta meta()
    {
        return this.meta;
    }

    @Override
    public EntityItemMetaBuilder getBuilder()
    {
        return this;
    }

    /**
     * Returns new builder of item meta data.
     *
     * @return new builder of item meta data.
     */
    public EntityItemMetaBuilder start()
    {
        return new EntityItemMetaBuilder();
    }

    /**
     * Returns new builder of item meta data, based on given one.
     *
     * @param meta source meta to copy to new builder.
     *
     * @return new builder of item meta data.
     */
    public EntityItemMetaBuilder start(final EntityItemMeta meta)
    {
        return new EntityItemMetaBuilder(meta);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("meta", this.meta).toString();
    }
}
