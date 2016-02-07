/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2016. Diorite (by Bartłomiej Mazur (aka GotoFinal))
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

import java.util.regex.Pattern;

import org.diorite.impl.command.SystemCommandImpl;
import org.diorite.Diorite;
import org.diorite.cfg.messages.DioriteMessages;
import org.diorite.command.CommandPriority;
import org.diorite.command.MainCommand;

public class HelpCmd extends SystemCommandImpl
{
    public HelpCmd()
    {
        super("help", (Pattern) null, CommandPriority.LOW);
        this.setDescription("Command list");
        this.setCommandExecutor((sender, command, label, matchedPattern, args) -> {
            DioriteMessages.sendMessage(DioriteMessages.MSG_HELP_HEADER, sender, sender.getPreferedLocale());
            for(final MainCommand cmd : Diorite.getCommandMap().getCommandMap().values())
            {
                String desc = cmd.getDescription();
                if(desc == null)
                {
                    desc = DioriteMessages.getMessage(DioriteMessages.MSG_HELP_NODESC).toString();
                }

                sender.sendSimpleColoredMessage("&3/" + cmd.getName() + " &7- " + desc);
            }
        });
    }
}