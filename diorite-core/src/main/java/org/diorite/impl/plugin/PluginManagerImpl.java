/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2016. Diorite (by Bartłomiej Mazur (aka GotoFinal))
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package org.diorite.impl.plugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;

import com.google.common.collect.ImmutableSet;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.CoreMain;
import org.diorite.plugin.BasePlugin;
import org.diorite.plugin.FakeDioritePlugin;
import org.diorite.plugin.PluginDataBuilder;
import org.diorite.plugin.PluginException;
import org.diorite.plugin.PluginLoader;
import org.diorite.plugin.PluginManager;
import org.diorite.plugin.PluginsDirectory;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

public class PluginManagerImpl implements PluginManager
{
    private final Collection<PluginsDirectoryImpl> pluginDirectories = new ArrayList<>(1);
    private final Collection<File>                 loadedFiles       = new ArrayList<>(20);
    private final Collection<BasePlugin>           plugins           = new ArrayList<>(20);
    private final Map<String, PluginLoader>        pluginLoaders     = new CaseInsensitiveMap<>(5);

    @Override
    public Collection<PluginsDirectory> getPluginsDirectories()
    {
        return ImmutableSet.copyOf(this.pluginDirectories);
    }

    @Override
    public void addPluginsDirectory(final File directory)
    {
        CoreMain.debug("Added new plugins directory: " + directory);
        final PluginsDirectoryImpl pluginsDirectory = new PluginsDirectoryImpl(directory);
        this.pluginDirectories.add(pluginsDirectory);
        try
        {
            pluginsDirectory.init();
        }
        catch (final IOException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public PluginsDirectoryImpl getPluginsDirectory(final File directory)
    {
        return this.pluginDirectories.stream().filter(dir -> dir.getDirectory().equals(directory)).findAny().orElse(null);
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
    public void scanForPlugins()
    {
        CoreMain.debug("Scanning for plugins...");
        for (final PluginsDirectoryImpl directory : this.pluginDirectories)
        {
            final File pluginsFile = directory.getDirectory();
            Arrays.stream(Objects.requireNonNull(pluginsFile.listFiles())).filter(File::isFile).forEach(plugin ->
            {
                if (! this.loadedFiles.contains(plugin))
                {
                    final String extension = "." + plugin.getName().substring(plugin.getName().indexOf('.') + 1);
                    final PluginLoader pluginLoader = this.pluginLoaders.get(extension);
                    if (pluginLoader != null)
                    {
                        try
                        {
                            this.plugins.add(pluginLoader.loadPlugin(plugin));
                            this.loadedFiles.add(plugin);
                        }
                        catch (final PluginException e)
                        {
                            e.printStackTrace();
                        }
                    }
                    else
                    {
                        CoreMain.debug("Not found plugin loader for " + plugin);
                    }
                }
                else
                {
                    CoreMain.debug("Skipped " + plugin + " because is arleady loaded");
                }
            });
        }
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
            return;
        }

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
        this.plugins.add(plugin);
        plugin.init(null, null, this.pluginLoaders.get(FakePluginLoader.FAKE_PLUGIN_SUFFIX), null);
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
        return this.plugins.stream().filter(p -> p.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }

    @Override
    public Collection<BasePlugin> getPlugins()
    {
        return ImmutableSet.copyOf(this.plugins);
    }

    @Override
    public PluginDataImpl createPluginData(final PluginDataBuilder builder)
    {
        return new PluginDataImpl(builder);
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
    public void saveClassCaches()
    {
        this.pluginDirectories.forEach(PluginsDirectoryImpl::saveCache);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("plugins", this.plugins).append("pluginLoaders", this.pluginLoaders).toString();
    }
}
