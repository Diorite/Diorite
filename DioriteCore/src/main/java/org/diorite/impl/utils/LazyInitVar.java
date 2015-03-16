package org.diorite.impl.utils;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public abstract class LazyInitVar<T>
{
    private T object;
    private boolean initialized = false;

    public T get()
    {
        if (! this.initialized)
        {
            this.initialized = true;
            this.object = this.init();
        }
        return this.object;
    }

    public boolean isInitialized()
    {
        return this.initialized;
    }

    protected abstract T init();

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("object", this.object).append("initialized", this.initialized).toString();
    }
}
