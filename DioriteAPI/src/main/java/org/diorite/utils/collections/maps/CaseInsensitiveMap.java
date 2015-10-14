package org.diorite.utils.collections.maps;

import java.util.Map;

import org.diorite.utils.collections.hash.CaseInsensitiveHashingStrategy;

import gnu.trove.map.hash.TCustomHashMap;

public class CaseInsensitiveMap<V> extends TCustomHashMap<String, V>
{
    private static final long serialVersionUID = 0;

    public CaseInsensitiveMap()
    {
        super(CaseInsensitiveHashingStrategy.INSTANCE);
    }

    public CaseInsensitiveMap(final int initialCapacity)
    {
        super(CaseInsensitiveHashingStrategy.INSTANCE, initialCapacity);
    }

    public CaseInsensitiveMap(final int initialCapacity, final float loadFactor)
    {
        super(CaseInsensitiveHashingStrategy.INSTANCE, initialCapacity, loadFactor);
    }

    public CaseInsensitiveMap(final TCustomHashMap<? extends String, ? extends V> map)
    {
        super(CaseInsensitiveHashingStrategy.INSTANCE, map);
    }

    public CaseInsensitiveMap(final Map<? extends String, ? extends V> map)
    {
        super(CaseInsensitiveHashingStrategy.INSTANCE, map);
    }
}
