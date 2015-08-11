package org.diorite.plugin;

import java.io.File;

public interface PluginLoader
{
    /**
     * Loads plugin from file, may return null.
     *
     * @param file file to load.
     *
     * @return loaded plugin or null.
     *
     * @throws PluginException when file don't exist, there is no main class, invalid annotation etc...
     */
    DioritePlugin loadPlugin(File file) throws PluginException;

    void enablePlugin(BasePlugin plugin) throws PluginException;

    void disablePlugin(BasePlugin name) throws PluginException;

    String getFileSuffix();
}
