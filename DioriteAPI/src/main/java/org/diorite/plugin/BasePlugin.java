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

import org.slf4j.Logger;

import org.diorite.Diorite;

public interface BasePlugin
{
    /**
     * Invoked on load of plugin.
     */
    void onLoad();

    /**
     * Invoked when enabling plugin, always after {@link #onLoad()}
     */
    void onEnable();

    /**
     * Invoked when disabling plugin.
     */
    void onDisable();

    /**
     * Returns name and version of plugin and parent, can't be null.
     *
     * @return name and version of plugin.
     */
    default String getFullName()
    {
        return this.getName() + " v" + this.getVersion();
    }

    /**
     * Returns name of plugin, can't be null.
     *
     * @return name of plugin.
     */
    String getName();

    /**
     * Returns version of plugin, can't be null.
     *
     * @return version of plugin.
     */
    String getVersion();

    /**
     * Returns prefix of plugin, can't be null.
     *
     * @return prefix of plugin.
     */
    String getPrefix();

    /**
     * Returns author/s of plugin, can't be null, may contains custom text.
     *
     * @return author.s of plugin.
     */
    String getAuthor();

    /**
     * Returns description of plugin, can't be null.
     *
     * @return description of plugin.
     */
    String getDescription();

    /**
     * Returns website of plugin, can't be null.
     *
     * @return website of plugin.
     */
    String getWebsite();

    /**
     * Returns Logger for this plugin.
     *
     * @return Logger for this plugin.
     */
    Logger getLogger();

    /**
     * Returns PluginLoaded for this plugin.
     *
     * @return PluginLoaded for this plugin.
     */
    PluginLoader getPluginLoader();

    /**
     * Returns folder where plugin should store all data. With default settings this is /plugins/{pluginName} folder.
     *
     * @return folder where plugin should store all data.
     */
    default File getDataFolder()
    {
        return new File(Diorite.getPluginManager().getDirectory(), this.getName());
    }

    /**
     * Returns folder where plugin should store all languages files, with default settings this is /lang/{pluginName} folder.
     *
     * @return folder where plugin should store all languages files.
     */
    default File getLanguageFolder()
    {
        return new File(this.getDataFolder(), "lang");
    }

    /**
     * Method invoked on plugin init, do not override.
     *
     * @param classLoader  used class loader.
     * @param pluginLoader used plugin loader.
     * @param pluginData   other data of plugin. (like name, version, author etc.)
     */
    void init(final PluginClassLoader classLoader, final PluginLoader pluginLoader, final PluginDataBuilder pluginData);

    /**
     * Set if plugin is enabled.
     *
     * @param enabled if plugin should be enabled.
     */
    void setEnabled(boolean enabled);

    /**
     * Returns true if plugin is already initialised.
     *
     * @return true if plugin is already initialised.
     */
    boolean isInitialised();

    /**
     * Returns true if plugin is already enabled.
     *
     * @return true if plugin is already enabled.
     */
    boolean isEnabled();

    /**
     * Return true if this is core mod for diorite.
     *
     * @return true if this is core mod for diorite.
     */
    default boolean isCoreMod()
    {
        return false;
    }
}
