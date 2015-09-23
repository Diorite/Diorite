package org.diorite.utils.function;

import java.util.function.Supplier;

/**
 * Represents a supplier of {@code char}-valued results.  This is the
 * {@code char}-producing primitive specialization of {@link Supplier}.
 * <br>
 * <p>There is no requirement that a new or distinct result be returned each
 * time the supplier is invoked.
 *
 * @see Supplier
 */
@FunctionalInterface
public interface CharSupplier
{

    /**
     * Gets a result.
     *
     * @return a result
     */
    char getAsChar();
}
