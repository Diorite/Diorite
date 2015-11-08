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

package org.diorite.impl.command;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.DioriteCore;
import org.diorite.Core;
import org.diorite.Diorite;
import org.diorite.chat.ChatColor;
import org.diorite.chat.component.BaseComponent;
import org.diorite.command.sender.ConsoleCommandSender;
import org.diorite.permissions.PermissionsContainer;
import org.diorite.utils.lazy.LazyValue;

public class ConsoleCommandSenderImpl implements ConsoleCommandSender
{
    private final DioriteCore core;
    private final LazyValue<PermissionsContainer> permissionsContainer = new LazyValue<>(() -> Diorite.getServerManager().getPermissionsManager().createContainer(this));

    public ConsoleCommandSenderImpl(final DioriteCore core)
    {
        this.core = core;
    }

    @Override
    public void sendMessage(final String str)
    {
        System.out.println(ChatColor.stripColor(str));
    }

    @Override
    public void sendMessage(final BaseComponent component)
    {
        this.sendMessage(component.toLegacyText());
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
}
