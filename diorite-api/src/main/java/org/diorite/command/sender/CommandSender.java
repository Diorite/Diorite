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

package org.diorite.command.sender;

import java.util.Locale;

import org.diorite.Core;
import org.diorite.chat.ChatPosition;
import org.diorite.chat.component.BaseComponent;
import org.diorite.permissions.ServerOperator;

/**
 * Represent source of command.
 */
public interface CommandSender extends ServerOperator, MessageOutput
{
    /**
     * Returns name of command sender.
     *
     * @return name of command sender.
     */
    String getName();

    /**
     * Returns true if sender is a console. <br>
     * So it must implement {@link ConsoleCommandSender} interface.
     *
     * @return true if sender is a console.
     */
    default boolean isConsole()
    {
        return false;
    }

    /**
     * Returns true if sender is a player.<br>
     * So it must implement {@link PlayerCommandSender} interface.
     *
     * @return true if sender is a player.
     */
    default boolean isPlayer()
    {
        return false;
    }

    /**
     * Returns true if sender is a command block.<br>
     * So it must implement {@link CommandBlockSender} interface, but it may be command block in minecart.
     *
     * @return true if sender is a command block.
     */
    default boolean isCommandBlock()
    {
        return false;
    }

    /**
     * Returns true if sender is a sign block.<br>
     * So it must implement {@link CommandBlockSender} interface, but it may be command block in minecart.
     *
     * @return true if sender is a sign block.
     */
    default boolean isSign()
    {
        return false;
    }

    /**
     * Returns message output for this sender.
     *
     * @return message output for this sender.
     */
    MessageOutput getMessageOutput();

    /**
     * Sets message output for this command sender. <br>
     * Message output only need implement {@link MessageOutput} interface, it don't need to implement/extends any implementation classes.
     * So you can create own message output without any problems. <br>
     * Message output can't be null, use {@link MessageOutput#IGNORE} instead.
     *
     * @param messageOutput new message output.
     */
    void setMessageOutput(MessageOutput messageOutput);

    /**
     * Returns core instance.
     *
     * @return core instance.
     */
    Core getCore();

    @Override
    default void sendMessage(final ChatPosition position, final BaseComponent component)
    {
        this.getMessageOutput().sendMessage(position, component);
    }

    @Override
    default void sendRawMessage(final String str)
    {
        this.getMessageOutput().sendRawMessage(str);
    }

    @Override
    default void sendMessage(final String str)
    {
        this.getMessageOutput().sendMessage(str);
    }

    @Override
    default void sendMessage(final BaseComponent component)
    {
        this.getMessageOutput().sendMessage(component);
    }

    @Override
    default Locale getPreferedLocale()
    {
        return this.getMessageOutput().getPreferedLocale();
    }

    @Override
    default void setPreferedLocale(final Locale locale)
    {
        this.getMessageOutput().setPreferedLocale(locale);
    }

    @Override
    default void sendRawMessage(final String... strs)
    {
        this.getMessageOutput().sendRawMessage(strs);
    }

    @Override
    default void sendMessage(final String... strs)
    {
        this.getMessageOutput().sendMessage(strs);
    }

    @Override
    default void sendSimpleColoredMessage(final String str)
    {
        this.getMessageOutput().sendSimpleColoredMessage(str);
    }

    @Override
    default void sendSimpleColoredMessage(final String... strs)
    {
        this.getMessageOutput().sendSimpleColoredMessage(strs);
    }

    @Override
    default void sendDioriteMessage(final String str)
    {
        this.getMessageOutput().sendDioriteMessage(str);
    }

    @Override
    default void sendDioriteMessage(final String... strs)
    {
        this.getMessageOutput().sendDioriteMessage(strs);
    }

    @Override
    default void sendMessage(final BaseComponent... components)
    {
        this.getMessageOutput().sendMessage(components);
    }
}
