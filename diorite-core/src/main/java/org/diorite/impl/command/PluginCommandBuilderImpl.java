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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.command.CommandExecutor;
import org.diorite.command.ExceptionHandler;
import org.diorite.command.PluginCommandBuilder;
import org.diorite.plugin.DioritePlugin;

public class PluginCommandBuilderImpl implements PluginCommandBuilder
{
    private final DioritePlugin    dioritePlugin;
    private final String           name;
    private       Pattern          pattern;
    private       List<String>     aliases;
    private       CommandExecutor  executor;
    private       ExceptionHandler handler;
    private       byte             priority;

    private PluginCommandBuilderImpl(final DioritePlugin dioritePlugin, final String name)
    {
        this.dioritePlugin = dioritePlugin;
        this.name = name;
    }

    private void checkAliases()
    {
        if (this.pattern != null)
        {
            throw new IllegalArgumentException("Can't change aliasses if you use pattern.");
        }
        if (this.aliases == null)
        {
            this.aliases = new ArrayList<>(5);
        }
    }

    private void checkPattern()
    {
        if (this.aliases != null)
        {
            throw new IllegalArgumentException("Can't change pattern if you use alliases.");
        }
    }

    @Override
    public PluginCommandBuilderImpl alias(final String str)
    {
        this.checkAliases();
        this.aliases.add(str);
        return this;
    }

    @Override
    public PluginCommandBuilderImpl alias(final String... str)
    {
        this.checkAliases();
        Collections.addAll(this.aliases, str);
        return this;
    }

    @Override
    public PluginCommandBuilderImpl alias(final Collection<String> str)
    {
        this.checkAliases();
        this.aliases.addAll(str);
        return this;
    }

    @Override
    public PluginCommandBuilderImpl pattern(final String pattern)
    {
        this.checkPattern();
        this.pattern = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE);
        return this;
    }

    @Override
    public PluginCommandBuilderImpl pattern(final String... pattern)
    {
        this.checkPattern();
        this.pattern = Pattern.compile(StringUtils.join(pattern), Pattern.CASE_INSENSITIVE);
        return this;
    }

    @Override
    public PluginCommandBuilderImpl pattern(final Pattern pattern)
    {
        this.checkPattern();
        this.pattern = Pattern.compile(pattern.pattern(), pattern.flags());
        return this;
    }

    @Override
    public PluginCommandBuilderImpl executor(final CommandExecutor executor)
    {
        this.executor = executor;
        return this;
    }

    @Override
    public PluginCommandBuilderImpl exceptionHandler(final ExceptionHandler handler)
    {
        this.handler = handler;
        return this;
    }

    @Override
    public PluginCommandBuilderImpl priority(final byte priority)
    {
        this.priority = priority;
        return this;
    }

    @Override
    public PluginCommandImpl build()
    {
        if ((this.pattern == null) && (this.aliases == null))
        {
            this.aliases = new ArrayList<>(1);
        }
        final PluginCommandImpl cmd = (this.pattern == null) ? new PluginCommandImpl(this.name, this.aliases, this.priority, this.dioritePlugin) : new PluginCommandImpl(this.name, this.pattern, this.priority, this.dioritePlugin);
        if (this.executor != null)
        {
            cmd.setCommandExecutor(this.executor);
        }
        if (this.handler != null)
        {
            cmd.setExceptionHandler(this.handler);
        }
        return cmd;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("plugin", this.dioritePlugin).append("name", this.name).append("pattern", this.pattern).append("aliases", this.aliases).append("executor", this.executor).append("handler", this.handler).append("priority", this.priority).toString();
    }

    public static PluginCommandBuilderImpl start(final DioritePlugin dioritePlugin, final String name)
    {
        Objects.requireNonNull(dioritePlugin, "plugin can't be null.");
        Objects.requireNonNull(name, "name od command can't be null.");
        return new PluginCommandBuilderImpl(dioritePlugin, name);
    }
}
