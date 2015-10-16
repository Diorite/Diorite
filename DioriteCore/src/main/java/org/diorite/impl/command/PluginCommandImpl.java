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

package org.diorite.impl.command;

import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.command.PluginCommand;
import org.diorite.plugin.DioritePlugin;

public class PluginCommandImpl extends MainCommandImpl implements PluginCommand
{
    private final DioritePlugin dioritePlugin;

    public PluginCommandImpl(final String name, final Pattern pattern, final byte priority, final DioritePlugin dioritePlugin)
    {
        super(name, pattern, priority);
        this.dioritePlugin = dioritePlugin;
    }

    public PluginCommandImpl(final String name, final Pattern pattern, final DioritePlugin dioritePlugin)
    {
        super(name, pattern);
        this.dioritePlugin = dioritePlugin;
    }

    public PluginCommandImpl(final String name, final DioritePlugin dioritePlugin)
    {
        super(name);
        this.dioritePlugin = dioritePlugin;
    }

    public PluginCommandImpl(final String name, final Collection<String> aliases, final byte priority, final DioritePlugin dioritePlugin)
    {
        super(name, aliases, priority);
        this.dioritePlugin = dioritePlugin;
    }

    public PluginCommandImpl(final String name, final Collection<String> aliases, final DioritePlugin dioritePlugin)
    {
        super(name, aliases);
        this.dioritePlugin = dioritePlugin;
    }

    @Override
    public DioritePlugin getPlugin()
    {
        return this.dioritePlugin;
    }


    @Override
    public Matcher matcher(final String name)
    {
        if (name.toLowerCase().startsWith(this.dioritePlugin.getName().toLowerCase() + COMMAND_PLUGIN_SEPARATOR))
        {
            return super.matcher(name.replace(this.dioritePlugin.getName().toLowerCase() + COMMAND_PLUGIN_SEPARATOR, ""));
        }
        else
        {
            return super.matcher(name);
        }
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("plugin", this.dioritePlugin).toString();
    }
}
