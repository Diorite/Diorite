package org.diorite.impl.plugin;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import com.google.common.collect.ImmutableSet;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.Main;
import org.diorite.Diorite;
import org.diorite.plugin.DioritePlugin;
import org.diorite.plugin.PluginException;
import org.diorite.plugin.PluginLoader;
import org.diorite.plugin.PluginManager;
import org.diorite.plugin.PluginNotFoundException;

public class PluginManagerImpl implements PluginManager
{
    private final Collection<DioritePlugin> plugins       = new ArrayList<>(20);
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
        final PluginLoader pluginLoader = this.pluginLoaders.get(FilenameUtils.getExtension(file.getAbsolutePath()));
        if (pluginLoader == null)
        {
            Main.debug("Non-plugin file: " + file.getName());
            return;
        }
        this.plugins.add(pluginLoader.loadPlugin(file));
    }

    @Override
    public void enablePlugin(final String name) throws PluginException
    {
        final DioritePlugin plugin = this.getPlugin(name);
        if (plugin == null)
        {
            throw new PluginNotFoundException();
        }
        plugin.getPluginLoader().enablePlugin(name);
    }

    @Override
    public void disablePlugin(final String name) throws PluginException
    {
        final DioritePlugin plugin = this.getPlugin(name);
        if (plugin == null)
        {
            throw new PluginNotFoundException();
        }
        plugin.getPluginLoader().disablePlugin(name);
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
        this.plugins.stream().filter(DioritePlugin::isEnabled).forEach(plugin -> {
            try
            {
                this.disablePlugin(plugin.getName());
            } catch (final PluginException e)
            {
                e.printStackTrace();
            }
        });
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("plugins", this.plugins).append("pluginLoaders", this.pluginLoaders).toString();
    }
}
