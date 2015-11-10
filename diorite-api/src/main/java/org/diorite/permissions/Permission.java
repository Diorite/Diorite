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
