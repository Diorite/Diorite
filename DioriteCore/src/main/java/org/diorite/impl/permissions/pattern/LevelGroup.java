package org.diorite.impl.permissions.pattern;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class LevelGroup extends SpecialGroup
{
    protected final boolean ascending; // {$++} if true, for pattern "foo.{$++}" permission "foo.3" will return true for player with "foo.5", as 3 < 5.

    public LevelGroup(final int index, final boolean ascending)
    {
        super(index);
        this.ascending = ascending;
    }

    public boolean isAscending()
    {
        return this.ascending;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this).append("ascending", this.ascending).toString();
    }
}
