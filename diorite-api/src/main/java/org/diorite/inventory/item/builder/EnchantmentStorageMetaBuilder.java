package org.diorite.inventory.item.builder;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.Diorite;
import org.diorite.inventory.item.meta.EnchantmentStorageMeta;

/**
 * Represent builder of enchantment storage item meta data.
 */
public class EnchantmentStorageMetaBuilder implements IEnchantmentStorageMetaBuilder<EnchantmentStorageMetaBuilder, EnchantmentStorageMeta>
{
    /**
     * Wrapped item meta used by builder.
     */
    protected final EnchantmentStorageMeta meta;

    /**
     * Construct new meta builder, based on given meta.
     *
     * @param meta source meta to copy to this builder.
     */
    protected EnchantmentStorageMetaBuilder(final EnchantmentStorageMeta meta)
    {
        this.meta = meta.clone();
    }

    /**
     * Construct new meta builder.
     */
    protected EnchantmentStorageMetaBuilder()
    {
        this.meta = Diorite.getCore().getItemFactory().construct(EnchantmentStorageMeta.class);
    }

    @Override
    public EnchantmentStorageMeta meta()
    {
        return this.meta;
    }

    @Override
    public EnchantmentStorageMetaBuilder getBuilder()
    {
        return this;
    }

    /**
     * Returns new builder of item meta data.
     *
     * @return new builder of item meta data.
     */
    public EnchantmentStorageMetaBuilder start()
    {
        return new EnchantmentStorageMetaBuilder();
    }

    /**
     * Returns new builder of item meta data, based on given one.
     *
     * @param meta source meta to copy to new builder.
     *
     * @return new builder of item meta data.
     */
    public EnchantmentStorageMetaBuilder start(final EnchantmentStorageMeta meta)
    {
        return new EnchantmentStorageMetaBuilder(meta);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("meta", this.meta).toString();
    }
}
