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

package org.diorite.event.input;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.command.Command;
import org.diorite.command.sender.CommandSender;

/**
 * When player type something on chat, and it should be now displayed to whole server.
 */
public class SenderCommandEvent extends SenderEvent
{
    protected String  message;
    protected Command command; // cmd that will be executed

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

        return this.message.equals(that.message) && ! ((this.command != null) ? ! this.command.equals(that.command) : (that.command != null));

    }

    @Override
    public int hashCode()
    {
        int result = super.hashCode();
        result = (31 * result) + this.message.hashCode();
        result = (31 * result) + ((this.command != null) ? this.command.hashCode() : 0);
        return result;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("message", this.message).append("command", (this.command == null) ? null : this.command.getName()).toString();
    }
}
