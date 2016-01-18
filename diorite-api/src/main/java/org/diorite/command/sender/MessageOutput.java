package org.diorite.command.sender;

import java.util.Locale;

import org.diorite.chat.ChatColor;
import org.diorite.chat.ChatPosition;
import org.diorite.chat.DioriteMarkdownParser;
import org.diorite.chat.component.BaseComponent;
import org.diorite.chat.component.TextComponent;

/**
 * Represent object that process messages, every command sender contains own MessageOutput that can be overriden.
 */
public interface MessageOutput
{
    /**
     * Message output that ignore all messages.
     */
    MessageOutput IGNORE = new MessageOutput()
    {
        @Override
        public void sendMessage(final ChatPosition position, final BaseComponent component)
        {

        }

        @Override
        public void sendRawMessage(final String str)
        {

        }

        @Override
        public void sendMessage(final String str)
        {

        }

        @Override
        public void sendMessage(final BaseComponent component)
        {

        }

        @Override
        public void sendRawMessage(final String... strs)
        {

        }

        @Override
        public void sendMessage(final String... strs)
        {

        }

        @Override
        public void sendSimpleColoredMessage(final String str)
        {

        }

        @Override
        public void sendSimpleColoredMessage(final String... strs)
        {

        }

        @Override
        public void sendDioriteMessage(final String str)
        {

        }

        @Override
        public void sendDioriteMessage(final String... strs)
        {

        }

        @Override
        public void sendMessage(final BaseComponent... components)
        {

        }

        @Override
        public void sendRawMessage(final ChatPosition position, final String str)
        {

        }

        @Override
        public void sendRawMessage(final ChatPosition position, final String... strs)
        {

        }

        @Override
        public void sendMessage(final ChatPosition position, final String str)
        {

        }

        @Override
        public void sendMessage(final ChatPosition position, final String[] strs)
        {

        }

        @Override
        public void sendSimpleColoredMessage(final ChatPosition position, final String str)
        {

        }

        @Override
        public void sendSimpleColoredMessage(final ChatPosition position, final String[] strs)
        {

        }

        @Override
        public void sendDioriteMessage(final ChatPosition position, final String str)
        {

        }

        @Override
        public void sendDioriteMessage(final ChatPosition position, final String[] strs)
        {

        }

        @Override
        public void sendMessage(final ChatPosition position, final BaseComponent[] components)
        {

        }

//        @Override
//        public Locale getPreferedLocale()
//        {
//            return null;
//        }
//
//        @Override
//        public void setPreferedLocale(final Locale locale)
//        {
//
//        }
    };

    /**
     * Sends given message on given position if supported, if output don't support given position message will be displayed in other position.
     *
     * @param position  position of message.
     * @param component message to send.
     */
    void sendMessage(ChatPosition position, BaseComponent component);

    /**
     * Sends raw message to this command sender if possible. <br>
     * Raw messages don't use {@link org.diorite.chat.component.TextComponent#fromLegacyText(String)}
     *
     * @param str message to send.
     */
    default void sendRawMessage(final String str)
    {
        this.sendMessage(ChatPosition.SYSTEM, str);
    }

    /**
     * Sends message to this command sender if possible. <br>
     *
     * @param str message to send.
     */
    default void sendMessage(final String str)
    {
        this.sendMessage(ChatPosition.SYSTEM, str);
    }

    /**
     * Sends message to this command sender if possible. <br>
     *
     * @param component message to send.
     */
    default void sendMessage(final BaseComponent component)
    {
        this.sendMessage(ChatPosition.SYSTEM, component);
    }

    /**
     * Sends raw messages to this command sender if possible. <br>
     *
     * @param strs messages to send.
     */
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

    /**
     * Sends messages to this command sender if possible. <br>
     *
     * @param strs messages to send.
     */
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

    /**
     * Sends messages to this command sender if possible. <br>
     *
     * @param components messages to send.
     */
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

    /**
     * Sends message with color codes using '&amp;' char.
     *
     * @param str message to send.
     */
    default void sendSimpleColoredMessage(final String str)
    {
        this.sendMessage(ChatColor.translateAlternateColorCodesInString(str));
    }

