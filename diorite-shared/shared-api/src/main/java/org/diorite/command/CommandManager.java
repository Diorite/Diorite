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

package org.diorite.command;

import java.util.Collection;

import org.diorite.command.argument.ArgumentCommandBuilder;
import org.diorite.command.types.PluginCommand;
import org.diorite.plugin.Plugin;

/**
 * Interface with all needed methods for managing command adding/removing,
 * it does not cover execution of these commands, or handling incoming commands.
 */
public interface CommandManager
{
    /**
     * Get plugin that is managing commands,
     * may return null if there is no plugin for that, and server
     * is using default implementation.
     *
     * @return plugin implementing command manager or null.
     */
    Plugin getImplementingPlugin();

    /**
     * Adds the command to the manager.
     *
     * @param command
     *          command object.
     */
    void addCommand(CommandObject command);

    /**
     * Adds the commands to the manager.
     *
     * @param holder
     *          the holder class.
     * @param plugin
     *          the plugin requesting the commands to be registered
     */
    void addCommands(CommandHolder holder, Plugin plugin);

    /**
     * Register a plugin command with the given name
     *
     * @param name
     *          name of the command
     * @param plugin
     *          the plugin that owns the command
     * @return the plugin command registered
     */
    PluginCommand registerCommand(String name, Plugin plugin);

    /**
     * Removes a command from the manager.
     *
     * @param command
     *          command object to remove.
     */
    void removeCommand(CommandObject command);

    /**
     * Removes commands from the manager.
     *
     * @param holder
     *          the holder class
     */
    void removeCommands(CommandHolder holder);

    /**
     * Removes a command from the manager.
     *
     * @param commandName
     *          name of the command to remove.
     */
    void removeCommand(String commandName);

    /**
     * Get command object of a command by name
     *
     * @param commandName
     *          name of command to get the command object of.
     *
     * @return the command object.
     */
    CommandObject getCommandFromName(String commandName);

    /**
     * Create an ArgumentCommandBuilder
     *
     * @param name
     *          command name
     * @param plugin
     *          plugin requesting the builder
     * @return the builder
     */
    ArgumentCommandBuilder createCommandWithArgs(String name, Plugin plugin);
}
