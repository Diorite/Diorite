package diorite.impl.command;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import diorite.command.Command;
import diorite.command.CommandMap;
import diorite.command.CommandSender;
import diorite.command.MainCommand;
import diorite.command.PluginCommand;
import diorite.plugin.Plugin;
import diorite.utils.DioriteStringUtils;
import diorite.utils.collections.ConcurrentSimpleStringHashMap;

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
        this.commandMap.put(pluginCommand.getPlugin().getName() + "::" + pluginCommand.getName(), pluginCommand);
    }

    public void registerCommand(final MainCommand command)
    {
        if (command instanceof PluginCommand)
        {
            this.commandMap.put(((PluginCommand) command).getPlugin().getName() + "::" + command.getName(), command);
        }
        else if (command instanceof SystemCommandImpl)
        {
            this.commandMap.put("diorite::" + command.getName(), command);
        }
        else
        {
            throw new IllegalArgumentException("Command must be from plugin");
        }
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
        MainCommand cmd = this.commandMap.get(plugin + "::" + str);
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
            if (entry.getKey().endsWith("::" + str))
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
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("commandMap", this.commandMap.keySet()).toString();
    }
}
