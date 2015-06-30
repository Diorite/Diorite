package org.diorite.impl.plugin;

import java.io.File;

import org.diorite.plugin.PluginLoader;

public class JarPluginLoader implements PluginLoader
{
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
    public String getFileExtension()
    {
        return "jar";
    }
}
