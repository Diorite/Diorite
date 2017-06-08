/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017. Diorite (by Bartłomiej Mazur (aka GotoFinal))
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

package org.diorite.command;

import javax.annotation.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.diorite.permissions.Permission;
import org.diorite.sender.CommandSender;

public abstract class CommandObject
{
    private String       name;
    private List<String> aliases;
    private @Nullable Collection<? extends Argument<?>> arguments = null;
    private int[] optional = {};
    private @Nullable CommandManager manager = null;
    private @Nullable Permission permission;
    protected @Nullable String description = "";
    protected String use;

    protected CommandObject(String name)
    {
        this(name, "", "", new ArrayList<String>());
    }

    protected CommandObject(String name, String description, String use, List<String> aliases)
    {
        this.name = name;
        this.description = description;
        this.use = use;
        this.aliases = aliases;
    }

    public abstract boolean invoke(CommandContext context);

    public boolean register(CommandManager manager)
    {
        if(allowChanges(manager))
        {
            this.manager = manager;
            return true;
        }
        return false;
    }

    private boolean allowChanges(CommandManager manager)
    {
        return (null == this.manager || this.manager == manager);
    }

    public String getName()
    {
        return name;
    }

    public boolean setName(String name)
    {
        //check if it is already registered
        this.name = name;
        return true;
    }

    @Nullable
    public Permission getPermission()
    {
        return this.permission;
    }

    public void setPermission(Permission permission)
    {
        this.permission = permission;
    }

    public boolean testPermission(CommandSender sender)
    {
        return (this.permission == null || sender.hasPermission(this.permission));
    }

    public List<String> getAliases()
    {
        return this.aliases;
    }

    public void setAliases(List<String> aliases)
    {
        this.aliases = aliases;
    }

    @Nullable
    public String getDescription()
    {
        return this.description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getUse()
    {
        return this.use;
    }

    public void setUse(String use)
    {
        this.use = use;
    }

    @Nullable
    public Collection<? extends Argument<?>> getArguments()
    {
        return this.arguments;
    }

    public void setArguments(Collection<? extends Argument<?>> arguments)
    {
        this.arguments = arguments;
    }

    public int[] getOptional()
    {
        return optional;
    }

    public void setOptional(int[] optional)
    {
        this.optional = optional;
    }
}
