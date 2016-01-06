package org.diorite.command.sender;

import org.diorite.chat.ChatColor;
import org.diorite.chat.ChatPosition;
import org.diorite.chat.DioriteMarkdownParser;
import org.diorite.chat.component.BaseComponent;
import org.diorite.chat.component.TextComponent;

/**
 * Represent message output with support for {@link ChatPosition} enum.
 */
public interface HumanMessageOutput extends MessageOutput
{
    @Override
    default void sendMessage(final String str)
    {
        this.sendMessage(TextComponent.fromLegacyText(str));
    }

    @Override
    default void sendRawMessage(final String str)
    {
        this.sendMessage(ChatPosition.SYSTEM, new TextComponent(str));
    }

    @Override
    default void sendMessage(final BaseComponent component)
    {
        this.sendMessage(ChatPosition.SYSTEM, component);
    }

    void sendMessage(ChatPosition position, BaseComponent component);

    default void sendRawMessage(final ChatPosition position, final String str)
    {
        this.sendMessage(position, new TextComponent(str));
    }

    default void sendRawMessage(final ChatPosition position, final String... strs)
    {
        if (strs != null)
        {
            for (final String str : strs)
            {
                this.sendRawMessage(position, str);
            }
        }
    }

    default void sendMessage(final ChatPosition position, final String str)
    {
        this.sendMessage(position, TextComponent.fromLegacyText(str));
    }

    default void sendMessage(final ChatPosition position, final String[] strs)
    {
        if (strs != null)
        {
            for (final String str : strs)
            {
                this.sendMessage(position, str);
            }
        }
    }

    default void sendSimpleColoredMessage(final ChatPosition position, final String str)
    {
        this.sendMessage(position, ChatColor.translateAlternateColorCodes(str));
    }

    default void sendSimpleColoredMessage(final ChatPosition position, final String[] strs)
    {
        if (strs != null)
        {
            for (final String str : strs)
            {
                this.sendSimpleColoredMessage(position, str);
            }
        }
    }

    default void sendDioriteMessage(final ChatPosition position, final String str)
    {
        this.sendMessage(position, DioriteMarkdownParser.parse(str, false));
    }

    default void sendDioriteMessage(final ChatPosition position, final String[] strs)
    {
        if (strs != null)
        {
            for (final String str : strs)
            {
                this.sendDioriteMessage(position, str);
            }
        }
    }
    default void sendMessage(final ChatPosition position, final BaseComponent[] components)
    {
        if (components != null)
        {
            for (final BaseComponent component : components)
            {
                this.sendMessage(position, component);
            }
        }
    }
}
