package org.diorite.inventory.item.builder;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.Diorite;
import org.diorite.inventory.item.meta.BlockItemMeta;

/**
 * Represent builder of block item meta data.
 */
public class BlockItemMetaBuilder implements IBlockItemMetaBuilder<BlockItemMetaBuilder, BlockItemMeta>
{
    /**
     * Wrapped item meta used by builder.
     */
    protected final BlockItemMeta meta;

    /**
     * Construct new meta builder, based on given meta.
     *
     * @param meta source meta to copy to this builder.
     */
    protected BlockItemMetaBuilder(final BlockItemMeta meta)
    {
        this.meta = meta.clone();
    }

    /**
     * Construct new meta builder.
     */
    protected BlockItemMetaBuilder()
    {
        this.meta = Diorite.getCore().getItemFactory().construct(BlockItemMeta.class);
    }

    @Override
    public BlockItemMeta meta()
    {
        return this.meta;
    }

    @Override
    public BlockItemMetaBuilder getBuilder()
    {
        return this;
    }

    /**
     * Returns new builder of item meta data.
     *
     * @return new builder of item meta data.
     */
    public BlockItemMetaBuilder start()
    {
        return new BlockItemMetaBuilder();
    }

    /**
     * Returns new builder of item meta data, based on given one.
     *
     * @param meta source meta to copy to new builder.
     *
     * @return new builder of item meta data.
     */
    public BlockItemMetaBuilder start(final BlockItemMeta meta)
    {
        return new BlockItemMetaBuilder(meta);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("meta", this.meta).toString();
    }
}
