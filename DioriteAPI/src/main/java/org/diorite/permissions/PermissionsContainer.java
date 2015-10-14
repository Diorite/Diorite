package org.diorite.permissions;

/**
 * Represents set of permissions, every container can have single parent,
 * when checking permissions container should check own data, and if it don't know anything
 * about permissions then check parent. So parent data have lower priority. <br>
 * You should not use metohds like {@link #setPermission(Permission, PermissionLevel)} or {@link #removePermission(Permission)}
 * if you only want edit permissions of player/group, use {@link PermissionsManager} instead.
 */
public interface PermissionsContainer extends Permissible
{
    /**
     * Returns {@link PermissionLevel} for given permission, if container or parent containers don't contains
     * this permissions, null will be returned instead.
     *
     * @param permission permission to check.
     *
     * @return {@link PermissionLevel} for given permission.
     */
    PermissionLevel getPermissionLevel(Permission permission);

    /**
     * Add permissions to this container, use null level to remove permission from container.
     *
     * @param permission permission to set.
     * @param level      level of given permission.
     */
    void setPermission(Permission permission, PermissionLevel level);

    /**
     * Remove permissions from this container, works like using null for level in {@link #setPermission(Permission, PermissionLevel)}
     *
     * @param permission permission to remove.
     */
    void removePermission(Permission permission);

    @Override
    void recalculatePermissions(); // overrided to force different implementation
}
