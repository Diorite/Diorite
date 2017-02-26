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

import javax.annotation.Nullable;

import java.util.Locale;
import java.util.function.UnaryOperator;

/**
 * Represent object that process messages, every message receiver contains own MessageOutput that can be overridden.
 */
public interface MessageOutput
{
    /**
     * Message output that ignores all messages.
     */
    MessageOutput IGNORE = new IgnoreMessageOutput();

    /**
     * Adds filter to this message output. <br>
     * If filter return null message will be canceled.
     *
     * @param filter
     *         filter to add.
     */
    void addFilter(UnaryOperator<ChatMessage> filter);

    /**
     * Removes given filter.
     *
     * @param filter
     *         filter to remove.
     *
     * @return true if any filter was removed.
     */
    boolean removeFilter(Object filter);

    /**
     * Sends given message on given position if supported, if output don't support given message type it may be displayed as other type.
     *
     * @param type
     *         type of message.
     * @param component
     *         message to send.
     */
    void sendMessage(ChatMessageType type, ChatMessage component);

    /**
     * Returns preferred locale for this sender, may return null.
     *
     * @return preferred locale for this sender, may return null.
     */
    @Nullable
    default Locale getPreferredLocale()
    {
        return null;
    }

    /**
     * Set preferred locale for this sender, may not do anything to sender if it don't support locales.
     *
     * @param locale
     *         new prefered locale.
     */
    default void setPreferredLocale(@Nullable Locale locale)
    {
    }

}
