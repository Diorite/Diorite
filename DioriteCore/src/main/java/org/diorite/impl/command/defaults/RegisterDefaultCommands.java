package org.diorite.impl.command.defaults;

import org.diorite.impl.command.CommandMapImpl;

public final class RegisterDefaultCommands
{
    private RegisterDefaultCommands()
    {
    }

    public static void init(final CommandMapImpl cmds)
    {
        cmds.registerCommand(new TpsCmd());
        cmds.registerCommand(new SetTpsCmd());
        cmds.registerCommand(new ColoredConsoleCmd());
    }
}
