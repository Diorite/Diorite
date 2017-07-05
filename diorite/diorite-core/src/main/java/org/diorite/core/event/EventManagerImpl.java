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

package org.diorite.core.event;

import javax.annotation.Nullable;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;

import org.diorite.commons.sets.ConcurrentSet;
import org.diorite.event.DioriteEventBus;
import org.diorite.event.Event;
import org.diorite.event.EventManager;
import org.diorite.event.Listener;
import org.diorite.plugin.Plugin;

public class EventManagerImpl implements EventManager
{
    private final DioriteEventBus dioriteEventBus = DioriteEventBus.create();

    private final Multimap<Plugin, Listener> pluginListeners   = Multimaps.newSetMultimap(new ConcurrentHashMap<>(20), () -> new ConcurrentSet<>(10));
    private final Map<Listener, Plugin>      listenerPluginMap = new ConcurrentHashMap<>(100);

    private final Set<Listener> dioriteListeners = new ConcurrentSet<>(100);

    @Override
    public void callEvent(Event event)
    {
        this.dioriteEventBus.publish(event);
    }

    @Override
    public void registerListener(Plugin plugin, Listener listener)
    {
        if (this.listenerPluginMap.containsKey(listener))
        {
            throw new IllegalStateException("Listener already registered!");
        }
        this.dioriteEventBus.subscribe(listener);
        this.pluginListeners.put(plugin, listener);
        this.listenerPluginMap.put(listener, plugin);
    }

    @Override
    public void unregisterListener(Listener listener)
    {
        this.dioriteEventBus.unsubscribe(listener);
        Plugin plugin = this.listenerPluginMap.remove(listener);
        if (plugin != null)
        {
            this.pluginListeners.remove(plugin, listener);
        }
    }

    @Nullable
    @Override
    public Plugin getListenerOwner(Listener listener)
    {
        return this.listenerPluginMap.get(listener);
    }

    public void unregisterListener(Plugin plugin, Listener listener)
    {
        this.dioriteEventBus.unsubscribe(listener);
        Plugin temp = this.listenerPluginMap.remove(listener);
        // low chance that this will fail
        if (plugin == temp)
        {
            this.pluginListeners.remove(plugin, listener);
        }
        else
        {
            this.listenerPluginMap.put(listener, temp);
        }
    }

    @Override
    public Collection<Listener> unregisterAllListeners(Class<? extends Listener> type)
    {
        Collection<Listener> result = new LinkedList<>();
        for (Iterator<Listener> iterator = this.pluginListeners.values().iterator(); iterator.hasNext(); )
        {
            Listener listener = iterator.next();
            if (type.isInstance(listener))
            {
                iterator.remove();
                result.add(listener);
                this.listenerPluginMap.remove(listener);
                this.dioriteEventBus.unsubscribe(listener);
            }
        }
        return result;
    }

    @Override
    public Collection<Listener> unregisterAllListeners(Plugin plugin)
    {
        Collection<Listener> listeners = this.pluginListeners.removeAll(plugin);
        for (Listener listener : listeners)
        {
            this.unregisterListener(plugin, listener);
        }
        return listeners;
    }

    public void registerDioriteListener(Listener listener)
    {
        this.dioriteListeners.add(listener);
        this.dioriteEventBus.subscribe(listener);
    }

    public void unregisterDioriteListener(Listener listener)
    {
        this.dioriteListeners.remove(listener);
        this.dioriteEventBus.unsubscribe(listener);
    }

    public Set<Listener> getDioriteListeners()
    {
        return Collections.unmodifiableSet(this.dioriteListeners);
    }
}
