package org.diorite.permissions.pattern;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class SimplePermissionPattern implements PermissionPattern
{
    private final String permission;

    public SimplePermissionPattern(String permission)
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
        if (! (o instanceof SimplePermissionPattern))
        {
            return false;
        }

        final SimplePermissionPattern that = (SimplePermissionPattern) o;
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
