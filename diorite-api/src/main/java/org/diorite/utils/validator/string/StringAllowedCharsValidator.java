package org.diorite.utils.validator.string;

/**
 * Validator interface for String allowed chars check.
 */
public interface StringAllowedCharsValidator extends StringCustomValidator<StringAllowedCharsValidator>
{
    /**
     * Returns array of allowed chars.
     *
     * @return array of allowed chars.
     */
    char[] getChars();

    /**
     * Create new String allowed chars validator with given array of allowed chars.
     *
     * @param chars array of allowed chars.
     *
     * @return new string allowed chars validator.
     */
    static StringAllowedCharsValidator create(final char... chars)
    {
        return new StringAllowedCharsValidatorImpl(chars);
    }
}
