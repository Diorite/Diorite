package org.diorite.impl.permissions.pattern;

public class AdvancedPermissionPatternImpl extends PermissionPatternImpl
{
    protected final String         basePermission; // permission without pattern in it, like "foo.{$++}.bar.{$--}" will be "foo..bar." here
    protected final SpecialGroup[] groups;

    public AdvancedPermissionPatternImpl(final String basePermission, final SpecialGroup[] groups)
    {
        this.basePermission = basePermission;
        this.groups = groups;
    }

}
