package org.diorite.utils.collections.maps;

import java.util.Map;

import org.diorite.utils.SimpleEnum;

import gnu.trove.map.hash.TCustomHashMap;

public class SimpleEnumMap<K extends SimpleEnum<K>, V> extends TCustomHashMap<K, V>
{
    private static final long serialVersionUID = 0;

    public SimpleEnumMap()
    {
        super(SimpleEnumHashingStrategy.INSTANCE);
    }

    public SimpleEnumMap(final int initialCapacity)
    {
        super(SimpleEnumHashingStrategy.INSTANCE, initialCapacity);
    }

    public SimpleEnumMap(final int initialCapacity, final float loadFactor)
    {
        super(SimpleEnumHashingStrategy.INSTANCE, initialCapacity, loadFactor);
    }

    public SimpleEnumMap(final TCustomHashMap<? extends K, ? extends V> map)
    {
        super(SimpleEnumHashingStrategy.INSTANCE, map);
    }

    public SimpleEnumMap(final Map<? extends K, ? extends V> map)
    {
        super(SimpleEnumHashingStrategy.INSTANCE, map);
    }
}
