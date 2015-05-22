package org.diorite.event.others;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.command.Command;
import org.diorite.command.sender.CommandSender;
import org.diorite.event.Cancellable;

/**
 * When player type something on chat, and it should be now displayed to whole server.
 */
public class SenderTabCompleteEvent extends SenderEvent implements Cancellable
{
    private       String  message;
    private       Command command; // cmd that will be executed, may be null
    private final boolean isCommand; // if tab complete was used when typing command
    private List<String> completes = new ArrayList<>(5);
    private boolean cancelled;

    /**
     * Construct new chat event with given sender and message.
     *
     * @param sender  sender that typed command.
     * @param message message that player typed.
     */
    public SenderTabCompleteEvent(final CommandSender sender, final String message)
    {
        super(sender);
        Validate.notNull(message, "command message can't be null.");
        this.message = message;
        this.isCommand = (! message.isEmpty()) && (message.charAt(0) == '/');
        if (this.isCommand)
        {
            this.command = sender.getServer().getCommandMap().findCommand(message);
        }
    }

    /**
     * Construct new chat event with given sender and message.
     *
     * @param sender  sender that typed command.
     * @param message message that player typed.
     * @param command command that is typed, may be null.
     */
    public SenderTabCompleteEvent(final CommandSender sender, final String message, final Command command)
    {
        super(sender);
        Validate.notNull(message, "command message can't be null.");
        this.message = message;
        this.command = command;
        //noinspection HardcodedFileSeparator
        this.isCommand = (! message.isEmpty()) && (message.charAt(0) == '/');
    }

    /**
     * Returns command from first argument (if any exist),
     * if tab complete isn't for command, it will return null. <br>
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

    /**
     * @return list of possible endings of current command/message.
     */
    public List<String> getCompletes()
    {
        return this.completes;
    }

    /**
     * Change list of possible endings of current command/message.
     *
     * @param completes new list of endings.
     */
    public void setCompletes(final List<String> completes)
    {
        this.completes = new ArrayList<>(completes);
    }

    /**
     * @return true if this is tab complete used when typing command.
     */
    public boolean isCommand()
    {
        return this.isCommand;
    }

    @Override
    public boolean equals(final Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (! (o instanceof SenderTabCompleteEvent))
        {
            return false;
        }
        if (! super.equals(o))
        {
            return false;
        }

        final SenderTabCompleteEvent that = (SenderTabCompleteEvent) o;
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
