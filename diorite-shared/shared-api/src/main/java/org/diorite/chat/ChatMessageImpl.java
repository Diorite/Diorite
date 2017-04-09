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

import javax.annotation.Nullable;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

class ChatMessageImpl extends BaseComponentElement<ChatMessageImpl, ChatMessageEvent, ChatEventAppendChat> implements ChatMessage
{
    @Nullable Object cache; // for chat service implementation.

    @Override
    public void sendTo(ChatMessageType type, MessageReceiver messageReceiver)
    {
        ChatService.getInstance().sendMessage(type, messageReceiver, this);
    }

    @Override
    public ChatMessage addEvent(String text, ChatMessageEvent event)
    {
        return null;
    }

    @Override
    public ChatMessage removeEvent(String... text)
    {
        return null;
    }

    @Override
    public ChatMessage removeEvent(ChatMessageEvent... event)
    {
        return null;
    }

    @Override
    public ChatMessage clearEvents()
    {
        return null;
    }

    @Override
    protected ChatMessageImpl createElement()
    {
        return new ChatMessageImpl();
    }

    @Override
    public ChatMessageImpl duplicate()
    {
        return null;
    }

    @Override
    public String toLegacyText()
    {
        StringBuilder builder = new StringBuilder();
        this.toLegacyText(builder);
        return builder.toString();
    }

    /**
     * Adds color codes of this element to given string builder.
     *
     * @param builder
     *         string builder to be used.
     */
    void addFormat(StringBuilder builder)
    {
        ChatColor color = this.color;
        if (color != null)
        {
            builder.append(color);
        }
        if (this.isBold())
        {
            builder.append(ChatColor.BOLD);
        }
        if (this.isItalic())
        {
            builder.append(ChatColor.ITALIC);
        }
        if (this.isUnderlined())
        {
            builder.append(ChatColor.UNDERLINE);
        }
        if (this.isStrikethrough())
        {
            builder.append(ChatColor.STRIKETHROUGH);
        }
        if (this.isObfuscated())
        {
            builder.append(ChatColor.OBFUSCATE);
        }
    }

    public void toLegacyText(StringBuilder builder)
    {
        this.addFormat(builder);
        if (this.text != null)
        {
            builder.append(this.text);
        }
        else if (this.selector != null)
        {
            builder.append(this.selector);
        }
        else if (this.translate != null)
        {
            builder.append(this.translate);
        }
        if (this.extra != null)
        {
            for (ChatMessageImpl message : this.extra)
            {
                message.toLegacyText(builder);
            }
        }
    }

    @Override
    public String toPlainText()
    {
        StringBuilder builder = new StringBuilder();
        this.toPlainText(builder);
        return builder.toString();
    }

    public void toPlainText(StringBuilder builder)
    {
        if (this.text != null)
        {
            builder.append(this.text);
        }
        else if (this.selector != null)
        {
            builder.append(this.selector);
        }
        else if (this.translate != null)
        {
            builder.append(this.translate);
        }
        if (this.extra != null)
        {
            for (ChatMessageImpl message : this.extra)
            {
                message.toPlainText(builder);
            }
        }
    }

    @Override
    public String toJson()
    {
        return null;
    }

    @Override
    public JsonElement toJsonElement()
    {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("text", "NOT IMPLEMENTED YET, DIORITE CORPORAJT 2017");
        return jsonObject;
    }
}
