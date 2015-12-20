package org.diorite.utils.validator.string;

import java.util.regex.Pattern;

/**
 * Validator interface for String RegEx check.
 */
public interface StringRegExValidator extends StringCustomValidator<StringRegExValidator>
{
    /**
     * Returns validator reg-ex pattern.
     *
     * @return validator reg-ex pattern.
     */
    Pattern getPattern();

    /**
     * Create new String regex validator with given pattern.
     *
     * @param pattern RegEx pattern to be used.
     *
     * @return new string regex validator.
     */
    static StringRegExValidator create(final Pattern pattern)
    {
        return new StringRegExValidatorImpl(pattern);
    }
}
