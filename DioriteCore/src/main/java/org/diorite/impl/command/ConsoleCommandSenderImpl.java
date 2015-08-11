package org.diorite.impl.command;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.DioriteCore;
import org.diorite.Core;
import org.diorite.chat.ChatColor;
import org.diorite.chat.component.BaseComponent;
import org.diorite.command.sender.ConsoleCommandSender;

public class ConsoleCommandSenderImpl implements ConsoleCommandSender
{
    private final DioriteCore core;

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
}
