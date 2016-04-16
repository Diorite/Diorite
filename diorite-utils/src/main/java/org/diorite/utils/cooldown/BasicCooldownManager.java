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
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

class BasicCooldownManager<K> implements CooldownManager<K>
{
    private static final int DEFAULT_INITIAL_SIZE = 50;

    private final ConcurrentHashMap<K, BasicCooldownEntry> map;

    BasicCooldownManager(final int initialSize)
    {
        this.map = new ConcurrentHashMap<>(initialSize);
    }

    @Override
    public BasicCooldownEntry add(final K key, final long cooldownTime, final long from)
    {
        BasicCooldownEntry entry = this.map.get(key);
        if (entry == null)
        {
            entry = new BasicCooldownEntry(key, from, cooldownTime);
            this.map.put(key, entry);
        }
        else
        {
            entry.reset(from, cooldownTime);
        }
        return entry;
    }

    @Override
    public BasicCooldownEntry remove(final K key)
    {
        return this.map.remove(key);
    }

    @Override
    public boolean hasExpired(final K key, final long from)
    {
        final BasicCooldownEntry entry = this.map.get(key);
        return (entry == null) || (entry.deltaLazy(from) <= 0);
    }

    @Override
    public boolean hasExpiredOrAdd(final K key, final long cooldownTime, final long from)
    {
        BasicCooldownEntry entry = this.map.get(key);
        if (entry == null)
        {
            entry = new BasicCooldownEntry(key, from, cooldownTime);
            this.map.put(key, entry);
            return true;
        }
        if (entry.deltaLazy(from) > 0)
        {
            return false;
        }
        entry.reset(from, cooldownTime);
        return true;
    }

    @Override
    public BasicCooldownEntry getEntry(final K key)
    {
        return this.map.get(key);
    }

    @Override
    public Set<BasicCooldownEntry> getExpired(final long from)
    {
        Set<BasicCooldownEntry> expired = null;
        for (final Iterator<Entry<K, BasicCooldownEntry>> iterator = this.map.entrySet().iterator(); iterator.hasNext(); )
        {
            final BasicCooldownEntry entry = iterator.next().getValue();
            if (entry.deltaLazy(from) <= 0)
            {
                if (expired == null)
                {
                    expired = new HashSet<>((this.map.size() / 2) + 1);
                }
                expired.add(entry);
                iterator.remove();
            }
        }
        return (expired == null) ? Collections.emptySet() : expired;
    }

    @Override
    public boolean removeExpired(final long from)
    {
        return this.map.entrySet().removeIf(e -> e.getValue().deltaLazy(from) <= 0);
    }

    @Override
    public boolean isEmpty()
    {
        return this.map.isEmpty();
    }

    @Override
    public void clear()
    {
        this.map.clear();
    }

    @Override
    public Collection<BasicCooldownEntry> getEntires()
    {
        return this.map.values();
    }

    private class BasicCooldownEntry implements CooldownEntry<K>
    {
        private final K    key;
        private       long startTime;
        private       long cooldownTime;

        private BasicCooldownEntry(final K key, final long startTime, final long cooldownTime)
        {
            this.key = key;
            this.startTime = startTime;
            this.cooldownTime = cooldownTime;
        }

        @Override
        public K getKey()
        {
            return this.key;
        }

        @Override
        public long getStartTime()
        {
            return this.startTime;
        }

        @Override
        public long getCooldownTime()
        {
            return this.cooldownTime;
        }

        private synchronized void reset(final long startTime, final long cooldownTime)
        {
            this.startTime = startTime;
            this.cooldownTime = cooldownTime;
        }

        @Override
        public synchronized long delta(final long currentTime)
        {
            final long delta = this.deltaLazy(currentTime);
            if (delta <= 0)
            {
                BasicCooldownManager.this.map.remove(this.key);
            }
            return delta;
        }

        @Override
        public synchronized long deltaLazy(final long currentTime)
        {
            final long sum = this.startTime + this.cooldownTime;
            return sum - currentTime;
        }

        private BasicCooldownManager<K> getManager()
        {
            return BasicCooldownManager.this;
        }

        @Override
        public boolean equals(final Object o)
        {
            if (this == o)
            {
                return true;
            }
            if (! (o instanceof BasicCooldownManager.BasicCooldownEntry))
            {
                return false;
            }
            final BasicCooldownManager<?>.BasicCooldownEntry that = (BasicCooldownManager<?>.BasicCooldownEntry) o;
            return (BasicCooldownManager.this == that.getManager()) && (this.startTime == that.startTime) && (this.cooldownTime == that.cooldownTime) && this.key.equals(that.key);
        }

        @Override
        public int hashCode()
        {
            int result = BasicCooldownManager.this.hashCode();
            result = (31 * result) + this.key.hashCode();
            result = (31 * result) + (int) (this.startTime ^ (this.startTime >>> 32));
            result = (31 * result) + (int) (this.cooldownTime ^ (this.cooldownTime >>> 32));
            return result;
        }

        @Override
        public String toString()
        {
            return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("key", this.key).append("startTime", this.startTime).append("cooldownTime", this.cooldownTime).toString();
        }
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("map", this.map).toString();
    }
}
