package org.diorite.utils.collections;


import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.stream.Collectors;

import com.google.common.collect.Iterators;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@SuppressWarnings("ObjectEquality")
public final class WeakCollection<T> implements Collection<T>
{
    static final Object NO_VALUE = new Object();
    private final Collection<WeakReference<T>> collection;
    private final boolean                      identity;

    public WeakCollection(final int size)
    {
        this(size, false);
    }

    public WeakCollection(final int size, final boolean identity)
    {
        this.collection = new ArrayList<>(size);
        this.identity = identity;
    }

    private WeakCollection(final Collection<WeakReference<T>> collection)
    {
        this(collection, false);
    }

    private WeakCollection(final Collection<WeakReference<T>> collection, final boolean identity)
    {
        this.collection = collection;
        this.identity = identity;
    }

    @Override
    public int size()
    {
        return Iterators.size(this.iterator());
    }

    @Override
    public boolean isEmpty()
    {
        return ! this.iterator().hasNext();
    }

    @Override
    public boolean contains(final Object object)
    {
        if (object == null)
        {
            return false;
        }
        else
        {
            final Iterator<T> it = this.iterator();

            Object compare;
            do
            {
                if (! it.hasNext())
                {
                    return false;
                }

                compare = it.next();
            } while (! (this.identity ? (object == compare) : object.equals(compare)));

            return true;
        }
    }

    @Override
    public Iterator<T> iterator()
    {
        return new Iterator<T>()
        {
            Iterator<WeakReference<T>> it;
            Object value;

            {
                this.it = WeakCollection.this.collection.iterator();
                this.value = WeakCollection.NO_VALUE;
            }

            @Override
            public boolean hasNext()
            {
                Object value = this.value;
                if ((value != null) && (! Objects.equals(value, WeakCollection.NO_VALUE)))
                {
                    return true;
                }
                else
                {
                    final Iterator<WeakReference<T>> it = this.it;

                    while (it.hasNext())
                    {
                        final WeakReference<T> ref = it.next();
                        value = ref.get();
                        if (value != null)
                        {
                            this.value = value;
                            return true;
                        }

                        it.remove();
                    }

                    return false;
                }
            }

            @Override
            @SuppressWarnings("unchecked")
            public T next() throws NoSuchElementException
            {
                if (! this.hasNext())
                {
                    throw new NoSuchElementException("No more elements");
                }
                else
                {
                    final Object value = this.value;
                    this.value = WeakCollection.NO_VALUE;
                    return (T) value;
                }
            }

            @Override
            public void remove() throws IllegalStateException
            {
                if (! Objects.equals(this.value, WeakCollection.NO_VALUE))
                {
                    throw new IllegalStateException("No last element");
                }
                else
                {
                    this.value = null;
                    this.it.remove();
                }
            }
        };
    }

    @Override
    public Object[] toArray()
    {
        return this.toArray(new Object[this.size()]);
    }

    @SuppressWarnings("SuspiciousToArrayCall")
    @Override
    public <U> U[] toArray(final U[] array)
    {
        return this.toCollection().toArray(array);
    }

    @Override
    public boolean add(final T value)
    {
        Validate.notNull(value, "Cannot add null value");
        return this.collection.add(new WeakReference<>(value));
    }

    @Override
    public boolean remove(final Object object)
    {
        if (object == null)
        {
            return false;
        }
        else
        {
            final Iterator<T> it = this.iterator();

            do
            {
                if (! it.hasNext())
                {
                    return false;
                }
            } while (! (this.identity ? (object == it.next()) : object.equals(it.next())));

            it.remove();
            return true;
        }
    }

    @Override
    public boolean containsAll(final Collection<?> collection)
    {
        return this.toCollection().containsAll(collection);
    }

    @Override
    public boolean addAll(final Collection<? extends T> collection)
    {
        final Collection<WeakReference<T>> values = this.collection;
        boolean ret = false;

        T value;
        for (final Iterator<? extends T> it = collection.iterator(); it.hasNext(); ret |= values.add(new WeakReference<>(value)))
        {
            value = it.next();
            Validate.notNull(value, "Cannot add null value");
        }

        return ret;
    }

    @Override
    public boolean removeAll(final Collection<?> collection)
    {
        final Iterator<T> it = this.iterator();
        boolean ret = false;

        while (it.hasNext())
        {
            if (collection.contains(it.next()))
            {
                ret = true;
                it.remove();
            }
        }

        return ret;
    }

    @Override
    public boolean retainAll(final Collection<?> collection)
    {
        final Iterator<T> it = this.iterator();
        boolean ret = false;

        while (it.hasNext())
        {
            if (! collection.contains(it.next()))
            {
                ret = true;
                it.remove();
            }
        }

        return ret;
    }

    @Override
    public void clear()
    {
        this.collection.clear();
    }

    private Collection<T> toCollection()
    {
        final Collection<T> collection = new ArrayList<>(this.size());
        collection.addAll(this.stream().collect(Collectors.toList()));
        return collection;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("collection", this.collection).toString();
    }

    public static <T> WeakCollection<T> usingHashSet(final int size)
    {
        return new WeakCollection<>(new HashSet<>(size));
    }

    public static <T> WeakCollection<T> usingHashSet(final int size, final boolean identity)
    {
        return new WeakCollection<>(new HashSet<>(size), identity);
    }

    public static <T> WeakCollection<T> using(final Collection<WeakReference<T>> collection)
    {
        return new WeakCollection<>(collection);
    }

    public static <T> WeakCollection<T> using(final Collection<WeakReference<T>> collection, final boolean identity)
    {
        return new WeakCollection<>(collection, identity);
    }
}
