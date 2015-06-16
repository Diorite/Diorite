package org.diorite.impl;

/**
 * Stuff that is updated in tick loop.
 */
@FunctionalInterface
public interface Tickable
{
    void doTick(final int tps);
}
