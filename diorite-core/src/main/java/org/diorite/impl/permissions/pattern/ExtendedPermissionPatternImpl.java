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

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.permissions.pattern.ExtendedPermissionPattern;
import org.diorite.permissions.pattern.group.SpecialGroup;

public class ExtendedPermissionPatternImpl extends AbstractPermissionPattern implements ExtendedPermissionPattern
{
    /**
     * Value of pattern, needed for {@link #getValue()}
     */
    protected final String              patternValue;
    /**
     * Permission without pattern in it, like "foo.{$++}.bar.{$--}" will be "foo..bar." here
     */
    protected final String              basePermission;
    /**
     * All special groups and index in {@link #basePermission} where pattern should start.
     */
    protected final SpecialGroupEntry[] groups;
    /**
     * Array of permission parts splited by special groups where null values means special groups,
     * like "foo.fos.{$++}.bar.{$--}" will be ["foo.fos.", null, ".bar.", null]
     */
    protected final String[]            permMap;

    @SuppressWarnings("rawtypes")
    private static class SpecialGroupEntry
    {
        private final SpecialGroup group;
        private final int          index;

        private SpecialGroupEntry(final SpecialGroup<?, ?> group, final int index)
        {
            this.group = group;
            this.index = index;
        }

        @Override
        public String toString()
        {
            return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("group", this.group).append("index", this.index).toString();
        }
    }

    /**
     * Construct new advanced permission pattern.
     *
     * @param patternValue value of permission.
     * @param groups       special groups used by this pattern.
     */
    public ExtendedPermissionPatternImpl(String patternValue, final SpecialGroup<?, ?>[] groups)
    {
        Validate.isTrue(groups.length > 0, "Advanced pattern must use special groups.");
        patternValue = patternValue.intern();
        this.patternValue = patternValue;
        String temp = patternValue;
        final List<String> parts = new ArrayList<>(10);
        this.groups = new SpecialGroupEntry[groups.length];
        try
        {
            int lastPart = 0;
            String rest = null;
            for (int i = 0, groupsLength = groups.length; i < groupsLength; i++)
            {
                final SpecialGroup<?, ?> group = groups[i];
                final String pat = group.getGroupPattern();
                final int index = temp.indexOf(pat);
                if (index == - 1)
                {
                    throw new RuntimeException("Pattern value doesn't match all special groups.");
                }
                this.groups[i] = new SpecialGroupEntry(group, index);
                final String part = temp.substring(lastPart, index);
                lastPart = index;
                if (! part.isEmpty())
                {
                    parts.add(part);
                }
                parts.add(null);
                rest = temp.substring(index + pat.length());
                temp = temp.substring(0, index) + rest;
            }
            if ((rest != null) && ! rest.isEmpty())
            {
                parts.add(rest);
            }
        } catch (final Exception e)
        {
            throw new RuntimeException("Pattern value doesn't match all special groups.", e);
        }
        this.permMap = parts.toArray(new String[parts.size()]);
        this.basePermission = temp;
    }

    @Override
    @SuppressWarnings("rawtypes")
    public SpecialGroup[] getGroups()
    {
        final SpecialGroup[] groups = new SpecialGroup[this.groups.length];
        for (int i = 0; i < this.groups.length; i++)
        {
            groups[i] = this.groups[i].group;
        }
        return groups;
    }

    @Override
    public boolean isValidValue(final String str)
    {
        int groupIndex = 0;
        String toCheck = str;
        for (int i = 0; i < this.permMap.length; i++)
        {
            final String mapPart = this.permMap[i];
            if (mapPart == null)
            {
                if (groupIndex >= this.groups.length)
                {
                    return false;
                }
                final SpecialGroupEntry entry = this.groups[groupIndex];
                if ((i + 1) >= this.permMap.length)
                {
                    return entry.group.isValidValue(toCheck);
                }
                else
                {
                    final String nextMapPart = this.permMap[i + 1];
                    if (nextMapPart == null)
                    {
                        return false; // maybe throw exception here?
                    }
                    final int index = toCheck.indexOf(nextMapPart);
                    if (index == - 1)
                    {
                        return false;
                    }
                    final String groupData = toCheck.substring(0, index);
                    if (! entry.group.isValidValue(groupData))
                    {
                        return false;
                    }
                    groupIndex++;
                    toCheck = toCheck.substring(index);
                }
            }
            else
            {
                if (! toCheck.startsWith(mapPart))
                {
                    return false;
                }
                toCheck = toCheck.substring(mapPart.length());
            }

        }
        return toCheck.isEmpty();
    }

