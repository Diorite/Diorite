package org.diorite.utils.collections.sets;

import java.io.Serializable;
import java.util.AbstractSet;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Concurrent set implemented as wrapper for ConcurrentHashMap.
 *
 * @param <E> elements type.
 *
 * @see ConcurrentHashMap
 */
public class ConcurrentSet<E> extends AbstractSet<E> implements Serializable
{
    private static final long serialVersionUID = 0;

    private final ConcurrentMap<E, Boolean> map;

    /**
     * Construct new ConcurrentSet.
     */
    public ConcurrentSet()
    {
        this.map = new ConcurrentHashMap<>(10);
    }

    /**
     * Construct new ConcurrentSet.
     *
     * @param size initial capacity of set.
     */
    public ConcurrentSet(final int size)
    {
        this.map = new ConcurrentHashMap<>(size);
    }

    /**
     * Construct new ConcurrentSet.
     *
     * @param size       initial capacity of set.
     * @param loadFactor load factor of set.
     */
    public ConcurrentSet(final int size, final float loadFactor)
    {
        this.map = new ConcurrentHashMap<>(size, loadFactor);
    }

    /**
     * Construct new ConcurrentSet.
     *
     * @param size             initial capacity of set.
     * @param loadFactor       load factor of set.
     * @param concurrencyLevel concurrency level of set.
     */
    public ConcurrentSet(final int size, final float loadFactor, final int concurrencyLevel)
    {
        this.map = new ConcurrentHashMap<>(size, loadFactor, concurrencyLevel);
    }

    @Override
    public int size()
    {
        return this.map.size();
    }

    @Override
    public boolean contains(final Object o)
    {
        return this.map.containsKey(o);
    }

    @Override
    public boolean add(final E o)
    {
        return this.map.putIfAbsent(o, Boolean.TRUE) == null;
    }

    @Override
    public boolean remove(final Object o)
    {
        return this.map.remove(o) != null;
    }

    @Override
    public void clear()
    {
        this.map.clear();
    }

    @Override
    public Iterator<E> iterator()
    {
        return this.map.keySet().iterator();
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("map", this.map.keySet()).toString();
    }
}

