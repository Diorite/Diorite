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

    void injectPlugin(FakeDioritePlugin plugin) throws PluginException;

    void enablePlugin(BasePlugin name) throws PluginException;

    void disablePlugin(BasePlugin name) throws PluginException;

    BasePlugin getPlugin(String name);

    Collection<BasePlugin> getPlugins();

    void enablePlugins();

    void disablePlugins();

    File getDirectory();
}
