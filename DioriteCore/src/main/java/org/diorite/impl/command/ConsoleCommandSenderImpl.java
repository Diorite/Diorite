package org.diorite.impl.command;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.DioriteCore;
import org.diorite.Server;
import org.diorite.chat.ChatColor;
import org.diorite.chat.component.BaseComponent;
import org.diorite.command.sender.ConsoleCommandSender;

public class ConsoleCommandSenderImpl implements ConsoleCommandSender
{
    private final DioriteCore server;

    public ConsoleCommandSenderImpl(final DioriteCore server)
    {
        this.server = server;
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
    public Server getServer()
    {
        return this.server;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("server", this.server).toString();
    }
}
