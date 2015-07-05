package org.diorite.utils.lazy;

import java.util.Collection;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.utils.function.ShortSupplier;
import org.diorite.utils.others.Resetable;

public class ShortLazyValue implements Resetable
{
    private final ShortSupplier supplier;
    private       short         cached;
    private       boolean       updated;

    public ShortLazyValue(final ShortSupplier supplier)
    {
        this.supplier = supplier;
    }

    public ShortLazyValue(final Collection<? super ShortLazyValue> collection, final ShortSupplier supplier)
    {
        this.supplier = supplier;
        collection.add(this);
    }

    public short get()
    {
        if (this.updated)
        {
            return this.cached;
        }
        else
        {
            this.cached = this.supplier.getAsShort();
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
