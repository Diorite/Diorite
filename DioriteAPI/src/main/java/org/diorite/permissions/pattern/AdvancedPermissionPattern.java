package org.diorite.permissions.pattern;

import org.apache.commons.lang3.builder.ToStringBuilder;

import org.diorite.permissions.pattern.group.SpecialGroup;

/*
TODO: pattern podawany podczas sprawdzania
Pat: foo.{$++}.bar.{$--}
playerpex: foo.5.bar.3
checkpex: foo.2.bar.2


 */
public class AdvancedPermissionPattern implements PermissionPattern
{
    protected final String            basePermission; // permission without pattern in it, like "foo.{$++}.bar.{$--}" will be "foo..bar." here
    protected final SpecialGroup<?>[] groups;

    public AdvancedPermissionPattern(final String basePermission, final SpecialGroup<?>[] groups)
    {
        this.basePermission = basePermission;
        this.groups = groups;
    }

    public void test(final AdvancedPermission playerPex, final AdvancedPermission checkPex)
    {
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
    }

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
        return false;
    }
}
