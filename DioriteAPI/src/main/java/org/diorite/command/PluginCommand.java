package org.diorite.command;

import org.diorite.plugin.Plugin;

public interface PluginCommand extends MainCommand
{
    Plugin getPlugin();
}
