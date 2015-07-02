package org.diorite.plugin;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public abstract class DioritePlugin
{
    private DioritePlugin     instance;
    private PluginClassLoader classLoader;
    private boolean           initialised;
    private boolean           enabled;
    private String            name;
    private String            version;
    private String            author;

    protected DioritePlugin()
    {
    }

    /**
     * Invokes very early, at end of {@link org.diorite.Server} constructor
     */
    public void onLoad()
    {
    }

    /**
     * Invokes before world load
     */
    public void onEnable()
    {
    }

    public void onDisable()
    {
    }

    public final String getName()
    {
        this.initCheck();
        return this.name;
    }

    public final String getVersion()
    {
        this.initCheck();
        return this.version;
    }

    public final String getAuthor()
    {
        this.initCheck();
        return this.author;
    }

    protected final DioritePlugin instance()
    {
        this.initCheck();
        return this.instance;
    }

    public final void init(final PluginClassLoader classLoader, final DioritePlugin instance, final String name, final String version, final String author)
    {
        if (this.initialised)
        {
            throw new RuntimeException("Internal method");
        }

        this.instance = instance;
        this.classLoader = classLoader;
        this.name = name;
        this.version = version;
        this.author = author;
        this.initialised = true;
    }

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

    private final void initCheck()
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
