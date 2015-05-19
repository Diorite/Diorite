package org.diorite.event.pipelines;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.event.Cancellable;
import org.diorite.event.Event;

/**
 * Exception event, contains {@link Throwable} that was thrown,
 * if event is cancelled then no error is printed to console
 */
public class ExceptionEvent extends Event implements Cancellable
{
    private final Throwable throwable;
    private       boolean   cancelled;

    /**
     * Construct new exception event with given error.
     *
     * @param throwable
     */
    public ExceptionEvent(final Throwable throwable)
    {
        this.throwable = throwable;
    }

    /**
     * @return throwed exception.
     */
    public Throwable getThrowable()
    {
        return this.throwable;
    }

    @Override
    public boolean isCancelled()
    {
        return this.cancelled;
    }

    @Override
    public void setCancelled(final boolean bool)
    {
        this.cancelled = bool;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("throwable", this.throwable).append("cancelled", this.cancelled).toString();
    }
}
