package org.diorite.impl.plugin;

import static org.diorite.utils.function.FunctionUtils.not;


import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import com.google.common.collect.ImmutableSet;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.Main;
import org.diorite.plugin.BasePlugin;
import org.diorite.plugin.FakeDioritePlugin;
import org.diorite.plugin.PluginException;
import org.diorite.plugin.PluginLoader;
import org.diorite.plugin.PluginManager;

public class PluginManagerImpl implements PluginManager
{
    private final Collection<BasePlugin>    plugins       = new ArrayList<>(20);
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
        Main.debug("Loading plugin " + file.getName() + " with pluginloader: " + pluginLoader.getClass().getSimpleName());
        this.plugins.add(pluginLoader.loadPlugin(file));
    }

    @Override
    public void injectPlugin(final FakeDioritePlugin plugin) throws PluginException
    {
        if (this.getPlugin(plugin.getName()) != null)
        {
            throw new PluginException("Plugin with name " + plugin.getName() + " is arleady loaded!");
        }
        Main.debug("Injecting plugin: " + plugin.getName());
        this.plugins.add(plugin);
        plugin.init(null, this.pluginLoaders.get(FakePluginLoader.FAKE_PLUGIN_EXTENSION), null, null, null, null, null, null);
    }

    @Override
    public void enablePlugin(final BasePlugin plugin) throws PluginException
    {
        if (plugin == null)
        {
            throw new NullPointerException("plugin can't be null!");
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
        this.plugins.stream().filter(not(BasePlugin::isEnabled)).forEach(plugin -> {
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
        this.plugins.stream().filter(BasePlugin::isEnabled).forEach(plugin -> {
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
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("plugins", this.plugins).append("pluginLoaders", this.pluginLoaders).toString();
    }
}
