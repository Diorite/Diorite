/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 Diorite (by Bartłomiej Mazur (aka GotoFinal))
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package org.diorite.permissions;

import java.util.SortedSet;

import org.diorite.Diorite;
import org.diorite.ServerManager;
import org.diorite.entity.Player;
import org.diorite.permissions.pattern.PermissionPattern;
import org.diorite.plugin.DioritePlugin;

/**
 * Interface with all needed permissions methods,
 * if you are only adding/removing permissions you should use it instead of methods in {@link PermissionsContainer} <br>
 *
 * @see Diorite#getServerManager()
 * @see ServerManager#getPermissionsManager()
 */
public interface PermissionsManager
{
    // TODO registring permissions (with childs) from file.

    /**
     * Method to get plugin that is managing permission system,
     * may return null if there is no plugin for that, and server
     * is using default implementation.
     *
     * @return plugin implementing permissions system or null.
     */
    DioritePlugin getImplementingPlugin();

    /**
     * Create permissions container for given permissible.
     *
     * @param permissible permissible that need container, may be null.
     *
     * @return new instance of permissions container.
     */
    PermissionsContainer createContainer(Permissible permissible);

    /**
     * Create player permissions container for given permissible.
     *
     * @param permissible permissible that need container, may be null.
     *
     * @return new instance of player permissions container.
     */
    PlayerPermissionsContainer createPlayerContainer(Player permissible);

    /**
     * Create groupable permissions container for given permissible.
     *
     * @param permissible permissible that need container, may be null.
     *
     * @return new instance of groupable permissions container.
     */
    GroupablePermissionsContainer createGroupableContainer(Permissible permissible);

    /**
     * Find permission by pattern, for single permissions pattern value is equals to permission value.
     *
     * @param permissionPattern pattern of permission to find.
     *
     * @return {@link Permission} object for this pattern.
     */
    Permission getPermission(String permissionPattern);

    /**
     * Find permission by pattern, for single permissions pattern value is equals to permission value.
     *
     * @param permissionPattern pattern of permission to find.
     *
     * @return {@link Permission} object for this pattern.
     */
    Permission getPermission(PermissionPattern permissionPattern);

    /**
     * Returns cached permission pattern object by pattern value.
     *
     * @param permissionPattern value of pattern to find.
     *
     * @return cached permission pattern object by pattern value.
     */
    PermissionPattern getPermissionPattern(String permissionPattern);

    /**
     * Find {@link PermissionPattern} by permission, like "foo.5.bar" or "foo.5-10.bar" may return "foo.{$-$}.bar" pattern.<br>
     * Method should use {@link PermissionPattern#isValid(String)} using given permission to find good pattern,
     * but it may use some cache too.
     *
     * @param permission permissions to use.
     *
     * @return valid permission pattern or null.
     */
    PermissionPattern getPatternByPermission(String permission);

    /**
     * Register new permission in this manager.
     *
     * @param permission permission to register.
     */
    void registerPermission(Permission permission);

    /**
     * Unregister given permission from this manager.
     *
     * @param pat pattern of permission to unregister.
     *
     * @return true if permissions was unregistered.
     */
    boolean unregisterPermission(PermissionPattern pat);

    /**
     * Unregister given permission from this manager.
     *
     * @param permission permission to unregister.
     *
     * @return true if permissions was unregistered.
     */
    default boolean unregisterPermission(final Permission permission)
    {
        return this.unregisterPermission(permission.getPattern());
    }

    /**
     * Returns true if given permissions is registered in this manager.
     *
     * @param permission permission to check.
     *
     * @return true if given permissions is registered in this manager.
     */
    boolean isRegisteredPermission(Permission permission);

    /**
     * Create simple permission object for given pattern value and default level.<br>
     * If permissions already exist method will just return old one. <br>
     * This method can't return null.
     *
     * @param permission   permission value of permission to create.
     * @param defaultLevel default level of new permission.
     *
     * @return created permission instance.
     */
    default Permission createSimplePermission(final String permission, final PermissionLevel defaultLevel)
    {
        return this.createPermission(permission, permission, defaultLevel);
    }

    /**
     * Create permission pattern for given pattern value, it will detect if pattern should be simple or advanced etc... <br>
     * If pattern already exist method will just return old one. <br>
     * This method can't return null.
     *
     * @param permissionPattern value of pattern to create.
     *
     * @return created permission pattern instance.
     */
    PermissionPattern createPattern(String permissionPattern);

    /**
     * Create permission object for given permission value and default level, it will detect if pattern should be simple or advanced etc... <br>
     * It will only create advanced permission if there is maching pattern for this permission in manager. <br>
     * If permissions already exist method will just return old one. <br>
     * This method can't return null.
     *
     * @param permission   permission value of permission to create.
     * @param defaultLevel default level of new permission.
     *
     * @return created permission instance.
     */
    Permission createPermission(String permission, PermissionLevel defaultLevel);

