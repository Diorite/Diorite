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

import org.diorite.impl.command.SystemCommandImpl;
import org.diorite.Core;
import org.diorite.command.CommandPriority;

public class SetTpsCmd extends SystemCommandImpl
{
    public SetTpsCmd()
    {
        super("setTps", (Pattern) null, CommandPriority.LOW);
        //noinspection HardcodedFileSeparator
        this.setCommandExecutor((sender, command, label, matchedPattern, args) -> sender.sendMessage("§4Invalid usage, please type /setTps <number from 1 to 99>"));
        this.registerSubCommand("core", Pattern.compile("(?<tps>([0-9]{2})|([1-9]))((-multi=(?<multi>\\d+((\\.\\d+)|)))|)", Pattern.CASE_INSENSITIVE), (sender, command, label, matchedPattern, args) -> {
            final int tps = Integer.parseInt(matchedPattern.group("tps"));
            final String temp = matchedPattern.group("multi");
            final double multi = (temp == null) ? (((double) Core.DEFAULT_TPS) / tps) : Double.parseDouble(temp);
            sender.sendSimpleColoredMessage("&7TPS set to: &3" + tps + "&7, and server speed to: &3" + TpsCmd.format.format(multi));
            sender.getCore().setTps(tps);
            sender.getCore().setSpeedMutli(multi);
            sender.getCore().resetRecentTps();
        });
    }
}
