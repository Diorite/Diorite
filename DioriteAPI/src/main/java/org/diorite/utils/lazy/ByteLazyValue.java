package org.diorite.utils.lazy;

import java.util.Collection;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.utils.function.ByteSupplier;
import org.diorite.utils.others.Resetable;

public class ByteLazyValue implements Resetable
{
    private final ByteSupplier supplier;
    private       byte         cached;
    private       boolean      updated;

    public ByteLazyValue(final ByteSupplier supplier)
    {
        this.supplier = supplier;
    }
    public ByteLazyValue(final Collection<? super ByteLazyValue> collection, final ByteSupplier supplier)
    {
        this.supplier = supplier;
        collection.add(this);
    }

    public byte get()
    {
        if (this.updated)
        {
            return this.cached;
        }
        else
        {
            this.cached = this.supplier.getAsByte();
            this.updated = true;
            return this.cached;
        }
    }

    @Override
    public void reset()
    {
        this.updated = false;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("supplier", this.supplier).append("cached", this.cached).toString();
    }
}
