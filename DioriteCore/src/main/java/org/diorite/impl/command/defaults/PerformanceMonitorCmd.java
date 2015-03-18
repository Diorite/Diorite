package org.diorite.impl.command.defaults;

import java.util.regex.Pattern;

import org.diorite.impl.ServerImpl;
import org.diorite.impl.command.SystemCommandImpl;
import org.diorite.command.CommandPriority;

public class PerformanceMonitorCmd extends SystemCommandImpl
{
    public static final int ONE_MiB = 1_048_576;

    public PerformanceMonitorCmd()
    {
        super("performanceMonitor", Pattern.compile("(performance(Monitor|)|perf)", Pattern.CASE_INSENSITIVE), CommandPriority.LOW);
        this.setCommandExecutor((sender, command, label, matchedPattern, args) -> {
            final Runtime rt = Runtime.getRuntime();
            final long maxMemTemp = rt.maxMemory();
            final StringBuilder sb = new StringBuilder(512);
            sb.append("&7====== &3Performance Monitor &7======\n");
            sb.append("&7  == &3Memory &7==\n");
            sb.append("&7    Free: &3").append((rt.freeMemory() / ONE_MiB)).append(" &7MiB");
            sb.append("&7    Allocated: &3").append((rt.totalMemory() / ONE_MiB)).append(" &7MiB");
            if (maxMemTemp != Long.MAX_VALUE)
            {
                sb.append("&7    Max: &3").append((maxMemTemp / ONE_MiB)).append(" &7MiB");
            }
            else
            {
                sb.append("&7    No memory limit.");
            }
            sb.append("&7  == &3CPU &7==\n");
            sb.append("&7    Available Processors: &3").append(rt.availableProcessors()).append("\n");
            sb.append("&7  == &3Diorite &7==\n");
            sb.append("&7    Waiting chat actions: &3").append(ServerImpl.getInstance().getChatThread().getActionsSize()).append(" &7\n");
            sb.append("&7    Waiting commands actions: &3").append(ServerImpl.getInstance().getCommandsThread().getActionsSize()).append("\n");
            sender.sendSimpleColoredMessage(sb.toString());
        });
    }
}
