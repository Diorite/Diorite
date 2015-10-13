package org.diorite.impl.permissions.perm;

import org.diorite.permissions.Permission;
import org.diorite.permissions.PermissionLevel;

public class PermissionImplEntry extends PermissionEntry<Permission>
{
    public PermissionImplEntry(final Permission permission, final PermissionLevel level, final boolean fromParent)
    {
        super(permission, level, fromParent);
    }
}
