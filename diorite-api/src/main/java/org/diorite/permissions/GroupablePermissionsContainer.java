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
     *
     * @return true if group was removed.
     */
    boolean removeGroup(GroupEntry group);

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
