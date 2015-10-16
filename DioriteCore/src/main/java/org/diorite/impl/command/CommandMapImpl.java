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

package org.diorite.impl.command;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.stream.Collectors;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.DioriteCore;
import org.diorite.command.Command;
import org.diorite.command.CommandMap;
import org.diorite.command.MainCommand;
import org.diorite.command.PluginCommand;
import org.diorite.command.sender.CommandSender;
import org.diorite.plugin.DioritePlugin;
import org.diorite.utils.DioriteStringUtils;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

public class CommandMapImpl implements CommandMap
{
    private final Map<String, MainCommand> commandMap = new CaseInsensitiveMap<>(50, .20f);

    private Iterable<MainCommand> getSortedCommandList()
    {
        return this.commandMap.values().stream().sorted((c1, c2) -> Byte.compare(c2.getPriority(), c1.getPriority())).collect(Collectors.toList());
    }

    @Override
    public synchronized void registerCommand(final PluginCommand pluginCommand)
    {
        this.commandMap.put(pluginCommand.getPlugin().getName() + Command.COMMAND_PLUGIN_SEPARATOR + pluginCommand.getName(), pluginCommand);
    }

    @Override
    public Set<MainCommand> getCommandsFromPlugin(final DioritePlugin dioritePlugin)
    {
        return this.commandMap.values().parallelStream().filter(cmd -> (cmd instanceof PluginCommand) && ((PluginCommand) cmd).getPlugin().equals(dioritePlugin)).collect(Collectors.toSet());
    }

    @Override
    public Set<MainCommand> getCommandsFromPlugin(final String plugin)
    {
        return this.commandMap.values().parallelStream().filter(cmd -> (cmd instanceof PluginCommand) && ((PluginCommand) cmd).getPlugin().getName().equalsIgnoreCase(plugin)).collect(Collectors.toSet());
    }

    @Override
    public Set<MainCommand> getCommands(final String str)
    {
        return this.commandMap.values().parallelStream().filter(cmd -> cmd.getName().equalsIgnoreCase(str)).collect(Collectors.toSet());
    }

    @Override
    public Optional<MainCommand> getCommand(final DioritePlugin dioritePlugin, final String str)
    {
        MainCommand cmd = this.commandMap.get(dioritePlugin + Command.COMMAND_PLUGIN_SEPARATOR + str);
        if (cmd == null)
        {
            cmd = this.commandMap.get(str);
        }
        return Optional.ofNullable(cmd);
    }

    @Override
    public Optional<MainCommand> getCommand(final String str)
    {
        final MainCommand cmd = this.commandMap.get(str);
        if (cmd != null)
        {
            return Optional.of(cmd);
        }
        for (final Map.Entry<String, MainCommand> entry : this.commandMap.entrySet())
        {
            if (entry.getKey().endsWith(Command.COMMAND_PLUGIN_SEPARATOR + str))
            {
                return Optional.of(entry.getValue());
            }
        }
        return Optional.empty();
    }

    @Override
    public Map<String, MainCommand> getCommandMap()
    {
        return new HashMap<>(this.commandMap);
    }

    @Override
    public List<String> tabComplete(final CommandSender sender, final String cmdLine)
    {
        final String[] args;
        if ((cmdLine == null) || cmdLine.isEmpty() || ((args = DioriteStringUtils.splitArguments(cmdLine)).length == 0))
        {
            return this.commandMap.keySet().parallelStream().map(s -> Command.COMMAND_PREFIX + s).collect(Collectors.toList());
        }
        final String command = args[0];
        final String[] newArgs;
        if (args.length == 1)
        {
            newArgs = Command.EMPTY_ARGS;
        }
        else
        {
            newArgs = new String[args.length - 1];
            System.arraycopy(args, 1, newArgs, 0, args.length - 1);
        }
        for (final MainCommand cmd : this.getSortedCommandList())
        {
            final Matcher matcher = cmd.matcher(command);
            if (matcher.matches())
            {
                final List<String> result = cmd.tabComplete(sender, command, matcher, newArgs);
                if (result.isEmpty())
                {
                    return DioriteCore.getInstance().getPlayersManager().getOnlinePlayersNames(args[args.length - 1]);
                }
                else
                {
                    return result;
                }
            }
        }
        if (args.length > 1)
        {
            return DioriteCore.getInstance().getPlayersManager().getOnlinePlayersNames(args[args.length - 1]);
        }
        if (cmdLine.endsWith(" "))
        {
            return DioriteCore.getInstance().getPlayersManager().getOnlinePlayersNames();
        }
        final String lcCmd = command.toLowerCase();
        final List<String> result = this.commandMap.entrySet().parallelStream().filter(this.tabCompeleterFilter.apply(lcCmd)).map(e -> Command.COMMAND_PREFIX + e.getValue().getFullName()).sorted().collect(Collectors.toList());
        return result.isEmpty() ? this.commandMap.keySet().parallelStream().map(s -> Command.COMMAND_PREFIX + s).collect(Collectors.toList()) : result;
    }

    private final Function<String, Predicate<Map.Entry<String, MainCommand>>> tabCompeleterFilter = lcCmd -> e -> {
        final String key = e.getKey();
        final String plugin = key.substring(0, key.indexOf(Command.COMMAND_PLUGIN_SEPARATOR) + 2);
        return (key.startsWith(lcCmd) || (! lcCmd.contains(Command.COMMAND_PLUGIN_SEPARATOR) && key.startsWith(plugin + lcCmd))) && ! key.equals(lcCmd);
    };

    @Override
    public Command findCommand(final String cmdLine)
    {
        if ((cmdLine == null) || cmdLine.isEmpty())
        {
            return null;
        }
        final int index = cmdLine.indexOf(' ');
        final String command;
        if (index == - 1)
        {
            command = cmdLine;
        }
        else
        {
            command = cmdLine.substring(0, index);
        }
        for (final MainCommand cmd : this.getSortedCommandList())
        {
            if (cmd.matches(command))
            {
                return cmd;
            }
        }
        return null;
    }

    @Override
    public boolean dispatch(final CommandSender sender, final String cmdLine)
    {
        if ((cmdLine == null) || cmdLine.isEmpty())
        {
            return false;
        }
        final String[] args = DioriteStringUtils.splitArguments(cmdLine);
        if (args.length == 0)
        {
            return false;
        }
        if (sender.isPlayer())
        {
            DioriteCore.getInstance().getConsoleSender().sendMessage(sender.getName() + ": " + Command.COMMAND_PREFIX + cmdLine);
        }
        //else if (sender.isCommandBlock()) TODO
        final String command = args[0];
        final String[] newArgs;
        if (args.length == 1)
        {
            newArgs = Command.EMPTY_ARGS;
        }
        else
        {
            newArgs = new String[args.length - 1];
            System.arraycopy(args, 1, newArgs, 0, args.length - 1);
        }
        for (final MainCommand cmd : this.getSortedCommandList())
        {
            if (cmd.tryDispatch(sender, command, newArgs))
            {
                return true;
            }
        }
        // TO|DO: changeable message
        //   sender.sendSimpleColoredMessage("&4No command: &c" + command);
        return false;
    }

    public synchronized void registerCommand(final MainCommand command)
    {
        if (command instanceof PluginCommand)
        {
            this.registerCommand((PluginCommand) command);
        }
        else if (command instanceof SystemCommandImpl)
        {
            this.commandMap.put("diorite" + Command.COMMAND_PLUGIN_SEPARATOR + command.getName(), command);
        }
        else
        {
            throw new IllegalArgumentException("Command must be from plugin");
        }
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("commandMap", this.commandMap.keySet()).toString();
    }
}
