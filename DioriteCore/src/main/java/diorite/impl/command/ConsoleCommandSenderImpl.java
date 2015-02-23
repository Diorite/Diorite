package diorite.impl.command;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import diorite.ChatColor;
import diorite.Server;
import diorite.command.ConsoleCommandSender;
import diorite.impl.ServerImpl;

public class ConsoleCommandSenderImpl implements ConsoleCommandSender
{
    private final ServerImpl server;

    public ConsoleCommandSenderImpl(final ServerImpl server)
    {
        this.server = server;
    }

    @Override
    public void sendMessage(final String str)
    {
        System.out.println(ChatColor.stripColor(str));
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
