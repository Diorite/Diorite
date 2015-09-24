package org.diorite.impl.plugin;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.plugin.Plugin;
import org.diorite.plugin.PluginData;
import org.diorite.plugin.PluginDataBuilder;

public class PluginDataImpl implements PluginData
{
    private final String name;
    private final String version;
    private final String prefix;
    private final String author;
    private final String description;
    private final String website;

    public PluginDataImpl(final Plugin plugin)
    {
        this.name = plugin.name();
        this.version = plugin.version();
        this.prefix = StringUtils.replaceEach(plugin.prefix(), new String[]{"%name%", "%version%"}, new String[]{this.name, this.version});
        this.author = plugin.author();
        this.description = plugin.description();
        this.website = plugin.website();
    }

    public PluginDataImpl(final PluginDataBuilder builder)
    {
        this.name = builder.getName();
        this.version = builder.getVersion();
        this.prefix = StringUtils.replaceEach(builder.getPrefix(), new String[]{"%name%", "%version%"}, new String[]{this.name, this.version});
        this.author = builder.getAuthor();
        this.description = builder.getDescription();
        this.website = builder.getWebsite();
    }

    public PluginDataImpl(final String name)
    {
        Validate.notNull(name, "name can't be null!");
        this.name = name;
        this.version = "unknown";
        this.prefix = (this.name + " v" + this.version);
        this.author = "unknown";
        this.description = "";
        this.website = "";
    }

    public PluginDataImpl(final String name, final String version)
    {
        Validate.notNull(name, "name can't be null!");
        this.name = name;
        this.version = (version == null) ? "unknown" : version;
        this.prefix = (this.name + " v" + this.version);
        this.author = "unknown";
        this.description = "";
        this.website = "";
    }

    public PluginDataImpl(final String name, final String version, final String author)
    {
        Validate.notNull(name, "name can't be null!");
        this.name = name;
        this.version = (version == null) ? "unknown" : version;
        this.author = (author == null) ? "unknown" : author;
        this.prefix = (this.name + " v" + this.version);
        this.description = "";
        this.website = "";
    }

    public PluginDataImpl(final String name, final String version, final String prefix, final String author, final String description, final String website)
    {
        Validate.notNull(name, "name can't be null!");
        this.name = name;
        this.version = (version == null) ? "unknown" : version;
        this.author = (author == null) ? "unknown" : author;
        this.prefix = (prefix == null) ? (this.name + " v" + this.version) : StringUtils.replaceEach(prefix, new String[]{"%name%", "%version%"}, new String[]{this.name, this.version});
        this.description = (description == null) ? "" : description;
        this.website = (website == null) ? "" : website;
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

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("name", this.name).append("version", this.version).append("prefix", this.prefix).append("author", this.author).append("description", this.description).append("website", this.website).toString();
    }
}
