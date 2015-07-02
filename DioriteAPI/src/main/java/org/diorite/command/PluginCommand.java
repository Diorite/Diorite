package org.diorite.command;

import org.diorite.plugin.DioritePlugin;

public interface PluginCommand extends MainCommand
{
    @Override
    default String getFullName()
    {
        return this.getPlugin().getName() + COMMAND_PLUGIN_SEPARATOR + this.getName();
    }

    DioritePlugin getPlugin();
}
