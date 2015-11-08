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

package org.diorite.impl.permissions.pattern;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.permissions.Permission;
import org.diorite.permissions.PermissionLevel;
import org.diorite.permissions.pattern.PermissionPattern;

/**
 * Basic abstract permission pattern implementing only permissions map,
 *
 * @see PermissionPattern#getPermissions()
 */
public abstract class AbstractPermissionPattern implements PermissionPattern
{
    /**
     * This pattern child permissions.
     */
    protected Map<Permission, PermissionLevel> permissions;

    @Override
    public synchronized Map<Permission, PermissionLevel> getPermissions()
    {
        if (this.permissions == null)
        {
            this.permissions = new HashMap<>(16, .4f);
        }
        return this.permissions;
    }

    @Override
    public synchronized void setPermission(final Permission permission, final PermissionLevel level)
    {
        if ((level == null) && (this.permissions == null))
        {
            return;
        }
        final Map<Permission, PermissionLevel> map = this.getPermissions();
        if (level == null)
        {
            this.permissions.remove(permission);
            if (this.permissions.isEmpty())
            {
                this.permissions = null;
            }
            return;
        }
        map.put(permission, level);
    }

    @Override
    public synchronized boolean containsPermissions()
    {
        return (this.permissions != null) && ! this.permissions.isEmpty();
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("permissions", this.permissions).toString();
    }
}
