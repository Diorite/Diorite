package org.diorite.permissions.pattern;

import org.diorite.permissions.pattern.group.SpecialGroup;

public class AdvancedPermissionPattern implements PermissionPattern
{
    protected final String         basePermission; // permission without pattern in it, like "foo.{$++}.bar.{$--}" will be "foo..bar." here
    protected final SpecialGroup[] groups;

    public AdvancedPermissionPattern(final String basePermission, final SpecialGroup[] groups)
    {
        this.basePermission = basePermission;
        this.groups = groups;
    }

    @Override
    public boolean isValid(final String str)
    {// TODO
        return false;
    }
}
