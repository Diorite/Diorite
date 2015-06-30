package org.diorite.plugin;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public abstract class PluginMainClass
{
    private boolean initialised;
    private String name;
    private String version;
    private String author;

    private PluginMainClass()
    {
        System.out.println("Constructor!");
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

    /**
     * Invokes after worlds being loaded
     */
    public void postEnable()
    {
    }

    public void onDisable()
    {
    }

    public final String getName()
    {
        return this.name;
    }

    public final String getVersion()
    {
        return this.version;
    }

    public final String getAuthor()
    {
        return this.author;
    }

    public void init(final String name, final String version, final String author)
    {
        if (this.initialised)
        {
            throw new RuntimeException("Internal method");
        }

        this.name = name;
        this.version = version;
        this.author = author;
        this.initialised = true;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("name", this.name).append("version", this.version).append("author", this.author).toString();
    }
}
