package org.diorite.command.sender;

import java.util.Locale;

import org.diorite.Core;
import org.diorite.chat.ChatColor;
import org.diorite.chat.DioriteMarkdownParser;
import org.diorite.chat.component.BaseComponent;

public interface CommandSender
{
    String getName();

    boolean isConsole();

    boolean isPlayer();

    boolean isCommandBlock();

    void sendRawMessage(String str);

    void sendMessage(String str);

    void sendMessage(BaseComponent component);

    Core getCore();

    /**
     * Returns prefered locale for this sender, may return null.
     *
     * @return prefered locale for this sender, may return null.
     */
    default Locale getPreferedLocale()
    {
        return null;
    }

    default void sendRawMessage(final String... strs)
    {
        if (strs != null)
        {
            for (final String str : strs)
            {
                this.sendRawMessage(str);
            }
        }
    }

    default void sendMessage(final String... strs)
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
        this.sendMessage(ChatColor.translateAlternateColorCodesInString(str));
    }

    default void sendSimpleColoredMessage(final String... strs)
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

    default void sendDioriteMessage(final String... strs)
    {
        if (strs != null)
        {
            for (final String str : strs)
            {
                this.sendDioriteMessage(str);
            }
        }
    }

    default void sendMessage(final BaseComponent... components)
    {
        if (components != null)
        {
            for (final BaseComponent component : components)
            {
                this.sendMessage(component);
            }
        }
    }
}
