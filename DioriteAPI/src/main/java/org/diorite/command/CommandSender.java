package org.diorite.command;

import org.diorite.chat.ChatColor;
import org.diorite.Server;
import org.diorite.chat.component.BaseComponent;
import org.diorite.chat.DioriteMarkdownParser;

public interface CommandSender
{
    String getName();

    boolean isConsole();

    boolean isPlayer();

    boolean isCommandBlock();

    void sendMessage(String str);

    default void sendMessage(final String[] strs)
    {
        if (strs != null)
        {
            for (final String str : strs)
            {
                this.sendMessage(str);
            }
        }
    }

    default void sendSimpleColoredMessage(final String str)
    {
        this.sendMessage(ChatColor.translateAlternateColorCodes(str));
    }

    default void sendSimpleColoredMessage(final String[] strs)
    {
        if (strs != null)
        {
            for (final String str : strs)
            {
                this.sendSimpleColoredMessage(str);
            }
        }
    }

    default void sendDioriteMessage(final String str)
    {
        this.sendMessage(DioriteMarkdownParser.parse(str, false));
    }

    default void sendDioriteMessage(final String[] strs)
    {
        if (strs != null)
        {
            for (final String str : strs)
            {
                this.sendDioriteMessage(str);
            }
        }
    }

    void sendMessage(BaseComponent component);

    default void sendMessage(final BaseComponent[] components)
    {
        if (components != null)
        {
            for (final BaseComponent component : components)
            {
                this.sendMessage(component);
            }
        }
    }

    Server getServer();
}
