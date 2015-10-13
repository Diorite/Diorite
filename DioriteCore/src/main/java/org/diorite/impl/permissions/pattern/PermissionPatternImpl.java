package org.diorite.impl.permissions.pattern;

import org.apache.commons.lang3.builder.ToStringBuilder;

import org.diorite.permissions.pattern.PermissionPattern;

public class PermissionPatternImpl extends AbstractPermissionPattern
{
    private final String permission;

    public PermissionPatternImpl(String permission)
    {
        permission = permission.intern();
        this.permission = permission;
    }

    @Override
    public boolean isValid(final String str)
    {
        return this.permission.equalsIgnoreCase(str);
    }

    @Override
    public String getValue()
    {
        return this.permission;
    }

    @Override
    public boolean equals(final Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (! (o instanceof PermissionPattern))
        {
            return false;
        }

        final PermissionPatternImpl that = (PermissionPatternImpl) o;
        return this.permission.equals(that.permission);
    }

    @Override
    public int hashCode()
    {
        return this.permission.hashCode();
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this).append("permission", this.permission).toString();
    }
}
