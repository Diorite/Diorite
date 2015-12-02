package org.diorite.inventory.item.builder;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.Diorite;
import org.diorite.inventory.item.meta.LeatherArmorMeta;

/**
 * Represent builder of leather armor item meta data.
 */
public class LeatherArmorMetaBuilder implements ILeatherArmorMetaBuilder<LeatherArmorMetaBuilder, LeatherArmorMeta>
{
    /**
     * Wrapped item meta used by builder.
     */
    protected final LeatherArmorMeta meta;

    /**
     * Construct new meta builder, based on given meta.
     *
     * @param meta source meta to copy to this builder.
     */
    protected LeatherArmorMetaBuilder(final LeatherArmorMeta meta)
    {
        this.meta = meta.clone();
    }

    /**
     * Construct new meta builder.
     */
    protected LeatherArmorMetaBuilder()
    {
        this.meta = Diorite.getCore().getItemFactory().construct(LeatherArmorMeta.class);
    }

    @Override
    public LeatherArmorMeta meta()
    {
        return this.meta;
    }

    @Override
    public LeatherArmorMetaBuilder getBuilder()
    {
        return this;
    }

    /**
     * Returns new builder of item meta data.
     *
     * @return new builder of item meta data.
     */
    public LeatherArmorMetaBuilder start()
    {
        return new LeatherArmorMetaBuilder();
    }

    /**
     * Returns new builder of item meta data, based on given one.
     *
     * @param meta source meta to copy to new builder.
     *
     * @return new builder of item meta data.
     */
    public LeatherArmorMetaBuilder start(final LeatherArmorMeta meta)
    {
        return new LeatherArmorMetaBuilder(meta);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("meta", this.meta).toString();
    }
}
