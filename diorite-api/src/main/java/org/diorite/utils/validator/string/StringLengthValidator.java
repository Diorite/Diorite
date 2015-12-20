package org.diorite.utils.validator.string;

/**
 * Validator interface for String length check.
 */
public interface StringLengthValidator extends StringCustomValidator<StringLengthValidator>
{
    /**
     * Returns min length of string, -1 if no limit.
     *
     * @return min length of string, -1 if no limit.
     */
    int getMinLength();

    /**
     * Returns max length of string, -1 if no limit.
     *
     * @return max length of string, -1 if no limit.
     */
    int getMaxLength();

    /**
     * Create new String length validator with given min/max length, use -1 to ignore.
     *
     * @param min minimum length of string.
     * @param max maximim length of string.
     *
     * @return new string length validator.
     */
    static StringLengthValidator create(final int min, final int max)
    {
        return new StringLengthValidatorImpl(min, max);
    }
}
