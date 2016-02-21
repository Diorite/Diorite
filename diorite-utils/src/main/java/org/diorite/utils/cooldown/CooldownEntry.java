/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2016. Diorite (by Bartłomiej Mazur (aka GotoFinal))
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package org.diorite.utils.cooldown;

/**
 * Represent cooldown entry, entry contains used key, cooldown time, and start time (cooldown is counted relative to start time).
 *
 * @param <K> type of key.
 */
public interface CooldownEntry<K>
{
    /**
     * Returns key of this cooldown entry.
     *
     * @return key of this cooldown entry.
     */
    K getKey();

    /**
     * Returns start time of this cooldown. (milliseconds)
     *
     * @return start time of this cooldown. (milliseconds)
     */
    long getStartTime();

    /**
     * Returns time of this cooldown. (milliseconds)
     *
     * @return time of this cooldown. (milliseconds)
     */
    long getCooldownTime();

    /**
     * Returns delta time of cooldown - rest of cooldown time if it already started. <br>
     * If cooldown isn't started yet, returned value may be higher than cooldown time. <br>
     * This method will remove entry from manager if it is expired.
     *
     * @return delta time of cooldown - rest of cooldown time if it already started.
     */
    default long delta()
    {
        return this.delta(System.currentTimeMillis());
    }

    /**
     * Returns delta time of cooldown - rest of cooldown time if it already started. <br>
     * If cooldown isn't started yet, returned value may be higher than cooldown time. <br>
     * This method will remove entry from manager if it is expired.
     *
     * @param currentTime time in milliseconds that will be used as current one.
     *
     * @return delta time of cooldown - rest of cooldown time if it already started.
     */
    default long delta(final long currentTime)
    {
        final long sum = this.getStartTime() + this.getCooldownTime();
        return sum - currentTime;
    }

    /**
     * Returns true if this cooldown already started. <br>
     * This method will remove entry from manager if it is expired.
     *
     * @return true if this cooldown already started.
     */
    default boolean hasAlreadyStarted()
    {
        return this.hasAlreadyStarted(System.currentTimeMillis());
    }

    /**
     * Returns true if this cooldown already started. <br>
     * This method will remove entry from manager if it is expired.
     *
     * @param currentTime time in milliseconds that will be used as current one.
     *
     * @return true if this cooldown already started.
     */
    default boolean hasAlreadyStarted(final long currentTime)
    {
        return this.delta(currentTime) <= this.getCooldownTime();
    }

    /**
     * Returns true if cooldown time already expired. <br>
     * This method will remove entry from manager if it is expired.
     *
     * @return true if cooldown time already expired.
     */
    default boolean hasExpired()
    {
        return this.hasExpired(System.currentTimeMillis());
    }

    /**
     * Returns true if cooldown time already expired. <br>
     * This method will remove entry from manager if it is expired.
     *
     * @param currentTime time in milliseconds that will be used as current one.
     *
     * @return true if cooldown time already expired.
     */
    default boolean hasExpired(final long currentTime)
    {
        return this.delta(currentTime) <= 0;
    }

    /**
     * Returns delta time of cooldown - rest of cooldown time if it already started. <br>
     * If cooldown isn't started yet, returned value may be higher than cooldown time.
     *
     * @return delta time of cooldown - rest of cooldown time if it already started.
     */
    default long deltaLazy()
    {
        return this.deltaLazy(System.currentTimeMillis());
    }

    /**
     * Returns delta time of cooldown - rest of cooldown time if it already started. <br>
     * If cooldown isn't started yet, returned value may be higher than cooldown time.
     *
     * @param currentTime time in milliseconds that will be used as current one.
     *
     * @return delta time of cooldown - rest of cooldown time if it already started.
     */
    default long deltaLazy(final long currentTime)
    {
        final long sum = this.getStartTime() + this.getCooldownTime();
        return sum - currentTime;
    }

    /**
     * Returns true if this cooldown already started.
     *
     * @return true if this cooldown already started.
     */
    default boolean hasAlreadyStartedLazy()
    {
        return this.hasAlreadyStartedLazy(System.currentTimeMillis());
    }

    /**
     * Returns true if this cooldown already started.
     *
     * @param currentTime time in milliseconds that will be used as current one.
     *
     * @return true if this cooldown already started.
     */
    default boolean hasAlreadyStartedLazy(final long currentTime)
    {
        return this.deltaLazy(currentTime) <= this.getCooldownTime();
    }

    /**
     * Returns true if cooldown time already expired.
     *
     * @return true if cooldown time already expired.
     */
    default boolean hasExpiredLazy()
    {
        return this.hasExpiredLazy(System.currentTimeMillis());
    }

    /**
     * Returns true if cooldown time already expired.
     *
     * @param currentTime time in milliseconds that will be used as current one.
     *
     * @return true if cooldown time already expired.
     */
    default boolean hasExpiredLazy(final long currentTime)
    {
        return this.deltaLazy(currentTime) <= 0;
    }
}
