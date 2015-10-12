package org.diorite.permissions;

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
     * Check if given permission is compatible with this one. <br>
     * This method must return true when checking permissions to make check return true too. <br>
     *
     * @param permission permissions to true.
     *
     * @return true if given permission is compatible with this one.
     */
    default boolean isMatching(final Permission permission)
    {
        return true;
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
