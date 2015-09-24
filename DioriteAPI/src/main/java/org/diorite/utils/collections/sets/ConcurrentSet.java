package org.diorite.utils.collections.sets;

import java.io.Serializable;
import java.util.AbstractSet;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class ConcurrentSet<E> extends AbstractSet<E> implements Serializable
{
    private static final long serialVersionUID = 0;

    private final ConcurrentMap<E, Boolean> map;

    public ConcurrentSet()
    {
        this.map = new ConcurrentHashMap<>(10);
    }

    public ConcurrentSet(final int size)
    {
        this.map = new ConcurrentHashMap<>(size);
    }

    public ConcurrentSet(final int size, final float loadFactor)
    {
        this.map = new ConcurrentHashMap<>(size, loadFactor);
    }

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

