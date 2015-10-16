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
