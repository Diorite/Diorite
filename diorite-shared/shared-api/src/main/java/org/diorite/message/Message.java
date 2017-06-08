/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017. Diorite (by Bartłomiej Mazur (aka GotoFinal))
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

package org.diorite.message;

import javax.annotation.Nullable;

import java.util.Locale;

import org.diorite.chat.ChatMessage;
import org.diorite.sender.CommandSender;

public interface Message
{
    /**
     * Prepare message for given player, language and data.
     *
     * @param target
     *         target of message.
     * @param lang
     *         language to use if possible.
     * @param data
     *         placeholder objects to use.
     *
     * @return message object, null if message isn't enabled or can't be used.
     */
    @Nullable
    ChatMessage prepare(CommandSender target, Locale lang, MessageData<?>... data);

    /**
     * Prepare message for given language and data, note that not all messages can be used as static messages.
     *
     * @param lang
     *         language to use if possible.
     * @param data
     *         placeholder objects to use.
     *
     * @return message object, null if message isn't enabled or can't be used.
     */
    @Nullable
    ChatMessage prepare(Locale lang, MessageData<?>... data);

    /**
     * Try send this message to given {@link CommandSender}, if message is disabled method will just return false.
     *
     * @param target
     *         target of message.
     * @param lang
     *         language to use if possible.
     * @param data
     *         placeholder objects to use.
     *
     * @return true if message was send.
     */
    boolean sendMessage(CommandSender target, Locale lang, MessageData<?>... data);

    /**
     * Try broadcast this message (to all players), if message is disabled method will just return false.
     *
     * @param lang
     *         language to use if possible.
     * @param data
     *         placeholder objects to use.
     *
     * @return true if message was send.
     */
    boolean broadcastStaticMessage(Locale lang, MessageData<?>... data);
    /**
     * Try broadcast this message to selected comamnd senders, if message is disabled method will just return false.
     *
     * @param targets
     *         targets of message.
     * @param lang
     *         language to use if possible.
     * @param data
     *         placeholder objects to use.
     *
     * @return true if message was send.
     */

    boolean broadcastStaticMessage(Iterable<? extends CommandSender> targets, Locale lang, MessageData<?>... data);
    /**
     * Try broadcast this message (to all players) in target player language if possible, if message is disabled method will just return false.
     *
     * @param lang
     *         default language to use if target don't have any.
     * @param data
     *         placeholder objects to use.
     *
     * @return true if message was send.
     */
    boolean broadcastMessage(Locale lang, MessageData<?>... data);

    /**
     * Try broadcast this message to selected comamnd senders in target sender language if possible, if message is disabled method will just return false.
     *
     * @param targets
     *         targets of message.
     * @param lang
     *         default language to use if target don't have any.
     * @param data
     *         placeholder objects to use.
     *
     * @return true if message was send.
     */

    boolean broadcastMessage(Iterable<? extends CommandSender> targets, Locale lang, MessageData<?>... data);
    /**
     * Try broadcast this message (to all players) in target player language if possible, if message is disabled method will just return false.
     *
     * @param data
     *         placeholder objects to use.
     *
     * @return true if message was send.
     */

    boolean broadcastMessage(MessageData<?>... data);
    /**
     * Try broadcast this message to selected comamnd senders in target sender language if possible, if message is disabled method will just return false.
     *
     * @param targets
     *         targets of message.
     * @param data
     *         placeholder objects to use.
     *
     * @return true if message was send.
     */
    boolean broadcastMessage(Iterable<? extends CommandSender> targets, MessageData<?>... data);
}