    /**
     * Create permission object for given pattern, value and default level, it will detect if pattern should be simple or advanced etc... <br>
     * If permissions already exist method will just return old one. <br>
     * This method can't return null.
     *
     * @param permissionPattern pattern of permission to create.
     * @param permission        permission value of permission to create.
     * @param defaultLevel      default level of new permission.
     *
     * @return created permission instance.
     */
    Permission createPermission(String permissionPattern, String permission, PermissionLevel defaultLevel);

    /**
     * Create permission object for given pattern and default level, it will detect if pattern should be simple or advanced etc...<br>
     * If permissions already exist method will just return old one. <br>
     * This method can't return null.
     *
     * @param permissionPattern pattern of permission to create.
     * @param permission        permission value of permission to create.
     * @param defaultLevel      default level of new permission.
     *
     * @return created permission instance.
     */
    Permission createPermission(PermissionPattern permissionPattern, String permission, PermissionLevel defaultLevel);

    /**
     * Create new permissions group with given name. <br>
     * If group already exist method will return that group.
     *
     * @param name name of permissions group.
     *
     * @return created or old if already exist permissions group.
     */
    PermissionsGroup createGroup(String name);

    /**
     * Adds given group to manager. <br>
     * If manager already contains this group (same reference) method will do nothing. <br>
     * But if manager contains group with this same name but other reference (other object)
     * method will register new group and update group in all online players containers.
     *
     * @param group group to add.
     */
    void addGroup(PermissionsGroup group);

    /**
     * Returns permissions group for given name, may return null if group doesn't exist yet.
     *
     * @param name name of permissions group.
     *
     * @return permissions group for given name, may return null if group doesn't exist yet.
     */
    PermissionsGroup getGroup(String name);

    /**
     * Returns true if this manager contains given group.
     *
     * @param name name of group.
     *
     * @return true if this manager contains given group.
     */
    boolean containsGroup(String name);

    /**
     * Returns true if this manager contains given group (must be this same reference/object. nut just name).
     *
     * @param group to check.
     *
     * @return true if this manager contains given group.
     */
    boolean containsGroup(PermissionsGroup group);

    /**
     * Remove permissions group and return it,
     * method will return null if there is no permissions group for given name. <br>
     * Method will remove this group from {@link GroupablePermissionsContainer} of online players.
     *
     * @param name name of permissions group to remove.
     *
     * @return removed group or null if there is no group with given name.
     */
    PermissionsGroup removeGroup(String name);

    /**
     * Method will add given permissible to given group with given priority,
     * if group doesn't exist (isn't registred) or permissible doesn't supprot groups method will do nothing and return false. <br>
     * If permissible is already in this group only priority will be updated.
     *
     * @param permissible permissible to add to a group.
     * @param group       group for permissible.
     * @param priotity    priority of group.
     *
     * @return true if permissible was added to group.
     */
    default boolean addPermissibleToGroup(final Permissible permissible, final String group, final int priotity)
    {
        final PermissionsGroup permissionsGroup = this.getGroup(group);
        return (group != null) && this.addPermissibleToGroup(permissible, new GroupEntry(permissionsGroup, priotity));
    }

    /**
     * Method will add given permissible to given group with given priority,
     * if group doesn't exist (isn't registred) or permissible doesn't supprot groups method will do nothing and return false. <br>
     * If permissible is already in this group only priority will be updated.
     *
     * @param permissible permissible to add to a group.
     * @param group       group for permissible.
     * @param priotity    priority of group.
     *
     * @return true if permissible was added to group.
     */
    default boolean addPermissibleToGroup(final Permissible permissible, final PermissionsGroup group, final int priotity)
    {
        return this.addPermissibleToGroup(permissible, new GroupEntry(group, priotity));
    }

    /**
     * Returns copy of permissible groups. <br>
     * If permissible can't have groups method will return empty set.
     *
     * @param permissible permissible to check
     *
     * @return copy of permissible groups.
     */
    SortedSet<GroupEntry> getPermissibleGroups(Permissible permissible);

    /**
     * Method will add given permissible to given group with given priority,
     * if group doesn't exist (isn't registred) or permissible doesn't supprot groups method will do nothing and return false. <br>
     * If permissible is already in this group only priority will be updated.
     *
     * @param permissible permissible to add to a group.
     * @param groupEntry  group for permissible.
     *
     * @return true if permissible was added to group.
     */
    boolean addPermissibleToGroup(final Permissible permissible, final GroupEntry groupEntry);

    /**
     * Method will remove given permissible from given group.
     * if group doesn't exist (isn't registred) or permissible doesn't supprot groups method will do nothing and return true. <br>
     *
     * @param permissible permissible to remove from a group.
     * @param group       group of permissible.
     *
     * @return true if permissible was removed from group.
     */
    default boolean removePermissibleFromGroup(final Permissible permissible, final String group)
    {
        final PermissionsGroup permissionsGroup = this.getGroup(group);
        return (group != null) && this.removePermissibleFromGroup(permissible, permissionsGroup);
    }

