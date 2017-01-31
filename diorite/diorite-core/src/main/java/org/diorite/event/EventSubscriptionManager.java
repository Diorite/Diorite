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

package org.diorite.event;

import javax.annotation.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;

import org.diorite.Diorite;
import org.diorite.commons.reflections.DioriteReflectionUtils;

import net.engio.mbassy.bus.BusRuntime;
import net.engio.mbassy.common.ReflectionUtils;
import net.engio.mbassy.common.StrongConcurrentSet;
import net.engio.mbassy.listener.MessageHandler;
import net.engio.mbassy.listener.MetadataReader;
import net.engio.mbassy.subscription.Subscription;
import net.engio.mbassy.subscription.SubscriptionFactory;
import net.engio.mbassy.subscription.SubscriptionManager;

/**
 * The subscription managers responsibility is to consistently handle and synchronize the message listener subscription process.
 * It provides fast lookup of existing subscriptions when another instance of an already known
 * listener is subscribed and takes care of creating new set of subscriptions for any unknown class that defines
 * message handlers.
 */
@SuppressWarnings("ForLoopReplaceableByForEach")
class EventSubscriptionManager extends SubscriptionManager
{
    // The metadata reader that is used to inspect objects passed to the subscribe method
    private final MetadataReader metadataReader;

    // All subscriptions per message type
    // This is the primary list for dispatching a specific message
    // write access is synchronized and happens only when a listener of a specific class is registered the first time
    private final Map<Class<?>, ArrayList<Subscription>> subscriptionsPerMessage;

    // All subscriptions per messageHandler type
    // This map provides fast access for subscribing and unsubscribing
    // write access is synchronized and happens very infrequently
    // once a collection of subscriptions is stored it does not change
    private final Map<Class<?>, SubscriptionWrapper[]> subscriptionsPerListener;

    // Remember already processed classes that do not contain any message handlers
    private final StrongConcurrentSet<Class<?>> nonListeners = new StrongConcurrentSet<>();

    // This factory is used to create specialized subscriptions based on the given message handler configuration
    // It can be customized by implementing the getSubscriptionFactory() method
    private final SubscriptionFactory subscriptionFactory;

    // Synchronize read/write access to the subscription maps
    private final ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    private final BusRuntime runtime;

    @SuppressWarnings({"unchecked", "rawtypes"})
    EventSubscriptionManager(MetadataReader metadataReader, SubscriptionFactory subscriptionFactory, BusRuntime runtime)
    {
        super(metadataReader, subscriptionFactory, runtime);
        this.metadataReader = metadataReader;
        this.subscriptionFactory = subscriptionFactory;
        this.runtime = runtime;

        Map<Class<?>, ArrayList<Subscription>> subscriptionsPerMessage = null;
        Map<Class<?>, SubscriptionWrapper[]> subscriptionsPerListener = null;
        try
        {
            subscriptionsPerMessage = (Map) DioriteReflectionUtils.getField(SubscriptionManager.class, "subscriptionsPerMessage").get(this);
            subscriptionsPerListener = (Map) DioriteReflectionUtils.getField(SubscriptionManager.class, "subscriptionsPerListener").get(this);
        }
        catch (Exception e)
        {
            Diorite.getDiorite().debug(e);
        }
        if (subscriptionsPerMessage == null)
        {
            subscriptionsPerMessage = new HashMap<>(256);
        }
        if (subscriptionsPerListener == null)
        {
            subscriptionsPerListener = new HashMap<>(256);
        }
        this.subscriptionsPerMessage = subscriptionsPerMessage;
        this.subscriptionsPerListener = subscriptionsPerListener;
    }

    @Override
    public boolean unsubscribe(@Nullable Object listener)
    {
        if (listener == null)
        {
            return false;
        }
        SubscriptionWrapper[] subscriptions = this.getSubscriptionsByListener(listener);
        if (subscriptions == null)
        {
            return false;
        }
        boolean isRemoved = true;
        for (SubscriptionWrapper subscription : subscriptions)
        {
            isRemoved &= subscription.subscription.unsubscribe(listener);
        }
        return isRemoved;
    }

    public void reEnable(@Nullable Object listener, Class<?> messageType)
    {
        if (listener == null)
        {
            return;
        }
        SubscriptionWrapper[] subscriptions = this.getSubscriptionsByListener(listener);
        if (subscriptions == null)
        {
            return;
        }
        boolean isRemoved = true;
        for (SubscriptionWrapper subscription : subscriptions)
        {
            if (subscription.subscription.contains(listener) && (subscription.handler instanceof EventMessageHandler))
            {
                ((EventMessageHandler) subscription.handler).reRegister();
            }
        }
    }

    public void disable(@Nullable Object listener, Class<?> messageType)
    {
        if (listener == null)
        {
            return;
        }
        SubscriptionWrapper[] subscriptions = this.getSubscriptionsByListener(listener);
        if (subscriptions == null)
        {
            return;
        }
        boolean isRemoved = true;
        for (SubscriptionWrapper subscription : subscriptions)
        {
            if (subscription.subscription.contains(listener) && (subscription.handler instanceof EventMessageHandler))
            {
                ((EventMessageHandler) subscription.handler).unregister();
            }
        }
    }

