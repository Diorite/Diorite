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

import org.apache.commons.lang3.builder.ToStringBuilder;

import org.diorite.commons.objects.Cancellable;
import org.diorite.core.protocol.connection.ActiveConnection;
import org.diorite.event.Event;

public class ReceivePacketEvent implements Event, Cancellable
{
    private final ActiveConnection activeConnection;
    private       Packet           packet;
    private       boolean          cancelled;

    public ReceivePacketEvent(ActiveConnection connection, Packet packet)
    {
        this.activeConnection = connection;
        this.packet = packet;
    }

    public ActiveConnection getActiveConnection()
    {
        return this.activeConnection;
    }

    public Packet getPacket()
    {
        return this.packet;
    }

    public void setPacket(Packet packet)
    {
        this.packet = packet;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this).appendSuper(super.toString()).append("activeConnection", this.activeConnection).append("packet", this.packet)
                                        .toString();
    }

    @Override
    public boolean isCancelled()
    {
        return this.cancelled;
    }

    @Override
    public void setCancelled(boolean cancelled)
    {
        this.cancelled = cancelled;
    }
}
