package org.diorite.utils.validator.string;

/**
 * Validator interface for String disallowed chars check.
 */
public interface StringDisallowedCharsValidator extends StringCustomValidator<StringDisallowedCharsValidator>
{
    /**
     * Returns array of allowed chars.
     *
     * @return array of allowed chars.
     */
    char[] getChars();

    /**
     * Create new String disallowed chars validator with given array of disallowed chars.
     *
     * @param chars array of disallowed chars.
     *
     * @return new string disallowed chars validator.
     */
    static StringDisallowedCharsValidator create(final char... chars)
    {
        return new StringDisallowedCharsValidatorImpl(chars);
    }
}
