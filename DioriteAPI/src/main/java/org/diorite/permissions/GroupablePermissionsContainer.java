package org.diorite.permissions;

import java.util.SortedSet;

/**
 * Represent permissions container that can have groups.
 *
 * @see PermissionsGroup
 */
public interface GroupablePermissionsContainer extends PermissionsContainer
{
    /**
     * Adds given group to this container. <br>
     * Groups with higher priotity will be checked first on hasPermission method.<br>
     * This method does not check if container already contains this group with any other priority.
     *
     * @param group group to add.
     */
    void addGroup(GroupEntry group);

    /**
     * Remove given group from this container. <br>
     * If container contains multiple entries for this group, all of them will be removed.
     *
     * @param group group to remove.
     *
     * @return removed group entry, if method removed more than ore group entry, this will return group entry with higher priority.
     */
    GroupEntry removeGroup(PermissionsGroup group);

    /**
     * Remove single group entry from this container.
     *
     * @param group group entry to remove.
     */
    void removeGroup(GroupEntry group);

    /**
     * Returns true if this container is in given group.
     *
     * @param group group to check.
     *
     * @return true if this container is in given group.
     */
    boolean isInGroup(PermissionsGroup group);

    /**
     * Returns sorted set with all groups.
     *
     * @return sorted set with all groups.
     */
    SortedSet<GroupEntry> getGroups();
}
