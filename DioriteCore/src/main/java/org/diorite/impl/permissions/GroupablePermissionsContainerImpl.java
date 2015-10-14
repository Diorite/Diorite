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
    protected SortedSet<GroupEntry> groups = new TreeSet<>();

    protected GroupablePermissionsContainerImpl()
    {
    }

    protected GroupablePermissionsContainerImpl(final Permissible permissible)
    {
        super(permissible);
    }

    protected GroupablePermissionsContainerImpl(final PermissionsContainerImpl parent)
    {
        super(parent);
    }

    @Override
    public void addGroup(final GroupEntry group)
    {
        this.groups.add(group);
    }

    public PermissionLevel getPermissionLevel(final Permission permission)
    {
        final PermissionEntry<?> entry;
        if (permission instanceof PermissionImpl)
        {
            entry = (this.permissions == null) ? null : this.permissions.get(permission);
        }
        else if ((permission instanceof CheckExtendedPermission) || (permission instanceof AdvancedPermission))
        {
            entry = (this.advancedPermissions == null) ? null : this.advancedPermissions.get(permission.getPattern());
        }
        else
        {
            throw new UnsupportedOperationException("Unknown type of permission!");
        }

        if ((entry == null) || ((permission instanceof CheckExtendedPermission) && (entry.getPermission() instanceof AdvancedPermission) && ! isMatching((AdvancedPermission) entry.getPermission(), (CheckExtendedPermission) permission)))
        {
            if ((this.groups != null) && ! this.groups.isEmpty())
            {
                for (final GroupEntry group : this.groups)
                {
                    final PermissionLevel level = group.getGroup().getPermissionsContainer().getPermissionLevel(permission);
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
    public GroupEntry removeGroup(final PermissionsGroup group)
    {
        GroupEntry result = null;
        for (final Iterator<GroupEntry> iterator = this.groups.iterator(); iterator.hasNext(); )
        {
            final GroupEntry entry = iterator.next();
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
            if (entry.getPriotity() > result.getPriotity())
            {
                result = entry;
            }
        }
        return result;
    }

    @Override
    public boolean removeGroup(final GroupEntry group)
    {
        return this.groups.remove(group);
    }

    @Override
    public boolean isInGroup(final PermissionsGroup group)
    {
        for (final GroupEntry groupEntry : this.groups)
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
