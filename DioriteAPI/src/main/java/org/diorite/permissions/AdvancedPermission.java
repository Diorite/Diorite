package org.diorite.permissions;

import org.diorite.permissions.pattern.ExtendedPermissionPattern;

/**
 * Represent advanced permission that can contains data and use {@link org.diorite.permissions.pattern.ExtendedPermissionPattern}
 */
public interface AdvancedPermission extends Permission
{
    /**
     * Returns data from this permission,
     * like in pattern "foo.{$++}" and permission "foo.5", this array will contain: [5]
     *
     * @return data from this permission.
     */
    Object[] getData();

    @Override
    ExtendedPermissionPattern getPattern();
}
