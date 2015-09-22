package org.diorite.permissions.pattern;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class SimplePermissionPattern implements PermissionPattern
{
    private final String permission;

    public SimplePermissionPattern(final String permission)
    {
        this.permission = permission;
    }

    @Override
    public boolean isValid(final String str)
    {
        return this.permission.equalsIgnoreCase(str);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this).append("permission", this.permission).toString();
    }
}
