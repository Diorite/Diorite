package org.diorite.utils.validator;

import java.util.function.Predicate;

/**
 * Basic validator interface, validators are created by builders and they may throw exception or return true/false.
 *
 * @param <T> type of validator.
 */
@FunctionalInterface
public interface Validator<T> extends Predicate<T>
{
    /**
     * Validate given object using this validator, method may throw exception on retun false if validation will fail.
     * <br>
     * There is also {@link #test(Object)} method that never throws exception, but should be used with caution as
     * it may affect performace.
     *
     * @param object object to validate.
     *
     * @return true if object is valid for this validator.
     *
     * @throws RuntimeException if object isn't valid and validator is set to throw error on invalid objects.
     */
    boolean validate(T object) throws RuntimeException;

    @Override
    default boolean test(final T t)
    {
        try
        {
            return this.validate(t);
        } catch (final RuntimeException exception)
        {
            return false;
        }
    }

    /**
     * Creates new Validator instance using sub-validators.
     *
     * @param validators validators to be used.
     * @param <T>        type of validator.
     *
     * @return created validator.
     */
    @SafeVarargs
    static <T> Validator<T> create(final Validator<T>... validators)
    {
        return new SimpleValidator<>(validators);
    }
}
