package org.diorite.plugin;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

public class PluginClassLoader extends URLClassLoader
{
    public PluginClassLoader(final File jarToLoad) throws MalformedURLException
    {
        super(new URL[]{jarToLoad.toURI().toURL()});
    }
}
