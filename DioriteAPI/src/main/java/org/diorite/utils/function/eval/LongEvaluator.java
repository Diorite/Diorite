package org.diorite.utils.function.eval;

import java.util.function.LongSupplier;

/**
 * Simple {@link LongSupplier}-like class, but use "eval" as method name for simpler reflection usage.
 */
@FunctionalInterface
public interface LongEvaluator extends LongSupplier
{
    /**
     * Gets a result.
     *
     * @return a result
     */
    long eval();

    @Override
    default long getAsLong()
    {
        return this.eval();
    }
}
