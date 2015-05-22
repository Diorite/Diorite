package org.diorite.event;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Base Event class, events are only pack of data used by pipelines
 */
public abstract class Event
{
    /**
     * Method will find event type of this event and pass given event to its pipeline. <br>
     * If it can't find event type or pipeline is null, method will return false.
     *
     * @param event event to call.
     *
     * @return true if pipeline was found and event was passed to it.
     */
    public boolean call()
    {
        return EventType.callEvent(this);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).toString();
    }
}
