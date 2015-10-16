/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 Diorite (by Bartłomiej Mazur (aka GotoFinal))
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
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NoSuchElementException;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

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
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

public class PluginManagerImpl implements PluginManager
{
    private static final String                    CACHE_PATTERN_SEP = " == ";
    private static final Pattern                   CACHE_PATTERN     = Pattern.compile(CACHE_PATTERN_SEP);
    private final        Collection<BasePlugin>    plugins           = new ArrayList<>(20);
    private final        Map<String, PluginLoader> pluginLoaders     = new CaseInsensitiveMap<>(5);
    private final File directory;
    private static final Map<String, String> mainClassCache = new HashMap<>(20);

    public static String getCachedClass(final String file)
    {
        try
        {
            return mainClassCache.get(file);
        } catch (final Exception e)
        {
            if (CoreMain.isEnabledDebug())
            {
                e.printStackTrace();
            }
            return null;
        }
    }

    public static void setCachedClass(final String file, final Class<?> clazz)
    {
        try
        {
            mainClassCache.put(file, clazz.getName());
        } catch (final Exception e)
        {
            if (CoreMain.isEnabledDebug())
            {
                e.printStackTrace();
            }
        }
    }

    public static void saveCache()
    {
        try
        {
            Files.write(new File("pluginsCache.txt").toPath(), mainClassCache.entrySet().stream().map(e -> e.getValue() + CACHE_PATTERN_SEP + e.getKey()).collect(Collectors.toList()), StandardCharsets.UTF_8, StandardOpenOption.WRITE, StandardOpenOption.CREATE);
        } catch (final Exception e)
        {
            if (CoreMain.isEnabledDebug())
            {
                e.printStackTrace();
            }
        }
    }

    public PluginManagerImpl(final File directory)
    {
        this.directory = directory;
        final File cache = new File("pluginsCache.txt");
        try
        {
            if (cache.exists())
            {
                try
                {
                    Files.readAllLines(cache.toPath(), StandardCharsets.UTF_8).forEach(s -> {
                        try
                        {
                            final String[] parts = CACHE_PATTERN.split(s, 2);
                            if ((parts != null) && (parts.length == 2))
                            {
                                mainClassCache.put(parts[1], parts[0]);
                            }
                        } catch (final Exception e)
                        {
                            if (CoreMain.isEnabledDebug())
                            {
                                e.printStackTrace();
                            }
                        }
                    });
                } catch (final IOException e)
                {
                    e.printStackTrace();
                }
            }
            else
            {
                try
                {
                    cache.createNewFile();
                } catch (final IOException e)
                {
                    e.printStackTrace();
                }
            }
        } catch (final Exception t)
        {
            System.err.println("Error when loading plugin main classes cache: " + t.getClass().getName() + ", " + t.getMessage());
            if (CoreMain.isEnabledDebug())
            {
                t.printStackTrace();
            }
            try
            {
                cache.delete();
            } catch (final Exception e)
            {
                if (CoreMain.isEnabledDebug())
                {
                    e.printStackTrace();
                }
            }
        }
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
//            CoreMain.debug("Non-plugin file: " + file.getName());
            return;
        }
//        CoreMain.debug("Loading plugin " + file.getName() + " with pluginloader: " + pluginLoader.getClass().getSimpleName());
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
//        CoreMain.debug("Injecting plugin: " + plugin.getName());
        this.plugins.add(plugin);
        plugin.init(null, this.pluginLoaders.get(FakePluginLoader.FAKE_PLUGIN_SUFFIX), null);
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
