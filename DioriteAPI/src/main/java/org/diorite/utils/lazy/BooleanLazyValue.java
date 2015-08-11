package org.diorite.utils.lazy;

import java.util.Collection;
import java.util.function.BooleanSupplier;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.utils.others.Resetable;

public class BooleanLazyValue implements Resetable
{
    private final BooleanSupplier supplier;
    private       boolean         cached;
    private       boolean         updated;

    public BooleanLazyValue(final BooleanSupplier supplier)
    {
        this.supplier = supplier;
    }

    public BooleanLazyValue(final Collection<? super BooleanLazyValue> collection, final BooleanSupplier supplier)
    {
        this.supplier = supplier;
        collection.add(this);
    }

    public boolean get()
    {
        if (this.updated)
        {
            return this.cached;
        }
        else
        {
            this.cached = this.supplier.getAsBoolean();
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
