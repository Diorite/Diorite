package org.diorite.utils.function.eval;

import java.util.function.Supplier;

/**
 * Simple {@link Supplier}-like class, but use "eval" as method name for simpler reflection usage.
 *
 * @param <T> type of evaluator.
 */
@FunctionalInterface
public interface Evaluator<T> extends Supplier<T>
{
    /**
     * Gets a result.
     *
     * @return a result
     */
    T eval();

    @Override
    default T get()
    {
        return this.eval();
    }
}
