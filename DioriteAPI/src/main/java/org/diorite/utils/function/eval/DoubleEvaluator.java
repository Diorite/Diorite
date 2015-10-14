package org.diorite.utils.function.eval;

import java.util.function.DoubleSupplier;

/**
 * Simple {@link DoubleSupplier}-like class, but use "eval" as method name for simpler reflection usage.
 */
@FunctionalInterface
public interface DoubleEvaluator extends DoubleSupplier
{
    /**
     * Gets a result.
     *
     * @return a result
     */
    double eval();

    @Override
    default double getAsDouble()
    {
        return this.eval();
    }
}
