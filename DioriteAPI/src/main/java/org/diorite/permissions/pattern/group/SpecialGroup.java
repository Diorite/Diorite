package org.diorite.permissions.pattern.group;

/**
 * Base abstract class for special permission pattern groups, like permissions that can use number ranges. {@link RangeGroup}
 *
 * @param <R> type of input data when checking string.
 */
public interface SpecialGroup<R>
{
    /**
     * This method will parse given string, check if it is valid, and check if given data is in range, so hasPermission should return true. <br>
     * Given string ALWAYS must start with pattern data to parse, but may contains additional chars at the end,
     * like when parsing "foo.15.bar" with data 10 for pattern "foo.{$++}.bar", given string will be "15.bar", and it will return that
     * string is valid, (as it contains valid number where excepted), and it is matching LevelGroup 10 {@literal <=} 15,
     * and end index of 2 as pattern data is only one 2 digit number, 2 chars.
     *
     * @param string given string to parse.
     * @param data   data to check.
     *
     * @return {@link GroupResult} results contains if string was valid, if data was matching, and end index of pattern data to get unparsed part of given string.
     */
    GroupResult parse(final String string, final R data);

    /**
     * This method will convert given string to valid group data, like in pattern "foo.{$++}.bar" for "foo.56.bar" and R type of {@link Long}
     * this method will parse "56" string to Long value. <br>
     * Or return null if it is not possible.
     *
     * @param data data to parse in string from permission.
     *
     * @return parsed data or null.
     */
    R parseData(final String data);

    /**
     * Returns pattern value for this group, like "{$++}". <br>
     * This will return always this same value for this same type of special group.
     *
     * @return pattern value for this group.
     */
    String getGroupPattern();

    /**
     * This method will parse given string, check if it is valid, and check if given data is in range, so hasPermission should return true. <br>
     * Given string ALWAYS must start with pattern data to parse, but may contains additional chars at the end,
     * like when parsing "foo.15.bar" with data 10 for pattern "foo.{$++}.bar", given string will be "15.bar", and it will return that
     * string is valid, (as it contains valid number where excepted), and it is matching LevelGroup 10 {@literal <=} 15,
     * and end index of 2 as pattern data is only one 2 digit number, 2 chars.
     *
     * @param string given string to parse.
     * @param data   data to check.
     *
     * @return {@link GroupResult} results contains if string was valid, if data was matching, and end index of pattern data to get unparsed part of given string.
     */
    default GroupResult parse(final String string, final String data)
    {
        final R parsedData = this.parseData(data);
        if (parsedData == null)
        {
            return new GroupResult(false, false, 0);
        }
        return this.parse(string, parsedData);
    }

    /**
     * Check if given string can be value of special group, like "5" for "{$++}"
     *
     * @param string string to check.
     *
     * @return true if given string can be value of this group.
     */
    boolean isValid(final String string);
}
