package org.diorite.utils.collections.maps;

import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.utils.collections.comparators.ByValueComparator;

/**
 * TreeMap sorted by values instead of keys.
 *
 * @param <K> key type.
 * @param <V> value type.
 *
 * @see TreeMap
 */
public class ByValueSortingTreeMap<K extends Comparable<K>, V extends Comparable<V>> extends TreeMap<K, V>
{
    private static final long serialVersionUID = 0;
    /**
     * Comparator used by this map.
     */
    protected final ByValueComparator<K, V> comparator;

    /**
     * Construct new ByValueSortingTreeMap.
     */
    @SuppressWarnings("unchecked")
    public ByValueSortingTreeMap()
    {
        super(new ByValueComparator<>(true));
        this.comparator = (ByValueComparator<K, V>) this.comparator();
    }

    /**
     * Construct new ByValueSortingTreeMap with given comparator.
     *
     * @param comparator comparator to use.
     */
    public ByValueSortingTreeMap(final ByValueComparator<K, V> comparator)
    {
        super(comparator);
        this.comparator = comparator;
    }

    @Override
    public void putAll(final Map<? extends K, ? extends V> map)
    {
        this.comparator.getMap().putAll(map);
        super.putAll(map);
    }

    @Override
    public V put(final K key, final V value)
    {
        this.comparator.getMap().put(key, value);
        return super.put(key, value);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("comparator", this.comparator).toString();
    }
}
