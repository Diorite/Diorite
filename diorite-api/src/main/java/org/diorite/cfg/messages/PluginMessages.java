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

package org.diorite.cfg.messages;

import java.io.File;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.Diorite;
import org.diorite.plugin.BasePlugin;
import org.diorite.plugin.ChildPlugin;

/**
 * Represent messages related to some plugin.
 */
public class PluginMessages extends Messages
{
    private final BasePlugin  plugin;
    private final File        languageFolder;
    private final String      prefix;

    /**
     * Construct new Plugin messages for given plugin and default data.
     *
     * @param plugin      owner of messages.
     * @param prefix      prefix of message files, like "lang_"pl-PL.yml
     */
    public PluginMessages(final BasePlugin plugin, final String prefix)
    {
        super(Diorite.getConfig().getLanguages());
        this.plugin = plugin;
        this.languageFolder = plugin.getLanguageFolder();
        this.prefix = prefix;
    }

    /**
     * Construct new Plugin messages for given plugin and default data.
     *
     * @param plugin      owner of messages.
     * @param prefix      prefix of message files, like "lang_"pl-PL.yml
     * @param languages   languages supported by this messages node.
     */
    public PluginMessages(final BasePlugin plugin, final String prefix, final Locale... languages)
    {
        super(languages);
        this.plugin = plugin;
        this.languageFolder = plugin.getLanguageFolder();
        this.prefix = prefix;
    }

    /**
     * Returns messages for given plugin, may return null if plugin don't use diorite message system. :(
     *
     * @param plugin plugin to get messages.
     *
     * @return messages for given plugin or null.
     */
    public Messages getSubPluginMesssages(final ChildPlugin plugin)
    {
        return this.getMessages(StringUtils.replaceEach(plugin.getName(), new String[]{Character.toString(this.getNodeSeparator()), ChildPlugin.CHILD_SEPARATOR}, new String[]{"_", this.getNodeSeparator() + "plugins" + this.getNodeSeparator()}));
    }

    /**
     * Returns plugin owner of this messages.
     *
     * @return plugin owner of this messages
     */
    public BasePlugin getPlugin()
    {
        return this.plugin;
    }

    /**
     * Returns folder where all language files should be saved/loaded.
     *
     * @return folder where all language files should be saved/loaded.
     */
    public File getLanguageFolder()
    {
        return this.languageFolder;
    }

    /**
     * Returns file prefix, like "lang_"pl-PL.yml
     *
     * @return file prefix.
     */
    public String getPrefix()
    {
        return this.prefix;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("plugin", this.plugin.getFullName()).toString();
    }
}
