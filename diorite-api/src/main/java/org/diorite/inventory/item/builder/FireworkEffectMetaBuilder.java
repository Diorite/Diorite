package org.diorite.inventory.item.builder;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.Diorite;
import org.diorite.inventory.item.meta.FireworkEffectMeta;

/**
 * Represent builder of firework effect item meta data.
 */
public class FireworkEffectMetaBuilder implements IFireworkEffectMetaBuilder<FireworkEffectMetaBuilder, FireworkEffectMeta>
{
    /**
     * Wrapped item meta used by builder.
     */
    protected final FireworkEffectMeta meta;

    /**
     * Construct new meta builder, based on given meta.
     *
     * @param meta source meta to copy to this builder.
     */
    protected FireworkEffectMetaBuilder(final FireworkEffectMeta meta)
    {
        this.meta = meta.clone();
    }

    /**
     * Construct new meta builder.
     */
    protected FireworkEffectMetaBuilder()
    {
        this.meta = Diorite.getCore().getItemFactory().construct(FireworkEffectMeta.class);
    }

    @Override
    public FireworkEffectMeta meta()
    {
        return this.meta;
    }

    @Override
    public FireworkEffectMetaBuilder getBuilder()
    {
        return this;
    }

    /**
     * Returns new builder of item meta data.
     *
     * @return new builder of item meta data.
     */
    public FireworkEffectMetaBuilder start()
    {
        return new FireworkEffectMetaBuilder();
    }

    /**
     * Returns new builder of item meta data, based on given one.
     *
     * @param meta source meta to copy to new builder.
     *
     * @return new builder of item meta data.
     */
    public FireworkEffectMetaBuilder start(final FireworkEffectMeta meta)
    {
        return new FireworkEffectMetaBuilder(meta);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("meta", this.meta).toString();
    }
}
