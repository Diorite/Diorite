package org.diorite.plugin;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class DioritePlugin implements BasePlugin
{
    private Logger logger;

    private PluginClassLoader classLoader;
    private PluginLoader      pluginLoader;
    private boolean           initialised;
    private boolean           enabled;
    private PluginData        pluginData;

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
        return this.pluginData.getName();
    }

    @Override
    public String getPrefix()
    {
        this.initCheck();
        return this.pluginData.getPrefix();
    }

    @Override
    public final String getVersion()
    {
        this.initCheck();
        return this.pluginData.getVersion();
    }

    @Override
    public final String getAuthor()
    {
        this.initCheck();
        return this.pluginData.getAuthor();
    }

    @Override
    public String getDescription()
    {
        this.initCheck();
        return this.pluginData.getDescription();
    }

    @Override
    public String getWebsite()
    {
        this.initCheck();
        return this.pluginData.getWebsite();
    }

    @Override
    public Logger getLogger()
    {
        this.initCheck();
        if (this.logger == null)
        {
            this.logger = LoggerFactory.getLogger("[" + this.getPrefix() + "]");
        }
        return this.logger;
    }

    @Override
    public final PluginLoader getPluginLoader()
    {
        return this.pluginLoader;
    }

    protected final DioritePlugin instance()
    {
        this.initCheck();
        return this;
    }

    @Override
    public final void init(final PluginClassLoader classLoader, final PluginLoader pluginLoader, final PluginDataBuilder data)
    {
        if (this.initialised)
        {
            throw new RuntimeException("Internal method");
        }
        this.classLoader = classLoader;
        this.pluginLoader = pluginLoader;
        this.pluginData = data.build();
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
            this.logger.info("Enabling " + this.getFullName());
            this.onEnable();
        }
        else
        {
            this.logger.info("Disabling " + this.getFullName());
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
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("name", this.getName()).append("version", this.getVersion()).append("author", this.getAuthor()).toString();
    }
}