    @Override
    public boolean isValid(final String str)
    {
        int groupIndex = 0;
        String toCheck = str;
        for (int i = 0; i < this.permMap.length; i++)
        {
            final String mapPart = this.permMap[i];
            if (mapPart == null)
            {
                if (groupIndex >= this.groups.length)
                {
                    return false;
                }
                final SpecialGroupEntry entry = this.groups[groupIndex];
                if ((i + 1) >= this.permMap.length)
                {
                    return entry.group.isValidPattern(toCheck);
                }
                else
                {
                    final String nextMapPart = this.permMap[i + 1];
                    if (nextMapPart == null)
                    {
                        return false; // maybe throw exception here?
                    }
                    final int index = toCheck.indexOf(nextMapPart);
                    if (index == - 1)
                    {
                        return false;
                    }
                    final String groupData = toCheck.substring(0, index);
                    if (! entry.group.isValidPattern(groupData))
                    {
                        return false;
                    }
                    groupIndex++;
                    toCheck = toCheck.substring(index);
                }
            }
            else
            {
                if (! toCheck.startsWith(mapPart))
                {
                    return false;
                }
                toCheck = toCheck.substring(mapPart.length());
            }

        }
        return toCheck.isEmpty();
    }

    @Override
    public Object[] getValueData(final String permission)
    {
        final Object[] data = new Object[this.groups.length];

        int groupIndex = 0;
        String toCheck = permission;
        for (int i = 0; i < this.permMap.length; i++)
        {
            final String mapPart = this.permMap[i];
            if (mapPart == null)
            {
                if (groupIndex >= this.groups.length)
                {
                    return null;
                }
                final SpecialGroupEntry entry = this.groups[groupIndex];
                if ((i + 1) >= this.permMap.length)
                {
                    if (! entry.group.isValidValue(toCheck))
                    {
                        return null;
                    }
                    data[groupIndex] = entry.group.parseValueData(toCheck);
                    return data;
                }
                else
                {
                    final String nextMapPart = this.permMap[i + 1];
                    if (nextMapPart == null)
                    {
                        return null;
                    }
                    final int index = toCheck.indexOf(nextMapPart);
                    if (index == - 1)
                    {
                        return null;
                    }
                    final String groupData = toCheck.substring(0, index);
                    if (! entry.group.isValidValue(groupData))
                    {
                        return null;
                    }
                    data[groupIndex] = entry.group.parseValueData(groupData);
                    groupIndex++;
                    toCheck = toCheck.substring(index);
                }
            }
            else
            {
                if (! toCheck.startsWith(mapPart))
                {
                    return null;
                }
                toCheck = toCheck.substring(mapPart.length());
            }

        }
        return data;
    }

    @Override
    public Object[] getPatternData(final String permission)
    {
        final Object[] data = new Object[this.groups.length];

        int groupIndex = 0;
        String toCheck = permission;
        for (int i = 0; i < this.permMap.length; i++)
        {
            final String mapPart = this.permMap[i];
            if (mapPart == null)
            {
                if (groupIndex >= this.groups.length)
                {
                    return null;
                }
                final SpecialGroupEntry entry = this.groups[groupIndex];
                if ((i + 1) >= this.permMap.length)
                {
                    if (! entry.group.isValidPattern(toCheck))
                    {
                        return null;
                    }
                    data[groupIndex] = entry.group.parsePatternData(toCheck);
                    return data;
                }
                else
                {
                    final String nextMapPart = this.permMap[i + 1];
                    if (nextMapPart == null)
                    {
                        return null;
                    }
                    final int index = toCheck.indexOf(nextMapPart);
                    if (index == - 1)
                    {
                        return null;
                    }
                    final String groupData = toCheck.substring(0, index);
                    if (! entry.group.isValidPattern(groupData))
                    {
                        return null;
                    }
                    data[groupIndex] = entry.group.parsePatternData(groupData);
                    groupIndex++;
                    toCheck = toCheck.substring(index);
                }
            }
            else
            {
                if (! toCheck.startsWith(mapPart))
                {
                    return null;
                }
                toCheck = toCheck.substring(mapPart.length());
            }

        }
        return data;
    }

    @Override
    public String getValue()
    {
        return this.patternValue;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this).append("basePermission", this.basePermission).append("groups", this.groups).toString();
    }

    @Override
    public boolean equals(final Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (! (o instanceof ExtendedPermissionPatternImpl))
        {
            return false;
        }

        final ExtendedPermissionPatternImpl pattern = (ExtendedPermissionPatternImpl) o;

        return this.patternValue.equals(pattern.patternValue) && this.basePermission.equals(pattern.basePermission);
    }

    @Override
    public int hashCode()
    {
        int result = this.patternValue.hashCode();
        result = (31 * result) + this.basePermission.hashCode();
        return result;
    }
}
