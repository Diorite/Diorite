/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017. Diorite (by Bartłomiej Mazur (aka GotoFinal))
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

package org.diorite.chat;

import org.apache.commons.lang3.builder.ToStringBuilder;

class ChatMutableEvent implements Cloneable, ChatMessageEvent
{
    protected final Action action;
    protected       Object value;

    protected ChatMutableEvent(Action action, Object value)
    {
        this.action = action;
        this.value = value;
    }

    public Object getValue()
    {
        return this.value;
    }

    public void setValue(Object value)
    {
        this.value = value;
    }

    @Override
    public Action getAction()
    {
        return this.action;
    }

    @Override
    public Object getRawValue()
    {
        return this.value;
    }

    @Override
    public ChatMutableEvent duplicate()
    {
        return new ChatMutableEvent(this.action, this.value);
    }

    public ChatMessageEvent create()
    {
        switch (this.action)
        {
            case SHOW_TEXT:
                return ChatMessageEvent.showText((ChatMessage) this.value);
            case SHOW_ACHIEVEMENT:
                throw new UnsupportedOperationException("Not implemented yet!"); // TODO SHOW_ACHIEVEMENT
            case SHOW_ITEM:
                throw new UnsupportedOperationException("Not implemented yet!"); // TODO SHOW_ITEM
            case SHOW_ENTITY:
                throw new UnsupportedOperationException("Not implemented yet!"); // TODO SHOW_ENTITY
            case CHANGE_PAGE:
                return ChatMessageEvent.changePage((Integer) this.value);
            case OPEN_URL:
                return ChatMessageEvent.openURL(this.value.toString());
            case OPEN_FILE:
                return ChatMessageEvent.openFile(this.value.toString());
            case RUN_COMMAND:
                return ChatMessageEvent.runCommand(this.value.toString());
            case SUGGEST_COMMAND:
                return ChatMessageEvent.suggestCommand(this.value.toString());
            case APPEND_CHAT:
                return ChatMessageEvent.appendChat(this.value.toString());
            default:
                throw new UnsupportedOperationException("Unknown action: " + this.action);
        }
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this).appendSuper(super.toString()).append("action", this.action).append("value", this.value).toString();
    }
}
