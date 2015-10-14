package org.diorite.utils.lazy;

import java.util.Collection;

import org.apache.commons.lang3.Validate;

import org.diorite.utils.function.supplier.CharSupplier;

/**
 * Class to represent lazy init char values that use {@link CharSupplier} passed in constructor to initialize value in {@link #init()} method. <br>
 * Class is extending {@link CharLazyValueAbstract}
 *
 * @see CharLazyValueAbstract
 */
@SuppressWarnings("ClassHasNoToStringMethod")
public class CharLazyValue extends CharLazyValueAbstract
{
    /**
     * supplier used by {@link #init()} method.
     */
    protected final CharSupplier supplier;

    /**
     * Construct new CharLazyValue with given supplier for value.
     *
     * @param supplier supplier used to initialize value in {@link #init()} method.
     */
    public CharLazyValue(final CharSupplier supplier)
    {
        Validate.notNull(supplier, "supplier can't be null!");
        this.supplier = supplier;
    }

    /**
     * Construct new CharLazyValue with given supplier for value.
     *
     * @param collection created instance will be added to this list.
     * @param supplier   supplier used to initialize value in {@link #init()} method.
     */
    public CharLazyValue(final Collection<? super CharLazyValue> collection, final CharSupplier supplier)
    {
        this.supplier = supplier;
        collection.add(this);
    }

    @Override
    protected char init()
    {
        return this.supplier.getAsChar();
    }
}
