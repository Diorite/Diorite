package org.diorite.impl.permissions;

import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.permissions.GroupEntry;
import org.diorite.permissions.GroupablePermissionsContainer;
import org.diorite.permissions.Permissible;
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
    public void removeGroup(final GroupEntry group)
    {
        this.groups.remove(group);
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
