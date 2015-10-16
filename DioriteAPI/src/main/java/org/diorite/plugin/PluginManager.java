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
import java.util.Collection;

/**
 * This class manages and redirects load/enable/disable requests to valid PluginLoaders
 *
 * @see PluginLoader
 */
public interface PluginManager
{
    void registerPluginLoader(PluginLoader pluginLoader);

    PluginLoader getPluginLoader(String suffix);

    void loadPlugin(File file) throws PluginException;

    void injectPlugin(FakeDioritePlugin plugin) throws PluginException;

    void enablePlugin(BasePlugin name) throws PluginException;

    void disablePlugin(BasePlugin name) throws PluginException;

    BasePlugin getPlugin(String name);

    Collection<BasePlugin> getPlugins();

    PluginData createPluginData(PluginDataBuilder builder);

    void enablePlugins();

    void disablePlugins();

    File getDirectory();
}
