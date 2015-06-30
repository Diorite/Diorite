package org.diorite.plugin;

import java.io.File;
import java.util.Collection;
import java.util.List;

/**
 * This class manages and redirects load/enable/disable requests to valid PluginLoaders
 * @see PluginLoader
 */
public interface PluginManager
{
    void registerPluginLoader(PluginLoader pluginLoader);
    void loadPlugin(File file);
    void enablePlugin(String name);
    void disablePlugin(String name);
    PluginMainClass getPlugin(String name);
    Collection<PluginMainClass> getPlugins();
    void disablePlugins();
}
