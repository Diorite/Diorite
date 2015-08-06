package org.diorite.impl.command.defaults;

import java.util.regex.Pattern;

import org.diorite.impl.DioriteCore;
import org.diorite.impl.command.ColoredConsoleCommandSenderImpl;
import org.diorite.impl.command.ConsoleCommandSenderImpl;
import org.diorite.impl.command.SystemCommandImpl;
import org.diorite.command.CommandPriority;

public class ColoredConsoleCmd extends SystemCommandImpl
{
    public ColoredConsoleCmd()
    {
        super("coloredConsole", Pattern.compile("(set|)Col(ored|)Con(sole|)", Pattern.CASE_INSENSITIVE), CommandPriority.LOW);
        //noinspection HardcodedFileSeparator
        this.setCommandExecutor((sender, command, label, matchedPattern, args) -> sender.sendMessage("§4Invalid usage, please type /setColoredConsole <true|false>"));
        this.registerSubCommand("core", "(?<bool>(true|false))", (sender, command, label, matchedPattern, args) -> {
            final boolean bool = Boolean.parseBoolean(matchedPattern.group("bool"));
            final DioriteCore impl = (DioriteCore) sender.getServer();
            if (bool && ! (impl.getConsoleSender() instanceof ColoredConsoleCommandSenderImpl))
            {
                impl.setConsoleCommandSender(new ColoredConsoleCommandSenderImpl(impl));
            }
            else if (! bool && (impl.getConsoleSender() instanceof ColoredConsoleCommandSenderImpl))
            {
                impl.setConsoleCommandSender(new ConsoleCommandSenderImpl(impl));
            }
            sender.sendMessage("§7Colored console set to: §8" + bool);
        });
    }
}
