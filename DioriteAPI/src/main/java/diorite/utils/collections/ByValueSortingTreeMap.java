package diorite.utils.collections;

import java.util.Map;
import java.util.TreeMap;

public class ByValueSortingTreeMap<K extends Comparable<K>, V extends Comparable<V>> extends TreeMap<K, V>
{
    protected final ByValueComparator<K, V> comparator;

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
        return "ByValueSortingTreeMap{" +
                       "comparator=" + this.comparator +
                       "} " + super.toString();
    }
}
