package org.diorite.impl.command;

import java.util.Collection;
import java.util.regex.Pattern;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.command.Command;
import org.diorite.command.SubCommand;

public class SubCommandImpl extends CommandImpl implements SubCommand
{
    private final CommandImpl parent;

    public SubCommandImpl(final String name, final Pattern pattern, final CommandImpl parent)
    {
        super(name, pattern);
        this.parent = parent;
    }

    public SubCommandImpl(final String name, final Collection<String> aliases, final CommandImpl parent)
    {
        super(name, aliases);
        this.parent = parent;
    }

    public SubCommandImpl(final String name, final CommandImpl parent)
    {
        super(name);
        this.parent = parent;
    }

    @Override
    public Command getParent()
    {
        return this.parent;
    }

    @Override
    public int hashCode()
    {
        int result = super.hashCode();
        result = (31 * result) + this.parent.hashCode();
        return result;
    }

    @Override
    public boolean equals(final Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (! (o instanceof SubCommandImpl))
        {
            return false;
        }
        if (! super.equals(o))
        {
            return false;
        }

        final SubCommandImpl that = (SubCommandImpl) o;

        return this.parent.equals(that.parent);

    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("parent", this.parent.getName()).toString();
    }
}
