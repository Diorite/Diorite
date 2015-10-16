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

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.Diorite;

public class PluginDataBuilder implements AnyPluginData
{
    private String name;
    private String version;
    private String prefix;
    private String author;
    private String description;
    private String website;

    public PluginDataBuilder(final Plugin plugin)
    {
        this.name = plugin.name();
        this.version = plugin.version();
        this.prefix = plugin.prefix();
        this.author = plugin.author();
        this.description = plugin.description();
        this.website = plugin.website();
    }

    public PluginDataBuilder(final String name)
    {
        this.setName(name);
        this.setVersion(null);
        this.setPrefix(null);
        this.setAuthor(null);
        this.setDescription(null);
        this.setWebsite(null);
    }

    public PluginDataBuilder(final String name, final String version)
    {
        this.setName(name);
        this.setVersion(version);
        this.setPrefix(null);
        this.setAuthor(null);
        this.setDescription(null);
        this.setWebsite(null);
    }

    public PluginDataBuilder(final String name, final String version, final String author)
    {
        this.setName(name);
        this.setVersion(version);
        this.setAuthor(author);
        this.setPrefix(null);
        this.setDescription(null);
        this.setWebsite(null);
    }

    public PluginDataBuilder(final String name, final String version, final String prefix, final String author, final String description, final String website)
    {
        this.setName(name);
        this.setVersion(version);
        this.setPrefix(prefix);
        this.setAuthor(author);
        this.setDescription(description);
        this.setWebsite(website);
    }

    @Override
    public String getName()
    {
        return this.name;
    }

    @Override
    public String getVersion()
    {
        return this.version;
    }

    @Override
    public String getPrefix()
    {
        return this.prefix;
    }

    @Override
    public String getAuthor()
    {
        return this.author;
    }

    @Override
    public String getDescription()
    {
        return this.description;
    }

    @Override
    public String getWebsite()
    {
        return this.website;
    }

    /**
     * Set name of plugin, can't be null.
     *
     * @param name name of plugin.
     *
     * @return builder itself.
     */
    public PluginDataBuilder setName(final String name)
    {
        Validate.notNull(name, "name can't be null.");
        this.name = name;
        return this;
    }

    /**
     * Set version of plugin.
     *
     * @param version version of plugin.
     *
     * @return builder itself.
     */
    public PluginDataBuilder setVersion(final String version)
    {
        this.version = (version == null) ? "unknown" : version;
        return this;
    }

    /**
     * Set prefix of plugin.
     *
     * @param prefix prefix of plugin.
     *
     * @return builder itself.
     */
    public PluginDataBuilder setPrefix(final String prefix)
    {
        this.prefix = (prefix == null) ? ("%name% v%version%") : prefix;
        return this;
    }

    /**
     * Set author of plugin.
     *
     * @param author author of plugin.
     *
     * @return builder itself.
     */
    public PluginDataBuilder setAuthor(final String author)
    {
        this.author = (author == null) ? "unknown" : author;
        return this;
    }

    /**
     * Set description of plugin.
     *
     * @param description description of plugin.
     *
     * @return builder itself.
     */
    public PluginDataBuilder setDescription(final String description)
    {
        this.description = (description == null) ? "" : description;
        return this;
    }

    /**
     * Set website of plugin.
     *
     * @param website website of plugin.
     *
     * @return builder itself.
     */
    public PluginDataBuilder setWebsite(final String website)
    {
        this.website = (website == null) ? "" : website;
        return this;
    }

    public PluginData build()
    {
        return Diorite.getPluginManager().createPluginData(this);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("name", this.name).append("version", this.version).append("prefix", this.prefix).append("author", this.author).append("description", this.description).append("website", this.website).toString();
    }
}
