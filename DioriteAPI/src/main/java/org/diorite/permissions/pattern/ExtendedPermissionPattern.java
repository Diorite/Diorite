package org.diorite.permissions.pattern;

import org.diorite.permissions.pattern.group.SpecialGroup;

/**
 * Represent more complicated permission pattern that use {@link SpecialGroup}s.
 */
public interface ExtendedPermissionPattern extends PermissionPattern
{
    /**
     * Returns array of {@link SpecialGroup}s used by this extended pattern.
     *
     * @return array of {@link SpecialGroup}s used by this extended pattern.
     */
    @SuppressWarnings("rawtypes")
    SpecialGroup[] getGroups();

    /**
     * Checks if given string can be a permission value using this pattern. <br>
     * Like "foo.5" will return true for "foo.{$-$}" pattern, but "foo.5-10" will return false as this is pattern value.
     *
     * @param str string/permission to check.
     *
     * @return if given string is valid for this pattern.
     */
    boolean isValidValue(String str);

    /**
     * Get value data of given permission for this pattern,
     * if permission isn't valid NULL value will be returned. <br>
     * Like valid value data for {$-$} is a long value.
     *
     * @param permission permissions to extract data.
     *
     * @return data of permission or null.
     */
    Object[] getValueData(String permission);

    /**
     * Get pattern data of given permission for this pattern,
     * if permission isn't valid NULL value will be returned. <br>
     * Like valid pattern data for {$-$} is array of {@link org.diorite.utils.math.LongRange}
     *
     * @param permission permissions to extract data.
     *
     * @return data of permission or null.
     */
    Object[] getPatternData(String permission);
}
