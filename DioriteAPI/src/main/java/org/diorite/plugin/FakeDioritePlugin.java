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

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FakeDioritePlugin implements ChildPlugin
{
    private Logger logger;

    private final DioritePlugin parent;
    private       PluginLoader  loader;
    private       boolean       enabled;
    private final PluginData    pluginData;
    private       File          dataFolder;

    public FakeDioritePlugin(final DioritePlugin parent, final PluginDataBuilder data)
    {
        this.parent = parent;
        data.setName(parent.getName() + CHILD_SEPARATOR + data.getName());
        this.pluginData = data.build();
    }

    public FakeDioritePlugin(final DioritePlugin parent, final String name, final String version, final String author)
    {
        this(parent, new PluginDataBuilder(name, version, author));
    }

    @Override
    public void onLoad()
    {
    }

    @Override
    public void onEnable()
    {
    }

    @Override
    public void onDisable()
    {
    }

    @Override
    public String getPrefix()
    {
        return this.pluginData.getPrefix();
    }

    @Override
    public final String getName()
    {
        return this.pluginData.getName();
    }

    @Override
    public final String getVersion()
    {
        return this.pluginData.getVersion();
    }

    @Override
    public String getAuthor()
    {
        return this.pluginData.getAuthor();
    }

    @Override
    public String getDescription()
    {
        return this.pluginData.getDescription();
    }

    @Override
    public String getWebsite()
    {
        return this.pluginData.getWebsite();
    }

    @Override
    public Logger getLogger()
    {
        if (this.logger == null)
        {
            this.logger = LoggerFactory.getLogger("[" + this.getPrefix() + "]");
        }
        return this.logger;
    }

    @Override
    public PluginLoader getPluginLoader()
    {
        return this.loader;
    }

    @Override
    public File getDataFolder()
    {
        if (this.dataFolder == null)
        {
            this.dataFolder = new File(this.getParent().getDataFolder(), this.getSimpleName());
        }
        return this.dataFolder;
    }

    @Override
    public void init(final PluginClassLoader classLoader, final PluginLoader pluginLoader, final PluginDataBuilder data)
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
            this.getLogger().info("Enabling " + this.getFullName());
            this.onEnable();
        }
        else
        {
            this.getLogger().info("Disabling " + this.getFullName());
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

    @Override
    public BasePlugin getParent()
    {
        return this.parent;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("parent", this.parent).toString();
    }
}
