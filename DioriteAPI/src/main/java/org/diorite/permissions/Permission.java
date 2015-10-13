package org.diorite.permissions;

import java.util.Map;

import org.diorite.permissions.pattern.PermissionPattern;

/**
 * Class representing a single permission node, like "foo.bar.5"
 */
public interface Permission
{
    /**
     * Returns a {@link PermissionPattern} for this permission.
     *
     * @return a {@link PermissionPattern} for this permission.
     */
    PermissionPattern getPattern();

    /**
     * Returns permission value, like "foo.bar.5".
     *
     * @return permission value.
     */
    String getValue();

    /**
     * Returns default level of permission.
     *
     * @return default level of permission.
     */
    PermissionLevel getDefaultLevel();

    /**
     * Change default level of this permission.
     *
     * @param defaultLevel new default level of this permission.
     */
    void setDefaultLevel(PermissionLevel defaultLevel);

    /**
     * Returns children permissions for pattern of this permission. <br>
     * If you edit this map you will need call {@link Permissible#recalculatePermissions()}
     *
     * @return children permissions for pattern of this permission.
     */
    default Map<Permission, PermissionLevel> getPermissions()
    {
        return this.getPattern().getPermissions();
    }

    /**
     * Returns true if this permissions contains any child permissions.
     *
     * @return true if this permissions contains any child permissions.
     */
    default boolean containsPermissions()
    {
        return this.getPattern().containsPermissions();
    }

    /**
     * Return true if given permissible object have this permission.
     *
     * @param permissible permissible object to check.
     *
     * @return true if given permissible object have this permission.
     *
     * @see Permissible#hasPermission(Permission)
     */
    default boolean has(final Permissible permissible)
    {
        return permissible.hasPermission(this);
    }
}
