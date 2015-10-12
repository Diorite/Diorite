package org.diorite.permissions.pattern;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.permissions.pattern.group.SpecialGroup;

/*
TODO: pattern podawany podczas sprawdzania
Pat: foo.{$++}.bar.{$--}
playerpex: foo.5.bar.3
checkpex: foo.2.bar.2


 */
public class AdvancedPermissionPattern implements PermissionPattern
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

    private static class SpecialGroupEntry
    {
        private final SpecialGroup<?> group;
        private final int             index;

        private SpecialGroupEntry(final SpecialGroup<?> group, final int index)
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

    public AdvancedPermissionPattern(String patternValue, final SpecialGroup<?>[] groups)
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
                final SpecialGroup<?> group = groups[i];
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

//    public void test(final AdvancedPermission playerPex, final AdvancedPermission checkPex)
//    {
//        int strIndex = 0;
//        int pexIndex = 0;
//        int gIndex = 0;
//        final char[] strChars = string.toCharArray();
//        final char[] pexChars = this.basePermission.toCharArray();
//        while (true)
//        {
//            if (strChars[strIndex++] == pexChars[pexIndex++])
//            {
//                continue;
//            }
//            if (gIndex >= this.groups.length)
//            {
//                throw new RuntimeException();
//                // TODO: invalid
//            }
//            final SpecialGroup group = this.groups[gIndex++];
//            group.parse(string.substring(strIndex));
//        }
//    }

//    // foo.{$++}.bar
//    @Override
//    public boolean isValid(final String str)
//    {
//        int gIndex =0;
//        int sIndex = 0;
//        final char[] chars = str.toCharArray();
//        for (;gIndex < this.groups.length; gIndex++)
//        {
//            if (chars[sIndex] == '{')
//            {
//                final SpecialGroup<?> group = this.groups[gIndex];
//                group.isValid(str.substring(sIndex));
//            }
//        }
//    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this).append("basePermission", this.basePermission).append("groups", this.groups).toString();
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
                    return entry.group.isValid(toCheck);
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
                    if (! entry.group.isValid(groupData))
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
    public String getValue()
    {
        return this.patternValue;
    }
}
