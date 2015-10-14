package org.diorite.utils.function.eval;

import org.diorite.utils.function.supplier.ByteSupplier;

/**
 * Simple {@link ByteSupplier}-like class, but use "eval" as method name for simpler reflection usage.
 */
@FunctionalInterface
public interface ByteEvaluator extends ByteSupplier
{
    /**
     * Gets a result.
     *
     * @return a result
     */
    byte eval();

    @Override
    default byte getAsByte()
    {
        return this.eval();
    }
}
