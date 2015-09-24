package org.diorite.utils.collections.maps;

import gnu.trove.strategy.HashingStrategy;

class CaseInsensitiveHashingStrategy implements HashingStrategy<String>
{
    private static final long serialVersionUID = 0;

    static final CaseInsensitiveHashingStrategy INSTANCE = new CaseInsensitiveHashingStrategy();

    @Override
    public int computeHashCode(final String s)
    {
        return s.toLowerCase().hashCode();
    }

    @Override
    public boolean equals(final String s1, final String s2)
    {
        return (s1.equals(s2)) || (((s2 != null)) && (s1.toLowerCase().equals(s2.toLowerCase())));
    }
}
