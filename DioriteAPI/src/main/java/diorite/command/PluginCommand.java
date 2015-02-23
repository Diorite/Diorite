package diorite.command;

import diorite.plugin.Plugin;

public interface PluginCommand extends MainCommand
{
    Plugin getPlugin();
}
