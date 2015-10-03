package org.diorite.utils.lazy;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.utils.others.Resetable;

/**
 * Class to represent lazy init byte values, lazy value is initialized on first {@link #get()} invoke by {@link #init()} method. <br>
 * Class also implements {@link Resetable} so cached value can be reset and new value will be created on next {@link #get()} method invoke.
 */
public abstract class ByteLazyValueAbstract implements Resetable
{
    /**
     * Used to store cached value.
     */
    protected byte    cached;
    /**
     * Determine if value was already initialized.
     */
    protected boolean isCached;

    /**
     * Construct new ByteLazyValueAbstract object.
     */
    protected ByteLazyValueAbstract()
    {
    }

    /**
     * Method that will return cached value or initialize new if value isn't cached yet.
     *
     * @return value of this lazy value.
     */
    public byte get()
    {
        if (this.isCached)
        {
            return this.cached;
        }
        else
        {
            this.cached = this.init();
            this.isCached = true;
            return this.cached;
        }
    }

    /**
     * Method that should return new value to cache. <br>
     * Invoked in {@link #get()} method when value isn't cached/initialized yet.
     *
     * @return new value to cache.
     */
    protected abstract byte init();

    /**
     * Returns true if value was already initialized.
     *
     * @return true if value was already initialized.
     */
    public boolean isCached()
    {
        return this.isCached;
    }

    @Override
    public void reset()
    {
        this.isCached = false;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("isCached", this.isCached).append("cached", this.cached).toString();
    }
}
