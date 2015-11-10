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

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class GroupEntry implements Comparable<GroupEntry>
{
    private final PermissionsGroup group;
    private final int              priotity;

    public GroupEntry(final PermissionsGroup group, final int priotity)
    {
        Validate.notNull(group, "group can't be null.");
        this.group = group;
        this.priotity = priotity;
    }

    public PermissionsGroup getGroup()
    {
        return this.group;
    }

    public int getPriotity()
    {
        return this.priotity;
    }

    public String getName()
    {
        return this.group.getName();
    }

    @Override
    public int compareTo(final GroupEntry o)
    {
        final int i = Integer.compare(o.priotity, this.priotity);
        if (i != 0)
        {
            return i;
        }
        return o.getName().compareTo(this.getName());
    }

    @Override
    public boolean equals(final Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (! (o instanceof GroupEntry))
        {
            return false;
        }

        final GroupEntry that = (GroupEntry) o;

        return (this.priotity == that.priotity) && this.group.equals(that.group);

    }

    @Override
    public int hashCode()
    {
        int result = this.group.hashCode();
        result = (31 * result) + this.priotity;
        return result;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("group", this.group).append("priotity", this.priotity).toString();
    }
}
