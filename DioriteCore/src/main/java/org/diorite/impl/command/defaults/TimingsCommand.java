package org.diorite.impl.command.defaults;

import java.util.regex.Pattern;

import org.diorite.impl.command.SystemCommandImpl;
import org.diorite.command.CommandPriority;
import org.diorite.command.sender.CommandSender;
import org.diorite.event.EventType;

public class TimingsCommand extends SystemCommandImpl
{
    public TimingsCommand() // TODO
    {
        super("timings", (Pattern) null, CommandPriority.LOW);
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
