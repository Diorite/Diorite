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

import java.util.Collection;
import java.util.Set;

/**
 * Represent cooldown manager. <br>
 * Cooldowns may be checked only at access time, you can remove old values by {@link #getExpired()} method.
 */
public interface CooldownManager<K>
{
    /**
     * Adds new object to manager with given cooldown time. <br>
     * If object is already in manager cooldown time will be restarted and replaced with new one.
     *
     * @param key          key to be added.
     * @param cooldownTime time of cooldown.
     *
     * @return created cooldown entry.
     */
    default CooldownEntry<K> add(final K key, final long cooldownTime)
    {
        return this.add(key, cooldownTime, System.currentTimeMillis());
    }

    /**
     * Adds new object to manager with given cooldown time.
     *
     * @param key          key to be added.
     * @param cooldownTime time of cooldown.
     * @param from         time to use as current time. (in milliseconds)
     */
    CooldownEntry<K> add(K key, long cooldownTime, long from);

    /**
     * Removes (cancel cooldown) given key from manager.
     *
     * @param key key to cancel cooldown.
     *
     * @return removed cooldown entry or null if there was no entry for given key.
     */
    CooldownEntry<K> remove(K key);

    /**
     * Returns true if given key isn't in manager or his cooldown already expired. <br>
     * If cooldown will be expired at moment of check, it will be removed from manager.
     *
     * @param key key to be checked.
     *
     * @return true if given key isn't in manager or his cooldown already expired.
     */
    default boolean check(final K key)
    {
        return this.check(key, System.currentTimeMillis());
    }

    /**
     * Returns true if given key isn't in manager or his cooldown already expired. <br>
     * If cooldown will be expired at moment of check, it will be removed from manager.
     *
     * @param key  key to be checked.
     * @param from time to use as current time. (in milliseconds)
     *
     * @return true if given key isn't in manager or his cooldown already expired.
     */
    boolean check(K key, long from);

    /**
     * Returns cooldown entry for given key, or null if entry don't exist. <br>
     * If cooldown will be expired at moment of check, it will be removed from manager, but entry will be still returned.
     *
     * @param key key to check.
     *
     * @return cooldown entry for given key, or null if entry don't exist.
     */
    CooldownEntry<K> getEntry(final K key);

    /**
     * Check all entries if they are expired, and remove them from manager.
     *
     * @return all removed entires.
     */
    default Set<? extends CooldownEntry<K>> getExpired()
    {
        return this.getExpired(System.currentTimeMillis());
    }

    /**
     * Check all entries if they are expired, and remove them from manager.
     *
     * @param from time to use as current time. (in milliseconds)
     *
     * @return all removed entires.
     */
    Set<? extends CooldownEntry<K>> getExpired(long from);

    /**
     * Returns true if there is no entires in this manager.
     *
     * @return true if there is no entires in this manager.
     */
    boolean isEmpty();

    /**
     * Returns all entires without removing expired one.
     *
     * @return all entires without removing expired one.
     */
    Collection<? extends CooldownEntry<K>> getEntires();

    /**
     * Create and return defult, basic (concurrent) implementation of cooldown manager.
     *
     * @param initialSize initial size of backing hashmap.
     * @param <K>         type of keys.
     *
     * @return created defult, basic implementation of cooldown manager.
     */
    static <K> CooldownManager<K> createManager(final int initialSize)
    {
        return new BasicCooldownManager<>(initialSize);
    }

    /**
     * Create and return defult, basic (concurrent) implementation of cooldown manager.
     *
     * @param clazz       clazz defining type of manager keys, used only as generic type selector.
     * @param initialSize initial size of backing hashmap.
     * @param <K>         type of keys.
     *
     * @return created defult, basic implementation of cooldown manager.
     */
    static <K> CooldownManager<K> createManager(final Class<K> clazz, final int initialSize)
    {
        return createManager(initialSize);
    }

}
