package org.diorite.permissions.pattern.group;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Base abstract class for special permission pattern groups, like permissions that can use number ranges. {@link RangeGroup}
 *
 * @param <R> type of input data when checking string.
 */
public abstract class SpecialGroup<R>
{
    /**
     * index of basePermission where data should be, like in "foo.{$++}.bar.{$--}" -> "foo..bar." it will be 3 and 8
     */
    protected final int index;

    /**
     * Construct new special group on given index.
     *
     * @param index index of basePermission where data should be stored.
     */
    public SpecialGroup(final int index)
    {
        this.index = index;
    }

    /**
     * Returns index of basePermission where data should be stored, like in "foo.{$++}.bar.{$--}" -> "foo..bar." it will be 3 and 8;
     *
     * @return index of basePermission where data should be stored.
     */
    public int getIndex()
    {
        return this.index;
    }

    /**
     * This method should parse given string, check if it is valid, and check if given data is in range, so hasPermission should return true. <br>
     * Given string ALWAYS must start with pattern data to parse, but may contains additional chars at the end,
     * like when parsing "foo.15.bar" with data 10 for pattern "foo.{$++}.bar", given string should be "15.bar", and it should return that
     * string is valid, (as it contains valid number where excepted), and it is matching LevelGroup 10 <= 15,
     * and end index of 2 as pattern data is only one 2 digit number, 2 chars.
     *
     * @param string given string to parse.
     * @param data   data to check.
     *
     * @return {@link GroupResult} results contains if string was valid, if data was matching, and end index of pattern data to get unparsed part of given string.
     */
    public abstract GroupResult parse(final String string, final R data);

    @Override
    public String toString()
    {
        return new ToStringBuilder(this).append("index", this.index).toString();
    }
}