    /**
     * Sends messages with color codes using '&amp;' char.
     *
     * @param strs messages to send.
     */
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

    /**
     * Sends message using custom diorite parser.
     *
     * @param str message to send.
     */
    default void sendDioriteMessage(final String str)
    {
        this.sendMessage(DioriteMarkdownParser.parse(str, false));
    }

    /**
     * Sends messages using custom diorite parser.
     *
     * @param strs messages to send.
     */
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

    /**
     * Sends given raw message on given position if supported, if output don't support given position message will be displayed in other position.
     *
     * @param position position of message.
     * @param str      message to send.
     */
    default void sendRawMessage(final ChatPosition position, final String str)
    {
        this.sendMessage(position, new TextComponent(str));
    }

    /**
     * Sends given messages on given position if supported, if output don't support given position message will be displayed in other position.
     *
     * @param position position of message.
     * @param strs     messages to send.
     */
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

    /**
     * Sends given message on given position if supported, if output don't support given position message will be displayed in other position.
     *
     * @param position position of message.
     * @param str      message to send.
     */
    default void sendMessage(final ChatPosition position, final String str)
    {
        this.sendMessage(position, TextComponent.fromLegacyText(str));
    }

    /**
     * Sends given messages on given position if supported, if output don't support given position message will be displayed in other position.
     *
     * @param position position of message.
     * @param strs     messages to send.
     */
    default void sendMessage(final ChatPosition position, final String... strs)
    {
        if (strs != null)
        {
            for (final String str : strs)
            {
                this.sendMessage(position, str);
            }
        }
    }

    /**
     * Sends given message (colored using '&amp;' char) on given position if supported, if output don't support given position message will be displayed in other position.
     *
     * @param position position of message.
     * @param str      message to send.
     */
    default void sendSimpleColoredMessage(final ChatPosition position, final String str)
    {
        this.sendMessage(position, ChatColor.translateAlternateColorCodes(str));
    }

    /**
     * Sends given messages (colored using '&amp;' char) on given position if supported, if output don't support given position message will be displayed in other position.
     *
     * @param position position of message.
     * @param strs     messages to send.
     */
    default void sendSimpleColoredMessage(final ChatPosition position, final String... strs)
    {
        if (strs != null)
        {
            for (final String str : strs)
            {
                this.sendSimpleColoredMessage(position, str);
            }
        }
    }

    /**
     * Sends given message (parsed using Diorite Markdown parser.) on given position if supported, if output don't support given position message will be displayed in other position.
     *
     * @param position position of message.
     * @param str      message to send.
     */
    default void sendDioriteMessage(final ChatPosition position, final String str)
    {
        this.sendMessage(position, DioriteMarkdownParser.parse(str, false));
    }

    /**
     * Sends given messages (parsed using Diorite Markdown parser.) on given position if supported, if output don't support given position message will be displayed in other position.
     *
     * @param position position of message.
     * @param strs     messages to send.
     */
    default void sendDioriteMessage(final ChatPosition position, final String... strs)
    {
        if (strs != null)
        {
            for (final String str : strs)
            {
                this.sendDioriteMessage(position, str);
            }
        }
    }

    /**
     * Sends given messages on given position if supported, if output don't support given position message will be displayed in other position.
     *
     * @param position   position of message.
     * @param components messages to send.
     */
    default void sendMessage(final ChatPosition position, final BaseComponent... components)
    {
        if (components != null)
        {
            for (final BaseComponent component : components)
            {
                this.sendMessage(position, component);
            }
        }
    }

    /**
     * Returns prefered locale for this sender, may return null.
     *
     * @return prefered locale for this sender, may return null.
     */
    default Locale getPreferedLocale()
    {
        return null;
    }

    /**
     * Set prefered locale for this sender, may not do anything to sender if it don't support locales.
     *
     * @param locale new prefered locale.
     */
    default void setPreferedLocale(final Locale locale)
    {
    }
}
