package org.diorite.impl.multithreading;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.command.sender.CommandSender;

public class ChatAction
{
    private final String        msg;
    private final CommandSender sender;

    public ChatAction(final String msg, final CommandSender sender)
    {
        this.msg = msg;
        this.sender = sender;
    }

    public String getMsg()
    {
        return this.msg;
    }

    public CommandSender getSender()
    {
        return this.sender;
    }

    @Override
    public int hashCode()
    {
        int result = (this.msg != null) ? this.msg.hashCode() : 0;
        result = (31 * result) + ((this.sender != null) ? this.sender.hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(final Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (! (o instanceof ChatAction))
        {
            return false;
        }

        final ChatAction that = (ChatAction) o;

        return ! ((this.msg != null) ? ! this.msg.equals(that.msg) : (that.msg != null)) && ! ((this.sender != null) ? ! this.sender.equals(that.sender) : (that.sender != null));
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("msg", this.msg).append("sender", this.sender).toString();
    }
}
