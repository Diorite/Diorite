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

import org.diorite.impl.permissions.pattern.PermissionPatternImpl;
import org.diorite.permissions.BasicPermission;
import org.diorite.permissions.PermissionLevel;
import org.diorite.permissions.pattern.PermissionPattern;

public class PermissionImpl extends BasicPermission
{
    private final PermissionPattern pattern;

    public PermissionImpl(final PermissionLevel defaultLevel, final PermissionPattern pattern)
    {
        super(defaultLevel);
        Validate.notNull(pattern, "Pattern can't be null.");
        this.pattern = pattern;
    }

    public PermissionImpl(final PermissionPattern pattern)
    {
        this(PermissionLevel.OP, pattern);
    }

    public PermissionImpl(final PermissionLevel defaultLevel, final String permission)
    {
        this(defaultLevel, new PermissionPatternImpl(permission));
    }

    public PermissionImpl(final String permission)
    {
        this(PermissionLevel.OP, new PermissionPatternImpl(permission));
    }

    @Override
    public PermissionPattern getPattern()
    {
        return this.pattern;
    }

    @Override
    public String getValue()
    {
        return this.pattern.getValue();
    }

    @Override
    public boolean equals(final Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (! (o instanceof PermissionImpl))
        {
            return false;
        }

        final PermissionImpl that = (PermissionImpl) o;

        return this.pattern.equals(that.pattern);
    }

    @Override
    public int hashCode()
    {
        return this.pattern.hashCode();
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("pattern", this.pattern).toString();
    }
}
