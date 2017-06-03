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
 * In registry both keys and values are unique. <br>
 * When requested game id is from unknown namespace registry will try to find value from minecraft namespace, and if value isn't found it will try to find any
 * matching one.
 *
 * @param <V>
 *         value type.
 */
public interface SimpleRegistry<V extends HasGameId>
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
    V get(GameId key);

    /**
     * Returns registry value for given key.
     *
     * @param key
     *         key object.
     *
     * @return registry value for given key.
     *
     * @exception IllegalStateException
     *         if registry is missing given key.
     */
    default V getOrThrow(GameId key) throws IllegalStateException
    {
        V v = this.get(key);
        if (v == null)
        {
            throw new IllegalStateException("Missing registry entry for key: " + key);
        }
        return v;
    }

    /**
     * Returns all values from given namespace.
     *
     * @param namespace
     *         namespace name.
     *
     * @return all values from given namespace.
     */
    Set<? extends V> getAll(String namespace);

    /**
     * Returns all registry values that are registered using matching key.
     *
     * @param key
     *         key object.
     *
     * @return all registry values that are registered using matching key.
     */
    Set<? extends V> getAll(GameId key);

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
    Set<? extends GameId> keys();

    /**
     * Returns size of registry.
     *
     * @return size of registry.
     */
    int size();
}
