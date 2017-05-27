/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017. Diorite (by Bartłomiej Mazur (aka GotoFinal))
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

package org.diorite.commons.classes;

import java.net.URL;
import java.net.URLClassLoader;
import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.concurrent.ConcurrentHashMap;

import org.diorite.commons.reflections.DioriteReflectionUtils;
import org.diorite.commons.reflections.FieldAccessor;

/**
 * Dynamic class loader instance that allows for new urls and adding new additional class loaders
 */
public class DynamicClassLoader extends URLClassLoader
{
    private static final URL[]                     URLS         = new URL[0];
    private final        Map<ClassLoader, Integer> priorityMap  = new ConcurrentHashMap<>(5);
    private final        Collection<ClassLoader>   classLoaders = new PriorityQueue<>(Comparator.comparingInt(this.priorityMap::get).reversed());

    public DynamicClassLoader(ClassLoader parent)
    {
        super(URLS, parent);
    }

    public DynamicClassLoader(URL[] urls, ClassLoader parent)
    {
        super(urls, parent);
    }

    public DynamicClassLoader(URL[] urls)
    {
        super(urls);
    }

    @Override
    public void addURL(URL url)
    {
        super.addURL(url);
    }

    /**
     * Register new class loader to this dynamic class loader.
     *
     * @param classLoader
     *         new class loader to register.
     * @param priority
     *         priority of class loader.
     */
    public void addClassLoader(ClassLoader classLoader, int priority)
    {
        this.priorityMap.put(classLoader, priority);
        this.classLoaders.add(classLoader);
    }

    /**
     * Remove given class loader from this dynamic class loader.
     *
     * @param classLoader
     *         loader to remove.
     */
    public void removeClassLoader(ClassLoader classLoader)
    {
        this.classLoaders.remove(classLoader);
        this.priorityMap.remove(classLoader);
    }

    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException
    {
        try
        {
            return super.loadClass(name);
        }
        catch (ClassNotFoundException rootException)
        {
            ClassNotFoundException lastException = rootException;
            for (ClassLoader classLoader : this.classLoaders)
            {
                try
                {
                    return classLoader.loadClass(name);
                }
                catch (ClassNotFoundException exception)
                {
                    lastException.addSuppressed(exception);
                    lastException = exception;
                }
            }
            throw lastException;
        }
    }

    /**
     * Creates new dynamic class loader and injects it as new system class loader, if system class loader is already a DynamicClassLoader then existing instance
     * will be returned
     *
     * @return dynamic class loader instance.
     */
    public static DynamicClassLoader injectAsSystemClassLoader()
    {
        ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();
        if (systemClassLoader instanceof DynamicClassLoader)
        {
            return (DynamicClassLoader) systemClassLoader;
        }
        FieldAccessor<ClassLoader> scl = DioriteReflectionUtils.getField(ClassLoader.class, "scl", ClassLoader.class);
        DynamicClassLoader dynamicClassLoader = new DynamicClassLoader(systemClassLoader);
        scl.set(systemClassLoader, dynamicClassLoader);
        if (ClassLoader.getSystemClassLoader() != dynamicClassLoader)
        {
            throw new InternalError("Injection failed");
        }
        return dynamicClassLoader;
    }
}
