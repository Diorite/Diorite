package org.diorite.plugin;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public final class FakeDioritePlugin implements BasePlugin
{
    private final DioritePlugin parent;
    private       PluginLoader  loader;
    private       boolean       enabled;
    private final String        name;
    private final String        version;
    private final String        author;
    private final String        description;
    private final String        website;

    public FakeDioritePlugin(final DioritePlugin parent, final String name, final String version, final String author, final String description, final String website)
    {
        this.parent = parent;
        this.name = name;
        this.version = version;
        this.author = author;
        this.description = description;
        this.website = website;
    }

    public FakeDioritePlugin(final DioritePlugin parent, final String name, final String version, final String author)
    {
        this(parent, name, version, author, null, null);
    }

    @Override
    public void onLoad()
    {
        // TODO allow to change this
    }

    @Override
    public void onEnable()
    {
        // TODO allow to change this
    }

    @Override
    public void onDisable()
    {
        // TODO allow to change this
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
    public PluginLoader getPluginLoader()
    {
        return this.loader;
    }

    @Override
    public void init(final PluginClassLoader classLoader, final PluginLoader pluginLoader, final String name, final String version, final String author, final String description, final String website)
    {
        this.loader = pluginLoader;
    }

    @Override
    public void setEnabled(final boolean enabled)
    {
        if (enabled == this.enabled)
        {
            throw new IllegalArgumentException();
        }

        this.enabled = enabled;
        if (enabled)
        {
            System.out.println("Enabling " + this.name + "(" + this.parent.getName() + ")");
            this.onEnable();
        }
        else
        {
            System.out.println("Disabling " + this.name + "(" + this.parent.getName() + ")");
            this.onDisable();
        }
    }

    @Override
    public boolean isInitialised()
    {
        return this.loader != null;
    }

    @Override
    public boolean isEnabled()
    {
        return this.enabled;
    }

    public DioritePlugin getParent()
    {
        return this.parent;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("parent", this.parent).toString();
    }
}
