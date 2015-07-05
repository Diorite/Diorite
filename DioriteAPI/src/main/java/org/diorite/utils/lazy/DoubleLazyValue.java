package org.diorite.utils.lazy;

import java.util.Collection;
import java.util.function.DoubleSupplier;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.utils.others.Resetable;

public class DoubleLazyValue implements Resetable
{
    private final DoubleSupplier supplier;
    private       double         cached;
    private       boolean        updated;

    public DoubleLazyValue(final DoubleSupplier supplier)
    {
        this.supplier = supplier;
    }

    public DoubleLazyValue(final Collection<? super DoubleLazyValue> collection, final DoubleSupplier supplier)
    {
        this.supplier = supplier;
        collection.add(this);
    }

    public double get()
    {
        if (this.updated)
        {
            return this.cached;
        }
        else
        {
            this.cached = this.supplier.getAsDouble();
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
