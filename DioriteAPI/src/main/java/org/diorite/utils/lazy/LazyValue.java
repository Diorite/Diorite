package org.diorite.utils.lazy;

import java.util.Collection;
import java.util.function.Supplier;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.utils.others.Resetable;

public class LazyValue<T> implements Resetable
{
    private final Supplier<T> supplier;
    private       T           cached;

    public LazyValue(final Supplier<T> supplier)
    {
        this.supplier = supplier;
    }

    public LazyValue(final Collection<? super LazyValue<T>> collection, final Supplier<T> supplier)
    {
        this.supplier = supplier;
        collection.add(this);
    }

    public T get()
    {
        return (this.cached != null) ? this.cached : ((this.cached = this.supplier.get()));
    }

    @Override
    public void reset()
    {
        this.cached = null;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("supplier", this.supplier).append("cached", this.cached).toString();
    }
}
