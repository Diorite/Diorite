package org.diorite.event.others;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.command.Command;
import org.diorite.command.sender.CommandSender;
import org.diorite.event.Cancellable;

/**
 * When player type something on chat, and it should be now displayed to whole server.
 */
public class SenderCommandEvent extends SenderEvent implements Cancellable
{
    private String  message;
    private Command command; // cmd that will be executed
    private boolean cancelled;

    /**
     * Construct new chat event with given sender and message.
     *
     * @param sender  sender that typed command.
     * @param message message that player typed.
     */
    public SenderCommandEvent(final CommandSender sender, final String message)
    {
        super(sender);
        Validate.notNull(message, "command message can't be null.");
        this.message = message;
    }

    /**
     * Returns command that will be executed at the end of pipeline event,
     * this will be null at the beginning, and should be set to some command
     * instance before end of pipeline.
     * <p>
     * By default diorite will set that on first pipeline handler.
     *
     * @return command to use.
     */
    public Command getCommand()
    {
        return this.command;
    }

    /**
     * Change/set command that should be executed at the end of pipeline.
     *
     * @param command command to use
     */
    public void setCommand(final Command command)
    {
        this.command = command;
    }

    /**
     * @return command message typed by sender, may be already changed by other event handlers
     */
    public String getMessage()
    {
        return this.message;
    }

    /**
     * Change message typed by player.
     *
     * @param message new message.
     */
    public void setMessage(final String message)
    {
        Validate.notNull(message, "command message can't be null.");
        this.message = message;
    }

    @Override
    public boolean equals(final Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (! (o instanceof SenderCommandEvent))
        {
            return false;
        }
        if (! super.equals(o))
        {
            return false;
        }

        final SenderCommandEvent that = (SenderCommandEvent) o;
        return (this.cancelled == that.cancelled) && this.message.equals(that.message) && ! ((this.command != null) ? ! this.command.equals(that.command) : (that.command != null));

    }

    @Override
    public int hashCode()
    {
        int result = super.hashCode();
        result = (31 * result) + this.message.hashCode();
        result = (31 * result) + ((this.command != null) ? this.command.hashCode() : 0);
        result = (31 * result) + (this.cancelled ? 1 : 0);
        return result;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("message", this.message).append("command", this.command).append("cancelled", this.cancelled).toString();
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
}
