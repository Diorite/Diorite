/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017. Diorite (by Bartłomiej Mazur (aka GotoFinal))
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

package org.diorite.registry;

import javax.annotation.Nullable;

import java.util.Set;

/**
 * Represents registry, registry is like simple Map, but often optimized for concurrent reads and with support for wildcard keys. <br>
 * In registry both keys and values are unique.
 *
 * @param <K>
 *         key type.
 * @param <V>
 *         value type.
 */
public interface Registry<K, V>
{
    /**
     * Returns registry value for given key.
     *
     * @param key
     *         key object.
     *
     * @return registry value for given key.
     */
    @Nullable
    V get(K key);

    /**
     * Returns all registry values that are registered using matching key.
     *
     * @param key
     *         key object.
     *
     * @return all registry values that are registered using matching key.
     */
    Set<? extends V> getAll(K key);

    /**
     * Register new registry entry with given key and value.
     *
     * @param key
     *         entry key.
     * @param value
     *         entry value.
     */
    void register(K key, V value);

    /**
     * Returns set of available values.
     *
     * @return set of available values.
     */
    Set<? extends V> values();

    /**
     * Returns set of available keys.
     *
     * @return set of available keys.
     */
    Set<? extends K> keys();

    /**
     * Returns set of available entries.
     *
     * @return set of available entries.
     */
    Set<? extends RegistryEntry<? extends K, ? extends V>> entries();

    /**
     * Returns size of registry.
     *
     * @return size of registry.
     */
    int size();

    /**
     * Represents registry entry.
     *
     * @param <K>
     *         key type.
     * @param <V>
     *         value type.
     */
    interface RegistryEntry<K, V>
    {
        K getKey();
        V getValue();
    }
}
