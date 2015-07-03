package org.diorite.plugin;

import java.io.File;
import java.util.Collection;

/**
 * This class manages and redirects load/enable/disable requests to valid PluginLoaders
 *
 * @see PluginLoader
 */
public interface PluginManager
{
    void registerPluginLoader(PluginLoader pluginLoader);

    void loadPlugin(File file) throws PluginException;

    void enablePlugin(String name) throws PluginException;

    void disablePlugin(String name) throws PluginException;

    DioritePlugin getPlugin(String name);

    Collection<DioritePlugin> getPlugins();

    void disablePlugins();
}
