package org.diorite.impl;

/**
 * Stuff that is updated in tick loop.
 */
@FunctionalInterface
public interface Tickable
{
    void doTick();

    default int updateEveryNTicks()
    {
        return 1;
    }
}
