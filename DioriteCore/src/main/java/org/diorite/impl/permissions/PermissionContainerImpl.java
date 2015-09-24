package org.diorite.impl.permissions;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public abstract class PermissionContainerImpl
{
    private PermissionContainerImpl parent;

    public PermissionContainerImpl()
    {
    }

    public PermissionContainerImpl(final PermissionContainerImpl parent)
    {
        this.parent = parent;
    }

    public void setParent(final PermissionContainerImpl parent)
    {
        this.parent = parent;
    }

    public boolean hasPermission(final String permission)
    {
        // TODO;
        return false;
    }

    public boolean hasPermission(final String pattern, final String permission)
    {
        // TODO;
        return false;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("parent", this.parent).toString();
    }
}
