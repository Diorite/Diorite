package org.diorite.impl.plugin;

import java.io.File;
import java.net.MalformedURLException;
import java.util.Set;

import org.reflections.Reflections;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import org.diorite.impl.ServerImpl;
import org.diorite.plugin.Plugin;
import org.diorite.plugin.PluginClassLoader;
import org.diorite.plugin.PluginException;
import org.diorite.plugin.PluginLoader;
import org.diorite.plugin.PluginMainClass;
import org.diorite.plugin.PluginNotFoundException;

public class JarPluginLoader implements PluginLoader
{
    @Override
    public PluginMainClass loadPlugin(final File file) throws PluginException
    {
        try
        {
            final PluginClassLoader classLoader = new PluginClassLoader(file);

            final ConfigurationBuilder config = new ConfigurationBuilder();
            config.setClassLoaders(new PluginClassLoader[]{classLoader});
            config.setUrls(ClasspathHelper.forClassLoader(classLoader));

            final Reflections ref = new Reflections(config);
            final Set<Class<?>> annotated = ref.getTypesAnnotatedWith(Plugin.class);
            if (annotated.isEmpty())
            {
                throw new PluginException("Plugin annotation doesn't found!");
            }
            if (annotated.size() > 1)
            {
                throw new PluginException("Plugin has more than one main class!");
            }

            final Class<?> mainClass = annotated.iterator().next();

            if (! PluginMainClass.class.isAssignableFrom(mainClass))
            {
                throw new PluginException("Main class must extend PluginMainClass!");
            }

            final PluginMainClass pluginMainClass = (PluginMainClass) mainClass.newInstance();
            final Plugin pluginDescription = mainClass.getAnnotation(Plugin.class);

            if (ServerImpl.getInstance().getPluginManager().getPlugin(pluginDescription.name()) != null)
            {
                throw new PluginException("Plugin " + pluginDescription.name() + " is arleady loaded!");
            }

            pluginMainClass.init(classLoader, pluginMainClass, pluginDescription.name(), pluginDescription.version(), pluginDescription.author());
            System.out.println("Loading " + pluginDescription.name() + " v" + pluginDescription.version() + " by " + pluginDescription.author() + " from file " + file.getName());
            pluginMainClass.onLoad();

            return pluginMainClass;
        } catch (final InstantiationException | IllegalAccessException | MalformedURLException e)
        {
            throw new PluginException("Exception while loading plugin from file " + file.getName(), e);
        }
    }

    @Override
    public void enablePlugin(final String name) throws PluginException
    {
        final PluginMainClass plugin = ServerImpl.getInstance().getPluginManager().getPlugin(name);
        if (plugin == null)
        {
            throw new PluginNotFoundException();
        }
        plugin.setEnabled(true);
    }

    @Override
    public void disablePlugin(final String name) throws PluginException
    {
        final PluginMainClass plugin = ServerImpl.getInstance().getPluginManager().getPlugin(name);
        if (plugin == null)
        {
            throw new PluginNotFoundException();
        }
        plugin.setEnabled(false);
    }

    @Override
    public String getFileExtension()
    {
        return "jar";
    }
}
