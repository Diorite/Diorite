package org.diorite.utils.lazy;

import java.util.Collection;

import org.apache.commons.lang3.Validate;

import org.diorite.utils.function.FloatSupplier;

/**
 * Class to represent lazy init float values that use {@link FloatSupplier} passed in constructor to initialize value in {@link #init()} method. <br>
 * Class is extending {@link FloatLazyValueAbstract}
 *
 * @see FloatLazyValueAbstract
 */
@SuppressWarnings("ClassHasNoToStringMethod")
public class FloatLazyValue extends FloatLazyValueAbstract
{
    /**
     * supplier used by {@link #init()} method.
     */
    protected final FloatSupplier supplier;

    /**
     * Construct new FloatLazyValue with given supplier for value.
     *
     * @param supplier supplier used to initialize value in {@link #init()} method.
     */
    public FloatLazyValue(final FloatSupplier supplier)
    {
        Validate.notNull(supplier, "supplier can't be null!");
        this.supplier = supplier;
    }

    /**
     * Construct new FloatLazyValue with given supplier for value.
     *
     * @param collection created instance will be added to this list.
     * @param supplier   supplier used to initialize value in {@link #init()} method.
     */
    public FloatLazyValue(final Collection<? super FloatLazyValue> collection, final FloatSupplier supplier)
    {
        this.supplier = supplier;
        collection.add(this);
    }

    @Override
    protected float init()
    {
        return this.supplier.getAsFloat();
    }
}
