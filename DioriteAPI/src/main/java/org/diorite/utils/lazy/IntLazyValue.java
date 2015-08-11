package org.diorite.utils.lazy;

import java.util.Collection;
import java.util.function.IntSupplier;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.utils.others.Resetable;

public class IntLazyValue implements Resetable
{
    private final IntSupplier supplier;
    private       int         cached;
    private       boolean     updated;

    public IntLazyValue(final IntSupplier supplier)
    {
        this.supplier = supplier;
    }

    public IntLazyValue(final Collection<? super IntLazyValue> collection, final IntSupplier supplier)
    {
        this.supplier = supplier;
        collection.add(this);
    }

    public int get()
    {
        if (this.updated)
        {
            return this.cached;
        }
        else
        {
            this.cached = this.supplier.getAsInt();
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
