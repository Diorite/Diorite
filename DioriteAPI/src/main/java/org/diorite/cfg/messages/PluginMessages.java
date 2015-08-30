package org.diorite.cfg.messages;

import java.io.File;
import java.io.InputStream;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

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
    private final InputStream defaultData;

    /**
     * Construct new Plugin messages for given plugin and default data.
     *
     * @param plugin      owner of messages.
     * @param prefix      prefix of message files, like "lang_"pl-PL.yml
     * @param defaultData default data for messages.
     */
    public PluginMessages(final BasePlugin plugin, final String prefix, final InputStream defaultData)
    {
        this.plugin = plugin;
        this.languageFolder = plugin.getLanguageFolder();
        this.prefix = prefix;
        this.defaultData = defaultData;
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
     * Returns default data, used when diorite can't find data for given language.
     *
     * @return default data.
     */
    public InputStream getDefaultData()
    {
        return this.defaultData;
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
