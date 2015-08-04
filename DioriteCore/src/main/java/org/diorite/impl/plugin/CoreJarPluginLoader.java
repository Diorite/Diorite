package org.diorite.impl.plugin;

import java.io.File;
import java.net.MalformedURLException;
import java.util.Set;

import org.reflections.Reflections;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import org.diorite.impl.DioriteCore;
import org.diorite.plugin.BasePlugin;
import org.diorite.plugin.Plugin;
import org.diorite.plugin.PluginClassLoader;
import org.diorite.plugin.PluginException;
import org.diorite.plugin.PluginLoader;
import org.diorite.plugin.PluginNotFoundException;

public class CoreJarPluginLoader implements PluginLoader
{
    public static final String CORE_JAR_SUFFIX = ".core.jar";

    @Override
    public DioriteMod loadPlugin(final File file) throws PluginException
    {
        try
        {
            if (DioriteCore.getInstance().isStartedCore())
            {
                return null;
            }
            final PluginClassLoader classLoader = new PluginClassLoader(file);

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

            final Class<?> mainClass = annotated.iterator().next();

            if (! DioriteMod.class.isAssignableFrom(mainClass) || ! mainClass.isAnnotationPresent(Plugin.class))
            {
                throw new PluginException("Main class must extend DioriteMod");
            }

            final DioriteMod dioriteMod = (DioriteMod) mainClass.newInstance();
            final Plugin pluginDescription = mainClass.getAnnotation(Plugin.class);

            if (DioriteCore.getInstance().getPluginManager().getPlugin(pluginDescription.name()) != null)
            {
                throw new PluginException("Plugin/Mod " + pluginDescription.name() + " is arleady loaded!");
            }

            dioriteMod.init(classLoader, this, pluginDescription.name(), pluginDescription.version(), pluginDescription.author(), pluginDescription.description(), pluginDescription.website());
            System.out.println("Loading " + pluginDescription.name() + " v" + pluginDescription.version() + " by " + pluginDescription.author() + " from file " + file.getName());
            dioriteMod.onLoad();

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
