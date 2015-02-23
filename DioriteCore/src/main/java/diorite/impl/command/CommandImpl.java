package diorite.impl.command;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import diorite.command.Command;
import diorite.command.CommandExecutor;
import diorite.command.CommandSender;
import diorite.command.ExceptionHandler;
import diorite.command.SubCommand;

public abstract class CommandImpl implements Command
{
    private final String                  name;
    protected     Pattern                 pattern;
    private       Map<String, SubCommand> subCommandMap;

    private transient ExceptionHandler exceptionHandler;
    private transient CommandExecutor  commandExecutor;


    public CommandImpl(final String name, final Pattern pattern)
    {
        this.name = name;
        this.pattern = (pattern == null) ? Pattern.compile(name, Pattern.CASE_INSENSITIVE) : pattern;
    }

    public CommandImpl(final String name)
    {
        this(name, (Pattern) null);
    }

    public CommandImpl(final String name, final Collection<String> aliases)
    {
        Objects.requireNonNull(name, "Command name can't be null");
        Objects.requireNonNull(aliases, "Command aliases can't be null");
        this.name = name;
        this.pattern = Pattern.compile(aliases.stream().map(str -> "(" + Pattern.quote(str) + ")").reduce((s1, s2) -> s1 + "|" + s2).map(str -> name + "|(" + str + ")").orElse(name), Pattern.CASE_INSENSITIVE);
    }

    private void checkSubCommandsMap()
    {
        if (this.subCommandMap == null)
        {
            this.subCommandMap = new ConcurrentHashMap<>(5, .75f, 8);
        }
    }

    @Override
    public String getName()
    {
        return this.name;
    }

    @Override
    public Pattern getPattern()
    {
        return this.pattern;
    }

    @Override
    public void setPattern(final Pattern pattern)
    {
        this.pattern = pattern;
    }

