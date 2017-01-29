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

package org.diorite.core.protocol.connection.internal;

import javax.annotation.Nullable;

import org.apache.commons.lang3.builder.ToStringBuilder;

import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

public class QueuedPacket
{
    private final           Packet<?>                                               packet;
    @Nullable private final GenericFutureListener<? extends Future<? super Void>>[] listeners;

    @SafeVarargs
    public QueuedPacket(Packet<?> packet, @Nullable GenericFutureListener<? extends Future<? super Void>>... listeners)
    {
        this.packet = packet;
        this.listeners = listeners;
    }

    public Packet<?> getPacket()
    {
        return this.packet;
    }

    @Nullable
    public GenericFutureListener<? extends Future<? super Void>>[] getListeners()
    {
        return this.listeners;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this).appendSuper(super.toString()).append("packet", this.packet).append("listeners", this.listeners).toString();
    }
}
