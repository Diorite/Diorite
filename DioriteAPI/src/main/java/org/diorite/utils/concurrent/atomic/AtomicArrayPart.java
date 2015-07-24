package org.diorite.utils.concurrent.atomic;

import java.util.function.BinaryOperator;
import java.util.function.UnaryOperator;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class AtomicArrayPart<E> implements AtomicArray<E>
{
    protected final AtomicArray<E> base;
    protected final int            offset;
    protected final int            length;

    protected AtomicArrayPart(final AtomicArray<E> base, final int offset, final int length)
    {
        Validate.isTrue(offset >= 0, "offset can't be negative!");
        Validate.isTrue(length > 0, "length must be positive!");
        Validate.isTrue(offset < base.length(), "offset can't be bigger than array. (offset: " + offset + ", base: " + base.length() + ")");
        Validate.isTrue((length + offset) <= base.length(), "base array is too small for this offset and length. (offset: " + offset + ", length: " + length + ", base: " + base.length() + ")");
        this.base = base;
        this.offset = offset;
        this.length = length;
    }

    protected AtomicArrayPart(final AtomicArray<E> base, final int offset)
    {
        this(base, offset, base.length() - offset);
    }

    protected AtomicArray<E> getBase()
    {
        return this.base;
    }

    protected int addOffset(final int i)
    {
        if (i >= this.length)
        {
            throw new IndexOutOfBoundsException("Index out of bounds, index: " + i + ", length: " + this.length);
        }
        return this.offset + i;
    }

    @Override
    public int length()
    {
        return this.length;
    }

    @Override
    public int offset()
    {
        return this.offset;
    }

    @Override
    public E get(final int i)
    {
        return this.base.get(this.addOffset(i));
    }

    @Override
    public void set(final int i, final E newValue)
    {
        this.base.set(this.addOffset(i), newValue);
    }

    @Override
    public void lazySet(final int i, final E newValue)
    {
        this.base.lazySet(this.addOffset(i), newValue);
    }

    @Override
    public E getAndSet(final int i, final E newValue)
    {
        return this.base.getAndSet(this.addOffset(i), newValue);
    }

    @Override
    public boolean compareAndSet(final int i, final E expect, final E update)
    {
        return this.base.compareAndSet(this.addOffset(i), expect, update);
    }

    @Override
    public boolean weakCompareAndSet(final int i, final E expect, final E update)
    {
        return this.base.weakCompareAndSet(this.addOffset(i), expect, update);
    }

    @Override
    public E getAndUpdate(final int i, final UnaryOperator<E> updateFunction)
    {
        return this.base.getAndUpdate(this.addOffset(i), updateFunction);
    }

    @Override
    public E updateAndGet(final int i, final UnaryOperator<E> updateFunction)
    {
        return this.base.updateAndGet(this.addOffset(i), updateFunction);
    }

    @Override
    public E getAndAccumulate(final int i, final E x, final BinaryOperator<E> accumulatorFunction)
    {
        return this.base.getAndAccumulate(this.addOffset(i), x, accumulatorFunction);
    }

    @Override
    public E accumulateAndGet(final int i, final E x, final BinaryOperator<E> accumulatorFunction)
    {
        return this.base.accumulateAndGet(this.addOffset(i), x, accumulatorFunction);
    }

    @Override
    public AtomicArray<E> getSubArray(final int offset, final int length)
    {
        if ((offset == 0) && (length == this.length()))
        {
            return this;
        }
        Validate.isTrue(offset >= 0, "offset can't be negative!");
        // base array is used, to avoid creating nested wrappers.
        return new AtomicArrayPart<>(this.base, this.offset + offset, length);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T[] toArray(final T[] a)
    {
        final int l = Math.min(a.length, this.length);
        for (int i = 0; i < l; i++)
        {
            a[i] = (T) this.get(i);
        }
        return a;
    }

    @Override
    public Object[] toArray()
    {
        final Object[] array = new Object[this.length];
        for (int i = 0; i < array.length; i++)
        {
            array[i] = this.get(i);
        }
        return array;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("base", this.base).append("offset", this.offset).append("length", this.length).toString();
    }
}
