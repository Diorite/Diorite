package org.diorite.impl.command.defaults;

import org.diorite.impl.command.CommandMapImpl;

public final class RegisterDefaultCommands
{
    private RegisterDefaultCommands()
    {
    }

    public static void init(final CommandMapImpl cmds)
    {
        cmds.registerCommand(new DevCmd()); // TODO: remove

        cmds.registerCommand(new SayCmd());
        cmds.registerCommand(new TpsCmd());
        cmds.registerCommand(new FlyCmd());
        cmds.registerCommand(new SaveCmd());
        cmds.registerCommand(new StopCmd());
        cmds.registerCommand(new KickCmd());
        cmds.registerCommand(new GiveCmd());
        cmds.registerCommand(new ItemCmd());
        cmds.registerCommand(new OnlineCmd());
        cmds.registerCommand(new SetTpsCmd());
        cmds.registerCommand(new GamemodeCmd());
        cmds.registerCommand(new ParticleCmd());
        cmds.registerCommand(new BroadcastCmd());
        cmds.registerCommand(new ColoredConsoleCmd());
        cmds.registerCommand(new PerformanceMonitorCmd());
    }
}
