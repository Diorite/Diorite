package org.diorite.event.pipelines;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.event.Event;

/**
 * Exception event, contains {@link Throwable} that was thrown,
 * if event is cancelled then no error is printed to console
 */
public class ExceptionEvent extends Event
{
    private final Throwable throwable;

    /**
     * Construct new exception event with given error.
     *
     * @param throwable throwed exception.
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
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("throwable", this.throwable).toString();
    }
}
