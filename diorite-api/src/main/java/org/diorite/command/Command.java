/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 Diorite (by Bartłomiej Mazur (aka GotoFinal))
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

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

    default SubCommand registerSubCommand(final String name, final CommandExecutor commandExecutor)
    {
        return this.registerSubCommand(name, (Pattern) null, commandExecutor);
    }

    default SubCommand registerSubCommand(final String name, final CommandExecutor commandExecutor, final ExceptionHandler exceptionHandler)
    {
        return this.registerSubCommand(name, (Pattern) null, commandExecutor, exceptionHandler);
    }

    SubCommand registerSubCommand(String name, Pattern pattern);

    SubCommand registerSubCommand(String name, String pattern);

    SubCommand registerSubCommand(String name, Collection<String> aliases);

    SubCommand registerSubCommand(String name, Pattern pattern, CommandExecutor commandExecutor);

    SubCommand registerSubCommand(String name, String pattern, CommandExecutor commandExecutor);

    SubCommand registerSubCommand(String name, Collection<String> aliases, CommandExecutor commandExecutor);

    SubCommand registerSubCommand(String name, Pattern pattern, CommandExecutor commandExecutor, ExceptionHandler exceptionHandler);

    SubCommand registerSubCommand(String name, String pattern, CommandExecutor commandExecutor, ExceptionHandler exceptionHandler);

    SubCommand registerSubCommand(String name, Collection<String> aliases, CommandExecutor commandExecutor, ExceptionHandler exceptionHandler);

    void registerSubCommand(SubCommand subCommand);

    SubCommand unregisterSubCommand(String subCommand);

    Map<String, SubCommand> getSubCommandMap();

    boolean matches(String name);

    Matcher matcher(String name);

    boolean tryDispatch(CommandSender sender, String label, String[] args);

    List<String> tabComplete(CommandSender sender, String label, Matcher matchedPattern, String[] args);

    void dispatch(CommandSender sender, String label, Matcher matchedPattern, String[] args);
}
