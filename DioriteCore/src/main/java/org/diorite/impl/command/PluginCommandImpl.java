package org.diorite.impl.command;

import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.command.PluginCommand;
import org.diorite.plugin.PluginMainClass;

public class PluginCommandImpl extends MainCommandImpl implements PluginCommand
{
    private final PluginMainClass pluginMainClass;

    public PluginCommandImpl(final String name, final Pattern pattern, final byte priority, final PluginMainClass pluginMainClass)
    {
        super(name, pattern, priority);
        this.pluginMainClass = pluginMainClass;
    }

    public PluginCommandImpl(final String name, final Pattern pattern, final PluginMainClass pluginMainClass)
    {
        super(name, pattern);
        this.pluginMainClass = pluginMainClass;
    }

    public PluginCommandImpl(final String name, final PluginMainClass pluginMainClass)
    {
        super(name);
        this.pluginMainClass = pluginMainClass;
    }

    public PluginCommandImpl(final String name, final Collection<String> aliases, final byte priority, final PluginMainClass pluginMainClass)
    {
        super(name, aliases, priority);
        this.pluginMainClass = pluginMainClass;
    }

    public PluginCommandImpl(final String name, final Collection<String> aliases, final PluginMainClass pluginMainClass)
    {
        super(name, aliases);
        this.pluginMainClass = pluginMainClass;
    }

    @Override
    public PluginMainClass getPlugin()
    {
        return this.pluginMainClass;
    }


    @Override
    public Matcher matcher(final String name)
    {
        if (name.toLowerCase().startsWith(this.pluginMainClass.getName().toLowerCase() + COMMAND_PLUGIN_SEPARATOR))
        {
            return super.matcher(name.replace(this.pluginMainClass.getName().toLowerCase() + COMMAND_PLUGIN_SEPARATOR, ""));
        }
        else
        {
            return super.matcher(name);
        }
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("plugin", this.pluginMainClass).toString();
    }
}
