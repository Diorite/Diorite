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
import org.diorite.command.CommandPriority;
import org.diorite.command.sender.CommandSender;
import org.diorite.event.EventType;

public class TimingsCmd extends SystemCommandImpl
{
    public TimingsCmd() // TODO
    {
        super("timings", (Pattern) null, CommandPriority.LOW);
        this.setDescription("Timmings");
        this.setCommandExecutor((sender, command, label, matchedPattern, args) -> {
            if (args.length() == 0)
            {
                this.showHelp(sender);
            }
            else if (args.length() == 1)
            {
                this.showTimings(sender);
            }
            else
            {
                this.showHelp(sender);
            }
        });
    }

    private void showHelp(final CommandSender sender)
    {

    }

    private void showTimings(final CommandSender s)
    {
        for (final EventType<?, ?> event : EventType.values())
        {
            s.sendSimpleColoredMessage("&7" + event.getEventClass().getSimpleName());
            event.getPipeline().getTimings().forEach((eventPipelineHandler, timingsContainer) -> s.sendSimpleColoredMessage("&7  " + timingsContainer.getName() + " &3[" + timingsContainer.getLatestTime() + "/" + timingsContainer.getAvarageTime() + "]"));
        }
    }
}
