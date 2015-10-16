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

package org.diorite.impl.command.defaults;

import java.util.Objects;
import java.util.regex.Pattern;

import org.diorite.impl.command.SystemCommandImpl;
import org.diorite.Core;
import org.diorite.chat.ChatPosition;
import org.diorite.command.CommandPriority;

public class SayCmd extends SystemCommandImpl
{
    public SayCmd()
    {
        super("say", Pattern.compile("(say)(:(?<type>[a-z0-9]+)|)", Pattern.CASE_INSENSITIVE), CommandPriority.LOW);
        this.setCommandExecutor((sender, command, label, matchedPattern, args) -> {
            ChatPosition chatPosition;
            try
            {
                chatPosition = ChatPosition.getByEnumName(matchedPattern.group("type"));
                if (chatPosition == null)
                {
                    chatPosition = ChatPosition.CHAT;
                }
            } catch (IllegalStateException | IllegalArgumentException e)
            {
                chatPosition = ChatPosition.CHAT;
            }
            if (Objects.equals(chatPosition, ChatPosition.ACTION))
            {
                sender.getCore().sendConsoleSimpleColoredMessage(Core.PREFIX_MSG + args.asText());
            }
            sender.getCore().broadcastSimpleColoredMessage(chatPosition, Core.PREFIX_MSG + args.asText());
        });
    }
}
