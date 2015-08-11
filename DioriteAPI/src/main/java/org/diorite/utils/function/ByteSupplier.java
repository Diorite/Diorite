package org.diorite.utils.function;

import java.util.function.Supplier;

/**
 * Represents a supplier of {@code byte}-valued results.  This is the
 * {@code byte}-producing primitive specialization of {@link Supplier}.
 * <p>
 * <p>There is no requirement that a new or distinct result be returned each
 * time the supplier is invoked.
 *
 * @see Supplier
 */
@FunctionalInterface
public interface ByteSupplier
{

    /**
     * Gets a result.
     *
     * @return a result
     */
    byte getAsByte();
}
