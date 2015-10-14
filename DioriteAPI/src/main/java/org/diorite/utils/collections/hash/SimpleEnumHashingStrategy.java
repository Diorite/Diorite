package org.diorite.utils.collections.hash;

import org.diorite.utils.SimpleEnum;

import gnu.trove.strategy.HashingStrategy;

/**
 * Hashing strategy for simple enum objects.
 */
@SuppressWarnings("rawtypes")
public class SimpleEnumHashingStrategy implements HashingStrategy<SimpleEnum>
{
    private static final long serialVersionUID = 0;

    /**
     * Protected constructor, use {@link #INSTANCE} to get instance.
     */
    protected SimpleEnumHashingStrategy()
    {
    }

    /**
     * Static instance of this hashing strategy.
     */
    public static final SimpleEnumHashingStrategy INSTANCE = new SimpleEnumHashingStrategy();

    @Override
    public int computeHashCode(final SimpleEnum s)
    {
        return s.ordinal();
    }

    @Override
    public boolean equals(final SimpleEnum s1, final SimpleEnum s2)
    {
        //noinspection ObjectEquality
        return (s1 == s2) || (((s2 != null)) && (s1.ordinal() == s2.ordinal()));
    }
}
