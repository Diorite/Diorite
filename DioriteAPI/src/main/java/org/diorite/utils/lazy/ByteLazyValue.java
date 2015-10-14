package org.diorite.utils.lazy;

import java.util.Collection;

import org.apache.commons.lang3.Validate;

import org.diorite.utils.function.supplier.ByteSupplier;

/**
 * Class to represent lazy init byte values that use {@link ByteSupplier} passed in constructor to initialize value in {@link #init()} method. <br>
 * Class is extending {@link ByteLazyValueAbstract}
 *
 * @see ByteLazyValueAbstract
 */
@SuppressWarnings("ClassHasNoToStringMethod")
public class ByteLazyValue extends ByteLazyValueAbstract
{
    /**
     * supplier used by {@link #init()} method.
     */
    protected final ByteSupplier supplier;

    /**
     * Construct new ByteLazyValue with given supplier for value.
     *
     * @param supplier supplier used to initialize value in {@link #init()} method.
     */
    public ByteLazyValue(final ByteSupplier supplier)
    {
        Validate.notNull(supplier, "supplier can't be null!");
        this.supplier = supplier;
    }

    /**
     * Construct new ByteLazyValue with given supplier for value.
     *
     * @param collection created instance will be added to this list.
     * @param supplier   supplier used to initialize value in {@link #init()} method.
     */
    public ByteLazyValue(final Collection<? super ByteLazyValue> collection, final ByteSupplier supplier)
    {
        this.supplier = supplier;
        collection.add(this);
    }

    @Override
    protected byte init()
    {
        return this.supplier.getAsByte();
    }
}
