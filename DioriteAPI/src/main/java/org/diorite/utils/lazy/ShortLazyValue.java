package org.diorite.utils.lazy;

import java.util.Collection;

import org.apache.commons.lang3.Validate;

import org.diorite.utils.function.supplier.ShortSupplier;

/**
 * Class to represent lazy init byte values that use {@link ShortSupplier} passed in constructor to initialize value in {@link #init()} method. <br>
 * Class is extending {@link ShortLazyValueAbstract}
 *
 * @see ShortLazyValueAbstract
 */
@SuppressWarnings("ClassHasNoToStringMethod")
public class ShortLazyValue extends ShortLazyValueAbstract
{
    /**
     * supplier used by {@link #init()} method.
     */
    protected final ShortSupplier supplier;

    /**
     * Construct new ShortLazyValue with given supplier for value.
     *
     * @param supplier supplier used to initialize value in {@link #init()} method.
     */
    public ShortLazyValue(final ShortSupplier supplier)
    {
        Validate.notNull(supplier, "supplier can't be null!");
        this.supplier = supplier;
    }

    /**
     * Construct new ShortLazyValue with given supplier for value.
     *
     * @param collection created instance will be added to this list.
     * @param supplier   supplier used to initialize value in {@link #init()} method.
     */
    public ShortLazyValue(final Collection<? super ShortLazyValue> collection, final ShortSupplier supplier)
    {
        this.supplier = supplier;
        collection.add(this);
    }

    @Override
    protected short init()
    {
        return this.supplier.getAsShort();
    }
}
