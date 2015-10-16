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

package org.diorite.impl.permissions.perm;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.permissions.Permission;
import org.diorite.permissions.PermissionLevel;

public class PermissionEntry<T extends Permission>
{
    private final T               permission;
    private final PermissionLevel level;
    private final boolean         fromParent;

    public PermissionEntry(final T permission, final PermissionLevel level, final boolean fromParent)
    {
        Validate.notNull(permission, "Permission can't be null.");
        Validate.notNull(level, "Level can't be null.");
        this.permission = permission;
        this.level = level;
        this.fromParent = fromParent;
    }

    public T getPermission()
    {
        return this.permission;
    }

    public PermissionLevel getLevel()
    {
        return this.level;
    }

    public boolean isFromParent()
    {
        return this.fromParent;
    }

    @Override
    public boolean equals(final Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (! (o instanceof PermissionEntry))
        {
            return false;
        }

        final PermissionEntry<?> that = (PermissionEntry<?>) o;

        return (this.fromParent == that.fromParent) && this.permission.equals(that.permission) && (this.level == that.level);
    }

    @Override
    public int hashCode()
    {
        int result = this.permission.hashCode();
        result = (31 * result) + this.level.hashCode();
        result = (31 * result) + (this.fromParent ? 1 : 0);
        return result;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("permission", this.permission).append("level", this.level).append("fromParent", this.fromParent).toString();
    }
}
