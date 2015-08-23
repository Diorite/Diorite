package org.diorite.plugin;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javassist.ClassPool;
import javassist.LoaderClassPath;

public class PluginClassLoader extends URLClassLoader
{
    private static final Map<String, Class<?>> globalClasses = new HashMap<>(100); // Global classes
    private final        Map<String, Class<?>> classes       = new HashMap<>(50); // Local classes

    public PluginClassLoader(final File jarToLoad) throws MalformedURLException
    {
        super(new URL[]{jarToLoad.toURI().toURL()});
        ClassPool.getDefault().appendClassPath(new LoaderClassPath(this));
    }

    @Override
    protected Class<?> findClass(final String name) throws ClassNotFoundException
    {
        return this.findClass(name, true);
    }

    public Class<?> findClass(final String name, final boolean global) throws ClassNotFoundException
    {
        Class<?> result = this.classes.get(name);

        if (result == null)
        {
            if (global)
            {
                result = PluginClassLoader.globalClasses.get(name);
            }

            if (result == null)
            {
                result = super.findClass(name);

                if (result != null)
                {
                    PluginClassLoader.globalClasses.put(name, result);
                }
            }

            this.classes.put(name, result);
        }

        return result;
    }

    public int loadedClasses()
    {
        return this.classes.size();
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("classes", this.classes).toString();
    }
}
