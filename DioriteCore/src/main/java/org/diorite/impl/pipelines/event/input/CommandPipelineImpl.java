package org.diorite.impl.pipelines.event.input;

import org.diorite.impl.ServerImpl;
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
            evt.setCommand(this.server.getCommandMap().findCommand(evt.getMessage()));
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
                ServerImpl.getInstance().getConsoleSender().sendMessage(sender.getName() + ": " + Command.COMMAND_PREFIX + evt.getMessage());
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
