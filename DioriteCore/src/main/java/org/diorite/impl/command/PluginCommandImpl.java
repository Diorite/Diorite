package org.diorite.impl.command;

import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.command.PluginCommand;
import org.diorite.plugin.DioritePlugin;

public class PluginCommandImpl extends MainCommandImpl implements PluginCommand
{
    private final DioritePlugin dioritePlugin;

    public PluginCommandImpl(final String name, final Pattern pattern, final byte priority, final DioritePlugin dioritePlugin)
    {
        super(name, pattern, priority);
        this.dioritePlugin = dioritePlugin;
    }

    public PluginCommandImpl(final String name, final Pattern pattern, final DioritePlugin dioritePlugin)
    {
        super(name, pattern);
        this.dioritePlugin = dioritePlugin;
    }

    public PluginCommandImpl(final String name, final DioritePlugin dioritePlugin)
    {
        super(name);
        this.dioritePlugin = dioritePlugin;
    }

    public PluginCommandImpl(final String name, final Collection<String> aliases, final byte priority, final DioritePlugin dioritePlugin)
    {
        super(name, aliases, priority);
        this.dioritePlugin = dioritePlugin;
    }

    public PluginCommandImpl(final String name, final Collection<String> aliases, final DioritePlugin dioritePlugin)
    {
        super(name, aliases);
        this.dioritePlugin = dioritePlugin;
    }

    @Override
    public DioritePlugin getPlugin()
    {
        return this.dioritePlugin;
    }


    @Override
    public Matcher matcher(final String name)
    {
        if (name.toLowerCase().startsWith(this.dioritePlugin.getName().toLowerCase() + COMMAND_PLUGIN_SEPARATOR))
        {
            return super.matcher(name.replace(this.dioritePlugin.getName().toLowerCase() + COMMAND_PLUGIN_SEPARATOR, ""));
        }
        else
        {
            return super.matcher(name);
        }
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("plugin", this.dioritePlugin).toString();
    }
}
