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

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.engio.mbassy.bus.BusRuntime;
import net.engio.mbassy.bus.MBassador;
import net.engio.mbassy.bus.MessagePublication;
import net.engio.mbassy.bus.config.BusConfiguration;
import net.engio.mbassy.bus.config.Feature.AsynchronousHandlerInvocation;
import net.engio.mbassy.bus.config.Feature.AsynchronousMessageDispatch;
import net.engio.mbassy.bus.config.Feature.SyncPubSub;
import net.engio.mbassy.bus.config.IBusConfiguration;
import net.engio.mbassy.bus.error.PublicationError;
import net.engio.mbassy.subscription.Subscription;

public final class DioriteEventBus extends MBassador<Event>
{
    private static final Logger logger = LoggerFactory.getLogger("[event-bus]");

    // diorite cache unused events, cleared on every listener register, improves total performance.
    private static final Set<Class<?>> unusedEvents = new HashSet<>(500);

    private final EventSubscriptionManager subscriptionManager;

    private DioriteEventBus(IBusConfiguration configuration)
    {
        super(configuration);
        this.subscriptionManager = this.getRuntime().get(SubscriptionManagerProvider.KEY);
    }

    public void clearCache()
    {
        unusedEvents.clear();
    }

    public boolean isDeadType(Class<?> type)
    {
        return unusedEvents.contains(type);
    }

    // skip dead messages
    @Override
    protected MessagePublication createMessagePublication(Event message)
    {
        Class<? extends Event> messageClass = message.getClass();
        Collection<Subscription> subscriptions = this.getSubscriptionsByMessageType(messageClass);
        if ((subscriptions == null) || subscriptions.isEmpty())
        {
            // unused event
            unusedEvents.add(messageClass);
            return new DummyMessage(this.getRuntime(), Collections.emptyList(), message);
        }
        else
        {
            return this.getPublicationFactory().createPublication(this.getRuntime(), subscriptions, message);
        }
    }

    public void reRegisterTypeListener(@Nullable Object listener, Class<?> messageType)
    {
        this.subscriptionManager.reEnable(listener, messageType);
    }

    public void unregisterTypeListener(@Nullable Object listener, Class<?> messageType)
    {
        this.subscriptionManager.disable(listener, messageType);
    }

    public static DioriteEventBus create()
    {
        IBusConfiguration configuration =
                new BusConfiguration()
                        .addFeature(SyncPubSub.Default()
                                              .setMetadataReader(new EventHandlerMetadataReader())
                                              .setSubscriptionManagerProvider(SubscriptionManagerProvider.singleton()))
                        .addFeature(AsynchronousMessageDispatch.Default())
                        .addFeature(AsynchronousHandlerInvocation.Default())
                        .addPublicationErrorHandler(error -> logger.error(
                                "Error publishing event: " + error.getPublishedMessage() + " to: " + error.getHandler() + " from listener: " +
                                error.getListener() + ", error: " + error.getMessage(), error.getCause()));
        return new DioriteEventBus(configuration);
    }

    private static class DummyMessage extends MessagePublication
    {
        protected DummyMessage(BusRuntime runtime, Collection<Subscription> subscriptions, Object message)
        {
            super(runtime, subscriptions, message, null);
        }

        @Override
        public boolean add(Subscription subscription) {return true;}

        @Override
        public void execute() {}

        @Override
        public boolean isFinished() {return true;}

        @Override
        public boolean isRunning() {return false;}

        @Override
        public boolean isScheduled() {return false;}

        @Override
        public boolean hasError() {return false;}

        @Override
        @Nullable
        public PublicationError getError() {return null;}

        @Override
        public void markDispatched() {}

        @Override
        public void markError(PublicationError error) {}

        @Override
        public MessagePublication markScheduled() {return this;}

        @Override
        public boolean isDeadMessage() {return true;}

        @Override
        public boolean isFilteredMessage() {return false;}
    }
}
