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

import org.apache.commons.lang3.builder.ToStringBuilder;

import org.diorite.permissions.pattern.PermissionPattern;

public class PermissionPatternImpl extends AbstractPermissionPattern
{
    private final String permission;

    public PermissionPatternImpl(String permission)
    {
        permission = permission.intern();
        this.permission = permission;
    }

    @Override
    public boolean isValid(final String str)
    {
        return this.permission.equalsIgnoreCase(str);
    }

    @Override
    public String getValue()
    {
        return this.permission;
    }

    @Override
    public boolean equals(final Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (! (o instanceof PermissionPattern))
        {
            return false;
        }

        final PermissionPatternImpl that = (PermissionPatternImpl) o;
        return this.permission.equals(that.permission);
    }

    @Override
    public int hashCode()
    {
        return this.permission.hashCode();
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this).append("permission", this.permission).toString();
    }
}
