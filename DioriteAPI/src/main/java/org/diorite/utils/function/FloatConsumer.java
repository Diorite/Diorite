package org.diorite.utils.function;

import java.util.Objects;

/**
 * Represents an operation that accepts a single {@code float}-valued argument and
 * returns no result.  This is the primitive type specialization of
 * {@link java.util.function.Consumer} for {@code float}.  Unlike most other functional interfaces,
 * {@code FloatConsumer} is expected to operate via side-effects.
 *
 * @see java.util.function.Consumer
 */
@FunctionalInterface
public interface FloatConsumer
{

    /**
     * Performs this operation on the given argument.
     *
     * @param value the input argument
     */
    void accept(float value);

    /**
     * Returns a composed {@code FloatConsumer} that performs, in sequence, this
     * operation followed by the {@code after} operation. If performing either
     * operation throws an exception, it is relayed to the caller of the
     * composed operation.  If performing this operation throws an exception,
     * the {@code after} operation will not be performed.
     *
     * @param after the operation to perform after this operation
     *
     * @return a composed {@code FloatConsumer} that performs in sequence this
     * operation followed by the {@code after} operation
     *
     * @throws NullPointerException if {@code after} is null
     */
    default FloatConsumer andThen(final FloatConsumer after)
    {
        Objects.requireNonNull(after);
        return (float t) -> {
            this.accept(t);
            after.accept(t);
        };
    }
}