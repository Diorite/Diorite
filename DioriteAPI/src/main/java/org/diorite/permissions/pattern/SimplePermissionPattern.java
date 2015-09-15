package org.diorite.permissions.pattern;

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
}
