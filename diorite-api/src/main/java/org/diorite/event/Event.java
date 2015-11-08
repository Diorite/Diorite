/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 Diorite (by Bartłomiej Mazur (aka GotoFinal))
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package org.diorite.event;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Base Event class, events are only pack of data used by pipelines
 */
public abstract class Event
{
    protected boolean cancelled;

    /**
     * Method will find event type of this event and pass given event to its pipeline. <br>
     * If it can't find event type or pipeline is null, method will return false.
     *
     * @return true if pipeline was found and event was passed to it.
     */
    public boolean call()
    {
        return EventType.callEvent(this);
    }

    /**
     * @return true if event is cancelled.
     */
    public boolean isCancelled()
    {
        return this.cancelled;
    }

    /**
     * Set if event should be cancelled.
     *
     * @param bool new cancelled state.
     */
    public void setCancelled(final boolean bool)
    {
        this.cancelled = bool;
    }

    /**
     * Cancel event using {@link #setCancelled(boolean)} and return true if
     * event wasn't already cancelled.
     *
     * @return true if event wasn't already cancelled.
     *
     * @see #setCancelled(boolean)
     */
    public boolean cancel()
    {
        if (this.cancelled)
        {
            return false;
        }
        this.cancelled = true;
        return true;
    }

    @Override
    public boolean equals(final Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (! (o instanceof Event))
        {
            return false;
        }

        final Event event = (Event) o;

        return this.cancelled == event.cancelled;

    }

    @Override
    public int hashCode()
    {
        return (this.cancelled ? 1 : 0);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("cancelled", this.cancelled).toString();
    }
}
