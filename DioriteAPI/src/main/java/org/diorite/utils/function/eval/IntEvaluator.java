package org.diorite.utils.function.eval;

import java.util.function.IntSupplier;

/**
 * Simple {@link IntSupplier}-like class, but use "eval" as method name for simpler reflection usage.
 */
@FunctionalInterface
public interface IntEvaluator extends IntSupplier
{
    /**
     * Gets a result.
     *
     * @return a result
     */
    int eval();

    @Override
    default int getAsInt()
    {
        return this.eval();
    }
}
