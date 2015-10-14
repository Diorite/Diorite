package org.diorite.utils.collections.sets;

import java.util.Collection;

import org.diorite.utils.collections.hash.CaseInsensitiveHashingStrategy;

import gnu.trove.set.hash.TCustomHashSet;

/**
 * Case insensitive Hash set for strings.
 */
public class CaseInsensitiveHashSet extends TCustomHashSet<String>
{
    private static final long serialVersionUID = 0;

    /**
     * Construct new CaseInsensitiveHashSet.
     */
    public CaseInsensitiveHashSet()
    {
        super(CaseInsensitiveHashingStrategy.INSTANCE);
    }

    /**
     * Construct new CaseInsensitiveHashSet.
     *
     * @param initialCapacity initial capacity of set.
     * @param loadFactor      load factor of set.
     */
    public CaseInsensitiveHashSet(final int initialCapacity, final float loadFactor)
    {
        super(CaseInsensitiveHashingStrategy.INSTANCE, initialCapacity, loadFactor);
    }

    /**
     * Construct new CaseInsensitiveHashSet.
     *
     * @param initialCapacity initial capacity of set.
     */
    public CaseInsensitiveHashSet(final int initialCapacity)
    {
        super(CaseInsensitiveHashingStrategy.INSTANCE, initialCapacity);
    }

    /**
     * Construct new CaseInsensitiveHashSet.
     *
     * @param collection collection to copy as set.
     */
    public CaseInsensitiveHashSet(final Collection<? extends String> collection)
    {
        super(CaseInsensitiveHashingStrategy.INSTANCE, collection);
    }
}
