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

import org.diorite.config.serialization.Serialization;
import org.diorite.config.serialization.StringSerializer;

/**
 * Represent chat handler, something that can send given message to given player.
 */
public interface ChatService
{
    /**
     * Sens given message to given receiver. <br>
     * Type of message is {@link ChatMessageType#SYSTEM} by default.
     *
     * @param messageReceiver
     *         receiver of message.
     * @param message
     *         message to send.
     */
    default void sendMessage(MessageReceiver messageReceiver, ChatMessage message)
    {
        this.sendMessage(ChatMessageType.SYSTEM, messageReceiver, message);
    }

    /**
     * Sens given message to given receiver.
     *
     * @param type
     *         type of message.
     * @param messageReceiver
     *         receiver of message.
     * @param message
     *         message to send.
     */
    void sendMessage(ChatMessageType type, MessageReceiver messageReceiver, ChatMessage message);

    /**
     * Returns instance of chat service.
     *
     * @return instance of chat service.
     */
    static ChatService getInstance()
    {
        ChatService chatService = ChatServiceHandler.chatService;
        if (chatService == null)
        {
            throw new IllegalStateException("Chat is not implemented!");
        }
        return chatService;
    }

    /**
     * Set implementation of chat service, it can't be changed later.
     *
     * @param implementation
     *         implementation of chat service.
     */
    static void setInstance(ChatService implementation)
    {
        if (ChatServiceHandler.chatService != null)
        {
            throw new IllegalStateException("Chat is already implemented!");
        }
        ChatServiceHandler.chatService = implementation;

        Serialization.getInstance().registerStringSerializer(StringSerializer.of(ChatMessage.class, ChatMessage::toJson, ChatMessage::fromJson));
    }

}
