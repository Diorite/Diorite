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
import java.net.MalformedURLException;
import java.util.Set;

import org.reflections.Reflections;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import org.diorite.impl.CoreMain;
import org.diorite.impl.DioriteCore;
import org.diorite.plugin.BasePlugin;
import org.diorite.plugin.DioritePlugin;
import org.diorite.plugin.Plugin;
import org.diorite.plugin.PluginClassLoader;
import org.diorite.plugin.PluginDataBuilder;
import org.diorite.plugin.PluginException;
import org.diorite.plugin.PluginLoader;
import org.diorite.plugin.PluginNotFoundException;

public class CoreJarPluginLoader implements PluginLoader
{
    public static final String CORE_JAR_SUFFIX = ".core.jar";

    @Override
    public DioriteMod loadPlugin(final File file) throws PluginException
    {
        final PluginsDirectoryImpl pluginsDirectory = (PluginsDirectoryImpl) DioriteCore.getInstance().getPluginManager().getPluginsDirectory(file.getParentFile());
        try
        {
            if (DioriteCore.getInstance().isStartedCore())
            {
                return null;
            }
            final PluginClassLoader classLoader = new PluginClassLoader(file);

            Class<?> mainClass = null;
            try
            {
                final String className = pluginsDirectory.getCachedClass("corejar|" + file.getName());
                if (className != null)
                {
                    mainClass = classLoader.findClass(className, false);
                    if (! DioritePlugin.class.isAssignableFrom(mainClass) || ! mainClass.isAnnotationPresent(Plugin.class) || ! mainClass.isAnnotationPresent(CoreMod.class))
                    {
                        mainClass = null;
                        CoreMain.debug("Cached main class for plugin: " + file.getPath() + " is invalid[2].");
                    }
                    else
                    {
                        CoreMain.debug("Cached main class for plugin: " + file.getPath() + " is " + mainClass.getName());
                    }
                }
                else
                {
                    CoreMain.debug("Can't find cached main class for plugin: " + file.getPath());
                }
            } catch (final ClassNotFoundException e)
            {
                CoreMain.debug("Cached main class for plugin: " + file.getPath() + " is invalid.");
            }

            if (mainClass == null)
            {
                final ConfigurationBuilder config = new ConfigurationBuilder();
                config.setClassLoaders(new PluginClassLoader[]{classLoader});
                config.setUrls(ClasspathHelper.forClassLoader(classLoader));

                final Reflections ref = new Reflections(config);
                final Set<Class<?>> annotated = ref.getTypesAnnotatedWith(CoreMod.class);
                if (annotated.isEmpty())
                {
                    throw new PluginException("Mod annotation wasn't found!");
                }
                if (annotated.size() > 1)
                {
                    throw new PluginException("Mod has more than one main class!");
                }

                mainClass = annotated.iterator().next();

                if (! DioriteMod.class.isAssignableFrom(mainClass) || ! mainClass.isAnnotationPresent(Plugin.class))
                {
                    throw new PluginException("Main class must extend DioriteMod");
                }
            }

            final DioriteMod dioriteMod = (DioriteMod) mainClass.newInstance();
            final Plugin pluginDescription = mainClass.getAnnotation(Plugin.class);

            if (DioriteCore.getInstance().getPluginManager().getPlugin(pluginDescription.name()) != null)
            {
                throw new PluginException("Plugin/Mod " + pluginDescription.name() + " is arleady loaded!");
            }

            dioriteMod.init(pluginsDirectory.getDirectory(), classLoader, this, new PluginDataBuilder(pluginDescription));
            dioriteMod.getLogger().info("Loading " + pluginDescription.name() + " v" + pluginDescription.version() + " by " + pluginDescription.author() + " from file " + file.getName());
            dioriteMod.onLoad();

            pluginsDirectory.setCachedClass("corejar|" + file.getName(), mainClass);
            return dioriteMod;
        } catch (final InstantiationException | IllegalAccessException | MalformedURLException e)
        {
            throw new PluginException("Exception while loading mod from file " + file.getName(), e);
        }
    }

    @Override
    public void enablePlugin(final BasePlugin plugin) throws PluginException
    {
        if (plugin == null)
        {
            throw new PluginNotFoundException();
        }
        plugin.setEnabled(true);
    }

    @Override
    public void disablePlugin(final BasePlugin plugin) throws PluginException
    {
        if (plugin == null)
        {
            throw new PluginNotFoundException();
        }
        plugin.setEnabled(false);
    }

    @Override
    public String getFileSuffix()
    {
        return CORE_JAR_SUFFIX;
    }
}
