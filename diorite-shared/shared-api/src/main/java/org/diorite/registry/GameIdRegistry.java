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

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.diorite.commons.sets.ConcurrentSet;

/**
 * Represent registry with game id as key. <br>
 * When requested game id is from unknown namespace registry will try to find value from minecraft namespace, and if value isn't found it will try to find any
 * matching one.
 *
 * @param <V>
 *         value type.
 *
 * @see GameId#isMatching(GameId)
 */
public class GameIdRegistry<V extends HasGameId> implements Registry<GameId, V>
{
    private final Map<GameId, V>                cache   = new ConcurrentHashMap<>(50);
    private final Map<GameId, V>                data    = new ConcurrentHashMap<>(50);
    private final Set<GameId>                   keys    = new ConcurrentSet<>();
    private final Set<V>                        values  = new ConcurrentSet<>();
    private final Set<RegistryEntry<GameId, V>> entries = new ConcurrentSet<>();

    private final Map<String, Set<V>> namespaceCache = new ConcurrentHashMap<>(1);

    @Override
    @Nullable
    public V get(GameId key)
    {
        return this.cache.computeIfAbsent(key, k -> {
            if (k.hasNamespace())
            {
                return this.data.get(k);
            }
            GameId gameId = k.toMinecraftNamespace();
            V v = this.data.get(gameId);
            if (v == null)
            {
                return this.data.get(k);
            }
            return v;
        });
    }

    @Override
    public Set<? extends V> getAll(GameId key)
    {
        Set<V> set = new HashSet<>(this.data.size());
        for (V v : this.data.values())
        {
            if (v.getGameId().isMatching(key))
            {
                set.add(v);
            }
        }
        return set;
    }

    /**
     * Returns all values from given namespace.
     *
     * @param namespace
     *         namespace name.
     *
     * @return all values from given namespace.
     */
    public Set<? extends V> getAll(String namespace)
    {
        namespace = namespace.toLowerCase();
        return new HashSet<>(this.namespaceCache.computeIfAbsent(namespace, n -> {
            Set<V> set = new HashSet<>(this.data.size());
            for (V v : this.data.values())
            {
                if (v.getGameId().getNamespace().equals(n))
                {
                    set.add(v);
                }
            }
            return set;
        }));
    }

    /**
     * Register new registry entry using value and key provided by value.
     *
     * @param value
     *         value to register.
     *
     * @see HasGameId#getGameId()
     */
    public void register(V value)
    {
        this.register(value.getGameId(), value);
    }

    @Override
    public void register(GameId key, V value)
    {
        if (! key.hasNamespace())
        {
            throw new IllegalStateException("Can't register value with game id of unknown namespace!");
        }
        if (key != value.getGameId())
        {
            throw new IllegalStateException("Can't register value with different game id than provided by value!");
        }
        synchronized (this.data)
        {
            this.data.put(key, value);
            this.keys.add(key);
            this.values.add(value);
            this.entries.add(new GameIdRegistryEntry<>(value));
            if (key.getNamespace() != GameId.MINECRAFT)
            {
                this.data.put(key.toUnknownNamespace(), value);
            }
            this.cache.clear();
            this.namespaceCache.remove(key.getNamespace());
        }
    }

    @Override
    public Set<? extends V> values()
    {
        return new HashSet<>(this.values);
    }

    @Override
    public Set<? extends GameId> keys()
    {
        return new HashSet<>(this.keys);
    }

    @Override
    public Set<? extends RegistryEntry<? extends GameId, ? extends V>> entries()
    {
        return new HashSet<>(this.entries);
    }

    @Override
    public int size()
    {
        return this.keys.size();
    }

    @Override
    public String toString()
    {
        return this.keys.toString();
    }

    private static final class GameIdRegistryEntry<V extends HasGameId> implements RegistryEntry<GameId, V>
    {
        private final V value;

        private GameIdRegistryEntry(V value) {this.value = value;}

        @Override
        public GameId getKey()
        {
            return this.value.getGameId();
        }

        @Override
        public V getValue()
        {
            return this.value;
        }
    }
}
