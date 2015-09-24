package org.diorite.utils.collections.comparators;

import java.io.Serializable;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class ByValueComparator<U extends Comparable<U>, T extends Comparable<T>> implements Comparator<U>, Serializable
{
    private static final long serialVersionUID = 0;
    protected final boolean   ascend;
    protected final Map<U, T> map;

    public ByValueComparator(final int size)
    {
        this.map = new HashMap<>(size);
        this.ascend = true;
    }

    public ByValueComparator(final Map<U, T> map)
    {
        this.map = map;
        this.ascend = true;
    }

    public ByValueComparator(final Map<U, T> map, final boolean ascend)
    {
        this.map = map;
        this.ascend = ascend;
    }

    public Map<U, T> getMap()
    {
        return this.map;
    }

    @Override
    public int compare(final U o1, final U o2)
    {
        final int result = this.ascend ? this.map.get(o1).compareTo(this.map.get(o2)) : this.map.get(o2).compareTo(this.map.get(o1));
        if (result != 0)
        {
            return result;
        }
        return this.ascend ? o1.compareTo(o2) : o2.compareTo(o1);
    }

    @Override
    public ByValueComparator<U, T> reversed()
    {
        return new ByValueComparator<>(this.map, ! this.ascend);
    }

    @Override
    public String toString()
    {
        return "MapByValueComparator{}";
    }

}
