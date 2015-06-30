package org.diorite.impl.plugin;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.diorite.plugin.PluginLoader;
import org.diorite.plugin.PluginMainClass;
import org.diorite.plugin.PluginManager;

public class PluginManagerImpl implements PluginManager
{
    private List<PluginMainClass> plugins = new ArrayList<>(20);

    @Override
    public void registerPluginLoader(final PluginLoader pluginLoader)
    {

    }

    @Override
    public void loadPlugin(final File file)
    {

    }

    @Override
    public void enablePlugin(final String name)
    {

    }

    @Override
    public void disablePlugin(final String name)
    {

    }

    @Override
    public PluginMainClass getPlugin(final String name)
    {
        return null;
    }

    @Override
    public Collection<PluginMainClass> getPlugins()
    {
        return this.plugins;
    }

    @Override
    public void disablePlugins()
    {

    }
}
