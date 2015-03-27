package org.diorite.impl.command;

import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.command.PluginCommand;
import org.diorite.plugin.Plugin;

public class PluginCommandImpl extends MainCommandImpl implements PluginCommand
{
    private final Plugin plugin;

    public PluginCommandImpl(final String name, final Pattern pattern, final byte priority, final Plugin plugin)
    {
        super(name, pattern, priority);
        this.plugin = plugin;
    }

    public PluginCommandImpl(final String name, final Pattern pattern, final Plugin plugin)
    {
        super(name, pattern);
        this.plugin = plugin;
    }

    public PluginCommandImpl(final String name, final Plugin plugin)
    {
        super(name);
        this.plugin = plugin;
    }

    public PluginCommandImpl(final String name, final Collection<String> aliases, final byte priority, final Plugin plugin)
    {
        super(name, aliases, priority);
        this.plugin = plugin;
    }

    public PluginCommandImpl(final String name, final Collection<String> aliases, final Plugin plugin)
    {
        super(name, aliases);
        this.plugin = plugin;
    }

    @Override
    public Plugin getPlugin()
    {
        return this.plugin;
    }


    @Override
    public Matcher matcher(final String name)
    {
        if (name.toLowerCase().startsWith(this.plugin.getName().toLowerCase() + COMMAND_PLUGIN_SEPARATOR))
        {
            return super.matcher(name.replace(this.plugin.getName().toLowerCase() + COMMAND_PLUGIN_SEPARATOR, ""));
        }
        else
        {
            return super.matcher(name);
        }
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("plugin", this.plugin).toString();
    }
}
