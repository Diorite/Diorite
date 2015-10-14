package org.diorite.utils.lazy;

import java.util.Collection;
import java.util.function.Supplier;

import org.apache.commons.lang3.Validate;

/**
 * Class to represent lazy init object values that use {@link Supplier} passed in constructor to initialize value in {@link #init()} method. <br>
 * Class is extending {@link LazyValueAbstract}
 *
 * @param <T> type of lazy init object.
 *
 * @see LazyValueAbstract
 */
@SuppressWarnings("ClassHasNoToStringMethod")
public class LazyValue<T> extends LazyValueAbstract<T>
{
    /**
     * supplier used by {@link #init()} method.
     */
    protected final Supplier<T> supplier;

    /**
     * Construct new LazyValue with given supplier for value.
     *
     * @param supplier supplier used to initialize value in {@link #init()} method.
     */
    public LazyValue(final Supplier<T> supplier)
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
    public LazyValue(final Collection<? super LazyValue<T>> collection, final Supplier<T> supplier)
    {
        this.supplier = supplier;
        collection.add(this);
    }

    @Override
    protected T init()
    {
        return this.supplier.get();
    }
}
