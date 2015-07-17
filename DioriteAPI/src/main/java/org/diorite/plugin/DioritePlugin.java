package org.diorite.plugin;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public abstract class DioritePlugin implements BasePlugin
{
    private DioritePlugin     instance;
    private PluginClassLoader classLoader;
    private PluginLoader      pluginLoader;
    private boolean           initialised;
    private boolean           enabled;
    private String            name;
    private String            version;
    private String            author;
    private String            description;
    private String            website;

    protected DioritePlugin()
    {
    }

    /**
     * Invokes very early, at end of {@link org.diorite.Server} constructor
     */
    @Override
    public void onLoad()
    {
    }

    /**
     * Invokes before world load
     */
    @Override
    public void onEnable()
    {
    }

    @Override
    public void onDisable()
    {
    }

    @Override
    public final String getName()
    {
        this.initCheck();
        return this.name;
    }

    @Override
    public final String getVersion()
    {
        this.initCheck();
        return this.version;
    }

    @Override
    public final String getAuthor()
    {
        this.initCheck();
        return this.author;
    }

    @Override
    public final String getDescription()
    {
        return this.description;
    }

    @Override
    public final String getWebsite()
    {
        return this.website;
    }

    @Override
    public final PluginLoader getPluginLoader()
    {
        return this.pluginLoader;
    }

    protected final DioritePlugin instance()
    {
        this.initCheck();
        return this.instance;
    }

    @Override
    public final void init(final PluginClassLoader classLoader, final PluginLoader pluginLoader, final DioritePlugin instance, final String name, final String version, final String author, final String description, final String website)
    {
        if (this.initialised)
        {
            throw new RuntimeException("Internal method");
        }

        this.instance = instance;
        this.classLoader = classLoader;
        this.pluginLoader = pluginLoader;
        this.name = name;
        this.version = version;
        this.author = author;
        this.website = website.isEmpty() ? null : website;
        this.description = description.isEmpty() ? null : description;
        this.initialised = true;
    }

    @Override
    public final void setEnabled(final boolean enabled)
    {
        this.initCheck();

        if (enabled == this.enabled)
        {
            throw new IllegalArgumentException();
        }

        this.enabled = enabled;
        if (enabled)
        {
            System.out.println("Enabling " + this.name);
            this.onEnable();
        }
        else
        {
            System.out.println("Disabling " + this.name);
            this.onDisable();
        }
    }

    @Override
    public boolean isInitialised()
    {
        return this.initialised;
    }

    @Override
    public final boolean isEnabled()
    {
        this.initCheck();
        return this.enabled;
    }

    public final PluginClassLoader getClassLoader()
    {
        this.initCheck();
        return this.classLoader;
    }

    private void initCheck()
    {
        if (! this.initialised)
        {
            throw new IllegalStateException();
        }
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("name", this.name).append("version", this.version).append("author", this.author).toString();
    }
}
