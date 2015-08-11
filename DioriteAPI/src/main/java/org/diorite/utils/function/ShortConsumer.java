package org.diorite.utils.function;

import java.util.Objects;

/**
 * Represents an operation that accepts a single {@code short}-valued argument and
 * returns no result.  This is the primitive type specialization of
 * {@link Consumer} for {@code short}.  Unlike most other functional interfaces,
 * {@code ShortConsumer} is expected to operate via side-effects.
 *
 * @see Consumer
 */
@FunctionalInterface
public interface ShortConsumer
{

    /**
     * Performs this operation on the given argument.
     *
     * @param value the input argument
     */
    void accept(short value);

    /**
     * Returns a composed {@code ShortConsumer} that performs, in sequence, this
     * operation followed by the {@code after} operation. If performing either
     * operation throws an exception, it is relayed to the caller of the
     * composed operation.  If performing this operation throws an exception,
     * the {@code after} operation will not be performed.
     *
     * @param after the operation to perform after this operation
     *
     * @return a composed {@code ShortConsumer} that performs in sequence this
     * operation followed by the {@code after} operation
     *
     * @throws NullPointerException if {@code after} is null
     */
    default ShortConsumer andThen(final ShortConsumer after)
    {
        Objects.requireNonNull(after);
        return (short t) -> {
            this.accept(t);
            after.accept(t);
        };
    }
}