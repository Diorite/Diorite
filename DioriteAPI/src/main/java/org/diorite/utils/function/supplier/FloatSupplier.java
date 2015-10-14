package org.diorite.utils.function.supplier;

import java.util.function.Supplier;

/**
 * Represents a supplier of {@code float}-valued results.  This is the
 * {@code float}-producing primitive specialization of {@link Supplier}.
 * <br>
 * <p>There is no requirement that a new or distinct result be returned each
 * time the supplier is invoked.
 *
 * @see Supplier
 */
@FunctionalInterface
public interface FloatSupplier
{

    /**
     * Gets a result.
     *
     * @return a result
     */
    float getAsFloat();
}
