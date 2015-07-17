package org.diorite.utils.collections.maps;

import org.diorite.utils.SimpleEnum;

import gnu.trove.strategy.HashingStrategy;

@SuppressWarnings("rawtypes")
class SimpleEnumHashingStrategy implements HashingStrategy<SimpleEnum>
{
    private static final long serialVersionUID = 0L;

    static final SimpleEnumHashingStrategy INSTANCE = new SimpleEnumHashingStrategy();

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
