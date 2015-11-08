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

package org.diorite.chat;

import org.diorite.utils.SimpleEnum;
import org.diorite.utils.SimpleEnum.ASimpleEnum;

import gnu.trove.map.TIntObjectMap;

public class ChatPosition extends ASimpleEnum<ChatPosition>
{
    static
    {
        init(ChatPosition.class, 3);
    }

    public static final ChatPosition CHAT   = new ChatPosition("CHAT");
    public static final ChatPosition SYSTEM = new ChatPosition("SYSTEM");
    public static final ChatPosition ACTION = new ChatPosition("ACTION");

    public ChatPosition(final String enumName, final int enumId)
    {
        super(enumName, enumId);
    }

    public ChatPosition(final String enumName)
    {
        super(enumName);
    }

    /**
     * Register new {@link ChatPosition} entry in this enum.
     *
     * @param element new element to register.
     */
    public static void register(final ChatPosition element)
    {
        ASimpleEnum.register(ChatPosition.class, element);
    }

    /**
     * Get one of {@link ChatPosition} entry by its ordinal id.
     *
     * @param ordinal ordinal id of entry.
     *
     * @return one of entry or null.
     */
    public static ChatPosition getByEnumOrdinal(final int ordinal)
    {
        return getByEnumOrdinal(ChatPosition.class, ordinal);
    }

    /**
     * Get one of {@link ChatPosition} entry by its name.
     *
     * @param name name of entry.
     *
     * @return one of entry or null.
     */
    public static ChatPosition getByEnumName(final String name)
    {
        return getByEnumName(ChatPosition.class, name);
    }

    /**
     * @return all values in array.
     */
    public static ChatPosition[] values()
    {
        final TIntObjectMap<SimpleEnum<?>> map = getByEnumOrdinal(ChatPosition.class);
        return (ChatPosition[]) map.values(new ChatPosition[map.size()]);
    }

    static
    {
        ChatPosition.register(CHAT);
        ChatPosition.register(SYSTEM);
        ChatPosition.register(ACTION);
    }
}
