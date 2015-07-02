package org.diorite.utils.others;

public interface Dirtable
{
    boolean isDirty();

    boolean setDirty(final boolean dirty);

    default boolean setDirty()
    {
        return this.setDirty(true);
    }

    default boolean setClean()
    {
        return this.setDirty(false);
    }
}
