package org.diorite.impl.command.defaults;

import java.util.regex.Pattern;

import org.diorite.command.CommandPriority;
import org.diorite.impl.command.SystemCommandImpl;

public class PerformanceMonitorCmd extends SystemCommandImpl
{
    public PerformanceMonitorCmd()
    {
        super("coloredConsole", Pattern.compile("(performance(Monitor|)|perf)", Pattern.CASE_INSENSITIVE), CommandPriority.LOW);
        this.setCommandExecutor((sender, command, label, matchedPattern, args) -> {
            Runtime rt = Runtime.getRuntime();
            sender.sendSimpleColoredMessage("&7====== &3Performance Monitor &7======");
//            rt.availableProcessors() TODO: finish.
        });
    }
}
