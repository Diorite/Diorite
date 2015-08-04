package org.diorite.impl.plugin;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NoSuchElementException;

import com.google.common.collect.ImmutableSet;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.CoreMain;
import org.diorite.plugin.BasePlugin;
import org.diorite.plugin.FakeDioritePlugin;
import org.diorite.plugin.PluginException;
import org.diorite.plugin.PluginLoader;
import org.diorite.plugin.PluginManager;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

public class PluginManagerImpl implements PluginManager
{
    private final Collection<BasePlugin>    plugins       = new ArrayList<>(20);
    private final Map<String, PluginLoader> pluginLoaders = new CaseInsensitiveMap<>(5);
    private final File directory;

    public PluginManagerImpl(final File directory)
    {
        this.directory = directory;
    }

    @Override
    public void registerPluginLoader(final PluginLoader pluginLoader)
    {
        CoreMain.debug("Registered plugin loader " + pluginLoader.getClass().getSimpleName() + " for suffix " + pluginLoader.getFileSuffix());
        this.pluginLoaders.put(pluginLoader.getFileSuffix(), pluginLoader);
    }

    @Override
    public PluginLoader getPluginLoader(final String suffix)
    {
        return this.pluginLoaders.get(suffix);
    }

    @Override
    public void loadPlugin(final File file) throws PluginException
    {
        PluginLoader pluginLoader = null;
        int i = - 1;
        final String name = file.getName().toLowerCase();
        for (final Entry<String, PluginLoader> entry : this.pluginLoaders.entrySet())
        {
            if (name.endsWith(entry.getKey().toLowerCase()))
            {
                final int temp = entry.getKey().length();
                if (temp > i)
                {
                    i = temp;
                    pluginLoader = entry.getValue();
                }
            }
        }
        if (pluginLoader == null)
        {
            CoreMain.debug("Non-plugin file: " + file.getName());
            return;
        }
        CoreMain.debug("Loading plugin " + file.getName() + " with pluginloader: " + pluginLoader.getClass().getSimpleName());
        final BasePlugin plugin = pluginLoader.loadPlugin(file);
        if (plugin != null)
        {
            this.plugins.add(plugin);
        }
    }

    @Override
    public void injectPlugin(final FakeDioritePlugin plugin) throws PluginException
    {
        if (this.getPlugin(plugin.getName()) != null)
        {
            throw new PluginException("Plugin with name " + plugin.getName() + " is arleady loaded!");
        }
        CoreMain.debug("Injecting plugin: " + plugin.getName());
        this.plugins.add(plugin);
        plugin.init(null, this.pluginLoaders.get(FakePluginLoader.FAKE_PLUGIN_SUFFIX), null, null, null, null, null);
    }

    @Override
    public void enablePlugin(final BasePlugin plugin) throws PluginException
    {
        if (plugin == null)
        {
            throw new NullPointerException("plugin can't be null!");
        }
        if (plugin.isCoreMod())
        {
            return; // skip core mods, as they are too important to enable at runtime. (they will just not work if enabled here...)
        }
        plugin.getPluginLoader().enablePlugin(plugin);
    }

    @Override
    public void disablePlugin(final BasePlugin plugin) throws PluginException
    {
        if (plugin == null)
        {
            throw new NullPointerException("plugin can't be null!");
        }
        if (plugin.isCoreMod())
        {
            return; // skip core mods, as they are too important to disable
        }
        plugin.getPluginLoader().disablePlugin(plugin);
    }

    @Override
    public BasePlugin getPlugin(final String name)
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
    public Collection<BasePlugin> getPlugins()
    {
        return ImmutableSet.copyOf(this.plugins);
    }

    @Override
    public void enablePlugins()
    {
        this.plugins.stream().filter(p -> ! p.isEnabled() && ! p.isCoreMod()).forEach(plugin -> {
            try
            {
                this.enablePlugin(plugin);
            } catch (final PluginException e)
            {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void disablePlugins()
    {
        this.plugins.stream().filter(p -> p.isEnabled() && ! p.isCoreMod()).forEach(plugin -> {
            try
            {
                this.disablePlugin(plugin);
            } catch (final PluginException e)
            {
                e.printStackTrace();
            }
        });
    }

    @Override
    public File getDirectory()
    {
        return this.directory;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("plugins", this.plugins).append("pluginLoaders", this.pluginLoaders).toString();
    }
}
