package org.diorite.utils.lazy;

import java.util.Collection;
import java.util.function.LongSupplier;

import org.apache.commons.lang3.Validate;

/**
 * Class to represent lazy init long values that use {@link LongSupplier} passed in constructor to initialize value in {@link #init()} method. <br>
 * Class is extending {@link LongLazyValueAbstract}
 *
 * @see LongLazyValueAbstract
 */
@SuppressWarnings("ClassHasNoToStringMethod")
public class LongLazyValue extends LongLazyValueAbstract
{
    /**
     * supplier used by {@link #init()} method.
     */
    protected final LongSupplier supplier;

    /**
     * Construct new LongLazyValue with given supplier for value.
     *
     * @param supplier supplier used to initialize value in {@link #init()} method.
     */
    public LongLazyValue(final LongSupplier supplier)
    {
        Validate.notNull(supplier, "supplier can't be null!");
        this.supplier = supplier;
    }

    /**
     * Construct new LongLazyValue with given supplier for value.
     *
     * @param collection created instance will be added to this list.
     * @param supplier   supplier used to initialize value in {@link #init()} method.
     */
    public LongLazyValue(final Collection<? super LongLazyValue> collection, final LongSupplier supplier)
    {
        this.supplier = supplier;
        collection.add(this);
    }

    @Override
    protected long init()
    {
        return this.supplier.getAsLong();
    }
}
