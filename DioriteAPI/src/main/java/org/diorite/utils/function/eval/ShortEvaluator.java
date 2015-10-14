package org.diorite.utils.function.eval;

import org.diorite.utils.function.supplier.ShortSupplier;

/**
 * Simple {@link ShortSupplier}-like class, but use "eval" as method name for simpler reflection usage.
 */
@FunctionalInterface
public interface ShortEvaluator extends ShortSupplier
{
    /**
     * Gets a result.
     *
     * @return a result
     */
    short eval();

    @Override
    default short getAsShort()
    {
        return this.eval();
    }
}
