package diorite.impl.command;

import java.util.Collection;
import java.util.regex.Pattern;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import diorite.command.CommandPriority;
import diorite.command.MainCommand;

public class MainCommandImpl extends CommandImpl implements MainCommand
{
    private byte priority; // commands with higher priority will be checked first, priority can be changed in cfg. (and on runtime)

    public MainCommandImpl(final String name, final Pattern pattern, final byte priority)
    {
        super(name, pattern);
        this.priority = priority;
    }

    public MainCommandImpl(final String name, final Pattern pattern)
    {
        this(name, pattern, CommandPriority.NORMAL);
    }

    public MainCommandImpl(final String name)
    {
        this(name, (Pattern) null);
    }

    public MainCommandImpl(final String name, final Collection<String> aliases, final byte priority)
    {
        super(name, aliases);
        this.priority = priority;
    }

    public MainCommandImpl(final String name, final Collection<String> aliases)
    {
        this(name, aliases, CommandPriority.NORMAL);
    }

    @Override
    public byte getPriority()
    {
        return this.priority;
    }

    @Override
    public void setPriority(final byte priority)
    {
        this.priority = priority;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("priority", this.priority).toString();
    }

    @Override
    public boolean equals(final Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (! (o instanceof MainCommandImpl))
        {
            return false;
        }
        if (! super.equals(o))
        {
            return false;
        }

        final MainCommandImpl that = (MainCommandImpl) o;

        return (this.priority == that.priority);

    }

    @Override
    public int hashCode()
    {
        int result = super.hashCode();
        result = (31 * result) + (int) this.priority;
        return result;
    }
}
