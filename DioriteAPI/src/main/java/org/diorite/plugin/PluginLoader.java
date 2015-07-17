package org.diorite.plugin;

import java.io.File;

public interface PluginLoader
{
    DioritePlugin loadPlugin(File file) throws PluginException;

    void enablePlugin(BasePlugin plugin) throws PluginException;

    void disablePlugin(BasePlugin name) throws PluginException;

    String getFileExtension();
}
