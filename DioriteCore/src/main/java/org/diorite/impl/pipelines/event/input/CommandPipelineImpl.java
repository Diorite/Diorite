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

package org.diorite.impl.pipelines.event.input;

import org.diorite.impl.DioriteCore;
import org.diorite.command.Command;
import org.diorite.command.sender.CommandSender;
import org.diorite.event.input.SenderCommandEvent;
import org.diorite.event.pipelines.event.input.CommandPipeline;
import org.diorite.utils.DioriteStringUtils;
import org.diorite.utils.pipeline.SimpleEventPipeline;

public class CommandPipelineImpl extends SimpleEventPipeline<SenderCommandEvent> implements CommandPipeline
{
    @Override
    public void reset_()
    {
        this.addFirst("Diorite|Cmd", (evt, pipeline) -> {
            if (evt.isCancelled() || (evt.getCommand() != null))
            {
                return;
            }
            evt.setCommand(this.core.getCommandMap().findCommand(evt.getMessage()));
        });
        this.addLast("Diorite|Exec", (evt, pipeline) -> {
            if (evt.isCancelled())
            {
                return;
            }
            final CommandSender sender = evt.getSender();
            if (evt.getCommand() == null)
            {
                sender.sendSimpleColoredMessage("&4No command: &c" + evt.getMessage());
                return;
            }
            final String[] args = DioriteStringUtils.splitArguments(evt.getMessage());
            if (args.length == 0)
            {
                return;
            }
            if (sender.isPlayer())
            {
                DioriteCore.getInstance().getConsoleSender().sendMessage(sender.getName() + ": " + Command.COMMAND_PREFIX + evt.getMessage());
            }
            //else if (sender.isCommandBlock()) TODO
            final String command = args[0];
            final String[] newArgs;
            if (args.length == 1)
            {
                newArgs = Command.EMPTY_ARGS;
            }
            else
            {
                newArgs = new String[args.length - 1];
                System.arraycopy(args, 1, newArgs, 0, args.length - 1);
            }
            evt.getCommand().tryDispatch(sender, command, newArgs);
        });
    }
}
