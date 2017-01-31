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

import net.engio.mbassy.bus.MBassador;
import net.engio.mbassy.bus.config.BusConfiguration;
import net.engio.mbassy.bus.config.Feature.AsynchronousHandlerInvocation;
import net.engio.mbassy.bus.config.Feature.AsynchronousMessageDispatch;
import net.engio.mbassy.bus.config.Feature.SyncPubSub;
import net.engio.mbassy.bus.config.IBusConfiguration;

public final class DioriteEventBus extends MBassador<Event>
{
    private final EventSubscriptionManager subscriptionManager;

    private DioriteEventBus(IBusConfiguration configuration)
    {
        super(configuration);
        this.subscriptionManager = this.getRuntime().get(SubscriptionManagerProvider.KEY);
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
                        .addFeature(AsynchronousHandlerInvocation.Default());
        return new DioriteEventBus(configuration);
    }
}
