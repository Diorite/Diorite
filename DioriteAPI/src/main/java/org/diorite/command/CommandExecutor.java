package org.diorite.command;

import java.util.List;
import java.util.regex.Matcher;
import java.util.stream.Collectors;

import org.diorite.command.sender.CommandSender;

@FunctionalInterface
public interface CommandExecutor
{
    void runCommand(CommandSender sender, final Command command, final String label, Matcher matchedPattern, Arguments args);

    default List<String> onTabComplete(final CommandSender sender, final Command command, final String label, final Matcher matchedPattern, final Arguments args)
    {
        return command.getSubCommandMap().keySet().stream().collect(Collectors.toList());
    }
}
