package org.diorite.command;

import java.util.Collection;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public interface Command
{
    @SuppressWarnings("HardcodedFileSeparator")
    String[] EMPTY_ARGS = new String[0];

    String getName();

    Pattern getPattern();

    void setPattern(Pattern pattern);

    void setPattern(String pattern);

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

    boolean tryDispatch(CommandSender sender, String label, String[] args);

    void dispatch(CommandSender sender, String label, Matcher matchedPattern, String[] args);
}
