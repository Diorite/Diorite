package org.diorite.utils.lazy;

import java.util.Collection;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.utils.function.FloatSupplier;
import org.diorite.utils.others.Resetable;

public class FloatLazyValue implements Resetable
{
    private final FloatSupplier supplier;
    private       float         cached;
    private       boolean       updated;

    public FloatLazyValue(final FloatSupplier supplier)
    {
        this.supplier = supplier;
    }

    public FloatLazyValue(final Collection<? super FloatLazyValue> collection, final FloatSupplier supplier)
    {
        this.supplier = supplier;
        collection.add(this);
    }

    public float get()
    {
        if (this.updated)
        {
            return this.cached;
        }
        else
        {
            this.cached = this.supplier.getAsFloat();
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
