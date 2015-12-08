package org.diorite.inventory.item.builder;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.Diorite;
import org.diorite.inventory.item.meta.BookMeta;

/**
 * Represent builder of book item meta data.
 */
public class BookMetaBuilder implements IBookMetaBuilder<BookMetaBuilder, BookMeta>
{
    /**
     * Wrapped item meta used by builder.
     */
    protected final BookMeta meta;

    /**
     * Construct new meta builder, based on given meta.
     *
     * @param meta source meta to copy to this builder.
     */
    protected BookMetaBuilder(final BookMeta meta)
    {
        this.meta = meta.clone();
    }

    /**
     * Construct new meta builder.
     */
    protected BookMetaBuilder()
    {
        this.meta = Diorite.getCore().getItemFactory().construct(BookMeta.class);
    }

    @Override
    public BookMeta meta()
    {
        return this.meta;
    }

    @Override
    public BookMetaBuilder getBuilder()
    {
        return this;
    }

    /**
     * Returns new builder of item meta data.
     *
     * @return new builder of item meta data.
     */
    public BookMetaBuilder start()
    {
        return new BookMetaBuilder();
    }

    /**
     * Returns new builder of item meta data, based on given one.
     *
     * @param meta source meta to copy to new builder.
     *
     * @return new builder of item meta data.
     */
    public BookMetaBuilder start(final BookMeta meta)
    {
        return new BookMetaBuilder(meta);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("meta", this.meta).toString();
    }
}
