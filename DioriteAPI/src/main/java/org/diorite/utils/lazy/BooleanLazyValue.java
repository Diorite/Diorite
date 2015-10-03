package org.diorite.utils.lazy;

import java.util.Collection;
import java.util.function.BooleanSupplier;

import org.apache.commons.lang3.Validate;

/**
 * Class to represent lazy init boolean values that use {@link BooleanSupplier} passed in constructor to initialize value in {@link #init()} method. <br>
 * Class is extending {@link BooleanLazyValueAbstract}
 *
 * @see BooleanLazyValueAbstract
 */
@SuppressWarnings("ClassHasNoToStringMethod")
public class BooleanLazyValue extends BooleanLazyValueAbstract
{
    /**
     * supplier used by {@link #init()} method.
     */
    protected final BooleanSupplier supplier;

    /**
     * Construct new BooleanLazyValue with given supplier for value.
     *
     * @param supplier supplier used to initialize value in {@link #init()} method.
     */
    public BooleanLazyValue(final BooleanSupplier supplier)
    {
        Validate.notNull(supplier, "supplier can't be null!");
        this.supplier = supplier;
    }

    /**
     * Construct new BooleanLazyValue with given supplier for value.
     *
     * @param collection created instance will be added to this list.
     * @param supplier   supplier used to initialize value in {@link #init()} method.
     */
    public BooleanLazyValue(final Collection<? super BooleanLazyValue> collection, final BooleanSupplier supplier)
    {
        this.supplier = supplier;
        collection.add(this);
    }

    @Override
    protected boolean init()
    {
        return this.supplier.getAsBoolean();
    }
}
