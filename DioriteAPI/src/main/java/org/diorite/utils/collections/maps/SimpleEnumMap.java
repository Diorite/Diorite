package org.diorite.utils.collections.maps;

import java.util.Map;

import org.diorite.utils.SimpleEnum;
import org.diorite.utils.collections.hash.SimpleEnumHashingStrategy;

import gnu.trove.map.hash.TCustomHashMap;

/**
 * Map with simple enum objects as keys.
 *
 * @param <K> simple enum key type.
 * @param <V> value type.
 */
public class SimpleEnumMap<K extends SimpleEnum<K>, V> extends TCustomHashMap<K, V>
{
    private static final long serialVersionUID = 0;

    /**
     * Construct new SimpleEnumMap.
     */
    public SimpleEnumMap()
    {
        super(SimpleEnumHashingStrategy.INSTANCE);
    }

    /**
     * Construct new SimpleEnumMap.
     *
     * @param initialCapacity initial capacity of map.
     */
    public SimpleEnumMap(final int initialCapacity)
    {
        super(SimpleEnumHashingStrategy.INSTANCE, initialCapacity);
    }

    /**
     * Construct new SimpleEnumMap.
     *
     * @param initialCapacity initial capacity of map.
     * @param loadFactor      load factor of map.
     */
    public SimpleEnumMap(final int initialCapacity, final float loadFactor)
    {
        super(SimpleEnumHashingStrategy.INSTANCE, initialCapacity, loadFactor);
    }

    /**
     * Construct new SimpleEnumMap.
     *
     * @param map map to copy.
     */
    public SimpleEnumMap(final TCustomHashMap<? extends K, ? extends V> map)
    {
        super(SimpleEnumHashingStrategy.INSTANCE, map);
    }

    /**
     * Construct new SimpleEnumMap.
     *
     * @param map map to copy.
     */
    public SimpleEnumMap(final Map<? extends K, ? extends V> map)
    {
        super(SimpleEnumHashingStrategy.INSTANCE, map);
    }
}
