/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2016. Diorite (by Bartłomiej Mazur (aka GotoFinal))
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

package org.diorite.impl.command;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

import org.diorite.impl.DioriteCore;
import org.diorite.Core;
import org.diorite.Diorite;
import org.diorite.chat.ChatColor;
import org.diorite.chat.ChatPosition;
import org.diorite.chat.component.BaseComponent;
import org.diorite.command.sender.ConsoleCommandSender;
import org.diorite.command.sender.MessageOutput;
import org.diorite.permissions.PermissionsContainer;
import org.diorite.utils.lazy.LazyValue;

public class ConsoleCommandSenderImpl implements ConsoleCommandSender
{
    protected final DioriteCore core;
    protected final LazyValue<PermissionsContainer> permissionsContainer = new LazyValue<>(() -> Diorite.getServerManager().getPermissionsManager().createContainer(this));
    protected MessageOutput messageOutput;

    public ConsoleCommandSenderImpl(final DioriteCore core)
    {
        this.core = core;
        this.messageOutput = new ConsoleMessageOutput(core);
    }

    @Override
    public MessageOutput getMessageOutput()
    {
        return this.messageOutput;
    }

    @Override
    public void setMessageOutput(final MessageOutput messageOutput)
    {
        this.messageOutput = messageOutput;
    }

    @Override
    public Core getCore()
    {
        return this.core;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("server", this.core).toString();
    }

    @Override
    public PermissionsContainer getPermissionsContainer()
    {
        return this.permissionsContainer.get();
    }

    public static class ConsoleMessageOutput implements MessageOutput
    {
        private final DioriteCore core;
        private final Marker chat   = MarkerFactory.getMarker(ChatPosition.CHAT.name().toLowerCase());
        private final Marker system = MarkerFactory.getMarker(ChatPosition.SYSTEM.name().toLowerCase());
        private final Marker action = MarkerFactory.getMarker(ChatPosition.ACTION.name().toLowerCase());
        private final Marker none   = MarkerFactory.getMarker("");

        public ConsoleMessageOutput(final DioriteCore core)
        {
            this.core = core;
        }

        @SuppressWarnings("ObjectEquality")
        protected Marker getMarket(final ChatPosition position)
        {
            if (position == ChatPosition.CHAT)
            {
                return this.chat;
            }
            if (position == ChatPosition.SYSTEM)
            {
                return this.system;
            }
            if (position == ChatPosition.ACTION)
            {
                return this.action;
            }
            return this.none;
        }

        protected void sendMessage(final Marker marker, final String str)
        {
            this.core.getLogger().info(marker, ChatColor.stripColor(str));
        }

        @Override
        public void sendMessage(final ChatPosition position, final BaseComponent component)
        {
            this.sendMessage(this.getMarket(position), component.toLegacyText());
        }

        @Override
        public void sendRawMessage(final String str)
        {
            this.sendMessage(this.system, str);
        }

        @Override
        public void sendMessage(final String str)
        {
            this.sendMessage(this.system, str);
        }

        @Override
        public void sendMessage(final BaseComponent component)
        {
            this.sendMessage(this.system, component.toPlainText());
        }

        @Override
        public void sendRawMessage(final ChatPosition position, final String str)
        {
            this.sendMessage(this.getMarket(position), str);
        }

        @Override
        public void sendMessage(final ChatPosition position, final String str)
        {
            this.sendMessage(this.getMarket(position), str);
        }

        @Override
        public void sendSimpleColoredMessage(final ChatPosition position, final String str)
        {
            this.sendMessage(this.getMarket(position), ChatColor.translateAlternateColorCodesInString(str));
        }

        @Override
        public String toString()
        {
            return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("core", this.core).append("chat", this.chat).append("system", this.system).append("action", this.action).append("none", this.none).toString();
        }
    }
}

