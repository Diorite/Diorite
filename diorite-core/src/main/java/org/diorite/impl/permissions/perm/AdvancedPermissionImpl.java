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

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.permissions.AdvancedPermission;
import org.diorite.permissions.BasicPermission;
import org.diorite.permissions.PermissionLevel;
import org.diorite.permissions.pattern.ExtendedPermissionPattern;

/**
 * Represents advanced version of permission object.
 */
public class AdvancedPermissionImpl extends BasicPermission implements AdvancedPermission
{
    /**
     * Pattern of permission.
     */
    protected final ExtendedPermissionPattern pattern;
    /**
     * String representation of permission.
     */
    protected final String                    value;
    /**
     * Data from permission.
     */
    protected final Object[]                  data;

    public AdvancedPermissionImpl(final ExtendedPermissionPattern pattern, final PermissionLevel defaultLevel, final String permission)
    {
        super(defaultLevel);
        this.pattern = pattern;
        this.value = permission;
        this.data = pattern.getPatternData(permission);
    }

    @Override
    public Object[] getData()
    {
        return this.data;
    }

    @Override
    public ExtendedPermissionPattern getPattern()
    {
        return this.pattern;
    }

    @Override
    public String getValue()
    {
        return this.value;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("pattern", this.pattern).append("value", this.value).append("data", this.data).toString();
    }
}
