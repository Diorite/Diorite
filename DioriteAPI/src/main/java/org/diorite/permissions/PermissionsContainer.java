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
