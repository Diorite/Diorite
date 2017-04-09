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

package org.diorite.impl.permissions;

import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.permissions.perm.CheckExtendedPermission;
import org.diorite.impl.permissions.perm.PermissionEntry;
import org.diorite.impl.permissions.perm.PermissionImpl;
import org.diorite.permissions.AdvancedPermission;
import org.diorite.permissions.GroupEntry;
import org.diorite.permissions.GroupablePermissionsContainer;
import org.diorite.permissions.Permissible;
import org.diorite.permissions.Permission;
import org.diorite.permissions.PermissionLevel;
import org.diorite.permissions.PermissionsGroup;

public class GroupablePermissionsContainerImpl extends PermissionsContainerImpl implements GroupablePermissionsContainer
{
    protected final SortedSet<GroupEntry> groups = new TreeSet<>();

    protected GroupablePermissionsContainerImpl()
    {
    }

    protected GroupablePermissionsContainerImpl(Permissible permissible)
    {
        super(permissible);
    }

    protected GroupablePermissionsContainerImpl(PermissionsContainerImpl parent)
    {
        super(parent);
    }

    @Override
    public void addGroup(GroupEntry group)
    {
        this.groups.add(group);
    }

    @Override
    public PermissionLevel getPermissionLevel(Permission permission)
    {
        PermissionEntry<?> entry;
        if (permission instanceof PermissionImpl)
        {
            entry = this.permissions.get(permission);
        }
        else if ((permission instanceof CheckExtendedPermission) || (permission instanceof AdvancedPermission))
        {
            entry = this.advancedPermissions.get(permission.getPattern());
        }
        else
        {
            throw new UnsupportedOperationException("Unknown type of permission!");
        }

        if ((entry == null) || ((permission instanceof CheckExtendedPermission) && (entry.getPermission() instanceof AdvancedPermission) && ! isMatching((AdvancedPermission) entry.getPermission(), (CheckExtendedPermission) permission)))
        {
            if (! this.groups.isEmpty())
            {
                for (GroupEntry group : this.groups)
                {
                    PermissionLevel level = group.getGroup().getPermissionsContainer().getPermissionLevel(permission);
                    if (level != null)
                    {
                        return level;
                    }
                }
            }
            if (this.parent != null)
            {
                return this.parent.getPermissionLevel(permission);
            }
            return null;
        }
        validateType(entry.getPermission());
        return entry.getLevel();
    }

    @Override
    public GroupEntry removeGroup(PermissionsGroup group)
    {
        GroupEntry result = null;
        for (Iterator<GroupEntry> iterator = this.groups.iterator(); iterator.hasNext(); )
        {
            GroupEntry entry = iterator.next();
            if (! entry.getGroup().equals(group))
            {
                continue;
            }
            iterator.remove();
            if (result == null)
            {
                result = entry;
                continue;
            }
            if (entry.getPriority() > result.getPriority())
            {
                result = entry;
            }
        }
        return result;
    }

    @Override
    public boolean removeGroup(GroupEntry group)
    {
        return this.groups.remove(group);
    }

    @Override
    public boolean isInGroup(PermissionsGroup group)
    {
        for (GroupEntry groupEntry : this.groups)
        {
            if (groupEntry.getGroup().equals(group))
            {
                return true;
            }
        }
        return false;
    }

    @Override
    public SortedSet<GroupEntry> getGroups()
    {
        return new TreeSet<>(this.groups);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("groups", this.groups).toString();
    }
}
