package org.diorite.permissions;

import org.diorite.permissions.pattern.PermissionPattern;

/**
 * Class representing object that can have permissions.
 */
public interface Permissible
{
    /**
     * Returns true if this object have given permission. <br>
     *
     * @param permission permission to check.
     *
     * @return true if this object have given permission.
     */
    default boolean hasPermission(final String permission)
    {
        return this.getPermissionsContainer().hasPermission(permission);
    }

    /**
     * Returns true if this object have given permission. <br>
     * This method should be much faster than {@link #hasPermission(String)},
     * as you need pass pattern too, it can be even faster if there is registred
     * permissions pattern with this value.
     *
     * @param pattern    pattern of permission, like "foo.{$++}.bar".
     * @param permission permission to check, like "foo.5.bar".
     *
     * @return true if this object have given permission.
     */
    default boolean hasPermission(final String pattern, final String permission)
    {
        return this.getPermissionsContainer().hasPermission(pattern, permission);
    }

    /**
     * Returns true if this object have given permission. <br>
     * This method should be much faster than {@link #hasPermission(String)},
     * as you need pass pattern too, it can be even faster if there is registred
     * permissions pattern with this value.
     *
     * @param pattern    pattern object for given permission, like "foo.{$++}.bar".
     * @param permission permission to check, like "foo.5.bar".
     *
     * @return true if this object have given permission.
     */
    default boolean hasPermission(final PermissionPattern pattern, final String permission)
    {
        return this.getPermissionsContainer().hasPermission(pattern, permission);
    }

    /**
     * Returns true if this object have given permission. <br>
     * This is fasted method, but you need registred permssion object.
     *
     * @param permission permission object.
     *
     * @return true if this object have given permission.
     */
    default boolean hasPermission(final Permission permission)
    {
        final PermissionsContainer pc = this.getPermissionsContainer();
        return (pc == null) ? permission.getDefaultLevel().getValue(false) : pc.hasPermission(permission);
    }

    /**
     * Returns object containing all permissions for this permissible.
     *
     * @return object containing all permissions for this permissible.
     */
    PermissionsContainer getPermissionsContainer();

    /**
     * Update permissions status, check for cyclic references etc.
     */
    default void recalculatePermissions()
    {
        this.getPermissionsContainer().recalculatePermissions();
    }
}
