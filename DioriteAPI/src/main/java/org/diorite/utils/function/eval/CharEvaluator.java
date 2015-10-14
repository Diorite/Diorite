package org.diorite.utils.function.eval;

import org.diorite.utils.function.supplier.CharSupplier;

/**
 * Simple {@link CharSupplier}-like class, but use "eval" as method name for simpler reflection usage.
 */
@FunctionalInterface
public interface CharEvaluator extends CharSupplier
{
    /**
     * Gets a result.
     *
     * @return a result
     */
    char eval();

    @Override
    default char getAsChar()
    {
        return this.eval();
    }
}
