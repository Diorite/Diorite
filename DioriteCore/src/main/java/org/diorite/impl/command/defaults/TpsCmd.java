package org.diorite.impl.command.defaults;

import java.util.regex.Pattern;

import org.diorite.impl.command.SystemCommandImpl;
import org.diorite.command.CommandPriority;

public class TpsCmd extends SystemCommandImpl
{
    public TpsCmd()
    { // TODO: display speed of some events, like entity updates
        super("tps", (Pattern) null, CommandPriority.LOW);
        this.setCommandExecutor((sender, command, label, matchedPattern, args) -> sender.sendMessage("Not implemented yet"));
    }
}
