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

/**
 * Represents message receiver.
 */
public interface MessageReceiver
{
    /**
     * Method for checking if this object is instance of another class. As replacement for instanceof.
     *
     * @param type
     *         type to check
     *
     * @return true if this is instance of given class.
     */
    default boolean is(Class<? extends MessageReceiver> type)
    {
        return type.isInstance(this);
    }

    /**
     * Send message to this receiver.
     *
     * @param chatMessage
     *         message to send.
     */
    default void sendMessage(ChatMessage chatMessage)
    {
        chatMessage.sendTo(this);
    }

    /**
     * Send message to this receiver.
     *
     * @param messageType
     *         type of message.
     * @param chatMessage
     *         message to send.
     */
    default void sendMessage(ChatMessageType messageType, ChatMessage chatMessage)
    {
        chatMessage.sendTo(messageType, this);
    }

    /**
     * Returns output object for this receiver, used to add filters etc.
     *
     * @return output object for this receiver.
     */
    MessageOutput getOutput();

    /**
     * Set new output object for this receiver.
     *
     * @param output
     *         new output object.
     */
    void setOutput(MessageOutput output);
}
