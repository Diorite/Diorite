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

import java.util.regex.Pattern;

import org.diorite.impl.DioriteCore;
import org.diorite.impl.command.ColoredConsoleCommandSenderImpl;
import org.diorite.impl.command.ConsoleCommandSenderImpl;
import org.diorite.impl.command.SystemCommandImpl;
import org.diorite.command.CommandPriority;

public class ColoredConsoleCmd extends SystemCommandImpl
{
    public ColoredConsoleCmd()
    {
        super("coloredConsole", Pattern.compile("(set|)Col(ored|)Con(sole|)", Pattern.CASE_INSENSITIVE), CommandPriority.LOW);
        //noinspection HardcodedFileSeparator
        this.setCommandExecutor((sender, command, label, matchedPattern, args) -> sender.sendMessage("§4Invalid usage, please type /setColoredConsole <true|false>"));
        this.registerSubCommand("core", "(?<bool>(true|false))", (sender, command, label, matchedPattern, args) -> {
            final boolean bool = Boolean.parseBoolean(matchedPattern.group("bool"));
            final DioriteCore impl = (DioriteCore) sender.getCore();
            if (bool && ! (impl.getConsoleSender() instanceof ColoredConsoleCommandSenderImpl))
            {
                impl.setConsoleCommandSender(new ColoredConsoleCommandSenderImpl(impl));
            }
            else if (! bool && (impl.getConsoleSender() instanceof ColoredConsoleCommandSenderImpl))
            {
                impl.setConsoleCommandSender(new ConsoleCommandSenderImpl(impl));
            }
            sender.sendMessage("§7Colored console set to: §8" + bool);
        });
    }
}