    /**
     * Method will remove given permissible from given group.
     * if group doesn't exist (isn't registred) or permissible doesn't supprot groups method will do nothing and return true. <br>
     *
     * @param permissible permissible to remove from a group.
     * @param group       group of permissible.
     *
     * @return true if permissible was removed from group.
     */
    boolean removePermissibleFromGroup(final Permissible permissible, final PermissionsGroup group);

    /**
     * Method will remove given permissible from given group only if priority matches.
     * if group doesn't exist (isn't registred) or permissible doesn't supprot groups method will do nothing and return true. <br>
     *
     * @param permissible permissible to remove from a group.
     * @param groupEntry  group of permissible.
     *
     * @return true if permissible was removed from group.
     */
    boolean removePermissibleFromGroup(final Permissible permissible, final GroupEntry groupEntry);

    /**
     * Adds given permission to given permissible (player, group etc...). <br>
     * Permission will be added to manager if it isn't already registered.
     *
     * @param permissible permissible to add new permission.
     * @param permission  permission to add.
     * @param level       level of given permission, use null to remove permission.
     */
    default void setPermission(final Permissible permissible, final String permission, final PermissionLevel level)
    {
        Permission perm = this.getPermission(permission);
        if (perm == null)
        {
            final PermissionPattern pattern = this.getPatternByPermission(permission);
            if (pattern != null)
            {
                perm = this.getPermission(pattern);
            }
        }
        if (perm == null)
        {
            perm = this.createPermission(permission, permission, PermissionLevel.OP);
        }
        this.setPermission(permissible, perm, level);
    }

    /**
     * Adds given permission to given permissible (player, group etc...). <br>
     * Permission will be added to manager if it isn't already registered.
     *
     * @param permissible       permissible to add new permission.
     * @param permissionPattern pattern of permission to add.
     * @param permission        permission to add.
     * @param level             level of given permission, use null to remove permission.
     */
    default void setPermission(final Permissible permissible, final String permissionPattern, final String permission, final PermissionLevel level)
    {
        Permission perm = this.getPermission(permissionPattern);
        if (perm == null)
        {
            perm = this.createPermission(permissionPattern, permission, PermissionLevel.OP);
        }
        this.setPermission(permissible, perm, level);
    }

    /**
     * Adds given permission to given permissible (player, group etc...). <br>
     * Permission will be added to manager if it isn't already registered.
     *
     * @param permissible       permissible to add new permission.
     * @param permissionPattern pattern of permission to add.
     * @param permission        permission to add.
     * @param level             level of given permission, use null to remove permission.
     */
    default void setPermission(final Permissible permissible, final PermissionPattern permissionPattern, final String permission, final PermissionLevel level)
    {
        Permission perm = this.getPermission(permissionPattern);
        if (perm == null)
        {
            perm = this.createPermission(permissionPattern, permission, PermissionLevel.OP);
        }
        this.setPermission(permissible, perm, level);
    }

    /**
     * Adds given permission to given permissible (player, group etc...). <br>
     * Permission will be added to manager if it isn't already registered.
     *
     * @param permissible permissible to add new permission.
     * @param permission  permission to add.
     * @param level       level of given permission, use null to remove permission.
     */
    void setPermission(Permissible permissible, Permission permission, final PermissionLevel level);

    /**
     * Remove given permission from given permissible.
     *
     * @param permissible permissible to remove a permission.
     * @param permission  permission to remove.
     */
    default void removePermission(final Permissible permissible, final String permission)
    {
        this.setPermission(permissible, permission, null);
    }

    /**
     * Remove given permission from given permissible.
     *
     * @param permissible       permissible to remove a permission.
     * @param permissionPattern pattern of permission to remove.
     * @param permission        permission to remove.
     */
    default void removePermission(final Permissible permissible, final String permissionPattern, final String permission)
    {
        this.setPermission(permissible, permissionPattern, permission, null);
    }

    /**
     * Remove given permission from given permissible.
     *
     * @param permissible       permissible to remove a permission.
     * @param permissionPattern pattern of permission to remove.
     * @param permission        permission to remove.
     */
    default void removePermission(final Permissible permissible, final PermissionPattern permissionPattern, final String permission)
    {
        this.setPermission(permissible, permissionPattern, permission, null);
    }

    /**
     * Remove given permission from given permissible.
     *
     * @param permissible permissible to remove a permission.
     * @param permission  permission to remove.
     */
    default void removePermission(final Permissible permissible, final Permission permission)
    {
        this.setPermission(permissible, permission, null);
    }

    /**
     * Called after all other checks from {@link PermissionsContainer}, manager can change result here,
     * so even if player don't have permission, manager can force method to return true.
     *
     * @param permissible parmissible object to check, it may be null!
     * @param permission  permission to check.
     * @param level       current result of check.
     *
     * @return new result of permission check.
     */
    default PermissionLevel onPermissionCheck(final Permissible permissible, final Permission permission, final PermissionLevel level)
    {
        return (level == null) ? permission.getDefaultLevel() : level;
    }
}
