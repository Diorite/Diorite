package org.diorite.impl.command.defaults;

import java.util.regex.Pattern;

import org.diorite.Diorite;
import org.diorite.impl.command.SystemCommandImpl;
import org.diorite.command.CommandPriority;

public class VersionCmd extends SystemCommandImpl
{
    public VersionCmd()
    {
        super("version", (Pattern) null, CommandPriority.LOW);
        this.setCommandExecutor((sender, command, label, matchedPattern, args) -> {
            sender.sendSimpleColoredMessage("This server is running Diorite " + Diorite.getVersion());
        });
    }
}
