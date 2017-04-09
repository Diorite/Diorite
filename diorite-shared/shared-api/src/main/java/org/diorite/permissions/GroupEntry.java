/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017. Diorite (by Bartłomiej Mazur (aka GotoFinal))
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

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class GroupEntry implements Comparable<GroupEntry>
{
    private final PermissionsGroup group;
    private final int              priority;

    public GroupEntry(PermissionsGroup group, int priority)
    {
        this.group = group;
        this.priority = priority;
    }

    public PermissionsGroup getGroup()
    {
        return this.group;
    }

    public int getPriority()
    {
        return this.priority;
    }

    public String getName()
    {
        return this.group.getName();
    }

    @Override
    public int compareTo(GroupEntry o)
    {
        int i = Integer.compare(o.priority, this.priority);
        if (i != 0)
        {
            return i;
        }
        return o.getName().compareTo(this.getName());
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (! (o instanceof GroupEntry))
        {
            return false;
        }

        GroupEntry that = (GroupEntry) o;

        return (this.priority == that.priority) && this.group.equals(that.group);

    }

    @Override
    public int hashCode()
    {
        int result = this.group.hashCode();
        result = (31 * result) + this.priority;
        return result;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("group", this.group)
                                                                          .append("priority", this.priority).toString();
    }
}
