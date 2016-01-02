package org.diorite.utils.validator.string;

import java.util.function.Predicate;

import org.diorite.utils.validator.Validator;

/**
 * Validator interface for custom String check.
 */
public interface StringCustomValidator<T extends Predicate<String>> extends Validator<String>
{
    /**
     * Returns predicate used be this validator.
     *
     * @return predicate used be this validator.
     */
    @SuppressWarnings("unchecked")
    default T getPredicate()
    {
        return (T) this;
    }

    @Override
    default boolean test(final String s)
    {
        return this.validate(s);
    }

    /**
     * Create new custom String validator.
     *
     * @param predicate predicate to check string.
     * @param <T>       type of Predicate.
     *
     * @return new custom string validator.
     */
    static <T extends Predicate<String>> StringCustomValidator<T> create(final T predicate)
    {
        return new StringCustomValidatorImpl<>(predicate);
    }
}