    @Nullable
    private SubscriptionWrapper[] getSubscriptionsByListener(Object listener)
    {
        SubscriptionWrapper[] subscriptions;
        ReadLock readLock = this.readWriteLock.readLock();
        try
        {
            readLock.lock();
            subscriptions = this.subscriptionsPerListener.get(listener.getClass());
        }
        finally
        {
            readLock.unlock();
        }
        return subscriptions;
    }

    @Override
    public void subscribe(Object listener)
    {
        try
        {
            Class<?> listenerClass = listener.getClass();

            if (this.nonListeners.contains(listenerClass))
            {
                return; // early reject of known classes that do not define message handlers
            }
            SubscriptionWrapper[] subscriptionsByListener = this.getSubscriptionsByListener(listener);
            // a listener is either subscribed for the first time
            if (subscriptionsByListener == null)
            {
                MessageHandler[] messageHandlers = this.metadataReader.getMessageListener(listenerClass).getHandlers();
                int length = messageHandlers.length;

                if (length == 0)
                {  // remember the class as non listening class if no handlers are found
                    this.nonListeners.add(listenerClass);
                    return;
                }
                subscriptionsByListener = new SubscriptionWrapper[length]; // it's safe to use non-concurrent collection here (read only)

                // create subscriptions for all detected message handlers
                MessageHandler messageHandler;
                for (int i = 0; i < length; i++)
                {
                    messageHandler = messageHandlers[i];
                    subscriptionsByListener[i] =
                            new SubscriptionWrapper(this.subscriptionFactory.createSubscription(this.runtime, messageHandler), messageHandler);
                }

                // this will acquire a write lock and handle the case when another thread already subscribed
                // this particular listener in the mean-time
                this.subscribe(listener, subscriptionsByListener);
            } // [1]...or the subscriptions already exists and must only be updated
            else
            {
                for (SubscriptionWrapper sub : subscriptionsByListener)
                {
                    sub.subscription.subscribe(listener);
                }
            }

        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    static class SubscriptionWrapper
    {
        SubscriptionWrapper(Subscription subscription, MessageHandler handler)
        {
            this.subscription = subscription;
            this.handler = handler;
        }

        final Subscription   subscription;
        final MessageHandler handler;
    }

    private void subscribe(Object listener, SubscriptionWrapper[] subscriptions)
    {
        WriteLock writeLock = this.readWriteLock.writeLock();
        try
        {
            writeLock.lock();
            // Basically this is a deferred double check.
            // It's an ugly pattern but necessary because atomic upgrade from read to write lock
            // is not possible.
            // The alternative of using a write lock from the beginning would decrease performance dramatically
            // due to the read heavy read:write ratio
            SubscriptionWrapper[] subscriptionsByListener = this.getSubscriptionsByListener(listener);

            if (subscriptionsByListener == null)
            {
                for (int i = 0, n = subscriptions.length; i < n; i++)
                {
                    SubscriptionWrapper subscription = subscriptions[i];
                    subscription.subscription.subscribe(listener);

                    for (Class<?> messageType : subscription.subscription.getHandledMessageTypes())
                    {
                        // associate a subscription with a message type
                        ArrayList<Subscription> subscriptions2 = this.subscriptionsPerMessage.computeIfAbsent(messageType, k -> new ArrayList<>(8));
                        subscriptions2.add(subscription.subscription);
                    }
                }

                this.subscriptionsPerListener.put(listener.getClass(), subscriptions);
            }
            // the rare case when multiple threads concurrently subscribed the same class for the first time
            // one will be first, all others will subscribe to the newly created subscriptions
            else
            {
                for (int i = 0, n = subscriptionsByListener.length; i < n; i++)
                {
                    SubscriptionWrapper existingSubscription = subscriptionsByListener[i];
                    existingSubscription.subscription.subscribe(listener);
                }
            }
        }
        finally
        {
            writeLock.unlock();
        }
    }

    // obtain the set of subscriptions for the given message type
    // Note: never returns null!
    @SuppressWarnings("rawtypes")
    @Override
    public Collection<Subscription> getSubscriptionsByMessageType(Class messageType)
    {
        Set<Subscription> subscriptions = new TreeSet<>(Subscription.SubscriptionByPriorityDesc);
        ReadLock readLock = this.readWriteLock.readLock();
        try
        {
            readLock.lock();

            Subscription subscription;
            ArrayList<Subscription> subsPerMessage = this.subscriptionsPerMessage.get(messageType);

            if (subsPerMessage != null)
            {
                subscriptions.addAll(subsPerMessage);
            }

            Class<?>[] types = ReflectionUtils.getSuperTypes(messageType);
            for (int i = 0, n = types.length; i < n; i++)
            {
                Class<?> eventSuperType = types[i];
                ArrayList<Subscription> subs = this.subscriptionsPerMessage.get(eventSuperType);
                if (subs != null)
                {
                    for (int j = 0, m = subs.size(); j < m; j++)
                    {
                        subscription = subs.get(j);
                        if (subscription.handlesMessageType(messageType))
                        {
                            subscriptions.add(subscription);
                        }
                    }
                }
            }
        }
        finally
        {
            readLock.unlock();
        }
        return subscriptions;
    }
}
