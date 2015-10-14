package org.diorite.utils.function.eval;

import org.diorite.utils.function.supplier.FloatSupplier;

/**
 * Simple {@link FloatSupplier}-like class, but use "eval" as method name for simpler reflection usage.
 */
@FunctionalInterface
public interface FloatEvaluator extends FloatSupplier
{
    /**
     * Gets a result.
     *
     * @return a result
     */
    float eval();

    @Override
    default float getAsFloat()
    {
        return this.eval();
    }
}
