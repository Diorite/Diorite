package org.diorite.command;

import org.diorite.plugin.Plugin;

public interface PluginCommand extends MainCommand
{
    @Override
    default String getFullName()
    {
        return this.getPlugin().getName() + COMMAND_PLUGIN_SEPARATOR + this.getName();
    }

    Plugin getPlugin();
}
