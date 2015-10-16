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
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("classes", this.classes).toString();
    }
}
