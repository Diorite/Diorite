package org.diorite.command;

import java.util.Collection;
import java.util.regex.Pattern;

public interface PluginCommandBuilder
{
    PluginCommandBuilder alias(String str);

    PluginCommandBuilder alias(String... str);

    PluginCommandBuilder alias(Collection<String> str);

    PluginCommandBuilder pattern(String pattern);

    PluginCommandBuilder pattern(String... pattern);

    PluginCommandBuilder pattern(Pattern pattern);

    PluginCommandBuilder executor(CommandExecutor executor);

    PluginCommandBuilder exceptionHandler(ExceptionHandler handler);

    PluginCommandBuilder priority(byte priority);

    PluginCommand build();
}
