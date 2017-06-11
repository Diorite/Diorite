/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017. Diorite (by Bartłomiej Mazur (aka GotoFinal))
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

package org.diorite.command.argument;

import java.util.Collection;

import org.diorite.command.CommandExecutor;
import org.diorite.command.CommandManager;
import org.diorite.command.types.PluginCommand;
import org.diorite.plugin.Plugin;

/**
 * Builder for building command arguments
 */
public final class ArgumentCommandBuilder
{
    private Collection<? extends Argument<?>> arguments;
    private String                            name;
    private Plugin                            plugin;
    private CommandManager                    manager;
    private CommandExecutor                   executor;
    private PluginCommand                     command;

    public ArgumentCommandBuilder(String commandName, Plugin plugin, CommandManager manager)
    {
        this.name = commandName;
        this.plugin = plugin;
        this.manager = manager;
        this.command = manager.registerCommand(name, plugin);
    }

    public void withArgument(ArgumentBuilder builder)
    {
        arguments.add(builder.get());
    }

    public void withExecutor(CommandExecutor executor)
    {
        this.executor = executor;
    }

    public void register()
    {
        command.setExecutor(executor);
        command.setArguments(arguments);
        command.register(manager);
    }
}
