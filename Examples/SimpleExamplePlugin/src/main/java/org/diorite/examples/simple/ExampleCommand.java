package org.diorite.examples.simple;

import java.util.regex.Matcher;

import org.diorite.command.Arguments;
import org.diorite.command.Command;
import org.diorite.command.CommandExecutor;
import org.diorite.command.sender.CommandSender;

public class ExampleCommand implements CommandExecutor
{
    @Override
    public void runCommand(final CommandSender sender, final Command command, final String label, final Matcher matchedPattern, final Arguments args)
    {
        final String speedArg = matchedPattern.group("superArg");
        sender.sendSimpleColoredMessage("&7You used example command with superArg of " + speedArg);
        sender.sendSimpleColoredMessage("&7Type /example subcmd");
    }

    public static class ExampleSubCmdCommand implements CommandExecutor
    {

        @Override
        public void runCommand(final CommandSender sender, final Command command, final String label, final Matcher matchedPattern, final Arguments args)
        {
            sender.sendSimpleColoredMessage("&7You used subcommand of example command!");
        }
    }
}
