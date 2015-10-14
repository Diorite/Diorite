package org.diorite.utils.function.eval;

import java.util.function.BooleanSupplier;

/**
 * Simple {@link BooleanSupplier}-like class, but use "eval" as method name for simpler reflection usage.
 */
@FunctionalInterface
public interface BooleanEvaluator extends BooleanSupplier
{
    /**
     * Gets a result.
     *
     * @return a result
     */
    boolean eval();

    @Override
    default boolean getAsBoolean()
    {
        return this.eval();
    }
}
