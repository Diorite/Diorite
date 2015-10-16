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

package org.diorite.impl.input;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.command.sender.CommandSender;

public class InputAction
{
    private final String          msg;
    private final CommandSender   sender;
    private final InputActionType type;

    public InputAction(final String msg, final CommandSender sender, final InputActionType type)
    {
        this.msg = msg;
        this.sender = sender;
        this.type = type;
    }

    public String getMsg()
    {
        return this.msg;
    }

    public CommandSender getSender()
    {
        return this.sender;
    }

    public InputActionType getType()
    {
        return this.type;
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
        if (! (o instanceof InputAction))
        {
            return false;
        }

        final InputAction that = (InputAction) o;

        return ! ((this.msg != null) ? ! this.msg.equals(that.msg) : (that.msg != null)) && ! ((this.sender != null) ? ! this.sender.equals(that.sender) : (that.sender != null));
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("msg", this.msg).append("sender", this.sender).toString();
    }
}
