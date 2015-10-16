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

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.diorite.command.sender.CommandSender;
import org.diorite.plugin.DioritePlugin;

public interface CommandMap
{
    /**
     * Register new plugin command.
     *
     * @param command command to register.
     */
    void registerCommand(PluginCommand command);

    /**
     * Returns all commands from given plugin.
     *
     * @param dioritePlugin plugin instance.
     *
     * @return all commands from given plugin.
     */
    Set<MainCommand> getCommandsFromPlugin(DioritePlugin dioritePlugin);

    /**
     * Returns all commands from given plugin. (by name)
     *
     * @param plugin plugin name.
     *
     * @return all commands from given plugin.
     */
    Set<MainCommand> getCommandsFromPlugin(String plugin);

    /**
     * Get all commands, from all plugins, named as given.
     *
     * @param str name of command.
     *
     * @return all commands named as given.
     */
    Set<MainCommand> getCommands(String str);

    /**
     * Get selected command from given plugin.
     *
     * @param dioritePlugin plugin instance.
     * @param str           command name.
     *
     * @return command instance or null.
     */
    Optional<MainCommand> getCommand(DioritePlugin dioritePlugin, String str);

    /**
     * Get selected command from any plugin.
     *
     * @param str command name.
     *
     * @return command instance or null.
     */
    Optional<MainCommand> getCommand(String str);

    /**
     * @return map with all commands.
     */
    Map<String, MainCommand> getCommandMap();

    /**
     * Execute tab-complete for given command line and sender.
     *
     * @param sender  sender that used tab-complete
     * @param cmdLine current command line.
     *
     * @return list of possible commands to use.
     */
    List<String> tabComplete(CommandSender sender, String cmdLine);

    /**
     * Find command for given command line, works like
     * {@link #dispatch(CommandSender, String)} but don't execute command.
     *
     * @param cmdLine command line.
     *
     * @return command instance or null.
     */
    Command findCommand(String cmdLine);

    /**
     * Find and execude command for given command line. <br>
     * return true if command was found and executed, and
     * returns false if command wasn't found.
     *
     * @param sender sender od command
     * @param args   command line.
     *
     * @return return true if command was found and executed, and
     * returns false if command wasn't found.
     */
    boolean dispatch(CommandSender sender, String args);
}
