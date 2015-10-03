package org.diorite.utils.lazy;

import java.util.Collection;
import java.util.function.IntSupplier;

import org.apache.commons.lang3.Validate;

/**
 * Class to represent lazy init int values that use {@link IntSupplier} passed in constructor to initialize value in {@link #init()} method. <br>
 * Class is extending {@link IntLazyValueAbstract}
 *
 * @see IntLazyValueAbstract
 */
@SuppressWarnings("ClassHasNoToStringMethod")
public class IntLazyValue extends IntLazyValueAbstract
{
    /**
     * supplier used by {@link #init()} method.
     */
    protected final IntSupplier supplier;

    /**
     * Construct new IntLazyValue with given supplier for value.
     *
     * @param supplier supplier used to initialize value in {@link #init()} method.
     */
    public IntLazyValue(final IntSupplier supplier)
    {
        Validate.notNull(supplier, "supplier can't be null!");
        this.supplier = supplier;
    }

    /**
     * Construct new IntLazyValue with given supplier for value.
     *
     * @param collection created instance will be added to this list.
     * @param supplier   supplier used to initialize value in {@link #init()} method.
     */
    public IntLazyValue(final Collection<? super IntLazyValue> collection, final IntSupplier supplier)
    {
        this.supplier = supplier;
        collection.add(this);
    }

    @Override
    protected int init()
    {
        return this.supplier.getAsInt();
    }
}
