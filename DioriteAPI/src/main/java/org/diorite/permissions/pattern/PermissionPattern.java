package org.diorite.permissions.pattern;

public interface PermissionPattern
{
    /**
     * Checks if given string can be a permission using this pattern. <br>
     * Like "foo.5" will return true for "foo.{$++}" or "foo.{$--}" pattern, but "foo.5a" will return false.
     *
     * @param str string/permission to check.
     *
     * @return if given string is valid for this pattern.
     */
    boolean isValid(String str);
}
