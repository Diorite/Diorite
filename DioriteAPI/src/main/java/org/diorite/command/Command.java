package org.diorite.command;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.diorite.command.sender.CommandSender;

public interface Command
{
    String COMMAND_PLUGIN_SEPARATOR = "::";
    @SuppressWarnings("HardcodedFileSeparator")
    String COMMAND_PREFIX      = "/";
    @SuppressWarnings("HardcodedFileSeparator")
    char   COMMAND_PREFIX_CHAR = '/';
    String[] EMPTY_ARGS = new String[0];

    String getName();

    Pattern getPattern();

    void setPattern(String pattern);

    void setPattern(Pattern pattern);

    ExceptionHandler getExceptionHandler();

    void setExceptionHandler(ExceptionHandler exceptionHandler);

    CommandExecutor getCommandExecutor();

    void setCommandExecutor(CommandExecutor commandExecutor);

    SubCommand registredSubCommand(String name, Pattern pattern);

    SubCommand registredSubCommand(String name, String pattern);

    SubCommand registredSubCommand(String name, Collection<String> aliases);

    SubCommand registredSubCommand(String name, Pattern pattern, CommandExecutor commandExecutor);

    SubCommand registredSubCommand(String name, String pattern, CommandExecutor commandExecutor);

    SubCommand registredSubCommand(String name, Collection<String> aliases, CommandExecutor commandExecutor);

    SubCommand registredSubCommand(String name, Pattern pattern, CommandExecutor commandExecutor, ExceptionHandler exceptionHandler);

    SubCommand registredSubCommand(String name, String pattern, CommandExecutor commandExecutor, ExceptionHandler exceptionHandler);

    SubCommand registredSubCommand(String name, Collection<String> aliases, CommandExecutor commandExecutor, ExceptionHandler exceptionHandler);

    void registredSubCommand(SubCommand subCommand);

    SubCommand unregistredSubCommand(String subCommand);

    Map<String, SubCommand> getSubCommandMap();

    boolean matches(String name);

    Matcher matcher(String name);

    boolean tryDispatch(CommandSender sender, String label, String[] args);

    List<String> tabComplete(CommandSender sender, String label, Matcher matchedPattern, String[] args);

    void dispatch(CommandSender sender, String label, Matcher matchedPattern, String[] args);
}
