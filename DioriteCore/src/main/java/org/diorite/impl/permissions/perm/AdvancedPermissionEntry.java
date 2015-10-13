package org.diorite.impl.permissions.perm;

import org.diorite.permissions.AdvancedPermission;
import org.diorite.permissions.PermissionLevel;

public class AdvancedPermissionEntry extends PermissionEntry<AdvancedPermission>
{
    public AdvancedPermissionEntry(final AdvancedPermission permission, final PermissionLevel level, final boolean fromParent)
    {
        super(permission, level, fromParent);
    }
}