    @Override
    public void setPattern(final String pattern)
    {
        this.pattern = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE);
    }

    @Override
    public ExceptionHandler getExceptionHandler()
    {
        return this.exceptionHandler;
    }

    @Override
    public void setExceptionHandler(final ExceptionHandler exceptionHandler)
    {
        this.exceptionHandler = exceptionHandler;
    }

    @Override
    public CommandExecutor getCommandExecutor()
    {
        return this.commandExecutor;
    }

    @Override
    public void setCommandExecutor(final CommandExecutor commandExecutor)
    {
        this.commandExecutor = commandExecutor;
    }

    @Override
    public SubCommand registredSubCommand(final String name, final Pattern pattern)
    {
        final SubCommand subCmd = new SubCommandImpl(name, pattern, this);
        this.registredSubCommand(subCmd);
        return subCmd;
    }

    @Override
    public SubCommand registredSubCommand(final String name, final String pattern)
    {
        final SubCommand subCmd = new SubCommandImpl(name, Pattern.compile(pattern, Pattern.CASE_INSENSITIVE), this);
        this.registredSubCommand(subCmd);
        return subCmd;
    }

    @Override
    public SubCommand registredSubCommand(final String name, final Collection<String> aliases)
    {
        final SubCommand subCmd = new SubCommandImpl(name, aliases, this);
        this.registredSubCommand(subCmd);
        return subCmd;
    }

    @Override
    public SubCommand registredSubCommand(final String name, final Pattern pattern, final CommandExecutor commandExecutor)
    {
        final SubCommand subCmd = new SubCommandImpl(name, pattern, this);
        subCmd.setCommandExecutor(commandExecutor);
        this.registredSubCommand(subCmd);
        return subCmd;
    }

    @Override
    public SubCommand registredSubCommand(final String name, final String pattern, final CommandExecutor commandExecutor)
    {
        final SubCommand subCmd = new SubCommandImpl(name, Pattern.compile(pattern, Pattern.CASE_INSENSITIVE), this);
        subCmd.setCommandExecutor(commandExecutor);
        this.registredSubCommand(subCmd);
        return subCmd;
    }

    @Override
    public SubCommand registredSubCommand(final String name, final Collection<String> aliases, final CommandExecutor commandExecutor)
    {
        final SubCommand subCmd = new SubCommandImpl(name, aliases, this);
        subCmd.setCommandExecutor(commandExecutor);
        this.registredSubCommand(subCmd);
        return subCmd;
    }

    @Override
    public SubCommand registredSubCommand(final String name, final Pattern pattern, final CommandExecutor commandExecutor, final ExceptionHandler exceptionHandler)
    {
        final SubCommand subCmd = new SubCommandImpl(name, pattern, this);
        subCmd.setCommandExecutor(commandExecutor);
        subCmd.setExceptionHandler(exceptionHandler);
        this.registredSubCommand(subCmd);
        return subCmd;
    }

    @Override
    public SubCommand registredSubCommand(final String name, final String pattern, final CommandExecutor commandExecutor, final ExceptionHandler exceptionHandler)
    {
        final SubCommand subCmd = new SubCommandImpl(name, Pattern.compile(pattern, Pattern.CASE_INSENSITIVE), this);
        subCmd.setCommandExecutor(commandExecutor);
        subCmd.setExceptionHandler(exceptionHandler);
        this.registredSubCommand(subCmd);
        return subCmd;
    }

    @Override
    public SubCommand registredSubCommand(final String name, final Collection<String> aliases, final CommandExecutor commandExecutor, final ExceptionHandler exceptionHandler)
    {
        final SubCommand subCmd = new SubCommandImpl(name, aliases, this);
        subCmd.setCommandExecutor(commandExecutor);
        subCmd.setExceptionHandler(exceptionHandler);
        this.registredSubCommand(subCmd);
        return subCmd;
    }

    @Override
    public void registredSubCommand(final SubCommand subCommand)
    {
        this.checkSubCommandsMap();
        this.subCommandMap.put(subCommand.getName(), subCommand);
    }

    @Override
    public SubCommand unregistredSubCommand(final String subCommand)
    {
        if (this.subCommandMap == null)
        {
            return null;
        }
        return this.subCommandMap.remove(subCommand);
    }

    @Override
    public Map<String, SubCommand> getSubCommandMap()
    {
        if (this.subCommandMap == null)
        {
            return new HashMap<>(1);
        }
        return new HashMap<>(this.subCommandMap);
    }

    @Override
    public boolean matches(final String name)
    {
        return this.pattern.matcher(name).matches();
    }

    @Override
    public boolean tryDispatch(final CommandSender sender, final String label, final String[] args)
    {
        final Matcher matcher = this.pattern.matcher(label);
        if (! matcher.matches())
        {
            return false;
        }
        this.dispatch(sender, label, matcher, args);
        return true;
    }

    protected void execute(final CommandSender sender, final String label, final Matcher matchedPattern, final String[] args)
    {
        Objects.requireNonNull(sender, "sender can't be null");
        Objects.requireNonNull(label, "label can't be null");
        Objects.requireNonNull(matchedPattern, "matchedPattern can't be null");
        Objects.requireNonNull(args, "args can't be null");
        matchedPattern.reset();
        matchedPattern.find();
        this.commandExecutor.runCommand(sender, this, label, matchedPattern, args);
    }

    @Override
    public final void dispatch(final CommandSender sender, final String label, final Matcher matchedPattern, final String[] args)
    {
        Objects.requireNonNull(sender, "sender can't be null");
        Objects.requireNonNull(label, "label can't be null");
        Objects.requireNonNull(matchedPattern, "matchedPattern can't be null");
        Objects.requireNonNull(args, "args can't be null");
        try // TODO: add supprot for basic exceptions
        {
            if ((this.subCommandMap != null) && (args.length > 0))
            {
                final String[] newArgs;
                if (args.length == 1)
                {
                    newArgs = EMPTY_ARGS;
                }
                else
                {
                    newArgs = new String[args.length - 1];
                    System.arraycopy(args, 1, newArgs, 0, args.length - 1);
                }
                for (final SubCommand subCommand : this.subCommandMap.values())
                {
                    if (subCommand.tryDispatch(sender, args[0], newArgs))
                    {
                        return;
                    }
                }
            }
            this.execute(sender, label, matchedPattern, args);
        } catch (Exception e)
        {
            if (this.exceptionHandler != null)
            {
                final Optional<Exception> opt = this.exceptionHandler.handle(e);
                if (opt.isPresent())
                {
                    e = opt.get();
                }
            }
            if (e != null)
            {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("name", this.name).append("pattern", this.pattern).append("subCommandMap", (this.subCommandMap == null) ? "null" : this.subCommandMap.keySet()).toString();
    }

    @Override
    public boolean equals(final Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (! (o instanceof CommandImpl))
        {
            return false;
        }

        final CommandImpl command = (CommandImpl) o;

        return this.name.equals(command.name) && this.pattern.equals(command.pattern);

    }

    @Override
    public int hashCode()
    {
        int result = this.name.hashCode();
        result = (31 * result) + this.pattern.hashCode();
        return result;
    }
}
