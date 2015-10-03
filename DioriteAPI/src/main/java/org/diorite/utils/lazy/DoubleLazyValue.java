package org.diorite.utils.lazy;

import java.util.Collection;
import java.util.function.DoubleSupplier;

import org.apache.commons.lang3.Validate;

/**
 * Class to represent lazy init double values that use {@link DoubleSupplier} passed in constructor to initialize value in {@link #init()} method. <br>
 * Class is extending {@link DoubleLazyValueAbstract}
 *
 * @see DoubleLazyValueAbstract
 */
@SuppressWarnings("ClassHasNoToStringMethod")
public class DoubleLazyValue extends DoubleLazyValueAbstract
{
    /**
     * supplier used by {@link #init()} method.
     */
    protected final DoubleSupplier supplier;

    /**
     * Construct new DoubleLazyValue with given supplier for value.
     *
     * @param supplier supplier used to initialize value in {@link #init()} method.
     */
    public DoubleLazyValue(final DoubleSupplier supplier)
    {
        Validate.notNull(supplier, "supplier can't be null!");
        this.supplier = supplier;
    }

    /**
     * Construct new DoubleLazyValue with given supplier for value.
     *
     * @param collection created instance will be added to this list.
     * @param supplier   supplier used to initialize value in {@link #init()} method.
     */
    public DoubleLazyValue(final Collection<? super DoubleLazyValue> collection, final DoubleSupplier supplier)
    {
        this.supplier = supplier;
        collection.add(this);
    }

    @Override
    protected double init()
    {
        return this.supplier.getAsDouble();
    }
}
