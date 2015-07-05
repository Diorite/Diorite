package org.diorite.utils.lazy;

import java.util.Collection;
import java.util.function.LongSupplier;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.utils.others.Resetable;

public class LongLazyValue implements Resetable
{
    private final LongSupplier supplier;
    private       long         cached;
    private       boolean      updated;

    public LongLazyValue(final LongSupplier supplier)
    {
        this.supplier = supplier;
    }

    public LongLazyValue(final Collection<? super LongLazyValue> collection, final LongSupplier supplier)
    {
        this.supplier = supplier;
        collection.add(this);
    }

    public long get()
    {
        if (this.updated)
        {
            return this.cached;
        }
        else
        {
            this.cached = this.supplier.getAsLong();
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
