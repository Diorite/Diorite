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

import org.diorite.impl.ServerImpl;
import org.diorite.command.Command;
import org.diorite.command.CommandMap;
import org.diorite.command.MainCommand;
import org.diorite.command.PluginCommand;
import org.diorite.command.sender.CommandSender;
import org.diorite.plugin.Plugin;
import org.diorite.utils.DioriteStringUtils;
import org.diorite.utils.collections.ConcurrentSimpleStringHashMap;

public class CommandMapImpl implements CommandMap
{
    private final Map<String, MainCommand> commandMap = new ConcurrentSimpleStringHashMap<>(50, .20f, 8);

    private Iterable<MainCommand> getSortedCommandList()
    {
        return this.commandMap.values().stream().sorted((c1, c2) -> Byte.compare(c2.getPriority(), c1.getPriority())).collect(Collectors.toList());
    }

    @Override
    public void registerCommand(final PluginCommand pluginCommand)
    {
        this.commandMap.put(pluginCommand.getPlugin().getName() + Command.COMMAND_PLUGIN_SEPARATOR + pluginCommand.getName(), pluginCommand);
    }

    @Override
    public Set<MainCommand> getCommandsFromPlugin(final Plugin plugin)
    {
        return this.commandMap.values().parallelStream().filter(cmd -> (cmd instanceof PluginCommand) && ((PluginCommand) cmd).getPlugin().equals(plugin)).collect(Collectors.toSet());
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
    public Optional<MainCommand> getCommand(final Plugin plugin, final String str)
    {
        MainCommand cmd = this.commandMap.get(plugin + Command.COMMAND_PLUGIN_SEPARATOR + str);
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
                    return ServerImpl.getInstance().getPlayersManager().getOnlinePlayersNames(args[args.length - 1]);
                }
                else
                {
                    return result;
                }
            }
        }
        if (args.length > 1)
        {
            return ServerImpl.getInstance().getPlayersManager().getOnlinePlayersNames(args[args.length - 1]);
        }
        if (cmdLine.endsWith(" "))
        {
            return ServerImpl.getInstance().getPlayersManager().getOnlinePlayersNames();
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
    public void dispatch(final CommandSender sender, final String cmdLine)
    {
        if ((cmdLine == null) || cmdLine.isEmpty())
        {
            return;
        }
        final String[] args = DioriteStringUtils.splitArguments(cmdLine);
        if (args.length == 0)
        {
            return;
        }
        if (sender.isPlayer())
        {
            ServerImpl.getInstance().getConsoleSender().sendMessage(sender.getName() + ": " + Command.COMMAND_PREFIX + cmdLine);
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
                return;
            }
        }
        // TODO: changeable message
        sender.sendSimpleColoredMessage("&4No command: &c" + command);
    }

    public void registerCommand(final MainCommand command)
    {
        if (command instanceof PluginCommand)
        {
            this.commandMap.put(((PluginCommand) command).getPlugin().getName() + Command.COMMAND_PLUGIN_SEPARATOR + command.getName(), command);
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
