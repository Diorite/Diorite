package org.diorite.impl.plugin;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import com.google.common.collect.ImmutableSet;

import org.diorite.impl.Main;
import org.diorite.plugin.DioritePlugin;
import org.diorite.plugin.PluginException;
import org.diorite.plugin.PluginLoader;
import org.diorite.plugin.PluginManager;

public class PluginManagerImpl implements PluginManager
{
    private final List<DioritePlugin>       plugins       = new ArrayList<>(20);
    private final Map<String, PluginLoader> pluginLoaders = new HashMap<>(5);

    @Override
    public void registerPluginLoader(final PluginLoader pluginLoader)
    {
        Main.debug("Registered plugin loader " + pluginLoader.getClass().getSimpleName() + " for extension " + pluginLoader.getFileExtension());
        this.pluginLoaders.put(pluginLoader.getFileExtension(), pluginLoader);
    }

    @Override
    public void loadPlugin(final File file) throws PluginException
    {
        this.plugins.add(this.pluginLoaders.get("jar").loadPlugin(file)); // TODO redirect it to valid PluginLoader
    }

    @Override
    public void enablePlugin(final String name) throws PluginException
    {
        this.getPlugin(name).setEnabled(true); // TODO redirect it to valid PluginLoader
    }

    @Override
    public void disablePlugin(final String name) throws PluginException
    {
        this.getPlugin(name).setEnabled(false); // TODO redirect it to valid PluginLoader
    }

    @Override
    public DioritePlugin getPlugin(final String name)
    {
        try
        {
            return this.plugins.stream().filter(p -> p.getName().equalsIgnoreCase(name)).findFirst().get();
        } catch (final NoSuchElementException ignored)
        {
            return null;
        }
    }

    @Override
    public Collection<DioritePlugin> getPlugins()
    {
        return ImmutableSet.copyOf(this.plugins);
    }

    @Override
    public void disablePlugins()
    {
        this.plugins.stream().filter(DioritePlugin::isEnabled).forEach(plugin -> plugin.setEnabled(false));
    }
}
