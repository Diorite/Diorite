package org.diorite.event.input;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.command.sender.CommandSender;
import org.diorite.event.Event;

public abstract class SenderEvent extends Event
{
    protected final CommandSender sender;

    /**
     * Construct new sender event.
     *
     * @param sender sender related to event, can't be null.
     */
    public SenderEvent(final CommandSender sender)
    {
        Validate.notNull(sender, "CommandSender can't be null.");
        this.sender = sender;
    }

    /**
     * @return sender related to event.
     */
    public CommandSender getSender()
    {
        return this.sender;
    }

    @Override
    public boolean equals(final Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (! (o instanceof SenderEvent))
        {
            return false;
        }

        final SenderEvent that = (SenderEvent) o;
        return this.sender.equals(that.sender);

    }

    @Override
    public int hashCode()
    {
        return this.sender.hashCode();
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("sender", this.sender).toString();
    }
}