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

package org.diorite.chat;

import com.google.gson.JsonElement;

/**
 * Represents chat message, chat messages can contain multiple formatting, including hover and click events.
 */
public interface ChatMessage extends Cloneable
{
    /**
     * Sends this message to given receiver.
     *
     * @param messageReceiver
     *         receiver of message.
     */
    default void sendTo(MessageReceiver messageReceiver)
    {
        this.sendTo(ChatMessageType.SYSTEM, messageReceiver);
    }

    /**
     * Sends this message to given receiver.
     *
     * @param type
     *         type of message.
     * @param messageReceiver
     *         receiver of message.
     */
    void sendTo(ChatMessageType type, MessageReceiver messageReceiver);

    ChatMessage addEvent(String text, ChatMessageEvent event); // hover or click
    ChatMessage removeEvent(String... text);
    ChatMessage removeEvent(ChatMessageEvent... event);
    ChatMessage clearEvents();

    ChatMessage duplicate();

//    ChatMessage append(String text);
//    ChatMessage append(ChatMessage text);
//    ChatMessage prepend(String text);
//    ChatMessage prepend(ChatMessage text);
//
//    ChatMessage replaceAll(String text, ChatMessage replacement);
//    ChatMessage replaceFirst(String text, ChatMessage replacement);
//    ChatMessage replaceLast(String text, ChatMessage replacement);
//
//    ChatMessage removeAll(String text);
//    ChatMessage removeFirst(String text);
//    ChatMessage removeLast(String text);
//
//    boolean contains(String text);
//    boolean containsIgnoreCase(String text);
//
//    ChatMessage toLowerCase();
//    ChatMessage toUpperCase();
//
//    ChatMessage[] split(String token);
//    ChatMessage[] split(String token, int max);

    String toLegacyText();
    String toPlainText();
    String toJson();
    JsonElement toJsonElement();
//    String toMarkup();

    static ChatMessage fromLegacy(String s)
    {
        return new ChatMessageImpl();
    }
    static ChatMessage fromString(String s) {return null;}
    //    static ChatMessage fromMarkup(String s) {return null;}
    static ChatMessage fromJson(String s) {return null;}
}
