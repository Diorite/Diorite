package org.diorite.impl.command.defaults;

import java.util.regex.Pattern;

import org.diorite.impl.command.SystemCommandImpl;
import org.diorite.command.CommandPriority;

public class StopCmd extends SystemCommandImpl
{
    public StopCmd()
    {
        super("stop", (Pattern) null, CommandPriority.LOW);
        this.setCommandExecutor((sender, command, label, matchedPattern, args) -> {
            if (sender.isConsole()) // TODO: temp
            {
                sender.sendSimpleColoredMessage("&4Stopping... ");
                sender.getServer().stop();
                return;
            }
            sender.sendSimpleColoredMessage("&4No permissions...");
        });
    }
}
